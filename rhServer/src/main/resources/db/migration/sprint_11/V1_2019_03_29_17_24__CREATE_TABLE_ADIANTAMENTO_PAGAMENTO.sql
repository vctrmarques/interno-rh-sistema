-- create table adiantamento_pagamento
-- Júlio Galvão

if not exists (select * from sys.tables where name = 'adiantamento_pagamento')
create table adiantamento_pagamento (
	id bigint not null identity primary key,
	funcionario_id bigint not null,
	empresa_filial_id bigint not null,
	lotacao_id bigint not null,
	tipo_adiantamento varchar(255) not null,
	recebimento varchar(255) not null,
	valor_adiantamento numeric(13,3) not null,
	percentual_adiantamento float not null,
	data_inicio datetime2 not null,
	data_fim datetime2,
	status varchar(255),
	created_at datetime2 not null,
	updated_at datetime2 not null,
	created_by bigint,
	updated_by bigint,
	
	constraint fk_adiantamento_pagamento_funcionario foreign key (funcionario_id) references funcionario(id),
	constraint fk_adiantamento_pagamento_empresa_filial foreign key (empresa_filial_id) references empresa_filial(id),
	constraint fk_adiantamento_pagamento_lotacao foreign key (lotacao_id) references lotacao(id)
);