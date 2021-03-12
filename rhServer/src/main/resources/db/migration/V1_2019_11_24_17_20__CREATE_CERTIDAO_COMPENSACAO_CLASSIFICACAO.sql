IF NOT EXISTS(SELECT * FROM SYS.TABLES WHERE name = 'certidao_compensacao_classificacao')
BEGIN
	CREATE TABLE certidao_compensacao_classificacao (
		certidao_compensacao_id bigint NOT NULL,
		classificacao varchar(255) NOT NULL,
		CONSTRAINT pk_certidao_compensacao_classificacao PRIMARY KEY (certidao_compensacao_id,classificacao),
		CONSTRAINT fk_certidao_compensacao_classificacao_certidao_compensacao_id FOREIGN KEY (certidao_compensacao_id) REFERENCES certidao_compensacao(id) 
	);
END