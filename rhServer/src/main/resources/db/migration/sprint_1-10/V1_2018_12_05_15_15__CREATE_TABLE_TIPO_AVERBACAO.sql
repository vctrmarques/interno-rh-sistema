-- Criação de tabela Tipo Averbação
-- Roberto Araujo

IF NOT EXISTS(SELECT * FROM SYS.TABLES WHERE name = 'tipo_averbacao')
CREATE TABLE tipo_averbacao (

	id 					bigint 		PRIMARY KEY 	NOT NULL  IDENTITY(1,1),
	codigo				INT 						NOT NULL,
	descricao	 		VARCHAR(255) 				NOT NULL,
	forma_averbacao 	VARCHAR(255) 				NOT NULL,
	deducao_averbacao 	VARCHAR(255) 				NOT NULL,
	created_at 			datetime2(7) 				NOT NULL,
	updated_at 			datetime2(7) 				NOT NULL,
	created_by 			bigint								,
	updated_by 			bigint	
)
