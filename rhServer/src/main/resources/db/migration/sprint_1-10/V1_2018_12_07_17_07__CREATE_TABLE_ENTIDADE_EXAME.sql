-- Criação da tabela de entiade exame

-- Flávio Silva

CREATE TABLE entidade_exame (
	id bigint NOT NULL IDENTITY(1,1),
	created_at datetime2(7) NOT NULL,
	updated_at datetime2(7) NOT NULL,
	created_by bigint,
	updated_by bigint,
	bairro varchar(255),
	cep varchar(255),
	complemento varchar(255),
	logradouro varchar(255),
	numero varchar(255),
	telefone varchar(255),
	tipo varchar(255) NOT NULL,
	municipio_id bigint,
	tomador_servico_id bigint NOT NULL,
	uf_id bigint,
	CONSTRAINT pk_entidade_exame PRIMARY KEY (id),
	CONSTRAINT fk_entidade_exame_municipio_id FOREIGN KEY (municipio_id) REFERENCES municipio(id) ,
	CONSTRAINT fk_entidade_exame_uf_id FOREIGN KEY (uf_id) REFERENCES unidade_federativa(id) ,
	CONSTRAINT fk_entidade_exame_tomador_servico_id FOREIGN KEY (tomador_servico_id) REFERENCES tomador_servico(id) 
);


CREATE TABLE entidade_exame_exame (
	entidade_exame_id bigint NOT NULL,
	exame_id bigint NOT NULL,
	CONSTRAINT pk_entidade_exame_exame PRIMARY KEY (entidade_exame_id,exame_id),
	CONSTRAINT fk_entidade_exame_exame_exame_id FOREIGN KEY (exame_id) REFERENCES exame(id) ,
	CONSTRAINT fk_entidade_exame_exame_entidade_exame_id FOREIGN KEY (entidade_exame_id) REFERENCES entidade_exame(id) 
);
