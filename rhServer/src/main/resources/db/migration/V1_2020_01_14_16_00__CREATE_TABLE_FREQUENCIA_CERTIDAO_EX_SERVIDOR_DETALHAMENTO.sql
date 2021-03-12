-- Criação das tabelas declaração tempo contribuição ex-servidor, dados funcionais.
-- Insere dados no menu
 
-- Rodrigo Leite

IF NOT EXISTS(SELECT * FROM SYS.TABLES WHERE name = 'frequencia_certidao_ex_servidor_detalhamento')
CREATE TABLE frequencia_certidao_ex_servidor_detalhamento
(
    id										bigint			not null IDENTITY PRIMARY KEY,
	certidao_ex_servidor_id			 		bigint			not null,
	periodo_inicio 							date 			null,
	periodo_final 							date 			null,
	tempo 									int 			null,
	descricao 								varchar(max) 	null,
	tipo		 							varchar(100) 	null,
	created_at 								datetime 		not null default getDate(),
	updated_at 								datetime		not null default getDate(),
	created_by 								bigint 			not null,
	updated_by 								bigint 			not null,
	
	CONSTRAINT fk_detalhamenot_certidao_ex_servidor FOREIGN KEY (certidao_ex_servidor_id) REFERENCES certidao_ex_segurado(id)
)

-- DROP de tabela descontinuada.

IF EXISTS(SELECT * FROM SYS.TABLES WHERE name = 'frequencia_deducao_certidao_ex_segurado')
    DROP TABLE frequencia_deducao_certidao_ex_segurado

IF EXISTS(SELECT * FROM SYS.TABLES WHERE name = 'frequencia_licenca_certidao_ex_segurado')
    DROP TABLE frequencia_licenca_certidao_ex_segurado