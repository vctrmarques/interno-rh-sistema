--Davi Queiroz
--Adicionando colunas novas na tabela de cargo.

IF NOT EXISTS(SELECT * FROM SYS.TABLES WHERE name = 'cargo_curso')
BEGIN
	CREATE TABLE cargo_curso (
		cargo_id bigint NOT NULL,
		curso_id bigint NOT NULL,
		CONSTRAINT pk_cargo_curso PRIMARY KEY (cargo_id,curso_id),
		CONSTRAINT fk_cargo_curso_cargo_id FOREIGN KEY (cargo_id) REFERENCES cargo(id) ,
		CONSTRAINT fk_cargo_curso_curso_id FOREIGN KEY (curso_id) REFERENCES atividade(id) 
	);
END
