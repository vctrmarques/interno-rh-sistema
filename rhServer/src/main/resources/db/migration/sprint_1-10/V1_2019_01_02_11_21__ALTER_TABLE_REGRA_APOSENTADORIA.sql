--Adição de colunas na tabela de regra aposentadoria. 
--Flávio Silva.

IF COL_LENGTH('regra_aposentadoria', 'tempo_contribuicao_homem_formula') IS NULL
BEGIN
    ALTER TABLE regra_aposentadoria
    ADD tempo_contribuicao_homem_formula VARCHAR(255);
END

IF COL_LENGTH('regra_aposentadoria', 'tempo_contribuicao_mulher_formula') IS NULL
BEGIN
    ALTER TABLE regra_aposentadoria
    ADD tempo_contribuicao_mulher_formula VARCHAR(255);
END

