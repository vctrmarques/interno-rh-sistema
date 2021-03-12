-- Marconi Motta
-- Alteração do menu Situação Funcional para Histórico de Situação Funcional do módulo de RH
UPDATE menu SET nome = 'Histórico Situação Funcional' WHERE nome = 'Situação Funcional';
UPDATE menu SET url = '/historicoSituacaoFuncional/gestao' WHERE nome = 'Histórico Situação Funcional';