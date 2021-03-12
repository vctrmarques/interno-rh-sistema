--Criação da tabela turno_folga.
--Wallace Nascimento.


CREATE TABLE turno_folga
(
  id bigint NOT NULL IDENTITY,
  turno_id bigint not null,
  dia varchar(10) not null,
  created_at datetime2 NOT NULL,
  updated_at datetime2 NOT NULL,
  created_by bigint,
  updated_by bigint,
  CONSTRAINT pk_turno_folga PRIMARY KEY (id),
  CONSTRAINT fk_turno_folga_turno_id FOREIGN KEY (turno_id) REFERENCES turno (id)
);
