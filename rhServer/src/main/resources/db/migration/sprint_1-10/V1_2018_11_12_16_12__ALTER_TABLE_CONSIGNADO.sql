--Alteração da tabela Consignado, adicionando coluna. 
--Davi Queiroz.
IF COL_LENGTH('consignado', 'verba_desconto') IS NULL
BEGIN
    ALTER TABLE consignado
    ADD verba_desconto INT;
END
