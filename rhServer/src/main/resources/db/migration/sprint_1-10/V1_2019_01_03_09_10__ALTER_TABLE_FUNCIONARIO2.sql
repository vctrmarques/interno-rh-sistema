--Alteração da tabela funcionario, atualizando colunas. 
--Davi Queiroz.

DROP INDEX funcionario.uk_funcionario_matricula;

IF COL_LENGTH('funcionario', 'matricula') IS NOT NULL
BEGIN
    ALTER TABLE funcionario
    ALTER COLUMN matricula INT NOT NULL;
END

CREATE UNIQUE INDEX uk_funcionario_matricula ON funcionario (matricula);