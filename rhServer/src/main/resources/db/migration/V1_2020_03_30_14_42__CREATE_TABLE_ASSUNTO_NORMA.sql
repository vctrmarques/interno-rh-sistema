--Criação da tabela assunto_norma
--Flávio Silva

CREATE TABLE assunto_norma (

	id bigint NOT NULL IDENTITY(1,1),
	
	created_at datetime2(7) NOT NULL,
	updated_at datetime2(7) NOT NULL,
	created_by bigint,
	updated_by bigint,
	
	codigo int NOT NULL,
	descricao varchar(255) NOT NULL,
	vigencia datetime2(7) NOT NULL,
	
	CONSTRAINT pk_assunto_norma PRIMARY KEY (id)
	
) 
CREATE UNIQUE INDEX uk_assunto_norma_codigo ON assunto_norma (codigo);
CREATE UNIQUE INDEX uk_assunto_norma_descricao ON assunto_norma (descricao);