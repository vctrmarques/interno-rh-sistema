-- Adição da coluna entra folha na tabela situação funcinal 
-- Flávio Silva

if col_length('situacao_funcional', 'entra_folha') is null
    begin
        alter table situacao_funcional add entra_folha BIT NOT NULL DEFAULT(0);
    end