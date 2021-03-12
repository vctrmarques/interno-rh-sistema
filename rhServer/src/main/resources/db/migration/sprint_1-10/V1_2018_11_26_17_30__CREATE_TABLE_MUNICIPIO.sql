-- Criação de tabela nacionalidade
-- Davi Queiroz

IF NOT EXISTS(SELECT * FROM SYS.TABLES WHERE name = 'municipio')
CREATE TABLE municipio (
	id bigint PRIMARY KEY NOT NULL  IDENTITY(1,1),
	descricao VARCHAR(255) NOT NULL,
	uf VARCHAR(255) NOT NULL,
	cep VARCHAR(255) NOT NULL,
	naturalidade VARCHAR(255) NOT NULL,
	regiao_fiscal VARCHAR(255) NOT NULL,
	created_at datetime2(7) NOT NULL,
	updated_at datetime2(7) NOT NULL,
	created_by bigint,
	updated_by bigint
)
