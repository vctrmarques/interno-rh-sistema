--Flavio Silva
--Adicionando colunas novas na tabela de cargo.


IF COL_LENGTH('cargo', 'cbo_id') IS NULL
BEGIN
	ALTER TABLE cargo ADD cbo_id BIGINT;
	ALTER TABLE cargo ADD CONSTRAINT fk_cargo_cbo_id FOREIGN KEY (cbo_id) REFERENCES cbo (id) ON DELETE CASCADE ON UPDATE CASCADE;
END

IF COL_LENGTH('cargo', 'natureza_funcao_id') IS NULL
BEGIN
	ALTER TABLE cargo ADD natureza_funcao_id BIGINT;
	ALTER TABLE cargo ADD CONSTRAINT fk_cargo_natureza_funcao_id FOREIGN KEY (natureza_funcao_id) REFERENCES natureza_funcao (id) ON DELETE CASCADE ON UPDATE CASCADE;
END

IF COL_LENGTH('cargo', 'processo_funcao_id') IS NULL
BEGIN
	ALTER TABLE cargo ADD processo_funcao_id BIGINT;
	ALTER TABLE cargo ADD CONSTRAINT fk_cargo_processo_funcao_id FOREIGN KEY (processo_funcao_id) REFERENCES processo_funcao (id) ON DELETE CASCADE ON UPDATE CASCADE;
END

IF COL_LENGTH('cargo', 'categoria_profissional_id') IS NULL
BEGIN
	ALTER TABLE cargo ADD categoria_profissional_id BIGINT;
	ALTER TABLE cargo ADD CONSTRAINT fk_cargo_categoria_profissional_id FOREIGN KEY (categoria_profissional_id) REFERENCES categoria_profissional (id) ON DELETE CASCADE ON UPDATE CASCADE;
END

IF COL_LENGTH('cargo', 'sindicato_id') IS NULL
BEGIN
	ALTER TABLE cargo ADD sindicato_id BIGINT;
	ALTER TABLE cargo ADD CONSTRAINT fk_cargo_sindicato_id FOREIGN KEY (sindicato_id) REFERENCES sindicato (id) ON DELETE CASCADE ON UPDATE CASCADE;
END

IF COL_LENGTH('cargo', 'contagem_tempo_especial') IS NULL
BEGIN
	ALTER TABLE cargo ADD contagem_tempo_especial varchar(255);
END

IF COL_LENGTH('cargo', 'motivo_lei') IS NULL
BEGIN
	ALTER TABLE cargo ADD motivo_lei varchar(255);
END

IF COL_LENGTH('cargo', 'dedicacao_exclusiva') IS NULL
BEGIN
	ALTER TABLE cargo ADD dedicacao_exclusiva bit;
END

IF NOT EXISTS(SELECT * FROM SYS.TABLES WHERE name = 'cargo_vinculo')
BEGIN
	CREATE TABLE cargo_vinculo (
		cargo_id bigint NOT NULL,
		vinculo_id bigint NOT NULL,
		CONSTRAINT pk_cargo_vinculo PRIMARY KEY (cargo_id,vinculo_id),
		CONSTRAINT fk_cargo_vinculo_cargo_id FOREIGN KEY (cargo_id) REFERENCES cargo(id) ,
		CONSTRAINT fk_cargo_vinculo_vinculo_id FOREIGN KEY (vinculo_id) REFERENCES vinculo(id) 
	);
END

