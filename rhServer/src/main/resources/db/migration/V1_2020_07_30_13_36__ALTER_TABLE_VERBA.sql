-- Flávio Silva
-- Adição do atributo faixa aliquota.

IF COL_LENGTH('verba', 'faixa_aliquota') IS NULL
BEGIN
    ALTER TABLE verba ADD faixa_aliquota VARCHAR(255) NULL;
END