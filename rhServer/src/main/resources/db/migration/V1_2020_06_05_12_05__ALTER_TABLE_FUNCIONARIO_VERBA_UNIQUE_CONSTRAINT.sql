-- Flávio Silva
-- Criando uk_funcionario_verba para fazer uma unique constraint dos atributos verba e funcionário.

IF NOT EXISTS (SELECT * FROM sys.indexes WHERE name = 'uk_funcionario_verba')
BEGIN
  CREATE UNIQUE INDEX uk_funcionario_verba ON funcionario_verba (funcionario_id, verba_id);
END