-- Criação da tabela de anexo_processo
-- Railson Silva

IF NOT EXISTS (SELECT * FROM SYS.TABLES WHERE name = 'anexo_processo')
CREATE TABLE anexo_processo (
	processo_id bigint NOT NULL,
	anexo_id bigint NOT NULL,
	CONSTRAINT pk_anexo_processo PRIMARY KEY (processo_id,anexo_id),
	CONSTRAINT fk_anexo_processo foreign key (processo_id) REFERENCES processo(id),
	CONSTRAINT fk_processo_anexo foreign key (anexo_id) REFERENCES anexo(id),
) 

