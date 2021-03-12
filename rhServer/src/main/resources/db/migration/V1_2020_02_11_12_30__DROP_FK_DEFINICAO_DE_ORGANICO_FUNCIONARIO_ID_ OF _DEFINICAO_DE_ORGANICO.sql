-- Anderson Galindro
-- Deletando fk_definicao_de_organico_funcionario_id da tabela definicao_de_organico

ALTER TABLE definicao_de_organico   
DROP CONSTRAINT fk_definicao_de_organico_funcionario_id; 