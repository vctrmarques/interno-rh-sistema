-- Criação da tabela avaliacao_desempenho
-- Lucas Moura

if not exists (select * from sys.tables where name = 'avaliacao_desempenho')
	BEGIN
		create table avaliacao_desempenho (
			id bigint not null identity primary key,
			cod_avaliacao varchar(255) not null,
			nome varchar(255) not null,
			modelo varchar(50) not null,
			empresa_filial_id bigint not null,
			created_at datetime2 not null,
			updated_at datetime2 not null,
			created_by bigint,
			updated_by bigint,
			
			constraint fk_avaliacao_desempenho_empresa_filial foreign key (empresa_filial_id) references empresa_filial(id)
		
		)
		
		create table avaliacao_lotacao (
			avaliacao_id bigint not null,
			lotacao_id bigint not null,
			
			constraint fk_avaliacao_lotacao_avaliacao foreign key (avaliacao_id) references avaliacao_desempenho(id),
			constraint fk_avaliacao_lotacao_lotacao foreign key (lotacao_id) references lotacao(id)
		)
		
		create table avaliacao_cargo (
			avaliacao_id bigint not null,
			cargo_id bigint not null,
		
			constraint fk_avaliacao_cargo_avaliacao foreign key (avaliacao_id) references avaliacao_desempenho(id),
			constraint fk_avaliacao_cargo_cargo foreign key (cargo_id) references cargo(id)
		)
		
		create table avaliacao_funcao (
			avaliacao_id bigint not null,
			funcao_id bigint not null,
		
		constraint fk_avaliacao_funcao_avaliacao foreign key (avaliacao_id) references avaliacao_desempenho(id),
		constraint fk_avaliacao_funcao_funcao foreign key (funcao_id) references funcao(id)
		)
	END
;