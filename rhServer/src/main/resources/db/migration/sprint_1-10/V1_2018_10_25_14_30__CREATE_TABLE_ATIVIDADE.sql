-- Criação de tabela atividade
-- Davi Queiroz

-- Criação da tabela atividade

CREATE TABLE atividade (
	id bigint NOT NULL IDENTITY(1,1),
	codigo INT NOT NULL,
	descricao VARCHAR(255) NOT NULL,
	observacao VARCHAR(255),
	created_at datetime2(7) NOT NULL,
	updated_at datetime2(7) NOT NULL,
	created_by bigint,
	updated_by bigint
)
