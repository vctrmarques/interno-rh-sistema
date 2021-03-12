
--Criação da tabela Grupo Salarial.
--Marconi Motta.

CREATE TABLE grupo_salarial
(
  id bigint NOT NULL IDENTITY,
  nome varchar(255),
  created_at datetime2 NOT NULL,
  updated_at datetime2 NOT NULL,
  created_by bigint,
  updated_by bigint
  CONSTRAINT pk_grupo_salarial PRIMARY KEY (id)
);

CREATE UNIQUE INDEX uk_grupo_salarial_nome ON grupo_salarial (nome);

--Criação da tabela Classe Salarial.
--Marconi Motta.

CREATE TABLE classe_salarial
(
  id bigint NOT NULL IDENTITY,
  nome varchar(255),
  id_grupo_salarial bigint not null,
  created_at datetime2 NOT NULL,
  updated_at datetime2 NOT NULL,
  created_by bigint,
  updated_by bigint
  CONSTRAINT pk_classe_salarial PRIMARY KEY (id),
  CONSTRAINT fk_id_grupo_salarial_classe_salarial FOREIGN KEY (id_grupo_salarial) REFERENCES grupo_salarial(id),
);

CREATE UNIQUE INDEX uk_classe_salarial_nome ON classe_salarial (nome);
