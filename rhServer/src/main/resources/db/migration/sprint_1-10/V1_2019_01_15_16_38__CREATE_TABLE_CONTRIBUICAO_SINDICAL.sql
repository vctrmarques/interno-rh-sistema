-- Marconi Motta

-- Criação da tabela de Contribuição Sindica e Menu

CREATE TABLE contribuicao_sindical (
	id bigint NOT NULL IDENTITY(1,1),
	created_at datetime2(7) NOT NULL,
	updated_at datetime2(7) NOT NULL,
	created_by bigint,
	updated_by bigint,
	funcionario_id bigint NOT NULL,
	sindicato_id bigint NOT NULL,
	permite_desconto bit default 0,
	anexo_id bigint NULL,
	ano int NOT NULL,
	CONSTRAINT pk_funcionario_contribuicao_sindical PRIMARY KEY (id),
	CONSTRAINT fk_contribuicao_sindical_funcionario_id FOREIGN KEY (funcionario_id) REFERENCES funcionario(id),
	CONSTRAINT fk_contribuicao_sindical_sindicato_id FOREIGN KEY (sindicato_id) REFERENCES sindicato(id),
	CONSTRAINT fk_contribuicao_sindical_anexo_id FOREIGN KEY (anexo_id) REFERENCES anexo(id)
)

-- Marconi Motta

-- insert do menu Contribuição Sindical

INSERT INTO menu VALUES(SYSDATETIME(), SYSDATETIME(), 1, 1, 1, 'MODULO_RH', 'Contribuição Sindical', '/contribuicaoSindical/gestao');

