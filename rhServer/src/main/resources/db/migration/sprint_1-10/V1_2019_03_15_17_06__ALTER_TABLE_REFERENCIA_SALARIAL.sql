-- Davi Queiroz

-- alteração da tabela de referencia salarial

BEGIN IF COL_LENGTH('referencia_salarial', 'mes_ano_competencia') IS NULL
    ALTER TABLE referencia_salarial
    ADD mes_ano_competencia VARCHAR(255)
END