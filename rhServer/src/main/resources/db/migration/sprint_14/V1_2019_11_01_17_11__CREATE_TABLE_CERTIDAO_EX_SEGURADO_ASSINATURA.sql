create table certidao_ex_segurado_assinatura(
 id bigint not null IDENTITY PRIMARY KEY,
 certidao_ex_segurado_id bigint not null,
 id_funcionario bigint not null,
 funcao varchar(50) not null,
 aba varchar(50) not null,
 CONSTRAINT fk_certidao_ex_segurado_assinatura_certidao FOREIGN KEY (certidao_ex_segurado_id) REFERENCES certidao_ex_segurado(id),
 CONSTRAINT fk_ex_segurado_assinatura_funcionario FOREIGN KEY (id_funcionario) REFERENCES funcionario(id),
);