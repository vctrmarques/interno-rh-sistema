-- João Marques
-- Deletando uk_paciente_pericia_medica_numero_processo da tabela paciente_pericia_medica

IF EXISTS (SELECT * FROM sys.indexes WHERE name = 'uk_paciente_pericia_medica_numero_processo')
BEGIN
  DROP INDEX paciente_pericia_medica.uk_paciente_pericia_medica_numero_processo;
END