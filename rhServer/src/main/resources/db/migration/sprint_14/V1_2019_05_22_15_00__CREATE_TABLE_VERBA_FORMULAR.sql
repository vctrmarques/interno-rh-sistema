-- Eduardo Costa

-- Criação da tabela de verba fórmula, responsável por armazenar as várias fórmulas que as verbas possuem.

IF NOT EXISTS (SELECT * FROM SYS.TABLES WHERE name = 'verba_formula')
CREATE TABLE verba_formula (
    id bigint NOT NULL IDENTITY(1,1),
    formula varchar(255) NOT NULL,
    descricao varchar(255) NOT NULL,
    verba_id bigint NOT NULL,
    created_at datetime2 not null,
	updated_at datetime2 not null,
	created_by bigint,
	updated_by bigint
    CONSTRAINT pk_verba_formula PRIMARY KEY (id),
    CONSTRAINT fk_verba_formula_verba FOREIGN KEY (verba_id) REFERENCES verba(id)
);