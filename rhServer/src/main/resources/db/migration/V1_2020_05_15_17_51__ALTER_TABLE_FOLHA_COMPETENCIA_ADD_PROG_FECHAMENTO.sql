-- Marconi Motta
-- Adicionando o campo Programação de fechamento de folha para salvar a data que deseja programar o fechamento.

IF COL_LENGTH('folha_competencia', 'programacao_fechamento') is null
begin
    alter table folha_competencia add programacao_fechamento date null;
end