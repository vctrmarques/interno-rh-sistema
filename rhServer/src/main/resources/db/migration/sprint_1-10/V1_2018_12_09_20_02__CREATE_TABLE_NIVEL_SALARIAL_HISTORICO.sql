
--Criação da tabela nivel_salarial_historico.
--Wallace Nascimento.

CREATE TABLE nivel_salarial_historico
(
  id bigint NOT NULL IDENTITY,
  nivel_salarial_id bigint NOT NULL,
  descricao varchar(255),
  valor int not null,
  created_at datetime2 NOT NULL,
  updated_at datetime2 NOT NULL,
  created_by bigint,
  updated_by bigint
  CONSTRAINT pk_nivel_salarial_historico PRIMARY KEY (id),
  CONSTRAINT fk_historico_nivel_salarial_id FOREIGN KEY (nivel_salarial_id) REFERENCES nivel_salarial (id)
);

