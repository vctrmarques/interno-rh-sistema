--Railson Silva
--Add coluna principal

IF NOT EXISTS(SELECT *
          FROM   INFORMATION_SCHEMA.COLUMNS
          WHERE  TABLE_NAME = 'banco'
                 AND COLUMN_NAME = 'principal') 
BEGIN
	ALTER TABLE banco
	add  principal bit
END


