-- João Marques
IF COL_LENGTH('prontuario_pericia_medica', 'especialidade_medica_id') IS NOT NULL
	BEGIN
	    ALTER TABLE prontuario_pericia_medica ALTER COLUMN especialidade_medica_id BIGINT NULL;
	END
	

IF COL_LENGTH('motivo_afastamento', 'disponivel_para_pericia') IS NULL
    BEGIN
		exec sp_rename 'motivo_afastamento.[entra_folha]', 'disponivel_para_pericia', 'column'
    END


