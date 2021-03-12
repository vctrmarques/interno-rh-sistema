CREATE TABLE funcao_curso (
	funcao_id bigint NOT NULL,
	curso_id bigint NOT NULL,
	CONSTRAINT pk_funcao_curso PRIMARY KEY (funcao_id,curso_id),
	CONSTRAINT fk_funcao_curso_funcao_id FOREIGN KEY (funcao_id) REFERENCES funcao(id) ,
	CONSTRAINT fk_funcao_curso_curso_id FOREIGN KEY (curso_id) REFERENCES curso(id) 
) ;