-- Criação de tabela Sindicato
-- Roberto Araujo

IF NOT EXISTS(SELECT * FROM SYS.TABLES WHERE name = 'sindicato')
CREATE TABLE sindicato (

	id 						bigint 		PRIMARY KEY 	NOT NULL  IDENTITY(1,1),
	descricao	 			VARCHAR(255) 				NOT NULL,
	mes_base				INT							NOT NULL,
	piso_salarial			INT							NOT NULL,
	cnpj					VARCHAR(14)					NOT NULL,
	codigo_entidade			VARCHAR(255)					NOT NULL,
	ddd						INT							NOT NULL,
	telefone				VARCHAR(255)				NOT NULL,
	patronal				VARCHAR(255)				NOT NULL,
	endereco		 		VARCHAR(255) 				NOT NULL,
	numero			 		VARCHAR(255) 				NOT NULL,
	complemento				VARCHAR(255)				NOT NULL,
	bairro					VARCHAR(255)				NOT NULL,
	municipio				VARCHAR(255)				NOT NULL,
	id_unidade_federativa_sindicato		bigint						NOT NULL,
	cep						VARCHAR(255)				NOT NULL,
	created_at 				datetime2(7) 				NOT NULL,
	updated_at 				datetime2(7) 				NOT NULL,
	created_by 				bigint								,
	updated_by 				bigint								,
	
	CONSTRAINT fk_id_unidade_federativa_sindicato FOREIGN KEY (id_unidade_federativa_sindicato) REFERENCES unidade_federativa(id)
	
)
