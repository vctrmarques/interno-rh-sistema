-- Criação das tabelas declaração tempo contribuição ex-servidor, dados funcionais.
-- Insere dados no menu
 
-- Rodrigo Leite

IF NOT EXISTS(SELECT * FROM SYS.TABLES WHERE name = 'declaracao_ex_servidor')
CREATE TABLE declaracao_ex_servidor
(
    id 							BIGINT 					 	NOT NULL IDENTITY,
    funcionario_id				BIGINT						NULL,
    responsavel_id				BIGINT						NULL,
    dirigente_id				BIGINT						NULL,
    status_atual				VARCHAR(255)				NULL,
	created_at 					datetime2(7)	 			NOT NULL,
	updated_at 					datetime2(7) 				NOT NULL,
	created_by 					bigint						NULL,
	updated_by 					bigint						NULL,
	
	CONSTRAINT pk_declaracao_ex_servidor PRIMARY KEY (id),
    CONSTRAINT fk_declaracao_ex_servidor_funcionario FOREIGN KEY (funcionario_id) REFERENCES funcionario(id),
	CONSTRAINT fk_declaracao_ex_servidor_responsavel FOREIGN KEY (responsavel_id) REFERENCES funcionario(id),
	CONSTRAINT fk_declaracao_ex_servidor_dirigente FOREIGN KEY (dirigente_id) REFERENCES funcionario(id)
)

IF NOT EXISTS(SELECT * FROM SYS.TABLES WHERE name = 'declaracao_ex_servidor_dados_funcionais')
CREATE TABLE declaracao_ex_servidor_dados_funcionais
(
    id 									BIGINT 					 	NOT NULL IDENTITY,
	declaracao_ex_servidor_id			BIGINT						NOT NULL,
	cargo_id							BIGINT						NULL,
	ato_nomeacao						VARCHAR(255)				NULL,
	data_entrada						DATE						NULL,
	data_do_entrada						DATE						NULL,
	data_encerramento					DATE						NULL,
	ato_encerramento					VARCHAR(255)				NULL,
	data_do_encerramento				DATE						NULL,
	created_at 							datetime2(7)	 			NOT NULL,
	updated_at 							datetime2(7) 				NOT NULL,
	created_by 							bigint						NULL,
	updated_by 							bigint						NULL,
	
	CONSTRAINT pk_declaracao_ex_servidor_dados_funcionais PRIMARY KEY (id),
    CONSTRAINT fk_declaracao_ex_servidor_dados_funcionais_declaracao FOREIGN KEY (declaracao_ex_servidor_id) REFERENCES declaracao_ex_servidor(id),
    CONSTRAINT fk_declaracao_ex_servidor_dados_funcionais_cargo FOREIGN KEY (cargo_id) REFERENCES cargo(id)
)

IF NOT EXISTS(SELECT * FROM menu WHERE url = '/declaracaoExServidor/gestao')
	INSERT INTO menu VALUES(SYSDATETIME(), SYSDATETIME(), 1, 1, 1, 'MODULO_PREVIDENCIARIO', 'DTC - Ex-Servidor', '/declaracaoExServidor/gestao')

