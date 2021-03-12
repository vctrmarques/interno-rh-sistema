-- Júlio Galvão

-- alteração da tabela de recisão de contrato, drop columns 'data_admissao', 'empresa_filial_id' e 'lotacao_id'

begin if col_length('recisao_contrato', 'data_admissao') is not null
    alter table recisao_contrato drop column data_admissao;
end
