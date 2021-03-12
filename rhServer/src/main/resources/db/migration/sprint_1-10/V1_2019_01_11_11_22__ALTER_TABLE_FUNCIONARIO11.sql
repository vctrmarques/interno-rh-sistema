IF COL_LENGTH('funcionario', 'vinculo_id') IS NULL
BEGIN
    ALTER TABLE funcionario
    ADD vinculo_id BIGINT NOT NULL;
    
    ALTER TABLE funcionario
    ADD CONSTRAINT fk_funcionario_vinculo_vinculo_id FOREIGN KEY(vinculo_id) REFERENCES vinculo(id);
END

IF COL_LENGTH('funcionario', 'funcao_id') IS NULL
BEGIN
    ALTER TABLE funcionario
    ADD funcao_id BIGINT NOT NULL;
    
    ALTER TABLE funcionario
    ADD CONSTRAINT fk_funcionario_funcao_funcao_id FOREIGN KEY(funcao_id) REFERENCES funcao(id);
END

IF COL_LENGTH('funcionario', 'municipio_trabalho_id') IS NULL
BEGIN
    ALTER TABLE funcionario
    ADD municipio_trabalho_id BIGINT;
    
    ALTER TABLE funcionario
    ADD CONSTRAINT fk_funcionario_municipio_municipio_trabalho_id FOREIGN KEY(municipio_trabalho_id) REFERENCES municipio(id);
END

IF COL_LENGTH('funcionario', 'lotacao_id') IS NULL
BEGIN
    ALTER TABLE funcionario
    ADD lotacao_id BIGINT;
    
    ALTER TABLE funcionario
    ADD CONSTRAINT fk_funcionario_lotacao_lotacao_id FOREIGN KEY(lotacao_id) REFERENCES lotacao(id);
END

IF COL_LENGTH('funcionario', 'banco_id') IS NULL
BEGIN
    ALTER TABLE funcionario
    ADD banco_id BIGINT NOT NULL;
    
    ALTER TABLE funcionario
    ADD CONSTRAINT fk_funcionario_banco_banco_id FOREIGN KEY(banco_id) REFERENCES banco(id);
END

IF COL_LENGTH('funcionario', 'tipo_folha_id') IS NULL
BEGIN
    ALTER TABLE funcionario
    ADD tipo_folha_id BIGINT;
    
    ALTER TABLE funcionario
    ADD CONSTRAINT fk_funcionario_tipo_folha_tipo_folha_id FOREIGN KEY(tipo_folha_id) REFERENCES tipo_folha(id);
END

IF COL_LENGTH('funcionario', 'jornada_trabalho_turno_id') IS NULL
BEGIN
    ALTER TABLE funcionario
    ADD jornada_trabalho_turno_id BIGINT NOT NULL;
    
    ALTER TABLE funcionario
    ADD CONSTRAINT fk_funcionario_turno_jornada_trabalho_turno_id FOREIGN KEY(jornada_trabalho_turno_id) REFERENCES turno(id);
END

IF COL_LENGTH('funcionario', 'sindicato_id') IS NULL
BEGIN
    ALTER TABLE funcionario
    ADD sindicato_id BIGINT;
    
    ALTER TABLE funcionario
    ADD CONSTRAINT fk_funcionario_sindicato_sindicato_id FOREIGN KEY(sindicato_id) REFERENCES sindicato(id);
END

IF COL_LENGTH('funcionario', 'tp_sit_func_afastamento_id') IS NULL
BEGIN
    ALTER TABLE funcionario
    ADD tp_sit_func_afastamento_id BIGINT;
    
    ALTER TABLE funcionario
    ADD CONSTRAINT fk_funcionario_afastamento_tp_sit_func_afastamento_id FOREIGN KEY(tp_sit_func_afastamento_id) REFERENCES afastamento(id);
END

IF COL_LENGTH('funcionario', 'motivo_afastamento_id') IS NULL
BEGIN
    ALTER TABLE funcionario
    ADD motivo_afastamento_id BIGINT;
    
    ALTER TABLE funcionario
    ADD CONSTRAINT fk_funcionario_motivo_afastamento_motivo_afastamento_id FOREIGN KEY(motivo_afastamento_id) REFERENCES motivo_afastamento(id);
END