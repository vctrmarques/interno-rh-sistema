
-- Flávio Silva
-- Adição da coluna de pensao_alimenticia_id

IF COL_LENGTH('folha_pagamento_contracheque_verba', 'pensao_alimenticia_id') IS NULL
BEGIN
    ALTER TABLE folha_pagamento_contracheque_verba ADD pensao_alimenticia_id BIGINT;
    ALTER TABLE folha_pagamento_contracheque_verba ADD CONSTRAINT fk_folha_pagamento_contracheque_verba_pensao_alimenticia_id FOREIGN KEY (pensao_alimenticia_id) REFERENCES pensao_alimenticia(id)
END