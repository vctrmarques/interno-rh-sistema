
-- Flávio Silva
-- Adição das colunas de funcionario_verba_id, pensionista_verba_id e numero_parcella na tabela de lancamento.

IF COL_LENGTH('folha_pagamento_contracheque_verba', 'funcionario_verba_id') IS NULL
BEGIN
    ALTER TABLE folha_pagamento_contracheque_verba ADD funcionario_verba_id BIGINT;
    ALTER TABLE folha_pagamento_contracheque_verba ADD CONSTRAINT fk_folha_pagamento_contracheque_verba_funcionario_verba_id FOREIGN KEY (funcionario_verba_id) REFERENCES funcionario_verba(id)
END

IF COL_LENGTH('folha_pagamento_contracheque_verba', 'pensionista_verba_id') IS NULL
BEGIN
    ALTER TABLE folha_pagamento_contracheque_verba ADD pensionista_verba_id BIGINT;
    ALTER TABLE folha_pagamento_contracheque_verba ADD CONSTRAINT fk_folha_pagamento_contracheque_verba_pensionista_verba_id FOREIGN KEY (pensionista_verba_id) REFERENCES pensionista_verba(id)
END

IF COL_LENGTH('folha_pagamento_contracheque_verba', 'numero_parcela') IS NULL
BEGIN
    ALTER TABLE folha_pagamento_contracheque_verba ADD numero_parcela INT NULL;
END

