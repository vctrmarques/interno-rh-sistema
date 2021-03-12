-- João Marques
-- Adição da coluna entra folha na tabela situação funcinal 

if col_length('motivo_afastamento', 'entra_folha') is null
    begin
        alter table motivo_afastamento add entra_folha BIT NOT NULL DEFAULT(0);
    end
	