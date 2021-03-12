--Alteração da tabela Funcionário, adicionando colunas. 
--Davi Queiroz.

BEGIN IF COL_LENGTH('funcionario', 'matricula_vinculo') IS NULL
    ALTER TABLE funcionario
    ADD matricula_vinculo VARCHAR(255)
END

BEGIN IF COL_LENGTH('funcionario', 'descricao_vinculo') IS NULL
    ALTER TABLE funcionario
    ADD descricao_vinculo VARCHAR(255)
END

BEGIN IF COL_LENGTH('funcionario', 'valor_vinculo') IS NULL
    ALTER TABLE funcionario
    ADD valor_vinculo FLOAT
END