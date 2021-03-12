--Criação da tabela de subcategoria de doença
--Flávio Silva

CREATE TABLE sub_categoria_doenca (
	id bigint NOT NULL IDENTITY(1,1),
	created_at datetime2(7) NOT NULL,
	updated_at datetime2(7) NOT NULL,
	created_by bigint,
	updated_by bigint,
	codigo varchar(255) NOT NULL,
	descricao varchar(255) NOT NULL,
	CONSTRAINT pk_sub_categoria_doenca PRIMARY KEY (id)
) 
CREATE UNIQUE INDEX uk_sub_categoria_doenca_codigo ON sub_categoria_doenca (codigo) 
CREATE UNIQUE INDEX uk_sub_categoria_doenca_descricao ON sub_categoria_doenca (descricao) ;
