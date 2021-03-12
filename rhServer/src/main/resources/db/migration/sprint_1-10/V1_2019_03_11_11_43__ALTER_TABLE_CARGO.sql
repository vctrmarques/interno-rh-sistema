--Railson Silva
--Drop coluna faixa_salarial_id

IF EXISTS(SELECT *
          FROM   INFORMATION_SCHEMA.COLUMNS
          WHERE  TABLE_NAME = 'cargo'
                 AND COLUMN_NAME = 'faixa_salarial_id') 
BEGIN
	ALTER TABLE cargo
	DROP CONSTRAINT  fk_cargo_faixa_salrial_faixa_salarial_id;
	
	ALTER TABLE cargo
	DROP COLUMN faixa_salarial_id
END


