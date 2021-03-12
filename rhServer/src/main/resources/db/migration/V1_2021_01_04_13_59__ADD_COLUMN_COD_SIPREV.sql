-- Flávio Silva
-- Adição da coluna codigo_siprev e codigo_ibge em várias tabelas abaixo:

IF COL_LENGTH('nacionalidade', 'codigo_siprev') IS NULL
    BEGIN
        ALTER TABLE nacionalidade ADD codigo_siprev BIGINT NULL;
    END
    
IF COL_LENGTH('municipio', 'codigo_ibge') IS NULL
    BEGIN
        ALTER TABLE municipio ADD codigo_ibge BIGINT NULL;
    END



