--Alteração da tabela afastamento, adicionando colunas. 
--Davi Queiroz.

IF COL_LENGTH('afastamento', 'id_motivo_desligamento') IS NULL
BEGIN
    ALTER TABLE afastamento
    ADD id_motivo_desligamento BIGINT;
END