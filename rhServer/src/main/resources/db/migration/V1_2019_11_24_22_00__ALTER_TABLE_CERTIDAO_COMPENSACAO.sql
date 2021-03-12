-- Flávio Silva
-- Alteração da tabela certidao_compensacao para retirar o atributo classificacao.

    
if col_length('certidao_compensacao', 'classificacao') is not null
    begin
        alter table certidao_compensacao drop column classificacao;
    end