create table certidao_ex_segurado(
	id bigint not null IDENTITY PRIMARY KEY,
	id_funcionario bigint not null,
	numero_certidao int not null,
	ano_certidao int default YEAR(getdate()),
	status_atual varchar(50),
	id_lotacao bigint not null,
	data_exoneracao DATETIME not null,
	fonte_informacao varchar(255) not NULL,
	created_at datetime not null default getDate(),
	updated_at datetime not null default getDate(),
	created_by bigint not null,
	updated_by bigint not null,
	CONSTRAINT fk_ex_segurado_funcionario FOREIGN KEY (id_funcionario) REFERENCES funcionario(id),
	CONSTRAINT fk_ex_segurado_lotacao FOREIGN KEY (id_lotacao) REFERENCES lotacao(id)
);

