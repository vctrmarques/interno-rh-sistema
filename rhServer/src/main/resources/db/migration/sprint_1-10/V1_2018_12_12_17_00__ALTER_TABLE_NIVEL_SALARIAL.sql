-- Alteração tabela nivel_salarial e nivel_salarial_historico
-- Wallace Nascimento

ALTER TABLE nivel_salarial ALTER COLUMN valor float not null;
ALTER TABLE nivel_salarial_historico ALTER COLUMN valor float not null;
