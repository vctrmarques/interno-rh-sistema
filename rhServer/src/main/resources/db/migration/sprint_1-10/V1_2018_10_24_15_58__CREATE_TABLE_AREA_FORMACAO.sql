-- Criação de tabela area_formacao
-- Davi Queiroz

-- Criação da tabela area_formacao

CREATE TABLE area_formacao (
	id bigint NOT NULL IDENTITY(1,1),
	area_formacao varchar(255) NOT NULL,
	created_at datetime2(7) NOT NULL,
	updated_at datetime2(7) NOT NULL,
	created_by bigint,
	updated_by bigint
) 
CREATE UNIQUE INDEX uk_area_formacao ON area_formacao (area_formacao) ;
