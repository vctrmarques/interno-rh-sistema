
-- Anderson Galindro
-- Add fk na tabela pensionista e columa nacionalidade_id

IF COL_LENGTH('pensionista', 'nacionalidade_id') IS NOT NULL
BEGIN
    ALTER TABLE pensionista
    ADD CONSTRAINT fk_pensionista_nacionalidade_id FOREIGN KEY (nacionalidade_id) REFERENCES nacionalidade(id);
END