-- Marconi Motta
-- Inserindo parametro global para Tipo de processamento 13 salário


IF NOT EXISTS(SELECT * FROM parametro_global WHERE nome = 'FOLHA_13_SALARIO')
	INSERT INTO .dbo.parametro_global (created_at, updated_at, created_by, updated_by, ativo, nome, tipo, valor)
		VALUES(SYSDATETIME(), SYSDATETIME(), 0, 0, 1, 'FOLHA_13_SALARIO', 'INTEIRO', '3');