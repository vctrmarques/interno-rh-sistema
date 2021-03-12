create table tempo_especial_certidao_ex_segurado(
 id bigint not null IDENTITY PRIMARY KEY,
 certidao_ex_segurado_id bigint not null,
 periodo_inicial date not null,
 periodo_final date not null,
 tipo_tempo_especial varchar(50),
 grau varchar(50),
 tempo int, 
 created_at datetime not null default getDate(),
 updated_at datetime not null default getDate(),
 created_by bigint not null,
 updated_by bigint not null,
 CONSTRAINT fk_certidao_ex_segurado_tempo_especial_certidao FOREIGN KEY (certidao_ex_segurado_id) REFERENCES certidao_ex_segurado(id)
 );
