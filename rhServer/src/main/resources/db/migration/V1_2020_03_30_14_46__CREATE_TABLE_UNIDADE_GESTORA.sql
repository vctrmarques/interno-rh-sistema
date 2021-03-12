--Criação da tabela unidade_gestora
--Flávio Silva

CREATE TABLE unidade_gestora (

	id bigint NOT NULL IDENTITY(1,1),
	
	created_at datetime2(7) NOT NULL,
	updated_at datetime2(7) NOT NULL,
	created_by bigint,
	updated_by bigint,
	
	codigo int NOT NULL,
	descricao varchar(255) NOT NULL,
	
	CONSTRAINT pk_unidade_gestora PRIMARY KEY (id)
	
) 
CREATE UNIQUE INDEX uk_unidade_gestora_codigo ON unidade_gestora (codigo);
CREATE UNIQUE INDEX uk_unidade_gestora_descricao ON unidade_gestora (descricao);