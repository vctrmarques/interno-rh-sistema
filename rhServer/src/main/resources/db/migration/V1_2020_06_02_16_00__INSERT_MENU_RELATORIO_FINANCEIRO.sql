-- Marconi Motta

-- insert do menu Relatório Financeiro
IF NOT EXISTS(SELECT * FROM menu WHERE nome = 'Rel. Financeiro')
	INSERT INTO menu (nome, ativo, categoria, url) VALUES('Rel. Financeiro', 1, 'FOLHA_PAGAMENTO', '/relatorio/financeiro/gestao');