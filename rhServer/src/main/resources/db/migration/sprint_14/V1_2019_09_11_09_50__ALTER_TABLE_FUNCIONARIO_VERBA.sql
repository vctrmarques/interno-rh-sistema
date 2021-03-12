-- Inserir campo Recorrencia e parcelas na tabela FUNCIONARIO_VERBA
-- Rodrigo Leite

ALTER TABLE funcionario_verba ADD recorrencia VARCHAR(255) NULL;
ALTER TABLE funcionario_verba ADD parcelas INT NULL;