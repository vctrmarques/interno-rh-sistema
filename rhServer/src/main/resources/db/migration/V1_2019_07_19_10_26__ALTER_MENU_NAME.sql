-- Felipe Rios
-- Alteração dos Menus do Módulo de Avaliação de Desempenho

UPDATE menu
SET nome = 'Gestão de Avaliação'
WHERE categoria = 'MODULO_AVALIACAO'
  AND nome = 'Avaliação de Desempenho';

INSERT INTO menu
VALUES (GETDATE(), GETDATE(), 1, 1, 1, 'MODULO_AVALIACAO', 'Respostas e Resultados da Avaliação', '/respostasResultados/gestao')

GO