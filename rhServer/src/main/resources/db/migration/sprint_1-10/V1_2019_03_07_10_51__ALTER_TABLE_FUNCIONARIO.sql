--Alteração da tabela Funcionário, adicionando colunas. 
--Davi Queiroz.

BEGIN IF COL_LENGTH('funcionario', 'serie_ctps') IS NULL
    ALTER TABLE funcionario
    ADD serie_ctps VARCHAR(255)
END