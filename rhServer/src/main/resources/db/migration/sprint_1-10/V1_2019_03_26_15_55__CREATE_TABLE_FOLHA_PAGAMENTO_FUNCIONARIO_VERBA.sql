-- Criação da tabela de folha_pagamento_funcionario_verba
-- Railson Silva


IF NOT EXISTS (SELECT * FROM SYS.TABLES WHERE name = 'folha_pagamento_funcionario_verba')
CREATE TABLE folha_pagamento_funcionario_verba(
	id bigint not null identity(1,1),
	funcionario_id bigint not null,
	folha_pagamento_id bigint not null,
	verba_id bigint,
	valor_referencia float,
	created_at datetime2(7) NOT NULL,
	updated_at datetime2(7) NOT NULL,
	created_by bigint,
	updated_by bigint,
	CONSTRAINT pk_folha_pagamento_funcionario_verba PRIMARY KEY (id),
	constraint fk_folha_pagamento_funcionario_verba_funcionario foreign key (funcionario_id) references funcionario(id),
	constraint fk_folha_pagamento_funcionario_verba_verba foreign key (verba_id) references verba(id),
	constraint fk_folha_pagamento_funcionario_verba_folha foreign key (folha_pagamento_id) references folha_pagamento(id)
)