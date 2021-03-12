ALTER TABLE municipio DROP COLUMN uf;

ALTER TABLE municipio
    ADD id_unidade_federativa bigint,
    CONSTRAINT fk_unidade_federativa_municipio FOREIGN KEY (id_unidade_federativa) REFERENCES unidade_federativa(id);