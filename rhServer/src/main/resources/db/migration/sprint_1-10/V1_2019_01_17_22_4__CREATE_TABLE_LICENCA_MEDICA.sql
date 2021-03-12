-- Criação da tabela de licenca_medica
-- Railson Silva

IF NOT EXISTS (SELECT * FROM SYS.TABLES WHERE name = 'licenca_medica')
CREATE TABLE licenca_medica (
	id bigint NOT NULL IDENTITY(1,1),
	created_at datetime2(7) NOT NULL,
	updated_at datetime2(7) NOT NULL,
	created_by bigint,
	updated_by bigint,
	funcionario_id bigint NOT NULL,
	crm_id bigint,
	afastamento_id bigint NOT NULL,
	motivo_afastamento_id bigint NOT NULL,
	data_inspecao datetime2(7) NOT NULL,
	periodo_inicial datetime2(7) NOT NULL,
	periodo_final datetime2(7),
	CONSTRAINT pk_licenca_medica PRIMARY KEY (id),
	CONSTRAINT fk_licenca_medica_funcionaio foreign key (funcionario_id) REFERENCES funcionario(id),
	CONSTRAINT fk_licenca_medica_crm foreign key (crm_id) REFERENCES crm_crea(id),
	CONSTRAINT fk_licenca_medica_afastamento foreign key (afastamento_id) REFERENCES afastamento(id),
	CONSTRAINT fk_licenca_medica_motivo_afastamento foreign key (motivo_afastamento_id) REFERENCES motivo_afastamento(id)
) 

