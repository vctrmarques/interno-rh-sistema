IF NOT EXISTS(SELECT * FROM SYS.TABLES WHERE name = 'certidao_ex_servidor_cargos')
BEGIN
	CREATE TABLE certidao_ex_servidor_cargos
(
    id 							BIGINT 					 	NOT NULL IDENTITY,
    certidao_ex_servidor_id		BIGINT						NOT NULL,
    descricao_cargo				VARCHAR(255)				NOT NULL,
	created_at 					datetime2(7)	 			NOT NULL,
	updated_at 					datetime2(7) 				NOT NULL,
	created_by 					bigint						NULL,
	updated_by 					bigint						NULL,
	
	CONSTRAINT pk_certidao_ex_servidor_cargos PRIMARY KEY (id),
    CONSTRAINT fk_certidao_ex_servidor_cargos_certidao FOREIGN KEY (certidao_ex_servidor_id) REFERENCES certidao_ex_segurado(id)
)
END

--Rodrigo Leite
--Remoção da coluna cargo
IF COL_LENGTH('certidao_ex_segurado_periodo', 'cargo') IS NOT NULL
BEGIN
    ALTER TABLE certidao_ex_segurado_periodo DROP COLUMN cargo;
END