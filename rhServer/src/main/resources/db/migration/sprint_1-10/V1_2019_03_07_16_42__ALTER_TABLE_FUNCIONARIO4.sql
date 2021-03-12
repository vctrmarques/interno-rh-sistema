--Alteração da tabela Funcionário, adicionando colunas. 
--Davi Queiroz.

BEGIN IF COL_LENGTH('funcionario', 'numero_sus') IS NULL
    ALTER TABLE funcionario
    ADD numero_sus VARCHAR(255)
END