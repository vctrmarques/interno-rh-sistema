--Alteração da tabela funcionario, atualizando colunas. 
--Davi Queiroz.

IF COL_LENGTH('funcionario', 'filial_id') IS NOT NULL
BEGIN
    ALTER TABLE funcionario
    ALTER COLUMN filial_id BIGINT;
END

IF COL_LENGTH('funcionario', 'sexo') IS NOT NULL
BEGIN
    ALTER TABLE funcionario
    ALTER COLUMN sexo CHAR;
END