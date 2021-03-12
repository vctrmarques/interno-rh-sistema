--Alteração da tabela funcionario, adicionando colunas. 
--Davi Queiroz.

DELETE FROM funcionario;

IF COL_LENGTH('funcionario', 'orgao_expeditor_uf_id') IS NULL
BEGIN
    ALTER TABLE funcionario
    ADD orgao_expeditor_uf_id BIGINT NOT NULL;
    
    ALTER TABLE funcionario
    ADD CONSTRAINT fk_funcionario_unidade_federativa_orgao_expeditor_uf_id FOREIGN KEY(orgao_expeditor_uf_id) REFERENCES unidade_federativa(id);
END

IF COL_LENGTH('funcionario', 'ctps_uf_id') IS NULL
BEGIN
    ALTER TABLE funcionario
    ADD ctps_uf_id BIGINT NOT NULL;
    
    ALTER TABLE funcionario
    ADD CONSTRAINT fk_funcionario_unidade_federativa_ctps_uf_id FOREIGN KEY(ctps_uf_id) REFERENCES unidade_federativa(id);
END

IF COL_LENGTH('funcionario', 'uf_id') IS NULL
BEGIN
    ALTER TABLE funcionario
    ADD titulo_eleitor_uf_id BIGINT;
    
    ALTER TABLE funcionario
    ADD CONSTRAINT fk_funcionario_unidade_federativa_titulo_eleitor_uf_id FOREIGN KEY(titulo_eleitor_uf_id) REFERENCES unidade_federativa(id);
END