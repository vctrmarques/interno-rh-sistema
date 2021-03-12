
-- Marconi Motta

-- insert do menu Natureza da Função

IF NOT EXISTS(SELECT * FROM menu WHERE nome = 'Natureza da Função')
INSERT INTO menu VALUES(SYSDATETIME(), SYSDATETIME(), 1, 1, 1, 'GESTAO', 'Natureza da Função');