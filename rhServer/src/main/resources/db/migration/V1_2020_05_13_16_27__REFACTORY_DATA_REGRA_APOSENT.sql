-- Flávio Silva
-- Ajuste nas regras de aposentadoria. Padronização.

-- Deletando todas as regras
DELETE FROM regra_aposentadoria;

-- Inserindo regras novas atualizadas.
INSERT INTO regra_aposentadoria (created_at,updated_at,created_by,updated_by,abono_permanencia,artigo,idade_homem,idade_mulher,lei_base,licenca_premio,modalidade_aposentadoria,modalidade_aposentadoria_nome,pedagio,tempo_cargo_efetivo_homem,tempo_cargo_efetivo_mulher,tempo_carreira,tempo_contribuicao_homem,tempo_contribuicao_mulher,tempo_servico_publico,tipo_aposentadoria,tipo_regra,vigencia,proventos,tipo_vigencia,idade_homem_formula,idade_mulher_formula,tempo_contribuicao_homem_formula,tempo_contribuicao_mulher_formula,tempo_servico_em_pleno_exercicio) VALUES 
('2018-11-28 09:42:38.031','2019-01-09 07:00:37.342',1,1,1,'ART 40 §19, CF/88',60,55,'ART 40, §1º, III, a, CF/88',NULL,'GERAL',NULL,0,5,5,0,35,30,10,'VOLUNTARIA','CONVENCIONAL','2003-12-31 00:00:00.000','MÉDIA(INTEGRAL)','IGNORAR',NULL,NULL,NULL,NULL,0)
,('2018-11-28 09:45:02.501','2019-01-09 07:00:30.975',1,1,0,NULL,65,60,'ART 40, §1º, III, b, CF/88',NULL,'GERAL',NULL,0,5,5,0,0,0,10,'VOLUNTARIA','CONVENCIONAL','2003-12-31 00:00:00.000','MÉDIA/PROPORCIONAL','IGNORAR',NULL,NULL,NULL,NULL,0)
,('2018-11-28 09:49:17.517','2020-05-13 13:47:58.131',1,1,1,'ART 2º §5º, EC 41/03',53,48,'ART 2º EC 41/03',NULL,'GERAL',NULL,1,5,5,0,NULL,NULL,0,'VOLUNTARIA','TRANSICAO','1998-12-16 00:00:00.000','MÉDIA/PROPORCIONAL','ANTES',NULL,NULL,'=(35- Math.abs (@vigencia - @dataIngressoServicoPublico)/365.25)*1.2 + Math.abs (@vigencia - @dataIngressoServicoPublico)/365.25','=(30- Math.abs (@vigencia - @dataIngressoServicoPublico)/365.25)*1.2 + Math.abs (@vigencia - @dataIngressoServicoPublico)/365.25',0)
,('2018-11-28 09:52:02.692','2020-05-13 13:50:12.975',1,1,1,'ART 40 §19 CF/88',60,55,'ART 6º EC 41/03',NULL,'GERAL',NULL,0,5,5,10,35,30,20,'VOLUNTARIA','TRANSICAO','2003-12-31 00:00:00.000','INTEGRAL/PARIDADE','ATE',NULL,NULL,NULL,NULL,0)
,('2019-01-03 12:17:14.249','2020-03-19 10:36:48.246',1,1,0,NULL,NULL,NULL,'ART 3º EC 47/05',0,'GERAL',NULL,0,5,5,15,35,30,25,'VOLUNTARIA','TRANSICAO','1998-12-16 12:04:37.495','Integrais','ATE','=
variavel(tempoDeContribuicao) = @tempoDeContribuicao * 365;
variavel(idade);
do {
	variavel(idadeEmDias) = @diasDeIdade + (tempoDeContribuicao - @tempoDeContribuicaoTotal);
        variavel(tempoDeContribuicaoUsada) = tempoDeContribuicao;
        tempoDeContribuicao = tempoDeContribuicao + 1;
        variavel(soma) = idadeEmDias + tempoDeContribuicaoUsada;
        idade = idadeEmDias;
}
while (soma < 34675 || (Math.floor(tempoDeContribuicaoUsada / 365) + Math.floor(idade / 365)) < 95 )','=
variavel(tempoDeContribuicao) = @tempoDeContribuicao * 365;
variavel(idade);
do {
	variavel(idadeEmDias) = @diasDeIdade + (tempoDeContribuicao - @tempoDeContribuicaoTotal);
        variavel(tempoDeContribuicaoUsada) = tempoDeContribuicao;
        tempoDeContribuicao = tempoDeContribuicao + 1;
        variavel(soma) = idadeEmDias + tempoDeContribuicaoUsada;
        idade = idadeEmDias;
}
while (soma < 31025 || (Math.floor(tempoDeContribuicaoUsada / 365) + Math.floor(idade / 365)) < 85 )',NULL,NULL,0)
,('2019-01-09 07:39:01.655','2020-05-13 14:10:30.319',1,1,1,'ART 40º, §19º, CF/88',55,50,'ART. 6º EC 41/03',0,'ESPECIFICA','Professor',0,5,5,10,30,25,20,'VOLUNTARIA','TRANSICAO','2003-12-30 18:00:00.000','INTEGRAL/PARIDADE','ATE',NULL,NULL,NULL,NULL,1)
,('2019-01-09 07:43:03.138','2020-05-13 14:06:26.319',1,1,1,'ART 40º, §19º, CF/88',55,50,'ART 40º, §5º, CF/88',0,'ESPECIFICA','Professor',0,5,5,0,30,25,10,'VOLUNTARIA','CONVENCIONAL','2003-12-30 18:00:00.000','MÉDIA','IGNORAR',NULL,NULL,NULL,NULL,0)
;