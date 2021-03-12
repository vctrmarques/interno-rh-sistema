-- Davi Queiroz

-- atualização do menu referente a referencia salarial


BEGIN IF (SELECT COUNT(id) FROM menu WHERE nome LIKE '%Referência%') > 0
    UPDATE menu SET url = '/referenciaSalarial/gestao' WHERE nome LIKE '%Referência%'
END