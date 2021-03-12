-- Flávio Silva
-- Alteração da tabela declaracao_aposentadoria para adicionar 2 atributos e excluir 1 atributo.
   

if col_length('declaracao_aposentadoria', 'rascunho') is not null
    begin
        alter table declaracao_aposentadoria drop column rascunho;
    end
    
if col_length('declaracao_aposentadoria', 'arquivada') is null
    begin
        alter table declaracao_aposentadoria add arquivada BIT NOT NULL DEFAULT(0);
    end
    
if col_length('declaracao_aposentadoria', 'tipo_declaracao') is null
    begin
        alter table declaracao_aposentadoria add tipo_declaracao varchar(255) NOT NULL DEFAULT('DECLARACAO');
    end
			