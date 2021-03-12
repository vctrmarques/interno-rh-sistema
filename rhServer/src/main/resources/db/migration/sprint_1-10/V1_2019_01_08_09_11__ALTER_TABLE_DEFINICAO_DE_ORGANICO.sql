--Alteração da tabela funcionario, adicionando colunas. 
--Lucas Moura.

IF COL_LENGTH('definicao_de_organico', 'empresa_filial_id') IS NULL
BEGIN
    ALTER TABLE definicao_de_organico
    ADD empresa_filial_id bigint NOT NULL;
END