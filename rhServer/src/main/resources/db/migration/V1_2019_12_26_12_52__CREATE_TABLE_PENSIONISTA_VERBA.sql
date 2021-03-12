-- Anderson Galindro
-- 26/12/2019 
-- adicionar a tabela pensao_previdenciaria
IF NOT EXISTS (SELECT * FROM SYS.TABLES WHERE name = 'pensionista_verba')	
BEGIN
	CREATE TABLE pensionista_verba (
	id bigint IDENTITY(1,1) NOT NULL,
	pensionista_id bigint NOT NULL,
	verba_id bigint NOT NULL,
	valor float NULL,
	created_at datetime2(7) NULL,
	updated_at datetime2(7) NULL,
	created_by bigint NULL,
	updated_by bigint NULL,
	tipo_valor varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	recorrencia varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	parcelas int NULL,
	parcelas_pagas int NULL,
	CONSTRAINT pk_pensionista_verba PRIMARY KEY (id),
	CONSTRAINT fk_pensionista_verba_pensionista_id FOREIGN KEY (pensionista_id) REFERENCES pensao_previdenciaria(id),
	CONSTRAINT fk_pensionista_verba_verba_id FOREIGN KEY (verba_id) REFERENCES verba(id)
	);
END