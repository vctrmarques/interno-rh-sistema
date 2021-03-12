--Alteração da tabela funcionario, adicionando colunas. 
--Davi Queiroz.

IF COL_LENGTH('funcionario', 'dt_admissao') IS NULL
BEGIN
    ALTER TABLE funcionario
    ADD dt_admissao DATETIME2(7) NOT NULL;
END

IF COL_LENGTH('funcionario', 'n_dependentes_ir') IS NULL
BEGIN
    ALTER TABLE funcionario
    ADD n_dependentes_ir INT;
END

IF COL_LENGTH('funcionario', 'n_dependentes_sf') IS NULL
BEGIN
    ALTER TABLE funcionario
    ADD n_dependentes_sf INT;
END

IF COL_LENGTH('funcionario', 'opc_fgts') IS NULL
BEGIN
    ALTER TABLE funcionario
    ADD opc_fgts BIT;
END

IF COL_LENGTH('funcionario', 'dt_opc_fgts') IS NULL
BEGIN
    ALTER TABLE funcionario
    ADD dt_opc_fgts DATETIME2(7);
END

IF COL_LENGTH('funcionario', 'agencia_fgts') IS NULL
BEGIN
    ALTER TABLE funcionario
    ADD agencia_fgts INT;
END

IF COL_LENGTH('funcionario', 'conta_fgts') IS NULL
BEGIN
    ALTER TABLE funcionario
    ADD conta_fgts INT;
END

IF COL_LENGTH('funcionario', 'dt_exercicio_admissao_ats') IS NULL
BEGIN
    ALTER TABLE funcionario
    ADD dt_exercicio_admissao_ats DATETIME2(7);
END

IF COL_LENGTH('funcionario', 'tipo_adicional_tempo_servico') IS NULL
BEGIN
    ALTER TABLE funcionario
    ADD tipo_adicional_tempo_servico VARCHAR(255);
END

IF COL_LENGTH('funcionario', 'estabilidade') IS NULL
BEGIN
    ALTER TABLE funcionario
    ADD estabilidade BIT;
END

IF COL_LENGTH('funcionario', 'tipo_conta') IS NULL
BEGIN
    ALTER TABLE funcionario
    ADD tipo_conta VARCHAR(255);
END

IF COL_LENGTH('funcionario', 'numero_conta') IS NULL
BEGIN
    ALTER TABLE funcionario
    ADD numero_conta VARCHAR(255) NOT NULL;
END

IF COL_LENGTH('funcionario', 'digito_conta') IS NULL
BEGIN
    ALTER TABLE funcionario
    ADD digito_conta VARCHAR(255) NOT NULL;
END

IF COL_LENGTH('funcionario', 'probatorio') IS NULL
BEGIN
    ALTER TABLE funcionario
    ADD probatorio BIT NOT NULL;
END

IF COL_LENGTH('funcionario', 'carga_horaria') IS NULL
BEGIN
    ALTER TABLE funcionario
    ADD carga_horaria VARCHAR(255);
END

IF COL_LENGTH('funcionario', 'tipo_frequencia') IS NULL
BEGIN
    ALTER TABLE funcionario
    ADD tipo_frequencia VARCHAR(255);
END

IF COL_LENGTH('funcionario', 'dt_ini_situacao_funcional') IS NULL
BEGIN
    ALTER TABLE funcionario
    ADD dt_ini_situacao_funcional DATETIME2(7);
END

IF COL_LENGTH('funcionario', 'dt_fim_situacao_funcional') IS NULL
BEGIN
    ALTER TABLE funcionario
    ADD dt_fim_situacao_funcional DATETIME2(7);
END