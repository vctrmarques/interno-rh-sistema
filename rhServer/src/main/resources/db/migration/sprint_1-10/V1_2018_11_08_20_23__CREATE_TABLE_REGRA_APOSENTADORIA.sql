--Criação da tabela de regra de aposentadoria
--Flávio Silva

CREATE TABLE regra_aposentadoria (
	id bigint NOT NULL IDENTITY(1,1),
	created_at datetime2(7) NOT NULL,
	updated_at datetime2(7) NOT NULL,
	created_by bigint,
	updated_by bigint,
	abono_permanencia bit,
	artigo varchar(255),
	idade_homem int,
	idade_mulher int,
	lei_base varchar(255) NOT NULL,
	licenca_premio bit,
	modalidade_aposentadoria varchar(255) NOT NULL,
	modalidade_aposentadoria_nome varchar(255),
	pedagio bit,
	tempo_cargo_efetivo_homem int,
	tempo_cargo_efetivo_mulher int,
	tempo_carreira int,
	tempo_contribuicao_homem int,
	tempo_contribuicao_mulher int,
	tempo_servico_publico int,
	tipo_aposentadoria varchar(255),
	tipo_regra varchar(255),
	vigencia datetime2(7) NOT NULL,
	CONSTRAINT pk_regra_aposentadoria PRIMARY KEY (id)
) ;
