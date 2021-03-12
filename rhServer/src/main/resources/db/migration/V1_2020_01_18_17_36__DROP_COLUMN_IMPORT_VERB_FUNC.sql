-- Flávio Silva
-- Alteração da tabela importador_verbas_funcionario, atributos nome_original, nome_sistema e dt_importacao não são necessários.
   

if col_length('importador_verbas_funcionario', 'nome_original') is not null
    begin
        alter table importador_verbas_funcionario drop column nome_original;
    end
    
if col_length('importador_verbas_funcionario', 'nome_sistema') is not null
    begin
        alter table importador_verbas_funcionario drop column nome_sistema;
    end
			
if col_length('importador_verbas_funcionario', 'dt_importacao') is not null
    begin
        alter table importador_verbas_funcionario drop column dt_importacao;
    end
    
    
    