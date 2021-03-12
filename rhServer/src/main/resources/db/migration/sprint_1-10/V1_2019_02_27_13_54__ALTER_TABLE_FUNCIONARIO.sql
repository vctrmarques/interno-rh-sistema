--Alteração da tabela Funcionário, renomeando colunas. 
--Davi Queiroz.

BEGIN IF COL_LENGTH('funcionario', 'classificao_ato') IS NOT NULL
    ALTER TABLE funcionario
    DROP COLUMN classificao_ato
END

BEGIN IF COL_LENGTH('funcionario', 'classificacao_ato_id') IS NULL
    ALTER TABLE funcionario
    ADD classificacao_ato_id bigint
    CONSTRAINT fk_funcionario_classificacao_ato_classificacao_ato_id foreign key (classificacao_ato_id) REFERENCES classificacao_ato(id)
END