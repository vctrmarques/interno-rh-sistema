-- Criação de tabela requisito
-- Davi Queiroz

CREATE TABLE requisito (
  id bigint NOT NULL IDENTITY(1,1),
  descricao varchar(255) NOT NULL,
  dado_comparativo varchar(255) NOT NULL,
  comparacao varchar(255) NOT NULL,
  valor varchar(255),
  inicio_intervalo varchar(255),
  fim_intervalo varchar(255),
  created_at datetime2(7) NOT NULL,
  updated_at datetime2(7) NOT NULL,
  created_by bigint,
  updated_by bigint,
);