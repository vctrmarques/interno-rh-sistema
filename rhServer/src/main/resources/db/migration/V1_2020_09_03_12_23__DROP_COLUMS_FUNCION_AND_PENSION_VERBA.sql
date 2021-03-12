
-- Flávio Silva
-- A coluna parcelas_pagas será descontinuada do banco e será um transiente, pois essa informações será lida 
-- das tabelas de lancamentos.


IF COL_LENGTH('funcionario_verba', 'parcelas_pagas') IS NOT NULL
BEGIN
	ALTER TABLE funcionario_verba DROP COLUMN parcelas_pagas;
END

IF COL_LENGTH('pensionista_verba', 'parcelas_pagas') IS NOT NULL
BEGIN
	ALTER TABLE pensionista_verba DROP COLUMN parcelas_pagas;
END