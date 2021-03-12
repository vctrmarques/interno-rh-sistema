-- Rodrigo Leite
-- Alterações para atender a demanda de arquivo de remessa. Relato na task 121

IF COL_LENGTH('empresa_filial', 'agencia_id') IS NULL
BEGIN
    ALTER TABLE empresa_filial ADD agencia_id BIGINT;
    ALTER TABLE empresa_filial ADD CONSTRAINT fk_empresa_filial_agencia_id FOREIGN KEY (agencia_id) REFERENCES agencia(id)
END

IF COL_LENGTH('funcionario', 'agencia_id') IS NULL
BEGIN
    ALTER TABLE funcionario ADD agencia_id BIGINT;
    ALTER TABLE funcionario ADD CONSTRAINT fk_funcionario_agencia_id FOREIGN KEY (agencia_id) REFERENCES agencia(id)
END

IF COL_LENGTH('consignado', 'agencia_id') IS NULL
BEGIN
    ALTER TABLE consignado ADD agencia_id BIGINT;
    ALTER TABLE consignado ADD CONSTRAINT fk_consignado_agencia_id FOREIGN KEY (agencia_id) REFERENCES agencia(id)
END

IF COL_LENGTH('consignado', 'digito_conta') IS NULL
BEGIN
    ALTER TABLE consignado ADD digito_conta VARCHAR(10);
END

IF COL_LENGTH('consignado', 'tipo_conta') IS NULL
BEGIN
    ALTER TABLE consignado ADD tipo_conta VARCHAR(100);
END

IF COL_LENGTH('pensao_alimenticia', 'digito_conta') IS NULL
BEGIN
    ALTER TABLE pensao_alimenticia ADD digito_conta VARCHAR(10);
END

IF COL_LENGTH('responsavel_legal', 'agencia_id') IS NULL
BEGIN
    ALTER TABLE responsavel_legal ADD agencia_id BIGINT;
    ALTER TABLE responsavel_legal ADD CONSTRAINT fk_responsavel_legal_agencia_id FOREIGN KEY (agencia_id) REFERENCES agencia(id)
END

IF COL_LENGTH('responsavel_legal', 'digito_conta') IS NULL
BEGIN
    ALTER TABLE responsavel_legal ADD digito_conta VARCHAR(10);
END