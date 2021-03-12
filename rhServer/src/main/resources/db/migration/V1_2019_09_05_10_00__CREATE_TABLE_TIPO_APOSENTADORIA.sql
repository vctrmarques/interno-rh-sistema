-- Criação da tabela Tipo de Aposentadoria. 
-- Eduardo Costa

IF NOT EXISTS(SELECT * FROM SYS.TABLES WHERE name = 'tipo_aposentadoria')
CREATE TABLE tipo_aposentadoria
(
    id 				BIGINT 		PRIMARY KEY 	NOT NULL IDENTITY,
	codigo			INT							NOT NULL,
    descricao 		VARCHAR(255) 				NOT NULL,
	created_at 		datetime2(7)	 			NOT NULL,
	updated_at 		datetime2(7) 				NOT NULL,
	created_by 		bigint								,
	updated_by 		bigint								  
)