--Criação da tabela simulador_nivel_salarial.
--Wallace Nascimento

CREATE TABLE simulador_nivel_salarial (
  id bigint NOT NULL IDENTITY(1,1),
  descricao varchar(255) NOT NULL,
  data_competencia datetime2(7),
  situacao varchar(50),
  programar_ajuste bit,
  motivo_ajuste varchar(50) NOT NULL,
  tipo_ajuste varchar(50) NOT NULL,
  valor_ajuste float NOT NULL,
  data_ajuste datetime2(7),
  created_at datetime2(7) NOT NULL,
  updated_at datetime2(7) NOT NULL,
  created_by bigint,
  updated_by bigint,
  CONSTRAINT pk_simulador_nivel_salarial PRIMARY KEY (id)
) 



CREATE TABLE simulador_nivel_salarial_valores (
  id bigint NOT NULL IDENTITY(1,1),
  nivel_salarial_id bigint NOT NULL,
  simulador_id bigint NOT NULL,
  valor_ajustado float NOT null,
  valor_retroativo float null,
  created_at datetime2(7) NOT NULL,
  updated_at datetime2(7) NOT NULL,
  created_by bigint,
  updated_by bigint,
  CONSTRAINT pk_simulador_nivel_salarial_valores PRIMARY KEY (id),
  CONSTRAINT fk_simulador_nivel_salarial_valores FOREIGN KEY (simulador_id) REFERENCES simulador_nivel_salarial(id),
  CONSTRAINT fk_simulador_nivel_salarial_nivel FOREIGN KEY (nivel_salarial_id) REFERENCES nivel_salarial(id)
)





CREATE TABLE simulador_nivel_salarial_acordo (
  id bigint NOT NULL IDENTITY(1,1),
  simulador_id bigint NOT NULL,
  tipo_acordo varchar(100) NOT NULL,
  data_acordo datetime2(7) NULL,
  data_incial datetime2(7) NOT NULL,
  data_final datetime2(7) NOT NULL,
  descricao varchar(255),
  created_at datetime2(7) NOT NULL,
  updated_at datetime2(7) NOT NULL,
  created_by bigint,
  updated_by bigint,
  CONSTRAINT pk_simulador_nivel_salarial_acordo PRIMARY KEY (id),
  CONSTRAINT fk_simulador_nivel_salarial_acordo FOREIGN KEY (simulador_id) REFERENCES simulador_nivel_salarial(id)
)
;

