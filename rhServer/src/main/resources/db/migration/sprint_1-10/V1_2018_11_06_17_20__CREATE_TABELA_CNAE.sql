-- Criação de tabela cnae
-- Wallace Nascimento

CREATE TABLE cnae (
  id bigint NOT NULL IDENTITY(1,1),
  created_at datetime2(7) NOT NULL,
  updated_at datetime2(7) NOT NULL,
  created_by bigint,
  updated_by bigint,
  codigo_secao varchar(1) NOT NULL,
  nome_secao varchar(200) NOT NULL,
  codigo_classe bigint NOT NULL, 
  nome_classe varchar(200) NOT NULL,
  CONSTRAINT pk_cnae PRIMARY KEY (id)
);
