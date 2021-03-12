-- Railson Silva
-- create table funcao_requisito

IF NOT EXISTS (SELECT * FROM SYS.TABLES WHERE name = 'funcao_requisito')
CREATE TABLE funcao_requisito (
	funcao_id bigint NOT NULL,
	requisito_id bigint NOT NULL,
	CONSTRAINT pk_funcao_requisito PRIMARY KEY (funcao_id,requisito_id),
	CONSTRAINT fk_funcao_requisito_funcao FOREIGN KEY (funcao_id) REFERENCES funcao(id) ,
	CONSTRAINT fk_funcao_requisito_requisito FOREIGN KEY (requisito_id) REFERENCES requisito(id)
) ;