-- Railson Silva
-- create table requisicao_pessoal_funcao

IF NOT EXISTS (SELECT * FROM SYS.TABLES WHERE name = 'requisicao_pessoal_funcao')
CREATE TABLE requisicao_pessoal_funcao (
	id bigint NOT NULL IDENTITY(1,1),
	funcao_id bigint not null,
	tipo_contratacao varchar(25) not null,
	qtd_vagas int not null,
	custo_por_vaga float not null,
	turno_id bigint not null,
	requisicao_pessoal_id bigint not null,
	created_at datetime2(7) NOT NULL,
	updated_at datetime2(7) NOT NULL,
	created_by bigint,
	updated_by bigint
	
	constraint requisicao_pessoal_funcao_pk primary key (id),
	constraint requisicao_pessoal_funcao_requisicao_pessoal foreign key (requisicao_pessoal_id) references requisicao_pessoal(id),	
	constraint requisicao_pessoal_funcao_funcao foreign key (funcao_id) references funcao(id),	
	constraint requisicao_pessoal_funcaoturno foreign key (turno_id) references turno(id)	
)
