
-- Anderson Galindro
-- Adição da coluna digito e operaçao na tabela pensao_previdenciaria_pagamento

IF COL_LENGTH('pensao_previdenciaria_pagamento', 'digito') IS NULL
BEGIN ALTER TABLE pensao_previdenciaria_pagamento ADD digito varchar;
END

IF COL_LENGTH('pensao_previdenciaria_pagamento', 'operacao') IS NULL
BEGIN ALTER TABLE pensao_previdenciaria_pagamento ADD operacao varchar;
END