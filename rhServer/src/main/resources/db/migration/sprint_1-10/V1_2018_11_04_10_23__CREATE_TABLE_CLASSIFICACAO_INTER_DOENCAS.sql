--Flávio Silva
--Criação da tabela de cassificação internacional de doenças


CREATE TABLE classificacao_internacional_doenca (
	id bigint NOT NULL IDENTITY(1,1),
	created_at datetime2(7) NOT NULL,
	updated_at datetime2(7) NOT NULL,
	created_by bigint,
	updated_by bigint,
	codigo varchar(255) NOT NULL,
	descricao varchar(255) NOT NULL,
	categoria_doenca_id bigint,
	sub_categoria_doenca_id bigint,
	CONSTRAINT pk_classificacao_internacional_doenca PRIMARY KEY (id),
	CONSTRAINT fk_classificacao_internacional_doenca_categoria_doenca_id FOREIGN KEY (categoria_doenca_id) REFERENCES categoria_doenca(id),
	CONSTRAINT fk_classificacao_internacional_doenca_sub_categoria_doenca_id FOREIGN KEY (sub_categoria_doenca_id) REFERENCES sub_categoria_doenca(id) 
) 
CREATE UNIQUE INDEX uk_classificacao_internacional_doenca_codigo ON classificacao_internacional_doenca (codigo) 
CREATE UNIQUE INDEX uk_classificacao_internacional_doenca_descricao ON classificacao_internacional_doenca (descricao) ;
