--Alteração da tabela funcionario, adicionando colunas. 
--Davi Queiroz.

IF COL_LENGTH('funcionario', 'naturalidade') IS NULL
BEGIN
    ALTER TABLE funcionario
    ADD naturalidade VARCHAR(255);
END

IF COL_LENGTH('funcionario', 'conhecido_como') IS NULL
BEGIN
    ALTER TABLE funcionario
    ADD conhecido_como VARCHAR(255);
END

IF COL_LENGTH('funcionario', 'dt_ultimo_exame') IS NULL
BEGIN
    ALTER TABLE funcionario
    ADD dt_ultimo_exame DATETIME2(7);
END

IF COL_LENGTH('funcionario', 'tipo_sanguineo') IS NULL
BEGIN
    ALTER TABLE funcionario
    ADD tipo_sanguineo VARCHAR(255);
END

IF COL_LENGTH('funcionario', 'cor_pele') IS NULL
BEGIN
    ALTER TABLE funcionario
    ADD cor_pele VARCHAR(255);
END

IF COL_LENGTH('funcionario', 'nome_mae') IS NULL
BEGIN
    ALTER TABLE funcionario
    ADD nome_mae VARCHAR(255);
END

IF COL_LENGTH('funcionario', 'nome_pai') IS NULL
BEGIN
    ALTER TABLE funcionario
    ADD nome_pai VARCHAR(255);
END
