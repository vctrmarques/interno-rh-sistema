-- João Marques
IF COL_LENGTH('medico', 'usuario_id') IS NULL
	BEGIN
	    ALTER TABLE medico ADD usuario_id bigint NULL;
	    ALTER TABLE medico ADD CONSTRAINT fk_medico_usuario_id FOREIGN KEY (usuario_id) REFERENCES usuario(id);
	END
	
IF COL_LENGTH('consulta_pericia_medica', 'tipo_pericia') IS NULL
	BEGIN
		ALTER TABLE consulta_pericia_medica ADD tipo_pericia VARCHAR(30) NOT NULL DEFAULT('Presencial');
	END
	
IF COL_LENGTH('consulta_pericia_medica', 'tipo_proxima_pericia') IS NULL
	BEGIN
	    ALTER TABLE consulta_pericia_medica ADD tipo_proxima_pericia VARCHAR(30) NULL;
	END
	
IF COL_LENGTH('consulta_pericia_medica', 'especialidade_medica_proxima_pericia_id') IS NULL
	BEGIN
	    ALTER TABLE consulta_pericia_medica ADD especialidade_medica_proxima_pericia_id BIGINT NULL;
	END
	
IF COL_LENGTH('consulta_pericia_medica', 'data_proximo_agendamento_pericia_medica') IS NULL
	BEGIN
	    ALTER TABLE consulta_pericia_medica ADD data_proximo_agendamento_pericia_medica DATE NULL;
	END
	
IF COL_LENGTH('consulta_pericia_medica', 'medico_id') IS NULL
	BEGIN
	    ALTER TABLE consulta_pericia_medica ADD medico_id BIGINT NULL;
	    ALTER TABLE consulta_pericia_medica ADD CONSTRAINT fk_consulta_pericia_medica_medico FOREIGN KEY (medico_id) REFERENCES medico(id);
	END	
    
    


