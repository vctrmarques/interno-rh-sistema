
--Criação da tabela nivel_salarial.
--Wallace Nascimento.

CREATE TABLE nivel_salarial
(
  id bigint NOT NULL IDENTITY,
  codigo int NOT NULL,
  descricao varchar(255),
  valor int not null,
  nivel_referencia bit,
  classe_salarial int not null,
  ordem int not null,
  created_at datetime2 NOT NULL,
  updated_at datetime2 NOT NULL,
  created_by bigint,
  updated_by bigint
  CONSTRAINT pk_nivel_salarial PRIMARY KEY (id)
);

CREATE UNIQUE INDEX uk_nivel_salarial_codigo ON nivel_salarial (codigo);
