create table frequencia_licenca_certidao_ex_segurado(
 id bigint not null IDENTITY PRIMARY KEY,
 frequencia_certidao_ex_segurado_id bigint not null,
 licenca varchar(255) not null,
 descricao varchar (255) not null,
 created_at datetime not null default getDate(),
 updated_at datetime not null default getDate(),
 created_by bigint not null,
 updated_by bigint not null,
 CONSTRAINT fk_frequencia_certidao_licenca_frequencia FOREIGN KEY (frequencia_certidao_ex_segurado_id) REFERENCES frequencia_certidao_ex_segurado(id)
);
