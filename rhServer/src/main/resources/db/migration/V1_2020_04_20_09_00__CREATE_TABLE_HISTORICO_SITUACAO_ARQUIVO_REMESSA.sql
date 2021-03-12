-- Rodrigo Leite
 

-- Criação da tabela historico situacao arquivo remessa pagamento
IF NOT EXISTS(SELECT * FROM SYS.TABLES WHERE name = 'arquivo_remessa_historico_situacao')
CREATE TABLE arquivo_remessa_historico_situacao
(
    id 							BIGINT 					 	NOT NULL IDENTITY,
    arquivo_remessa_id			BIGINT						NOT NULL,
    situacao					VARCHAR(100)				NOT NULL,
	created_at 					datetime2(7)	 			NOT NULL,
	updated_at 					datetime2(7) 				NOT NULL,
	created_by 					bigint						NULL,
	updated_by 					bigint						NULL,
	
	CONSTRAINT pk_arquivo_remessa_historico_situacao PRIMARY KEY (id),
	CONSTRAINT fk_arquivo_remessa_historico_arquivo_remessa FOREIGN KEY (arquivo_remessa_id) REFERENCES arquivo_remessa_pagamento(id)
)