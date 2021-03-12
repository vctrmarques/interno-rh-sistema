-- Anderson Galindro
-- Copiando dados da coluna vigencia_final 
-- da tabela Definicao_de_organico para Empresa_filial

UPDATE ep SET ep.vigencia_final = do.vigencia_final FROM empresa_filial ep INNER JOIN definicao_de_organico do ON ep.id = do.empresa_filial_id
