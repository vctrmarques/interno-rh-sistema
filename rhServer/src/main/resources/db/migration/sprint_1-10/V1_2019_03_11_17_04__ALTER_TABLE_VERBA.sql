-- Davi Queiroz

-- alteração da tabela de verba

BEGIN IF COL_LENGTH('verba', 'destinacao_externa') IS NOT NULL
    ALTER TABLE verba
    ALTER COLUMN destinacao_externa VARCHAR(255)
END