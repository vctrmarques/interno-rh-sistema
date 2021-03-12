-- Criação das tabelas relacionadas ao recadastramento.
-- Insere dados no menu
 
-- Rodrigo Leite

IF NOT EXISTS(SELECT * FROM SYS.TABLES WHERE name = 'recadastramento_dados')
CREATE TABLE recadastramento_dados
(
    id 							BIGINT 					 	NOT NULL IDENTITY,
    municipio_id				BIGINT						NULL,
    nacionalidade_id			BIGINT						NULL,
    nome						VARCHAR(255)				NULL,
    nome_mae					VARCHAR(255)				NULL,
    nome_pai					VARCHAR(255)				NULL,
    genero						VARCHAR(100)				NULL,
    estado_civil				VARCHAR(100)				NULL,
    raca_cor					VARCHAR(255)				NULL,
    tipo_sanguineo				VARCHAR(255)				NULL,
    grau_instrucao				VARCHAR(255)				NULL,
	
	
	CONSTRAINT pk_recadastramento_dados PRIMARY KEY (id),
	CONSTRAINT fk_recadastramento_dados_municipio FOREIGN KEY (municipio_id) REFERENCES municipio(id),
	CONSTRAINT fk_recadastramento_dados_nacionalidade FOREIGN KEY (nacionalidade_id) REFERENCES nacionalidade(id)
)

IF NOT EXISTS(SELECT * FROM SYS.TABLES WHERE name = 'recadastramento_endereco')
CREATE TABLE recadastramento_endereco
(
    id 							BIGINT 					 	NOT NULL IDENTITY,
    municipio_id				BIGINT						NULL,
    cep							VARCHAR(12)					NULL,
    bairro						VARCHAR(255)				NULL,
    logradouro					VARCHAR(255)				NULL,
    complemento					VARCHAR(255)				NULL,
    numero						VARCHAR(255)				NULL,
    email						VARCHAR(255)				NULL,
    observacao					VARCHAR(max)				NULL,
	
	
	CONSTRAINT pk_recadastramento_endereco PRIMARY KEY (id),
	CONSTRAINT fk_recadastramento_endereco_municipio FOREIGN KEY (municipio_id) REFERENCES municipio(id)
)

IF NOT EXISTS(SELECT * FROM SYS.TABLES WHERE name = 'telefone')
CREATE TABLE telefone
(
    id 							BIGINT 					 	NOT NULL IDENTITY,
   	numero						VARCHAR(255)				NULL,
    tipo						VARCHAR(255)				NULL,
    created_at 					datetime2(7)	 			NOT NULL,
	updated_at 					datetime2(7) 				NOT NULL,
	created_by 					bigint						NULL,
	updated_by 					bigint						NULL,
	
	CONSTRAINT pk_telefone PRIMARY KEY (id)
)

IF NOT EXISTS(SELECT * FROM SYS.TABLES WHERE name = 'recadastramento_endereco_telefone')
CREATE TABLE recadastramento_endereco_telefone
(
    recadastramento_endereco_id	BIGINT						NOT NULL,
    telefone_id					BIGINT						NOT NULL,
	
	CONSTRAINT fk_recadastramento_endereco_telefone_endereco FOREIGN KEY (recadastramento_endereco_id) REFERENCES recadastramento_endereco(id),
	CONSTRAINT fk_recadastramento_endereco_telefone_telefone FOREIGN KEY (telefone_id) REFERENCES telefone(id)
)

IF NOT EXISTS(SELECT * FROM SYS.TABLES WHERE name = 'recadastramento_contato')
CREATE TABLE recadastramento_contato
(
    id 							BIGINT 					 	NOT NULL IDENTITY,
    nome						VARCHAR(255)				NULL,
    email						VARCHAR(255)				NULL,
    observacao					VARCHAR(max)				NULL,
	
	CONSTRAINT pk_recadastramento_contato PRIMARY KEY (id)
)

IF NOT EXISTS(SELECT * FROM SYS.TABLES WHERE name = 'recadastramento_contato_telefone')
CREATE TABLE recadastramento_contato_telefone
(
    recadastramento_contato_id	BIGINT						NOT NULL,
    telefone_id					BIGINT						NOT NULL,
	
	CONSTRAINT fk_recadastramento_contato_telefone_contato FOREIGN KEY (recadastramento_contato_id) REFERENCES recadastramento_contato(id),
	CONSTRAINT fk_recadastramento_contato_telefone_telefone FOREIGN KEY (telefone_id) REFERENCES telefone(id)
)

IF NOT EXISTS(SELECT * FROM SYS.TABLES WHERE name = 'recadastramento')
CREATE TABLE recadastramento
(
    id 							BIGINT 					 	NOT NULL IDENTITY,
    funcionario_id				BIGINT						NULL,
    pensionista_id				BIGINT						NULL,
    dados_id					BIGINT						NULL,
    endereco_id					BIGINT						NULL,
    contato_id					BIGINT						NULL,
    data						DATE						NOT NULL,
    status						BIT							NOT NULL,
	created_at 					datetime2(7)	 			NOT NULL,
	updated_at 					datetime2(7) 				NOT NULL,
	created_by 					bigint						NULL,
	updated_by 					bigint						NULL,
	
	CONSTRAINT pk_recadastramento PRIMARY KEY (id),
	CONSTRAINT fk_recadastramento_funcionario FOREIGN KEY (funcionario_id) REFERENCES funcionario(id),
    CONSTRAINT fk_recadastramento_pensionista FOREIGN KEY (pensionista_id) REFERENCES pensionista(id),
    CONSTRAINT fk_recadastramento_dados FOREIGN KEY (dados_id) REFERENCES recadastramento_dados(id),
	CONSTRAINT fk_recadastramento_endereco FOREIGN KEY (endereco_id) REFERENCES recadastramento_endereco(id),
	CONSTRAINT fk_recadastramento_contato FOREIGN KEY (contato_id) REFERENCES recadastramento_contato(id)
)

IF NOT EXISTS(SELECT * FROM SYS.TABLES WHERE name = 'recadastramento_anexo')
CREATE TABLE recadastramento_anexo
(
    recadastramento_id			BIGINT						NOT NULL,
    anexo_id					BIGINT						NOT NULL,
	
	CONSTRAINT fk_recadastramento_anexo_recadastramento FOREIGN KEY (recadastramento_id) REFERENCES recadastramento(id),
	CONSTRAINT fk_recadastramento_anexo_anexo FOREIGN KEY (anexo_id) REFERENCES anexo(id)
)

IF NOT EXISTS(SELECT * FROM SYS.TABLES WHERE name = 'recadastramento_historico_ligacao')
CREATE TABLE recadastramento_historico_ligacao
(
    id 							BIGINT 					 	NOT NULL IDENTITY,
    funcionario_id				BIGINT						NOT NULL,
    observacao					VARCHAR(max)				NOT NULL,
    created_at 					datetime2(7)	 			NOT NULL,
	updated_at 					datetime2(7) 				NOT NULL,
	created_by 					bigint						NULL,
	updated_by 					bigint						NULL,
	
	CONSTRAINT pk_recadastramento_historico_ligacao PRIMARY KEY (id),
	CONSTRAINT fk_recadastramento_historico_ligacao_funcionario FOREIGN KEY (funcionario_id) REFERENCES funcionario(id)
)


IF NOT EXISTS(SELECT * FROM menu WHERE url = '/recadastramento/gestao')
	INSERT INTO menu VALUES(SYSDATETIME(), SYSDATETIME(), 1, 1, 1, 'MODULO_PREVIDENCIARIO', 'Recadastramento', '/recadastramento/gestao')

