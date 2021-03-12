-- Flávio Silva
-- Alteração da tabela certidao_compensacao_historico para adicionar atributos e excluir constraints not null.
    
if col_length('certidao_compensacao_historico', 'tipo_certidao_compensacao') is null
    begin
        alter table certidao_compensacao_historico add tipo_certidao_compensacao varchar(255) NOT NULL DEFAULT('CERTIDAO_COMPENSACAO');
    end
			