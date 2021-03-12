--Criação da tabela texto_documento
--Flávio Silva

CREATE TABLE texto_documento (

	id bigint NOT NULL IDENTITY(1,1),
	
	created_at datetime2(7) NOT NULL,
	updated_at datetime2(7) NOT NULL,
	created_by bigint,
	updated_by bigint,
	
	codigo int NOT NULL,
	descricao varchar(255) NOT NULL,
	vigencia datetime2(7) NOT NULL,
	
	CONSTRAINT pk_texto_documento PRIMARY KEY (id)
	
) 
CREATE UNIQUE INDEX uk_texto_documento_codigo ON texto_documento (codigo);
CREATE UNIQUE INDEX uk_texto_documento_descricao ON texto_documento (descricao);