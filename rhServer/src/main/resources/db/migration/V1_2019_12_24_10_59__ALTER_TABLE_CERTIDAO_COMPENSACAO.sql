-- Flávio Silva
-- Alteração da tabela certidao_compensacao para adicionar atributos e excluir constraints not null.
    
if col_length('certidao_compensacao', 'numero_retificacao') is null
    begin
        alter table certidao_compensacao add numero_retificacao bigint;
    end
    
if col_length('certidao_compensacao', 'certidao_compensacao_id') is null
    begin
	    alter table certidao_compensacao add certidao_compensacao_id bigint null;
	    alter table certidao_compensacao add CONSTRAINT fk_certidao_compensacao_certidao_compensacao_id FOREIGN KEY (certidao_compensacao_id) REFERENCES certidao_compensacao(id);
    end
    
if col_length('certidao_compensacao', 'processo') is not null
    begin
        alter table certidao_compensacao alter column processo varchar(255) null;
    end
    
if col_length('certidao_compensacao', 'funcionario_id') is not null
    begin
        alter table certidao_compensacao alter column funcionario_id bigint null;
    end
    
if col_length('certidao_compensacao', 'lotacao_id') is not null
    begin
        alter table certidao_compensacao alter column lotacao_id bigint null;
    end
    
if col_length('certidao_compensacao', 'fundo') is not null
    begin
        alter table certidao_compensacao alter column fundo varchar(255) null;
    end

if col_length('certidao_compensacao', 'arquivada') is null
    begin
        alter table certidao_compensacao add arquivada BIT NOT NULL DEFAULT(0);
    end
    
if col_length('certidao_compensacao', 'tipo_certidao_compensacao') is null
    begin
        alter table certidao_compensacao add tipo_certidao_compensacao varchar(255) NOT NULL DEFAULT('CERTIDAO_COMPENSACAO');
    end
			