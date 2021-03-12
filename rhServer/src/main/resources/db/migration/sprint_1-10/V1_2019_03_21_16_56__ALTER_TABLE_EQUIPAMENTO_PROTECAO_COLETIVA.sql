--Railson Silva
--Adição das colunas minimo, livre e atual

IF NOT EXISTS 
(
    SELECT * FROM INFORMATION_SCHEMA.COLUMNS 
    WHERE table_name = 'equipamento_protecao_coletiva'
    AND column_name = 'minimo'
)
BEGIN
	alter table equipamento_protecao_coletiva add minimo int
END	

IF NOT EXISTS 
(
    SELECT * FROM INFORMATION_SCHEMA.COLUMNS 
    WHERE table_name = 'equipamento_protecao_coletiva'
    AND column_name = 'livre'
)
BEGIN
	alter table equipamento_protecao_coletiva add livre int
END	

IF NOT EXISTS 
(
    SELECT * FROM INFORMATION_SCHEMA.COLUMNS 
    WHERE table_name = 'equipamento_protecao_coletiva'
    AND column_name = 'atual'
)
BEGIN
	alter table equipamento_protecao_coletiva add atual int
END	