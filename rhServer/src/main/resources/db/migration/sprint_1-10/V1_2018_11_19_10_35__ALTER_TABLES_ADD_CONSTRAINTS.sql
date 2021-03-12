--Criação da tabela curso. 
--Davi Queiroz
ALTER TABLE area_formacao
ADD CONSTRAINT pk_area_formacao PRIMARY KEY (id);

ALTER TABLE atividade
ADD CONSTRAINT pk_atividade PRIMARY KEY (id);

ALTER TABLE categoria_economica
ADD CONSTRAINT pk_categoria_economica PRIMARY KEY (id);