-- Davi Queiroz

-- renomeação da tabela nivel salarial para referencia salarial

BEGIN IF (SELECT COUNT(name) FROM sys.databases WHERE name LIKE '%linkcon%') > 0
    EXEC sp_rename 'dbo.nivel_salarial', 'referencia_salarial';
END