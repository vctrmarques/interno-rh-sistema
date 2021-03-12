--Railson Silva
--Alterar tipo de mes_competencia para int

IF EXISTS 
(
    SELECT * FROM INFORMATION_SCHEMA.COLUMNS 
    WHERE table_name = 'folha_competencia'
    AND column_name = 'mes_competencia_old'
)
BEGIN	
	ALTER TABLE folha_competencia
	add mes_competencia int;	
	
	ALTER TABLE folha_competencia
	add ano_competencia int;	
END	