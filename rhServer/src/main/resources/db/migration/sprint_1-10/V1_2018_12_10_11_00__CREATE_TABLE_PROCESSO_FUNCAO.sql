--Criação da tabela Processo de Função. 
--Roberto Araujo.

IF NOT EXISTS(SELECT * FROM SYS.TABLES WHERE name = 'processo_funcao')
CREATE TABLE processo_funcao
(
    id 				BIGINT 		PRIMARY KEY 	NOT NULL IDENTITY,
	codigo			INT							NOT NULL,
    descricao 		VARCHAR(255) 				NOT NULL,
	created_at 		datetime2(7)	 			NOT NULL,
	updated_at 		datetime2(7) 				NOT NULL,
	created_by 		bigint								,
	updated_by 		bigint								  
)