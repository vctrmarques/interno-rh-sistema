-- Criação da tabela da tabela pensao_alimenticia
-- Taylor Oliveira

IF NOT EXISTS (SELECT * FROM SYS.TABLES WHERE name = 'pensao_alimenticia')
create table pensao_alimenticia
(
	id bigint identity,
	created_at datetime2(7) NOT NULL,
	updated_at datetime2(7) NOT NULL,
	created_by bigint,
	updated_by bigint,
	funcionario_id bigint not null
		constraint pensao_alimenticia_funcionario_id_fk
			references funcionario,
	nome_alimentando varchar(500),
	nascimento_alimentando datetime2,
	tipo_pensao varchar(100),
	logradouro varchar(100),
	numero bigint,
	complemento varchar(500),
	bairro varchar(500),
	uf_id bigint
		constraint pensao_alimenticia_unidade_federativa_id_fk_2
			references unidade_federativa,
	municipio_id bigint
		constraint pensao_alimenticia_municipio_id_fk
			references municipio,
	cep bigint,
	rg bigint,
	orgao varchar(100),
	uf_documento_id bigint,
	cpf bigint,
	ddd bigint,
	numero_telefone bigint,
	responsavel_id bigint
		constraint pensao_alimenticia_responsavel_legal_id_fk
			references responsavel_legal,
	numero_processo_responsavel bigint,
	numero_processo_pagamento bigint,
	data_inicial datetime2,
	data_final datetime2,
	centro_custo_id bigint
		constraint pensao_alimenticia_centro_custo_id_fk
			references centro_custo,
	verba_id bigint
		constraint pensao_alimenticia_verba_id_fk
			references verba,
	data_inicial_pagamento datetime2,
	data_final_pagamento datetime2,
	tipo_conta varchar(100),
	banco_id bigint
		constraint pensao_alimenticia_banco_id_fk
			references banco,
	conta bigint,
	vencimento datetime2,
	tipo_desconto varchar(500),
	valor float,
	salario_familia bigint,
	incidencia_principal varchar(500),
	salario_13 bit default 0,
	ferias bit default 0,
	recisao bit default 0,
	agencia_id bigint,
	constraint pensao_alimenticia_agencia_id_fk
		foreign key (agencia_id) references agencia,
	constraint pensao_alimenticia_unidade_federativa_id_fk
		foreign key (uf_documento_id) references unidade_federativa
)
go

create unique index pensao_alimenticia_id_uindex
	on pensao_alimenticia (id)
go

alter table pensao_alimenticia
	add constraint pensao_alimenticia_pk
		primary key nonclustered (id)
go
