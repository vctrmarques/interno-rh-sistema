--Alteração da tabela funcionario, adicionando colunas. 
--Davi Queiroz.

IF COL_LENGTH('funcionario', 'titulo_eleitor_uf_id') IS NULL
BEGIN
    ALTER TABLE funcionario
    ADD titulo_eleitor_uf_id BIGINT;
    
    ALTER TABLE funcionario
    ADD CONSTRAINT fk_funcionario_unidade_federativa_titulo_eleitor_uf_id FOREIGN KEY(titulo_eleitor_uf_id) REFERENCES unidade_federativa(id);
END
