-- Júlio Galvão

-- alteração da tabela de recisão de contrato, drop columns 'data_admissao', 'empresa_filial_id' e 'lotacao_id'

begin if col_length('recisao_contrato', 'motivo_id') is not null
	alter table recisao_contrato drop constraint fk_recisao_contrato_motivo;
    alter table recisao_contrato drop column motivo_id;
    alter table recisao_contrato add motivo_id bigint not null;
   	alter table recisao_contrato add constraint fk_recisao_contrato_motivo foreign key (motivo_id) references motivo_desligamento(id);
end

