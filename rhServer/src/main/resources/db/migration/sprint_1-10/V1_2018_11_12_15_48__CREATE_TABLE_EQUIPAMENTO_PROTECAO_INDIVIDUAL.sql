--Criação da tabela equipamento_protecao_individual. 
--Roberto Araujo.

IF NOT EXISTS(SELECT * FROM SYS.TABLES WHERE name = 'equipamento_protecao_individual')
CREATE TABLE equipamento_protecao_individual
(
    id 					BIGINT 		PRIMARY KEY 	NOT NULL IDENTITY,
	codigo 				BIGINT 						NOT NULL,
	tipo_protecao		VARCHAR(255)				NOT NULL,
	descricao 			VARCHAR(255) 				NOT NULL,
	validade 			datetime2(7)	 			NOT NULL,
	certificado			VARCHAR(255)				NOT NULL,
	created_at 			datetime2(7)	 			NOT NULL,
	updated_at 			datetime2(7) 				NOT NULL,
	created_by 			bigint								,
	updated_by 			bigint								  
)