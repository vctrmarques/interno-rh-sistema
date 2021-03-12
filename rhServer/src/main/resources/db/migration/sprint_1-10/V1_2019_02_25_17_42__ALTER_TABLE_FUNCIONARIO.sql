--Alteração da tabela Funcionário, renomeando colunas. 
--Davi Queiroz.

BEGIN IF COL_LENGTH('funcionario', 'telefone_comercial') IS NULL
     ALTER TABLE funcionario
     ADD  telefone_comercial VARCHAR(255);
END
