ALTER TABLE transferencia_funcionario ADD empresa_anterior_id BIGINT NULL;
ALTER TABLE transferencia_funcionario ADD lotacao_anterior_id BIGINT NULL;

ALTER TABLE transferencia_funcionario
ADD CONSTRAINT transferencia_funcionario_empresa_anterior_id_fkey
FOREIGN KEY (empresa_anterior_id) REFERENCES empresa_filial(id); 

ALTER TABLE transferencia_funcionario
ADD CONSTRAINT transferencia_funcionario_lotacao_anterior_id_fkey
FOREIGN KEY (lotacao_anterior_id) REFERENCES lotacao(id); 