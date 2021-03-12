-- Criação de tabela Unidade Federativa
-- Roberto Araujo

IF NOT EXISTS(SELECT * FROM SYS.TABLES WHERE name = 'unidade_federativa')
CREATE TABLE unidade_federativa (
	
	id 				bigint 		PRIMARY KEY 	NOT NULL  IDENTITY(1,1),
	sigla 			VARCHAR(2)		 			NOT NULL,
	estado 			VARCHAR(255) 				NOT NULL,
	created_at 		datetime2(7) 				NOT NULL,
	updated_at 		datetime2(7) 				NOT NULL,
	created_by 		bigint,
	updated_by 		bigint
)
