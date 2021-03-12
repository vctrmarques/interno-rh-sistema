-- Flávio Silva
-- Recriação dos atributo parcelas_pagas em funcionario_verba e pensionista_verba

IF COL_LENGTH('funcionario_verba', 'parcelas_pagas') IS NULL
    BEGIN
        ALTER TABLE funcionario_verba ADD parcelas_pagas int NULL;
    END

IF COL_LENGTH('pensionista_verba', 'parcelas_pagas') IS NULL
    BEGIN
        ALTER TABLE pensionista_verba ADD parcelas_pagas int NULL;
    END

