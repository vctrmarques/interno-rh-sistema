-- Rodrigo Leite
 

-- Criação das tabelas relacionadas arquivo remessa pagamento.
IF NOT EXISTS(SELECT * FROM SYS.TABLES WHERE name = 'arquivo_remessa_pagamento')
CREATE TABLE arquivo_remessa_pagamento
(
    id 							BIGINT 					 	NOT NULL IDENTITY,
    folha_pagamento_id			BIGINT						NOT NULL,
    numero_remessa				INT							NOT NULL,
    data_pagamento				DATE						NOT NULL,
    situacao					VARCHAR(100)				NOT NULL,
    motivo						VARCHAR(MAX)				NULL,
	created_at 					datetime2(7)	 			NOT NULL,
	updated_at 					datetime2(7) 				NOT NULL,
	created_by 					bigint						NULL,
	updated_by 					bigint						NULL,
	
	CONSTRAINT pk_arquivo_remessa_pagamento PRIMARY KEY (id),
	CONSTRAINT fk_arquivo_remessa_pagamento_folha_pagamento FOREIGN KEY (folha_pagamento_id) REFERENCES folha_pagamento(id)
)

-- Insere dados no menu
IF NOT EXISTS(SELECT * FROM menu WHERE url = '/arquivoRemessaPagamento/gestao')
	INSERT INTO menu VALUES(SYSDATETIME(), SYSDATETIME(), 1, 1, 1, 'FOLHA_PAGAMENTO', 'Arquivo remessa pagamento', '/arquivoRemessaPagamento/gestao')