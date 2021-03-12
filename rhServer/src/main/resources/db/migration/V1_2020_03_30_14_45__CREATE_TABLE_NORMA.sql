--Criação da tabela norma
--Flávio Silva

CREATE TABLE norma (

	id bigint NOT NULL IDENTITY(1,1),
	
	created_at datetime2(7) NOT NULL,
	updated_at datetime2(7) NOT NULL,
	created_by bigint,
	updated_by bigint,
	
	codigo int NOT NULL,
	descricao varchar(255) NOT NULL,
	vigencia datetime2(7) NOT NULL,
	
	CONSTRAINT pk_norma PRIMARY KEY (id)
	
) 
CREATE UNIQUE INDEX uk_norma_codigo ON norma (codigo);
CREATE UNIQUE INDEX uk_norma_descricao ON norma (descricao);