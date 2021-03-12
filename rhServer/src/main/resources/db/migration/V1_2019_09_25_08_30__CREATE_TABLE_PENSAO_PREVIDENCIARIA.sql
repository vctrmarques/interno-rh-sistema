-- Criação das tabelas pensao previdenciaria dados e pagamentos. 
-- Rodrigo Leite


IF NOT EXISTS(SELECT * FROM SYS.TABLES WHERE name = 'pensao_previdenciaria_pagamento')
CREATE TABLE pensao_previdenciaria_pagamento
(
    id 							BIGINT 					 	NOT NULL IDENTITY,
    data_primeiro_pagamento		DATE						NOT NULL,
    tipo_rateio					VARCHAR(255)				NULL,
    percentual_rateio			INT							NULL,
    tipo_pensao					VARCHAR(255)				NOT NULL,
    com_paridade				BIT							NOT NULL,
    numero_processo_pensao		INT							NOT NULL,
    data_fim_beneficio			DATE						NULL,
    data_limite_retroativo		DATE						NOT NULL,
    tipo_cota					VARCHAR(255)				NULL,
    numero_reserva				INT							NULL,
    agencia_id					BIGINT						NOT NULL,
    tipo_conta					VARCHAR(50)					NOT NULL,
    numero_conta				INT							NOT NULL,
    condicao_isencao			VARCHAR(255)				NULL,
    data_inicio_isencao			DATE						NULL,
    data_fim_isencao			DATE						NULL,
	created_at 					datetime2(7)	 			NOT NULL,
	updated_at 					datetime2(7) 				NOT NULL,
	created_by 					bigint								,
	updated_by 					bigint								,
	
	CONSTRAINT pk_pensao_previdencia_pagamento PRIMARY KEY (id),
    CONSTRAINT fk_pensao_previdencia_pagamento_agencia FOREIGN KEY (agencia_id) REFERENCES agencia(id)
)

IF NOT EXISTS(SELECT * FROM SYS.TABLES WHERE name = 'pensao_previdenciaria')
CREATE TABLE pensao_previdenciaria
(
    id 									BIGINT 					 	NOT NULL IDENTITY,
	funcionario_id						BIGINT						NOT NULL,
	pensao_previdenciaria_pagamento_id	BIGINT						NOT NULL,
	matricula							INT							NOT NULL,
	nome								VARCHAR(255)				NOT NULL,
	data_nascimento						DATE						NOT NULL,
	genero								VARCHAR(50)					NOT NULL,
	estado_civil						VARCHAR(50)					NOT NULL,
	grau_instrucao						VARCHAR(100)				NOT NULL,
	naturalidade						BIGINT						NOT NULL,
	titulo_eleitor						INT							NULL,
	nome_mae							VARCHAR(255)				NULL,
	nome_pai							VARCHAR(255)				NULL,
	cep									INT							NULL,
	logradouro							VARCHAR(255)				NULL,
	numero_logradouro					VARCHAR(50)					NULL,
	complemento_logradouro				VARCHAR(100)				NULL,
	bairro								VARCHAR(255)				NULL,
	municipio_id						BIGINT						NULL,
    tipo_familia						VARCHAR(100)				NULL,
    grau_parentesco						VARCHAR(100)				NULL,
    motivo								VARCHAR(255)				NULL,
    responsavel_legal_id				BIGINT						NULL,
    data_inicio_resp					DATE						NULL,
    data_vencimento_resp				DATE						NULL,
    numero_processo_resp				INT							NULL,
    anexo_id							BIGINT						NULL,
	created_at 							datetime2(7)	 			NOT NULL,
	updated_at 							datetime2(7) 				NOT NULL,
	created_by 							bigint						NULL,
	updated_by 							bigint						NULL,
	
	CONSTRAINT pk_pensao_previdencia_dados PRIMARY KEY (id),
    CONSTRAINT fk_pensao_previdencia_funcionario FOREIGN KEY (funcionario_id) REFERENCES funcionario(id),
    CONSTRAINT fk_pensao_previdencia_pagamento FOREIGN KEY (pensao_previdenciaria_pagamento_id) REFERENCES pensao_previdenciaria_pagamento(id),
    CONSTRAINT fk_pensao_previdencia_naturalidade FOREIGN KEY (naturalidade) REFERENCES municipio(id),
    CONSTRAINT fk_pensao_previdencia_municipio FOREIGN KEY (municipio_id) REFERENCES municipio(id),
    CONSTRAINT fk_pensao_previdencia_responsavel_legal FOREIGN KEY (responsavel_legal_id) REFERENCES responsavel_legal(id),
    CONSTRAINT fk_pensao_previdencia_anexo FOREIGN KEY (anexo_id) REFERENCES anexo(id)
)

INSERT INTO menu VALUES(SYSDATETIME(), SYSDATETIME(), 1, 1, 1, 'MODULO_PREVIDENCIARIO', 'Pensão Previdenciária', '/pensaoPrevidenciaria/gestao');

