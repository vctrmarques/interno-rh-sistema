-- Criação da tabela de frequencia
-- Railson Silva


IF NOT EXISTS (SELECT * FROM SYS.TABLES WHERE name = 'frequencia')
CREATE TABLE frequencia(
	id bigint not null identity(1,1),
	funcionario_id bigint not null,
	data date,
	horas_trabalhadas datetime2,
	hora_extra datetime2,
	entrada_um datetime2,
	saida_um datetime2,
	entrada_dois datetime2,
	saida_dois datetime2,
	falta_id bigint,
	created_at datetime2(7) NOT NULL,
	updated_at datetime2(7) NOT NULL,
	created_by bigint,
	updated_by bigint,
	CONSTRAINT pk_frequencia PRIMARY KEY (id),
	CONSTRAINT fk_frequencia_funcionario FOREIGN KEY (funcionario_id) REFERENCES funcionario(id),
	CONSTRAINT fk_frequencia_falta FOREIGN KEY (falta_id) REFERENCES falta(id)
	
)
