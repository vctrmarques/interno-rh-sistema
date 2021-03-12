--Flávio Silva
--Criação do papel de gestor de auditoria

IF EXISTS(SELECT * FROM menu WHERE nome = 'Auditoria')
BEGIN
DECLARE
    @menuId AS VARCHAR(1000)

	SELECT @menuId = CAST(id AS VARCHAR(1000)) FROM menu WHERE nome = 'Auditoria'
	
	IF NOT EXISTS(SELECT * FROM papel WHERE nome = 'ROLE_AUDITORIA_GESTAO')
		INSERT INTO papel (nome, id_menu) VALUES ('ROLE_AUDITORIA_GESTAO', @menuId)
		
END

