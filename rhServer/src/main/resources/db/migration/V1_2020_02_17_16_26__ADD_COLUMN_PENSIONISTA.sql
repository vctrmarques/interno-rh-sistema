
-- Anderson Galindro
-- Inserindo coluna na tabela pensionista

if col_length('pensionista', 'cpf') is null
begin
	ALTER TABLE pensionista ADD cpf varchar (11)
end

if col_length('pensionista', 'identidade') is null
begin
	ALTER TABLE pensionista ADD identidade varchar (255)
end

if col_length('pensionista', 'dt_expedicao_rg') is null
begin
	ALTER TABLE pensionista ADD dt_expedicao_rg datetime2
end

if col_length('pensionista', 'cor_pele') is null
begin
	ALTER TABLE pensionista ADD cor_pele varchar (255)
end

if col_length('pensionista', 'nacionalidade_id') is null
begin
	ALTER TABLE pensionista ADD nacionalidade_id bigint
end

if col_length('pensionista', 'email_pessoal') is null
begin
	ALTER TABLE pensionista ADD email_pessoal varchar (255)
end

if col_length('pensionista', 'telefone_fixo') is null
begin
	ALTER TABLE pensionista ADD telefone_fixo varchar (255)
end

if col_length('pensionista', 'celular') is null
begin
	ALTER TABLE pensionista ADD celular varchar (255)
end

if col_length('pensionista', 'observacao') is null
begin
	ALTER TABLE pensionista ADD observacao varchar (255)
end

if col_length('pensionista', 'tipo_sanguineo') is null
begin
	ALTER TABLE pensionista ADD tipo_sanguineo varchar (255)
end
