-- João Marques

-- insert do menu JUNTA MÉDICA
INSERT INTO menu (nome, ativo, categoria, url) VALUES('Especialidade Médica', 1, 'JUNTA_MEDICA', '/especialidadeMedica/gestao');

IF EXISTS(SELECT * FROM menu WHERE url = '/especialidadeMedica/gestao')
BEGIN
DECLARE
    @menuId AS VARCHAR(1000)

	SELECT @menuId = CAST(id AS VARCHAR(1000)) FROM menu WHERE nome = 'Especialidade Médica'
	
	IF NOT EXISTS(SELECT * FROM papel WHERE nome = 'ROLE_ESPECIALIDADE_MEDICA_GESTAO')
		INSERT INTO papel (nome, id_menu) VALUES ('ROLE_ESPECIALIDADE_MEDICA_GESTAO', @menuId)
		
END