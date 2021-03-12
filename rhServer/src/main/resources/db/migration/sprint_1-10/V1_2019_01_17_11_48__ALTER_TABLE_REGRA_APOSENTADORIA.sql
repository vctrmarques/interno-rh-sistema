IF COL_LENGTH('regra_aposentadoria', 'tempo_servico_em_pleno_exercicio') IS NULL
BEGIN
    ALTER TABLE regra_aposentadoria
    ADD tempo_servico_em_pleno_exercicio bit;
END

