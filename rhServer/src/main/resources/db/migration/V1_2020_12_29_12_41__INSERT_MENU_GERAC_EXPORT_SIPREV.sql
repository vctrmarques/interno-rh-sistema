-- Flavio Silva
-- Insere dados no menu
-- Geração de Exportação SIPREV

IF NOT EXISTS(SELECT * FROM menu WHERE url = '/arquivoExportacaoSiprev/gestao')
	INSERT INTO menu VALUES(1, 'ARRECADACAO', 'Exportação SIPREV', '/arquivoExportacaoSiprev/gestao')
	
IF EXISTS(SELECT * FROM menu WHERE url = '/arquivoExportacaoSiprev/gestao')
BEGIN
DECLARE
    @menuId AS VARCHAR(1000)

	SELECT @menuId = CAST(id AS VARCHAR(1000)) FROM menu WHERE url = '/arquivoExportacaoSiprev/gestao'
	
	IF NOT EXISTS(SELECT * FROM papel WHERE nome = 'ROLE_ARQUIVO_EXPORTACAO_SIPREV_GESTAO')
		INSERT INTO papel (nome, id_menu) VALUES ('ROLE_ARQUIVO_EXPORTACAO_SIPREV_GESTAO', @menuId)

END