--Railson Silva
--Adicionando coluna centro_custo_id

IF NOT EXISTS(SELECT *
          FROM   INFORMATION_SCHEMA.COLUMNS
          WHERE  TABLE_NAME = 'verba'
                 AND COLUMN_NAME = 'centro_custo_id') 
BEGIN
	ALTER TABLE verba
	add centro_custo_id bigint not null
	CONSTRAINT fk_verba_centro_custo foreign key (centro_custo_id) REFERENCES centro_custo(id)
END


