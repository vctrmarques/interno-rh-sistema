-- João Marques
-- Adição da coluna 'semanal' na tabela de 'agenda_medica'.
-- Adição da coluna 'data' na tabela de 'agenda_medica_data' e por esse motivo DROP COLUMN nos campos 'ano' e 'mes' também da tabela 'agenda_medica_data'.

IF COL_LENGTH('agenda_medica', 'semanal') IS NULL
	BEGIN
	    ALTER TABLE agenda_medica ADD semanal bit NOT NULL DEFAULT(0);
	END
	
IF COL_LENGTH('agenda_medica_data', 'data') IS NULL	
	BEGIN
	    ALTER TABLE agenda_medica_data ADD data DATE;
	END
	
IF COL_LENGTH('agenda_medica_data', 'mes') IS NOT NULL	
	BEGIN
	    ALTER TABLE agenda_medica_data DROP COLUMN mes;
	END
	
IF COL_LENGTH('agenda_medica_data', 'ano') IS NOT NULL	
	BEGIN
	    ALTER TABLE agenda_medica_data DROP COLUMN ano;
	END	