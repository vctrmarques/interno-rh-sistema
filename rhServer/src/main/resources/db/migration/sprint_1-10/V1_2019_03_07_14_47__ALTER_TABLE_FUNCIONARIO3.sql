--Alteração da tabela Funcionário, adicionando colunas. 
--Davi Queiroz.

BEGIN IF COL_LENGTH('funcionario', 'tipo_estabilidade') IS NULL
    ALTER TABLE funcionario
    ADD tipo_estabilidade VARCHAR(255)
END