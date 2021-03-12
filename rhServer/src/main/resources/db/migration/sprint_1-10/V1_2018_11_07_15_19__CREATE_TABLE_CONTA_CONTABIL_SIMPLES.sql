--Criação da tabela conta_contabil_simples. 
--Davi Queiroz.

IF NOT EXISTS(SELECT * FROM SYS.TABLES WHERE name = 'conta_contabil_simples')
CREATE TABLE conta_contabil_simples
(
    id BIGINT PRIMARY KEY NOT NULL IDENTITY,
	codigo INT NOT NULL,
	descricao VARCHAR(255) NOT NULL,
	created_at datetime2(7) NOT NULL,
	updated_at datetime2(7) NOT NULL,
	created_by bigint,
	updated_by bigint
    
)