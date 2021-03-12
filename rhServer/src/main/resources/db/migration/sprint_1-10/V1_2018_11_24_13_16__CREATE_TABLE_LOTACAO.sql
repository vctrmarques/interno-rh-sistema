--Criação da tabela Lotacao. 
--Davi Queiroz.

IF NOT EXISTS(SELECT * FROM SYS.TABLES WHERE name = 'lotacao')
CREATE TABLE lotacao
(
    id 				BIGINT 		PRIMARY KEY 	NOT NULL IDENTITY,
    descricao 		VARCHAR(255) 				NOT NULL,
    descricao_completa 		VARCHAR(255) 		NOT NULL,
    nivel			INT,
    efetivo			INT,
    id_centro_custo	BIGINT,
    tipo_conta		VARCHAR(255),
    numero_conta	INT,
    tipo			INT,
    vigencia_inicial	datetime2(7),
    vigencia_final	datetime2(7),
	created_at 		datetime2(7)	 			NOT NULL,
	updated_at 		datetime2(7) 				NOT NULL,
	created_by 		bigint								,
	updated_by 		bigint,
	CONSTRAINT fk_lotacao_id_centro_custo FOREIGN KEY (id_centro_custo) REFERENCES centro_custo(id),
)