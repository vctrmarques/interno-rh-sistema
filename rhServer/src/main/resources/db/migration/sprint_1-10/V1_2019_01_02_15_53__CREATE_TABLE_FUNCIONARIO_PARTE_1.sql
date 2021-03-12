-- Davi Queiroz

-- Criação da tabela de Cargos e Menu

CREATE TABLE funcionario (
	id bigint NOT NULL IDENTITY(1,1),
	created_at datetime2(7) NOT NULL,
	updated_at datetime2(7) NOT NULL,
	created_by bigint,
	updated_by bigint,
	nome varchar(255) NOT NULL,
	matricula bigint NOT NULL,
	empresa_id bigint NOT NULL,
	filial_id bigint NOT NULL,
	sexo varchar(30) NOT NULL,
	estado_civil varchar(30),
	grau_instrucao varchar(30),
	CONSTRAINT pk_funcionario PRIMARY KEY (id),
	CONSTRAINT fk_funcionario_empresa_id FOREIGN KEY (empresa_id) REFERENCES empresa_filial(id),
	CONSTRAINT fk_funcionario_filial_id FOREIGN KEY (filial_id) REFERENCES empresa_filial(id),
)
CREATE UNIQUE INDEX uk_funcionario_nome ON funcionario (nome);
CREATE UNIQUE INDEX uk_funcionario_matricula ON funcionario (matricula);

