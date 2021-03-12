-- Lucas Moura

-- Criação da tabela de Funcionário Anexos

IF NOT EXISTS (SELECT * FROM SYS.TABLES WHERE name = 'funcionario_anexo')
CREATE TABLE funcionario_anexo (
	
	id 									bigint 		PRIMARY KEY		NOT NULL IDENTITY(1,1),
	funcionario_id 						bigint 						NOT NULL,
	anexo_id							bigint 						NOT NULL,
	
	CONSTRAINT fk_funcionario_anexo_funcionario_id FOREIGN KEY (funcionario_id) REFERENCES funcionario(id),
	CONSTRAINT fk_funcionario_anexo_anexo_id FOREIGN KEY (anexo_id) REFERENCES anexo(id)
		
)