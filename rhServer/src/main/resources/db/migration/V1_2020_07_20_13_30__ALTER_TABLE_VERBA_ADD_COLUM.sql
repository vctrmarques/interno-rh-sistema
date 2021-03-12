-- Criando os campo na tabela Verba
-- João Marques

ALTER TABLE verba ADD verba_associada_id BIGINT;

ALTER TABLE verba ADD identificador_verba VARCHAR(50);

ALTER TABLE verba ADD CONSTRAINT fk_verba_verba_associada FOREIGN KEY (verba_associada_id) REFERENCES verba(id)