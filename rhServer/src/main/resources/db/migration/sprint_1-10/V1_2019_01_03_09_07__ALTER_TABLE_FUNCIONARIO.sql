--Alteração da tabela funcionario, adicionando colunas. 
--Davi Queiroz.

IF COL_LENGTH('funcionario', 'dt_nascimento') IS NULL
BEGIN
    ALTER TABLE funcionario
    ADD dt_nascimento DATETIME2(7) NOT NULL;
END