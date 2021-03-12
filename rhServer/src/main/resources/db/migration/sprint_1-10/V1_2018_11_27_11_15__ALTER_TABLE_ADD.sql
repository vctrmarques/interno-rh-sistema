--Flávio Silva
--Add column proventos

IF COL_LENGTH('regra_aposentadoria', 'proventos') IS NULL
BEGIN
    ALTER TABLE regra_aposentadoria
    ADD proventos VARCHAR(255);
END