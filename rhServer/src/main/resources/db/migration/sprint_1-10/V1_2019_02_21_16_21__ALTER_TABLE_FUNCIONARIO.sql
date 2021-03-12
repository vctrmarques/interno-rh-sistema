--Alteração da tabela funcionario ajuste.
--Davi Queiroz.

BEGIN IF COL_LENGTH('funcionario', 'tipo_folha_id') IS NOT NULL
    ALTER TABLE funcionario
    DROP CONSTRAINT fk_funcionario_tipo_folha_tipo_folha_id;
    
    ALTER TABLE funcionario
    DROP COLUMN tipo_folha_id;
END