--Criação da tabela legislacao
--Flávio Silva

CREATE TABLE legislacao (

	id bigint NOT NULL IDENTITY(1,1),
	
	created_at datetime2(7) NOT NULL,
	updated_at datetime2(7) NOT NULL,
	created_by bigint,
	updated_by bigint,
	
	inicio_efeito_financeiro datetime2(7) NOT NULL,
	publicacao datetime2(7) NOT NULL,
	inicio_vigencia datetime2(7) NOT NULL,
	fim_vigencia datetime2(7) NULL,
	
	ente_federado_id BIGINT NOT NULL,
	norma_id BIGINT NOT NULL,
	detalhamento_norma_id BIGINT NOT NULL,
	assunto_norma_id BIGINT NOT NULL,
	unidade_gestora_id BIGINT NOT NULL,
	pessoal_legislacao_id BIGINT NULL,
	
	numero_norma int NOT NULL,
	ano_norma int NOT NULL,
	ementa_norma varchar(2000) NOT NULL,
	
	CONSTRAINT pk_legislacao PRIMARY KEY (id),
	CONSTRAINT fk_legislacao_ente_federado_id FOREIGN KEY (ente_federado_id) REFERENCES ente_federado(id),
	CONSTRAINT fk_legislacao_norma_id FOREIGN KEY (norma_id) REFERENCES norma(id),
	CONSTRAINT fk_legislacao_detalhamento_norma_id FOREIGN KEY (detalhamento_norma_id) REFERENCES detalhamento_norma(id),
	CONSTRAINT fk_legislacao_assunto_norma_id FOREIGN KEY (assunto_norma_id) REFERENCES assunto_norma(id),
	CONSTRAINT fk_legislacao_unidade_gestora_id FOREIGN KEY (unidade_gestora_id) REFERENCES unidade_gestora(id),
	CONSTRAINT fk_legislacao_pessoal_legislacao_id_id FOREIGN KEY (pessoal_legislacao_id) REFERENCES legislacao(id)
) 
CREATE UNIQUE INDEX uk_legislacao_numero_norma ON legislacao (numero_norma) ;