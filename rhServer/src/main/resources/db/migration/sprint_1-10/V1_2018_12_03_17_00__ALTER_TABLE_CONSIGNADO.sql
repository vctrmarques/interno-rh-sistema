--Alteração da tabela Consignado, adicionando coluna. 
--Roberto Araujo.

  exec sp_RENAME 'consignado.orgao_expeditor', 'orgao_expedidor' , 'COLUMN'