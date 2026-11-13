package com.pragma.banking.transactions.infrastructure.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "audit_logs", indexes = {
    @Index(name = "idx_audit_transaction_id", columnList = "transaction_id"),
    @Index(name = "idx_audit_timestamp", columnList = "timestamp"),
    @Index(name = "idx_audit_action", columnList = "action"),
    @Index(name = "idx_audit_performed_by", columnList = "performed_by")
})
public class AuditEntity {

    @Id
    @Column(name = "audit_id", nullable = false)
    private UUID auditId;

    @Column(name = "transaction_id", nullable = false)
    private UUID transactionId;

    @Column(name = "action", nullable = false, length = 50)
    private String action;

    @Column(name = "performed_by", nullable = false, length = 100)
    private String performedBy;

    @Column(name = "timestamp", nullable = false)
    private LocalDateTime timestamp;

    @Column(name = "details", columnDefinition = "TEXT")
    private String details;

    @Column(name = "ip_address", length = 45)
    private String ipAddress;

    @Column(name = "user_agent", length = 500)
    private String userAgent;

    @Column(name = "success", nullable = false)
    private Boolean success;

    @Column(name = "error_message", length = 1000)
    private String errorMessage;

    @Column(name = "execution_time_ms")
    private Long executionTimeMs;

    public AuditEntity() {
    }

    public AuditEntity(UUID auditId, UUID transactionId, String action,
                      String performedBy, LocalDateTime timestamp, String details) {
        this.auditId = auditId;
        this.transactionId = transactionId;
        this.action = action;
        this.performedBy = performedBy;
        this.timestamp = timestamp;
        this.details = details;
        this.success = true;
    }

    public static AuditEntity createFailure(UUID transactionId, String action,
                                           String performedBy, String errorMessage) {
        AuditEntity audit = new AuditEntity();
        audit.auditId = UUID.randomUUID();
        audit.transactionId = transactionId;
        audit.action = action;
        audit.performedBy = performedBy;
        audit.timestamp = LocalDateTime.now();
        audit.success = false;
        audit.errorMessage = errorMessage;
        return audit;
    }

    public UUID getAuditId() {
        return auditId;
    }

    public void setAuditId(UUID auditId) {
        this.auditId = auditId;
    }

    public UUID getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(UUID transactionId) {
        this.transactionId = transactionId;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getPerformedBy() {
        return performedBy;
    }

    public void setPerformedBy(String performedBy) {
        this.performedBy = performedBy;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public String getUserAgent() {
        return userAgent;
    }

    public void setUserAgent(String userAgent) {
        this.userAgent = userAgent;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public Long getExecutionTimeMs() {
        return executionTimeMs;
    }

    public void setExecutionTimeMs(Long executionTimeMs) {
        this.executionTimeMs = executionTimeMs;
    }
}