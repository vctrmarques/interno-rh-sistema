--Railson Silva
--Adição das colunas minimo, livre e atual

IF NOT EXISTS 
(
    SELECT * FROM INFORMATION_SCHEMA.COLUMNS 
    WHERE table_name = 'equipamento_protecao_individual'
    AND column_name = 'minimo'
)
BEGIN
	alter table equipamento_protecao_individual add minimo int
END	

IF NOT EXISTS 
(
    SELECT * FROM INFORMATION_SCHEMA.COLUMNS 
    WHERE table_name = 'equipamento_protecao_individual'
    AND column_name = 'livre'
)
BEGIN
	alter table equipamento_protecao_individual add livre int
END	

IF NOT EXISTS 
(
    SELECT * FROM INFORMATION_SCHEMA.COLUMNS 
    WHERE table_name = 'equipamento_protecao_individual'
    AND column_name = 'atual'
)
BEGIN
	alter table equipamento_protecao_individual add atual int
END	