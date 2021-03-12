--Railson Silva
--Removendo colunas codigo_motivo_retorno e codigo_saque

IF EXISTS 
(
    SELECT * FROM INFORMATION_SCHEMA.COLUMNS 
    WHERE table_name = 'afastamento'
    AND column_name = 'codigo_motivo_retorno'
)
BEGIN
	ALTER TABLE afastamento
	drop column codigo_motivo_retorno;
END	

IF EXISTS 
(
    SELECT * FROM INFORMATION_SCHEMA.COLUMNS 
    WHERE table_name = 'afastamento'
    AND column_name = 'codigo_saque'
)
BEGIN
	ALTER TABLE afastamento
	drop column codigo_saque;
END	


