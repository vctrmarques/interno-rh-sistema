-- pensao_alimenticia definition

-- Drop table
IF EXISTS (SELECT * FROM SYS.TABLES WHERE name = 'pensao_alimenticia')
	DROP TABLE pensao_alimenticia

IF NOT EXISTS (SELECT * FROM SYS.TABLES WHERE name = 'pensao_alimenticia')
	CREATE TABLE pensao_alimenticia (
	
		-- Bloco Inicial
		
		id 								bigint IDENTITY(1,1) 	NOT NULL,
		funcionario_id 					bigint 					NOT NULL,
		
		-- Bloco do Alimentando
		
		nome_alimentando 				VARCHAR(255) 			NOT NULL,
		nascimento_alimentando 			datetime2(7) 			NOT NULL,
		tipo_pensao 					VARCHAR(255) 			NOT NULL,
		rg 								VARCHAR(255) 			NOT NULL,
		orgao 							VARCHAR(255) 			NOT NULL,
		uf_documento_id 				bigint 					NOT NULL,
		cpf 							VARCHAR(11) 			NOT NULL,
		numero_telefone 				VARCHAR(255) 			NULL,
		logradouro 						VARCHAR(255) 			NOT NULL,
		numero 							VARCHAR(255) 			NOT NULL,
		complemento 					VARCHAR(255) 			NULL,
		bairro 							VARCHAR(255) 			NOT NULL,
		uf_id 							bigint 					NOT NULL,
		municipio_id 					bigint 					NOT NULL,
		cep 							VARCHAR(8) 				NOT NULL,
		
		-- Bloco do Responsável Legal
		
		responsavel_id 					bigint 					NULL,
		numero_processo_responsavel 	VARCHAR(255) 			NULL,
		data_inicial 					datetime2(7) 			NULL,
		data_final 						datetime2(7) 			NULL,
		
		-- Bloco de Pagamento Parte 1
		
		data_inicial_pagamento 			datetime2(7) 			NOT NULL,
		data_final_pagamento 			datetime2(7) 			NULL,
		centro_custo_id 				bigint 					NOT NULL,
		verba_id 						bigint 					NOT NULL,
		tipo_conta 						VARCHAR(255) 			NOT NULL,
		banco_id 						bigint 					NOT NULL,
		agencia_id 						bigint 					NOT NULL,
		conta		 					VARCHAR(255) 			NOT NULL,
		digito_conta 					VARCHAR(255) 			NOT NULL,
		
		-- Bloco de Pagamento Parte 2
		
		numero_processo_pagamento 		VARCHAR(255) 			NOT NULL,
		vencimento 						datetime2(7) 			NOT NULL,
		tipo_desconto 					VARCHAR(255) 			NOT NULL,
		valor 							float 					NOT NULL,
		salario_familia 				bigint 					NOT NULL,
		incidencia_principal 			VARCHAR(255) 			NOT NULL,
		salario_13 						bit 					NOT NULL,
		ferias 							bit 					NOT NULL,
		recisao 						bit 					NOT NULL,
		
		-- Bloco padrão de dado cadastral
		
		created_at 						datetime2(7) 			NOT NULL,
		updated_at 						datetime2(7) 			NOT NULL,
		created_by 						bigint 					NULL,
		updated_by 						bigint 					NULL,
		
		CONSTRAINT pk_pensao_alimenticia 					PRIMARY KEY (id),
		CONSTRAINT fk_pensao_alimenticia_agencia_id 		FOREIGN KEY (agencia_id) 		REFERENCES agencia(id),
		CONSTRAINT fk_pensao_alimenticia_banco_id 			FOREIGN KEY (banco_id) 			REFERENCES banco(id),
		CONSTRAINT fk_pensao_alimenticia_centro_custo_id 	FOREIGN KEY (centro_custo_id) 	REFERENCES centro_custo(id),
		CONSTRAINT fk_pensao_alimenticia_funcionario_id 	FOREIGN KEY (funcionario_id) 	REFERENCES funcionario(id),
		CONSTRAINT fk_pensao_alimenticia_municipio_id 		FOREIGN KEY (municipio_id) 		REFERENCES municipio(id),
		CONSTRAINT fk_pensao_alimenticia_responsavel_id 	FOREIGN KEY (responsavel_id) 	REFERENCES responsavel_legal(id),
		CONSTRAINT fk_pensao_alimenticia_uf_documento_id 	FOREIGN KEY (uf_documento_id) 	REFERENCES unidade_federativa(id),
		CONSTRAINT fk_pensao_alimenticia_uf_id 				FOREIGN KEY (uf_id) 			REFERENCES unidade_federativa(id),
		CONSTRAINT fk_pensao_alimenticia_verba_id 			FOREIGN KEY (verba_id) 			REFERENCES verba(id)
		
	);

