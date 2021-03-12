-- Anderson Galindro
-- Deletando colunas vigencia_final,email_responsavel,vigencia_inicial e funcionario_id 
-- da tabela definicao_de_organico


ALTER TABLE definicao_de_organico DROP COLUMN vigencia_final;
ALTER TABLE definicao_de_organico DROP COLUMN email_responsavel;
ALTER TABLE definicao_de_organico DROP COLUMN vigencia_inicial;
ALTER TABLE definicao_de_organico DROP COLUMN funcionario_id;