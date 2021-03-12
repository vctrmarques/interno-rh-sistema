--Flávio Silva
--Drop das colunas de auditoria das tabelas Menu, Assunto Norma, Detalhamento Norma, Ente Federado, Norma, Texto Documento, Unidade Gestora.

ALTER TABLE menu DROP COLUMN created_at, updated_at, created_by, updated_by;

ALTER TABLE assunto_norma DROP COLUMN created_at, updated_at, created_by, updated_by;

ALTER TABLE detalhamento_norma DROP COLUMN created_at, updated_at, created_by, updated_by;

ALTER TABLE ente_federado DROP COLUMN created_at, updated_at, created_by, updated_by;

ALTER TABLE norma DROP COLUMN created_at, updated_at, created_by, updated_by;

ALTER TABLE texto_documento DROP COLUMN created_at, updated_at, created_by, updated_by;

ALTER TABLE unidade_gestora DROP COLUMN created_at, updated_at, created_by, updated_by;

