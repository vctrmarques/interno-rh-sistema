--Criação da tabela Processo de Função TAG. 
--Roberto Araujo.

IF NOT EXISTS(SELECT * FROM SYS.TABLES WHERE name = 'processo_funcao_tag')
CREATE TABLE processo_funcao_tag (

	id 					bigint 		PRIMARY KEY 	NOT NULL  IDENTITY(1,1),
	descricao	 		VARCHAR(255) 				NOT NULL,
	id_processo_funcao 	bigint						NOT NULL,
CONSTRAINT fk_id_processo_funcao FOREIGN KEY (id_processo_funcao) REFERENCES processo_funcao(id),
)