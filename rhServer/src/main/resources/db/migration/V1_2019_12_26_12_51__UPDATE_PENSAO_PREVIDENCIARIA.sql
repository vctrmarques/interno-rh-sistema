-- Anderson Galindro
-- 26/12/2019 
-- adicionar a coluna status na tabela pensao_previdenciaria

if col_length('pensao_previdenciaria','status') is null 
	begin 
		ALTER TABLE pensao_previdenciaria ADD status bit NULL;
	end
