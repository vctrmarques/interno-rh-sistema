-- Atualizando regra de obrigatóriedade de preenchimento e descontinuando alguns enums.
-- Flávio Silva

IF COL_LENGTH('pensao_alimenticia', 'incidencia_principal') IS NOT NULL
BEGIN
    ALTER TABLE pensao_alimenticia
    ALTER COLUMN incidencia_principal VARCHAR(255);
    
    UPDATE pensao_alimenticia SET incidencia_principal = NULL WHERE incidencia_principal = 'SALARIO_BASE_CATEGORIA';
	UPDATE pensao_alimenticia SET incidencia_principal = NULL WHERE incidencia_principal = 'VALOR_INFORMADO';

END

IF COL_LENGTH('pensao_alimenticia', 'salario_familia') IS NOT NULL
BEGIN
    ALTER TABLE pensao_alimenticia
    ALTER COLUMN salario_familia BIGINT;
END
