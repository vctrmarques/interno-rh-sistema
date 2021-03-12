--Criação da tabela curso. 
--Roberto Araujo.

IF NOT EXISTS(SELECT * FROM SYS.TABLES WHERE name = 'curso')
CREATE TABLE curso
(
    id 						BIGINT 			PRIMARY KEY 	NOT NULL IDENTITY,
    nome_curso				VARCHAR(255)					NOT NULL,
    codigo_mec				VARCHAR(255)					NOT NULL,
    id_grau_academico		BIGINT							NOT NULL,
    id_area_formacao		BIGINT							NOT NULL,
    created_at 				datetime2(7) 					NOT NULL,
	updated_at 				datetime2(7) 					NOT NULL,
	created_by 				bigint,
	updated_by 				bigint,
	
	CONSTRAINT fk_curso_id_grau_academico FOREIGN KEY (id_grau_academico) REFERENCES grau_academico(id),
	CONSTRAINT fk_curso_id_area_formacao FOREIGN KEY (id_area_formacao) REFERENCES area_formacao(id) 
)

