--Atualização dos tipos para o valor correto do enum.
--Flávio Silva

UPDATE dependente SET tipo = 'CONJUGE' WHERE tipo = 'Cônjuge';
UPDATE dependente SET tipo = 'COMPANHEIRO_A' WHERE tipo = 'Companheiro(a)';
UPDATE dependente SET tipo = 'PAI_MAE' WHERE tipo = 'Pai / Mãe';
UPDATE dependente SET tipo = 'MENOR_SEM_GUARDA' WHERE tipo = 'Menor Sem Guarda';
UPDATE dependente SET tipo = 'UNIVERSITARIO' WHERE tipo = 'Universitário';
UPDATE dependente SET tipo = 'BENEFICIARIO_FACULTATIVO' WHERE tipo = 'Beneficiário Facultativo';
UPDATE dependente SET tipo = 'ENTEADO_A_MENOR' WHERE tipo = 'Enteado(a) Menor';
UPDATE dependente SET tipo = 'IRMAO_A_MENOR' WHERE tipo = 'Irmão(ã) Menor';
UPDATE dependente SET tipo = 'FILHO_AS_MENOR' WHERE tipo = 'Filho(a) Menor';
UPDATE dependente SET tipo = 'MENOR_TUTELADO' WHERE tipo = 'Menor Tutelado';
UPDATE dependente SET tipo = 'ENTEADO_A_INVALIDO' WHERE tipo = 'Enteado(a) Inválido';
UPDATE dependente SET tipo = 'IRMAO_A_INVALIDO' WHERE tipo = 'Irmão(ã) Inválido';
UPDATE dependente SET tipo = 'FILHO_AS_INVALIDO' WHERE tipo = 'Filho(a) Inválido';
UPDATE dependente SET tipo = 'MENOR_TUTELADO_INVALIDO' WHERE tipo = 'Menor Tutelado Inválido';
UPDATE dependente SET tipo = 'OUTROS' WHERE tipo = 'Outros';