-- Flávio Silva
-- Alterações na tabela de contracheque


IF COL_LENGTH('folha_pagamento_contracheque', 'pensionista_id') IS NULL
	BEGIN
		ALTER TABLE folha_pagamento_contracheque ADD pensionista_id BIGINT;
		ALTER TABLE folha_pagamento_contracheque ADD CONSTRAINT fk_folha_pagamento_contracheque_pensionista_id FOREIGN KEY (pensionista_id) REFERENCES pensionista (id);
	END

IF COL_LENGTH('folha_pagamento_contracheque', 'funcionario_id') IS NOT NULL
	BEGIN 
		ALTER TABLE folha_pagamento_contracheque ALTER COLUMN funcionario_id BIGINT NULL;
	END

IF COL_LENGTH('folha_pagamento_contracheque', 'nome_funcionario') IS NOT NULL
    begin
	    exec sp_RENAME 'folha_pagamento_contracheque.nome_funcionario', 'nome' , 'COLUMN';
    end
    
IF COL_LENGTH('folha_pagamento_contracheque', 'matricula_funcionario') IS NOT NULL
    begin
	    exec sp_RENAME 'folha_pagamento_contracheque.matricula_funcionario', 'matricula' , 'COLUMN';
    end
    
IF COL_LENGTH('folha_pagamento_contracheque', 'lotacao_funcionario') IS NOT NULL
    begin
	    exec sp_RENAME 'folha_pagamento_contracheque.lotacao_funcionario', 'lotacao' , 'COLUMN';
    end
    
IF COL_LENGTH('folha_pagamento_contracheque', 'municipio_funcionario') IS NOT NULL
    begin
	    exec sp_RENAME 'folha_pagamento_contracheque.municipio_funcionario', 'municipio' , 'COLUMN';
    end
    
IF COL_LENGTH('folha_pagamento_contracheque', 'data_admissao_funcionario') IS NOT NULL
    begin
	    exec sp_RENAME 'folha_pagamento_contracheque.data_admissao_funcionario', 'data_admissao' , 'COLUMN';
    end
    
IF COL_LENGTH('folha_pagamento_contracheque', 'cargo_efetivo_funcionario') IS NOT NULL
    begin
	    exec sp_RENAME 'folha_pagamento_contracheque.cargo_efetivo_funcionario', 'cargo_efetivo' , 'COLUMN';
    end
    
IF COL_LENGTH('folha_pagamento_contracheque', 'data_nascimento_funcionario') IS NOT NULL
    begin
	    exec sp_RENAME 'folha_pagamento_contracheque.data_nascimento_funcionario', 'data_nascimento' , 'COLUMN';
    end
    
IF COL_LENGTH('folha_pagamento_contracheque', 'nivel_funcionario') IS NOT NULL
    begin
	    exec sp_RENAME 'folha_pagamento_contracheque.nivel_funcionario', 'nivel' , 'COLUMN';
    end
    
IF COL_LENGTH('folha_pagamento_contracheque', 'cargo_funcao_funcionario') IS NOT NULL
    begin
	    exec sp_RENAME 'folha_pagamento_contracheque.cargo_funcao_funcionario', 'cargo_funcao' , 'COLUMN';
    end
    
IF COL_LENGTH('folha_pagamento_contracheque', 'referencia_funcionario') IS NOT NULL
    begin
	    exec sp_RENAME 'folha_pagamento_contracheque.referencia_funcionario', 'referencia' , 'COLUMN';
    end
    
IF COL_LENGTH('folha_pagamento_contracheque', 'vinculo_funcionario') IS NOT NULL
    begin
	    exec sp_RENAME 'folha_pagamento_contracheque.vinculo_funcionario', 'vinculo' , 'COLUMN';
    end
    
IF COL_LENGTH('folha_pagamento_contracheque', 'situacao_funcionario') IS NOT NULL
    begin
	    exec sp_RENAME 'folha_pagamento_contracheque.situacao_funcionario', 'tipo_situacao_funcional' , 'COLUMN';
    end
    
IF COL_LENGTH('folha_pagamento_contracheque', 'identidade_funcionario') IS NOT NULL
    begin
	    exec sp_RENAME 'folha_pagamento_contracheque.identidade_funcionario', 'identidade' , 'COLUMN';
    end
    
IF COL_LENGTH('folha_pagamento_contracheque', 'cpf_funcionario') IS NOT NULL
    begin
	    exec sp_RENAME 'folha_pagamento_contracheque.cpf_funcionario', 'cpf' , 'COLUMN';
    end
    
IF COL_LENGTH('folha_pagamento_contracheque', 'orgao_pagador_funcionario') IS NOT NULL
    begin
	    exec sp_RENAME 'folha_pagamento_contracheque.orgao_pagador_funcionario', 'orgao_pagador' , 'COLUMN';
    end
    
IF COL_LENGTH('folha_pagamento_contracheque', 'dep_sf_funcionario') IS NOT NULL
    begin
	    exec sp_RENAME 'folha_pagamento_contracheque.dep_sf_funcionario', 'dep_sf' , 'COLUMN';
    end
    
IF COL_LENGTH('folha_pagamento_contracheque', 'dep_ir_funcionario') IS NOT NULL
    begin
	    exec sp_RENAME 'folha_pagamento_contracheque.dep_ir_funcionario', 'dep_ir' , 'COLUMN';
    end
    
IF COL_LENGTH('folha_pagamento_contracheque', 'carga_horaria_funcionario') IS NOT NULL
    begin
	    exec sp_RENAME 'folha_pagamento_contracheque.carga_horaria_funcionario', 'carga_horaria' , 'COLUMN';
    end