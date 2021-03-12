ALTER TABLE empresa_filial ADD cei varchar(255) NULL;
ALTER TABLE empresa_filial ALTER COLUMN cnpj_cei varchar(255) NULL;
exec sp_RENAME 'empresa_filial.cnpj_cei', 'cnpj' , 'COLUMN';
ALTER TABLE empresa_filial ALTER COLUMN tipo_inscricao varchar(255) NULL;