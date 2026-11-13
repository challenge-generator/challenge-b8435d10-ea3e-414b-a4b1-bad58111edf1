package com.pragma.banking.transactions.domain.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Representa un registro de auditoría en el sistema.
 * Registra eventos importantes para garantizar trazabilidad y cumplimiento.
 */
public class Audit {
    @NotNull(message = "El ID de auditoría no puede ser nulo")
    private final UUID auditId;

    @NotNull(message = "El ID de la transacción no puede ser nulo")
    private final UUID transactionId;

    @NotBlank(message = "La acción no puede estar vacía")
    @Size(max = 50, message = "La acción no puede exceder 50 caracteres")
    private final String action;

    @NotBlank(message = "El usuario no puede estar vacío")
    @Size(max = 50, message = "El usuario no puede exceder 50 caracteres")
    private final String performedBy;

    @NotNull(message = "La marca de tiempo no puede ser nula")
    @PastOrPresent(message = "La marca de tiempo no puede ser futura")
    private final LocalDateTime timestamp;

    @Size(max = 200, message = "Los detalles no pueden exceder 200 caracteres")
    private final String details;

    public Audit(UUID auditId, UUID transactionId, String action, 
                String performedBy, LocalDateTime timestamp, String details) {
        this.auditId = auditId;
        this.transactionId = transactionId;
        this.action = action;
        this.performedBy = performedBy;
        this.timestamp = timestamp;
        this.details = details;
    }

    public UUID getAuditId() {
        return auditId;
    }

    public UUID getTransactionId() {
        return transactionId;
    }

    public String getAction() {
        return action;
    }

    public String getPerformedBy() {
        return performedBy;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public String getDetails() {
        return details;
    }

    @Override
    public String toString() {
        return "Audit{auditId=" + auditId +
                ", transactionId=" + transactionId +
                ", action='" + action + '\'' +
                ", performedBy='" + performedBy + '\'' +
                ", timestamp=" + timestamp +
                ", details='" + details + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Audit audit = (Audit) o;
        return auditId.equals(audit.auditId);
    }

    @Override
    public int hashCode() {
        return auditId.hashCode();
    }
}