-- Railson Silva
-- create table requisicao_pessoal

IF NOT EXISTS (SELECT * FROM SYS.TABLES WHERE name = 'requisicao_pessoal')
CREATE TABLE requisicao_pessoal (
	id bigint NOT NULL IDENTITY(1,1),
	solicitante_id bigint not null,
	justificativa varchar(255) not null,
	situacao varchar(25) not null,
	data_entrada datetime2(7) NOT NULL,
	data_limite datetime2(7) NOT NULL,
	funcionario_substituido_id bigint,
	motivo_solicitacao varchar(100) not null,
	data_prevista_adimissao datetime2(7) NOT NULL,
	created_at datetime2(7) NOT NULL,
	updated_at datetime2(7) NOT NULL,
	created_by bigint,
	updated_by bigint
	
	constraint requisicao_pessoal_pk primary key (id),
	constraint requisicao_pessoal_solicitante foreign key (solicitante_id) references funcionario(id),
	constraint requisicao_pessoal_substituto foreign key (funcionario_substituido_id) references funcionario(id)
)
