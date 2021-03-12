-- Lucas Moura

-- Criação da tabela de Funcionario Exercicio

IF NOT EXISTS (SELECT * FROM SYS.TABLES WHERE name = 'funcionario_exercicio')
CREATE TABLE funcionario_exercicio (
	
	id 									bigint 		PRIMARY KEY		NOT NULL IDENTITY(1,1),
	funcionario_id 						bigint 						NOT NULL,
	classificacao_ato_id				bigint 						NOT NULL,
	exercicio						    varchar(10) 				NOT NULL,
	data_inicio							date 		 				NOT NULL,
	data_fim							date 		 				NOT NULL,
	processo							varchar(30)					NOT NULL,
	num_diario_oficial					varchar(30)					NOT NULL,
	data_diario_oficial					date 		 				NOT NULL,
	numero_ato							varchar(30)					NOT NULL,
	ano_ato								varchar(10)					NOT NULL,
	data_ato							date 		 				NOT NULL,
	created_at 							DATETIME2(7) 				NOT NULL,
	updated_at 							DATETIME2(7) 				NOT NULL,
	created_by 							bigint								,
	updated_by 							bigint								,
	
	CONSTRAINT fk_funcionario_exercicio_funcionario_id FOREIGN KEY (funcionario_id) REFERENCES funcionario(id),
	CONSTRAINT fk_funcionario_exercicio_classificacao_ato_id FOREIGN KEY (classificacao_ato_id) REFERENCES classificacao_ato(id)
		
)