--Alteração da tabela centro_custo, adicionando colunas. 
--Davi Queiroz.

IF COL_LENGTH('afastamento', 'id_motivo_afastamento') IS NULL
BEGIN
    ALTER TABLE afastamento
    ADD id_motivo_afastamento BIGINT;
END