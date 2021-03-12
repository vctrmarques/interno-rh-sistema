-- Flavio Silva

-- Criação da tabela de Cargos e Menu

INSERT INTO menu VALUES(SYSDATETIME(), SYSDATETIME(), 1, 1, 1, 'GESTAO', 'Cargos');

CREATE TABLE cargo (
	id bigint NOT NULL IDENTITY(1,1),
	created_at datetime2(7) NOT NULL,
	updated_at datetime2(7) NOT NULL,
	created_by bigint,
	updated_by bigint,
	descricao varchar(255),
	extinto_em datetime2(7),
	nome varchar(255) NOT NULL,
	CONSTRAINT pk_cargo PRIMARY KEY (id)
)
CREATE UNIQUE INDEX uk_cargo_nome ON cargo (nome);

