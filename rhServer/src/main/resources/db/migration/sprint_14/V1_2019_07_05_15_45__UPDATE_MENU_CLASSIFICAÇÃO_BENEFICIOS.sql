-- Marconi Motta
-- Alteração de Menu Classificação dos Benefícios -> Classificação do Ato

UPDATE menu set nome = 'Classificações dos Atos', url = '/classificacaoAto/gestao' where nome = 'Classificações dos Benefícios';