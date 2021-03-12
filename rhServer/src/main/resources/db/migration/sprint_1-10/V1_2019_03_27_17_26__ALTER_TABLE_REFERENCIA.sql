DROP INDEX referencia_salarial.uk_nivel_salarial_codigo;

ALTER TABLE referencia_salarial ALTER COLUMN codigo VARCHAR(255) NOT NULL;

CREATE INDEX uk_nivel_salarial_codigo ON referencia_salarial (codigo);