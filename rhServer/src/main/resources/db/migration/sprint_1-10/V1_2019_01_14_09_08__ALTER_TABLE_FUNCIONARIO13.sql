--Alteração da tabela funcionario, adicionando colunas. 
--Davi Queiroz.

IF COL_LENGTH('funcionario', 'agencia') IS NULL
BEGIN
    ALTER TABLE funcionario
    ADD agencia VARCHAR(30) NOT NULL;
END
