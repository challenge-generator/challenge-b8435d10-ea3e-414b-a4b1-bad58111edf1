-- V2__Add_procedures.sql
-- Procedimientos almacenados para optimización de operaciones de transacciones
-- Optimización de rendimiento y manejo de concurrencia

-- Procedimiento para crear una transacción con bloqueo pesimista
CREATE OR REPLACE PROCEDURE create_transaction_with_lock(
    IN p_transaction_id UUID,
    IN p_client_id UUID,
    IN p_amount DECIMAL,
    IN p_type VARCHAR,
    IN p_description VARCHAR,
    OUT p_result BOOLEAN,
    OUT p_error_message VARCHAR
)
LANGUAGE plpgsql
AS $$
DECLARE
    v_lock_id UUID;
    v_current_balance DECIMAL;
BEGIN
    p_result := false;
    p_error_message := NULL;

    -- Adquirir bloqueo exclusivo en el cliente
    BEGIN
        INSERT INTO transaction_lock (lock_id, resource_id, resource_type, lock_type, acquired_by, expires_at)
        VALUES (gen_random_uuid(), p_client_id, 'CLIENT', 'EXCLUSIVE', 'transaction_service', CURRENT_TIMESTAMP + INTERVAL '30 seconds')
        ON CONFLICT (resource_id, resource_type, lock_type) DO NOTHING
        RETURNING lock_id INTO v_lock_id;

        IF v_lock_id IS NULL THEN
            p_error_message := 'No se pudo adquirir bloqueo para el cliente';
            RETURN;
        END IF;

        -- Verificar límite diario
        IF NOT check_daily_limit(p_client_id, p_amount) THEN
            p_error_message := 'Límite diario excedido';
            RETURN;
        END IF;

        -- Validar monto
        IF NOT validate_transaction_amount(p_amount) THEN
            p_error_message := 'Monto de transacción inválido';
            RETURN;
        END IF;

        -- Crear la transacción
        INSERT INTO transactions (transaction_id, client_id, amount, type, description, status)
        VALUES (p_transaction_id, p_client_id, p_amount, p_type, p_description, 'PENDING');

        -- Registrar en auditoría
        INSERT INTO audit_log (audit_id, transaction_id, action, performed_by, details, new_state)
        VALUES (gen_random_uuid(), p_transaction_id, 'TRANSACTION_CREATED', 'system', 
                'Transacción creada con bloqueo', '{"status": "PENDING"}');

        p_result := true;

    EXCEPTION
        WHEN OTHERS THEN
            p_error_message := SQLERRM;
            p_result := false;
    END;

    -- Liberar bloqueo
    IF v_lock_id IS NOT NULL THEN
        UPDATE transaction_lock
        SET released_at = CURRENT_TIMESTAMP
        WHERE lock_id = v_lock_id;
    END IF;
END;
$$;

-- Procedimiento para completar una transacción
CREATE OR REPLACE PROCEDURE complete_transaction(
    IN p_transaction_id UUID,
    IN p_performed_by VARCHAR,
    OUT p_result BOOLEAN,
    OUT p_error_message VARCHAR
)
LANGUAGE plpgsql
AS $$
DECLARE
    v_current_status VARCHAR;
    v_client_id UUID;
    v_amount DECIMAL;
BEGIN
    p_result := false;
    p_error_message := NULL;

    -- Obtener información de la transacción
    SELECT t.status, t.client_id, t.amount
    INTO v_current_status, v_client_id, v_amount
    FROM transactions t
    WHERE t.transaction_id = p_transaction_id
    FOR UPDATE;

    IF v_current_status IS NULL THEN
        p_error_message := 'Transacción no encontrada';
        RETURN;
    END IF;

    IF v_current_status != 'PENDING' THEN
        p_error_message := 'La transacción no está en estado PENDING';
        RETURN;
    END IF;

    -- Actualizar estado a COMPLETED
    UPDATE transactions
    SET status = 'COMPLETED', version = version + 1
    WHERE transaction_id = p_transaction_id AND version = (SELECT version FROM transactions WHERE transaction_id = p_transaction_id);

    IF NOT FOUND THEN
        p_error_message := 'Conflicto de versión - transacción modificada por otro proceso';
        RETURN;
    END IF;

    -- Registrar en auditoría
    INSERT INTO audit_log (audit_id, transaction_id, action, performed_by, details, previous_state, new_state)
    VALUES (gen_random_uuid(), p_transaction_id, 'TRANSACTION_COMPLETED', p_performed_by,
            'Transacción completada successfully',
            '{"status": "PENDING"}',
            '{"status": "COMPLETED"}');

    p_result := true;

