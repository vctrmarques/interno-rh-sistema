--Lucas Moura
--Adicionando campo de motivo

BEGIN IF COL_LENGTH('ferias_programacao', 'motivo') IS NULL
	ALTER TABLE ferias_programacao add motivo varchar(2000);
END


