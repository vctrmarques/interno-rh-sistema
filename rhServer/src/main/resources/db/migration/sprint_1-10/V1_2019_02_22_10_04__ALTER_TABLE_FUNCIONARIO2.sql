--Alteração da tabela funcionario ajuste.
--Davi Queiroz.

BEGIN IF COL_LENGTH('funcionario', 'previdencia') IS NOT NULL
     ALTER TABLE funcionario
     DROP COLUMN previdencia;
END


BEGIN IF COL_LENGTH('funcionario', 'imposto_renda') IS NOT NULL
     ALTER TABLE funcionario
     DROP COLUMN imposto_renda;
END

BEGIN IF COL_LENGTH('funcionario', 'pensao') IS NOT NULL
     ALTER TABLE funcionario
     DROP COLUMN pensao;
END