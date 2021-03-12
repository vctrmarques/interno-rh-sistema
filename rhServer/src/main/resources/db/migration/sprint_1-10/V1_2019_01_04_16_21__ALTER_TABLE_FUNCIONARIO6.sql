--Alteração da tabela funcionario, adicionando colunas. 
--Davi Queiroz.

IF COL_LENGTH('funcionario', 'registro_estrangeiro_uf_id') IS NULL
BEGIN
    ALTER TABLE funcionario
    ADD registro_estrangeiro_uf_id BIGINT;
    
    ALTER TABLE funcionario
    ADD CONSTRAINT fk_funcionario_unidade_federativa_registro_estrangeiro_uf_id FOREIGN KEY(registro_estrangeiro_uf_id) REFERENCES unidade_federativa(id);
END

IF COL_LENGTH('funcionario', 'municipio_registro_id') IS NULL
BEGIN
    ALTER TABLE funcionario
    ADD municipio_registro_id BIGINT;
    
    ALTER TABLE funcionario
    ADD CONSTRAINT fk_funcionario_municipio_municipio_registro_id FOREIGN KEY(municipio_registro_id) REFERENCES municipio(id);
END

IF COL_LENGTH('funcionario', 'uf_id') IS NULL
BEGIN
    ALTER TABLE funcionario
    ADD uf_id BIGINT NOT NULL;
    
    ALTER TABLE funcionario
    ADD CONSTRAINT fk_funcionario_unidade_federativa_uf_id FOREIGN KEY(uf_id) REFERENCES unidade_federativa(id);
END