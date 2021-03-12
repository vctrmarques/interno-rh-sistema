-- Criação da tabela de aliquota
-- Railson Silva


CREATE TABLE aliquota(
	id bigint primary key not null identity(1,1),
	valor_inicial float not null,
	valor_final float not null,
	deducao float,
	aliquota float not null,
	ano int not null,
	faixa varchar(100),
	created_at datetime2(7) NOT NULL,
	updated_at datetime2(7) NOT NULL,
	created_by bigint,
	updated_by bigint  
);
