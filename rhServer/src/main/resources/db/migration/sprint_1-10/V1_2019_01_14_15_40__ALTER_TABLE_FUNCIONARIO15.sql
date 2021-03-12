--Alteração da tabela funcionario, adicionando colunas. 
--Davi Queiroz.

IF COL_LENGTH('funcionario', 'ini_contrat_temp') IS NULL
BEGIN
    ALTER TABLE funcionario
    ADD ini_contrat_temp DATETIME2(7);
END

IF COL_LENGTH('funcionario', 'fim_contrat_temp') IS NULL
BEGIN
    ALTER TABLE funcionario
    ADD fim_contrat_temp DATETIME2(7);
END

IF COL_LENGTH('funcionario', 'dias_afastado') IS NULL
BEGIN
    ALTER TABLE funcionario
    ADD dias_afastado INT;
END

IF COL_LENGTH('funcionario', 'matricula_destino') IS NULL
BEGIN
    ALTER TABLE funcionario
    ADD matricula_destino INT;
END

IF COL_LENGTH('funcionario', 'ces_empresa_destino') IS NULL
BEGIN
    ALTER TABLE funcionario
    ADD ces_empresa_destino VARCHAR(255);
END