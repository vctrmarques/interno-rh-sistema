-- Railson Silva
-- add pk na tabela requisito

IF NOT EXISTS (SELECT * FROM information_schema.table_constraints WHERE constraint_type = 'PRIMARY KEY' AND table_name = 'requisito')
alter table requisito add primary key (id)