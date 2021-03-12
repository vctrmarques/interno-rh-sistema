
-- Atualização do enum de função das entidades relacionadas.
-- Flávio Silva


UPDATE declaracao_aposentadoria_assinatura SET funcao = 'PRESIDENTE_GOIANIAPREV' WHERE funcao = 'PRESIDENTE_UNIDADE';

UPDATE certidao_ex_segurado_assinatura SET funcao = 'PRESIDENTE_GOIANIAPREV' WHERE funcao = 'PRESIDENTE_UNIDADE';
