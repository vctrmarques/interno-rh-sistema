-- Criação de tabela nacionalidade
-- Davi Queiroz

IF NOT EXISTS(SELECT * FROM SYS.TABLES WHERE name = 'nacionalidade')
CREATE TABLE nacionalidade (
	id bigint PRIMARY KEY NOT NULL  IDENTITY(1,1),
	sigla VARCHAR(255) NOT NULL,
	nacionalidade_feminina VARCHAR(255) NOT NULL,
	nacionalidade_masculina VARCHAR(255) NOT NULL,
	created_at datetime2(7) NOT NULL,
	updated_at datetime2(7) NOT NULL,
	created_by bigint,
	updated_by bigint
)
