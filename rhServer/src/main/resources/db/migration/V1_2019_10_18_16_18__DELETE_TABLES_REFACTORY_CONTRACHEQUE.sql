--Flávio Silva
--Devido a criação da tabela folha_pagamento_contracheque e o refactory resultante de sua criação, abaixo está o delete dos dados atuais.

begin
	DELETE FROM folha_pagamento_contracheque_verba;
	DELETE FROM folha_pagamento_lotacao;
	DELETE FROM folha_pagamento_contracheque;
	DELETE FROM folha_pagamento;
end