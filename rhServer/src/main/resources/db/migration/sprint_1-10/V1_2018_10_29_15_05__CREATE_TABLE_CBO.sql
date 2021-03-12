--Criação da tabela CBO
--Flávio Silva

CREATE TABLE cbo (
	id bigint NOT NULL IDENTITY(1,1),
	created_at datetime2(7) NOT NULL,
	updated_at datetime2(7) NOT NULL,
	created_by bigint,
	updated_by bigint,
	codigo int NOT NULL,
	nome varchar(255) NOT NULL,
	CONSTRAINT pk_cbo PRIMARY KEY (id)
) 
CREATE UNIQUE INDEX uk_cbo_nome ON cbo (nome) 
CREATE UNIQUE INDEX uk_cbo_codigo ON cbo (codigo) ;
