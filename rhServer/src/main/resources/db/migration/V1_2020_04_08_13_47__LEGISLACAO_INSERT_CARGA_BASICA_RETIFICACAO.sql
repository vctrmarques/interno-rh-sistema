-- Flávio Silva
-- Inserindo novamente os dados da tabela de norma com o código correto, conforme a tabela fornecida na documentação

DELETE FROM norma;

INSERT INTO norma (created_at, updated_at, created_by, updated_by, codigo, descricao, vigencia) VALUES (SYSDATETIME(), SYSDATETIME(), 1, 1, 5, 'Lei Orgânica Municipal', '2017-01-01 00:00:00.000');
INSERT INTO norma (created_at, updated_at, created_by, updated_by, codigo, descricao, vigencia) VALUES (SYSDATETIME(), SYSDATETIME(), 1, 1, 6, 'Emenda à Lei Orgânica Municipal', '2017-01-01 00:00:00.000');
INSERT INTO norma (created_at, updated_at, created_by, updated_by, codigo, descricao, vigencia) VALUES (SYSDATETIME(), SYSDATETIME(), 1, 1, 7, 'Lei Complementar', '2017-01-01 00:00:00.000');
INSERT INTO norma (created_at, updated_at, created_by, updated_by, codigo, descricao, vigencia) VALUES (SYSDATETIME(), SYSDATETIME(), 1, 1, 8, 'Lei Ordinária', '2017-01-01 00:00:00.000');
INSERT INTO norma (created_at, updated_at, created_by, updated_by, codigo, descricao, vigencia) VALUES (SYSDATETIME(), SYSDATETIME(), 1, 1, 9, 'LDO', '2017-01-01 00:00:00.000');
INSERT INTO norma (created_at, updated_at, created_by, updated_by, codigo, descricao, vigencia) VALUES (SYSDATETIME(), SYSDATETIME(), 1, 1, 10, 'Decreto-lei', '2017-01-01 00:00:00.000');
INSERT INTO norma (created_at, updated_at, created_by, updated_by, codigo, descricao, vigencia) VALUES (SYSDATETIME(), SYSDATETIME(), 1, 1, 11, 'Decreto Executivo', '2017-01-01 00:00:00.000');
INSERT INTO norma (created_at, updated_at, created_by, updated_by, codigo, descricao, vigencia) VALUES (SYSDATETIME(), SYSDATETIME(), 1, 1, 12, 'Resolução', '2017-01-01 00:00:00.000');
INSERT INTO norma (created_at, updated_at, created_by, updated_by, codigo, descricao, vigencia) VALUES (SYSDATETIME(), SYSDATETIME(), 1, 1, 13, 'Decreto Legislativo', '2017-01-01 00:00:00.000');
INSERT INTO norma (created_at, updated_at, created_by, updated_by, codigo, descricao, vigencia) VALUES (SYSDATETIME(), SYSDATETIME(), 1, 1, 14, 'Portaria', '2017-01-01 00:00:00.000');
INSERT INTO norma (created_at, updated_at, created_by, updated_by, codigo, descricao, vigencia) VALUES (SYSDATETIME(), SYSDATETIME(), 1, 1, 15, 'Convenção / Acordo Trabalho', '2017-01-01 00:00:00.000');
INSERT INTO norma (created_at, updated_at, created_by, updated_by, codigo, descricao, vigencia) VALUES (SYSDATETIME(), SYSDATETIME(), 1, 1, 16, 'Instrução Normativa', '2017-01-01 00:00:00.000');
INSERT INTO norma (created_at, updated_at, created_by, updated_by, codigo, descricao, vigencia) VALUES (SYSDATETIME(), SYSDATETIME(), 1, 1, 17, 'Norma Empresa Pública', '2017-01-01 00:00:00.000');
