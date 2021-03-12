--Alteração da tabela Consignado, adicionando coluna. 
--Roberto Araujo.

IF COL_LENGTH('consignado', 'unidade_federativa_consignatario') IS NULL
BEGIN
    ALTER TABLE consignado 
    ADD id_unidade_federativa_consignatario bigint,
    CONSTRAINT fk_unidade_federativa_consignatario FOREIGN KEY (id_unidade_federativa_consignatario) REFERENCES unidade_federativa(id)
END
