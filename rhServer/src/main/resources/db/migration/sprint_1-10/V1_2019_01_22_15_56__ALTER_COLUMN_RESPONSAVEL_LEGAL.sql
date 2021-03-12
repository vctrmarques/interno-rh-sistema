--Alteração do tipo dos campos codigo e nas tabelas do sistema para string. 
--Davi Queiroz.

BEGIN IF COL_LENGTH('responsavel_legal', 'codigo_responsavel') IS NOT NULL
    ALTER TABLE responsavel_legal
    ALTER COLUMN codigo_responsavel varchar(255) NOT NULL;
END