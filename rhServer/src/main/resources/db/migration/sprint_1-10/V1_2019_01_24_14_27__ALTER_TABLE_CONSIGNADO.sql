-- Corrigindo incoerências do documento com o código
-- Marconi Motta
BEGIN IF (SELECT COUNT(id) FROM consignado) > 0
	UPDATE consignado SET cnpj = '41078711000166' WHERE cnpj = '' OR cnpj IS NULL;
END;
ALTER TABLE consignado ALTER COLUMN cnpj varchar(14) NOT NULL;
ALTER TABLE consignado ALTER COLUMN telefone varchar(255) NULL;
ALTER TABLE consignado ALTER COLUMN contrato varchar(255) NULL;