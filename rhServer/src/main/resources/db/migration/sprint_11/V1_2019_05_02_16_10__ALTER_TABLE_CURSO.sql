--Taylor Oliveira
--Add coluna carga_horaria

IF NOT EXISTS(SELECT *
          FROM   INFORMATION_SCHEMA.COLUMNS
          WHERE  TABLE_NAME = 'curso'
                 AND COLUMN_NAME = 'carga_horaria') 
BEGIN
	ALTER TABLE curso
	add  carga_horaria bigint not null
END
