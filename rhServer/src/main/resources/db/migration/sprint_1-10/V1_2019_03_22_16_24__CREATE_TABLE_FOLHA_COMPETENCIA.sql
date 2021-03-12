-- Criação da tabela de Folha Competencia
-- Marconi Motta


IF NOT EXISTS (SELECT * FROM SYS.TABLES WHERE name = 'folha_competencia')
CREATE TABLE folha_competencia(
	id bigint not null identity(1,1),
	mes_competencia date,
	inicio_competencia datetime2(7) not null,
	fim_competencia datetime2(7),
	status varchar(255) not null,
	created_at datetime2(7) NOT NULL,
	updated_at datetime2(7) NOT NULL,
	created_by bigint,
	updated_by bigint,
	CONSTRAINT pk_folha_competencia PRIMARY KEY (id),
)