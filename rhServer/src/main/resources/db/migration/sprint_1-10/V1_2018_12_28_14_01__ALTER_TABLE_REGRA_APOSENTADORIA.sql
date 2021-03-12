--Adição de colunas na tabela de regra aposentadoria. 
--Davi Queiroz.

IF COL_LENGTH('regra_aposentadoria', 'idade_homem_formula') IS NULL
BEGIN
    ALTER TABLE regra_aposentadoria
    ADD idade_homem_formula VARCHAR(255);
END

IF COL_LENGTH('regra_aposentadoria', 'idade_mulher_formula') IS NULL
BEGIN
    ALTER TABLE regra_aposentadoria
    ADD idade_mulher_formula VARCHAR(255);
END

