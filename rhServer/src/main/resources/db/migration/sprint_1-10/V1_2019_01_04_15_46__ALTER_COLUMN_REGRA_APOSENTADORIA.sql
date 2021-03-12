--Colunas de formula precisarão ter tamanho maior.
--Flávio Silva

ALTER TABLE regra_aposentadoria
ALTER COLUMN tempo_contribuicao_homem_formula varchar(1000);

ALTER TABLE regra_aposentadoria
ALTER COLUMN tempo_contribuicao_mulher_formula varchar(1000);

