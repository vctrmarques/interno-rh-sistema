--Criação da tabela de histórico de declaração aposentadoria
--Flávio Silva

    
IF NOT EXISTS(SELECT * FROM SYS.TABLES WHERE name = 'historico_declaracao_aposentadoria')
	BEGIN
		CREATE TABLE historico_declaracao_aposentadoria (
			id bigint NOT NULL IDENTITY(1,1),
			declaracao_aposentadoria_id bigint NOT NULL,
			tipo_declaracao varchar(255) NOT NULL,
			
			created_at datetime2(7) NOT NULL,
			updated_at datetime2(7) NOT NULL,
			created_by bigint NULL,
			updated_by bigint NULL,
			
			CONSTRAINT pk_historico_declaracao_aposentadoria PRIMARY KEY (id),
			CONSTRAINT fk_historico_declaracao_aposentadoria_declaracao_aposentadoria_id FOREIGN KEY (declaracao_aposentadoria_id) REFERENCES declaracao_aposentadoria(id) 
		);
	END
