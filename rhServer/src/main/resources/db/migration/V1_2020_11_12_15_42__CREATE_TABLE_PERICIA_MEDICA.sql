-- João Marques
IF NOT EXISTS(SELECT * FROM SYS.TABLES WHERE name = 'paciente_pericia_medica')
CREATE TABLE paciente_pericia_medica
(
    id 							BIGINT 					 	NOT NULL IDENTITY,
    funcionario_id				BIGINT      			    NULL,
	numero_processo             BIGINT			 			NULL,
	cid                      	VARCHAR(20)		 			NULL,
	cpf          				VARCHAR(11)	 				NOT NULL,
	nome     					VARCHAR(255) 				NOT NULL,
	tipo_analise				VARCHAR(30)					NOT NULL,	
	status						VARCHAR(20)					NOT NULL,
	
	CONSTRAINT pk_paciente_pericia_medica PRIMARY KEY (id),
	CONSTRAINT fk_paciente_pericia_medica_funcionario_id FOREIGN KEY (funcionario_id) REFERENCES funcionario(id)
)
CREATE UNIQUE INDEX uk_paciente_pericia_medica_numero_processo ON paciente_pericia_medica (numero_processo);
CREATE UNIQUE INDEX uk_paciente_pericia_medica_cpf ON paciente_pericia_medica (cpf);
CREATE UNIQUE INDEX uk_paciente_pericia_medica_nome ON paciente_pericia_medica (nome);

IF NOT EXISTS(SELECT * FROM SYS.TABLES WHERE name = 'consulta_pericia_medica')
CREATE TABLE consulta_pericia_medica
(
    id 							BIGINT 					 	NOT NULL IDENTITY,
    paciente_pericia_medica_id	BIGINT      			    NULL,
	agenda_medica_data_id       BIGINT			 			NULL,
	especialidade_medica_id		BIGINT 						NULL,
	compareceu     				BIT	 				        NOT NULL,
	observacao_medico			VARCHAR(255) 				NULL,
	observacao_assistente		VARCHAR(255)				NULL,
	created_at 					datetime2(7)	 			NOT NULL,
	updated_at 					datetime2(7) 				NOT NULL,
	created_by 					BIGINT						NULL,
	updated_by 					BIGINT						NULL,
	
	CONSTRAINT pk_consulta_pericia_medica PRIMARY KEY (id),
	CONSTRAINT fk_consulta_pericia_medica_paciente_pericia_medica_id FOREIGN KEY (paciente_pericia_medica_id) REFERENCES paciente_pericia_medica(id),
	CONSTRAINT fk_consulta_pericia_medica_agenda_medica_data_id FOREIGN KEY (agenda_medica_data_id) REFERENCES agenda_medica_data(id),
	CONSTRAINT fk_consulta_pericia_medica_especialidade_medica_id FOREIGN KEY (especialidade_medica_id) REFERENCES especialidade_medica(id)
)

IF NOT EXISTS(SELECT * FROM SYS.TABLES WHERE name = 'paciente_pericia_telefone')
CREATE TABLE paciente_pericia_telefone
(
    id							BIGINT				        NOT NULL IDENTITY,
	paciente_pericia_medica_id	BIGINT      			    NOT NULL,
    telefone					VARCHAR(20)			        NOT NULL,
	
	CONSTRAINT fk_paciente_pericia_telefone PRIMARY KEY (id),
	CONSTRAINT fk_paciente_pericia_telefone_paciente_pericia_medica_id FOREIGN KEY (paciente_pericia_medica_id) REFERENCES paciente_pericia_medica(id)
)

-- insert do sub-menu Perícia Médica no menu Junta Médica
IF NOT EXISTS(SELECT * FROM MENU WHERE url = '/periciaMedica/gestao')
	INSERT INTO menu (nome, ativo, categoria, url) VALUES('Perícia Médica', 1, 'JUNTA_MEDICA', '/periciaMedica/gestao');

IF EXISTS(SELECT * FROM menu WHERE url = '/periciaMedica/gestao')
BEGIN
DECLARE
    @menuId AS VARCHAR(1000)

	SELECT @menuId = CAST(id AS VARCHAR(1000)) FROM menu WHERE nome = 'Perícia Médica'
	
	IF NOT EXISTS(SELECT * FROM papel WHERE nome = 'ROLE_PERICIA_MEDICA_GESTAO')
		INSERT INTO papel (nome, id_menu) VALUES ('ROLE_PERICIA_MEDICA_GESTAO', @menuId)
	
	IF NOT EXISTS(SELECT * FROM papel WHERE nome = 'ROLE_PERICIA_MEDICA_CADASTRAR')
		INSERT INTO papel (nome, id_menu) VALUES ('ROLE_PERICIA_MEDICA_CADASTRAR', @menuId)
		
	IF NOT EXISTS(SELECT * FROM papel WHERE nome = 'ROLE_PERICIA_MEDICA_ATUALIZAR')
		INSERT INTO papel (nome, id_menu) VALUES ('ROLE_PERICIA_MEDICA_ATUALIZAR', @menuId)
		
	IF NOT EXISTS(SELECT * FROM papel WHERE nome = 'ROLE_PERICIA_MEDICA_EXCLUIR')
		INSERT INTO papel (nome, id_menu) VALUES ('ROLE_PERICIA_MEDICA_EXCLUIR', @menuId)
		
	IF NOT EXISTS(SELECT * FROM papel WHERE nome = 'ROLE_PERICIA_MEDICA_VISUALIZAR')
		INSERT INTO papel (nome, id_menu) VALUES ('ROLE_PERICIA_MEDICA_VISUALIZAR', @menuId)
		
END