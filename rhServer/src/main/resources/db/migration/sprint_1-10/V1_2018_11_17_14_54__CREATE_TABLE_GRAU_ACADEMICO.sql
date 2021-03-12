--Criação da tabela Grau Acadêmico. 
--Roberto Araujo.

IF NOT EXISTS(SELECT * FROM SYS.TABLES WHERE name = 'grau_academico')
CREATE TABLE grau_academico
(
    id 				BIGINT 		PRIMARY KEY 	NOT NULL IDENTITY,
	nome			VARCHAR(255)				NOT NULL,
	created_at 		datetime2(7)	 			NOT NULL,
	updated_at 		datetime2(7) 				NOT NULL,
	created_by 		bigint								,
	updated_by 		bigint								  
)