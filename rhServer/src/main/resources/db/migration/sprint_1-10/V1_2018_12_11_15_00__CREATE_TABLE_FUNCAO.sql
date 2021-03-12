--Criação da tabela funcao.
--Davi Queiroz


IF NOT EXISTS(SELECT * FROM SYS.TABLES WHERE name = 'funcao')
CREATE TABLE funcao (
	id bigint NOT NULL IDENTITY(1,1),
	nome varchar(255) NOT NULL,
	descricao varchar(255),
	cbo_id bigint NOT NULL,
	data_criacao datetime2(7) NOT NULL,
	data_extincao datetime2(7),
	funcao_extinta bit,
	natureza_funcao_id bigint,
	processo_funcao_id bigint,
	categoria_profissional_id bigint,
	sindicato varchar(255),
	dedicacao_exclusiva bit,
	funcao_acumulavel varchar(255),
	contagem_tempo_especial varchar(255),
	motivo_lei varchar(255),
	numero_lei int,
	data_lei datetime2(7) NOT NULL,
	created_at datetime2(7) NOT NULL,
	updated_at datetime2(7) NOT NULL,
	created_by bigint,
	updated_by bigint,
	CONSTRAINT pk_funcao PRIMARY KEY (id),
	CONSTRAINT fk_cbo_id FOREIGN KEY (cbo_id) REFERENCES cbo(id),
	CONSTRAINT fk_natureza_funcao_id FOREIGN KEY (natureza_funcao_id) REFERENCES natureza_funcao(id),
	CONSTRAINT fk_processo_funcao_id FOREIGN KEY (processo_funcao_id) REFERENCES processo_funcao(id),
	CONSTRAINT fk_categoria_profissional_id FOREIGN KEY (categoria_profissional_id) REFERENCES categoria_profissional(id),
) 

CREATE TABLE funcao_vinculo (
	funcao_id bigint NOT NULL,
	vinculo_id bigint NOT NULL,
	CONSTRAINT pk_funcao_vinculo PRIMARY KEY (funcao_id,vinculo_id),
	CONSTRAINT fk_funcao_vinculo_funcao_id FOREIGN KEY (funcao_id) REFERENCES funcao(id) ,
	CONSTRAINT fk_funcao_vinculo_vinculo_id FOREIGN KEY (vinculo_id) REFERENCES vinculo(id) 
) ;