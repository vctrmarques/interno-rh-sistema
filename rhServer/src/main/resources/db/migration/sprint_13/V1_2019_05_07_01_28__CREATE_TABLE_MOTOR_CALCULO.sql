-- Marconi Motta
-- create table motor_calculo_regra

IF NOT EXISTS (SELECT * FROM SYS.TABLES WHERE name = 'motor_calculo_regra')
CREATE TABLE motor_calculo_regra (
	id bigint NOT NULL IDENTITY(1,1),
	tipo_processamento_id bigint not null,
	verba_id bigint not null,
	descricao varchar(1000) not null,
	vigencia_inicial datetime2(7) NOT NULL,
	vigencia_final datetime2(7) NOT NULL,
	rotina varchar(max) not null,
	created_at datetime2(7) NOT NULL,
	updated_at datetime2(7) NOT NULL,
	created_by bigint,
	updated_by bigint
	
	constraint motor_calculo_regra_pk primary key (id),
	constraint motor_calculo_regra_tipo_processamento foreign key (tipo_processamento_id) references tipo_processamento(id),	
	constraint motor_calculo_regra_verba foreign key (verba_id) references verba(id),	
)