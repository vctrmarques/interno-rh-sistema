-- Criação da tabela de funcao_historico_lei
-- Railson Silva


IF NOT EXISTS (SELECT * FROM SYS.TABLES WHERE name = 'funcao_historico_lei')
CREATE TABLE funcao_historico_lei(
	id bigint not null identity(1,1),
	motivo_lei varchar(50) not null,
	numero_lei int not null,
	data_lei datetime2 not null,
	funcao_id bigint not null,
	CONSTRAINT pk_funcao_historico_lei PRIMARY KEY (id),
	CONSTRAINT fk_funcao_historico_lei_funcao FOREIGN KEY (funcao_id) REFERENCES funcao(id)	
)
