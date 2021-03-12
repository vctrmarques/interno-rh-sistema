
-- Marconi Motta

-- insert do menu Verbas

IF NOT EXISTS(SELECT * FROM menu WHERE nome = 'Verbas')
INSERT INTO menu VALUES(SYSDATETIME(), SYSDATETIME(), 1, 1, 1, 'GESTAO', 'Verbas');