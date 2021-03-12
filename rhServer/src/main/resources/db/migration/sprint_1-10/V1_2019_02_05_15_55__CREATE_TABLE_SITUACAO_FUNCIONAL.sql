-- Davi Queiroz

-- Criação da tabela de Situação Funcional

IF NOT EXISTS (SELECT * FROM SYS.TABLES WHERE name = 'situacao_funcional')
CREATE TABLE situacao_funcional (
	
	id bigint NOT NULL IDENTITY(1,1),
	funcionario_id BIGINT NOT NULL,
	tipo_situacao_funcional VARCHAR(255) NOT NULL,
	funcao_id BIGINT,
	nivel_salarial_id BIGINT,
	motivo_id BIGINT,
	ato VARCHAR(255),
	dt_ato DATETIME2(7),
	dt_diario_oficial DATETIME2(7),
	dt_inicio DATETIME2(7),
	dt_fim DATETIME2(7),
	dt_retorno DATETIME2(7),
	obs_cancel VARCHAR(255),
	empresa_filial_id BIGINT,
	tipo_exoneracao_demissao VARCHAR(255),
	sit_motivo_afastamento_id BIGINT,
	created_by BIGINT,
	updated_by BIGINT,
	created_at DATETIME2(7) NOT NULL,
	updated_at DATETIME2(7) NOT NULL,
	
	CONSTRAINT pk_situacao_funcional PRIMARY KEY (id),
	CONSTRAINT fk_situacao_funcional_funcionario_funcionario_id FOREIGN KEY (funcionario_id) REFERENCES funcionario(id),
	CONSTRAINT fk_situacao_funcional_funcao_funcao_id FOREIGN KEY (funcao_id) REFERENCES funcao(id),
	CONSTRAINT fk_situacao_funcional_nivel_salarial_nivel_salarial_id FOREIGN KEY (nivel_salarial_id) REFERENCES  nivel_salarial(id),
	CONSTRAINT fk_situacao_funcional_motivo_motivo_id FOREIGN KEY (motivo_id) REFERENCES motivo(id),
	CONSTRAINT fk_situacao_funcional_empresa_filial_empresa_filial_id FOREIGN KEY (empresa_filial_id) REFERENCES empresa_filial(id),
	CONSTRAINT fk_situacao_funcional_motivo_afastamento_sit_motivo_afastamento_id FOREIGN KEY (sit_motivo_afastamento_id) REFERENCES motivo_afastamento(id)

)