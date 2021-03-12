--Alteração da tabela funcionario, adicionando colunas. 
--Davi Queiroz.

IF COL_LENGTH('funcionario', 'dado_cadastral_complementar_id') IS NULL
BEGIN
    ALTER TABLE funcionario
    ADD dado_cadastral_complementar_id BIGINT;
    
    ALTER TABLE funcionario
    ADD CONSTRAINT fk_funcionario_dado_cadastral_complementar_dado_cadastral_complementar_id FOREIGN KEY(dado_cadastral_complementar_id) REFERENCES dado_cadastral_complementar(id);
END