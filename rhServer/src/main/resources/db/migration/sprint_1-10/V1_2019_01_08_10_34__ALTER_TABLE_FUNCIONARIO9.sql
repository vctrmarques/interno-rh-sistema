--Alteração da tabela funcionario, adicionando colunas. 
--Davi Queiroz.

IF COL_LENGTH('funcionario', 'identidade') IS NULL
BEGIN
    ALTER TABLE funcionario
    ADD identidade VARCHAR(255) NOT NULL;
END

IF COL_LENGTH('funcionario', 'orgao_expeditor') IS NULL
BEGIN
    ALTER TABLE funcionario
    ADD orgao_expeditor VARCHAR(255) NOT NULL;
END

IF COL_LENGTH('funcionario', 'dt_expedicao_rg') IS NULL
BEGIN
    ALTER TABLE funcionario
    ADD dt_expedicao_rg DATETIME2(7) NOT NULL;
END

IF COL_LENGTH('funcionario', 'numero_ctps') IS NULL
BEGIN
    ALTER TABLE funcionario
    ADD numero_ctps VARCHAR(255) NOT NULL;
END

IF COL_LENGTH('funcionario', 'cpf') IS NULL
BEGIN
    ALTER TABLE funcionario
    ADD cpf VARCHAR(11) NOT NULL;
END

IF COL_LENGTH('funcionario', 'pis_pasep') IS NULL
BEGIN
    ALTER TABLE funcionario
    ADD pis_pasep INT NOT NULL;
END

IF COL_LENGTH('funcionario', 'dt_emissao_pis_pasep') IS NULL
BEGIN
    ALTER TABLE funcionario
    ADD dt_emissao_pis_pasep DATETIME2(7);
END

IF COL_LENGTH('funcionario', 'agencia_pis_pasep') IS NULL
BEGIN
    ALTER TABLE funcionario
    ADD agencia_pis_pasep INT;
END

IF COL_LENGTH('funcionario', 'titulo_eleitor') IS NULL
BEGIN
    ALTER TABLE funcionario
    ADD titulo_eleitor INT;
END

IF COL_LENGTH('funcionario', 'cnh') IS NULL
BEGIN
    ALTER TABLE funcionario
    ADD cnh INT;
END

IF COL_LENGTH('funcionario', 'dt_validade_cnh') IS NULL
BEGIN
    ALTER TABLE funcionario
    ADD dt_validade_cnh DATETIME2(7);
END

IF COL_LENGTH('funcionario', 'categoria_cnh') IS NULL
BEGIN
    ALTER TABLE funcionario
    ADD categoria_cnh VARCHAR(255);
END

IF COL_LENGTH('funcionario', 'registro_alistamento') IS NULL
BEGIN
    ALTER TABLE funcionario
    ADD registro_alistamento INT;
END

IF COL_LENGTH('funcionario', 'classificao_ato') IS NULL
BEGIN
    ALTER TABLE funcionario
    ADD classificao_ato VARCHAR(255);
END

IF COL_LENGTH('funcionario', 'numero_processo') IS NULL
BEGIN
    ALTER TABLE funcionario
    ADD numero_processo INT;
END

IF COL_LENGTH('funcionario', 'numero_ato') IS NULL
BEGIN
    ALTER TABLE funcionario
    ADD numero_ato INT;
END

IF COL_LENGTH('funcionario', 'dt_nomeacao') IS NULL
BEGIN
    ALTER TABLE funcionario
    ADD dt_nomeacao DATETIME2(7);
END

IF COL_LENGTH('funcionario', 'numero_diario_oficial') IS NULL
BEGIN
    ALTER TABLE funcionario
    ADD numero_diario_oficial INT;
END

IF COL_LENGTH('funcionario', 'dt_publicacao_diario_oficial') IS NULL
BEGIN
    ALTER TABLE funcionario
    ADD dt_publicacao_diario_oficial DATETIME2(7);
END