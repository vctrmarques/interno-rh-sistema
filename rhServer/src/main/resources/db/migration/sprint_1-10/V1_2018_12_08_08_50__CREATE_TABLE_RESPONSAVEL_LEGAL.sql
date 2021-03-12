-- Criação de tabela Responsável Legal
-- Roberto Araújo

IF NOT EXISTS(SELECT * FROM SYS.TABLES WHERE name = 'responsavel_legal')
CREATE TABLE responsavel_legal (
	id bigint 				PRIMARY KEY 	NOT NULL  IDENTITY(1,1),
	codigo_responsavel 		INT				NOT NULL,
	nome 					VARCHAR(255) 	NOT NULL,
	id_banco 				BIGINT 			NOT NULL,
	agencia 				INT 			NOT NULL,
	conta_corrente			INT 			NOT NULL,
	tipo_responsavel 		VARCHAR(255) 	NOT NULL,
	cpf 					VARCHAR(11) 	NOT NULL,
	identidade 				INT 			NOT NULL,
	tipo_conta 				VARCHAR(255) 	NOT NULL,
	logradouro 				VARCHAR(255) 	NOT NULL,
	numero 					VARCHAR(255) 	NOT NULL,
	complemento 			VARCHAR(255) 	NOT NULL,
	bairro 					VARCHAR(255) 	NOT NULL,
	id_municipio 			BIGINT 			NOT NULL,
	cep 					VARCHAR(255) 	NOT NULL,
	id_unidade_federativa 	BIGINT			NOT NULL,
	telefone 				VARCHAR(255)	NOT NULL,
	created_at 				datetime2(7) 	NOT NULL,
	updated_at 				datetime2(7) 	NOT NULL,
	created_by 				bigint					,
	updated_by 				bigint					,
	CONSTRAINT fk_id_banco FOREIGN KEY (id_banco) REFERENCES banco(id),
	CONSTRAINT fk_id_unidade_federativa FOREIGN KEY (id_unidade_federativa) REFERENCES unidade_federativa(id),
	CONSTRAINT fk_id_municipio FOREIGN KEY (id_municipio) REFERENCES municipio(id)
)