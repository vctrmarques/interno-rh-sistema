
-- Flávio Silva

-- Criação da tabela de parametro global

CREATE TABLE parametro_global (
	id bigint IDENTITY(0,1) NOT NULL,
	created_at datetime2(7) NOT NULL,
	updated_at datetime2(7) NOT NULL,
	created_by bigint NULL,
	updated_by bigint NULL,
	ativo bit NOT NULL,
	nome varchar(255) COLLATE Latin1_General_CI_AS NOT NULL,
	tipo varchar(255) COLLATE Latin1_General_CI_AS NOT NULL,
	valor varchar(255) COLLATE Latin1_General_CI_AS NOT NULL,
	CONSTRAINT pk_parametro_global PRIMARY KEY (id)
) 
