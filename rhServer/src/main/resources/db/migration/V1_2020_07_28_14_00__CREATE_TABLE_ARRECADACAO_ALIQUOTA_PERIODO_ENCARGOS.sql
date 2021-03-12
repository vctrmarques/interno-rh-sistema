-- Rodrigo Leite

IF NOT EXISTS(SELECT * FROM SYS.TABLES WHERE name = 'arrecadacao_aliquota_periodo')
CREATE TABLE arrecadacao_aliquota_periodo
(
    id 							BIGINT 					 	NOT NULL IDENTITY,
    inicio_vigencia				DATE						NOT NULL,
    fim_vigencia				DATE						NOT NULL,
	created_at 					datetime2(7)	 			NOT NULL,
	updated_at 					datetime2(7) 				NOT NULL,
	created_by 					bigint						NULL,
	updated_by 					bigint						NULL,
	
	CONSTRAINT pk_arrecadacao_aliquota_periodo PRIMARY KEY (id)
)

-- Criação das tabelas relacionadas
IF NOT EXISTS(SELECT * FROM SYS.TABLES WHERE name = 'arrecadacao_aliquota_encargo')
CREATE TABLE arrecadacao_aliquota_encargo
(
    id 								BIGINT 					 	NOT NULL IDENTITY,
    arrecadacao_aliquota_periodo_id	BIGINT						NOT NULL,
    aliquota_engargo				VARCHAR(100)				NOT NULL,
    aliquota						FLOAT						NOT NULL,
	created_at	 					datetime2(7)	 			NOT NULL,
	updated_at 						datetime2(7) 				NOT NULL,
	created_by 						bigint						NULL,
	updated_by 						bigint						NULL,
	
	CONSTRAINT pk_arrecadacao_aliquota_encargo PRIMARY KEY (id),
	CONSTRAINT fk_arrecadacao_aliquota_encargo_periodo FOREIGN KEY (arrecadacao_aliquota_periodo_id) REFERENCES arrecadacao_aliquota_periodo(id)
)

-- Insere dados no menu
IF NOT EXISTS(SELECT * FROM menu WHERE url = '/arrecadacaoAliquota/gestao')
	INSERT INTO menu VALUES(1, 'FOLHA_PAGAMENTO', 'Arrecadação - Alíquota', '/arrecadacaoAliquota/gestao')
	
IF EXISTS(SELECT * FROM menu WHERE url = '/arrecadacaoAliquota/gestao')
BEGIN
DECLARE
    @menuId AS VARCHAR(1000)

	SELECT @menuId = CAST(id AS VARCHAR(1000)) FROM menu WHERE url = '/arrecadacaoAliquota/gestao'
	
	IF NOT EXISTS(SELECT * FROM papel WHERE nome = 'ROLE_ARRECADACAO_ALIQUOTA_GESTAO')
		INSERT INTO papel (nome, id_menu) VALUES ('ROLE_ARRECADACAO_ALIQUOTA_GESTAO', @menuId)

END