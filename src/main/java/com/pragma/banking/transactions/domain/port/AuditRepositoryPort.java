package com.pragma.banking.transactions.domain.port;

import com.pragma.banking.transactions.domain.model.Audit;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Puerto que define el contrato para la persistencia de registros de auditoría.
 * Esta interfaz pertenece al dominio y es implementada por la capa de infraestructura.
 * Permite al dominio mantener un registro inmutable de todas las operaciones realizadas.
 */
public interface AuditRepositoryPort {

    /**
     * Guarda un nuevo registro de auditoría en el repositorio.
     * Los registros de auditoría son inmutables y nunca se modifican.
     * @param audit el registro de auditoría a guardar
     * @return el registro de auditoría guardado con su identificador asignado
     */
    Audit save(Audit audit);

    /**
     * Busca un registro de auditoría por su identificador único.
     * @param auditId el identificador del registro de auditoría
     * @return un Optional que contiene el registro si existe
     */
    Optional<Audit> findById(UUID auditId);

    /**
     * Recupera todos los registros de auditoría asociados a una transacción específica.
     * @param transactionId el identificador de la transacción
     * @return lista de registros de auditoría para la transacción
     */
    List<Audit> findByTransactionId(UUID transactionId);

    /**
     * Busca registros de auditoría por acción específica.
     * @param action la acción registrada (CREATED, UPDATED, DELETED, etc.)
     * @return lista de registros de auditoría con la acción especificada
     */
    List<Audit> findByAction(String action);

    /**
     * Recupera registros de auditoría realizados por un usuario específico.
     * @param performedBy el identificador del usuario que realizó la acción
     * @return lista de registros de auditoría del usuario
     */
    List<Audit> findByPerformedBy(String performedBy);

    /**
     * Busca registros de auditoría dentro de un rango de fechas.
     * @param startDate fecha de inicio del rango
     * @param endDate fecha de fin del rango
     * @return lista de registros de auditoría en el rango de fechas
     */
    List<Audit> findByTimestampBetween(LocalDateTime startDate, LocalDateTime endDate);

    /**
     * Recupera los registros de auditoría más recientes.
     * @param limit número máximo de registros a retornar
     * @return lista de los registros más recientes
     */
    List<Audit> findTopByOrderByTimestampDesc(int limit);

    /**
     * Cuenta el número de registros de auditoría para una transacción.
     * @param transactionId el identificador de la transacción
     * @return cantidad de registros de auditoría asociados
     */
    long countByTransactionId(UUID transactionId);

    /**
     * Elimina registros de auditoría antiguos basándose en una fecha de corte.
     * Esta operación es útil para políticas de retención de datos.
     * @param cutoffDate fecha de corte para eliminar registros
     * @return número de registros eliminados
     */
    long deleteByTimestampBefore(LocalDateTime cutoffDate);
}