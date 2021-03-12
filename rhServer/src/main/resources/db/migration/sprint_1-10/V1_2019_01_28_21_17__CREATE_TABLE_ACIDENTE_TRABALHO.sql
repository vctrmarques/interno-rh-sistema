-- Criação da tabela de acidente de trabalho
-- FLávio Silva

CREATE TABLE acidente_trabalho (
	id bigint NOT NULL IDENTITY(1,1),
	created_at datetime2(7) NOT NULL,
	updated_at datetime2(7) NOT NULL,
	created_by bigint,
	updated_by bigint,
	aviso varchar(255) NOT NULL,
	causa varchar(255) NOT NULL,
	data_emissao_documento datetime2(7),
	data_hora_acidente datetime2(7) NOT NULL,
	data_previsa_volta datetime2(7) NOT NULL,
	dias_afastado int NOT NULL,
	documento_emitido bit,
	numero_cat int NOT NULL,
	resultado varchar(255),
	setor_local varchar(255) NOT NULL,
	id_anexo bigint,
	entidade_id bigint,
	funcionario_id bigint NOT NULL,
	tomador_servido_id bigint,
	CONSTRAINT pk_acidente_trabalho PRIMARY KEY (id),
	CONSTRAINT fk_acidente_trabalho_funcionario_id FOREIGN KEY (funcionario_id) REFERENCES funcionario(id) ,
	CONSTRAINT fk_acidente_trabalho_tomador_servido_id FOREIGN KEY (tomador_servido_id) REFERENCES tomador_servico(id) ,
	CONSTRAINT fk_acidente_trabalho_entidade_id FOREIGN KEY (entidade_id) REFERENCES tomador_servico(id) ,
	CONSTRAINT fk_acidente_trabalho_id_anexo FOREIGN KEY (id_anexo) REFERENCES anexo(id) 
) ;
