-- João Marques

IF NOT EXISTS(SELECT * FROM SYS.TABLES WHERE name = 'medico')
CREATE TABLE medico
(
    id 							BIGINT 					 	NOT NULL IDENTITY,
    matricula					INT				            NOT NULL,
    empresa_id					BIGINT				        NOT NULL,
    filial_id					BIGINT				        NOT NULL,
	nome 						VARCHAR(255) 				NOT NULL,
	status 						BIT				            NOT NULL,
	naturalidade_id				BIGINT				        NOT NULL,
	nacionalidade_id			BIGINT				        NOT NULL,
	estado_civil				VARCHAR(30) 				NOT NULL,
	sexo 						VARCHAR(30) 				NOT NULL,
	dt_nascimento 				datetime2(7)	            NOT NULL,
	nome_mae					VARCHAR(255) 				NOT NULL,
	nome_pai					VARCHAR(255) 				NULL,
	coordenador_medico			BIT				            NOT NULL,
	orgao_expeditor				VARCHAR(30) 				NOT NULL,
	rg							VARCHAR(20) 				NOT NULL,
	rg_uf_id					BIGINT				    	NOT NULL,
	dt_expedicao_rg 			datetime2(7)	 			NOT NULL,
	cpf							VARCHAR(11) 				NOT NULL,
	crm 						INT				            NOT NULL,
	dt_expedicao_crm 			datetime2(7)	 			NOT NULL,
	crm_uf_id 					BIGINT				        NULL,
	logradouro					VARCHAR(255) 				NOT NULL,
	numero					    VARCHAR(10) 				NOT NULL,
	complemento					VARCHAR(255) 				NULL,
	endereco_municipio_id		BIGINT				        NULL,	
	bairro 						VARCHAR(255)				NOT NULL,
	uf_id						BIGINT				    	NOT NULL,
	cep 						VARCHAR(8)				    NOT NULL,	
	created_at 					datetime2(7)	 			NOT NULL,
	updated_at 					datetime2(7) 				NOT NULL,
	created_by 					bigint						NULL,
	updated_by 					bigint						NULL,
	
	CONSTRAINT pk_medico PRIMARY KEY (id),
	CONSTRAINT fk_medico_empresa_id FOREIGN KEY (empresa_id) REFERENCES empresa_filial(id),
	CONSTRAINT fk_medico_filial_id FOREIGN KEY (filial_id) REFERENCES empresa_filial(id),
	CONSTRAINT fk_medico_naturalidade_id FOREIGN KEY (naturalidade_id) REFERENCES municipio(id),
	CONSTRAINT fk_medico_nacionalidade_id FOREIGN KEY (nacionalidade_id) REFERENCES nacionalidade(id),
	CONSTRAINT fk_medico_rg_uf_id FOREIGN KEY (rg_uf_id) REFERENCES unidade_federativa(id),
	CONSTRAINT fk_medico_crm_uf_id FOREIGN KEY (crm_uf_id) REFERENCES unidade_federativa(id),
	CONSTRAINT fk_medico_uf_id FOREIGN KEY (uf_id) REFERENCES unidade_federativa(id),
	CONSTRAINT fk_medico_endereco_municipio_id FOREIGN KEY (endereco_municipio_id) REFERENCES municipio(id)	
)
CREATE UNIQUE INDEX uk_medico_cpf ON medico (cpf);

IF NOT EXISTS(SELECT * FROM SYS.TABLES WHERE name = 'medico_telefone')
CREATE TABLE medico_telefone
(
    medico_id					BIGINT				        NOT NULL,
    telefone_id					BIGINT				        NOT NULL,
	
	CONSTRAINT fk_medico_telefone_medico_id FOREIGN KEY (medico_id) REFERENCES medico(id),
	CONSTRAINT fk_medico_telefone_id FOREIGN KEY (telefone_id) REFERENCES telefone(id)
)

IF NOT EXISTS(SELECT * FROM SYS.TABLES WHERE name = 'medico_especialidade_medica')
CREATE TABLE medico_especialidade_medica
(
    medico_id					BIGINT				        NOT NULL,
    especialidade_medica_id		BIGINT				        NOT NULL,
	
	CONSTRAINT fk_medico_especialidade_medica_medico_id FOREIGN KEY (medico_id) REFERENCES medico(id),
	CONSTRAINT fk_medico_especialidade_medica_id FOREIGN KEY (especialidade_medica_id) REFERENCES especialidade_medica(id)
)

IF NOT EXISTS(SELECT * FROM SYS.TABLES WHERE name = 'medico_email')
CREATE TABLE medico_email
(
	medico_id					BIGINT				        NOT NULL,
	email						VARCHAR(255)				NOT NULL,
	
	CONSTRAINT fk_medico_email_medico_id FOREIGN KEY (medico_id) REFERENCES medico(id)
)




