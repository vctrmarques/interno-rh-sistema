--Davi Queiroz
--Adicionando colunas novas na tabela de cargo.

IF NOT EXISTS(SELECT * FROM SYS.TABLES WHERE name = 'cargo_habilidade')
BEGIN
	CREATE TABLE cargo_habilidade (
		cargo_id bigint NOT NULL,
		habilidade_id bigint NOT NULL,
		CONSTRAINT pk_cargo_habilidade PRIMARY KEY (cargo_id,habilidade_id),
		CONSTRAINT fk_cargo_habilidade_cargo_id FOREIGN KEY (cargo_id) REFERENCES cargo(id) ,
		CONSTRAINT fk_cargo_habilidade_habilidade_id FOREIGN KEY (habilidade_id) REFERENCES atividade(id) 
	);
END

