-- Criação da tabela Natureza da Função
-- Marconi Motta

IF NOT EXISTS(SELECT * FROM SYS.TABLES WHERE name = 'natureza_funcao')
CREATE TABLE natureza_funcao (

	id 				bigint 			PRIMARY KEY 	NOT NULL  IDENTITY(1,1),
	codigo			INT 							NOT NULL,
	descricao	 	VARCHAR(255) 					NOT NULL,
	created_at 		datetime2(7) 					NOT NULL,
	updated_at 		datetime2(7) 					NOT NULL,
	created_by 		bigint								,
	updated_by 		bigint	
)
CREATE UNIQUE INDEX uk_natureza_funcao_descricao ON natureza_funcao (descricao) 
CREATE UNIQUE INDEX uk_natureza_funcao_codigo ON natureza_funcao (codigo) ;

IF NOT EXISTS(SELECT * FROM SYS.TABLES WHERE name = 'natureza_funcao_tag')
CREATE TABLE natureza_funcao_tag (

	id 				bigint 			PRIMARY KEY 	NOT NULL  IDENTITY(1,1),
	descricao	 	VARCHAR(255) 					NOT NULL,
	id_natureza_funcao bigint						NOT NULL,
CONSTRAINT fk_id_natureza_funcao FOREIGN KEY (id_natureza_funcao) REFERENCES natureza_funcao(id),
)
