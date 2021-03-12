-- Railson Silva
-- create table funcao_verba

IF NOT EXISTS (SELECT * FROM SYS.TABLES WHERE name = 'funcao_verba')
CREATE TABLE funcao_verba (
	funcao_id bigint NOT NULL,
	verba_id bigint NOT NULL,
	CONSTRAINT pk_funcao_verba PRIMARY KEY (funcao_id,verba_id),
	CONSTRAINT fk_funcao_verba_funcao FOREIGN KEY (funcao_id) REFERENCES funcao(id) ,
	CONSTRAINT fk_funcao_verba_verba FOREIGN KEY (verba_id) REFERENCES verba(id)
) ;