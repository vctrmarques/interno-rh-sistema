--Flávio Silva
--Alteração na tabela nivel salarial historico, adição de novos campos
--A tabela precisou ser recriada.

-- Drop table

DROP TABLE nivel_salarial_historico

-- Create table

CREATE TABLE nivel_salarial_historico (
	id bigint NOT NULL IDENTITY(1,1),
	created_at datetime2(7) NOT NULL,
	updated_at datetime2(7) NOT NULL,
	created_by bigint,
	updated_by bigint,
	data_ajuste datetime2(7) NOT NULL,
	origem_ajuste varchar(255) NOT NULL,
	valor_ajustado float NOT NULL,
	valor_original float NOT NULL,
	valor_retroativo float,
	nivel_salarial_id bigint,
	simulador_nivel_salarial_id bigint,
	CONSTRAINT pk_nivel_salarial_historico PRIMARY KEY (id),
	CONSTRAINT fk_nivel_salarial_historico_nivel_salarial_id FOREIGN KEY (nivel_salarial_id) REFERENCES nivel_salarial(id) ,
	CONSTRAINT fk_nivel_salarial_historico_simulador_nivel_salarial_id FOREIGN KEY (simulador_nivel_salarial_id) REFERENCES simulador_nivel_salarial(id) 
);


  
  
  



