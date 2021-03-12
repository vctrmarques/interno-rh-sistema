-- Criação da tabela de transferencia_funcionario
-- Taylor Oliveira

IF NOT EXISTS (SELECT * FROM SYS.TABLES WHERE name = 'transferencia_funcionario')
create table transferencia_funcionario
(
	id bigint identity,
	created_at datetime2(7) NOT NULL,
	updated_at datetime2(7) NOT NULL,
	created_by bigint,
	updated_by bigint,
	funcionario_id bigint not null
		constraint transferencia_funcionario_funcionario_id_fk
			references funcionario,
	lotacao_id bigint
		constraint transferencia_funcionario_lotacao_id_fk
			references lotacao,
	empresa_id bigint
		constraint transferencia_funcionario_empresa_filial_id_fk
			references empresa_filial
)
go

create unique index transferencia_funcionario_id_uindex
	on transferencia_funcionario (id)
go

alter table transferencia_funcionario
	add constraint transferencia_funcionario_pk
		primary key nonclustered (id)
go

