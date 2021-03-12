--Criação da tabela definicao_de_organico.
--Lucas Moura


IF NOT EXISTS (SELECT * FROM SYS.TABLES WHERE name = 'definicao_de_organico')
CREATE TABLE definicao_de_organico (
	id bigint NOT NULL IDENTITY(1,1),
	funcionario_id bigint NOT NULL,
	vigencia_inicial date,
	vigencia_final date,
	email_responsavel varchar(255) NOT NULL,
	primeiro_substituto_nome varchar(255),
	primeiro_substituto_email varchar(255),
	segundo_substituto_nome varchar(255),
	segundo_substituto_email varchar(255),
	previsao_funcionarios bigint,
	funcionarios_atuais bigint,
	total_funcionarios bigint,
	previsao_custos numeric(13,3),
	custos_atuais numeric(13,3),
	custo_total numeric(13,3),
	conf_critica_avisar bit,
	conf_critica_criticar bit,
	conf_critica_nenhum bit,
	created_at datetime2(7) NOT NULL,
	updated_at datetime2(7) NOT NULL,
	created_by bigint,
	updated_by bigint,
	CONSTRAINT pk_definicao_de_organico PRIMARY KEY (id),
	CONSTRAINT fk_definicao_de_organico_funcionario_id FOREIGN KEY (funcionario_id) REFERENCES funcionario(id)
	
)

IF NOT EXISTS(SELECT * FROM SYS.TABLES WHERE name = 'filial_lotacao')
CREATE TABLE empresa_filial_lotacao (
	empresa_filial_id bigint NOT NULL,
	lotacao_id bigint NOT NULL,
	CONSTRAINT pk_empresa_filial_id_lotacao PRIMARY KEY (empresa_filial_id,lotacao_id),
	CONSTRAINT fk_empresa_filial_lotacao_empresa_filial_id FOREIGN KEY (empresa_filial_id) REFERENCES empresa_filial(id),
	CONSTRAINT fk_empresa_filial_lotacao_empresa_filial_lotacao_id FOREIGN KEY (lotacao_id) REFERENCES lotacao(id) 
)

IF NOT EXISTS(SELECT * FROM SYS.TABLES WHERE name = 'lotacao_cargo')
CREATE TABLE lotacao_cargo (
	lotacao_id bigint NOT NULL,
	cargo_id bigint NOT NULL,
	CONSTRAINT pk_lotacao_cargo PRIMARY KEY (lotacao_id,cargo_id),
	CONSTRAINT fk_lotacao_cargo_lotacao_id FOREIGN KEY (lotacao_id) REFERENCES lotacao(id),
	CONSTRAINT fk_lotacao_cargo_lotacao_cargo_id FOREIGN KEY (cargo_id) REFERENCES cargo(id) 
)

IF NOT EXISTS(SELECT * FROM SYS.TABLES WHERE name = 'lotacao_funcao')
CREATE TABLE lotacao_funcao (
	lotacao_id bigint NOT NULL,
	funcao_id bigint NOT NULL,
	CONSTRAINT pk_lotacao_funcao PRIMARY KEY (lotacao_id,funcao_id),
	CONSTRAINT fk_lotacao_funcao_lotacao_id FOREIGN KEY (lotacao_id) REFERENCES lotacao(id),
	CONSTRAINT fk_lotacao_funcao_lotacao_funcao_id FOREIGN KEY (funcao_id) REFERENCES funcao(id) 
);