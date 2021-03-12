CREATE TABLE funcao_atividade (
	funcao_id bigint NOT NULL,
	atividade_id bigint NOT NULL,
	CONSTRAINT pk_funcao_atividade PRIMARY KEY (funcao_id,atividade_id),
	CONSTRAINT fk_funcao_atividade_funcao_id FOREIGN KEY (funcao_id) REFERENCES funcao(id) ,
	CONSTRAINT fk_funcao_atividade_atividade_id FOREIGN KEY (atividade_id) REFERENCES atividade(id)
) ;