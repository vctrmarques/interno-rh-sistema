IF NOT EXISTS (SELECT * FROM SYS.TABLES WHERE name = 'tipo_folha_verbas')
CREATE TABLE tipo_folha_verbas(
	id bigint NOT NULL IDENTITY(1,1),
	id_tipo_folha bigint NOT NULL,
	id_verba bigint NOT NULL,
	
	CONSTRAINT pk_tipo_folha_verbas PRIMARY KEY (id),
    CONSTRAINT fk_tipo_folha_verbas_tipo_folha FOREIGN KEY (id_tipo_folha) REFERENCES tipo_folha(id),
    CONSTRAINT fk_tipo_folha_verbas_verba FOREIGN KEY (id_verba) REFERENCES verba(id)
);