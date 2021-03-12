-- Insere dados no menu
IF NOT EXISTS(SELECT * FROM menu WHERE url = '/batimentoFolhaPagamento/relatorio')
	INSERT INTO menu VALUES(1, 'FOLHA_PAGAMENTO', 'Rel. batimento da folha', '/batimentoFolhaPagamento/relatorio');
