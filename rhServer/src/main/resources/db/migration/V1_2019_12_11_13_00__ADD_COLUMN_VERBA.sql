--Flávio Silva
--Adição da coluna formula_nova, que vai depreciar a coluna formula, na tabela de verba.

if col_length('verba', 'formula_nova') is null
    begin
        alter table verba add formula_nova varchar(4000);
    end
    