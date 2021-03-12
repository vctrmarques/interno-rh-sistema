--Alteração da tabela consignado ajuste.
--Davi Queiroz.

BEGIN IF (SELECT COUNT(id) FROM consignado) > 0 AND (SELECT count(id) FROM UNIDADE_FEDERATIVA ) > 0
	UPDATE consignado SET id_unidade_federativa_consignatario = 1;
END;
 
BEGIN IF (SELECT COUNT(id) FROM consignado) > 0 AND (SELECT count(id) FROM UNIDADE_FEDERATIVA ) > 0
	UPDATE consignado SET modalidade = 'SERVIDORES_PUBLICOS';
END;


