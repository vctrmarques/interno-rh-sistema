CREATE TABLE funcao_habilidade (
	funcao_id bigint NOT NULL,
	habilidade_id bigint NOT NULL,
	CONSTRAINT pk_funcao_habilidade PRIMARY KEY (funcao_id,habilidade_id),
	CONSTRAINT fk_funcao_habilidade_funcao_id FOREIGN KEY (funcao_id) REFERENCES funcao(id) ,
	CONSTRAINT fk_funcao_habilidade_habilidade_id FOREIGN KEY (habilidade_id) REFERENCES habilidade(id)
) ;