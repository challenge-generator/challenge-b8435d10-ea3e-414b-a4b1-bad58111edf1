package com.pragma.banking.transactions.application.usecase;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.pragma.banking.transactions.domain.model.Transaction;
import com.pragma.banking.transactions.domain.model.Transaction.TransactionStatus;
import com.pragma.banking.transactions.domain.model.Transaction.TransactionType;
import com.pragma.banking.transactions.domain.model.Audit;
import com.pragma.banking.transactions.domain.port.TransactionRepositoryPort;
import com.pragma.banking.transactions.domain.port.AuditRepositoryPort;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Tests para ProcessTransactionUseCase - Validación de cumplimiento ACID")
class ProcessTransactionUseCaseTest {

    @Mock
    private TransactionRepositoryPort transactionRepository;

    @Mock
    private AuditRepositoryPort auditRepository;

    @InjectMocks
    private ProcessTransactionUseCase processTransactionUseCase;

    private Transaction testTransaction;
    private UUID transactionId;
    private UUID clientId;

    @BeforeEach
    void setUp() {
        transactionId = UUID.randomUUID();
        clientId = UUID.randomUUID();
        testTransaction = new Transaction(
            transactionId,
            clientId,
            new BigDecimal("2500.00"),
            LocalDateTime.now(),
            TransactionType.DEPOSIT,
            "Depósito de prueba"
        );
        testTransaction.setStatus(TransactionStatus.PENDING);
    }

    @Test
    @DisplayName("Execute debería procesar transacción exitosamente - Atomicidad")
    void execute_shouldProcessTransactionSuccessfully_Atomicity() {
        when(transactionRepository.save(any(Transaction.class))).thenAnswer(invocation -> invocation.getArgument(0));
        when(auditRepository.save(any(Audit.class))).thenAnswer(invocation -> invocation.getArgument(0));
        
        Transaction result = processTransactionUseCase.execute(testTransaction);
        
        assertNotNull(result);
        assertEquals(TransactionStatus.COMPLETED, result.getStatus());
        assertTrue(result.isCompleted());
        verify(transactionRepository, times(2)).save(any(Transaction.class));
        verify(auditRepository, atLeastOnce()).save(any(Audit.class));
    }

    @Test
    @DisplayName("Execute debería rechazar transacción con monto inválido - Consistencia")
    void execute_shouldRejectTransactionWithInvalidAmount_Consistency() {
        Transaction invalidTransaction = new Transaction(
            UUID.randomUUID(),
            clientId,
            new BigDecimal("-100.00"),
            LocalDateTime.now(),
            TransactionType.WITHDRAWAL,
            "Monto negativo"
        );
        invalidTransaction.setStatus(TransactionStatus.PENDING);
        
        assertThrows(IllegalArgumentException.class, () -> processTransactionUseCase.execute(invalidTransaction));
        verify(transactionRepository, never()).save(any(Transaction.class));
    }

    @Test
    @DisplayName("Execute debería manejar fallo de base de datos - Atomicidad")
    void execute_shouldHandleDatabaseFailure_Atomicity() {
        when(transactionRepository.save(any(Transaction.class)))
            .thenAnswer(invocation -> invocation.getArgument(0))
            .thenThrow(new RuntimeException("Error de persistencia"));
        
        Transaction result = processTransactionUseCase.executeWithFallback(testTransaction);
        
        assertNotNull(result);
        assertEquals(TransactionStatus.FAILED, result.getStatus());
        assertTrue(result.isFailed());
    }

    @Test
    @DisplayName("Execute debería validar cliente existente - Consistencia")
    void execute_shouldValidateExistingClient_Consistency() {
        when(transactionRepository.findByClientId(clientId)).thenReturn(Optional.of(testTransaction));
        
        boolean isValid = processTransactionUseCase.validateClient(clientId);
        
        assertTrue(isValid);
        verify(transactionRepository, times(1)).findByClientId(clientId);
    }

    @Test
    @DisplayName("Execute debería rechazar cliente no existente - Consistencia")
    void execute_shouldRejectNonExistingClient_Consistency() {
        UUID nonExistingClientId = UUID.randomUUID();
        when(transactionRepository.findByClientId(nonExistingClientId)).thenReturn(Optional.empty());
        
        boolean isValid = processTransactionUseCase.validateClient(nonExistingClientId);
        
        assertFalse(isValid);
    }

