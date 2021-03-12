--Criação da tabela turno.
--Wallace Nascimento.


CREATE TABLE turno
(
  id bigint NOT NULL IDENTITY,
  codigo varchar(20) NOT NULL,
  data_inicio datetime2 NOT NULL,
  data_fim datetime2 NOT NULL,
  horario_flexivel  bit,
  horario_flexivel_inicio datetime2,
  horario_flexivel_fim datetime2,
  jornada FLOAT,
  intervalo_flexivel  bit,
  intervalo FLOAT,
  intervalo_flexivel_inicio datetime2,
  intervalo_flexivel_fim datetime2,
  created_at datetime2 NOT NULL,
  updated_at datetime2 NOT NULL,
  created_by bigint,
  updated_by bigint
  CONSTRAINT pk_turno PRIMARY KEY (id)
);

CREATE UNIQUE INDEX uk_turno_codigo ON turno (codigo);
