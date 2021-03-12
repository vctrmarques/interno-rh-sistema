--Criação da tabela verba_incidencia responsável por manter as diversas incidencias que uma verba possue. 
--Eduardo Costa.

IF NOT EXISTS(SELECT * FROM SYS.TABLES WHERE name = 'verba_incidencia')
CREATE TABLE verba_incidencia
(
    id 				  	BIGINT 		PRIMARY KEY 	NOT NULL IDENTITY,
	verba_incidente_id  BIGINT						NOT NULL,
    verba_incidida_id   BIGINT 				        NOT NULL,
    created_at datetime2 not null,
	updated_at datetime2 not null,
	created_by bigint,
	updated_by bigint,
    CONSTRAINT fk_verba_incidencia_verba_incidente_id FOREIGN KEY (verba_incidente_id) REFERENCES verba(id),
    CONSTRAINT fk_verba_incidencia_verba_verba_incidida_id FOREIGN KEY (verba_incidida_id) REFERENCES verba(id)
)