EXCEPTION
    WHEN OTHERS THEN
        p_error_message := SQLERRM;
        p_result := false;
END;
$$;

-- Procedimiento para revertir una transacción
CREATE OR REPLACE PROCEDURE rollback_transaction(
    IN p_transaction_id UUID,
    IN p_reason VARCHAR,
    IN p_performed_by VARCHAR,
    OUT p_result BOOLEAN,
    OUT p_error_message VARCHAR
)
LANGUAGE plpgsql
AS $$
DECLARE
    v_current_status VARCHAR;
    v_previous_state TEXT;
BEGIN
    p_result := false;
    p_error_message := NULL;

    -- Obtener estado actual
    SELECT status INTO v_current_status
    FROM transactions
    WHERE transaction_id = p_transaction_id
    FOR UPDATE;

    IF v_current_status IS NULL THEN
        p_error_message := 'Transacción no encontrada';
        RETURN;
    END IF;

    IF v_current_status IN ('COMPLETED', 'CANCELLED') THEN
        p_error_message := 'No se puede revertir una transacción ' || v_current_status;
        RETURN;
    END IF;

    v_previous_state := '{"status": "' || v_current_status || '"}';

    -- Marcar como cancelada
    UPDATE transactions
    SET status = 'CANCELLED', version = version + 1
    WHERE transaction_id = p_transaction_id;

    -- Registrar en auditoría
    INSERT INTO audit_log (audit_id, transaction_id, action, performed_by, details, previous_state, new_state)
    VALUES (gen_random_uuid(), p_transaction_id, 'TRANSACTION_ROLLBACK', p_performed_by,
            'Reversión: ' || p_reason,
            v_previous_state,
            '{"status": "CANCELLED"}');

    p_result := true;

EXCEPTION
    WHEN OTHERS THEN
        p_error_message := SQLERRM;
        p_result := false;
END;
$$;

-- Procedimiento para obtener resumen de transacciones por cliente
CREATE OR REPLACE PROCEDURE get_client_transaction_summary(
    IN p_client_id UUID,
    IN p_start_date DATE,
    IN p_end_date DATE,
    OUT p_total_transactions BIGINT,
    OUT p_total_amount DECIMAL,
    OUT p_completed_count BIGINT,
    OUT p_failed_count BIGINT,
    OUT p_pending_count BIGINT
)
LANGUAGE plpgsql
AS $$
BEGIN
    SELECT COUNT(*), COALESCE(SUM(amount), 0), 0, 0, 0
    INTO p_total_transactions, p_total_amount, p_completed_count, p_failed_count, p_pending_count
    FROM transactions
    WHERE client_id = p_client_id
      AND DATE(transaction_date) BETWEEN p_start_date AND p_end_date;

    SELECT COUNT(*) INTO p_completed_count
    FROM transactions
    WHERE client_id = p_client_id
      AND DATE(transaction_date) BETWEEN p_start_date AND p_end_date
      AND status = 'COMPLETED';

    SELECT COUNT(*) INTO p_failed_count
    FROM transactions
    WHERE client_id = p_client_id
      AND DATE(transaction_date) BETWEEN p_start_date AND p_end_date
      AND status = 'FAILED';

    SELECT COUNT(*) INTO p_pending_count
    FROM transactions
    WHERE client_id = p_client_id
      AND DATE(transaction_date) BETWEEN p_start_date AND p_end_date
      AND status = 'PENDING';
END;
$$;

-- Función para limpiar bloqueos expirados
CREATE OR REPLACE FUNCTION cleanup_expired_locks()
RETURNS INTEGER
LANGUAGE plpgsql
AS $$
DECLARE
    v_deleted INTEGER;
BEGIN
    UPDATE transaction_lock
    SET released_at = CURRENT_TIMESTAMP
    WHERE expires_at < CURRENT_TIMESTAMP AND released_at IS NULL
    RETURNING COUNT(*) INTO v_deleted;

    RETURN v_deleted;
END;
$$;

-- Función para obtener historial de auditoría de una transacción
CREATE OR REPLACE FUNCTION get_transaction_audit_history(p_transaction_id UUID)
RETURNS TABLE (
    audit_id UUID,
    action VARCHAR,
    performed_by VARCHAR,
    timestamp TIMESTAMP,
    details TEXT,
    previous_state TEXT,
    new_state TEXT
)
LANGUAGE plpgsql
AS $$
BEGIN
    RETURN QUERY
    SELECT a.audit_id, a.action, a.performed_by, a.timestamp, a.details, a.previous_state, a.new_state
    FROM audit_log a
    WHERE a.transaction_id = p_transaction_id
    ORDER BY a.timestamp ASC;
END;
$$;

