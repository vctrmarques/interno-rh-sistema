-- Victor Hugo

-- Armazenar os candidatos de uma requisição pessoal

CREATE TABLE requisicao_pessoal_candidato(
	id bigint NOT NULL IDENTITY(1,1),
	nome varchar(100) NOT NULL,
	comentario varchar(2000),
	comentario_curriculo varchar(2000),
	situacao varchar(30) NOT NULL,
	id_anexo bigint NOT NULL,
	id_req_pessoal bigint NOT NULL,
	created_at datetime2(7) NOT NULL,
	updated_at datetime2(7) NOT NULL,
	created_by bigint,
	updated_by bigint,
	
	CONSTRAINT pk_req_pes_candidato PRIMARY KEY (id),
    CONSTRAINT fk_req_pes_cand_to_anexo FOREIGN KEY (id_anexo) REFERENCES anexo(id),
    CONSTRAINT fk_req_pes_cand_to_req_pessoal FOREIGN KEY (id_req_pessoal) REFERENCES requisicao_pessoal(id)
);
