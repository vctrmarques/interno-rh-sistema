--Davi Queiroz
--Adicionando colunas novas na tabela de cargo.

IF COL_LENGTH('cargo', 'faixa_salarial_id') IS NULL
BEGIN
    ALTER TABLE cargo
    ADD faixa_salarial_id BIGINT;
    
    ALTER TABLE cargo
    ADD CONSTRAINT fk_cargo_faixa_salrial_faixa_salarial_id FOREIGN KEY(faixa_salarial_id) REFERENCES faixa_salarial(id);
END