-- Procedimiento para ejecutar transferencia entre cuentas
CREATE OR REPLACE PROCEDURE execute_transfer(
    IN p_transaction_id UUID,
    IN p_source_client_id UUID,
    IN p_destination_client_id UUID,
    IN p_amount DECIMAL,
    IN p_description VARCHAR,
    IN p_performed_by VARCHAR,
    OUT p_result BOOLEAN,
    OUT p_error_message VARCHAR
)
LANGUAGE plpgsql
AS $$
DECLARE
    v_source_lock UUID;
    v_dest_lock UUID;
BEGIN
    p_result := false;
    p_error_message := NULL;

    -- Adquirir bloqueos en orden para evitar deadlocks (cliente con ID menor primero)
    IF p_source_client_id < p_destination_client_id THEN
        INSERT INTO transaction_lock (lock_id, resource_id, resource_type, lock_type, acquired_by, expires_at)
        VALUES (gen_random_uuid(), p_source_client_id, 'CLIENT', 'EXCLUSIVE', 'transfer_service', CURRENT_TIMESTAMP + INTERVAL '60 seconds')
        ON CONFLICT DO NOTHING RETURNING lock_id INTO v_source_lock;

        INSERT INTO transaction_lock (lock_id, resource_id, resource_type, lock_type, acquired_by, expires_at)
        VALUES (gen_random_uuid(), p_destination_client_id, 'CLIENT', 'EXCLUSIVE', 'transfer_service', CURRENT_TIMESTAMP + INTERVAL '60 seconds')
        ON CONFLICT DO NOTHING RETURNING lock_id INTO v_dest_lock;
    ELSE
        INSERT INTO transaction_lock (lock_id, resource_id, resource_type, lock_type, acquired_by, expires_at)
        VALUES (gen_random_uuid(), p_destination_client_id, 'CLIENT', 'EXCLUSIVE', 'transfer_service', CURRENT_TIMESTAMP + INTERVAL '60 seconds')
        ON CONFLICT DO NOTHING RETURNING lock_id INTO v_dest_lock;

        INSERT INTO transaction_lock (lock_id, resource_id, resource_type, lock_type, acquired_by, expires_at)
        VALUES (gen_random_uuid(), p_source_client_id, 'CLIENT', 'EXCLUSIVE', 'transfer_service', CURRENT_TIMESTAMP + INTERVAL '60 seconds')
        ON CONFLICT DO NOTHING RETURNING lock_id INTO v_source_lock;
    END IF;

    IF v_source_lock IS NULL OR v_dest_lock IS NULL THEN
        p_error_message := 'No se pudieron adquirir los bloqueos necesarios';
        RETURN;
    END IF;

    -- Verificar límites para origen
    IF NOT check_daily_limit(p_source_client_id, p_amount) THEN
        p_error_message := 'Límite diario excedido para cuenta origen';
        RETURN;
    END IF;

    -- Validar monto
    IF NOT validate_transaction_amount(p_amount) THEN
        p_error_message := 'Monto de transferencia inválido';
        RETURN;
    END IF;

    -- Crear transacción de débito
    INSERT INTO transactions (transaction_id, client_id, amount, type, description, status)
    VALUES (p_transaction_id, p_source_client_id, p_amount, 'TRANSFER', p_description || ' (Débito)', 'COMPLETED');

    -- Crear transacción de crédito
    INSERT INTO transactions (transaction_id, client_id, amount, type, description, status)
    VALUES (gen_random_uuid(), p_destination_client_id, p_amount, 'TRANSFER', p_description || ' (Crédito)', 'COMPLETED');

    -- Registrar en auditoría
    INSERT INTO audit_log (audit_id, transaction_id, action, performed_by, details, new_state)
    VALUES (gen_random_uuid(), p_transaction_id, 'TRANSFER_EXECUTED', p_performed_by,
            'Transferencia de ' || p_amount || ' de ' || p_source_client_id || ' a ' || p_destination_client_id,
            '{"status": "COMPLETED"}');

    p_result := true;

EXCEPTION
    WHEN OTHERS THEN
        p_error_message := SQLERRM;
        p_result := false;
END;
$$;

-- Índices adicionales para optimizar consultas de procedimientos
CREATE INDEX IF NOT EXISTS idx_transactions_client_date_status 
ON transactions(client_id, transaction_date, status);

CREATE INDEX IF NOT EXISTS idx_audit_log_composite 
ON audit_log(transaction_id, timestamp, action);

-- Agregar columna para tracking de retries
ALTER TABLE transactions ADD COLUMN IF NOT EXISTS retry_count INTEGER DEFAULT 0;
ALTER TABLE transactions ADD COLUMN IF NOT EXISTS last_retry_at TIMESTAMP;