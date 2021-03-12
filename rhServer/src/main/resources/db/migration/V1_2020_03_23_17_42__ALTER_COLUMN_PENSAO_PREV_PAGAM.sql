-- Flávio Silva
-- Correção no tamanho dos campos dígito e operação

IF COL_LENGTH('pensao_previdenciaria_pagamento', 'digito') IS NOT NULL
BEGIN 
	ALTER TABLE pensao_previdenciaria_pagamento ALTER COLUMN digito varchar(255) NULL;
END

IF COL_LENGTH('pensao_previdenciaria_pagamento', 'operacao') IS NOT NULL
BEGIN 
	ALTER TABLE pensao_previdenciaria_pagamento ALTER COLUMN operacao varchar(255) NULL;
END
