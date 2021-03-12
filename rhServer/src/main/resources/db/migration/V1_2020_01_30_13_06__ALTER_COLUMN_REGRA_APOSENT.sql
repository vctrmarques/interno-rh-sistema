--Colunas de formula da tabela regra_aposentadoria devem suportar fórmulas grandes.
--Flávio Silva

ALTER TABLE regra_aposentadoria ALTER COLUMN idade_homem_formula varchar(2000);

ALTER TABLE regra_aposentadoria ALTER COLUMN idade_mulher_formula varchar(2000);

ALTER TABLE regra_aposentadoria ALTER COLUMN tempo_contribuicao_homem_formula varchar(2000);

ALTER TABLE regra_aposentadoria ALTER COLUMN tempo_contribuicao_mulher_formula varchar(2000);

