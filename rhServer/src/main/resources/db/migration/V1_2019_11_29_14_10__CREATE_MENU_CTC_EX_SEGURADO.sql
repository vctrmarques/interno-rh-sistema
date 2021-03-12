-- Eduardo Costa

-- insert do menu CTC Ex-Segurado


IF NOT EXISTS(SELECT * FROM menu WHERE nome = 'Certidões de Tempo de Contribuição - Ex-Segurados')
	INSERT INTO menu VALUES(SYSDATETIME(), SYSDATETIME(), 1, 1, 1, 'MODULO_PREVIDENCIARIO', 'Certidões de Tempo de Contribuição - Ex-Segurados', '/certidaoTempoContribuicaoExSegurado/gestao');

