-- Roberto Araújo
-- Alterado por Lucas Moura

-- Criação da tabela de Dependente

CREATE TABLE dependente (
	
	id 									bigint 		PRIMARY KEY		NOT NULL IDENTITY(1,1),
	funcionario_id 						bigint 						NOT NULL,
	tipo								VARCHAR(255) 				NOT NULL,
	nome								VARCHAR(255) 				NOT NULL,
	naturalidade						VARCHAR(80)							,
	inicio_motivo						date					        	,
	fim_motivo							date						        ,	
	sexo 								VARCHAR(30)	 				NOT NULL							,
	dt_nascimento 						date								,
	num_cpf 							VARCHAR(11)							,
	num_identidade 						VARCHAR(255)						,
	logradouro 							VARCHAR(255)						,
	numero 								VARCHAR(255)						,	
	municipio_id 						bigint						NOT NULL,
	unidade_federativa_id 				bigint						NOT NULL,
	cep									VARCHAR(12)							,
	num_documento_comprobatorio 		VARCHAR(20)							,
	dt_documento_comprobatorio 			date								,
	dependencia_ir 						bit									,
	dependencia_sf 						bit							NOT NULL,
	created_at 							DATETIME2(7) 				NOT NULL,
	updated_at 							DATETIME2(7) 				NOT NULL,
	created_by 							bigint								,
	updated_by 							bigint								,
	
	CONSTRAINT fk_dependente_funcionario_id FOREIGN KEY (funcionario_id) REFERENCES funcionario(id),
	CONSTRAINT fk_dependente_municipio_id FOREIGN KEY (municipio_id) REFERENCES municipio(id),
	CONSTRAINT fk_dependente_unidade_federativa_id FOREIGN KEY (unidade_federativa_id) REFERENCES unidade_federativa(id)
		
)