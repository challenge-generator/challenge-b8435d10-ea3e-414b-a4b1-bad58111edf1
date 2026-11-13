package com.pragma.banking.transactions.domain.model;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Representa una transacción financiera en el sistema.
 * Incluye validaciones para garantizar atomicidad y consistencia.
 */
public class Transaction {
    @NotNull(message = "El ID de la transacción no puede ser nulo")
    private final UUID transactionId;

    @NotNull(message = "El ID del cliente no puede ser nulo")
    private final UUID clientId;

    @NotNull(message = "El monto no puede ser nulo")
    @DecimalMin(value = "0.01", message = "El monto debe ser mayor que cero")
    private final BigDecimal amount;

    @NotNull(message = "La fecha de la transacción no puede ser nula")
    @PastOrPresent(message = "La fecha de la transacción no puede ser futura")
    private final LocalDateTime transactionDate;

    @NotNull(message = "El tipo de transacción no puede ser nulo")
    private final TransactionType type;

    @Size(max = 200, message = "La descripción no puede exceder 200 caracteres")
    private final String description;

    @NotNull(message = "El estado de la transacción no puede ser nulo")
    private TransactionStatus status;

    public enum TransactionType {
        DEPOSIT, WITHDRAWAL, TRANSFER, PAYMENT
    }

    public enum TransactionStatus {
        PENDING, COMPLETED, FAILED, REVERSED
    }

    public Transaction(UUID transactionId, UUID clientId, BigDecimal amount, 
                      LocalDateTime transactionDate, TransactionType type, String description) {
        this.transactionId = transactionId;
        this.clientId = clientId;
        this.amount = amount;
        this.transactionDate = transactionDate;
        this.type = type;
        this.description = description;
        this.status = TransactionStatus.PENDING;
    }

    public UUID getTransactionId() {
        return transactionId;
    }

    public UUID getClientId() {
        return clientId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public LocalDateTime getTransactionDate() {
        return transactionDate;
    }

    public TransactionType getType() {
        return type;
    }

    public String getDescription() {
        return description;
    }

    public TransactionStatus getStatus() {
        return status;
    }

    public void setStatus(TransactionStatus status) {
        this.status = status;
    }

    public boolean isCompleted() {
        return status == TransactionStatus.COMPLETED;
    }

    public boolean isFailed() {
        return status == TransactionStatus.FAILED;
    }

    @Override
    public String toString() {
        return "Transaction{transactionId=" + transactionId +
                ", clientId=" + clientId +
                ", amount=" + amount +
                ", transactionDate=" + transactionDate +
                ", type=" + type +
                ", description='" + description + '\'' +
                ", status=" + status +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Transaction that = (Transaction) o;
        return transactionId.equals(that.transactionId);
    }

    @Override
    public int hashCode() {
        return transactionId.hashCode();
    }
}