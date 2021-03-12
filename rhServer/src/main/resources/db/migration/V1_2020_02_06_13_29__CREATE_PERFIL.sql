
-- Criação da tabela perfil
CREATE TABLE perfil (
	id bigint NOT NULL IDENTITY(1,1),
	nome varchar(255),
	created_at datetime2(7) NOT NULL,
	updated_at datetime2(7) NOT NULL,
	created_by bigint,
	updated_by bigint,
	ativo bit,
	CONSTRAINT pk_perfil PRIMARY KEY (id)
) ;

-- Criação da tabela perfil_papel
CREATE TABLE perfil_papel (
	perfil_id bigint NOT NULL,
	papel_id bigint NOT NULL,
	CONSTRAINT pk_perfil_papel PRIMARY KEY (perfil_id,papel_id),
	CONSTRAINT fk_perfil_papel_perfil_id FOREIGN KEY (perfil_id) REFERENCES perfil(id) ,
	CONSTRAINT fk_perfil_papel_papel_id FOREIGN KEY (papel_id) REFERENCES papel(id) 
) ;

-- Criação da tabela usuario_perfil
CREATE TABLE usuario_perfil (
	usuario_id bigint NOT NULL,
	perfil_id bigint NOT NULL,
	CONSTRAINT pk_usuario_perfil PRIMARY KEY (usuario_id,perfil_id),
	CONSTRAINT fk_usuario_perfil_usuario_id FOREIGN KEY (usuario_id) REFERENCES usuario(id) ,
	CONSTRAINT fk_usuario_perfil_perfil_id FOREIGN KEY (perfil_id) REFERENCES perfil(id) 
) ;

