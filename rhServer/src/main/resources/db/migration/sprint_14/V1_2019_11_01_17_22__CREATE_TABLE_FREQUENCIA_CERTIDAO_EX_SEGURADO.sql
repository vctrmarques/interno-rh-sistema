create table frequencia_certidao_ex_segurado(
 id bigint not null IDENTITY PRIMARY KEY,
 certidao_ex_segurado_id bigint not null,
 ano int not null,
 tempo_bruto int,
 faltas int ,
 licencas int ,
 licencas_sem_venc int ,
 suspensoes int,
 disponibilidade int,
 outros int,
 tempo_liquido int,
 created_at datetime not null default getDate(),
 updated_at datetime not null default getDate(),
 created_by bigint not null,
 updated_by bigint not null,
 CONSTRAINT fk_certidao_ex_segurado_frequencia_certidao FOREIGN KEY (certidao_ex_segurado_id) REFERENCES certidao_ex_segurado(id)
);
