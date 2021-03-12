DROP INDEX uk_classe_salarial_nome ON classe_salarial;
UPDATE classe_salarial SET nome = 'TESTE_CORREÇÃO' WHERE nome is null;
ALTER TABLE classe_salarial ALTER COLUMN nome varchar(255) NOT NULL;