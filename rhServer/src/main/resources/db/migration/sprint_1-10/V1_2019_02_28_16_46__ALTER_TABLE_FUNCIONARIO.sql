--Alteração da tabela funcionario ajuste.
--Davi Queiroz.

BEGIN IF COL_LENGTH('funcionario', 'dt_ultimo_exame') IS NOT NULL   
    ALTER TABLE funcionario
    DROP COLUMN dt_ultimo_exame;
END