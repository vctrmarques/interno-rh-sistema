--Criação da tabela conta_contabil.
--Davi Queiroz


IF NOT EXISTS(SELECT * FROM SYS.TABLES WHERE name = 'conta_contabil')
CREATE TABLE conta_contabil (
	id bigint NOT NULL IDENTITY(1,1),
	empresa_id bigint,
	filial_id bigint,
	centro_custo_id bigint,
	tipo_conta varchar(255) NOT NULL,
	conta INT NOT NULL,
	rateio FLOAT NOT NULL,
	rateio_total FLOAT NOT NULL,
	created_at datetime2(7) NOT NULL,
	updated_at datetime2(7) NOT NULL,
	created_by bigint,
	updated_by bigint,
	CONSTRAINT pk_conta_contabil PRIMARY KEY (id),
	CONSTRAINT fk_conta_contabil_empresa_id FOREIGN KEY (empresa_id) REFERENCES empresa_filial(id),
	CONSTRAINT fk_conta_contabil_filial_id FOREIGN KEY (filial_id) REFERENCES empresa_filial(id),
	CONSTRAINT fk_conta_contabil_centro_custo_id FOREIGN KEY (centro_custo_id) REFERENCES centro_custo(id)
	
) 

CREATE TABLE conta_contabil_lotacao (
	conta_contabil_id bigint NOT NULL,
	lotacao_id bigint NOT NULL,
	CONSTRAINT pk_conta_contabil_lotacao PRIMARY KEY (conta_contabil_id,lotacao_id),
	CONSTRAINT fk_conta_contabil_lotacao_conta_contabil_id FOREIGN KEY (conta_contabil_id) REFERENCES conta_contabil(id),
	CONSTRAINT fk_conta_contabil_lotacao_conta_lotacao_id FOREIGN KEY (lotacao_id) REFERENCES lotacao(id) 
) ;