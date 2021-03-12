-- Lucas Moura

-- Criação da tabela de Funcionário Verba

IF NOT EXISTS (SELECT * FROM SYS.TABLES WHERE name = 'funcionario_verba')
CREATE TABLE funcionario_verba (
	
	id bigint NOT NULL IDENTITY(1,1),
	funcionario_id BIGINT NOT NULL,
	verba_id BIGINT NOT NULL
	
	CONSTRAINT pk_funcionario_verba PRIMARY KEY (id),
	CONSTRAINT fk_funcionario_verba_funcionario_id FOREIGN KEY (funcionario_id) REFERENCES funcionario(id),
	CONSTRAINT fk_funcionario_verba_verba_id FOREIGN KEY (verba_id) REFERENCES verba(id)


)