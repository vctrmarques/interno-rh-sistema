-- Flávio Silva
-- Refatoração da tabela de incidencia de verba.

-- Criando tabela nova	
CREATE TABLE verba_incidenciaNova (
	verba_id bigint NOT NULL,
	verba_incidencia_id bigint NOT NULL,
	CONSTRAINT pk_verba_incidencia PRIMARY KEY (verba_id, verba_incidencia_id),
	CONSTRAINT fk_verba_incidencia_verba_id FOREIGN KEY (verba_id) REFERENCES verba(id),
	CONSTRAINT fk_verba_incidencia_verba_incidencia_id FOREIGN KEY (verba_incidencia_id) REFERENCES verba(id) 
);