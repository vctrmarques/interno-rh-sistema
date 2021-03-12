--Criação da tabela Vale Transporte. 
--Roberto Araujo.

IF NOT EXISTS(SELECT * FROM SYS.TABLES WHERE name = 'vale_transporte')
CREATE TABLE vale_transporte
(
    id 				BIGINT 		PRIMARY KEY 	NOT NULL IDENTITY,
	codigo			VARCHAR(255)				NOT NULL,
    valor	 		FLOAT		 				NOT NULL,
	created_at 		datetime2(7)	 			NOT NULL,
	updated_at 		datetime2(7) 				NOT NULL,
	created_by 		bigint								,
	updated_by 		bigint								  
)