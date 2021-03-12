--Criação da tabela ente_federado
--Flávio Silva

CREATE TABLE ente_federado (

	id bigint NOT NULL IDENTITY(1,1),
	
	created_at datetime2(7) NOT NULL,
	updated_at datetime2(7) NOT NULL,
	created_by bigint,
	updated_by bigint,
	
	codigo int NOT NULL,
	descricao varchar(255) NOT NULL,
	vigencia datetime2(7) NOT NULL,
	
	CONSTRAINT pk_ente_federado PRIMARY KEY (id)
	
) 
CREATE UNIQUE INDEX uk_ente_federado_codigo ON ente_federado (codigo);
CREATE UNIQUE INDEX uk_ente_federado_descricao ON ente_federado (descricao);