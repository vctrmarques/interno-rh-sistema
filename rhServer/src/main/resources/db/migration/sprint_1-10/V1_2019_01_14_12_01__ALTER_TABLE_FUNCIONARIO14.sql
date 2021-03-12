--Alteração da tabela funcionario, adicionando colunas. 
--Davi Queiroz.

IF COL_LENGTH('funcionario', 'previdencia') IS NULL
BEGIN
    ALTER TABLE funcionario
    ADD previdencia BIT;
END

IF COL_LENGTH('funcionario', 'imposto_renda') IS NULL
BEGIN
    ALTER TABLE funcionario
    ADD imposto_renda BIT;
END

IF COL_LENGTH('funcionario', 'pensao') IS NULL
BEGIN
    ALTER TABLE funcionario
    ADD pensao BIT;
END
