create table relacao_remuneracao_certidao_ex_segurado(
 id bigint not null IDENTITY PRIMARY KEY,
 certidao_ex_segurado_id bigint not null,
 ano int not null,
 janeiro int,
 fevereiro int,
 marco int,
 abril int,
 maio int,
 junho int,
 julho int,
 agosto int,
 setembro int,
 outubro int,
 novembro int,
 dezembro int,
 decimo_terceiro int,
 created_at datetime not null default getDate(),
 updated_at datetime not null default getDate(),
 created_by bigint not null,
 updated_by bigint not null,
 CONSTRAINT fk_certidao_ex_segurado_relacao_remuneracao_certidao FOREIGN KEY (certidao_ex_segurado_id) REFERENCES certidao_ex_segurado(id)
 );
