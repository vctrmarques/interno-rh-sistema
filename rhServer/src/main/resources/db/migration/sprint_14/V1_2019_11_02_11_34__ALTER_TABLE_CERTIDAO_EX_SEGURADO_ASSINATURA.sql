    alter table certidao_ex_segurado_assinatura add created_at datetime not null default getDate();

    alter table certidao_ex_segurado_assinatura add updated_at datetime not null default getDate();

    alter table certidao_ex_segurado_assinatura add created_by bigint not null;

    alter table certidao_ex_segurado_assinatura add updated_by bigint not null;

    alter table certidao_ex_segurado_assinatura add CONSTRAINT fk_certidao_ex_segurado_assinatura_funcionario_criacao FOREIGN KEY (created_by) REFERENCES funcionario(id);

    alter table certidao_ex_segurado_assinatura add CONSTRAINT fk_certidao_ex_segurado_assinatura_funcionario_atualizacao FOREIGN KEY (updated_by) REFERENCES funcionario(id);
