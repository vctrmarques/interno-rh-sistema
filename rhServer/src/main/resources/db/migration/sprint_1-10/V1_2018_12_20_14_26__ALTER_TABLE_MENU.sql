--Lucas Moura
--Adicionando coluna nova na tabela de menu.


IF COL_LENGTH('menu', 'url') IS NULL
BEGIN
	ALTER TABLE menu ADD url varchar(255);
		
END