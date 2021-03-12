--Railson Silva
--Drop coluna sindicato

IF EXISTS(SELECT *
          FROM   INFORMATION_SCHEMA.COLUMNS
          WHERE  TABLE_NAME = 'funcao'
                 AND COLUMN_NAME = 'sindicato') 
BEGIN
	ALTER TABLE funcao
	drop column sindicato
END


