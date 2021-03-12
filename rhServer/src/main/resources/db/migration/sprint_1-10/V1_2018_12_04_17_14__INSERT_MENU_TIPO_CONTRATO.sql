
-- Marconi Motta

-- insert do menu Tipo de Contrato

IF NOT EXISTS(SELECT * FROM menu WHERE nome = 'tipo_contrato')
INSERT INTO menu VALUES(SYSDATETIME(), SYSDATETIME(), 1, 1, 1, 'GESTAO', 'Tipos de Contrato');