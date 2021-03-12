-- Rodrigo Leite
-- Inserindo coluna certidao_ex_segurado_id

IF COL_LENGTH('certidao_ex_segurado', 'certidao_ex_segurado_id') is null
begin
    alter table certidao_ex_segurado add certidao_ex_segurado_id bigint null;
    alter table certidao_ex_segurado add CONSTRAINT fk_certidao_ex_segurado_certidao_ex_segurado_id FOREIGN KEY (certidao_ex_segurado_id) REFERENCES certidao_ex_segurado(id);
end