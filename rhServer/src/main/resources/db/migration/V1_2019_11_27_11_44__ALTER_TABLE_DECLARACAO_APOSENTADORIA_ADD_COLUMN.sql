-- Flávio Silva
-- Alteração da tabela declaracao_aposentadoria para adicionar a coluna excluida.

if col_length('declaracao_aposentadoria', 'excluida') is null
    begin
        alter table declaracao_aposentadoria add excluida BIT;
    end