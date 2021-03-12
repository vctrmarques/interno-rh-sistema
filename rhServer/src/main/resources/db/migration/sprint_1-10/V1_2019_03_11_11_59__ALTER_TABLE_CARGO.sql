--Railson Silva
--Add coluna faixa_salarial_id

IF NOT EXISTS(SELECT *
          FROM   INFORMATION_SCHEMA.COLUMNS
          WHERE  TABLE_NAME = 'cargo'
                 AND COLUMN_NAME = 'grupo_salarial_id') 
BEGIN
	ALTER TABLE cargo
	add  grupo_salarial_id bigint
	constraint fk_cargo_grupo_salarial_id  foreign key (grupo_salarial_id) references grupo_salarial(id)
END


