--Flávio Silva
--Criação da tabela de agencia.

IF NOT EXISTS(SELECT * FROM SYS.TABLES WHERE name = 'agencia')
CREATE TABLE agencia (
	id bigint NOT NULL IDENTITY(1,1),
	created_at datetime2(7) NOT NULL,
	updated_at datetime2(7) NOT NULL,
	created_by bigint,
	updated_by bigint,
	bloqueado bit,
	digito int,
	nome varchar(255) NOT NULL,
	numero int NOT NULL,
	praca varchar(255) NOT NULL,
	referencia varchar(255),
	uf varchar(255) NOT NULL,
	banco_id bigint,
	CONSTRAINT pk_agencia PRIMARY KEY (id),
	CONSTRAINT fk_agencia_banco_id FOREIGN KEY (banco_id) REFERENCES banco(id) 
) ;
