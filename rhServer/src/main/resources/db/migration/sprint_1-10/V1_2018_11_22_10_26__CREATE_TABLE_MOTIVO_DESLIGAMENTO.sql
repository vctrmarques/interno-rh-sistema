--Criação da tabela motivo desligamento. 
--Roberto Araujo.

IF NOT EXISTS(SELECT * FROM SYS.TABLES WHERE name = 'motivo_desligamento')
CREATE TABLE motivo_desligamento
(
    id 				BIGINT 		PRIMARY KEY 	NOT NULL IDENTITY,
	codigo			INT							NOT NULL,
    descricao 		VARCHAR(255) 				NOT NULL,
	created_at 		datetime2(7)	 			NOT NULL,
	updated_at 		datetime2(7) 				NOT NULL,
	created_by 		bigint								,
	updated_by 		bigint								  
)