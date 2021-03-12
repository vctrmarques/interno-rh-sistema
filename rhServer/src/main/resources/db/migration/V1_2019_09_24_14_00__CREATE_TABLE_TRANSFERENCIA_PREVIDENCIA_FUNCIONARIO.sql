--Criação da tabela transferencia_previdencia_funcionario. 
--Eduardo Costa.

IF NOT EXISTS(SELECT * FROM SYS.TABLES WHERE name = 'transferencia_previdencia_funcionario')
create table transferencia_previdencia_funcionario (
    id bigint identity(1,1) not null,
    funcionario_id bigint not null,
    transferencia_id bigint not null,
    motivo_aposentadoria bigint not null,
    historico_situacao_funcional_id bigint not null,
    data_solicitacao datetime2(7) not null,
    data_aposentadoria datetime2(7) not null,
    tipo_proporcao varchar(80) not null,
    proporcao int,
    processo varchar(80) not null,
    observacao varchar(255),
    created_at datetime2(7) NOT NULL,
	updated_at datetime2(7) NOT NULL,
	created_by bigint,
	updated_by bigint,
    CONSTRAINT pk_transferencia_previdencia_funcionario PRIMARY KEY (id),
	CONSTRAINT fk_tpf_funcionario_id FOREIGN KEY (funcionario_id) REFERENCES funcionario(id),
	CONSTRAINT fk_tpf_transferencia_funcionario_id FOREIGN KEY (transferencia_id) REFERENCES transferencia_funcionario(id), 
    CONSTRAINT fk_tpf_motivo_aposentadoria_id FOREIGN KEY (motivo_aposentadoria) REFERENCES tipo_aposentadoria(id),
    CONSTRAINT fk_tpf_historico_situacao_funcional_id FOREIGN KEY (historico_situacao_funcional_id) REFERENCES historico_situacao_funcional(id)
)