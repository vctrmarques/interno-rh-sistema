--Railson Silva
--Alterar tipo de mes_competencia para int

IF EXISTS 
(
    SELECT * FROM INFORMATION_SCHEMA.COLUMNS 
    WHERE table_name = 'folha_competencia'
    AND column_name = 'mes_competencia_old'
)
BEGIN	
	UPDATE folha_competencia
	SET mes_competencia = YEAR(mes_competencia_old);
	
	ALTER TABLE folha_competencia
	DROP COLUMN mes_competencia_old
	
END	