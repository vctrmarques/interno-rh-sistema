-- Criação de tabela Prestador de Serviço
-- Roberto Araújo

IF NOT EXISTS(SELECT * FROM SYS.TABLES WHERE name = 'prestador_servico')
CREATE TABLE prestador_servico (

--DADOS PESSOAIS (22)

	id 										bigint 	PRIMARY KEY 	NOT NULL  IDENTITY(1,1),
	nome_civil								VARCHAR(255)			NOT NULL,
	nome_social 							VARCHAR(255) 					,
	cpf 									VARCHAR(11) 			NOT NULL,
	dt_nascimento							DATETIME2(7)			NOT NULL,
	estado_civil							VARCHAR(255)			NOT NULL,
	sexo									VARCHAR(255) 			NOT NULL,
	cor										VARCHAR(255) 					,
	nacionalidade_id						bigint					NOT NULL,
	nome_mae								VARCHAR(255) 			NOT NULL,
	nome_pai								VARCHAR(255) 					,
	logradouro 								VARCHAR(255)			NOT NULL,
	numero 									VARCHAR(255)			NOT NULL,
	complemento 							VARCHAR(255)					,
	bairro	 								VARCHAR(255)					,
	uf_id_endereco							bigint 					NOT NULL,	
	municipio_prestador_id 					bigint					NOT NULL,
	cep 									VARCHAR(255)			NOT NULL,
	ddd_celular								INT						NOT NULL,
	numero_celular							VARCHAR (255)			NOT NULL,
	ddd_telefone							INT								,
	telefone 								VARCHAR(255)					,
	email									VARCHAR(255)					,
	
-- DADOS TRABALHISTAS (11)
	
	locacao 								VARCHAR(255) 			NOT NULL,
	empresa_filial_id	 					bigint							,
	categoria_prestador_id					bigint					NOT NULL,
	cbo_prestador_id						bigint				 	NOT NULL,
	dt_inicial								DATETIME2(7)					,
	dt_final								DATETIME2(7)					,
	unidade_pgto_fixo						VARCHAR(255)					,
	natureza_atividade						VARCHAR(255)					,
	agente_nocivo							bit								,
	exposicao_passado						bit								,
	tempo_contribuicao						VARCHAR(255)					,
	
-- DADOS FISCAIS (18)
	
	recolhe_inss 							bit								,
	modo_pagamento 							VARCHAR(255) 					,
	valor_pago		 						FLOAT							,
	desconto_inss 							FLOAT							,
	base_calculo 							FLOAT							,
	desconto_ir 							FLOAT							,
	registro_fgts							VARCHAR(255)					,
	cnpj 									VARCHAR(14) 					,
	ir_retido 								FLOAT							,
	inss_pago								FLOAT							,
	cnpj_empresa_pagadora					VARCHAR(255) 			NOT NULL,
	dia_pagamento							INT						NOT NULL,
	verba_id								bigint							,
	convenio_id								bigint							,
	numero_empenho							INT								,
	dt_empenho								DATETIME2(7)					,
	numero_nf								INT								,
	dt_emissao_nf							DATETIME2(7)					,
	
--	DOCUMENTOS PESSOAIS (22)
	
	pis_pasep 								VARCHAR(255) 					,
	numero_ctps 							INT						NOT NULL,
	serie_ctps 								INT						NOT NULL,
	uf_id_ctps				 				bigint					NOT NULL,
	numero_ident_civil_nac			 		INT								,
	org_emissor_ident_civil_nac				VARCHAR(255) 					,
	dt_emissao_ident_civil_nac				DATETIME2(7)					,
	numero_rg 								INT						NOT NULL,
	orgao_emissor_rg 						VARCHAR(255) 			NOT NULL,
	dt_emissao_rg	 						DATETIME2(7)			NOT NULL,
	num_reg_nac_estrangeiro					INT								,
	org_emissor_reg_nac_estrangeiro			VARCHAR(255)					,
	dt_emissao_reg_nac_estrangeiro		 	DATETIME2(7)					,
	registro_profissional					INT								,
	dt_emissao_reg_profissional				DATETIME2(7)					,
	dt_validade_reg_profissional			DATETIME2(7)					,
	numero_cnh 								INT								,
	dt_validade_cnh	 						DATETIME2(7)					,
	dt_primeira_cnh	 						DATETIME2(7)					,
	dt_emissao_cnh	 						DATETIME2(7)					,
	categoria_cnh							VARCHAR(255) 					,
	uf_id_cnh								bigint							,
	
-- SEÇÃO DEPENDENTES (7)
	
	tipo_dependente 						VARCHAR(255) 					,
	nome_dependente 						VARCHAR(255) 					,
	cpf_dependente 							VARCHAR(11) 					,
	imposto_retido_fonte					bit								,
	plano_saude_privado						bit								,
	recebe_beneficio						bit								,
	capacitacao_profissional				bit								,
	created_at 								datetime2(7) 			NOT NULL,
	updated_at 								datetime2(7) 			NOT NULL,
	created_by 								bigint,
	updated_by 								bigint,
		
	CONSTRAINT fk_nacionalidade_id FOREIGN KEY (nacionalidade_id) REFERENCES nacionalidade(id),
	CONSTRAINT fk_empresa_filial_id FOREIGN KEY (empresa_filial_id) REFERENCES empresa_filial(id),
	CONSTRAINT fk_categoria_prestador_id FOREIGN KEY (categoria_prestador_id) REFERENCES categoria_profissional(id),
	CONSTRAINT fk_uf_id_endereco FOREIGN KEY (uf_id_endereco) REFERENCES unidade_federativa(id),
	CONSTRAINT fk_municipio_prestador_id FOREIGN KEY (municipio_prestador_id) REFERENCES municipio(id),
	CONSTRAINT fk_cbo_prestador_id FOREIGN KEY (cbo_prestador_id) REFERENCES cbo(id),
	CONSTRAINT fk_verba_id FOREIGN KEY (verba_id) REFERENCES verba(id),
	CONSTRAINT fk_convenio_id FOREIGN KEY (convenio_id) REFERENCES convenio(id),
	CONSTRAINT fk_uf_id_ctps FOREIGN KEY (uf_id_ctps) REFERENCES unidade_federativa(id),
	CONSTRAINT fk_uf_id_cnh FOREIGN KEY (uf_id_cnh) REFERENCES unidade_federativa(id)
)