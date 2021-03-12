-- Flávio Silva
-- Alteração da tabela declaracao_aposentadoria para adicionar 3 atributos e excluir uma not null.
    
if col_length('declaracao_aposentadoria', 'numero_retificacao') is null
    begin
        alter table declaracao_aposentadoria add numero_retificacao bigint;
    end
    
if col_length('declaracao_aposentadoria', 'declaracao_aposentadoria_id') is null
    begin
	    alter table declaracao_aposentadoria add declaracao_aposentadoria_id bigint null;
	    alter table declaracao_aposentadoria add CONSTRAINT fk_declaracao_aposentadoria_declaracao_aposentadoria_id FOREIGN KEY (declaracao_aposentadoria_id) REFERENCES declaracao_aposentadoria(id);
    end
    
if col_length('declaracao_aposentadoria', 'rascunho') is null
    begin
        alter table declaracao_aposentadoria add rascunho BIT;
    end
        
if col_length('declaracao_aposentadoria', 'funcionario_id') is not null
    begin
        alter table declaracao_aposentadoria alter column funcionario_id bigint null;
    end
    
    
