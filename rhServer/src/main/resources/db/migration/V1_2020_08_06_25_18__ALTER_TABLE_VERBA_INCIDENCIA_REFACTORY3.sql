-- Flávio Silva
-- Refatoração da tabela de incidencia de verba.

-- Apagando a tabela antiga.
IF EXISTS(SELECT * FROM SYS.TABLES WHERE name = 'verba_incidencia') 
	DROP TABLE verba_incidencia
	
-- Renomeando a tabela nova pro nome correto
EXEC sp_rename 'verba_incidenciaNova', 'verba_incidencia';