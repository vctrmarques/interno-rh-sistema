--Criação da tabela Classificação do Ato. 
--Roberto Araujo.

IF NOT EXISTS(SELECT * FROM SYS.TABLES WHERE name = 'classificacao_ato')
CREATE TABLE classificacao_ato
(
    id 				BIGINT 		PRIMARY KEY 	NOT NULL IDENTITY,
	descricao		VARCHAR(255)				NOT NULL,
	created_at 		datetime2(7)	 			NOT NULL,
	updated_at 		datetime2(7) 				NOT NULL,
	created_by 		bigint								,
	updated_by 		bigint								  
)