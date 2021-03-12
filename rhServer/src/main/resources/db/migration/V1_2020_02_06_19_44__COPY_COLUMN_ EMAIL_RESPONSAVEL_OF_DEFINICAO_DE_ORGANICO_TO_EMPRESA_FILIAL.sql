-- Anderson Galindro
-- Copiando dados da coluna email_responsavel 
-- da tabela Definicao_de_organico para Empresa_filial

UPDATE ep SET ep.email_responsavel = do.email_responsavel FROM empresa_filial ep INNER JOIN definicao_de_organico do ON ep.id = do.empresa_filial_id