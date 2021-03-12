--Criação da tabela que salva a importação de verbas do funcionario
--Eduardo Costa

    
IF NOT EXISTS(SELECT * FROM SYS.TABLES WHERE name = 'importador_verbas_funcionario')
	BEGIN
		CREATE TABLE importador_verbas_funcionario (
			id bigint NOT NULL IDENTITY(1,1),
			dt_importacao datetime2(7) NOT NULL,
			anexo_arquivo_importacao_id bigint NOT NULL,
			nome_original varchar(255) NOT NULL,
			nome_sistema varchar(255) NOT NULL,
			excluido bit NOT NULL DEFAULT 0,
			created_at datetime2(7) NOT NULL,
			updated_at datetime2(7) NOT NULL,
			created_by bigint NULL,
			updated_by bigint NULL,
			
			CONSTRAINT pk_importador_verbas_funcionario PRIMARY KEY (id),
			CONSTRAINT fk_importador_verbas_funcionario_anexo_id FOREIGN KEY (anexo_arquivo_importacao_id) REFERENCES anexo(id) 
		);
	END