
-- Flávio Silva
-- Atualização do valor chave de ítens de fórmula. Atualização de atributos para contemplar funcionário e pensionista aniversariantes e isentos de IR e Previdencia.

UPDATE item_formula SET valor = 'titular.aniversariante' WHERE valor = 'funcionario.mesAniversario';
UPDATE item_formula SET valor = 'titular.isentoIr' WHERE valor = 'funcionario.isentoIr';
UPDATE item_formula SET valor = 'titular.isentoPrevidencia' WHERE valor = 'funcionario.isentoPrevidencia';
