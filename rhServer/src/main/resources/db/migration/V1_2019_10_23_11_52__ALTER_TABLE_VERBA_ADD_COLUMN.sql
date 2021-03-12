-- Flávio Silva
-- Alteração da tabela verba para adicionar o atributo descricaoFormula.

if col_length('verba', 'descricaoFormula') is null
    begin
        alter table verba add descricaoFormula varchar(255);
    end