
--Inserts de parâmetros globais (repopulação da tabela).
--Flávio Silva

IF NOT EXISTS(SELECT * FROM parametro_global WHERE chave = 'TETO_PREFEITURA')
	INSERT INTO parametro_global (created_at, updated_at, created_by, updated_by, ativo, chave, tipo, valor)
		VALUES(SYSDATETIME(), SYSDATETIME(), 0, 0, 1, 'TETO_PREFEITURA', 'DECIMAL', '27089.54');

IF NOT EXISTS(SELECT * FROM parametro_global WHERE chave = 'TETO_INSS')
	INSERT INTO parametro_global (created_at, updated_at, created_by, updated_by, ativo, chave, tipo, valor)
		VALUES(SYSDATETIME(), SYSDATETIME(), 0, 0, 1, 'TETO_INSS', 'DECIMAL', '5839.45');
		
IF NOT EXISTS(SELECT * FROM parametro_global WHERE chave = 'VALOR_DEPENDENTE_IRRF')
	INSERT INTO parametro_global (created_at, updated_at, created_by, updated_by, ativo, chave, tipo, valor)
		VALUES(SYSDATETIME(), SYSDATETIME(), 0, 0, 1, 'VALOR_DEPENDENTE_IRRF', 'DECIMAL', '189.59');
		
IF NOT EXISTS(SELECT * FROM parametro_global WHERE chave = 'NUMERO_REMESSA')
	INSERT INTO .dbo.parametro_global (created_at, updated_at, created_by, updated_by, ativo, chave, tipo, valor)
		VALUES(SYSDATETIME(), SYSDATETIME(), 0, 0, 1, 'NUMERO_REMESSA', 'INTEIRO', '0');
		
IF NOT EXISTS(SELECT * FROM parametro_global WHERE chave = 'FOLHA_13_SALARIO')
	INSERT INTO .dbo.parametro_global (created_at, updated_at, created_by, updated_by, ativo, chave, tipo, valor)
		VALUES(SYSDATETIME(), SYSDATETIME(), 0, 0, 1, 'FOLHA_13_SALARIO', 'INTEIRO', '3');