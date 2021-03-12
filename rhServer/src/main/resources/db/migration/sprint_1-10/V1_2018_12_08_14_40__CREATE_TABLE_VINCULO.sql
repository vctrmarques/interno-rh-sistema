-- Criação da tabela de vinculo

-- Flávio Silva

CREATE TABLE vinculo (
	id bigint NOT NULL IDENTITY(1,1),
	created_at datetime2(7) NOT NULL,
	updated_at datetime2(7) NOT NULL,
	created_by bigint,
	updated_by bigint,
	data_final datetime2(7),
	data_inicial datetime2(7),
	descricao varchar(255) NOT NULL,
	grupo varchar(255) NOT NULL,
	categoria_esocial_id bigint,
	categoria_sefip_id bigint,
	ocorrencia_sefip_id bigint,
	situacao_inicial_afastamento_id bigint,
	tipo_contrato_id bigint,
	vinculo_apos_id bigint,
	CONSTRAINT pk_vinculo PRIMARY KEY (id),
	CONSTRAINT fk_vinculo_categoria_sefip_id FOREIGN KEY (categoria_sefip_id) REFERENCES sefip(id) ,
	CONSTRAINT fk_vinculo_tipo_contrato_id FOREIGN KEY (tipo_contrato_id) REFERENCES tipo_contrato(id) ,
	CONSTRAINT fk_vinculo_ocorrencia_sefip_id FOREIGN KEY (ocorrencia_sefip_id) REFERENCES sefip(id) ,
	CONSTRAINT fk_vinculo_categoria_esocial_id FOREIGN KEY (categoria_esocial_id) REFERENCES esocial(id) ,
	CONSTRAINT fk_vinculo_vinculo_apos_id FOREIGN KEY (vinculo_apos_id) REFERENCES vinculo(id) ,
	CONSTRAINT fk_vinculo_situacao_inicial_afastamento_id FOREIGN KEY (situacao_inicial_afastamento_id) REFERENCES afastamento(id) 
) 
CREATE UNIQUE INDEX uk_fk_vinculo_descricao ON vinculo (descricao) ;
