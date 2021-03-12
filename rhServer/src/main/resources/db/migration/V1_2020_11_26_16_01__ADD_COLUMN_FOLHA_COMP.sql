-- Criação dos campos de checagem de recadastramento e amparo legal.
-- Flávio Silva

IF COL_LENGTH('folha_competencia', 'checar_recad') IS NULL
	BEGIN
	    ALTER TABLE folha_competencia ADD checar_recad BIT NOT NULL DEFAULT(1);
	END
	
IF COL_LENGTH('folha_competencia', 'checar_recad_amp_legal') IS NULL
	BEGIN
	    ALTER TABLE folha_competencia ADD checar_recad_amp_legal VARCHAR(255) NULL;
	END