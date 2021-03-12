/** Adicionando o valor calculado da folha junto à folha processada. **/

alter table folha_pagamento_funcionario_verba add valor float constraint df_valor default (0);
