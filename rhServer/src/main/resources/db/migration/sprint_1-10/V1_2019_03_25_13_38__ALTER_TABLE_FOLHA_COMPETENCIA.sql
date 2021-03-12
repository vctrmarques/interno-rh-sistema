--Railson Silva
--Alterar tipo de mes_competencia para int

IF EXISTS 
(
    SELECT * FROM INFORMATION_SCHEMA.COLUMNS 
    WHERE table_name = 'folha_competencia'
    AND column_name = 'mes_competencia'
)
BEGIN
	EXEC sp_RENAME 'folha_competencia.mes_competencia' , 'mes_competencia_old', 'COLUMN';
	
END	