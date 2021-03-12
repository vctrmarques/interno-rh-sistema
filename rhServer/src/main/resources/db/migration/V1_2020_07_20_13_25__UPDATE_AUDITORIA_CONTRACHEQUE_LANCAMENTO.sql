-- Flávio
-- Devido a atualização no nome dos objetos FolhaPagamentoContracheque e FolhaPagamentoContrachequeVerba, é necessário também atualizar o nome da entidade 
-- em auditorias passadas.

UPDATE auditoria SET entidade = 'com.rhlinkcon.model.Contracheque' WHERE entidade = 'com.rhlinkcon.model.FolhaPagamentoContracheque';
UPDATE auditoria SET entidade = 'com.rhlinkcon.model.Lancamento' WHERE entidade = 'com.rhlinkcon.model.FolhaPagamentoContrachequeVerba';