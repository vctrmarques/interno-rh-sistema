-- João Marques
CREATE TABLE prontuario_pericia_medica
(
    id							BIGINT				        NOT NULL IDENTITY,
	paciente_pericia_medica_id	BIGINT      			    NOT NULL,
	consulta_pericia_medica_id	BIGINT      			    NOT NULL,
    medico_id					BIGINT      			    NOT NULL,
	especialidade_medica_id		BIGINT 						NOT NULL,
	numero_pericia	            INT			 				NOT NULL,
	ano_pericia					INT                      	NOT NULL,
	motivo_pericia				VARCHAR(255) 				NOT NULL,
    cid                      	VARCHAR(20)		 			NOT NULL,
	hda                      	VARCHAR(20)		 			NOT NULL,
	exame_fisico				VARCHAR(255)		 		NOT NULL,
	diagnostico					VARCHAR(255)		 		NOT NULL,
	acao 						VARCHAR(100)		 		NOT NULL,
	data_proxima_pericia        DATE			 			NULL,
	dias_proxima_consulta		INT                      	NULL,
	observacao_medico			VARCHAR(255) 				NULL,
	laudo						BIT 						NOT NULL,
	created_at 					datetime2(7)	 			NOT NULL,
	updated_at 					datetime2(7) 				NOT NULL,
	created_by 					BIGINT						NULL,
	updated_by 					BIGINT						NULL,
	
	CONSTRAINT pk_prontuario_pericia_medica PRIMARY KEY (id),
	CONSTRAINT fk_prontuario_pericia_medica_paciente_pericia_medica_id FOREIGN KEY (paciente_pericia_medica_id) REFERENCES paciente_pericia_medica(id),
	CONSTRAINT fk_prontuario_pericia_medica_consulta_pericia_medica_id FOREIGN KEY (consulta_pericia_medica_id) REFERENCES consulta_pericia_medica(id),
	CONSTRAINT fk_prontuario_pericia_medica_medico FOREIGN KEY (medico_id) REFERENCES medico(id),
	CONSTRAINT fk_prontuario_pericia_medica_especialidade_medica_id FOREIGN KEY (especialidade_medica_id) REFERENCES especialidade_medica(id)
)

-- insert do sub-menu Prontuario Perícia Médica no menu Junta Médica
IF NOT EXISTS(SELECT * FROM MENU WHERE url = '/prontuarioPericiaMedica/gestao')
	INSERT INTO menu (nome, ativo, categoria, url) VALUES('Prontuário Perícia Médica', 1, 'JUNTA_MEDICA', '/prontuarioPericiaMedica/gestao');

IF EXISTS(SELECT * FROM menu WHERE url = '/prontuarioPericiaMedica/gestao')
BEGIN
DECLARE
    @menuId AS VARCHAR(1000)

	SELECT @menuId = CAST(id AS VARCHAR(1000)) FROM menu WHERE nome = 'Prontuário Perícia Médica'
	
	IF NOT EXISTS(SELECT * FROM papel WHERE nome = 'ROLE_PRONTUARIO_PERICIA_MEDICA_GESTAO')
		INSERT INTO papel (nome, id_menu) VALUES ('ROLE_PRONTUARIO_PERICIA_MEDICA_GESTAO', @menuId)
	
	IF NOT EXISTS(SELECT * FROM papel WHERE nome = 'ROLE_PRONTUARIO_PERICIA_MEDICA_CADASTRAR')
		INSERT INTO papel (nome, id_menu) VALUES ('ROLE_PRONTUARIO_PERICIA_MEDICA_CADASTRAR', @menuId)
		
	IF NOT EXISTS(SELECT * FROM papel WHERE nome = 'ROLE_PRONTUARIO_PERICIA_MEDICA_ATUALIZAR')
		INSERT INTO papel (nome, id_menu) VALUES ('ROLE_PRONTUARIO_PERICIA_MEDICA_ATUALIZAR', @menuId)
		
	IF NOT EXISTS(SELECT * FROM papel WHERE nome = 'ROLE_PRONTUARIO_PERICIA_MEDICA_EXCLUIR')
		INSERT INTO papel (nome, id_menu) VALUES ('ROLE_PRONTUARIO_PERICIA_MEDICA_EXCLUIR', @menuId)
		
	IF NOT EXISTS(SELECT * FROM papel WHERE nome = 'ROLE_PRONTUARIO_PERICIA_MEDICA_VISUALIZAR')
		INSERT INTO papel (nome, id_menu) VALUES ('ROLE_PRONTUARIO_PERICIA_MEDICA_VISUALIZAR', @menuId)
		
END