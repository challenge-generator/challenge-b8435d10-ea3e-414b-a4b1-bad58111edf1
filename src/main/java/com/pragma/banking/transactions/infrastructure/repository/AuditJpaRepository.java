package com.pragma.banking.transactions.infrastructure.repository;

import com.pragma.banking.transactions.infrastructure.entity.AuditEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Repository
public interface AuditJpaRepository extends JpaRepository<AuditEntity, UUID> {

    List<AuditEntity> findByTransactionId(UUID transactionId);

    List<AuditEntity> findByPerformedBy(String performedBy);

    @Query("SELECT a FROM AuditEntity a WHERE a.timestamp BETWEEN :startDate AND :endDate ORDER BY a.timestamp DESC")
    List<AuditEntity> findByTimestampBetween(
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate);

    @Query("SELECT a FROM AuditEntity a WHERE a.action = :action AND a.timestamp >= :since")
    List<AuditEntity> findRecentByAction(
            @Param("action") String action,
            @Param("since") LocalDateTime since);

    @Query("SELECT COUNT(a) FROM AuditEntity a WHERE a.transactionId = :transactionId")
    long countByTransactionId(@Param("transactionId") UUID transactionId);

    @Query("SELECT a.performedBy, COUNT(a) FROM AuditEntity a WHERE a.timestamp >= :since GROUP BY a.performedBy")
    List<Object[]> countActionsByUserSince(@Param("since") LocalDateTime since);
}