-- Felipe Rios
-- Criação da Tabela de Item da Avaliação

IF NOT EXISTS(SELECT *
			  FROM sysobjects
			  WHERE name = 'avaliacao_item'
				AND xtype = 'U')
CREATE TABLE avaliacao_item
(
	id                      BIGINT IDENTITY
		CONSTRAINT avaliacao_item_pk
			PRIMARY KEY NONCLUSTERED,
	avaliacao_desempenho_id BIGINT       NOT NULL
		CONSTRAINT fk_avaliacao_item_avaliacao_desempenho_id
			REFERENCES avaliacao_desempenho,
	cod_item_avaliacao      VARCHAR(255) NOT NULL,
	nome                    VARCHAR(255) NOT NULL,
	seq_avaliacao           VARCHAR(2)   NOT NULL,
	descricao               VARCHAR(255) NOT NULL,
	tipo_questao            VARCHAR(50)  NOT NULL,
	forma_avaliacao         VARCHAR(50)  NOT NULL,
	created_at              DATETIME2    NOT NULL,
	updated_at              DATETIME2    NOT NULL,
	created_by              BIGINT       NOT NULL,
	updated_by              BIGINT       NOT NULL
)
GO

IF NOT EXISTS(SELECT *
			  FROM sysobjects
			  WHERE name = 'avaliacao_item'
				AND xtype = 'U')
CREATE UNIQUE INDEX avaliacao_item_id_uindex
	ON avaliacao_item (id)
GO