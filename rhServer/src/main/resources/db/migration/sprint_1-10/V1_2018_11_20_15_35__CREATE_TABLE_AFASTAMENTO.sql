--Criação da tabela curso. 
--Davi Queiroz.

IF NOT EXISTS(SELECT * FROM SYS.TABLES WHERE name = 'afastamento')
CREATE TABLE afastamento
(
    id BIGINT PRIMARY KEY NOT NULL IDENTITY,
   	codigo	INT NOT NULL,
   	descricao VARCHAR(255) NOT NULL,
   	codigo_motivo_afastamento INT,
   	codigo_motivo_retorno INT,
   	rais_situacao INT,
   	rais INT,
   	codigo_saque INT,
   	tipo VARCHAR(255),
   	modalidade VARCHAR(255),
   	qtd_dias INT,
    created_at datetime2(7) NOT NULL,
	updated_at datetime2(7) NOT NULL,
	created_by bigint,
	updated_by bigint
)

