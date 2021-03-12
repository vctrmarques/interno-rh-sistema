-- Criação de tabela categoria_doenca
-- Wallace Nascimento

CREATE TABLE categoria_doenca (
  id bigint NOT NULL IDENTITY(1,1),
  created_at datetime2(7) NOT NULL,
  updated_at datetime2(7) NOT NULL,
  created_by bigint,
  updated_by bigint,
  codigo int NOT NULL,
  ativo bit,
  descricao varchar(255) NOT NULL,
  CONSTRAINT pk_categoria_doenca PRIMARY KEY (id)
);

CREATE UNIQUE INDEX uk_categoria_doenca_codigo ON categoria_doenca (codigo);
CREATE UNIQUE INDEX uk_categoria_doenca_descricao ON categoria_doenca (descricao);
