--Alteração da tabela afastamento, removendo coluna. 
--Davi Queiroz.

IF COL_LENGTH('afastamento', 'codigo_motivo_afastamento') IS NOT NULL
BEGIN
    ALTER TABLE afastamento
    DROP COLUMN codigo_motivo_afastamento;
END