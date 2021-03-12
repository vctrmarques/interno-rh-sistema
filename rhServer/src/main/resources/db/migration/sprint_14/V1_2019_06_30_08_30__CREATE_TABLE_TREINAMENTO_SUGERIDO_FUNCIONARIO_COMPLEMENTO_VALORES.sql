IF EXISTS (SELECT * FROM SYS.TABLES WHERE name = 'treinamento_sugerido')
DROP TABLE treinamento_sugerido

IF NOT EXISTS (SELECT * FROM SYS.TABLES WHERE name = 'treinamento_sugerido')
CREATE TABLE treinamento_sugerido (

	id bigint NOT NULL IDENTITY(1,1),
	curso_id bigint not null,
	tipo varchar(15) not null,
	instrutor varchar(255) not null,
	motivo varchar(50) not null,
	local varchar(255) not null,
	data_inicio date not null,
	data_final date not null,
	situacao varchar(15) not null,
	informacoes_adicionais varchar(255),
	turma varchar(255),
	justificativa varchar(255),
	resultado varchar(255),
	created_at datetime2(7) NOT NULL,
	updated_at datetime2(7) NOT NULL,
	created_by bigint,
	updated_by bigint,
	
	constraint treinamento_sugerido_pk primary key (id),
	constraint treinamento_sugerido_curso foreign key (curso_id) references curso(id)	
);


IF NOT EXISTS (SELECT * FROM SYS.TABLES WHERE name = 'treinamento_sugerido_funcionario')
CREATE TABLE treinamento_sugerido_funcionario(
	id bigint NOT NULL IDENTITY(1,1),
	id_funcionario bigint NOT NULL,
	id_treinamento_sugerido bigint NOT NULL,
	
	CONSTRAINT pk_tre_sug_funcionario PRIMARY KEY (id),
    CONSTRAINT fk_tre_sug_func_funcionario FOREIGN KEY (id_funcionario) REFERENCES funcionario(id),
    CONSTRAINT fk_tre_sug_func_treinamento_sugerido FOREIGN KEY (id_treinamento_sugerido) REFERENCES treinamento_sugerido(id)
);

IF NOT EXISTS (SELECT * FROM SYS.TABLES WHERE name = 'treinamento_sugerido_complemento')
CREATE TABLE treinamento_sugerido_complemento(
	id bigint NOT NULL IDENTITY(1,1),
	id_treinamento_sugerido bigint NOT NULL,
	local varchar(255),
	endereco varchar(255),
	complemento varchar(255),
	bairro varchar(255),
	id_municipio bigint NOT NULL,
	created_at datetime2(7) NOT NULL,
	updated_at datetime2(7) NOT NULL,
	created_by bigint,
	updated_by bigint,
	
	CONSTRAINT pk_tre_sug_complemento PRIMARY KEY (id),
    CONSTRAINT fk_tre_sug_comp_to_treinamento_sugerido FOREIGN KEY (id_treinamento_sugerido) REFERENCES treinamento_sugerido(id),
    CONSTRAINT fk_tre_sug_comp_to_municipio FOREIGN KEY (id_municipio) REFERENCES municipio(id)
);

IF NOT EXISTS (SELECT * FROM SYS.TABLES WHERE name = 'treinamento_sugerido_valores')
CREATE TABLE treinamento_sugerido_valores(
	id bigint NOT NULL IDENTITY(1,1),
	id_treinamento_sugerido bigint NOT NULL,
	valor_docencia FLOAT,
	valor_conducao FLOAT,
	valor_diarias FLOAT,
	valor_material FLOAT,
	valor_hospedagem FLOAT,
	valor_individual FLOAT,
	created_at datetime2(7) NOT NULL,
	updated_at datetime2(7) NOT NULL,
	created_by bigint,
	updated_by bigint,
	
	CONSTRAINT pk_tre_sug_valores PRIMARY KEY (id),
    CONSTRAINT fk_tre_sug_val_treinamento_sugerido FOREIGN KEY (id_treinamento_sugerido) REFERENCES treinamento_sugerido(id)
);