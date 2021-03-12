-- Flávio Silva
-- Alteração da tabela folha_pagamento_contracheque para ajuste das colunas valorBruto, valorDesconto e valor liquido.

if col_length('folha_pagamento_contracheque', 'valor_bruto') is null
    begin
        alter table folha_pagamento_contracheque add valor_bruto float;
    end
    
if col_length('folha_pagamento_contracheque', 'valor_liquido') is null
    begin
        alter table folha_pagamento_contracheque add valor_liquido float;
    end
    
if col_length('folha_pagamento_contracheque', 'valor_desconto') is null
    begin
        alter table folha_pagamento_contracheque add valor_desconto float;
    end
    
if col_length('folha_pagamento_contracheque', 'valorBruto') is not null
    begin
        alter table folha_pagamento_contracheque drop column valorBruto;
    end

if col_length('folha_pagamento_contracheque', 'valorLiquido') is not null
    begin
        alter table folha_pagamento_contracheque drop column valorLiquido;
    end

if col_length('folha_pagamento_contracheque', 'valorDesconto') is not null
    begin
        alter table folha_pagamento_contracheque drop column valorDesconto;
    end
