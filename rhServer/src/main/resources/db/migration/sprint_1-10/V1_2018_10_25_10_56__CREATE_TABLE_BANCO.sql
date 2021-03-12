-- Criação da tabela de banco
-- Flávio Silva

CREATE TABLE banco (
	id bigint NOT NULL IDENTITY(1,1),
	created_at datetime2(7) NOT NULL,
	updated_at datetime2(7) NOT NULL,
	created_by bigint,
	updated_by bigint,
	agencia int NOT NULL,
	bloqueado bit,
	codigo int NOT NULL,
	digito int,
	nome varchar(255) NOT NULL,
	nome_agencia varchar(255) NOT NULL,
	praca varchar(255) NOT NULL,
	referencia varchar(255),
	uf varchar(255) NOT NULL,
	CONSTRAINT pk_banco PRIMARY KEY (id)
) 
CREATE UNIQUE INDEX uk_banco_codigo ON banco (codigo);
CREATE UNIQUE INDEX uk_banco_nome ON banco (nome);
