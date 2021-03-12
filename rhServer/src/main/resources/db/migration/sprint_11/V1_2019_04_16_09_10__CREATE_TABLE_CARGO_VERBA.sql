-- Railson Silva
-- create table cargo_verba

IF NOT EXISTS (SELECT * FROM SYS.TABLES WHERE name = 'cargo_verba')
CREATE TABLE cargo_verba (
	cargo_id bigint NOT NULL,
	verba_id bigint NOT NULL,
	CONSTRAINT pk_cargo_verba PRIMARY KEY (cargo_id,verba_id),
	CONSTRAINT fk_cargo_verba_cargo FOREIGN KEY (cargo_id) REFERENCES cargo(id) ,
	CONSTRAINT fk_cargo_verba_verba FOREIGN KEY (verba_id) REFERENCES verba(id)
) ;