create table historico_certidao_ex_segurado(
 id bigint not null IDENTITY PRIMARY KEY,
 certidao_ex_segurado_id bigint not null,
 status varchar(255),
 observacao varchar(max),
 created_at datetime not null default getDate(),
 updated_at datetime not null default getDate(),
 created_by bigint not null,
 updated_by bigint not null,
 CONSTRAINT fk_certidao_ex_segurado_historico_certidao FOREIGN KEY (certidao_ex_segurado_id) REFERENCES certidao_ex_segurado(id)
 );
