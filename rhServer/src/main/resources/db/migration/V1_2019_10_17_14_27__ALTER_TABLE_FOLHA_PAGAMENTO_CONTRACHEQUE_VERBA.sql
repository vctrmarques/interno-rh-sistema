-- Flávio Silva
-- Alteração da tabela folha_pagamento_contracheque_verba excluir a coluna situacao e adicionar a coluna folha_pagamento_contracheque_id.

if col_length('folha_pagamento_contracheque_verba', 'folha_pagamento_contracheque_id') is null
    begin
        alter table folha_pagamento_contracheque_verba add folha_pagamento_contracheque_id bigint null;
        alter table folha_pagamento_contracheque_verba add constraint fk_folha_pagamento_contracheque_verba_folha_pagamento_contracheque_id
            foreign key (folha_pagamento_contracheque_id) references folha_pagamento_contracheque(id);
    end

if col_length('folha_pagamento_contracheque_verba', 'situacao') is not null
    begin
        alter table folha_pagamento_contracheque_verba drop column situacao;
    end
