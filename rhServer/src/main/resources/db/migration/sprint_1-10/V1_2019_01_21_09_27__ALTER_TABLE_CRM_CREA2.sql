--Alteração da tabela crm_crea, adicionando coluna. 
--Davi Queiroz.

BEGIN IF (SELECT COUNT(id) FROM CRM_CREA) > 0
	UPDATE crm_crea SET tipo = 'CRM';
END;
 

BEGIN IF COL_LENGTH('crm_crea', 'tipo') IS NOT NULL
    ALTER TABLE crm_crea
    ALTER COLUMN tipo varchar(10) NOT NULL;
END