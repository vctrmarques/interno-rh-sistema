-- Criação das tabelas declaração aposentadoria, averbacao e assinatura.
-- Insere dados no menu
 
-- Rodrigo Leite

IF NOT EXISTS(SELECT * FROM SYS.TABLES WHERE name = 'declaracao_aposentadoria')
CREATE TABLE declaracao_aposentadoria
(
    id 							BIGINT 					 	NOT NULL IDENTITY,
    numero						BIGINT						NOT NULL,
    ano							INT							NOT NULL,
    funcionario_id				BIGINT						NOT NULL,
	created_at 					datetime2(7)	 			NOT NULL,
	updated_at 					datetime2(7) 				NOT NULL,
	created_by 					bigint								,
	updated_by 					bigint								,
	
	CONSTRAINT pk_declaracao_aposentadoria PRIMARY KEY (id),
    CONSTRAINT fk_declaracao_aposentadoria_funcionario FOREIGN KEY (funcionario_id) REFERENCES funcionario(id)
)

IF NOT EXISTS (SELECT * FROM SYS.TABLES WHERE name = 'declaracao_aposentadoria_anexo')
CREATE TABLE declaracao_aposentadoria_anexo 
(
	declaracao_aposentadoria_id 		BIGINT 						NOT NULL,
	anexo_id 							BIGINT 						NOT NULL,
	CONSTRAINT pk_declaracao_aposentadoria_anexo PRIMARY KEY (declaracao_aposentadoria_id, anexo_id),
	CONSTRAINT fk_declaracao_aposentadoria foreign key (declaracao_aposentadoria_id) REFERENCES declaracao_aposentadoria(id),
	CONSTRAINT fk_declaracao_aposentadoria_anexo foreign key (anexo_id) REFERENCES anexo(id)
) 

IF NOT EXISTS(SELECT * FROM SYS.TABLES WHERE name = 'declaracao_aposentadoria_averbacao')
CREATE TABLE declaracao_aposentadoria_averbacao
(
    id 									BIGINT 					 	NOT NULL IDENTITY,
	declaracao_aposentadoria_id			BIGINT						NOT NULL,
	empregador							VARCHAR(255)				NOT NULL,
	periodo_inicio						DATE						NULL,
	periodo_fim							DATE						NULL,
	fonte_inf							VARCHAR(255)				NULL,
	observacao							VARCHAR(255)				NULL,
    averbado							BIT							NULL,
	created_at 							datetime2(7)	 			NOT NULL,
	updated_at 							datetime2(7) 				NOT NULL,
	created_by 							bigint						NULL,
	updated_by 							bigint						NULL,
	
	CONSTRAINT pk_declaracao_aposentadoria_averbacao PRIMARY KEY (id),
    CONSTRAINT fk_declaracao_aposentadoria_averbacao_declaracao FOREIGN KEY (declaracao_aposentadoria_id) REFERENCES declaracao_aposentadoria(id)
)

IF NOT EXISTS(SELECT * FROM SYS.TABLES WHERE name = 'declaracao_aposentadoria_assinatura')
CREATE TABLE declaracao_aposentadoria_assinatura
(
    id 									BIGINT 					 	NOT NULL IDENTITY,
	declaracao_aposentadoria_id			BIGINT						NOT NULL,
	funcionario_id						BIGINT						NOT NULL,
	funcao								VARCHAR(255)				NOT NULL,
	created_at 							datetime2(7)	 			NOT NULL,
	updated_at 							datetime2(7) 				NOT NULL,
	created_by 							bigint						NULL,
	updated_by 							bigint						NULL,
	
	CONSTRAINT pk_declaracao_aposentadoria_assinatura PRIMARY KEY (id),
    CONSTRAINT fk_declaracao_aposentadoria_assinatura_declaracao FOREIGN KEY (declaracao_aposentadoria_id) REFERENCES declaracao_aposentadoria(id),
    CONSTRAINT fk_declaracao_aposentadoria_assinatura_funcionario FOREIGN KEY (funcionario_id) REFERENCES funcionario(id)
)

INSERT INTO menu VALUES(SYSDATETIME(), SYSDATETIME(), 1, 1, 1, 'MODULO_PREVIDENCIARIO', 'Declaração Aposentadoria', '/declaracaoAposentadoria/gestao');

