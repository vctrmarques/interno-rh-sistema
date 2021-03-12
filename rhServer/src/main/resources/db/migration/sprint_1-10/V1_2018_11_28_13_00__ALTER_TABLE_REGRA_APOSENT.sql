--Flávio Silva
--Add column tipo_vigencia

IF COL_LENGTH('regra_aposentadoria', 'tipo_vigencia') IS NULL
BEGIN
    ALTER TABLE regra_aposentadoria
    ADD tipo_vigencia VARCHAR(255);
END