-- Flávio Silva
-- Deletando uk_funcionario_nome da tabela funcionario. RETIFICAÇÃO


IF EXISTS (SELECT * FROM sys.indexes WHERE name = 'uk_funcionario_nome')
BEGIN
  DROP INDEX funcionario.uk_funcionario_nome;
END