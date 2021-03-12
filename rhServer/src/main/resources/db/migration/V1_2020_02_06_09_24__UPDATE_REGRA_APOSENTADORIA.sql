-- Flávio Silva
-- Atualizando as formulas de idade da regra lei base ART 3º EC 47/05

UPDATE regra_aposentadoria SET idade_homem_formula = '=
variavel(tempoDeContribuicao) = @tempoDeContribuicao * 365;
variavel(idade);
do {
	variavel(idadeEmDias) = @diasDeIdade + (tempoDeContribuicao - @tempoDeContribuicaoTotal);
        variavel(tempoDeContribuicaoUsada) = tempoDeContribuicao;
        tempoDeContribuicao = tempoDeContribuicao + 365;
        variavel(soma) = idadeEmDias + tempoDeContribuicaoUsada;
        idade = idadeEmDias;
}
while (soma < 34675)', idade_mulher_formula = '=
variavel(tempoDeContribuicao) = @tempoDeContribuicao * 365;
variavel(idade);
do {
	variavel(idadeEmDias) = @diasDeIdade + (tempoDeContribuicao - @tempoDeContribuicaoTotal);
        variavel(tempoDeContribuicaoUsada) = tempoDeContribuicao;
        tempoDeContribuicao = tempoDeContribuicao + 365;
        variavel(soma) = idadeEmDias + tempoDeContribuicaoUsada;
        idade = idadeEmDias;
}
while (soma < 31025)' where lei_base = 'ART 3º EC 47/05';