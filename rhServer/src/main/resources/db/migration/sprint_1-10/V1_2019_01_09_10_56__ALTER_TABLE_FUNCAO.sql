--Alteração da tabela funcao, adicionando colunas. 
--Davi Queiroz.

IF COL_LENGTH('funcao', 'faixa_salarial_id') IS NULL
BEGIN
    ALTER TABLE funcao
    ADD faixa_salarial_id BIGINT;
    
        ALTER TABLE funcao
    ADD CONSTRAINT fk_funcao_faixa_salarial_faixa_salarial_id FOREIGN KEY(faixa_salarial_id) REFERENCES faixa_salarial(id);
END

IF COL_LENGTH('funcao', 'grupo_salarial_id') IS NULL
BEGIN
    ALTER TABLE funcao
    ADD grupo_salarial_id BIGINT;
    
        ALTER TABLE funcao
    ADD CONSTRAINT fk_funcao_grupo_salarial_grupo_salarial_id FOREIGN KEY(grupo_salarial_id) REFERENCES grupo_salarial(id);
END

IF COL_LENGTH('funcao', 'classe_salarial_id') IS NULL
BEGIN
    ALTER TABLE funcao
    ADD classe_salarial_id BIGINT;
    
        ALTER TABLE funcao
    ADD CONSTRAINT fk_funcao_classe_salarial_classe_salarial_id FOREIGN KEY(classe_salarial_id) REFERENCES classe_salarial(id);
END
