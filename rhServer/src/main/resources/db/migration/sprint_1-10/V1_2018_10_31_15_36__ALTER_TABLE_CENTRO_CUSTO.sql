--Alteração da tabela centro_custo, adicionando colunas. 
--Davi Queiroz.

IF COL_LENGTH('centro_custo', 'tipo_centro_custo') IS NULL
BEGIN
    ALTER TABLE centro_custo
    ADD tipo_centro_custo VARCHAR(255);
END