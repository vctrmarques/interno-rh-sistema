--Criação da tabela dado_cadastral_complementar.
--Davi Queiroz


IF NOT EXISTS (SELECT * FROM SYS.TABLES WHERE name = 'dado_cadastral_complementar')
CREATE TABLE dado_cadastral_complementar (
	
	id bigint NOT NULL IDENTITY(1,1),
	funcionario_id bigint NOT NULL,
	fardamento VARCHAR(255),
	altura FLOAT,
	peso FLOAT,
	calcado VARCHAR(255),
	condicao_retorno_trabalho VARCHAR(255) NOT NULL,
	dt_ini_deficiencia DATETIME2(7),
	dt_fim_deficiencia DATETIME2(7),
	reabilitado BIT NOT NULL,
	cotista BIT NOT NULL,
	descricao_deficiencia VARCHAR(255),
	dt_aposentadoria DATETIME2(7) NOT NULL,
	tipo_aposentadoria VARCHAR(255) NOT NULL,
	proporcionalidade INT,
	tipo_proporcao VARCHAR(255) NOT NULL,
	contribuicao_inss BIT NOT NULL,
	consignado_bloqueado BIT,
	contribuicao_ir BIT NOT NULL,
	num_processo_aposentadoria BIGINT,
	local_redistribuicao VARCHAR(255),
	dt_distribuicao_cedencia DATETIME2(7),
	dt_ini_isencao_ir_previdencia DATETIME2(7),
	dt_fim_isencao_ir_previdencia DATETIME2(7),
	dt_falecimento DATETIME2(7),
	tempo_servidor_professor INT,
	created_by BIGINT,
	updated_by BIGINT,
	created_at DATETIME2(7) NOT NULL,
	updated_at DATETIME2(7) NOT NULL
	
	CONSTRAINT pk_dado_cadastral_complementar PRIMARY KEY (id),
	CONSTRAINT fk_dado_cadastral_complementar_funcionario_funcionario_id FOREIGN KEY (funcionario_id) REFERENCES funcionario(id)
	
)
