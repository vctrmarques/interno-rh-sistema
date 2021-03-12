-- João Marques
IF COL_LENGTH('medico', 'clinico_geral') IS NULL
	BEGIN
	    ALTER TABLE medico ADD clinico_geral BIT NOT NULL DEFAULT(0);
	END
	
IF COL_LENGTH('medico', 'especialista') IS NULL
	BEGIN
	    ALTER TABLE medico ADD especialista BIT NOT NULL DEFAULT(0);
	END	
	
-- alteração do nome do submenu de 'Prontuário Perícia Médica' do menu 'Junta Medica'
UPDATE menu SET nome = 'Perícias Médicas Agendadas' WHERE nome = 'Prontuário Perícia Médica';
	