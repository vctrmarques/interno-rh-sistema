-- Marconi Motta
-- Criação da tabela relatório financeiro
IF NOT EXISTS(SELECT * FROM SYS.TABLES WHERE name = 'relatorio_financeiro_folha_pagamento_historico')
CREATE TABLE relatorio_financeiro_folha_pagamento (

	id bigint NOT NULL IDENTITY(1,1),
	descricao varchar(2000) NOT NULL,
	anexo_id BIGINT NOT NULL,
	historico_atual_id BIGINT NOT NULL,
	created_at datetime2(7) NOT NULL,
	updated_at datetime2(7) NOT NULL,
	created_by bigint,
	updated_by bigint,
	
	CONSTRAINT pk_relatorio_financeiro_folha_pagamento PRIMARY KEY (id),
	CONSTRAINT fk_relatorio_financeiro_folha_pagamento_anexo_id FOREIGN KEY (anexo_id) REFERENCES anexo(id)
) 


-- Criação da tabela relatorio financeiro folha pagamento historico 
IF NOT EXISTS(SELECT * FROM SYS.TABLES WHERE name = 'relatorio_financeiro_folha_pagamento_historico')
CREATE TABLE relatorio_financeiro_folha_pagamento_historico
(
    id 							BIGINT 					 	NOT NULL IDENTITY,
    relatorio_financeiro_folha_pagamento_id			BIGINT						NOT NULL,
    status					VARCHAR(100)				NOT NULL,
	created_at 					datetime2(7)	 			NOT NULL,
	updated_at 					datetime2(7) 				NOT NULL,
	created_by 					bigint						NULL,
	updated_by 					bigint						NULL,
	
	CONSTRAINT pk_relatorio_financeiro_folha_pagamento_historico PRIMARY KEY (id),
	CONSTRAINT fk_relatorio_financeiro_folha_pagamento_id FOREIGN KEY (relatorio_financeiro_folha_pagamento_id) REFERENCES relatorio_financeiro_folha_pagamento(id)
)

ALTER TABLE relatorio_financeiro_folha_pagamento ADD CONSTRAINT fk_relatorio_financeiro_folha_pagamento_rffph_id FOREIGN KEY (historico_atual_id) REFERENCES relatorio_financeiro_folha_pagamento_historico (id);


IF NOT EXISTS(SELECT * FROM SYS.TABLES WHERE name = 'relatorio_financeiro_folha_pagamento_filial')
CREATE TABLE relatorio_financeiro_folha_pagamento_filial
(
    relatorio_financeiro_folha_pagamento_id			BIGINT						NOT NULL,
	empresa_filial_id					BIGINT						NOT NULL,
	CONSTRAINT pk_relatorio_financeiro_folha_pagamento_filial_relatorio_id PRIMARY KEY (relatorio_financeiro_folha_pagamento_id),
	CONSTRAINT fk_relatorio_financeiro_folha_pagamento_filial_relatorio_id FOREIGN KEY (relatorio_financeiro_folha_pagamento_id) REFERENCES relatorio_financeiro_folha_pagamento(id),
	CONSTRAINT fk_relatorio_financeiro_folha_pagamento_filial_filial_id FOREIGN KEY (empresa_filial_id) REFERENCES empresa_filial(id)
)

IF NOT EXISTS(SELECT * FROM SYS.TABLES WHERE name = 'relatorio_financeiro_folha_pagamento_lotacao')
CREATE TABLE relatorio_financeiro_folha_pagamento_lotacao
(
    relatorio_financeiro_folha_pagamento_id			BIGINT						NOT NULL,
	lotacao_id					BIGINT						NOT NULL,
	CONSTRAINT pk_relatorio_financeiro_folha_pagamento_lotacao_relatorio_id PRIMARY KEY (relatorio_financeiro_folha_pagamento_id),
	CONSTRAINT fk_relatorio_financeiro_folha_pagamento_lotacao_relatorio_id FOREIGN KEY (relatorio_financeiro_folha_pagamento_id) REFERENCES relatorio_financeiro_folha_pagamento(id),
	CONSTRAINT fk_relatorio_financeiro_folha_pagamento_lotacao_lotacao_id FOREIGN KEY (lotacao_id) REFERENCES lotacao(id)
)

IF NOT EXISTS(SELECT * FROM SYS.TABLES WHERE name = 'relatorio_financeiro_folha_pagamento_situacao_funcional')
CREATE TABLE relatorio_financeiro_folha_pagamento_situacao_funcional
(
    relatorio_financeiro_folha_pagamento_id			BIGINT						NOT NULL,
	situacao_funcional_id					BIGINT						NOT NULL,
	CONSTRAINT pk_relatorio_financeiro_folha_pagamento_situacao_funcional_relatorio_id PRIMARY KEY (relatorio_financeiro_folha_pagamento_id),
	CONSTRAINT fk_relatorio_financeiro_folha_pagamento_situacao_funcional_relatorio_id FOREIGN KEY (relatorio_financeiro_folha_pagamento_id) REFERENCES relatorio_financeiro_folha_pagamento(id),
	CONSTRAINT fk_relatorio_financeiro_folha_pagamento_situacao_funcional_situacao_funcional_id FOREIGN KEY (situacao_funcional_id) REFERENCES situacao_funcional(id)
)

