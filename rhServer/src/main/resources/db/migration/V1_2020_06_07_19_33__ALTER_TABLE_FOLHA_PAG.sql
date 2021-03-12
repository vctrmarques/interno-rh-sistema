-- Flávio Silva
-- Adição de colunas de controle de processamento.

if col_length('folha_pagamento', 'situacao') is null
begin
	ALTER TABLE folha_pagamento ADD situacao VARCHAR(100) NOT NULL DEFAULT('CONCLUIDO');
end

if col_length('folha_pagamento', 'processamentos') is null
begin
	ALTER TABLE folha_pagamento ADD processamentos BIGINT NULL;
end