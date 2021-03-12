-- Júlio Galvão

-- alteração da tabela de adiantamento de pagamento, add column 'competencia'

if col_length('adiantamento_pagamento', 'competencia') is null
	alter table adiantamento_pagamento add competencia varchar(8);

