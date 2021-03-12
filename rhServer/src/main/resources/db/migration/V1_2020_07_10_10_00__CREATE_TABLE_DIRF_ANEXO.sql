IF NOT EXISTS(SELECT * FROM SYS.TABLES WHERE name = 'dirf_anexo')
CREATE TABLE dirf_anexo
(
    dirf_id			BIGINT		NOT NULL,
    anexo_id		BIGINT		NOT NULL,
	
	CONSTRAINT fk_dirf_anexo_dirf FOREIGN KEY (dirf_id) REFERENCES dirf(id),
	CONSTRAINT fk_dirf_anexo_anexo FOREIGN KEY (anexo_id) REFERENCES anexo(id)
)