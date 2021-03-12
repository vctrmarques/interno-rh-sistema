-- Lucas Moura

-- Criação da tabela de Dependente Benefícios

IF NOT EXISTS (SELECT * FROM SYS.TABLES WHERE name = 'dependente_beneficio')
CREATE TABLE dependente_beneficio (
	
	id 									bigint 		PRIMARY KEY		NOT NULL IDENTITY(1,1),
	consignado_id 						bigint 						NOT NULL,
	dependente_id						bigint 						NOT NULL,
	valor								numeric(13,2) 				NOT NULL,
	created_at 							DATETIME2(7) 				NOT NULL,
	updated_at 							DATETIME2(7) 				NOT NULL,
	created_by 							bigint								,
	updated_by 							bigint								,
	
	CONSTRAINT fk_dependente_beneficio_consignado_id FOREIGN KEY (consignado_id) REFERENCES consignado(id),
	CONSTRAINT fk_dependente_beneficio_dependente_id FOREIGN KEY (dependente_id) REFERENCES dependente(id)
		
)