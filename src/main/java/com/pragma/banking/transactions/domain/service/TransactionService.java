package com.pragma.banking.transactions.domain.service;

import com.pragma.banking.transactions.domain.model.Audit;
import com.pragma.banking.transactions.domain.model.Transaction;
import com.pragma.banking.transactions.domain.model.Transaction.TransactionStatus;
import com.pragma.banking.transactions.domain.model.Transaction.TransactionType;
import com.pragma.banking.transactions.domain.port.AuditRepositoryPort;
import com.pragma.banking.transactions.domain.port.TransactionRepositoryPort;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Servicio de dominio que gestiona las operaciones de transacciones bancarias.
 * Implementa la lógica de negocio asegurando el cumplimiento del modelo ACID:
 * - Atomicidad: cada operación es atómica, se completa completamente o no se realiza
 * - Consistencia: las transacciones mantienen la integridad de los datos
 * - Aislamiento: las transacciones concurrentes no interfieren entre sí
 * - Durabilidad: una vez completada, la transacción persiste permanentemente
 */
@Service
public class TransactionService {

    private static final Logger logger = LoggerFactory.getLogger(TransactionService.class);
    private static final String SYSTEM_USER = "SYSTEM";

    private final TransactionRepositoryPort transactionRepository;
    private final AuditRepositoryPort auditRepository;

    public TransactionService(TransactionRepositoryPort transactionRepository,
                              AuditRepositoryPort auditRepository) {
        this.transactionRepository = transactionRepository;
        this.auditRepository = auditRepository;
    }

    /**
     * Procesa una nueva transacción bancaria aplicando el modelo ACID.
     * La operación se ejecuta dentro de una transacción que asegura atomicidad.
     * @param clientId identificador del cliente que realiza la transacción
     * @param amount monto de la transacción
     * @param type tipo de transacción (DEPOSIT, WITHDRAWAL, TRANSFER)
     * @param description descripción de la transacción
     * @return la transacción procesada
     */
    @Transactional
    public Transaction processTransaction(UUID clientId, BigDecimal amount,
                                          TransactionType type, String description) {
        logger.info("Iniciando procesamiento de transacción para cliente: {}, monto: {}, tipo: {}",
                    clientId, amount, type);

        validateTransactionInput(clientId, amount, type);

        Transaction transaction = new Transaction(
            UUID.randomUUID(),
            clientId,
            amount,
            LocalDateTime.now(),
            type,
            description,
            TransactionStatus.PENDING
        );

        Transaction savedTransaction = transactionRepository.save(transaction);
        logger.info("Transacción guardada con ID: {}", savedTransaction.getTransactionId());

        createAuditRecord(savedTransaction.getTransactionId(), "TRANSACTION_CREATED",
                         SYSTEM_USER, "Transacción creada con estado PENDING");

        try {
            Transaction completedTransaction = executeTransactionLogic(savedTransaction);
            transactionRepository.updateStatus(completedTransaction.getTransactionId(),
                                               TransactionStatus.COMPLETED);

            createAuditRecord(completedTransaction.getTransactionId(), "TRANSACTION_COMPLETED",
                             SYSTEM_USER, "Transacción completada exitosamente");

            logger.info("Transacción {} completada exitosamente",
                       completedTransaction.getTransactionId());
            return completedTransaction;

        } catch (Exception e) {
            logger.error("Error al procesar transacción {}: {}",
                        savedTransaction.getTransactionId(), e.getMessage());
            transactionRepository.updateStatus(savedTransaction.getTransactionId(),
                                               TransactionStatus.FAILED);

            createAuditRecord(savedTransaction.getTransactionId(), "TRANSACTION_FAILED",
                             SYSTEM_USER, "Error: " + e.getMessage());
            throw e;
        }
    }

