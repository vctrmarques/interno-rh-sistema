-- João Marques

IF NOT EXISTS(SELECT * FROM SYS.TABLES WHERE name = 'agenda_medica')
CREATE TABLE agenda_medica
(
    id 							BIGINT 					 	NOT NULL IDENTITY,
    medico_id					BIGINT      			    NOT NULL,
	data_inicio                 DATE			 			NOT NULL,
	data_fim                 	DATE			 			NOT NULL,
	hora_inicial				TIME	 					NOT NULL,
	hora_final					TIME	 					NOT NULL,
	intervalo 					INT 						NOT NULL,	
	created_at 					datetime2(7)	 			NOT NULL,
	updated_at 					datetime2(7) 				NOT NULL,
	created_by 					BIGINT						NULL,
	updated_by 					BIGINT						NULL,
	
	CONSTRAINT pk_agenda_medica PRIMARY KEY (id),
	CONSTRAINT fk_agenda_medica_medico FOREIGN KEY (medico_id) REFERENCES medico(id)
)

IF NOT EXISTS(SELECT * FROM SYS.TABLES WHERE name = 'agenda_medica_data')
CREATE TABLE agenda_medica_data
(
    id							BIGINT				        NOT NULL IDENTITY,
    agenda_medica_id			BIGINT				        NOT NULL,
	dia_semana					VARCHAR(20)			        NOT NULL,
	horario 					TIME						NOT NULL,
	ano							INT                      	NOT NULL,
	mes							VARCHAR(10)			        NOT NULL
	
	CONSTRAINT pk_agenda_medica_data PRIMARY KEY (id),
	CONSTRAINT fk_agenda_medica_data_agenda_medica FOREIGN KEY (agenda_medica_id) REFERENCES agenda_medica(id)
)

IF NOT EXISTS(SELECT * FROM SYS.TABLES WHERE name = 'agenda_medica_especialidade_medica')
CREATE TABLE agenda_medica_especialidade_medica
(
    agenda_medica_id			BIGINT				        NOT NULL,
    especialidade_medica_id		BIGINT				        NOT NULL,
	
	CONSTRAINT fk_agenda_medica_especialidade_medica_agenda_medica_id FOREIGN KEY (agenda_medica_id) REFERENCES agenda_medica(id),
	CONSTRAINT fk_agenda_medica_especialidade_medica_id FOREIGN KEY (especialidade_medica_id) REFERENCES especialidade_medica(id)
)

-- insert do sub-menu Índices no menu Arrecadação
IF NOT EXISTS(SELECT * FROM MENU WHERE url = '/agendaMedica/gestao')
	INSERT INTO menu (nome, ativo, categoria, url) VALUES('Agenda Médica', 1, 'JUNTA_MEDICA', '/agendaMedica/gestao');

IF EXISTS(SELECT * FROM menu WHERE url = '/agendaMedica/gestao')
BEGIN
DECLARE
    @menuId AS VARCHAR(1000)

	SELECT @menuId = CAST(id AS VARCHAR(1000)) FROM menu WHERE nome = 'Agenda Médica'
	
	IF NOT EXISTS(SELECT * FROM papel WHERE nome = 'ROLE_AGENDA_MEDICA_GESTAO')
		INSERT INTO papel (nome, id_menu) VALUES ('ROLE_AGENDA_MEDICA_GESTAO', @menuId)
	
	IF NOT EXISTS(SELECT * FROM papel WHERE nome = 'ROLE_AGENDA_MEDICA_CADASTRAR')
		INSERT INTO papel (nome, id_menu) VALUES ('ROLE_AGENDA_MEDICA_CADASTRAR', @menuId)
		
	IF NOT EXISTS(SELECT * FROM papel WHERE nome = 'ROLE_AGENDA_MEDICA_ATUALIZAR')
		INSERT INTO papel (nome, id_menu) VALUES ('ROLE_AGENDA_MEDICA_ATUALIZAR', @menuId)
		
	IF NOT EXISTS(SELECT * FROM papel WHERE nome = 'ROLE_AGENDA_MEDICA_EXCLUIR')
		INSERT INTO papel (nome, id_menu) VALUES ('ROLE_AGENDA_MEDICA_EXCLUIR', @menuId)
		
	IF NOT EXISTS(SELECT * FROM papel WHERE nome = 'ROLE_AGENDA_MEDICA_VISUALIZAR')
		INSERT INTO papel (nome, id_menu) VALUES ('ROLE_AGENDA_MEDICA_VISUALIZAR', @menuId)
		
END
