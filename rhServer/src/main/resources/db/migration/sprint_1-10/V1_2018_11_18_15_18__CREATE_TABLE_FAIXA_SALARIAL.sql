--Criação da tabela Faixa Salarial. 
--Roberto Araujo.

IF NOT EXISTS(SELECT * FROM SYS.TABLES WHERE name = 'faixa_salarial')
CREATE TABLE faixa_salarial
(
    id 				BIGINT 		PRIMARY KEY 	NOT NULL IDENTITY,
	funcao			VARCHAR(255)				NOT NULL,
	classe			VARCHAR(255)				NOT NULL,
	step			INT							NOT NULL,
	valor			FLOAT						NOT NULL,
	created_at 		datetime2(7)	 			NOT NULL,
	updated_at 		datetime2(7) 				NOT NULL,
	created_by 		bigint								,
	updated_by 		bigint								  
)