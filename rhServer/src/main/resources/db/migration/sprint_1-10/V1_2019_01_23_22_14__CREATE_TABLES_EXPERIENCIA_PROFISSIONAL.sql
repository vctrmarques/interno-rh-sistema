create table dbo.experiencia_profissional
(
  id             bigint NOT NULL IDENTITY(1,1),
  funcionario_id bigint       not null,
  created_at     datetime2 not null,
  updated_at     datetime2 not null,
  created_by     bigint,
  updated_by     bigint,
	CONSTRAINT pk_experiencia_profissional PRIMARY KEY (id),
	CONSTRAINT fk_experiencia_profissional_funcionario_id FOREIGN KEY (funcionario_id) REFERENCES funcionario(id)
);

create table experiencia_profissional_funcao
(
	experiencia_profissional_id bigint NOT NULL,
	funcao_id bigint NOT NULL,
	CONSTRAINT pk_experiencia_profissional_funcao PRIMARY KEY (experiencia_profissional_id,funcao_id),
	CONSTRAINT fk_experiencia_profissional_funcao_experiencia_profissional_id FOREIGN KEY (experiencia_profissional_id) REFERENCES experiencia_profissional(id) ,
	CONSTRAINT fk_experiencia_profissional_funcao_funcao_id FOREIGN KEY (funcao_id) REFERENCES funcao(id)
);

