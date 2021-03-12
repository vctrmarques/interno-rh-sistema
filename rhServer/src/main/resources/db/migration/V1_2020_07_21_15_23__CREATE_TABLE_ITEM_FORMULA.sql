-- Criação da tabela item_formula.
-- Flávio Silva

IF NOT EXISTS (SELECT * FROM SYS.TABLES WHERE name = 'item_formula')
	CREATE TABLE item_formula
	(
	  id bigint NOT NULL IDENTITY,
	  
	  ordem int NOT NULL,
	  valor varchar(255) NULL,
	  verba_id bigint NOT NULL,
	  
	  rotina_id bigint NULL,
	  tipo varchar(255) NOT NULL,
	  CONSTRAINT pk_item_formula PRIMARY KEY (id),
	  CONSTRAINT fk_item_formula_rotina_id FOREIGN KEY (rotina_id) REFERENCES verba (id),
	  CONSTRAINT fk_item_formula_verba_id FOREIGN KEY (verba_id) REFERENCES verba (id)
	);