--Criação da tabela centro_custo. 
--Davi Queiroz.

IF NOT EXISTS(SELECT * FROM SYS.TABLES WHERE name = 'centro_custo')
CREATE TABLE centro_custo
(
    id BIGINT PRIMARY KEY NOT NULL IDENTITY,
	codigo INT NOT NULL,
	descricao VARCHAR(255) NOT NULL,
	efetivo INT,
	nivel INT,
	tipo_debito VARCHAR(255),
	tipo_credito VARCHAR(255),
	conta_credito INT,
	conta_debito INT,
	created_at datetime2(7) NOT NULL,
	updated_at datetime2(7) NOT NULL,
	created_by bigint,
	updated_by bigint
    
)