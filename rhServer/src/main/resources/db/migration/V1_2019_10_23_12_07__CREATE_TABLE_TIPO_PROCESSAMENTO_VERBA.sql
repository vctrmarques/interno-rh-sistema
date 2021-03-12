-- Flávio Silva
-- Criação da tabela de junção entre verbas e tipos de processamento.

CREATE TABLE tipo_processamento_verba (
	tipo_processamento_id bigint NOT NULL,
	verba_id bigint NOT NULL,
	CONSTRAINT pk_tipo_processamento_verba PRIMARY KEY (tipo_processamento_id,verba_id),
	CONSTRAINT fk_tipo_processamento_verba_tipo_processamento_id FOREIGN KEY (tipo_processamento_id) REFERENCES tipo_processamento(id) ,
	CONSTRAINT fk_tipo_processamento_verba_verba_id FOREIGN KEY (verba_id) REFERENCES verba(id) 
) ;