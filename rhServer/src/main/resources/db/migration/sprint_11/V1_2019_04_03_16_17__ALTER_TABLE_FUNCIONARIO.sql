--Lucas Moura
--Add coluna operacao referente ao banco

IF NOT EXISTS(SELECT *
          FROM   INFORMATION_SCHEMA.COLUMNS
          WHERE  TABLE_NAME = 'funcionario'
                 AND COLUMN_NAME = 'operacao') 
BEGIN
	ALTER TABLE funcionario
	add  operacao varchar(10)
END


