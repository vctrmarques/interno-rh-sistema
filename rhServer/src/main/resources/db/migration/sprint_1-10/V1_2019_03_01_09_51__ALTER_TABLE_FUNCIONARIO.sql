--Alteração da tabela funcionario ajuste.
--Davi Queiroz.

BEGIN IF COL_LENGTH('funcionario', 'cidade') IS NOT NULL   
    ALTER TABLE funcionario
    DROP COLUMN cidade;
END