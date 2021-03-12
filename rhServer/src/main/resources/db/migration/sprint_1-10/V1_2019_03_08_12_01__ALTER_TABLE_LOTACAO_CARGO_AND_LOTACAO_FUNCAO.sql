--Lucas Moura
--Alteração das tabelas de lotacao_cargo e lotacao_funcao
--Inserindo novas colunas: empresa_filial_id, quant_permitido e id


DROP TABLE IF EXISTS lotacao_cargo

IF NOT EXISTS(SELECT * FROM SYS.TABLES WHERE name = 'lotacao_cargo')
CREATE TABLE lotacao_cargo (
	id bigint NOT NULL IDENTITY(1,1),
	empresa_filial_id bigint NOT NULL,
	lotacao_id bigint NOT NULL,
	cargo_id bigint NOT NULL,
    quant_permitido bigint NOT NULL,
	CONSTRAINT pk_lotacao_cargo PRIMARY KEY (id),
    CONSTRAINT fk_lotacao_cargo_empresa_filial_id FOREIGN KEY (empresa_filial_id) REFERENCES empresa_filial(id),
	CONSTRAINT fk_lotacao_cargo_lotacao_id FOREIGN KEY (lotacao_id) REFERENCES lotacao(id),
	CONSTRAINT fk_lotacao_cargo_lotacao_cargo_id FOREIGN KEY (cargo_id) REFERENCES cargo(id) 
)

DROP TABLE IF EXISTS lotacao_funcao

IF NOT EXISTS(SELECT * FROM SYS.TABLES WHERE name = 'lotacao_funcao')
CREATE TABLE lotacao_funcao (
	id bigint NOT NULL IDENTITY(1,1),
    empresa_filial_id bigint NOT NULL,    
	lotacao_id bigint NOT NULL,
	funcao_id bigint NOT NULL,
    quant_permitido bigint NOT NULL,
    
	CONSTRAINT pk_lotacao_funcao PRIMARY KEY (id),
    CONSTRAINT fk_lotacao_funcao_empresa_filial_id FOREIGN KEY (empresa_filial_id) REFERENCES empresa_filial(id),
	CONSTRAINT fk_lotacao_funcao_lotacao_id FOREIGN KEY (lotacao_id) REFERENCES lotacao(id),
	CONSTRAINT fk_lotacao_funcao_lotacao_funcao_id FOREIGN KEY (funcao_id) REFERENCES funcao(id) 
);

