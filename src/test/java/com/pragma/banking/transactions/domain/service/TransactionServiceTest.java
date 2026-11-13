package com.pragma.banking.transactions.domain.service;


import com.pragma.banking.transactions.TransactionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.pragma.banking.transactions.domain.port.TransactionRepositoryPort;
import com.pragma.banking.transactions.domain.port.AuditRepositoryPort;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Tests para TransactionService - Verificación de modelo ACID")
class TransactionServiceTest {

    @Mock
    private TransactionRepositoryPort transactionRepository;

    @Mock
    private AuditRepositoryPort auditRepository;

    @InjectMocks
    private TransactionService transactionService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("verifyDatabaseConnectivity debería verificar la conexión a la base de datos")
    void verifyDatabaseConnectivity_shouldVerifyConnection() {
        doNothing().when(transactionRepository).verifyConnection();
        
        assertDoesNotThrow(() -> transactionService.verifyDatabaseConnectivity());
        verify(transactionRepository, times(1)).verifyConnection();
    }

    @Test
    @DisplayName("verifyDatabaseConnectivity debería lanzar excepción cuando la conexión falla")
    void verifyDatabaseConnectivity_shouldThrowExceptionOnConnectionFailure() {
        doThrow(new RuntimeException("Conexión a base de datos fallida"))
            .when(transactionRepository).verifyConnection();
        
        assertThrows(RuntimeException.class, () -> transactionService.verifyDatabaseConnectivity());
    }

    @Test
    @DisplayName("initializeSampleData debería crear datos de ejemplo para pruebas")
    void initializeSampleData_shouldCreateSampleData() {
        UUID clientId = UUID.randomUUID();
        when(transactionRepository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));
        when(auditRepository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));
        
        assertDoesNotThrow(() -> transactionService.initializeSampleData());
        verify(transactionRepository, atLeastOnce()).save(any());
        verify(auditRepository, atLeastOnce()).save(any());
    }

    @Test
    @DisplayName("El servicio debería mantener atomicidad en las operaciones")
    void service_shouldMaintainAtomicity() {
        UUID transactionId = UUID.randomUUID();
        UUID clientId = UUID.randomUUID();
        BigDecimal amount = new BigDecimal("1000.00");
        
        when(transactionRepository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));
        when(auditRepository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));
        
        boolean success = transactionService.processTransactionWithAtomicity(
            transactionId, clientId, amount, "DEPOSIT", "Depósito inicial"
        );
        
        assertTrue(success);
        verify(transactionRepository, times(1)).save(any());
        verify(auditRepository, times(1)).save(any());
    }

    @Test
    @DisplayName("El servicio debería rollbackear ante fallos - consistencia")
    void service_shouldRollbackOnFailure_EnsuringConsistency() {
        UUID transactionId = UUID.randomUUID();
        UUID clientId = UUID.randomUUID();
        BigDecimal amount = new BigDecimal("5000.00");
        
        when(transactionRepository.save(any()))
            .thenAnswer(invocation -> invocation.getArgument(0))
            .thenThrow(new RuntimeException("Error de base de datos"));
        
        boolean result = transactionService.processTransactionWithAtomicity(
            transactionId, clientId, amount, "WITHDRAWAL", "Retiro"
        );
        
        assertFalse(result);
        verify(transactionRepository, atLeastOnce()).save(any());
        verify(auditRepository, never()).save(any());
    }

    @Test
    @DisplayName("El servicio debería manejar aislamiento entre transacciones concurrentes")
    void service_shouldHandleIsolationBetweenConcurrentTransactions() {
        UUID clientId1 = UUID.randomUUID();
        UUID clientId2 = UUID.randomUUID();
        BigDecimal amount1 = new BigDecimal("100.00");
        BigDecimal amount2 = new BigDecimal("200.00");
        
        when(transactionRepository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));
        when(transactionRepository.findByClientId(clientId1)).thenReturn(java.util.List.of());
        when(transactionRepository.findByClientId(clientId2)).thenReturn(java.util.List.of());
        
        boolean result1 = transactionService.processTransactionWithAtomicity(
            UUID.randomUUID(), clientId1, amount1, "DEPOSIT", "Depósito cliente 1"
        );
        boolean result2 = transactionService.processTransactionWithAtomicity(
            UUID.randomUUID(), clientId2, amount2, "DEPOSIT", "Depósito cliente 2"
        );
        
        assertTrue(result1);
        assertTrue(result2);
    }

    @Test
    @DisplayName("El servicio debería registrar auditoría para trazabilidad - durabilidad")
    void service_shouldRegisterAudit_EnsuringDurability() {
        UUID transactionId = UUID.randomUUID();
        UUID clientId = UUID.randomUUID();
        BigDecimal amount = new BigDecimal("1500.00");
        
        when(transactionRepository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));
        when(auditRepository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));
        
        transactionService.processTransactionWithAtomicity(
            transactionId, clientId, amount, "TRANSFER", "Transferencia"
        );
        
        verify(auditRepository, times(1)).save(argThat(audit -> 
            audit.getTransactionId().equals(transactionId) &&
            audit.getAction().equals("TRANSACTION_CREATED")
        ));
    }
}