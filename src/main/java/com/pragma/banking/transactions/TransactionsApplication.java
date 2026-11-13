package com.pragma.banking.transactions;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.retry.annotation.EnableRetry;
import org.springframework.scheduling.annotation.EnableAsync;
import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;
import io.github.resilience4j.retry.RetryConfig;
import io.github.resilience4j.retry.RetryRegistry;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.time.Duration;

@SpringBootApplication
@EnableConfigurationProperties
@EnableTransactionManagement
@EnableRetry
@EnableAsync
@ComponentScan(basePackages = {"com.pragma.banking.transactions"})
public class TransactionsApplication {
    
    public static void main(String[] args) {
        SpringApplication.run(TransactionsApplication.class, args);
    }
    
    @Bean
    public CircuitBreakerRegistry circuitBreakerRegistry() {
        CircuitBreakerConfig config = CircuitBreakerConfig.custom()
                .failureRateThreshold(50)
                .waitDurationInOpenState(Duration.ofMillis(1000))
                .permittedNumberOfCallsInHalfOpenState(3)
                .slidingWindowType(CircuitBreakerConfig.SlidingWindowType.COUNT_BASED)
                .slidingWindowSize(5)
                .recordExceptions(RuntimeException.class)
                .build();
        return CircuitBreakerRegistry.of(config);
    }
    
    @Bean
    public RetryRegistry retryRegistry() {
        RetryConfig config = RetryConfig.custom()
                .maxAttempts(3)
                .waitDuration(Duration.ofMillis(500))
                .retryExceptions(RuntimeException.class)
                .build();
        return RetryRegistry.of(config);
    }
    
    @Bean
    public CommandLineRunner startupRunner(TransactionService transactionService) {
        return args -> {
            try {
                // Verificación inicial de conectividad con la base de datos
                transactionService.verifyDatabaseConnectivity();
                System.out.println("Database connectivity verified successfully.");
                
                // Carga inicial de datos si es necesario
                transactionService.initializeSampleData();
                System.out.println("Sample data initialized.");
            } catch (Exception e) {
                System.err.println("Error during application startup: " + e.getMessage());
                throw e;
            }
        };
    }
}

interface TransactionService {
    void verifyDatabaseConnectivity();
    void initializeSampleData();
}