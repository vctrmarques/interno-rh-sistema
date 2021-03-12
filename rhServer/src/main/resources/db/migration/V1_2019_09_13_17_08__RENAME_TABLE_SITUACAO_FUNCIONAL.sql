-- Marconi Motta
-- Alteração da tabela de situacao_funcional para historico_situacao_funcional
EXEC sp_rename 'dbo.situacao_funcional', 'historico_situacao_funcional'; 