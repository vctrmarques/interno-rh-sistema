--Inserts de parâmetros globais (repopulação da tabela).
--Flávio Silva

IF NOT EXISTS(SELECT * FROM parametro_global WHERE chave = 'SALARIO_MINIMO')
	INSERT INTO parametro_global (created_at, updated_at, created_by, updated_by, ativo, chave, tipo, valor)
		VALUES(SYSDATETIME(), SYSDATETIME(), 0, 0, 1, 'SALARIO_MINIMO', 'DECIMAL', '1045.00');