-- Criação de tabela tomador_servico
-- Davi Queiroz

IF NOT EXISTS(SELECT * FROM SYS.TABLES WHERE name = 'tomador_servico')
CREATE TABLE tomador_servico (
	id bigint PRIMARY KEY NOT NULL  IDENTITY(1,1),
	tipo_inscricao VARCHAR(255) NOT NULL,
	razao_social VARCHAR(255) NOT NULL,
	cnpj VARCHAR(255) NOT NULL,
	id_codigo_pagamento_gps BIGINT,
	id_codigo_recolhimento BIGINT,
	estado VARCHAR(255) NOT NULL,
	cidade VARCHAR(255) NOT NULL,
	cep VARCHAR(255) NOT NULL,
	logradouro VARCHAR(255) NOT NULL,
	complemento VARCHAR(255) NOT NULL,
	numero VARCHAR(255) NOT NULL,
	bairro VARCHAR(255) NOT NULL,
	created_at datetime2(7) NOT NULL,
	updated_at datetime2(7) NOT NULL,
	created_by bigint,
	updated_by bigint,
	CONSTRAINT fk_id_codigo_pagamento_gps FOREIGN KEY (id_codigo_pagamento_gps) REFERENCES codigo_pagamento_gps(id),
	CONSTRAINT fk_id_codigo_recolhimento FOREIGN KEY (id_codigo_recolhimento) REFERENCES codigo_recolhimento(id)	
)
