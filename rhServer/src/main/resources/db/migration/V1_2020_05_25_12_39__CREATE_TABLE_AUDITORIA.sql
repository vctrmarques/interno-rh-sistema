-- Criação da tabela de Auditoria.
 
-- Marconi Motta

IF NOT EXISTS(SELECT * FROM SYS.TABLES WHERE name = 'auditoria')
CREATE TABLE auditoria(
 		id 							BIGINT 					 	NOT NULL IDENTITY,
 		acao						VARCHAR(255)				NOT NULL,
 		descricao					VARCHAR(5000)				NOT NULL,
 		entidade					VARCHAR(255)				NOT NULL,
 		id_entidade					BIGINT						NOT NULL,
 		created_at 					datetime2(7) 				NOT NULL,
		updated_at 					datetime2(7) 				NOT NULL,
		created_by 					bigint						NOT NULL,
		updated_by 					bigint						NOT NULL,		
);