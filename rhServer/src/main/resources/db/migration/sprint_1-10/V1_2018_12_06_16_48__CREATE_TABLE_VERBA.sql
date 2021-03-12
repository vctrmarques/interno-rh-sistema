-- Criação de tabela Verba
-- Marconi Motta

IF NOT EXISTS(SELECT * FROM SYS.TABLES WHERE name = 'verba')
CREATE TABLE verba (

	id 				bigint 			PRIMARY KEY 	NOT NULL  IDENTITY(1,1),
	codigo			INT 							NOT NULL,
	descricao_verba	 	VARCHAR(255) 				NOT NULL,
	descricao_resumida VARCHAR(1000) 					NOT NULL,
	tipo_verba VARCHAR(255) 					NOT NULL,
	destinacao_externa VARCHAR(255) 					NOT NULL,
	valor_maximo    FLOAT,
	tipo_valor	 	VARCHAR(255) 				NOT NULL,
	incidencia_valor VARCHAR(255),
	comentario		VARCHAR(2000),
	conta_debito			INT,
	conta_credito			INT,
	conta_auxiliar_primaria			INT,
	conta_auxiliar_secundaria			INT,
	vigencia_inicial 		datetime2(7) ,
	vigencia_final		datetime2(7),
	recorrencia	 	VARCHAR(255) 				NOT NULL,
	created_at 		datetime2(7) 					NOT NULL,
	updated_at 		datetime2(7) 					NOT NULL,
	created_by 		bigint								,
	updated_by 		bigint	
)
CREATE UNIQUE INDEX uk_verba_descricao_verba ON verba (descricao_verba) 
CREATE UNIQUE INDEX uk_verba_codigo ON verba (codigo) ;
