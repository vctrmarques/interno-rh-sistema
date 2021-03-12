--Railson Silva
--Drop coluna faixa_salarial_id e classe_salarial_id

IF EXISTS(SELECT *
          FROM   INFORMATION_SCHEMA.COLUMNS
          WHERE  TABLE_NAME = 'funcao'
                 AND COLUMN_NAME = 'faixa_salarial_id') 
BEGIN
	ALTER TABLE funcao
	DROP CONSTRAINT  fk_funcao_faixa_salarial_faixa_salarial_id;
	
	ALTER TABLE funcao
	DROP COLUMN faixa_salarial_id
END

IF EXISTS(SELECT *
          FROM   INFORMATION_SCHEMA.COLUMNS
          WHERE  TABLE_NAME = 'funcao'
                 AND COLUMN_NAME = 'classe_salarial_id') 
BEGIN
	ALTER TABLE funcao
	DROP CONSTRAINT  fk_funcao_classe_salarial_classe_salarial_id;
	
	ALTER TABLE funcao
	DROP COLUMN classe_salarial_id
END


