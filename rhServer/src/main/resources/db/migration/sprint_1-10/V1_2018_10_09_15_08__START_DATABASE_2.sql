
-- Criação da tabela papel

CREATE TABLE papel (
	id bigint NOT NULL IDENTITY(1,1),
	nome varchar(60),
	id_menu bigint,
	CONSTRAINT pk_papel PRIMARY KEY (id),
	CONSTRAINT fk_papel_id_menu FOREIGN KEY (id_menu) REFERENCES menu(id) 
) ;


-- Criação da tabela usuario_papel

CREATE TABLE usuario_papel (
	usuario_id bigint NOT NULL,
	papel_id bigint NOT NULL,
	CONSTRAINT pk_usuario_papel PRIMARY KEY (usuario_id,papel_id),
	CONSTRAINT fk_usuario_papel_papel_id FOREIGN KEY (papel_id) REFERENCES papel(id) ,
	CONSTRAINT fk_usuario_papel_usuario_id FOREIGN KEY (usuario_id) REFERENCES usuario(id) 
) ;



