--Alteração da tabela funcionario ajuste.
--Davi Queiroz.

BEGIN IF COL_LENGTH('funcionario', 'numero_ctps') IS NOT NULL
     ALTER TABLE funcionario
     ALTER COLUMN numero_ctps VARCHAR(255);
END


BEGIN IF COL_LENGTH('funcionario', 'ctps_uf_id') IS NOT NULL
     ALTER TABLE funcionario
     ALTER COLUMN ctps_uf_id BIGINT;
END
