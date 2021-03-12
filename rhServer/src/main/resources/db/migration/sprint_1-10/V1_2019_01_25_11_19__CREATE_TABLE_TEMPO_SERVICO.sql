--Criação da tabela tempo_servico.
--Davi Queiroz


IF NOT EXISTS (SELECT * FROM SYS.TABLES WHERE name = 'tempo_servico')
CREATE TABLE tempo_servico (
	
	id bigint NOT NULL IDENTITY(1,1),
	funcionario_id BIGINT NOT NULL,
	protocolo_ctc_cts VARCHAR(255) NOT NULL,
	salario FLOAT NOT NULL,
	indice_contribuicao VARCHAR(255) NOT NULL,
	ultimo_cargo VARCHAR(255) NOT NULL,
	sefip_id BIGINT NOT NULL,
	descricao_certidao VARCHAR(255),
	descricao_empresa VARCHAR(255) NOT NULL,
	tipo_averbacao_id BIGINT NOT NULL,
	tipo_averbacao VARCHAR(255) NOT NULL,
	averbado BIT NOT NULL,
	dt_inicio DATETIME2(7) NOT NULL,
	dt_termino DATETIME2(7) NOT NULL,
	qtd_dias INT NOT NULL,
	num_processo VARCHAR(255) NOT NULL,
	classificacao_ato_id BIGINT NOT NULL,
	num_diario_oficial VARCHAR(255) NOT NULL,
	ano_publicacao INT NOT NULL,
	dt_diario_oficial DATETIME2(7) NOT NULL,
	num_ato VARCHAR(255) NOT NULL,
	data DATETIME2(7) NOT NULL,
	endereco VARCHAR(255) NOT NULL,
	cidade VARCHAR(255) NOT NULL,
	numero VARCHAR(255) NOT NULL,
	complemento VARCHAR(255),
	bairro VARCHAR(255) NOT NULL,
	uf_id BIGINT NOT NULL,
	cep VARCHAR(8) NOT NULL,
	telefone VARCHAR(255) NOT NULL,
	cnpj VARCHAR(14) NOT NULL,
	created_by BIGINT,
	updated_by BIGINT,
	created_at DATETIME2(7) NOT NULL,
	updated_at DATETIME2(7) NOT NULL,
	
	CONSTRAINT pk_tempo_servico PRIMARY KEY (id),
	CONSTRAINT fk_tempo_servico_funcionario_funcionario_id FOREIGN KEY (funcionario_id) REFERENCES funcionario(id),
	CONSTRAINT fk_tempo_servico_sefip_sefip_id FOREIGN KEY (sefip_id) REFERENCES sefip(id),
	CONSTRAINT fk_tempo_servico_classificacao_ato_classificacao_ato_id FOREIGN KEY (classificacao_ato_id) REFERENCES classificacao_ato(id),
	CONSTRAINT fk_tempo_servico_unidade_federativa_uf_id FOREIGN KEY (uf_id) REFERENCES unidade_federativa(id)

)
