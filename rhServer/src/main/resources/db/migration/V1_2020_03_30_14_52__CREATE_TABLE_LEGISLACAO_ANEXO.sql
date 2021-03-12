--Criação da tabela legislacao_anexo
--Flávio Silva

CREATE TABLE legislacao_anexo (

	id bigint NOT NULL IDENTITY(1,1),
	
	created_at datetime2(7) NOT NULL,
	updated_at datetime2(7) NOT NULL,
	created_by bigint,
	updated_by bigint,
	
	texto_documento_id BIGINT NOT NULL,
	anexo_id BIGINT NOT NULL,
	legislacao_id BIGINT NOT NULL,
	
	CONSTRAINT pk_legislacao_anexo PRIMARY KEY (id),
	CONSTRAINT fk_legislacao_anexo_texto_documento_id FOREIGN KEY (texto_documento_id) REFERENCES texto_documento(id),
	CONSTRAINT fk_legislacao_anexo_anexo_id FOREIGN KEY (anexo_id) REFERENCES anexo(id),
	CONSTRAINT fk_legislacao_anexo_legislacao_id FOREIGN KEY (legislacao_id) REFERENCES legislacao(id)
)