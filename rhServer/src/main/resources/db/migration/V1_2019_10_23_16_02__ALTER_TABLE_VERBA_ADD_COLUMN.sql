-- Flávio Silva
-- Alteração da tabela verba para adicionar o atributo descricao_formula.

if col_length('verba', 'descricao_formula') is null
    begin
        alter table verba add descricao_formula varchar(255);
    end
    
if col_length('verba', 'descricaoFormula') is not null
    begin
        alter table verba drop column descricaoFormula;
    end