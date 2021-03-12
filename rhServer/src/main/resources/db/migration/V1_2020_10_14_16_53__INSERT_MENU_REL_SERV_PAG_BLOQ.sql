-- Flavio Silva
-- Insere dados no menu
-- SERVIDORES COM PAGAMENTO BLOQUEADO

IF NOT EXISTS(SELECT * FROM menu WHERE url = '/relatorioServidorPagBloqueado/gestao')
	INSERT INTO menu VALUES(1, 'FOLHA_PAGAMENTO', 'Rel. Pagamento Bloq.', '/relatorioServidorPagBloqueado/gestao')
	
IF EXISTS(SELECT * FROM menu WHERE url = '/relatorioServidorPagBloqueado/gestao')
BEGIN
DECLARE
    @menuId AS VARCHAR(1000)

	SELECT @menuId = CAST(id AS VARCHAR(1000)) FROM menu WHERE url = '/relatorioServidorPagBloqueado/gestao'
	
	IF NOT EXISTS(SELECT * FROM papel WHERE nome = 'ROLE_RELATORIO_SERV_PAG_BLOQUEADO_GESTAO')
		INSERT INTO papel (nome, id_menu) VALUES ('ROLE_RELATORIO_SERV_PAG_BLOQUEADO_GESTAO', @menuId)

END