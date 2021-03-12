create table certidao_ex_segurado_periodo(
 id bigint not null IDENTITY PRIMARY KEY,
 certidao_ex_segurado_id bigint not null,
 periodo_inicio date not null,
 periodo_final date not null,
 cargo varchar(255) not null,
 aproveitamento varchar(255) not null,
 created_at datetime not null default getDate(),
 updated_at datetime not null default getDate(),
 created_by bigint not null,
 updated_by bigint not null,
 CONSTRAINT fk_certidao_ex_segurado_periodo_certidao FOREIGN KEY (certidao_ex_segurado_id) REFERENCES certidao_ex_segurado(id)
);
