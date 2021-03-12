-- Rodrigo Leite

IF NOT EXISTS(SELECT * FROM SYS.TABLES WHERE name = 'dirf_responsavel_receita')
CREATE TABLE dirf_responsavel_receita
(
    id 							BIGINT 					 	NOT NULL IDENTITY,
    cpf							VARCHAR(11)					NOT NULL,
    nome						VARCHAR(255)				NOT NULL,
    ddd							VARCHAR(3)					NOT NULL,
    numero_telefone				VARCHAR(9)					NOT NULL,
    ramal						VARCHAR(50)					NULL,
    email						VARCHAR(150)				NULL,
	created_at 					datetime2(7)	 			NOT NULL,
	updated_at 					datetime2(7) 				NOT NULL,
	created_by 					bigint						NULL,
	updated_by 					bigint						NULL,
	
	CONSTRAINT pk_dirf_responsavel_receita PRIMARY KEY (id)
)

-- Criação das tabelas relacionadas a DIRF.
IF NOT EXISTS(SELECT * FROM SYS.TABLES WHERE name = 'dirf')
CREATE TABLE dirf
(
    id 							BIGINT 					 	NOT NULL IDENTITY,
    ano_base					INT							NOT NULL,
    tipo_declaracao				VARCHAR(100)				NOT NULL,
    numero_declaracao			INT							NULL,
    empresa_filial_id			BIGINT						NOT NULL,
    dirf_responsavel_receita_id	BIGINT						NOT NULL,
    natureza_declarante			INT							NOT NULL,
    situacao					VARCHAR(100)				NOT NULL,
	created_at 					datetime2(7)	 			NOT NULL,
	updated_at 					datetime2(7) 				NOT NULL,
	created_by 					bigint						NULL,
	updated_by 					bigint						NULL,
	
	CONSTRAINT pk_dirf PRIMARY KEY (id),
	CONSTRAINT fk_dirf_empresa_filial FOREIGN KEY (empresa_filial_id) REFERENCES empresa_filial(id),
	CONSTRAINT fk_dirf_responsavel_receita FOREIGN KEY (dirf_responsavel_receita_id) REFERENCES dirf_responsavel_receita(id)
)

-- Insere dados no menu
IF NOT EXISTS(SELECT * FROM menu WHERE url = '/dirf/gestao')
	INSERT INTO menu VALUES(1, 'FOLHA_PAGAMENTO', 'DIRF', '/dirf/gestao')
	
IF EXISTS(SELECT * FROM menu WHERE nome = 'DIRF')
BEGIN
DECLARE
    @menuId AS VARCHAR(1000)

	SELECT @menuId = CAST(id AS VARCHAR(1000)) FROM menu WHERE nome = 'DIRF'
	
	IF NOT EXISTS(SELECT * FROM papel WHERE nome = 'ROLE_DIRF_GESTAO')
		INSERT INTO papel (nome, id_menu) VALUES ('ROLE_DIRF_GESTAO', @menuId)
	
	IF NOT EXISTS(SELECT * FROM papel WHERE nome = 'ROLE_DIRF_CADASTRAR')
		INSERT INTO papel (nome, id_menu) VALUES ('ROLE_DIRF_CADASTRAR', @menuId)
		
	IF NOT EXISTS(SELECT * FROM papel WHERE nome = 'ROLE_DIRF_ATUALIZAR')
		INSERT INTO papel (nome, id_menu) VALUES ('ROLE_DIRF_ATUALIZAR', @menuId)
		
	IF NOT EXISTS(SELECT * FROM papel WHERE nome = 'ROLE_DIRF_EXCLUIR')
		INSERT INTO papel (nome, id_menu) VALUES ('ROLE_DIRF_EXCLUIR', @menuId)
		
	IF NOT EXISTS(SELECT * FROM papel WHERE nome = 'ROLE_DIRF_VISUALIZAR')
		INSERT INTO papel (nome, id_menu) VALUES ('ROLE_DIRF_VISUALIZAR', @menuId)
		
END