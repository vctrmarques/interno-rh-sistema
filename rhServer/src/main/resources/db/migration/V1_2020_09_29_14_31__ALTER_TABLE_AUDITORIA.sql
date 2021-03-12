
IF COL_LENGTH('auditoria', 'descricao') IS NOT NULL
    BEGIN
        ALTER TABLE auditoria ALTER COLUMN descricao varchar(MAX) NOT NULL;
    END


