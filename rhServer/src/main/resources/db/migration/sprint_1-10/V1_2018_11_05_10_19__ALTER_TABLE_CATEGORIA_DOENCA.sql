--Coluna codigo da tabela categoria_doenca tem que ser uma String
--Flávio Silva

DROP INDEX categoria_doenca.uk_categoria_doenca_codigo;

ALTER TABLE categoria_doenca
ALTER COLUMN codigo varchar(255);

CREATE INDEX uk_categoria_doenca_codigo ON categoria_doenca (codigo);

