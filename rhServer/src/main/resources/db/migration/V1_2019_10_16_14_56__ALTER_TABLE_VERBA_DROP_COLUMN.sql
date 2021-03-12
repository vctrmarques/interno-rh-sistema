-- Flávio Silva
-- Atributo incidencia valor foi descontinuado.

begin if col_length('verba', 'incidencia_valor') is not null
    alter table verba drop column incidencia_valor;
end

