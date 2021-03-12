--Alteração da tabela crm_crea, adicionando coluna. 
	--Railson Silva.

BEGIN IF COL_LENGTH('crm_crea', 'tipo') IS NULL
    ALTER TABLE crm_crea
    ADD tipo varchar(10);
END
