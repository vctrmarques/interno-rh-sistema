--Railson Silva
--Create table  categoria_profissional_verba

IF NOT EXISTS 
(
    SELECT * FROM INFORMATION_SCHEMA.COLUMNS 
    WHERE table_name = 'categoria_profissional_verba'
)
BEGIN
	CREATE TABLE categoria_profissional_verba (
	
	categoria_profissional_id BIGINT NOT NULL,
	verba_id BIGINT NOT NULL
	
	CONSTRAINT fk_categoria_profissional_verba_categoria FOREIGN KEY (categoria_profissional_id) REFERENCES categoria_profissional(id),
	CONSTRAINT fk_categoria_profissional_verba_verba FOREIGN KEY (verba_id) REFERENCES verba(id)


)
END	