--Criação da tabela dias uteis.
--Lucas Moura.

IF NOT EXISTS(SELECT * FROM SYS.TABLES WHERE name = 'dia_util')
CREATE TABLE dia_util
(
  id bigint NOT NULL IDENTITY,
  ano varchar(4) NOT NULL,
  mes varchar(2) NOT NULL,
  dia varchar(2) NOT NULL,
  dia_da_semana varchar(15) NOT NULL,
  created_at datetime2 NOT NULL,
  updated_at datetime2 NOT NULL,
  created_by bigint,
  updated_by bigint
);