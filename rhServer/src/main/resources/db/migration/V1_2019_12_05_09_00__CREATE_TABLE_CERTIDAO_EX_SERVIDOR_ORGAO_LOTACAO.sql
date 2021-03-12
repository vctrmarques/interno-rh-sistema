IF NOT EXISTS(SELECT * FROM SYS.TABLES WHERE name = 'certidao_ex_servidor_orgao_lotacao')
BEGIN
	CREATE TABLE certidao_ex_servidor_orgao_lotacao
(
    id 							BIGINT 					 	NOT NULL IDENTITY,
    certidao_ex_servidor_id		BIGINT						NOT NULL,
    descricao_orgao_lotacao		VARCHAR(255)				NOT NULL,
	created_at 					datetime2(7)	 			NOT NULL,
	updated_at 					datetime2(7) 				NOT NULL,
	created_by 					bigint						NULL,
	updated_by 					bigint						NULL,
	
	CONSTRAINT pk_certidao_ex_servidor_orgao_lotacao PRIMARY KEY (id),
    CONSTRAINT fk_certidao_ex_servidor_orgao_lotacao_certidao FOREIGN KEY (certidao_ex_servidor_id) REFERENCES certidao_ex_segurado(id)
)
END