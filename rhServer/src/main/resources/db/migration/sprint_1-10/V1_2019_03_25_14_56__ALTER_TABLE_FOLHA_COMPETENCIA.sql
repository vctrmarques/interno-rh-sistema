--Railson Silva
--Alterar tipo de mes_competencia para int

IF EXISTS 
(
    SELECT * FROM INFORMATION_SCHEMA.COLUMNS 
    WHERE table_name = 'folha_competencia'
    AND column_name = 'status'
)
BEGIN	

	ALTER TABLE folha_competencia
	DROP COLUMN status
	
END	