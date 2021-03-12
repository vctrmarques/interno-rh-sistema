-- Ajuste no atributo percentual_rateio, renomeando.
-- Flávio Silva

IF COL_LENGTH('pensao_previdenciaria_pagamento', 'percentual_rateio') IS NOT NULL
    begin
	    exec sp_RENAME 'pensao_previdenciaria_pagamento.percentual_rateio', 'valor_rateio' , 'COLUMN';
    end