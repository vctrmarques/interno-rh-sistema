-- Flávio Silva
-- Refatoração da tabela de incidencia de verba.

-- Copiando os dados da tabela antiga e inserindo na nova.
DECLARE @verba_incidente_id bigint, @verba_incidida_id bigint

DECLARE cur CURSOR FOR
SELECT
	verba_incidente_id,
	verba_incidida_id
FROM
	verba_incidencia

OPEN cur

FETCH NEXT FROM cur INTO @verba_incidente_id, @verba_incidida_id;
WHILE @@FETCH_STATUS = 0
BEGIN   
	
	INSERT INTO verba_incidenciaNova (verba_incidencia_id, verba_id) VALUES (@verba_incidente_id, @verba_incidida_id)

FETCH NEXT FROM cur INTO @verba_incidente_id, @verba_incidida_id;
END

CLOSE cur;
DEALLOCATE cur;
