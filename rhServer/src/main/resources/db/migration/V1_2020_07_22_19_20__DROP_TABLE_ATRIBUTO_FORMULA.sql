-- Flávio Silva
-- Tabela descontinuada.

IF EXISTS(SELECT * FROM SYS.TABLES WHERE name = 'atributo_formula') 
	DROP TABLE atributo_formula