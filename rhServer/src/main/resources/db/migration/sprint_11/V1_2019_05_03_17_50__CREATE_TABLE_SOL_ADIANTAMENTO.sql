-- create table sol_adiantamento
-- Taylor Oliveira

if not exists (select * from sys.tables where name = 'sol_adiantamento')
create table sol_adiantamento (

	id bigint not null identity primary key,
	solicitante bigint not null
		constraint sol_adiantamento_funcionario_id_fk
			references funcionario,
	porcentagem_adiantamento varchar(100) not null,
	mes_adiantamento varchar(100) not null,
	situacao varchar(100) not null,	
	created_at datetime2 not null,
	updated_at datetime2 not null,
	created_by bigint,
	updated_by bigint	
);
