-- Flávio Silva
-- Deletando papeis antigos da tabela de papel e usuário papel.

IF EXISTS(SELECT * FROM papel WHERE nome = 'ROLE_USUARIO')
BEGIN
DECLARE
    @papelId1 AS VARCHAR(1000)
	
	SELECT @papelId1 = CAST(id AS VARCHAR(1000)) FROM papel where nome = 'ROLE_USUARIO'
	
	EXEC('DELETE FROM usuario_papel WHERE papel_id = ' + @papelId1)
	EXEC('DELETE FROM papel WHERE id = ' + @papelId1)
	
END

IF EXISTS(SELECT * FROM papel WHERE nome = 'ROLE_USUARIO_VISUALIZAR')
BEGIN
DECLARE
    @papelId2 AS VARCHAR(1000)
	
	SELECT @papelId2 = CAST(id AS VARCHAR(1000)) FROM papel where nome = 'ROLE_USUARIO_VISUALIZAR'
	
	EXEC('DELETE FROM usuario_papel WHERE papel_id = ' + @papelId2)
	EXEC('DELETE FROM papel WHERE id = ' + @papelId2)
	
END

IF EXISTS(SELECT * FROM papel WHERE nome = 'ROLE_USUARIO_GESTAO')
BEGIN
DECLARE
    @papelId3 AS VARCHAR(1000)
	
	SELECT @papelId3 = CAST(id AS VARCHAR(1000)) FROM papel where nome = 'ROLE_USUARIO_GESTAO'
	
	EXEC('DELETE FROM usuario_papel WHERE papel_id = ' + @papelId3)
	EXEC('DELETE FROM papel WHERE id = ' + @papelId3)
	
END

IF EXISTS(SELECT * FROM papel WHERE nome = 'ROLE_REQUISICAO_PESSOAL_GESTAO')
BEGIN
DECLARE
    @papelId4 AS VARCHAR(1000)
	
	SELECT @papelId4 = CAST(id AS VARCHAR(1000)) FROM papel where nome = 'ROLE_REQUISICAO_PESSOAL_GESTAO'
	
	EXEC('DELETE FROM usuario_papel WHERE papel_id = ' + @papelId4)
	EXEC('DELETE FROM papel WHERE id = ' + @papelId4)
	
END

IF EXISTS(SELECT * FROM papel WHERE nome = 'ROLE_RECRUTAMENTO_SELECAO_GESTAO')
BEGIN
DECLARE
    @papelId5 AS VARCHAR(1000)
	
	SELECT @papelId5 = CAST(id AS VARCHAR(1000)) FROM papel where nome = 'ROLE_RECRUTAMENTO_SELECAO_GESTAO'
	
	EXEC('DELETE FROM usuario_papel WHERE papel_id = ' + @papelId5)
	EXEC('DELETE FROM papel WHERE id = ' + @papelId5)
	
END

    

   
