DELETE FROM menu WHERE url = '/certidaoTempoContribuicaoExSegurado/gestao'
DELETE FROM menu WHERE url = '/certidaoTempoContribuicaoExServidor/gestao'

INSERT INTO menu VALUES(SYSDATETIME(), SYSDATETIME(), 1, 1, 1, 'MODULO_PREVIDENCIARIO', 'CTC - Ex-Servidor', '/certidaoTempoContribuicaoExServidor/gestao');