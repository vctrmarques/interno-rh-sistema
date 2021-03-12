-- Adição das colunas codigo operacao e tipo operacao 
-- Rodrigo Leite

IF COL_LENGTH('empresa_filial', 'codigo_convenio') IS NULL
BEGIN
    ALTER TABLE empresa_filial ADD codigo_convenio INT NULL;
END

IF COL_LENGTH('empresa_filial', 'tipo_operacao') IS NULL
BEGIN
    ALTER TABLE empresa_filial ADD tipo_operacao VARCHAR(100) NULL;
END