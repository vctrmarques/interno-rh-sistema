-- Flávio Silva
-- Deletando uk_verba_descricao_verba da tabela verba


IF EXISTS (SELECT * FROM sys.indexes WHERE name = 'uk_verba_descricao_verba')
BEGIN
  DROP INDEX verba.uk_verba_descricao_verba;
END