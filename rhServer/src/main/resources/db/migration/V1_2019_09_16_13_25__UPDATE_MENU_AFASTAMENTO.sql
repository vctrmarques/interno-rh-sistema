-- Marconi Motta
-- Alteração dos Menus de Afastamento que passa a se chamar Situação Funcional
UPDATE menu SET nome = 'Situação Funcional' WHERE nome = 'Afastamento';
UPDATE menu SET url = '/situacaoFuncional/gestao' WHERE nome = 'Situação Funcional';