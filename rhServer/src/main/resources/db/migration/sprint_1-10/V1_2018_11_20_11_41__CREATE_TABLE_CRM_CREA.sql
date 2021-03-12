--Criação da tabela crm crea.
--Flávio Silva

CREATE TABLE crm_crea (
	id bigint NOT NULL IDENTITY(1,1),
	created_at datetime2(7) NOT NULL,
	updated_at datetime2(7) NOT NULL,
	created_by bigint,
	updated_by bigint,
	coordenador_pcmso bit,
	nome_conveniado varchar(255) NOT NULL,
	numero_crm_crea varchar(255) NOT NULL,
	responsavel_ltcat bit,
	CONSTRAINT pk_crm_crea PRIMARY KEY (id)
) 
CREATE UNIQUE INDEX uk_crm_crea_nome_conveniado ON crm_crea (nome_conveniado) 
CREATE UNIQUE INDEX uk_crm_crea_nome_numero_crm_crea ON crm_crea (numero_crm_crea) ;


CREATE TABLE crm_crea_convenio (
	crm_crea_id bigint NOT NULL,
	convenio_id bigint NOT NULL,
	CONSTRAINT pk_crm_crea_convenio PRIMARY KEY (crm_crea_id,convenio_id),
	CONSTRAINT fk_crm_crea_convenio_convenio_id FOREIGN KEY (convenio_id) REFERENCES convenio(id) ,
	CONSTRAINT fk_crm_crea_convenio_crm_crea_id FOREIGN KEY (crm_crea_id) REFERENCES crm_crea(id) 
) ;

