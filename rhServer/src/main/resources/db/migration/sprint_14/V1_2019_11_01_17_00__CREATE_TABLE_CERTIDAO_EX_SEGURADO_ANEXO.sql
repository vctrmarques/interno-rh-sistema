create table certidao_ex_segurado_anexo(
 anexo_id bigint not null,
 certidao_ex_segurado_id bigint not null,
 CONSTRAINT fk_certidao_ex_segurado_anexo_anexo FOREIGN KEY (anexo_id) REFERENCES anexo(id),
 CONSTRAINT fk_certidao_ex_segurado_anexo_certidao FOREIGN KEY (certidao_ex_segurado_id) REFERENCES certidao_ex_segurado(id),
);