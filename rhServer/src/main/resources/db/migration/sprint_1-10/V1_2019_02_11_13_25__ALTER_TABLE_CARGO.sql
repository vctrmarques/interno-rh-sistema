--Davi Queiroz
--Adicionando colunas novas na tabela de cargo.

IF NOT EXISTS(SELECT * FROM SYS.TABLES WHERE name = 'cargo_atividade')
BEGIN
	CREATE TABLE cargo_atividade (
		cargo_id bigint NOT NULL,
		atividade_id bigint NOT NULL,
		CONSTRAINT pk_cargo_atividade PRIMARY KEY (cargo_id,atividade_id),
		CONSTRAINT fk_cargo_atividade_cargo_id FOREIGN KEY (cargo_id) REFERENCES cargo(id) ,
		CONSTRAINT fk_cargo_atividade_atividade_id FOREIGN KEY (atividade_id) REFERENCES atividade(id) 
	);
END

