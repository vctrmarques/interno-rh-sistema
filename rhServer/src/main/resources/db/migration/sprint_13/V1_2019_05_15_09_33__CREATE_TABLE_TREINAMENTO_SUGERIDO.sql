-- Railson Silva
-- create table treinamento_sugerido

IF NOT EXISTS (SELECT * FROM SYS.TABLES WHERE name = 'treinamento_sugerido')
CREATE TABLE treinamento_sugerido (

	id bigint NOT NULL IDENTITY(1,1),
	curso_id bigint not null,
	tipo varchar(15) not null,
	instrutor varchar(255) not null,
	motivo varchar(50) not null,
	local varchar(255) not null,
	funcionario_id bigint not null,
	data_inicio date not null,
	data_final date not null,
	situacao varchar(15) not null,
	informacoes_adicionais varchar(255),
	created_at datetime2(7) NOT NULL,
	updated_at datetime2(7) NOT NULL,
	created_by bigint,
	updated_by bigint
	
	constraint treinamento_sugerido_pk primary key (id),
	constraint treinamento_sugerido_curso foreign key (curso_id) references curso(id),	
	constraint treinamento_sugerido_funcionario foreign key (funcionario_id) references funcionario(id),	
)