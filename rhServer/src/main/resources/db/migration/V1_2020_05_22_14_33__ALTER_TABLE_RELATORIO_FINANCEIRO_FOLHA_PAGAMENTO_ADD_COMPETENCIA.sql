-- Adição da coluna competencia_id
-- Marconi Motta

IF COL_LENGTH('relatorio_financeiro_folha_pagamento', 'folha_competencia_id') IS NULL
BEGIN
    ALTER TABLE relatorio_financeiro_folha_pagamento ADD folha_competencia_id BIGINT NULL;
    ALTER TABLE relatorio_financeiro_folha_pagamento ADD CONSTRAINT fk_relatorio_financeiro_folha_pagamento_folha_competencia_id FOREIGN KEY (folha_competencia_id) REFERENCES folha_competencia(id);
END
