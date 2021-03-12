-- Flávio Silva
-- Ajuste nos campos de tempo de cargo efetivo, que não dependem de sexo. Refactory. 
-- + criação do campo de reajuste.

IF COL_LENGTH('regra_aposentadoria', 'tempo_cargo_efetivo_homem') IS NOT NULL
    begin
	    exec sp_RENAME 'regra_aposentadoria.tempo_cargo_efetivo_homem', 'tempo_cargo_efetivo' , 'COLUMN';
    end

IF COL_LENGTH('regra_aposentadoria', 'tempo_cargo_efetivo_mulher') IS NOT NULL	
	BEGIN
	    ALTER TABLE regra_aposentadoria DROP COLUMN tempo_cargo_efetivo_mulher;
	END	
	
IF COL_LENGTH('regra_aposentadoria', 'reajuste') IS NULL
	BEGIN
	    ALTER TABLE regra_aposentadoria ADD reajuste VARCHAR(255) NULL;
	END