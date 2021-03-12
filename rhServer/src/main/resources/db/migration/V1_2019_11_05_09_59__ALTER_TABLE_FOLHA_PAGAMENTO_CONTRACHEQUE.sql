-- Flávio Silva
-- Alteração da tabela folha_pagamento_contracheque.

if col_length('folha_pagamento_contracheque', 'nome_funcionario') is null
    begin
        alter table folha_pagamento_contracheque add nome_funcionario varchar(255);
    end
    
if col_length('folha_pagamento_contracheque', 'matricula_funcionario') is null
    begin
        alter table folha_pagamento_contracheque add matricula_funcionario int;
    end
    
if col_length('folha_pagamento_contracheque', 'lotacao_funcionario') is null
    begin
        alter table folha_pagamento_contracheque add lotacao_funcionario varchar(255);
    end
    
if col_length('folha_pagamento_contracheque', 'municipio_funcionario') is null
    begin
        alter table folha_pagamento_contracheque add municipio_funcionario varchar(255);
    end
    
if col_length('folha_pagamento_contracheque', 'data_admissao_funcionario') is null
    begin
        alter table folha_pagamento_contracheque add data_admissao_funcionario datetime2(7);
    end
    
if col_length('folha_pagamento_contracheque', 'cargo_efetivo_funcionario') is null
    begin
        alter table folha_pagamento_contracheque add cargo_efetivo_funcionario varchar(255);
    end
    
if col_length('folha_pagamento_contracheque', 'data_nascimento_funcionario') is null
    begin
        alter table folha_pagamento_contracheque add data_nascimento_funcionario datetime2(7);
    end
    
if col_length('folha_pagamento_contracheque', 'nivel_funcionario') is null
    begin
        alter table folha_pagamento_contracheque add nivel_funcionario varchar(255);
    end
    
if col_length('folha_pagamento_contracheque', 'cargo_funcao_funcionario') is null
    begin
        alter table folha_pagamento_contracheque add cargo_funcao_funcionario varchar(255);
    end
    
if col_length('folha_pagamento_contracheque', 'referencia_funcionario') is null
    begin
        alter table folha_pagamento_contracheque add referencia_funcionario varchar(255);
    end
    
if col_length('folha_pagamento_contracheque', 'vinculo_funcionario') is null
    begin
        alter table folha_pagamento_contracheque add vinculo_funcionario varchar(255);
    end
    
if col_length('folha_pagamento_contracheque', 'situacao_funcionario') is null
    begin
        alter table folha_pagamento_contracheque add situacao_funcionario varchar(255);
    end
    
if col_length('folha_pagamento_contracheque', 'identidade_funcionario') is null
    begin
        alter table folha_pagamento_contracheque add identidade_funcionario varchar(255);
    end
    
if col_length('folha_pagamento_contracheque', 'cpf_funcionario') is null
    begin
        alter table folha_pagamento_contracheque add cpf_funcionario varchar(255);
    end
    
if col_length('folha_pagamento_contracheque', 'orgao_pagador_funcionario') is null
    begin
        alter table folha_pagamento_contracheque add orgao_pagador_funcionario varchar(255);
    end
    
if col_length('folha_pagamento_contracheque', 'dep_sf_funcionario') is null
    begin
        alter table folha_pagamento_contracheque add dep_sf_funcionario int;
    end
    
if col_length('folha_pagamento_contracheque', 'dep_ir_funcionario') is null
    begin
        alter table folha_pagamento_contracheque add dep_ir_funcionario int;
    end
    
if col_length('folha_pagamento_contracheque', 'carga_horaria_funcionario') is null
    begin
        alter table folha_pagamento_contracheque add carga_horaria_funcionario varchar(255);
    end
    
    
    