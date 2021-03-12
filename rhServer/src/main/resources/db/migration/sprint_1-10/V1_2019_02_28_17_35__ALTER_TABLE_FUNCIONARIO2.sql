--Alteração da tabela funcionario ajuste.
--Davi Queiroz.

BEGIN IF (SELECT count(id) FROM funcionario) > 0   
	UPDATE funcionario SET nome_mae = 'Alteracao nao nulo';

	ALTER TABLE funcionario
	ALTER COLUMN nome_mae VARCHAR(255) NOT NULL;
END