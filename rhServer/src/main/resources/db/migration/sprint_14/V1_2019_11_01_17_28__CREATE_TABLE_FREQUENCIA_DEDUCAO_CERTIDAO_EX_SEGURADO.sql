create table frequencia_deducao_certidao_ex_segurado(
 id bigint not null IDENTITY PRIMARY KEY,
 frequencia_certidao_ex_segurado_id bigint not null,
 periodo_inicio date not null,
 periodo_final date not null,
 tempo int not null,
 descricao varchar(max) not null,
 tipo_deducao varchar(50) not null,
 created_at datetime not null default getDate(),
 updated_at datetime not null default getDate(),
 created_by bigint not null,
 updated_by bigint not null,
 CONSTRAINT fk_frequencia_certidao_deducao_frequencia FOREIGN KEY (frequencia_certidao_ex_segurado_id) REFERENCES frequencia_certidao_ex_segurado(id)
);
