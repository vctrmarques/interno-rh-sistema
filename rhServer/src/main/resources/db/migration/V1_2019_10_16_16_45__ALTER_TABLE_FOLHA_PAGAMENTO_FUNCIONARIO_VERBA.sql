-- Flávio Silva

-- Drop das colunas folha de pagamento e funcionario, elas serão enviadas para a nova tabela folha_pagamento_contracheque
-- Alteração da tabela folha_pagamento_funcionario_verba para o nome de folha_pagamento_contracheque_verba


if col_length('folha_pagamento_funcionario_verba', 'folha_pagamento_id') is not null
    begin
	    alter table folha_pagamento_funcionario_verba drop constraint fk_folha_pagamento_funcionario_verba_folha;
        alter table folha_pagamento_funcionario_verba drop column folha_pagamento_id;
    end

if col_length('folha_pagamento_funcionario_verba', 'funcionario_id') is not null
    begin
        alter table folha_pagamento_funcionario_verba drop constraint fk_folha_pagamento_funcionario_verba_funcionario;
        alter table folha_pagamento_funcionario_verba drop column funcionario_id;
    end

begin
    EXEC sp_rename 'dbo.folha_pagamento_funcionario_verba', 'folha_pagamento_contracheque_verba';
end