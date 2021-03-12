-- Criação da tabela de folha de pagamento
-- Marconi Motta


IF NOT EXISTS (SELECT * FROM SYS.TABLES WHERE name = 'folha_pagamento')
CREATE TABLE folha_pagamento(
	id bigint not null identity(1,1),
	tipo_folha_id bigint not null,
	mes_competencia date,
	periodo_processamento_inicio datetime2(7) not null,
	periodo_processamento_fim datetime2(7),
	status varchar(255) not null,
	empresa_filial_id bigint not null,
	fechamento_competencia datetime2(7),
	created_at datetime2(7) NOT NULL,
	updated_at datetime2(7) NOT NULL,
	created_by bigint,
	updated_by bigint,
	CONSTRAINT pk_folha_pagamento PRIMARY KEY (id),
	CONSTRAINT fk_folha_pagamento_tipo_folha FOREIGN KEY (tipo_folha_id) REFERENCES tipo_folha(id),
	CONSTRAINT fk_folha_pagamento_empresa_filial FOREIGN KEY (empresa_filial_id) REFERENCES empresa_filial(id),
)

IF NOT EXISTS (SELECT * FROM SYS.TABLES WHERE name = 'folha_pagamento_lotacao')
CREATE TABLE folha_pagamento_lotacao(
	folha_pagamento_id bigint not null,
	lotacao_id bigint not null,
	CONSTRAINT pk_folha_pagamento_lotacao_folha PRIMARY KEY (folha_pagamento_id,lotacao_id),
	CONSTRAINT fk_folha_pagamento_folha FOREIGN KEY (folha_pagamento_id) REFERENCES folha_pagamento(id),
	CONSTRAINT fk_folha_pagamento_lotacao FOREIGN KEY (lotacao_id) REFERENCES lotacao(id)
)
