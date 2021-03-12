-- Criação de tabela Natureza Jurídica
-- Roberto Araujo

IF NOT EXISTS(SELECT * FROM SYS.TABLES WHERE name = 'natureza_juridica')
CREATE TABLE natureza_juridica (

	id 				bigint 			PRIMARY KEY 	NOT NULL  IDENTITY(1,1),
	codigo			INT 							NOT NULL,
	nome	 		VARCHAR(255) 					NOT NULL,
	grupamento 		VARCHAR(255) 					NOT NULL,
	created_at 		datetime2(7) 					NOT NULL,
	updated_at 		datetime2(7) 					NOT NULL,
	created_by 		bigint								,
	updated_by 		bigint	
)
