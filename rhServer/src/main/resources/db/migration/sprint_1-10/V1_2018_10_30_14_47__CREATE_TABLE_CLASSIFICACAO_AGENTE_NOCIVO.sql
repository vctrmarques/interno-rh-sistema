--Criação da tabela classificacao_agente_nocivo. 
--Davi Queiroz.

IF NOT EXISTS(SELECT * FROM SYS.TABLES WHERE name = 'classificacao_agente_nocivo')
CREATE TABLE classificacao_agente_nocivo
(
    id BIGINT PRIMARY KEY NOT NULL IDENTITY,
	codigo INT NOT NULL,
	descricao VARCHAR(255) NOT NULL,
	tempo_exposicao INT,
	created_at datetime2(7) NOT NULL,
	updated_at datetime2(7) NOT NULL,
	created_by bigint,
	updated_by bigint
    
)