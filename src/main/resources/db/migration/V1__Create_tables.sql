-- V1__Create_tables.sql
-- Creación de tablas para el sistema de transacciones bancarias
-- Cumplimiento del modelo ACID: Atomicidad, Consistencia, Aislamiento y Durabilidad

-- Tabla de clientes
CREATE TABLE IF NOT EXISTS clients (
    client_id UUID PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    identification_number VARCHAR(50) NOT NULL UNIQUE,
    email VARCHAR(255) NOT NULL UNIQUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX idx_clients_identification ON clients(identification_number);
CREATE INDEX idx_clients_email ON clients(email);

-- Tabla de transacciones
CREATE TABLE IF NOT EXISTS transactions (
    transaction_id UUID PRIMARY KEY,
    client_id UUID NOT NULL REFERENCES clients(client_id) ON DELETE RESTRICT,
    amount DECIMAL(19, 4) NOT NULL CHECK (amount > 0),
    transaction_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    type VARCHAR(20) NOT NULL CHECK (type IN ('DEPOSIT', 'WITHDRAWAL', 'TRANSFER', 'PAYMENT')),
    description VARCHAR(500),
    status VARCHAR(20) NOT NULL DEFAULT 'PENDING' CHECK (status IN ('PENDING', 'COMPLETED', 'FAILED', 'CANCELLED')),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    version INTEGER NOT NULL DEFAULT 0
);

CREATE INDEX idx_transactions_client ON transactions(client_id);
CREATE INDEX idx_transactions_date ON transactions(transaction_date);
CREATE INDEX idx_transactions_status ON transactions(status);
CREATE INDEX idx_transactions_client_date ON transactions(client_id, transaction_date);

-- Tabla de auditoría
CREATE TABLE IF NOT EXISTS audit_log (
    audit_id UUID PRIMARY KEY,
    transaction_id UUID NOT NULL REFERENCES transactions(transaction_id) ON DELETE CASCADE,
    action VARCHAR(50) NOT NULL,
    performed_by VARCHAR(255) NOT NULL,
    timestamp TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    details TEXT,
    previous_state TEXT,
    new_state TEXT
);

CREATE INDEX idx_audit_transaction ON audit_log(transaction_id);
CREATE INDEX idx_audit_timestamp ON audit_log(timestamp);
CREATE INDEX idx_audit_performed_by ON audit_log(performed_by);

-- Tabla de control de concurrencia para transacciones
CREATE TABLE IF NOT EXISTS transaction_lock (
    lock_id UUID PRIMARY KEY,
    resource_id UUID NOT NULL,
    resource_type VARCHAR(50) NOT NULL,
    lock_type VARCHAR(20) NOT NULL CHECK (lock_type IN ('SHARED', 'EXCLUSIVE')),
    acquired_by VARCHAR(255) NOT NULL,
    acquired_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    expires_at TIMESTAMP NOT NULL,
    released_at TIMESTAMP,
    UNIQUE(resource_id, resource_type, lock_type)
);

CREATE INDEX idx_lock_resource ON transaction_lock(resource_id, resource_type);
CREATE INDEX idx_lock_expires ON transaction_lock(expires_at);

-- Tabla de configuraciones de transacciones
CREATE TABLE IF NOT EXISTS transaction_config (
    config_id UUID PRIMARY KEY,
    config_key VARCHAR(100) NOT NULL UNIQUE,
    config_value TEXT NOT NULL,
    description VARCHAR(500),
    is_active BOOLEAN DEFAULT true,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX idx_config_key ON transaction_config(config_key);

-- Insertar configuraciones iniciales
INSERT INTO transaction_config (config_id, config_key, config_value, description, is_active)
VALUES 
    (gen_random_uuid(), 'max.transaction.amount', '1000000.00', 'Monto máximo por transacción', true),
    (gen_random_uuid(), 'min.transaction.amount', '0.01', 'Monto mínimo por transacción', true),
    (gen_random_uuid(), 'daily.transaction.limit', '5000000.00', 'Límite diario de transacciones', true),
    (gen_random_uuid(), 'transaction.timeout.seconds', '30', 'Timeout de transacción en segundos', true),
    (gen_random_uuid(), 'enable.circuit.breaker', 'true', 'Habilitar circuit breaker', true)
ON CONFLICT (config_key) DO NOTHING;

-- Función para actualizar timestamps automáticamente
CREATE OR REPLACE FUNCTION update_timestamp()
RETURNS TRIGGER AS $$
BEGIN
    NEW.updated_at = CURRENT_TIMESTAMP;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

-- Trigger para actualizar timestamp en clients
CREATE TRIGGER update_clients_timestamp
    BEFORE UPDATE ON clients
    FOR EACH ROW
    EXECUTE FUNCTION update_timestamp();

-- Trigger para actualizar timestamp en transactions
CREATE TRIGGER update_transactions_timestamp
    BEFORE UPDATE ON transactions
    FOR EACH ROW
    EXECUTE FUNCTION update_timestamp();

-- Trigger para actualizar timestamp en transaction_config
CREATE TRIGGER update_config_timestamp
    BEFORE UPDATE ON transaction_config
    FOR EACH ROW
    EXECUTE FUNCTION update_timestamp();

-- Función para validación de monto
CREATE OR REPLACE FUNCTION validate_transaction_amount(p_amount DECIMAL)
RETURNS BOOLEAN AS $$
DECLARE
    v_max_amount DECIMAL;
    v_min_amount DECIMAL;
BEGIN
    SELECT CAST(config_value AS DECIMAL) INTO v_max_amount
    FROM transaction_config
    WHERE config_key = 'max.transaction.amount' AND is_active = true;

    SELECT CAST(config_value AS DECIMAL) INTO v_min_amount
    FROM transaction_config
    WHERE config_key = 'min.transaction.amount' AND is_active = true;

    RETURN p_amount >= v_min_amount AND p_amount <= v_max_amount;
END;
$$ LANGUAGE plpgsql;

-- Función para verificar límite diario
CREATE OR REPLACE FUNCTION check_daily_limit(p_client_id UUID, p_amount DECIMAL)
RETURNS BOOLEAN AS $$
DECLARE
    v_daily_limit DECIMAL;
    v_total_today DECIMAL;
BEGIN
    SELECT CAST(config_value AS DECIMAL) INTO v_daily_limit
    FROM transaction_config
    WHERE config_key = 'daily.transaction.limit' AND is_active = true;

    SELECT COALESCE(SUM(amount), 0) INTO v_total_today
    FROM transactions
    WHERE client_id = p_client_id
      AND DATE(transaction_date) = CURRENT_DATE
      AND status = 'COMPLETED';

    RETURN (v_total_today + p_amount) <= v_daily_limit;
END;
$$ LANGUAGE plpgsql;