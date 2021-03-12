--Alteração da tabela Funcionário, alterar o tipo da coluna pis_pasep de int para varchar(20). 
--Júlio Galvão.

begin if col_length('funcionario', 'pis_pasep') is not null
    alter table funcionario alter column pis_pasep varchar(20) not null;
end