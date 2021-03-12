-- Criação de tabela Tipo Férias
-- Roberto Araujo

IF NOT EXISTS(SELECT * FROM SYS.TABLES WHERE name = 'tipo_ferias')
CREATE TABLE tipo_ferias (

	id 					bigint 		PRIMARY KEY 	NOT NULL  IDENTITY(1,1),
	descricao 			VARCHAR(255) 				NOT NULL,
	abono				INT							NOT NULL,
	dias				INT							NOT NULL,
	saldo				INT							NOT NULL,
	decimo_terceiro		VARCHAR(255) 				NOT NULL,
	coletivo			VARCHAR(255)				NOT NULL,
	licenca_premio		VARCHAR(255)				NOT NULL,
	created_at 			datetime2(7) 				NOT NULL,
	updated_at 			datetime2(7) 				NOT NULL,
	created_by 			bigint							,
	updated_by 			bigint	
)
