-- Criação de tabela categoria_economica
-- Davi Queiroz

-- Criação da tabela categoria_economica

CREATE TABLE categoria_economica (
	id bigint NOT NULL IDENTITY(1,1),
	codigo INT NOT NULL,
	descricao VARCHAR(255) NOT NULL,
	created_at datetime2(7) NOT NULL,
	updated_at datetime2(7) NOT NULL,
	created_by bigint,
	updated_by bigint
)
