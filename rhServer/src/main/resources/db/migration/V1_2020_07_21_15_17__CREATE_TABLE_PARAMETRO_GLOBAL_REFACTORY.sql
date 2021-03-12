
-- Flávio Silva
-- Recriando a tabela corrigindo a configuração dos IDs.

IF EXISTS(SELECT * FROM SYS.TABLES WHERE name = 'parametro_global') 
	DROP TABLE parametro_global
	
IF NOT EXISTS(SELECT * FROM SYS.TABLES WHERE name = 'parametro_global')
	CREATE TABLE parametro_global (
		id bigint NOT NULL IDENTITY(1,1),
		created_at datetime2(7) NOT NULL,
		updated_at datetime2(7) NOT NULL,
		created_by bigint NULL,
		updated_by bigint NULL,
		ativo bit NOT NULL,
		chave varchar(255) NOT NULL,
		tipo varchar(255) NOT NULL,
		valor varchar(255) NOT NULL,
		CONSTRAINT pk_parametro_global PRIMARY KEY (id)
	)