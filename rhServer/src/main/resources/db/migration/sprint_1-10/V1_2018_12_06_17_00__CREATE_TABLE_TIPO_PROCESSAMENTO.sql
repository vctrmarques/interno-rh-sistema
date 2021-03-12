-- Criação de tabela categoria_doenca
-- Wallace Nascimento

CREATE TABLE tipo_processamento (
  id bigint NOT NULL IDENTITY(1,1),
  codigo int NOT NULL,
  descricao varchar(255) NOT NULL,
  created_at datetime2(7) NOT NULL,
  updated_at datetime2(7) NOT NULL,
  created_by bigint,
  updated_by bigint,
  CONSTRAINT pk_tipo_processamento PRIMARY KEY (id)
);

CREATE UNIQUE INDEX uk_tipo_processamento_codigo ON tipo_processamento (codigo);
CREATE UNIQUE INDEX uk_tipo_processamento_descricao ON tipo_processamento (descricao);
