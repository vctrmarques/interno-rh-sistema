-- Davi Queiroz

-- atualização do menu referente a referencia salarial


BEGIN IF (SELECT COUNT(id) FROM menu WHERE nome = 'Nível') > 0
    UPDATE menu SET nome = 'Referência Salarial' WHERE nome = 'Nível' 
END