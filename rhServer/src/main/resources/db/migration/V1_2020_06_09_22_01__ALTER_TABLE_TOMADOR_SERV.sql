-- Flávio Silva
-- Ajustes na tabela de tomador_servico.

-- Adição da coluna de unidade federativa
IF COL_LENGTH('tomador_servico', 'uf_id') IS NULL
BEGIN
    ALTER TABLE tomador_servico ADD uf_id BIGINT;
    ALTER TABLE tomador_servico ADD CONSTRAINT fk_tomador_servico_uf_id FOREIGN KEY (uf_id) REFERENCES unidade_federativa(id)
END

-- Adição da coluna de município 
IF COL_LENGTH('tomador_servico', 'municipio_id') IS NULL
BEGIN
    ALTER TABLE tomador_servico ADD municipio_id BIGINT;
    ALTER TABLE tomador_servico ADD CONSTRAINT fk_tomador_servico_municipio_id FOREIGN KEY (municipio_id) REFERENCES municipio(id)
END

-- Ajustando o tamanho de dados de cep
if col_length('tomador_servico', 'cep') is not null
begin
	ALTER TABLE tomador_servico ALTER COLUMN cep varchar(8) NOT NULL;
end

-- Ajustando o tamanho de dados de cep
if col_length('tomador_servico', 'complemento') is not null
begin
	ALTER TABLE tomador_servico ALTER COLUMN complemento varchar(255) NULL;
end

-- Descontinuando o atribugo cidade, que será substituido pelo objeto municipio
if col_length('tomador_servico', 'cidade') is not null
begin
	ALTER TABLE tomador_servico DROP COLUMN cidade;
end

-- Descontinuando o atribugo estado, que será substituido pelo objeto unidade federativa
if col_length('tomador_servico', 'estado') is not null
begin
	ALTER TABLE tomador_servico DROP COLUMN estado;
end