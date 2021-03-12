--Alteração da tabela funcionario, adicionando colunas. 
--Davi Queiroz.

IF COL_LENGTH('funcionario', 'nacionalidade_id') IS NULL
BEGIN
    ALTER TABLE funcionario
    ADD nacionalidade_id BIGINT;
    
    ALTER TABLE funcionario
    ADD CONSTRAINT funcionario_nacionalidade_nacionalidade_id FOREIGN KEY(nacionalidade_id) REFERENCES nacionalidade(id);
END

IF COL_LENGTH('funcionario', 'municipio_id') IS NULL
BEGIN
    ALTER TABLE funcionario
    ADD municipio_id BIGINT;
    
    ALTER TABLE funcionario
    ADD CONSTRAINT funcionario_municipio_municipio_id FOREIGN KEY(municipio_id) REFERENCES municipio(id);
END
