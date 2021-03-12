--Lucas Moura
--Adicionando coluna nova na tabela de cargo.


IF COL_LENGTH('cargo', 'lei_respaldo') IS NULL
BEGIN
	ALTER TABLE cargo ADD lei_respaldo varchar(255);
END