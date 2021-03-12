-- Davi Queiroz

-- Criação da tabela de vinculo_verba

IF NOT EXISTS (SELECT * FROM SYS.TABLES WHERE name = 'vinculo_verba')
CREATE TABLE vinculo_verba (
	
	vinculo_id BIGINT NOT NULL,
	verba_id BIGINT NOT NULL
	
	CONSTRAINT fk_vinculo_verba_vinculo_id FOREIGN KEY (vinculo_id) REFERENCES vinculo(id),
	CONSTRAINT fk_vinculo_verba_verba_id FOREIGN KEY (verba_id) REFERENCES verba(id)


)