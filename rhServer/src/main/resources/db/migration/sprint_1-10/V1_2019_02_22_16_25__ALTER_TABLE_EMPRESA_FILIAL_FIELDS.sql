ALTER TABLE empresa_filial ALTER COLUMN sat FLOAT NULL;
ALTER TABLE empresa_filial ALTER COLUMN salario_educacao FLOAT NULL;
ALTER TABLE empresa_filial ALTER COLUMN senai FLOAT NULL;
ALTER TABLE empresa_filial ALTER COLUMN sesi FLOAT NULL;
ALTER TABLE empresa_filial ALTER COLUMN senac FLOAT NULL;
ALTER TABLE empresa_filial ALTER COLUMN sesc FLOAT NULL;
ALTER TABLE empresa_filial ALTER COLUMN sebrae FLOAT NULL;
ALTER TABLE empresa_filial ALTER COLUMN senar FLOAT NULL;
ALTER TABLE empresa_filial ALTER COLUMN senat FLOAT NULL;
ALTER TABLE empresa_filial ALTER COLUMN set_col FLOAT NULL;
ALTER TABLE empresa_filial ALTER COLUMN secoop FLOAT NULL;
ALTER TABLE empresa_filial ALTER COLUMN dpc FLOAT NULL;
ALTER TABLE empresa_filial ALTER COLUMN forca_aerea FLOAT NULL;
ALTER TABLE empresa_filial ALTER COLUMN cd_acidente_trabalho INT NULL;
ALTER TABLE empresa_filial ALTER COLUMN sigla varchar(10) NULL;

exec sp_RENAME 'empresa_filial.cd_acidente_trabalho', 'fap' , 'COLUMN';