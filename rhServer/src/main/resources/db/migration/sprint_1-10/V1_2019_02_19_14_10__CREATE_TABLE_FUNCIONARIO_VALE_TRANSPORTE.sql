-- Davi Queiroz

-- Criação da tabela de Funcionário Vale Transporte

IF NOT EXISTS (SELECT * FROM SYS.TABLES WHERE name = 'funcionario_vale_transporte')
CREATE TABLE funcionario_vale_transporte (
	
	id bigint NOT NULL IDENTITY(1,1),
	funcionario_id BIGINT NOT NULL,
	quantidade INT NOT NULL,
	vale_transporte_id BIGINT NOT NULL,
	created_by BIGINT,
	updated_by BIGINT,
	created_at DATETIME2(7) NOT NULL,
	updated_at DATETIME2(7) NOT NULL,
	
	CONSTRAINT pk_funcionario_vale_transporte PRIMARY KEY (id),
	CONSTRAINT fk_funcionario_vale_transporte_funcionario_funcionario_id FOREIGN KEY (funcionario_id) REFERENCES funcionario(id),
	CONSTRAINT ffk_funcionario_vale_transporte_vale_transporte_vale_tranporte_id FOREIGN KEY (vale_transporte_id) REFERENCES funcao(id)

)