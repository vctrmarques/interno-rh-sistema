-- Júlio Galvão

-- Criação da tabela de recisao_contrato

if not exists (select * from sys.tables where name = 'recisao_contrato')
create table recisao_contrato (
	
	id bigint not null identity primary key,
	funcionario_id bigint not null,
	data_admissao datetime2 not null,
	ferias_vencidas bit not null,
	aviso_previo bit not null,
	data_aviso datetime2,
	dias_aviso int,
	motivo_id bigint not null,
	data_desligamento datetime2 not null,
	data_homologacao datetime2,
	data_pagamento datetime2,
	status varchar(255) not null,
	created_at datetime2 not null,
	updated_at datetime2 not null,
	created_by bigint,
	updated_by bigint,
	
	constraint fk_recisao_contrato_funcionario foreign key (funcionario_id) references funcionario(id),
	constraint fk_recisao_contrato_motivo foreign key (motivo_id) references motivo(id)
	
)