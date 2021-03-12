--Criação da tabela causa afastamento. 
--Roberto Araujo.

IF NOT EXISTS(SELECT * FROM SYS.TABLES WHERE name = 'causa_afastamento')
CREATE TABLE causa_afastamento
(
    id 				BIGINT 		PRIMARY KEY 	NOT NULL IDENTITY,
	codigo			VARCHAR(255)				NOT NULL,
    descricao 		VARCHAR(255) 				NOT NULL,
	created_at 		datetime2(7)	 			NOT NULL,
	updated_at 		datetime2(7) 				NOT NULL,
	created_by 		bigint								,
	updated_by 		bigint								  
)