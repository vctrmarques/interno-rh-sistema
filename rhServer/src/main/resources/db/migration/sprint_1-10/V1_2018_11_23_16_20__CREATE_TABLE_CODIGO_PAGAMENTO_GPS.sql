--Criação da tabela Código Pagamento GPS. 
--Roberto Araujo.

IF NOT EXISTS(SELECT * FROM SYS.TABLES WHERE name = 'codigo_pagamento_gps')
CREATE TABLE codigo_pagamento_gps
(
    id 				BIGINT 		PRIMARY KEY 	NOT NULL IDENTITY,
	codigo			INT							NOT NULL,
    descricao 		VARCHAR(255) 				NOT NULL,
	created_at 		datetime2(7)	 			NOT NULL,
	updated_at 		datetime2(7) 				NOT NULL,
	created_by 		bigint								,
	updated_by 		bigint								  
)