-- Autor: Rodrigo Leite
-- Script de mudança da categoria do menu

IF EXISTS(SELECT * FROM menu WHERE url = '/arrecadacaoAliquota/gestao')
	UPDATE menu SET nome = 'Alíquota e encagos', categoria = 'ARRECADACAO' WHERE url = '/arrecadacaoAliquota/gestao';