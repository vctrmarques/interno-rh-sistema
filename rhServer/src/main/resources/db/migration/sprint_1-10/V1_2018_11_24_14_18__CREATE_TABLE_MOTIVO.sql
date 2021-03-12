--Criação da tabela motivo. 
--Roberto Araújo.

IF NOT EXISTS(SELECT * FROM SYS.TABLES WHERE name = 'motivo')
CREATE TABLE motivo
(
    id 				BIGINT 			PRIMARY KEY 	NOT NULL IDENTITY,
   	descricao 		VARCHAR(255) 					NOT NULL,
   	tipo 			VARCHAR(255)							,
   	evento 			VARCHAR(255)							,
   	created_at 		datetime2(7) 					NOT NULL,
	updated_at 		datetime2(7) 					NOT NULL,
	created_by 		bigint,
	updated_by 		bigint
)

