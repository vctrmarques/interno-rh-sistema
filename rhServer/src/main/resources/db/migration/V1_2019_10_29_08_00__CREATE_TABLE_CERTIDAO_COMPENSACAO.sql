-- Criação das tabelas certidao compensacao, periodos, anexos e assinatura.
-- Insere dados no menu
 
-- Rodrigo Leite

IF NOT EXISTS(SELECT * FROM SYS.TABLES WHERE name = 'certidao_compensacao')
CREATE TABLE certidao_compensacao
(
    id 							BIGINT 					 	NOT NULL IDENTITY,
    numero						BIGINT						NOT NULL,
    ano							INT							NOT NULL,
    funcionario_id				BIGINT						NOT NULL,
    classificacao				VARCHAR(255)				NOT NULL,
    status_atual				VARCHAR(255)				NULL,
    fundo						VARCHAR(255)				NOT NULL,
    lotacao_id					BIGINT						NOT NULL,
    processo					VARCHAR(255)				NOT NULL,
	created_at 					datetime2(7)	 			NOT NULL,
	updated_at 					datetime2(7) 				NOT NULL,
	created_by 					bigint						NULL,
	updated_by 					bigint						NULL,
	
	CONSTRAINT pk_certidao_compensacao PRIMARY KEY (id),
    CONSTRAINT fk_certidao_compensacao_funcionario FOREIGN KEY (funcionario_id) REFERENCES funcionario(id),
    CONSTRAINT fk_certidao_compensacao_lotacao FOREIGN KEY (lotacao_id) REFERENCES lotacao(id)
)

IF NOT EXISTS (SELECT * FROM SYS.TABLES WHERE name = 'certidao_compensacao_anexo')
CREATE TABLE certidao_compensacao_anexo 
(
	certidao_compensacao_id 			BIGINT 						NOT NULL,
	anexo_id 							BIGINT 						NOT NULL,
	
	CONSTRAINT pk_certidao_compensacao_anexo PRIMARY KEY (certidao_compensacao_id, anexo_id),
	CONSTRAINT fk_certidao_compensacao foreign key (certidao_compensacao_id) REFERENCES certidao_compensacao(id),
	CONSTRAINT fk_certidao_compensacao_anexo foreign key (anexo_id) REFERENCES anexo(id)
) 

IF NOT EXISTS(SELECT * FROM SYS.TABLES WHERE name = 'certidao_compensacao_periodo')
CREATE TABLE certidao_compensacao_periodo
(
    id 									BIGINT 					 	NOT NULL IDENTITY,
	certidao_compensacao_id				BIGINT						NOT NULL,
	periodo_inicio						DATE						NOT NULL,
	periodo_fim							DATE						NOT NULL,
	created_at 							datetime2(7)	 			NOT NULL,
	updated_at 							datetime2(7) 				NOT NULL,
	created_by 							bigint						NULL,
	updated_by 							bigint						NULL,
	
	CONSTRAINT pk_certidao_compensacao_averbacao PRIMARY KEY (id),
    CONSTRAINT fk_certidao_compensacao_averbacao_certidao FOREIGN KEY (certidao_compensacao_id) REFERENCES certidao_compensacao(id)
)

IF NOT EXISTS(SELECT * FROM SYS.TABLES WHERE name = 'certidao_compensacao_assinatura')
CREATE TABLE certidao_compensacao_assinatura
(
    id 									BIGINT 					 	NOT NULL IDENTITY,
	certidao_compensacao_id				BIGINT						NOT NULL,
	funcionario_id						BIGINT						NOT NULL,
	funcao								VARCHAR(255)				NOT NULL,
	created_at 							datetime2(7)	 			NOT NULL,
	updated_at 							datetime2(7) 				NOT NULL,
	created_by 							bigint						NULL,
	updated_by 							bigint						NULL,
	
	CONSTRAINT pk_certidao_compensacao_assinatura PRIMARY KEY (id),
    CONSTRAINT fk_certidao_compensacao_assinatura_certidao FOREIGN KEY (certidao_compensacao_id) REFERENCES certidao_compensacao(id),
    CONSTRAINT fk_certidao_compensacao_assinatura_funcionario FOREIGN KEY (funcionario_id) REFERENCES funcionario(id)
)

IF NOT EXISTS(SELECT * FROM SYS.TABLES WHERE name = 'certidao_compensacao_historico')
CREATE TABLE certidao_compensacao_historico
(
    id 							BIGINT 					 	NOT NULL IDENTITY,
    certidao_compensacao_id		BIGINT						NOT NULL,
    classificacao				VARCHAR(255)				NOT NULL,
    status						VARCHAR(255)				NULL,
    fundo						VARCHAR(255)				NULL,
    observacao					VARCHAR(255)				NULL,
	created_at 					datetime2(7)	 			NOT NULL,
	updated_at 					datetime2(7) 				NOT NULL,
	created_by 					bigint								,
	updated_by 					bigint								,
	
	CONSTRAINT pk_certidao_compensacao_historico PRIMARY KEY (id),
    CONSTRAINT fk_certidao_compensacao_historico_certidao FOREIGN KEY (certidao_compensacao_id) REFERENCES certidao_compensacao(id)
)

INSERT INTO menu VALUES(SYSDATETIME(), SYSDATETIME(), 1, 1, 1, 'MODULO_PREVIDENCIARIO', 'Certidão compensação', '/certidaoCompensacao/gestao');

