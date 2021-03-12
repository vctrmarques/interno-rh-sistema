IF COL_LENGTH('empresa_filial', 'pais_id') IS NULL
BEGIN
    ALTER TABLE empresa_filial
    ADD pais_id BIGINT NULL
    CONSTRAINT fk_empresa_filial_pais_id foreign key (pais_id) REFERENCES nacionalidade(id);
END