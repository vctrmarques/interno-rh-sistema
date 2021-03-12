--Criação da tabela ESOCIAL
--Marconi Motta

CREATE TABLE esocial (
	id bigint NOT NULL IDENTITY(1,1),
	created_at datetime2(7) NOT NULL,
	updated_at datetime2(7) NOT NULL,
	created_by bigint,
	updated_by bigint,
	descricao varchar(255) NOT NULL,
	CONSTRAINT pk_esocial PRIMARY KEY (id)
) 
CREATE UNIQUE INDEX uk_esocial_descricao ON esocial (descricao) 
