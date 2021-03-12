--Alteração da tabela Consignado, adicionando coluna. 
--Roberto Araujo.

IF COL_LENGTH('consignado', 'modalidade') IS NULL
BEGIN
    ALTER TABLE consignado 
    ADD modalidade VARCHAR(255);
END
