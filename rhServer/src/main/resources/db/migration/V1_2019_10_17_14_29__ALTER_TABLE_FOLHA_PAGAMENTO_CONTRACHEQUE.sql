-- Flávio Silva
-- Alteração da tabela folha_pagamento_contracheque para adicionar a coluna situacao

if col_length('folha_pagamento_contracheque', 'situacao') is null
    begin
        alter table folha_pagamento_contracheque add situacao varchar(255);
    end
