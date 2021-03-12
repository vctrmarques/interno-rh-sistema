-- Ajuste no enum de tipo de valor para obedecer o padrão em verba de funcioonário e verba de pensionista.
-- Flávio Silva

UPDATE pensao_alimenticia SET tipo_desconto = 'MOEDA' WHERE tipo_desconto = 'VALOR_FIXO';
