-- Flávio Silva
-- Criação da tabela de ImportadorLancamentoManual

IF NOT EXISTS (SELECT * FROM SYS.TABLES WHERE name = 'importador_lancamento_manual')
CREATE TABLE importador_lancamento_manual(
	id bigint not null identity(1,1),
	id_anexo bigint not null,
	folha_pagamento_id bigint not null,
	excluido BIT,
	created_at datetime2(7) NOT NULL,
	updated_at datetime2(7) NOT NULL,
	created_by bigint,
	updated_by bigint,
	CONSTRAINT pk_importador_lancamento_manual PRIMARY KEY (id),
	constraint fk_importador_lancamento_manual_id_anexo foreign key (id_anexo) references anexo(id),
	constraint fk_importador_lancamento_manual_folha_pagamento_id foreign key (folha_pagamento_id) references folha_pagamento(id)
)

