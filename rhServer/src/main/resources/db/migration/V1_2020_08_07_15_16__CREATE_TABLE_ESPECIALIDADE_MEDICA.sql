-- João Marques

IF NOT EXISTS(SELECT * FROM SYS.TABLES WHERE name = 'especialidade_medica')
CREATE TABLE especialidade_medica
(
    id 							BIGINT 					 	NOT NULL IDENTITY,
    codigo						VARCHAR(255)				NOT NULL,
    nome						VARCHAR(255)				NOT NULL,
	created_at 					datetime2(7)	 			NOT NULL,
	updated_at 					datetime2(7) 				NOT NULL,
	created_by 					bigint						NULL,
	updated_by 					bigint						NULL,
	
	CONSTRAINT pk_especialidade_medica PRIMARY KEY (id)
)
