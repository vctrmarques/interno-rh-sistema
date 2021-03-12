-- Flávio Silva

-- Excluindo uk_funcionario_verba para permitir verba e funcionário várias vezes, inativando as antigas.
IF EXISTS (SELECT * FROM sys.indexes WHERE name = 'uk_funcionario_verba')
	BEGIN
	  DROP INDEX funcionario_verba.uk_funcionario_verba;
	END

-- Adição da coluna ativo na tabela funcionario_verba para permitir exclusão lógica em caso de existencia de relacionamentos com lançamentos.
IF COL_LENGTH('funcionario_verba', 'ativo') IS NULL
    BEGIN
        ALTER TABLE funcionario_verba ADD ativo BIT NOT NULL DEFAULT(1);
    END

-- Adição da coluna ativo na tabela pensionista_verba para permitir exclusão lógica em caso de existencia de relacionamentos com lançamentos.
IF COL_LENGTH('pensionista_verba', 'ativo') IS NULL
    BEGIN
        ALTER TABLE pensionista_verba ADD ativo BIT NOT NULL DEFAULT(1);
    END