    /**
     * Ejecuta la lógica específica de la transacción según su tipo.
     * Este método contiene la lógica de negocio diferenciada por tipo de transacción.
     * @param transaction la transacción a ejecutar
     * @return la transacción con el resultado de la ejecución
     */
    private Transaction executeTransactionLogic(Transaction transaction) {
        switch (transaction.getType()) {
            case DEPOSIT:
                return processDeposit(transaction);
            case WITHDRAWAL:
                return processWithdrawal(transaction);
            case TRANSFER:
                return processTransfer(transaction);
            default:
                throw new IllegalArgumentException("Tipo de transacción no soportado: " + transaction.getType());
        }
    }

    private Transaction processDeposit(Transaction transaction) {
        logger.debug("Procesando depósito para transacción: {}", transaction.getTransactionId());
        validatePositiveAmount(transaction.getAmount());
        return transaction;
    }

    private Transaction processWithdrawal(Transaction transaction) {
        logger.debug("Procesando retiro para transacción: {}", transaction.getTransactionId());
        validatePositiveAmount(transaction.getAmount());
        return transaction;
    }

    private Transaction processTransfer(Transaction transaction) {
        logger.debug("Procesando transferencia para transacción: {}", transaction.getTransactionId());
        validatePositiveAmount(transaction.getAmount());
        return transaction;
    }

