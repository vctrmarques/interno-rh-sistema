-- Flávio Silva
-- Criação da tabela de FolhaPagamentoContracheque

IF NOT EXISTS (SELECT * FROM SYS.TABLES WHERE name = 'folha_pagamento_contracheque')
CREATE TABLE folha_pagamento_contracheque(
	id bigint not null identity(1,1),
	funcionario_id bigint not null,
	folha_pagamento_id bigint not null,
	valorBruto float,
	valorLiquido float,
	valorDesconto float,
	created_at datetime2(7) NOT NULL,
	updated_at datetime2(7) NOT NULL,
	created_by bigint,
	updated_by bigint,
	CONSTRAINT pk_folha_pagamento_contracheque PRIMARY KEY (id),
	constraint fk_folha_pagamento_contracheque_funcionario foreign key (funcionario_id) references funcionario(id),
	constraint fk_folha_pagamento_contracheque_folha foreign key (folha_pagamento_id) references folha_pagamento(id)
)

