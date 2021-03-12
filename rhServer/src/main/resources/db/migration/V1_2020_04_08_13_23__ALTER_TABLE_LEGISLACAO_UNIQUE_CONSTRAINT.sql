-- Flávio Silva
-- Deletando uk_legislacao_numero_norma da tabela legislacao


IF EXISTS (SELECT * FROM sys.indexes WHERE name = 'uk_legislacao_numero_norma')
BEGIN
  DROP INDEX legislacao.uk_legislacao_numero_norma;
END

IF NOT EXISTS (SELECT * FROM sys.indexes WHERE name = 'uk_legislacao')
BEGIN
  CREATE UNIQUE INDEX uk_legislacao ON legislacao (numero_norma, ente_federado_id, norma_id);
END