-- Criação da tabela de falta
-- Railson Silva


IF NOT EXISTS (SELECT * FROM SYS.TABLES WHERE name = 'falta')
CREATE TABLE falta(
	id bigint not null identity(1,1),
	funcionario_id bigint not null,
	data date,
	data_inicio datetime2(7) not null,
	data_retorno datetime2(7),
	motivo_afastamento_id bigint not null,
	afastamento_id bigint not null,
	diagnostico_medico varchar(255) not null,
	observacao_documento varchar(255),
	anexo_id bigint,
	created_at datetime2(7) NOT NULL,
	updated_at datetime2(7) NOT NULL,
	created_by bigint,
	updated_by bigint,
	CONSTRAINT pk_falta PRIMARY KEY (id),
	CONSTRAINT fk_falta_funcionario FOREIGN KEY (funcionario_id) REFERENCES funcionario(id),
	CONSTRAINT fk_falta_afastamento FOREIGN KEY (afastamento_id) REFERENCES afastamento(id),
	CONSTRAINT fk_falta_motivo_afastamento FOREIGN KEY (motivo_afastamento_id) REFERENCES motivo_afastamento(id),
	CONSTRAINT fk_falta_anexo FOREIGN KEY (anexo_id) REFERENCES anexo(id),
)
