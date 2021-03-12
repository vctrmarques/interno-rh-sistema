--Railson Silva
--Add column centro_custo_id 
  ALTER TABLE funcionario
    ADD centro_custo_id bigint
    CONSTRAINT fk_functionario_cetro_custo foreign key (centro_custo_id) REFERENCES centro_custo(id)
