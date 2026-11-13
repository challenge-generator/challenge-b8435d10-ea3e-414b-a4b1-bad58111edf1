package com.pragma.banking.transactions.infrastructure.adapter;



import com.pragma.banking.transactions.domain.model.TransactionType;
import com.pragma.banking.transactions.domain.model.TransactionStatus;
import com.pragma.banking.transactions.domain.model.Transaction;
import com.pragma.banking.transactions.domain.port.TransactionRepositoryPort;
import com.pragma.banking.transactions.infrastructure.entity.TransactionEntity;
import com.pragma.banking.transactions.infrastructure.repository.TransactionJpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
@Transactional
public class TransactionJpaAdapter implements TransactionRepositoryPort {

    private final TransactionJpaRepository transactionRepository;

    public TransactionJpaAdapter(TransactionJpaRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    @Override
    public Transaction save(Transaction transaction) {
        TransactionEntity entity = toEntity(transaction);
        TransactionEntity savedEntity = transactionRepository.save(entity);
        return toDomain(savedEntity);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Transaction> findById(UUID transactionId) {
        return transactionRepository.findById(transactionId)
                .map(this::toDomain);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Transaction> findAll() {
        return transactionRepository.findAll().stream()
                .map(this::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<Transaction> findByClientId(UUID clientId) {
        return transactionRepository.findByClientId(clientId).stream()
                .map(this::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<Transaction> findByStatus(Transaction.TransactionStatus status) {
        return transactionRepository.findByStatus(status.name()).stream()
                .map(this::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<Transaction> findByDateRange(LocalDateTime startDate, LocalDateTime endDate) {
        return transactionRepository.findByTransactionDateBetween(startDate, endDate).stream()
                .map(this::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public BigDecimal getTotalAmountByClientAndDateRange(UUID clientId, LocalDateTime startDate, LocalDateTime endDate) {
        BigDecimal total = transactionRepository.sumAmountByClientIdAndDateRange(clientId, startDate, endDate);
        return total != null ? total : BigDecimal.ZERO;
    }

    @Override
    public void delete(UUID transactionId) {
        transactionRepository.deleteById(transactionId);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsById(UUID transactionId) {
        return transactionRepository.existsById(transactionId);
    }

    private TransactionEntity toEntity(Transaction transaction) {
        TransactionEntity entity = new TransactionEntity();
        if (transaction.getTransactionId() != null) {
            entity.setTransactionId(transaction.getTransactionId());
        }
        entity.setClientId(transaction.getClientId());
        entity.setAmount(transaction.getAmount());
        entity.setTransactionDate(transaction.getTransactionDate());
        entity.setType(transaction.getType().name());
        entity.setDescription(transaction.getDescription());
        entity.setStatus(transaction.getStatus().name());
        return entity;
    }

    private Transaction toDomain(TransactionEntity entity) {
        return new Transaction(
                entity.getTransactionId(),
                entity.getClientId(),
                entity.getAmount(),
                entity.getTransactionDate(),
                Transaction.TransactionType.valueOf(entity.getType()),
                entity.getDescription(),
                Transaction.TransactionStatus.valueOf(entity.getStatus())
        );
    }
}