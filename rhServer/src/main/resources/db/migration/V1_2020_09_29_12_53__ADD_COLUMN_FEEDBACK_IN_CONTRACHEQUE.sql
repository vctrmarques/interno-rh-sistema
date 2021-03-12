-- Flávio Silva
-- Adição da coluna feedback para exibir o detalhamento do processamento do contracheque.

IF COL_LENGTH('folha_pagamento_contracheque', 'feedback') IS NULL
    BEGIN
        ALTER TABLE folha_pagamento_contracheque ADD feedback VARCHAR(MAX) NULL;
    END


