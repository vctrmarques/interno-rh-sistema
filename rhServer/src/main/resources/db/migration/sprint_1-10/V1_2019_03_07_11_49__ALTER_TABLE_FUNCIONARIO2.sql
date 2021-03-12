--Alteração da tabela Funcionário, adicionando colunas. 
--Davi Queiroz.

BEGIN IF COL_LENGTH('funcionario', 'categoria_alistamento') IS NULL
    ALTER TABLE funcionario
    ADD categoria_alistamento VARCHAR(255)
END