    @Test
    @DisplayName("Execute debería registrar auditoría de la operación - Durabilidad")
    void execute_shouldRegisterAuditOfOperation_Durability() {
        when(transactionRepository.save(any(Transaction.class))).thenAnswer(invocation -> invocation.getArgument(0));
        when(auditRepository.save(any(Audit.class))).thenAnswer(invocation -> invocation.getArgument(0));
        
        processTransactionUseCase.execute(testTransaction);
        
        verify(auditRepository, atLeastOnce()).save(argThat(audit -> 
            audit.getTransactionId().equals(transactionId) &&
            audit.getAction().equals("TRANSACTION_PROCESSED")
        ));
    }

    @Test
    @DisplayName("Execute debería mantener aislamiento entre transacciones")
    void execute_shouldMaintainIsolationBetweenTransactions() {
        Transaction transaction1 = new Transaction(
            UUID.randomUUID(), UUID.randomUUID(), new BigDecimal("500.00"),
            LocalDateTime.now(), TransactionType.DEPOSIT, "Transacción 1"
        );
        Transaction transaction2 = new Transaction(
            UUID.randomUUID(), UUID.randomUUID(), new BigDecimal("800.00"),
            LocalDateTime.now(), TransactionType.WITHDRAWAL, "Transacción 2"
        );
        
        when(transactionRepository.save(any(Transaction.class))).thenAnswer(invocation -> invocation.getArgument(0));
        when(auditRepository.save(any(Audit.class))).thenAnswer(invocation -> invocation.getArgument(0));
        
        Transaction result1 = processTransactionUseCase.execute(transaction1);
        Transaction result2 = processTransactionUseCase.execute(transaction2);
        
        assertNotNull(result1);
        assertNotNull(result2);
        assertNotEquals(result1.getTransactionId(), result2.getTransactionId());
        assertEquals(TransactionStatus.COMPLETED, result1.getStatus());
        assertEquals(TransactionStatus.COMPLETED, result2.getStatus());
    }

    @Test
    @DisplayName("Execute debería aplicar retry en caso de fallo transitorio")
    void execute_shouldApplyRetryOnTransientFailure() {
        when(transactionRepository.save(any(Transaction.class)))
            .thenThrow(new RuntimeException("Fallo transitorio"))
            .thenThrow(new RuntimeException("Fallo transitorio"))
            .thenAnswer(invocation -> invocation.getArgument(0));
        when(auditRepository.save(any(Audit.class))).thenAnswer(invocation -> invocation.getArgument(0));
        
        Transaction result = processTransactionUseCase.executeWithRetry(testTransaction);
        
        assertNotNull(result);
        verify(transactionRepository, times(3)).save(any(Transaction.class));
    }

    @Test
    @DisplayName("Execute debería usar circuit breaker para operaciones críticas")
    void execute_shouldUseCircuitBreakerForCriticalOperations() {
        when(transactionRepository.save(any(Transaction.class))).thenAnswer(invocation -> invocation.getArgument(0));
        when(auditRepository.save(any(Audit.class))).thenAnswer(invocation -> invocation.getArgument(0));
        
        boolean circuitClosed = processTransactionUseCase.isCircuitBreakerClosed();
        
        assertTrue(circuitClosed);
        verify(transactionRepository, times(0)).save(any(Transaction.class));
    }

    @Test
    @DisplayName("Execute debería validar tipo de transacción válido")
    void execute_shouldValidateTransactionType() {
        Transaction depositTransaction = new Transaction(
            UUID.randomUUID(), clientId, new BigDecimal("1000.00"),
            LocalDateTime.now(), TransactionType.DEPOSIT, "Depósito"
        );
        
        Transaction transferTransaction = new Transaction(
            UUID.randomUUID(), clientId, new BigDecimal("500.00"),
            LocalDateTime.now(), TransactionType.TRANSFER, "Transferencia"
        );
        
        assertTrue(processTransactionUseCase.isValidTransactionType(depositTransaction.getType()));
        assertTrue(processTransactionUseCase.isValidTransactionType(transferTransaction.getType()));
    }
}