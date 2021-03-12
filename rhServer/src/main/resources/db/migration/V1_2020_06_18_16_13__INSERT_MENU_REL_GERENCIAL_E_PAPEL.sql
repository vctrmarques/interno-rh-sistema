-- Flávio Silva

-- insert do menu de Relatório Gerencial
INSERT INTO menu (nome, ativo, categoria, url) VALUES('Rel. Gerencial', 1, 'FOLHA_PAGAMENTO', '/relatorioGerencial/gestao');

-- Criação do papel de Gestor Relatório Gerencial
IF EXISTS(SELECT * FROM menu WHERE nome = 'Rel. Gerencial')
BEGIN
DECLARE
    @menuId AS VARCHAR(1000)

	SELECT @menuId = CAST(id AS VARCHAR(1000)) FROM menu WHERE nome = 'Rel. Gerencial'
	
	IF NOT EXISTS(SELECT * FROM papel WHERE nome = 'ROLE_RELATORIO_GERENCIAL_GESTAO')
		INSERT INTO papel (nome, id_menu) VALUES ('ROLE_RELATORIO_GERENCIAL_GESTAO', @menuId)
		
END

