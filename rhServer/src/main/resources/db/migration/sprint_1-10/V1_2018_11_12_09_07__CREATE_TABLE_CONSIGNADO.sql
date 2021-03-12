--Criação da tabela consignado. 
--Davi Queiroz.

IF NOT EXISTS(SELECT * FROM SYS.TABLES WHERE name = 'consignado')
CREATE TABLE consignado
(
    id BIGINT PRIMARY KEY NOT NULL IDENTITY,
	descricao VARCHAR(255) NOT NULL,
	telefone VARCHAR(255) NOT NULL,
	id_centro_custo BIGINT NOT NULL,
	estado VARCHAR(255),
	cidade VARCHAR(255),
	cep VARCHAR(255),
	logradouro VARCHAR(255),
	complemento VARCHAR(255),
	numero VARCHAR(255),
	bairro VARCHAR(255),
	cnpj  VARCHAR(14),
	rg VARCHAR(40),
	orgao_expeditor VARCHAR(255),
	margem_consignavel FLOAT,
	id_banco BIGINT ,
	agencia VARCHAR(255),
	conta_corrente VARCHAR(255),
	contrato INT,
	tipo_calculo FLOAT,
	email VARCHAR(255),
	taxa_operacional FLOAT,
	tac FLOAT,
	taxa_cadastro_sigla_consignado INT,
	sigla_consignado VARCHAR (255),
	registro_ans INT,
	codigo_convergencia INT,
	digito_convergencia INT,
	extrato_convenente INT,
	digito_convenente INT,
	nome_convenente VARCHAR(255),
	pv_responsavel VARCHAR(255),
	dt_competencia_processamento DATETIME2,
	created_at datetime2(7) NOT NULL,
	updated_at datetime2(7) NOT NULL,
	created_by bigint,
	updated_by bigint,
	CONSTRAINT fk_consignado_id_centro_custo FOREIGN KEY (id_centro_custo) REFERENCES centro_custo(id),
	CONSTRAINT fk_consignado_id_banco FOREIGN KEY (id_banco) REFERENCES banco(id) 
)

