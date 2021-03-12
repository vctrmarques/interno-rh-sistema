-- Criação de tabela compensacao
-- Davi Queiroz

IF NOT EXISTS(SELECT * FROM SYS.TABLES WHERE name = 'compensacao')
CREATE TABLE compensacao (
	id bigint PRIMARY KEY NOT NULL  IDENTITY(1,1),
	tomador_servico_id BIGINT NOT NULL,
	competencia datetime2(7) NOT NULL,
	valor_compensacao FLOAT NOT NULL,
	data_compensacao_inicial datetime2(7),
	data_compensacao_final datetime2(7),
	campo_seis_gps_anterior INT,
	campo_nove_gps_anterior INT,
	created_at datetime2(7) NOT NULL,
	updated_at datetime2(7) NOT NULL,
	created_by bigint,
	updated_by bigint,
	CONSTRAINT fk_tomador_servico FOREIGN KEY (tomador_servico_id) REFERENCES tomador_servico(id)

)
