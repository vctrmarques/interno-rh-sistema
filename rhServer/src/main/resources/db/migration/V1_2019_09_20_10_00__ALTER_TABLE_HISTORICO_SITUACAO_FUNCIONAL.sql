-- Eduardo Costa
-- Adicionando campo situacao funcional vide tarefa git : http://git.gittech.info/rh/interno-rh-sistema/issues/31
ALTER TABLE historico_situacao_funcional
add  situacao_funcional_id BIGINT;

ALTER TABLE historico_situacao_funcional
ADD CONSTRAINT situaca_funcional_fkey
FOREIGN KEY (situacao_funcional_id) REFERENCES situacao_funcional(id); 