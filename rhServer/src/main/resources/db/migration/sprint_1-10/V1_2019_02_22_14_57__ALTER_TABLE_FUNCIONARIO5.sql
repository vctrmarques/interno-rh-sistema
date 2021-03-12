--Alteração da tabela Funcionário, renomeando colunas. 
--Davi Queiroz.

  exec sp_RENAME 'funcionario.telefone_residencial', 'telefone_principal' , 'COLUMN';

  exec sp_RENAME 'funcionario.telefone_celular', 'telefone_opcional' , 'COLUMN';