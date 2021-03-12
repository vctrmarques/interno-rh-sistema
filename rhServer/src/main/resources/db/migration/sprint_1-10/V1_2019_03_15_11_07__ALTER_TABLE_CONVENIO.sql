--Railson Silva
--Removendo coluna codigo 

IF EXISTS 
(
    SELECT * FROM INFORMATION_SCHEMA.COLUMNS 
    WHERE table_name = 'convenio'
    AND column_name = 'codigo'
)
BEGIN
	ALTER TABLE convenio
	drop column codigo;
END	