    /**
     * Valida los parámetros de entrada de una transacción.
     * @param clientId identificador del cliente
     * @param amount monto de la transacción
     * @param type tipo de transacción
     */
    private void validateTransactionInput(UUID clientId, BigDecimal amount, TransactionType type) {
        if (clientId == null) {
            throw new IllegalArgumentException("El identificador del cliente no puede ser nulo");
        }
        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("El monto debe ser mayor que cero");
        }
        if (type == null) {
            throw new IllegalArgumentException("El tipo de transacción no puede ser nulo");
        }
    }

    /**
     * Valida que el monto sea positivo.
     * @param amount monto a validar
     */
    private void validatePositiveAmount(BigDecimal amount) {
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("El monto debe ser positivo");
        }
    }

    /**
     * Crea un registro de auditoría para una operación.
     * @param transactionId identificador de la transacción
     * @param action acción realizada
     * @param performedBy usuario que realizó la acción
     * @param details detalles de la operación
     */
    private void createAuditRecord(UUID transactionId, String action,
                                   String performedBy, String details) {
        Audit audit = new Audit(
            UUID.randomUUID(),
            transactionId,
            action,
            performedBy,
            LocalDateTime.now(),
            details
        );
        auditRepository.save(audit);
        logger.debug("Registro de auditoría creado para transacción: {}", transactionId);
    }

    /**
     * Recupera una transacción por su identificador.
     * @param transactionId identificador de la transacción
     * @return Optional con la transacción si existe
     */
    @Transactional(readOnly = true)
    public Optional<Transaction> getTransactionById(UUID transactionId) {
        logger.debug("Consultando transacción con ID: {}", transactionId);
        return transactionRepository.findById(transactionId);
    }

    /**
     * Recupera todas las transacciones de un cliente.
     * @param clientId identificador del cliente
     * @return lista de transacciones del cliente
     */
    @Transactional(readOnly = true)
    public List<Transaction> getTransactionsByClient(UUID clientId) {
        logger.debug("Consultando transacciones para cliente: {}", clientId);
        return transactionRepository.findByClientId(clientId);
    }

    /**
     * Recupera las transacciones por estado.
     * @param status estado de las transacciones
     * @return lista de transacciones con el estado especificado
     */
    @Transactional(readOnly = true)
    public List<Transaction> getTransactionsByStatus(TransactionStatus status) {
        logger.debug("Consultando transacciones con estado: {}", status);
        return transactionRepository.findByStatus(status);
    }

    /**
     * Cancela una transacción existente.
     * @param transactionId identificador de la transacción a cancelar
     * @return la transacción cancelada
     */
    @Transactional
    public Transaction cancelTransaction(UUID transactionId) {
        logger.info("Cancelando transacción: {}", transactionId);

        Transaction transaction = transactionRepository.findById(transactionId)
            .orElseThrow(() -> new IllegalArgumentException(
                "Transacción no encontrada: " + transactionId));

        if (transaction.isCompleted()) {
            throw new IllegalStateException(
                "No se puede cancelar una transacción completada: " + transactionId);
        }

        if (transaction.isFailed()) {
            throw new IllegalStateException(
                "La transacción ya está en estado fallido: " + transactionId);
        }

        Transaction updated = transactionRepository.updateStatus(transactionId, TransactionStatus.CANCELLED)
            .orElseThrow(() -> new IllegalStateException(
                "Error al actualizar el estado de la transacción: " + transactionId));

        createAuditRecord(transactionId, "TRANSACTION_CANCELLED", SYSTEM_USER,
                         "Transacción cancelada por el sistema");

        logger.info("Transacción {} cancelada exitosamente", transactionId);
        return updated;
    }

    /**
     * Calcula el balance total de un cliente.
     * @param clientId identificador del cliente
     * @return suma de todos los depósitos menos retiros
     */
    @Transactional(readOnly = true)
    public BigDecimal calculateClientBalance(UUID clientId) {
        logger.debug("Calculando balance para cliente: {}", clientId);

        List<Transaction> transactions = transactionRepository.findByClientId(clientId);

        BigDecimal totalDeposits = transactions.stream()
            .filter(t -> t.getType() == TransactionType.DEPOSIT && t.isCompleted())
            .map(Transaction::getAmount)
            .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal totalWithdrawals = transactions.stream()
            .filter(t -> t.getType() == TransactionType.WITHDRAWAL && t.isCompleted())
            .map(Transaction::getAmount)
            .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal totalTransfersOut = transactions.stream()
            .filter(t -> t.getType() == TransactionType.TRANSFER && t.isCompleted())
            .map(Transaction::getAmount)
            .reduce(BigDecimal.ZERO, BigDecimal::add);

        return totalDeposits.subtract(totalWithdrawals).subtract(totalTransfersOut);
    }

    /**
     * Obtiene el historial de auditoría de una transacción.
     * @param transactionId identificador de la transacción
     * @return lista de registros de auditoría
     */
    @Transactional(readOnly = true)
    public List<Audit> getTransactionAuditHistory(UUID transactionId) {
        logger.debug("Consultando historial de auditoría para transacción: {}", transactionId);
        return auditRepository.findByTransactionId(transactionId);
    }

    /**
     * Verifica la conectividad con la base de datos.
     * Método utilizado para validación de startup.
     */
    public void verifyDatabaseConnectivity() {
        logger.info("Verificando conectividad con la base de datos");
        long count = transactionRepository.count();
        logger.info("Conectividad verificada. Total de transacciones en sistema: {}", count);
    }

    /**
     * Inicializa datos de ejemplo para pruebas.
     * Método utilizado para validación de startup.
     */
    public void initializeSampleData() {
        logger.info("Inicializando datos de ejemplo");
        UUID sampleClientId = UUID.fromString("550e8400-e29b-41d4-a716-446655440000");

        if (transactionRepository.findByClientId(sampleClientId).isEmpty()) {
            Transaction sampleTransaction = new Transaction(
                UUID.randomUUID(),
                sampleClientId,
                new BigDecimal("1000.00"),
                LocalDateTime.now(),
                TransactionType.DEPOSIT,
                "Transacción de ejemplo",
                TransactionStatus.COMPLETED
            );
            transactionRepository.save(sampleTransaction);
            createAuditRecord(sampleTransaction.getTransactionId(), "INITIAL_DATA",
                            SYSTEM_USER, "Datos de ejemplo inicializados");
            logger.info("Datos de ejemplo inicializados correctamente");
        } else {
            logger.info("Los datos de ejemplo ya existen, omitiendo inicialización");
        }
    }
}