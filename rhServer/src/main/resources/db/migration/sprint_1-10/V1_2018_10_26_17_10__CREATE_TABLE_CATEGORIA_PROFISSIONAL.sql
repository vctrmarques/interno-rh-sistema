--Flávio Silva
--Criação da tabela de categoria profissional

CREATE TABLE categoria_profissional (
	id bigint NOT NULL IDENTITY(1,1),
	created_at datetime2(7) NOT NULL,
	updated_at datetime2(7) NOT NULL,
	created_by bigint,
	updated_by bigint,
	ativo bit,
	codigo int NOT NULL,
	descricao varchar(255) NOT NULL,
	CONSTRAINT pk_categoria_profissional PRIMARY KEY (id)
) 
CREATE UNIQUE INDEX uk_categoria_profissional_descricao ON categoria_profissional (descricao) 
CREATE UNIQUE INDEX uk_categoria_profissional_codigo ON categoria_profissional (codigo);
