--Criação da tabela tipo_contrato
--Marconi Motta

IF NOT EXISTS(SELECT * FROM SYS.TABLES WHERE name = 'tipo_contrato')
CREATE TABLE tipo_contrato (
	id bigint NOT NULL IDENTITY(1,1),
	created_at datetime2(7) NOT NULL,
	updated_at datetime2(7) NOT NULL,
	created_by bigint,
	updated_by bigint,
	nome varchar(255) NOT NULL,
	CONSTRAINT pk_tipo_contrato PRIMARY KEY (id)
) 
CREATE UNIQUE INDEX uk_tipo_contrato_nome ON tipo_contrato (nome);