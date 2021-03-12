-- Rodrigo Leite
-- Insere dados no menu
-- Relatório Aposentados e Pensões
IF NOT EXISTS(SELECT * FROM menu WHERE url = '/relatorioAposentadoPensionista/gestao')
	INSERT INTO menu VALUES(1, 'MODULO_PREVIDENCIARIO', 'Rel. Aposentados e pensões', '/relatorioAposentadoPensionista/gestao')
	
IF EXISTS(SELECT * FROM menu WHERE url = '/relatorioAposentadoPensionista/gestao')
BEGIN
DECLARE
    @menuId AS VARCHAR(1000)

	SELECT @menuId = CAST(id AS VARCHAR(1000)) FROM menu WHERE url = '/relatorioAposentadoPensionista/gestao'
	
	IF NOT EXISTS(SELECT * FROM papel WHERE nome = 'ROLE_RELATORIO_APOSENTADO_PENSAO_GESTAO')
		INSERT INTO papel (nome, id_menu) VALUES ('ROLE_RELATORIO_APOSENTADO_PENSAO_GESTAO', @menuId)

END