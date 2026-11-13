package com.pragma.banking.transactions.infrastructure.config;

import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;
import io.github.resilience4j.retry.RetryConfig;
import io.github.resilience4j.retry.RetryRegistry;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.support.TransactionTemplate;

import javax.sql.DataSource;
import java.time.Duration;
import java.util.function.Supplier;

@Configuration
@EnableTransactionManagement
public class TransactionConfig {

    @Bean
    public PlatformTransactionManager transactionManager(DataSource dataSource) {
        DataSourceTransactionManager transactionManager = new DataSourceTransactionManager(dataSource);
        transactionManager.setNestedTransactionAllowed(true);
        return transactionManager;
    }

    @Bean
    public TransactionTemplate transactionTemplate(PlatformTransactionManager transactionManager) {
        TransactionTemplate template = new TransactionTemplate(transactionManager);
        template.setIsolationLevel(TransactionTemplate.ISOLATION_READ_COMMITTED);
        template.setPropagationBehavior(TransactionTemplate.PROPAGATION_REQUIRED);
        template.setTimeout(30);
        return template;
    }

    @Bean
    public CircuitBreakerRegistry circuitBreakerRegistry() {
        CircuitBreakerConfig config = CircuitBreakerConfig.custom()
                .failureRateThreshold(50)
                .waitDurationInOpenState(Duration.ofSeconds(60))
                .slidingWindowSize(10)
                .minimumNumberOfCalls(5)
                .permittedNumberOfCallsInHalfOpenState(3)
                .automaticTransitionFromOpenToHalfOpenEnabled(true)
                .waitDurationInOpenState(Duration.ofSeconds(60))
                .slidingWindowType(CircuitBreakerConfig.SlidingWindowType.COUNT_BASED)
                .recordExceptions(
                        java.io.IOException.class,
                        java.sql.SQLException.class,
                        org.springframework.dao.DataAccessException.class
                )
                .build();

        return CircuitBreakerRegistry.of(config);
    }

    @Bean
    public CircuitBreakerRegistry resilientCircuitBreakerRegistry() {
        CircuitBreakerConfig config = CircuitBreakerConfig.custom()
                .failureRateThreshold(30)
                .waitDurationInOpenState(Duration.ofSeconds(120))
                .slidingWindowSize(20)
                .minimumNumberOfCalls(10)
                .permittedNumberOfCallsInHalfOpenState(5)
                .automaticTransitionFromOpenToHalfOpenEnabled(true)
                .slowCallRateThreshold(50)
                .slowCallDurationThreshold(Duration.ofSeconds(5))
                .recordExceptions(
                        java.io.IOException.class,
                        java.sql.SQLException.class,
                        org.springframework.dao.DataAccessException.class,
                        java.util.concurrent.TimeoutException.class
                )
                .build();

        return CircuitBreakerRegistry.of(config);
    }

    @Bean
    public RetryRegistry retryRegistry() {
        RetryConfig config = RetryConfig.custom()
                .maxAttempts(3)
                .waitDuration(Duration.ofMillis(500))
                .retryExceptions(
                        java.io.IOException.class,
                        java.sql.SQLException.class,
                        org.springframework.dao.DataAccessException.class
                )
                .ignoreExceptions(
                        org.springframework.dao.DataIntegrityViolationException.class
                )
                .build();

        return RetryRegistry.of(config);
    }

    @Bean
    public RetryRegistry resilientRetryRegistry() {
        RetryConfig config = RetryConfig.custom()
                .maxAttempts(5)
                .waitDuration(Duration.ofSeconds(1))
                .retryExceptions(
                        java.io.IOException.class,
                        java.sql.SQLException.class,
                        org.springframework.dao.DataAccessException.class,
                        java.util.concurrent.TimeoutException.class
                )
                .ignoreExceptions(
                        org.springframework.dao.DataIntegrityViolationException.class
                )
                .build();

        return RetryRegistry.of(config);
    }

    @Bean
    public JdbcTemplate jdbcTemplate(DataSource dataSource) {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        jdbcTemplate.setQueryTimeout(30);
        return jdbcTemplate;
    }

    @Bean
    public Supplier<CircuitBreakerRegistry> circuitBreakerSupplier() {
        return this::circuitBreakerRegistry;
    }

    @Bean
    public Supplier<RetryRegistry> retrySupplier() {
        return this::retryRegistry;
    }
}