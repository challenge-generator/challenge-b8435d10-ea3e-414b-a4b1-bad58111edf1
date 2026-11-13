package com.pragma.banking.transactions.application.usecase;

import com.pragma.banking.transactions.domain.model.Audit;
import com.pragma.banking.transactions.domain.model.Transaction;
import com.pragma.banking.transactions.domain.model.Transaction.TransactionStatus;
import com.pragma.banking.transactions.domain.port.AuditRepositoryPort;
import com.pragma.banking.transactions.domain.port.TransactionRepositoryPort;
import com.pragma.banking.transactions.domain.service.TransactionService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
public class ProcessTransactionUseCase {

    private static final Logger log = LoggerFactory.getLogger(ProcessTransactionUseCase.class);

    private final TransactionRepositoryPort transactionRepository;
    private final AuditRepositoryPort auditRepository;
    private final TransactionService transactionService;

    public ProcessTransactionUseCase(
            TransactionRepositoryPort transactionRepository,
            AuditRepositoryPort auditRepository,
            TransactionService transactionService) {
        this.transactionRepository = transactionRepository;
        this.auditRepository = auditRepository;
        this.transactionService = transactionService;
    }

    @Transactional
    @CircuitBreaker(name = "transactionCircuitBreaker", fallbackMethod = "processTransactionFallback")
    @Retry(name = "transactionRetry")
    public Transaction processTransaction(Transaction transaction) {
        log.info("Iniciando procesamiento de transacción: {}", transaction.getTransactionId());

        transactionService.verifyDatabaseConnectivity();

        if (transaction.getAmount().compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("El monto de la transacción debe ser mayor que cero");
        }

        if (transaction.getClientId() == null) {
            throw new IllegalArgumentException("El cliente es obligatorio");
        }

        Transaction savedTransaction = transactionRepository.save(transaction);
        log.info("Transacción guardada con estado: {}", savedTransaction.getStatus());

        Audit auditEntry = new Audit(
                UUID.randomUUID(),
                savedTransaction.getTransactionId(),
                "TRANSACTION_CREATED",
                "SYSTEM",
                LocalDateTime.now(),
                String.format("Transacción creada para cliente %s por monto %s",
                        savedTransaction.getClientId(), savedTransaction.getAmount())
        );
        auditRepository.save(auditEntry);

        return savedTransaction;
    }

    @Transactional
    public Transaction updateTransactionStatus(UUID transactionId, TransactionStatus newStatus) {
        log.info("Actualizando estado de transacción {} a {}", transactionId, newStatus);

        Optional<Transaction> optionalTransaction = transactionRepository.findById(transactionId);
        if (optionalTransaction.isEmpty()) {
            throw new IllegalArgumentException("Transacción no encontrada: " + transactionId);
        }

        Transaction transaction = optionalTransaction.get();
        TransactionStatus oldStatus = transaction.getStatus();
        transaction.setStatus(newStatus);

        Transaction updatedTransaction = transactionRepository.save(transaction);

        Audit auditEntry = new Audit(
                UUID.randomUUID(),
                transactionId,
                "TRANSACTION_STATUS_CHANGED",
                "SYSTEM",
                LocalDateTime.now(),
                String.format("Estado cambiado de %s a %s", oldStatus, newStatus)
        );
        auditRepository.save(auditEntry);

        log.info("Transacción {} actualizada exitosamente", transactionId);
        return updatedTransaction;
    }

    @Transactional(readOnly = true)
    public Optional<Transaction> getTransactionById(UUID transactionId) {
        return transactionRepository.findById(transactionId);
    }

    @Transactional(readOnly = true)
    public java.util.List<Transaction> getTransactionsByClient(UUID clientId) {
        return transactionRepository.findByClientId(clientId);
    }

    @Transactional(readOnly = true)
    public java.util.List<Transaction> getTransactionsByStatus(TransactionStatus status) {
        return transactionRepository.findByStatus(status.name());
    }

    private Transaction processTransactionFallback(Transaction transaction, Throwable t) {
        log.error("Fallback activado para transacción {}: {}", transaction.getTransactionId(), t.getMessage());

        Audit auditEntry = new Audit(
                UUID.randomUUID(),
                transaction.getTransactionId(),
                "TRANSACTION_FAILED",
                "SYSTEM",
                LocalDateTime.now(),
                String.format("Transacción fallida: %s", t.getMessage())
        );
        try {
            auditRepository.save(auditEntry);
        } catch (Exception e) {
            log.error("Error al guardar auditoría de fallback: {}", e.getMessage());
        }

        transaction.setStatus(TransactionStatus.FAILED);
        return transaction;
    }
}