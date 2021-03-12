--Alteração da tabela Funcao, alterando data_lei para ser default NULL e não NOT NULL. 
--Marconi Motta.

 ALTER TABLE funcao ALTER COLUMN data_lei datetime2(7) NULL;