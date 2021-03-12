-- Anderson Galindro
-- Inserindo coluna na tabela Empresa_Filial

if col_length('empresa_filial', 'vigencia_inicial') is null
begin
	ALTER TABLE empresa_filial ADD vigencia_inicial datetime2 NULL;
end

if col_length('empresa_filial', 'vigencia_final') is null
begin
	ALTER TABLE empresa_filial ADD vigencia_final datetime2 NULL;
end

if col_length('empresa_filial', 'email_responsavel') is null
begin
	ALTER TABLE empresa_filial ADD email_responsavel VARCHAR(255) NULL;
end

if col_length('empresa_filial', 'nome_responsavel') is null
begin
	ALTER TABLE empresa_filial ADD nome_responsavel VARCHAR(100) NULL;
end
