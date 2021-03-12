--Alteração da tabela funcionario, inserção de colunas, faixa_salarial_cargo_id, referencia_salarial_cargo_id, faixa_salarial_funcao_id, referencia_salarial_funcao_id.
--Remoção de colunas genéricas, faixa_salarial_id, referencia_salarial_id
--Lucas Moura.



BEGIN   
    ALTER TABLE funcionario
    DROP CONSTRAINT fk_funcionario_faixa_salarial_faixa_salarial_id;
    ALTER TABLE funcionario
    DROP COLUMN faixa_salarial_id;
END

BEGIN  
    ALTER TABLE funcionario
    DROP CONSTRAINT fk_funcionario_referencia_salarial_referencia_salarial_id;
    ALTER TABLE funcionario
    DROP COLUMN referencia_salarial_id;
END

IF NOT EXISTS 
(
    SELECT * FROM INFORMATION_SCHEMA.COLUMNS 
    WHERE table_name = 'funcionario'
    AND column_name = 'faixa_salarial_cargo_id'
)
BEGIN
	ALTER TABLE funcionario
	add faixa_salarial_cargo_id bigint
END	

IF NOT EXISTS 
(
    SELECT * FROM INFORMATION_SCHEMA.COLUMNS 
    WHERE table_name = 'funcionario'
    AND column_name = 'referencia_salarial_cargo_id'
)
BEGIN
	ALTER TABLE funcionario
	add referencia_salarial_cargo_id bigint
END

IF NOT EXISTS 
(
    SELECT * FROM INFORMATION_SCHEMA.COLUMNS 
    WHERE table_name = 'funcionario'
    AND column_name = 'faixa_salarial_funcao_id'
)
BEGIN
	ALTER TABLE funcionario
	add faixa_salarial_funcao_id bigint
END

IF NOT EXISTS 
(
    SELECT * FROM INFORMATION_SCHEMA.COLUMNS 
    WHERE table_name = 'funcionario'
    AND column_name = 'referencia_salarial_funcao_id'
)
BEGIN
	ALTER TABLE funcionario
	add referencia_salarial_funcao_id bigint
END	