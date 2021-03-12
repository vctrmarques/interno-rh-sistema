--Alteração da tabela funcionario ajuste.
--Davi Queiroz.

BEGIN IF (SELECT MAX(id) FROM empresa_filial WHERE empresa_matriz IS NULL OR empresa_matriz = 0 ) > 0
	UPDATE funcionario SET filial_id = (SELECT MAX(id) FROM empresa_filial WHERE empresa_matriz IS NULL OR empresa_matriz = 0);
END;

BEGIN IF COL_LENGTH('funcionario', 'filial_id') IS NOT NULL
    ALTER TABLE funcionario
    ALTER COLUMN filial_id BIGINT NOT NULL;
END