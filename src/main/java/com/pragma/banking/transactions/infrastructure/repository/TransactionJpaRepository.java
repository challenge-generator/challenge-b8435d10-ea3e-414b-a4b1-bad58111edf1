package com.pragma.banking.transactions.infrastructure.repository;

import com.pragma.banking.transactions.infrastructure.entity.TransactionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Repository
public interface TransactionJpaRepository extends JpaRepository<TransactionEntity, UUID> {

    List<TransactionEntity> findByClientId(UUID clientId);

    List<TransactionEntity> findByStatus(String status);

    @Query("SELECT t FROM TransactionEntity t WHERE t.clientId = :clientId AND t.transactionDate BETWEEN :startDate AND :endDate")
    List<TransactionEntity> findByClientIdAndTransactionDateBetween(
            @Param("clientId") UUID clientId,
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate);

    @Query("SELECT t FROM TransactionEntity t WHERE t.status = :status AND t.transactionDate < :threshold")
    List<TransactionEntity> findStaleTransactions(
            @Param("status") String status,
            @Param("threshold") LocalDateTime threshold);

    @Query("SELECT SUM(t.amount) FROM TransactionEntity t WHERE t.clientId = :clientId AND t.type = :type")
    BigDecimal sumAmountByClientIdAndType(
            @Param("clientId") UUID clientId,
            @Param("type") String type);

    @Query("SELECT COUNT(t) FROM TransactionEntity t WHERE t.transactionDate >= :since")
    long countTransactionsSince(@Param("since") LocalDateTime since);

    @Query("SELECT t FROM TransactionEntity t WHERE t.clientId = :clientId ORDER BY t.transactionDate DESC")
    List<TransactionEntity> findLatestByClientId(@Param("clientId") UUID clientId);

    @Query("SELECT t.type, COUNT(t) FROM TransactionEntity t WHERE t.transactionDate BETWEEN :start AND :end GROUP BY t.type")
    List<Object[]> countByTypeBetweenDates(
            @Param("start") LocalDateTime start,
            @Param("end") LocalDateTime end);
}