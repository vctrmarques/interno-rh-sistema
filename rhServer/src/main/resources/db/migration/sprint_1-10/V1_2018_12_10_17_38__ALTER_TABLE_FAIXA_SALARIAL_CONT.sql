--Criação da tabela Faixa Salarial - refactory. 
--Marconi Motta.

DROP TABLE faixa_salarial;

IF NOT EXISTS(SELECT * FROM SYS.TABLES WHERE name = 'faixa_salarial')
CREATE TABLE faixa_salarial
(
    id 				BIGINT 		PRIMARY KEY 	NOT NULL IDENTITY,
	nome			VARCHAR(255)				NOT NULL,
	id_grupo_salarial	BIGINT 					NOT NULL,
	id_classe_salarial BIGINT					NOT NULL,
	created_at 		datetime2(7)	 			NOT NULL,
	updated_at 		datetime2(7) 				NOT NULL,
	created_by 		bigint								,
	updated_by 		bigint,
	CONSTRAINT fk_id_grupo_salarial_faixa_salarial FOREIGN KEY (id_grupo_salarial) REFERENCES grupo_salarial(id),
	CONSTRAINT fk_id_classe_salarial_faixa_salarial FOREIGN KEY (id_classe_salarial) REFERENCES classe_salarial(id)
);

CREATE TABLE faixa_salarial_nivel
(
id_faixa_salarial 	BIGINT NOT NULL,
id_nivel_salarial	BIGINT NOT NULL,
CONSTRAINT fk_id_faixa_salarial_faixa_salarial_nivel FOREIGN KEY (id_faixa_salarial) REFERENCES faixa_salarial(id),
CONSTRAINT fk_id_nivel_salarial_faixa_salarial_nivel FOREIGN KEY (id_nivel_salarial) REFERENCES nivel_salarial(id)
);