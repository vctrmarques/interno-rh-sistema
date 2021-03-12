-- João Marques

IF NOT EXISTS(SELECT * FROM SYS.TABLES WHERE name = 'arrecadacao_indice')
CREATE TABLE arrecadacao_indice
(
    id 							BIGINT 					 	NOT NULL IDENTITY,
    indice						VARCHAR(255)			    NOT NULL,
	created_at 					datetime2(7)	 			NOT NULL,
	updated_at 					datetime2(7) 				NOT NULL,
	created_by 					bigint						NULL,
	updated_by 					bigint						NULL,
	
	CONSTRAINT pk_arrecadacao_indice PRIMARY KEY (id)
)

IF NOT EXISTS(SELECT * FROM SYS.TABLES WHERE name = 'arrecadacao_indice_ano_periodicidade')
CREATE TABLE arrecadacao_indice_ano_periodicidade
(
    id							BIGINT				        NOT NULL IDENTITY,
    arrecadacao_indice_id		BIGINT				        NOT NULL,
	ano							BIGINT				        NOT NULL,
	periodicidade				VARCHAR(10) 		        NOT NULL,
	
	CONSTRAINT pk_arrecadacao_indice_ano_periodicidade PRIMARY KEY (id),
	CONSTRAINT fk_arrecadacao_indice_ano_periodicidade_arrecadacao_indices FOREIGN KEY (arrecadacao_indice_id) REFERENCES arrecadacao_indice(id)
)

IF NOT EXISTS(SELECT * FROM SYS.TABLES WHERE name = 'arrecadacao_indice_mes_valor')
CREATE TABLE arrecadacao_indice_mes_valor
(
    id												BIGINT				        NOT NULL IDENTITY,
    arrecadacao_indice_ano_periodicidade_id			BIGINT				        NOT NULL,
	mes												VARCHAR(10)			        NOT NULL,
	aliquota										FLOAT                		NOT NULL,
	
	CONSTRAINT pk_arrecadacao_indice_mes_valor PRIMARY KEY (id),
	CONSTRAINT fk_arrecadacao_indice_mes_valor_arrecadacao_indice_ano_periodicidade FOREIGN KEY (arrecadacao_indice_ano_periodicidade_id) REFERENCES arrecadacao_indice_ano_periodicidade(id)
)

-- insert do sub-menu Índices no menu Arrecadação
IF NOT EXISTS(SELECT * FROM MENU WHERE url = '/arrecadacaoIndice/gestao')
	INSERT INTO menu (nome, ativo, categoria, url) VALUES('Índice', 1, 'ARRECADACAO', '/arrecadacaoIndice/gestao');

IF EXISTS(SELECT * FROM menu WHERE url = '/arrecadacaoIndice/gestao')
BEGIN
DECLARE
    @menuId AS VARCHAR(1000)

	SELECT @menuId = CAST(id AS VARCHAR(1000)) FROM menu WHERE nome = 'Índice'
	
	IF NOT EXISTS(SELECT * FROM papel WHERE nome = 'ROLE_ARRECADACAO_INDICE_GESTAO')
		INSERT INTO papel (nome, id_menu) VALUES ('ROLE_ARRECADACAO_INDICE_GESTAO', @menuId)
	
	IF NOT EXISTS(SELECT * FROM papel WHERE nome = 'ROLE_ARRECADACAO_INDICE_CADASTRAR')
		INSERT INTO papel (nome, id_menu) VALUES ('ROLE_ARRECADACAO_INDICE_CADASTRAR', @menuId)
		
	IF NOT EXISTS(SELECT * FROM papel WHERE nome = 'ROLE_ARRECADACAO_INDICE_ATUALIZAR')
		INSERT INTO papel (nome, id_menu) VALUES ('ROLE_ARRECADACAO_INDICE_ATUALIZAR', @menuId)
		
	IF NOT EXISTS(SELECT * FROM papel WHERE nome = 'ROLE_ARRECADACAO_INDICE_EXCLUIR')
		INSERT INTO papel (nome, id_menu) VALUES ('ROLE_ARRECADACAO_INDICE_EXCLUIR', @menuId)
		
	IF NOT EXISTS(SELECT * FROM papel WHERE nome = 'ROLE_ARRECADACAO_INDICE_VISUALIZAR')
		INSERT INTO papel (nome, id_menu) VALUES ('ROLE_ARRECADACAO_INDICE_VISUALIZAR', @menuId)
		
END





