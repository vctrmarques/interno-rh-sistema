-- Rodrigo Leite
-- Inserindo coluna numero_retificação

IF COL_LENGTH('certidao_ex_segurado', 'numero_retificacao') is null
begin
    alter table certidao_ex_segurado add numero_retificacao bigint;
end

-- Inserindo colunas na tabela historico_certidao_ex_segurado para atender a copia da tabela certidao_ex_segurado

IF COL_LENGTH('historico_certidao_ex_segurado', 'id_funcionario') is null
begin
	alter table historico_certidao_ex_segurado add id_funcionario bigint;
end

IF COL_LENGTH('historico_certidao_ex_segurado', 'numero_certidao') is null
begin
	alter table historico_certidao_ex_segurado add numero_certidao bigint;
end

IF COL_LENGTH('historico_certidao_ex_segurado', 'ano_certidao') is null
begin
	alter table historico_certidao_ex_segurado add ano_certidao int;
end

IF COL_LENGTH('historico_certidao_ex_segurado', 'id_lotacao') is null
begin
	alter table historico_certidao_ex_segurado add id_lotacao bigint;
end

IF COL_LENGTH('historico_certidao_ex_segurado', 'data_exoneracao') is null
begin
	alter table historico_certidao_ex_segurado add data_exoneracao DATETIME;
end

IF COL_LENGTH('historico_certidao_ex_segurado', 'fonte_informacao') is null
begin
	alter table historico_certidao_ex_segurado add fonte_informacao varchar(255);
end

IF COL_LENGTH('historico_certidao_ex_segurado', 'fonte_informacao') is null
begin
	alter table historico_certidao_ex_segurado add fonte_informacao varchar(255);
end

IF COL_LENGTH('historico_certidao_ex_segurado', 'numero_retificacao') is null
begin
    alter table historico_certidao_ex_segurado add numero_retificacao bigint;
end
