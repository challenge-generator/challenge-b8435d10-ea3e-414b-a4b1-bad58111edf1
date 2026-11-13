package com.pragma.banking.transactions.infrastructure.adapter;

import com.pragma.banking.transactions.domain.model.Audit;
import com.pragma.banking.transactions.domain.port.AuditRepositoryPort;
import com.pragma.banking.transactions.infrastructure.entity.AuditEntity;
import com.pragma.banking.transactions.infrastructure.repository.AuditJpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
@Transactional
public class AuditJpaAdapter implements AuditRepositoryPort {

    private final AuditJpaRepository auditRepository;

    public AuditJpaAdapter(AuditJpaRepository auditRepository) {
        this.auditRepository = auditRepository;
    }

    @Override
    public Audit save(Audit audit) {
        AuditEntity entity = toEntity(audit);
        AuditEntity savedEntity = auditRepository.save(entity);
        return toDomain(savedEntity);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Audit> findById(UUID auditId) {
        return auditRepository.findById(auditId)
                .map(this::toDomain);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Audit> findAll() {
        return auditRepository.findAll().stream()
                .map(this::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<Audit> findByTransactionId(UUID transactionId) {
        return auditRepository.findByTransactionId(transactionId).stream()
                .map(this::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<Audit> findByAction(String action) {
        return auditRepository.findByAction(action).stream()
                .map(this::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<Audit> findByPerformedBy(String performedBy) {
        return auditRepository.findByPerformedBy(performedBy).stream()
                .map(this::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<Audit> findByDateRange(LocalDateTime startDate, LocalDateTime endDate) {
        return auditRepository.findByTimestampBetween(startDate, endDate).stream()
                .map(this::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public void delete(UUID auditId) {
        auditRepository.deleteById(auditId);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsById(UUID auditId) {
        return auditRepository.existsById(auditId);
    }

    @Override
    @Transactional(readOnly = true)
    public long countByTransactionId(UUID transactionId) {
        return auditRepository.countByTransactionId(transactionId);
    }

    private AuditEntity toEntity(Audit audit) {
        AuditEntity entity = new AuditEntity();
        if (audit.getAuditId() != null) {
            entity.setAuditId(audit.getAuditId());
        }
        entity.setTransactionId(audit.getTransactionId());
        entity.setAction(audit.getAction());
        entity.setPerformedBy(audit.getPerformedBy());
        entity.setTimestamp(audit.getTimestamp());
        entity.setDetails(audit.getDetails());
        return entity;
    }

    private Audit toDomain(AuditEntity entity) {
        return new Audit(
                entity.getAuditId(),
                entity.getTransactionId(),
                entity.getAction(),
                entity.getPerformedBy(),
                entity.getTimestamp(),
                entity.getDetails()
        );
    }
}