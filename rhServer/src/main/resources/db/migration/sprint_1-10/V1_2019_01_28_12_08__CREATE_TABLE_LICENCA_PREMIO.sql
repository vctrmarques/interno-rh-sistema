-- Lucas Moura

-- Criação da tabela de Licença Prêmio

IF NOT EXISTS (SELECT * FROM SYS.TABLES WHERE name = 'licenca_premio')
CREATE TABLE licenca_premio (
	
	id 									bigint 		PRIMARY KEY		NOT NULL IDENTITY(1,1),
	funcionario_exercicio_id 			bigint 						NOT NULL,
	observacao						    varchar(2000) 				NOT NULL,
	data_inicio							date 		 				NOT NULL,
	data_retorno						date 		 				NOT NULL,
	dias								varchar(3)					NOT NULL,
	created_at 							DATETIME2(7) 				NOT NULL,
	updated_at 							DATETIME2(7) 				NOT NULL,
	created_by 							bigint								,
	updated_by 							bigint								,
	
	CONSTRAINT fk_licenca_premio_funcionario_exercicio_id FOREIGN KEY (funcionario_exercicio_id) REFERENCES funcionario_exercicio(id)
		
)