--Railson Silva
--Adicionando coluna centro_custo_id

IF NOT EXISTS(SELECT *
          FROM   INFORMATION_SCHEMA.COLUMNS
          WHERE  TABLE_NAME = 'funcao'
                 AND COLUMN_NAME = 'centro_custo_id') 
BEGIN
	ALTER TABLE funcao
	add centro_custo_id bigint
	CONSTRAINT fk_funcao_centro_custo foreign key (centro_custo_id) REFERENCES centro_custo(id)
END


