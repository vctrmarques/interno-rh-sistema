-- Anderson Galindro
-- 26/12/2019 

IF NOT EXISTS (SELECT * FROM menu WHERE URL = '/verbasPensionistas/gestao')	
INSERT INTO menu VALUES(SYSDATETIME(), SYSDATETIME(), 1, 1, 1, 'FOLHA_PAGAMENTO', 'Verbas do Pensionista', '/verbasPensionistas/gestao');