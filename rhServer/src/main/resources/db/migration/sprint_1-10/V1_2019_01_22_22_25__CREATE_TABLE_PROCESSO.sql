-- Criação da tabela de processo
-- Railson Silva

IF NOT EXISTS (SELECT * FROM SYS.TABLES WHERE name = 'processo')
CREATE TABLE processo (
	id bigint NOT NULL IDENTITY(1,1),
	funcionario_id bigint not null,
	numero_processo varchar(255) not null,
	assunto varchar(255) not null,
	data_inicio datetime2(7) NOT NULL,
	data_fim datetime2(7),
	requerente varchar(255),
	situacao varchar(255),
	ato_portaria bigint,
	data_ato datetime2(7),
	doe varchar(255),
	data_doe datetime2(7),
	impacto_financeiro bit not null,
	tipo_reflexo varchar(255),
	created_at datetime2(7) NOT NULL,
	updated_at datetime2(7) NOT NULL,
	created_by bigint,
	updated_by bigint,
	inicio_impacto_financeiro datetime2(7),
	fim_impacto_financeiro datetime2(7),
	CONSTRAINT pk_processo PRIMARY KEY (id),
	CONSTRAINT fk_processo_funcionaio foreign key (funcionario_id) REFERENCES funcionario(id),
) 

