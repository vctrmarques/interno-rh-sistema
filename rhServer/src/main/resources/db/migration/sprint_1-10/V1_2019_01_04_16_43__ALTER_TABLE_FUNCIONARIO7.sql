--Alteração da tabela funcionario, adicionando colunas. 
--Davi Queiroz.

IF COL_LENGTH('funcionario', 'estrangeiro') IS NULL
BEGIN
    ALTER TABLE funcionario
    ADD estrangeiro BIT;
END

IF COL_LENGTH('funcionario', 'naturalizado') IS NULL
BEGIN
    ALTER TABLE funcionario
    ADD naturalizado BIT;
END

IF COL_LENGTH('funcionario', 'casamento_brasileiro') IS NULL
BEGIN
    ALTER TABLE funcionario
    ADD casamento_brasileiro BIT;
END

IF COL_LENGTH('funcionario', 'filho_brasileiro') IS NULL
BEGIN
    ALTER TABLE funcionario
    ADD filho_brasileiro BIT;
END

IF COL_LENGTH('funcionario', 'logradouro') IS NULL
BEGIN
    ALTER TABLE funcionario
    ADD logradouro VARCHAR(255) NOT NULL;
END

IF COL_LENGTH('funcionario', 'numero') IS NULL
BEGIN
    ALTER TABLE funcionario
    ADD numero VARCHAR(255) NOT NULL;
END

IF COL_LENGTH('funcionario', 'complemento') IS NULL
BEGIN
    ALTER TABLE funcionario
    ADD complemento VARCHAR(255);
END

IF COL_LENGTH('funcionario', 'bairro') IS NULL
BEGIN
    ALTER TABLE funcionario
    ADD bairro VARCHAR(255) NOT NULL;
END

IF COL_LENGTH('funcionario', 'cidade') IS NULL
BEGIN
    ALTER TABLE funcionario
    ADD cidade VARCHAR(255) NOT NULL;
END

IF COL_LENGTH('funcionario', 'cep') IS NULL
BEGIN
    ALTER TABLE funcionario
    ADD cep VARCHAR(8) NOT NULL;
END

IF COL_LENGTH('funcionario', 'email_pessoal') IS NULL
BEGIN
    ALTER TABLE funcionario
    ADD email_pessoal VARCHAR(255);
END

IF COL_LENGTH('funcionario', 'email_corporativo') IS NULL
BEGIN
    ALTER TABLE funcionario
    ADD email_corporativo VARCHAR(255);
END

IF COL_LENGTH('funcionario', 'telefone_residencial') IS NULL
BEGIN
    ALTER TABLE funcionario
    ADD telefone_residencial VARCHAR(255);
END

IF COL_LENGTH('funcionario', 'telefone_celular') IS NULL
BEGIN
    ALTER TABLE funcionario
    ADD telefone_celular VARCHAR(255);
END
