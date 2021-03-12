-- Flávio Silva
-- Ajustes na tabela de consignado.

-- Adição da coluna de unidade federativa
IF COL_LENGTH('consignado', 'uf_id') IS NULL
BEGIN
    ALTER TABLE consignado ADD uf_id BIGINT;
    ALTER TABLE consignado ADD CONSTRAINT fk_consignado_uf_id FOREIGN KEY (uf_id) REFERENCES unidade_federativa(id)
END

-- Adição da coluna de município 
IF COL_LENGTH('consignado', 'municipio_id') IS NULL
BEGIN
    ALTER TABLE consignado ADD municipio_id BIGINT;
    ALTER TABLE consignado ADD CONSTRAINT fk_consignado_municipio_id FOREIGN KEY (municipio_id) REFERENCES municipio(id)
END

-- Ajustando o tamanho de dados de cep
if col_length('consignado', 'cep') is not null
begin
	ALTER TABLE consignado ALTER COLUMN cep varchar(8) NULL;
end

-- Descontinuando o atribugo cidade, que será substituido pelo objeto municipio
if col_length('consignado', 'cidade') is not null
begin
	ALTER TABLE consignado DROP COLUMN cidade;
end

-- Descontinuando o atribugo estado, que será substituido pelo objeto unidade federativa
if col_length('consignado', 'estado') is not null
begin
	ALTER TABLE consignado DROP COLUMN estado;
end