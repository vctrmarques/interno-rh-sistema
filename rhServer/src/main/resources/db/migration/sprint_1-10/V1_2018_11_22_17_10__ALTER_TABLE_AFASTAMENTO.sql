--Alteração da tabela afastamento, adicionando colunas. 
--Davi Queiroz.

IF COL_LENGTH('afastamento', 'id_causa_afastamento') IS NULL
BEGIN
    ALTER TABLE afastamento
    ADD id_causa_afastamento BIGINT;
END