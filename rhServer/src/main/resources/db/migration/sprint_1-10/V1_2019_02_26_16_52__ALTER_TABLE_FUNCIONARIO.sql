--Alteração da tabela Funcionário, renomeando colunas. 
--Davi Queiroz.

BEGIN IF COL_LENGTH('funcionario', 'uf_trabalho_id') IS NULL
    ALTER TABLE funcionario
    ADD uf_trabalho_id bigint
    CONSTRAINT fk_funcionario_unidade_federativa_uf_trabalho_id foreign key (uf_trabalho_id) REFERENCES unidade_federativa(id)
END