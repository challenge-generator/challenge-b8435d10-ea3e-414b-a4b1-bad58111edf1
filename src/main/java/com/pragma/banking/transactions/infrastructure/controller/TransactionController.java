package com.pragma.banking.transactions.infrastructure.controller;



import com.pragma.banking.transactions.domain.model.TransactionStatus;
import com.pragma.banking.transactions.domain.model.TransactionType;
import com.pragma.banking.transactions.application.usecase.ProcessTransactionUseCase;
import com.pragma.banking.transactions.domain.model.Transaction;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/transactions")
public class TransactionController {

    private static final Logger logger = LoggerFactory.getLogger(TransactionController.class);

    private final ProcessTransactionUseCase processTransactionUseCase;

    public TransactionController(ProcessTransactionUseCase processTransactionUseCase) {
        this.processTransactionUseCase = processTransactionUseCase;
    }

    @PostMapping
    @CircuitBreaker(name = "transactionCircuitBreaker", fallbackMethod = "processTransactionFallback")
    @Retry(name = "transactionRetry")
    public ResponseEntity<TransactionResponse> processTransaction(
            @Valid @RequestBody TransactionRequest request,
            @RequestHeader(value = "X-User-Id", defaultValue = "system") String userId) {
        logger.info("Processing transaction request: type={}, amount={}, clientId={}",
                    request.type(), request.amount(), request.clientId());

        Transaction transaction = new Transaction(
            UUID.randomUUID(),
            request.clientId(),
            request.amount(),
            java.time.LocalDateTime.now(),
            Transaction.TransactionType.valueOf(request.type().toUpperCase()),
            request.description()
        );
        transaction.setStatus(Transaction.TransactionStatus.PENDING);

        processTransactionUseCase.execute(transaction, userId);

        TransactionResponse response = new TransactionResponse(
            transaction.getTransactionId().toString(),
            transaction.getStatus().name(),
            "Transaction processed successfully"
        );

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{transactionId}")
    public ResponseEntity<TransactionDetailsResponse> getTransaction(
            @PathVariable @NotNull UUID transactionId) {
        logger.info("Fetching transaction: {}", transactionId);

        Transaction transaction = processTransactionUseCase.findById(transactionId);

        if (transaction == null) {
            return ResponseEntity.notFound().build();
        }

        TransactionDetailsResponse response = new TransactionDetailsResponse(
            transaction.getTransactionId().toString(),
            transaction.getClientId().toString(),
            transaction.getAmount(),
            transaction.getTransactionDate().toString(),
            transaction.getType().name(),
            transaction.getDescription(),
            transaction.getStatus().name()
        );

        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<TransactionSummaryResponse>> getTransactions(
            @RequestParam(required = false) UUID clientId,
            @RequestParam(required = false) String status,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        logger.info("Fetching transactions: clientId={}, status={}, page={}, size={}",
                    clientId, status, page, size);

        List<Transaction> transactions = processTransactionUseCase.findByFilters(clientId, status, page, size);

        List<TransactionSummaryResponse> response = transactions.stream()
            .map(t -> new TransactionSummaryResponse(
                t.getTransactionId().toString(),
                t.getAmount(),
                t.getType().name(),
                t.getStatus().name(),
                t.getTransactionDate().toString()
            ))
            .toList();

        return ResponseEntity.ok(response);
    }

    @PostMapping("/{transactionId}/cancel")
    public ResponseEntity<Map<String, String>> cancelTransaction(
            @PathVariable @NotNull UUID transactionId,
            @RequestHeader(value = "X-User-Id", defaultValue = "system") String userId) {
        logger.info("Cancelling transaction: {}, user: {}", transactionId, userId);

        boolean cancelled = processTransactionUseCase.cancelTransaction(transactionId, userId);

        if (!cancelled) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(Map.of("error", "Transaction cannot be cancelled in its current state"));
        }

        return ResponseEntity.ok(Map.of(
            "transactionId", transactionId.toString(),
            "status", "CANCELLED"
        ));
    }

    @PostMapping("/{transactionId}/reverse")
    public ResponseEntity<Map<String, String>> reverseTransaction(
            @PathVariable @NotNull UUID transactionId,
            @RequestBody ReverseTransactionRequest request,
            @RequestHeader(value = "X-User-Id", defaultValue = "system") String userId) {
        logger.info("Reversing transaction: {}, reason: {}", transactionId, request.reason());

        boolean reversed = processTransactionUseCase.reverseTransaction(transactionId, request.reason(), userId);

        if (!reversed) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(Map.of("error", "Transaction cannot be reversed"));
        }

        return ResponseEntity.ok(Map.of(
            "transactionId", transactionId.toString(),
            "status", "REVERSED",
            "reversalReason", request.reason()
        ));
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Map<String, String>> handleIllegalArgument(IllegalArgumentException ex) {
        logger.warn("Invalid argument: {}", ex.getMessage());
        return ResponseEntity.badRequest()
            .body(Map.of("error", ex.getMessage()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, String>> handleGenericException(Exception ex) {
        logger.error("Unexpected error processing transaction", ex);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body(Map.of("error", "An unexpected error occurred"));
    }

    public ResponseEntity<TransactionResponse> processTransactionFallback(
            TransactionRequest request, Throwable t) {
        logger.error("Circuit breaker fallback triggered for transaction: {}", t.getMessage());
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
            .body(new TransactionResponse(
                null,
                "UNAVAILABLE",
                "Service temporarily unavailable. Please try again later."
            ));
    }

    public record TransactionRequest(
        @NotNull UUID clientId,
        @NotNull BigDecimal amount,
        @NotNull String type,
        String description
    ) {}

    public record TransactionResponse(String transactionId, String status, String message) {}

    public record TransactionDetailsResponse(
        String transactionId,
        String clientId,
        BigDecimal amount,
        String transactionDate,
        String type,
        String description,
        String status
    ) {}

    public record TransactionSummaryResponse(
        String transactionId,
        BigDecimal amount,
        String type,
        String status,
        String transactionDate
    ) {}

    public record ReverseTransactionRequest(String reason) {}
}