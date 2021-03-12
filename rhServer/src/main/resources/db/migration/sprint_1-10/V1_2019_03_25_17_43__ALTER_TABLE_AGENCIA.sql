ALTER TABLE agencia DROP COLUMN uf;
ALTER TABLE agencia DROP COLUMN municipio; 

DELETE FROM agencia;
ALTER TABLE agencia ADD unidade_federativa_id bigint not null 
constraint agencia_uf_id foreign key (unidade_federativa_id) references unidade_federativa(id);

ALTER TABLE agencia ADD municipio_id bigint not null
constraint agencia_municipio_id foreign key (municipio_id) references municipio(id);
