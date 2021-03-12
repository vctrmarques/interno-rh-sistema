--Railson Silva
--Removendo coluna referencia 
--Removendo coluna praca
--Adicionando coluna municipio id  

IF EXISTS 
(
    SELECT * FROM INFORMATION_SCHEMA.COLUMNS 
    WHERE table_name = 'agencia'
    AND column_name = 'referenia'
)
BEGIN
	ALTER TABLE agencia
	drop column referencia;
END	

IF EXISTS 
(
    SELECT * FROM INFORMATION_SCHEMA.COLUMNS 
    WHERE table_name = 'agencia'
    AND column_name = 'praca'
)
BEGIN
	EXEC sp_RENAME 'agencia.praca', 'municipio', 'COLUMN'
END


