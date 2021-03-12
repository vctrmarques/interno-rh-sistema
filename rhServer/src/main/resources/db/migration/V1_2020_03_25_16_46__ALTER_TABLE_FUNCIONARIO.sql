-- Flávio Silva
-- Deletando uk_funcionario_nome da tabela funcionario, a coluna naturalidade (string) e adicionando naturalidade_id (objeto Municipio).

if OBJECT_ID('uk_funcionario_nome') is not null
BEGIN
	ALTER TABLE funcionario DROP CONSTRAINT uk_funcionario_nome; 
END

IF COL_LENGTH('funcionario', 'naturalidade') IS NOT NULL
BEGIN 
 	ALTER TABLE funcionario DROP COLUMN naturalidade;
END

IF COL_LENGTH('funcionario', 'naturalidade_id') IS NULL
BEGIN
	ALTER TABLE funcionario ADD naturalidade_id BIGINT;
	ALTER TABLE funcionario ADD CONSTRAINT fk_funcionario_naturalidade_id FOREIGN KEY (naturalidade_id) REFERENCES municipio (id);
END