-- João Marques

-- insert do sub-menu MÉDICO no menu JUNTA MÉDICA
INSERT INTO menu (nome, ativo, categoria, url) VALUES('Médico', 1, 'JUNTA_MEDICA', '/medico/gestao');

IF EXISTS(SELECT * FROM menu WHERE url = '/medico/gestao')
BEGIN
DECLARE
    @menuId AS VARCHAR(1000)

	SELECT @menuId = CAST(id AS VARCHAR(1000)) FROM menu WHERE nome = 'Médico'
	
	IF NOT EXISTS(SELECT * FROM papel WHERE nome = 'ROLE_MEDICO_GESTAO')
		INSERT INTO papel (nome, id_menu) VALUES ('ROLE_MEDICO_GESTAO', @menuId)
		
END