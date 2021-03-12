-- Remover campos inscrição anterior e conta_fgts
-- Marconi Motta
ALTER TABLE empresa_filial DROP COLUMN inscricao_anterior;
ALTER TABLE empresa_filial DROP COLUMN conta_fgts;
