-- Trocando os tipos dos campo na tabela pensao_previdenciaria_pagamento
-- João Marques

ALTER TABLE pensao_previdenciaria_pagamento ALTER COLUMN percentual_rateio float NULL
ALTER TABLE pensao_previdenciaria_pagamento ALTER COLUMN numero_conta VARCHAR(20) NULL