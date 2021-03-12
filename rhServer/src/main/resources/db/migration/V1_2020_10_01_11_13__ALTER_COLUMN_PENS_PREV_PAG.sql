--Flávio Silva
--Adição de colunas novas na tabela de pensao_previdenciaria_pagamento


if col_length('pensao_previdenciaria_pagamento', 'dt_ini_isencao_ir') is null
    begin
        alter table pensao_previdenciaria_pagamento add dt_ini_isencao_ir datetime2(7);
    end
    
if col_length('pensao_previdenciaria_pagamento', 'dt_fim_isencao_ir') is null
    begin
        alter table pensao_previdenciaria_pagamento add dt_fim_isencao_ir datetime2(7);
    end
    
if col_length('pensao_previdenciaria_pagamento', 'dt_ini_isencao_previdencia') is null
    begin
        alter table pensao_previdenciaria_pagamento add dt_ini_isencao_previdencia datetime2(7);
    end
    
if col_length('pensao_previdenciaria_pagamento', 'dt_fim_isencao_previdencia') is null
    begin
        alter table pensao_previdenciaria_pagamento add dt_fim_isencao_previdencia datetime2(7);
    end
    
