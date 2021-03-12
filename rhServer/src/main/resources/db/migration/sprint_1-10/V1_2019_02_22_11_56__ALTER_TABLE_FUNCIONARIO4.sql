--Alteração da tabela funcionario ajuste.
--Davi Queiroz.

BEGIN IF COL_LENGTH('funcionario', 'secao') IS NULL
     ALTER TABLE funcionario
     ADD  secao INT;
END


BEGIN IF COL_LENGTH('funcionario', 'zona') IS NULL
     ALTER TABLE funcionario
     ADD zona INT;
END
