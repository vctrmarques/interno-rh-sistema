--Davi Queiroz
--Add column compensacao

IF COL_LENGTH('tomador_servico', 'compensacao_id') IS NULL
BEGIN
    ALTER TABLE tomador_servico
    ADD compensacao_id BIGINT;
END