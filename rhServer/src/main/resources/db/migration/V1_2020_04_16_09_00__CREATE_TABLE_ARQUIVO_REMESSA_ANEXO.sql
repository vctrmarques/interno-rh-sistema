--Script para criar tabela relacional com anexos

--Rodrigo Leite

IF NOT EXISTS(SELECT * FROM SYS.TABLES WHERE name = 'arquivo_remessa_pagamento_anexo')
CREATE TABLE arquivo_remessa_pagamento_anexo
(
    arquivo_remessa_id			BIGINT						NOT NULL,
    anexo_id					BIGINT						NOT NULL,
	
	CONSTRAINT fk_arquivo_remessa_pagamento_anexo_arquivo_remessa FOREIGN KEY (arquivo_remessa_id) REFERENCES arquivo_remessa_pagamento(id),
	CONSTRAINT fk_arquivo_remessa_pagamento_anexo_anexo FOREIGN KEY (anexo_id) REFERENCES anexo(id)
)