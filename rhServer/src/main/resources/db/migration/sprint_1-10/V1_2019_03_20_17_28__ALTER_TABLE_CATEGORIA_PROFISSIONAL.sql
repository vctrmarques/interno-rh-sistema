--Railson Silva
--Removendo coluna ativo 

IF EXISTS 
(
    SELECT * FROM INFORMATION_SCHEMA.COLUMNS 
    WHERE table_name = 'categoria_profissional'
    AND column_name = 'ativo'
)
BEGIN
	ALTER TABLE categoria_profissional
	drop column ativo;
END	