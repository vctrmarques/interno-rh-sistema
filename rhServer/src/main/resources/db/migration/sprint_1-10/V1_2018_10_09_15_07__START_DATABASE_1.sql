-- Flávio Barbosa - Script base.


-- Criação da tabela anexo

CREATE TABLE anexo (
	id bigint NOT NULL IDENTITY(1,1),
	created_at datetime2(7) NOT NULL,
	updated_at datetime2(7) NOT NULL,
	created_by bigint,
	updated_by bigint,
	description varchar(255),
	file_download_uri varchar(255),
	file_name varchar(255),
	file_type varchar(255) NOT NULL,
	size bigint NOT NULL,
	CONSTRAINT pk_anexo PRIMARY KEY (id)
) ;

-- Criação da tabela menu

CREATE TABLE menu (
	id bigint NOT NULL IDENTITY(1,1),
	created_at datetime2(7) NOT NULL,
	updated_at datetime2(7) NOT NULL,
	created_by bigint,
	updated_by bigint,
	ativo bit,
	categoria varchar(60),
	nome varchar(255) NOT NULL,
	CONSTRAINT pk_menu PRIMARY KEY (id)
) 
CREATE UNIQUE INDEX uk_menu_nome ON menu (nome) ;


-- Criação da tabela usuario

CREATE TABLE usuario (
	id bigint NOT NULL IDENTITY(1,1),
	created_at datetime2(7) NOT NULL,
	updated_at datetime2(7) NOT NULL,
	created_by bigint,
	updated_by bigint,
	ativo bit,
	cpf varchar(11),
	email varchar(255) NULL,
	login varchar(15),
	nome varchar(40),
	senha varchar(100),
	id_anexo bigint,
	CONSTRAINT pk_usuario PRIMARY KEY (id),
	CONSTRAINT fk_usuario_id_anexo FOREIGN KEY (id_anexo) REFERENCES anexo(id) 
) 
CREATE UNIQUE INDEX uk_usuario_login ON usuario (login) ;


-- Criação da tabela notificacao

CREATE TABLE notificacao (
	id bigint NOT NULL IDENTITY(1,1),
	created_at datetime2(7) NOT NULL,
	updated_at datetime2(7) NOT NULL,
	created_by bigint,
	updated_by bigint,
	descricao varchar(255) NOT NULL,
	envia_email bit,
	id_entidade bigint,
	tipo_notificacao varchar(100) NOT NULL,
	visualizada bit,
	id_usuario_destinatario bigint,
	CONSTRAINT pk_notificacao PRIMARY KEY (id),
	CONSTRAINT fk_notificacao_id_usuario_destinatario FOREIGN KEY (id_usuario_destinatario) REFERENCES usuario(id) 
) ;




