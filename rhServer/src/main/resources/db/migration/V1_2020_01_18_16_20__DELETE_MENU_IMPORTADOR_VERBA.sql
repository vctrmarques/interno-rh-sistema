-- Exclusão do menu 'Importador Verbas do Funcionário'. Conforme documentação, o acesso a este menu se dará via menu interno.
-- Flávio Silva

DELETE FROM menu WHERE nome = 'Importador Verbas do Funcionário' or categoria = 'ROTINA_PAGAMENTO';