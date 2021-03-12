--Alteração da tabela Folha Pagamento, renomeando colunas. 
--Marconi Motta.
exec sp_RENAME 'folha_pagamento.tipo_folha_id', 'tipo_processamento_id' , 'COLUMN';

BEGIN IF COL_LENGTH('folha_pagamento', 'tipo_processamento_id') IS NOT NULL
    ALTER TABLE folha_pagamento
    DROP CONSTRAINT fk_folha_pagamento_tipo_folha;
    
    ALTER TABLE folha_pagamento ADD CONSTRAINT fk_folha_pagamento_tipo_processamento FOREIGN KEY (tipo_processamento_id) REFERENCES tipo_processamento(id);
END