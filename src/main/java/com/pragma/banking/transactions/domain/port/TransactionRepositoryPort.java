package com.pragma.banking.transactions.domain.port;

import com.pragma.banking.transactions.domain.model.Transaction;
import com.pragma.banking.transactions.domain.model.Transaction.TransactionStatus;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Puerto que define el contrato para la persistencia de transacciones.
 * Esta interfaz pertenece al dominio y es implementada por la capa de infraestructura.
 * El dominio no conoce detalles de cómo se persiste la información.
 */
public interface TransactionRepositoryPort {

    /**
     * Guarda una nueva transacción en el repositorio.
     * @param transaction la transacción a guardar
     * @return la transacción guardada con su identificador asignado
     */
    Transaction save(Transaction transaction);

    /**
     * Busca una transacción por su identificador único.
     * @param transactionId el identificador de la transacción
     * @return un Optional que contiene la transacción si existe
     */
    Optional<Transaction> findById(UUID transactionId);

    /**
     * Recupera todas las transacciones asociadas a un cliente específico.
     * @param clientId el identificador del cliente
     * @return lista de transacciones del cliente
     */
    List<Transaction> findByClientId(UUID clientId);

    /**
     * Actualiza el estado de una transacción existente.
     * @param transactionId el identificador de la transacción
     * @param status el nuevo estado de la transacción
     * @return la transacción actualizada
     */
    Optional<Transaction> updateStatus(UUID transactionId, TransactionStatus status);

    /**
     * Busca transacciones por estado específico.
     * @param status el estado de las transacciones a buscar
     * @return lista de transacciones con el estado especificado
     */
    List<Transaction> findByStatus(TransactionStatus status);

    /**
     * Calcula el total de transacciones de un cliente dentro de un rango de monto.
     * @param clientId el identificador del cliente
     * @param minAmount monto mínimo del rango
     * @param maxAmount monto máximo del rango
     * @return suma total de los montos de las transacciones
     */
    BigDecimal sumAmountsByClientIdAndAmountBetween(UUID clientId, BigDecimal minAmount, BigDecimal maxAmount);

    /**
     * Verifica si existe una transacción con el mismo identificador externo.
     * @param externalId identificador externo de la transacción
     * @return true si existe una transacción con ese identificador
     */
    boolean existsByExternalId(String externalId);

    /**
     * Elimina una transacción por su identificador.
     * @param transactionId el identificador de la transacción a eliminar
     */
    void deleteById(UUID transactionId);

    /**
     * Cuenta el número total de transacciones en el sistema.
     * @return cantidad total de transacciones
     */
    long count();

    /**
     * Recupera las transacciones más recientes de un cliente.
     * @param clientId el identificador del cliente
     * @param limit número máximo de transacciones a retornar
     * @return lista de las transacciones más recientes
     */
    List<Transaction> findTopByClientIdOrderByTransactionDateDesc(UUID clientId, int limit);
}