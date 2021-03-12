--Alteração da tabela funcionario ajuste.
--Davi Queiroz.

BEGIN IF COL_LENGTH('funcionario', 'cargo_id') IS NULL   
	ALTER TABLE funcionario
    ADD cargo_id bigint
    CONSTRAINT fk_funcionario_cargo_cargo_id foreign key (cargo_id) REFERENCES cargo(id)
END

BEGIN IF COL_LENGTH('funcionario', 'faixa_salarial_id') IS NULL   
	ALTER TABLE funcionario
    ADD faixa_salarial_id bigint
    CONSTRAINT fk_funcionario_faixa_salarial_faixa_salarial_id foreign key (faixa_salarial_id) REFERENCES faixa_salarial(id)
END

BEGIN IF COL_LENGTH('funcionario', 'referencia_salarial_id') IS NULL   
	ALTER TABLE funcionario
    ADD referencia_salarial_id bigint
    CONSTRAINT fk_funcionario_referencia_salarial_referencia_salarial_id foreign key (referencia_salarial_id) REFERENCES referencia_salarial(id)
END