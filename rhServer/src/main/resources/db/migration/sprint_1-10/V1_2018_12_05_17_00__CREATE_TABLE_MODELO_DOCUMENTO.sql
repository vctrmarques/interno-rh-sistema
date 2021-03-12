--Criação da tabela MODELO DOCUMENTO
--Wallace Nascimento

CREATE TABLE modelo_documento (
	id bigint NOT NULL IDENTITY(1,1),
    descricao varchar(255) NOT NULL,
    conteudo  varchar(2000) NOT NULL,
	created_at datetime2(7) NOT NULL,
	updated_at datetime2(7) NOT NULL,
	created_by bigint,
	updated_by bigint,
	CONSTRAINT pk_modelo_documento PRIMARY KEY (id)
);
 
CREATE UNIQUE INDEX uk_modelo_documento_descricao ON modelo_documento (descricao);
