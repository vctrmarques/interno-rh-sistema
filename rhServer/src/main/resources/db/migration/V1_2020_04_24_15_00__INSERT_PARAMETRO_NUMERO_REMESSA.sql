-- inserir parametro para ser usado na continuidade do arquivo de remessa
-- Rodrigo Leite

IF NOT EXISTS(SELECT * FROM parametro_global WHERE nome = 'numeroRemessa')
	INSERT INTO .dbo.parametro_global (created_at, updated_at, created_by, updated_by, ativo, nome, tipo, valor)
		VALUES(SYSDATETIME(), SYSDATETIME(), 0, 0, 1, 'numeroRemessa', 'INTEIRO', '0');