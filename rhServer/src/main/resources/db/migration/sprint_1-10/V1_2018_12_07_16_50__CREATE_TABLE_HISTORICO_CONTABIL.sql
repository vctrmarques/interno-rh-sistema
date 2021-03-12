-- Criação de tabela historico_contabil
-- Davi Queiroz

CREATE TABLE historico_contabil (
  id bigint NOT NULL IDENTITY(1,1),
  codigo int NOT NULL,
  descricao varchar(255) NOT NULL,
  created_at datetime2(7) NOT NULL,
  updated_at datetime2(7) NOT NULL,
  created_by bigint,
  updated_by bigint,
);
