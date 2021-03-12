-- Flávio Silva
-- Alteração da tabela verba para adicionar o atributo formula.

if col_length('verba', 'formula') is null
    begin
        alter table verba add formula varchar(2000);
    end