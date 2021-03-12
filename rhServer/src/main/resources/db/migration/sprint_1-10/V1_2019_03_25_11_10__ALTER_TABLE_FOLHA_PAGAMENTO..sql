--Railson Silva
--Add column folha_competencia_id 

IF NOT EXISTS 
(
    SELECT * FROM INFORMATION_SCHEMA.COLUMNS 
    WHERE table_name = 'folha_pagamento'
    AND column_name = 'folha_competencia_id'
)
BEGIN
	ALTER TABLE folha_pagamento
	add folha_competencia_id bigint
	
	constraint folha_pagamento_competencia foreign key (folha_competencia_id) references folha_competencia(id);
END	