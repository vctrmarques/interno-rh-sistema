--Alteração do tipo dos campos codigo nas tabelas do sistema para string. 
--Davi Queiroz.

BEGIN IF COL_LENGTH('atividade', 'codigo') IS NOT NULL
    ALTER TABLE atividade
    ALTER COLUMN codigo varchar(255) NOT NULL;
END

DROP INDEX banco.uk_banco_codigo;

BEGIN IF COL_LENGTH('banco', 'codigo') IS NOT NULL
    ALTER TABLE banco
    ALTER COLUMN codigo varchar(255) NOT NULL;
    
    CREATE UNIQUE INDEX uk_banco_codigo ON banco (codigo);
END

BEGIN IF COL_LENGTH('categoria_economica', 'codigo') IS NOT NULL
    ALTER TABLE categoria_economica
    ALTER COLUMN codigo varchar(255) NOT NULL;
END

DROP INDEX categoria_profissional.uk_categoria_profissional_codigo

BEGIN IF COL_LENGTH('categoria_profissional', 'codigo') IS NOT NULL
    ALTER TABLE categoria_profissional
    ALTER COLUMN codigo varchar(255) NOT NULL;
    
    CREATE UNIQUE INDEX uk_categoria_profissional_codigo ON categoria_profissional (codigo);
END

BEGIN IF COL_LENGTH('classificacao_agente_nocivo', 'codigo') IS NOT NULL
    ALTER TABLE classificacao_agente_nocivo
    ALTER COLUMN codigo varchar(255) NOT NULL;
END

DROP INDEX cbo.uk_cbo_codigo

BEGIN IF COL_LENGTH('cbo', 'codigo') IS NOT NULL
    ALTER TABLE cbo
    ALTER COLUMN codigo varchar(255) NOT NULL;
    
    CREATE UNIQUE INDEX uk_cbo_codigo ON cbo (codigo);
END

BEGIN IF COL_LENGTH('centro_custo', 'codigo') IS NOT NULL
    ALTER TABLE centro_custo
    ALTER COLUMN codigo varchar(255) NOT NULL;
END

BEGIN IF COL_LENGTH('conta_contabil_simples', 'codigo') IS NOT NULL
    ALTER TABLE conta_contabil_simples
    ALTER COLUMN codigo varchar(255) NOT NULL;
END

BEGIN IF COL_LENGTH('convenio', 'codigo') IS NOT NULL
    ALTER TABLE convenio
    ALTER COLUMN codigo varchar(255) NOT NULL;
END

BEGIN IF COL_LENGTH('equipamento_protecao_individual', 'codigo') IS NOT NULL
    ALTER TABLE equipamento_protecao_individual
    ALTER COLUMN codigo varchar(255) NOT NULL;
END

BEGIN IF COL_LENGTH('equipamento_protecao_coletiva', 'codigo') IS NOT NULL
    ALTER TABLE equipamento_protecao_coletiva
    ALTER COLUMN codigo varchar(255) NOT NULL;
END

BEGIN IF COL_LENGTH('exame', 'codigo') IS NOT NULL
    ALTER TABLE exame
    ALTER COLUMN codigo varchar(255) NOT NULL;
END

BEGIN IF COL_LENGTH('afastamento', 'codigo') IS NOT NULL
    ALTER TABLE afastamento
    ALTER COLUMN codigo varchar(255) NOT NULL;
END

BEGIN IF COL_LENGTH('motivo_desligamento', 'codigo') IS NOT NULL
    ALTER TABLE motivo_desligamento
    ALTER COLUMN codigo varchar(255) NOT NULL;
END

BEGIN IF COL_LENGTH('codigo_pagamento_gps', 'codigo') IS NOT NULL
    ALTER TABLE codigo_pagamento_gps
    ALTER COLUMN codigo varchar(255) NOT NULL;
END

BEGIN IF COL_LENGTH('habilidade', 'codigo') IS NOT NULL
    ALTER TABLE habilidade
    ALTER COLUMN codigo varchar(255) NOT NULL;
END

BEGIN IF COL_LENGTH('sefip', 'codigo') IS NOT NULL
    ALTER TABLE sefip
    ALTER COLUMN codigo varchar(255) NOT NULL;
END

BEGIN IF COL_LENGTH('tipo_folha', 'codigo') IS NOT NULL
    ALTER TABLE tipo_folha
    ALTER COLUMN codigo varchar(255) NOT NULL;
END

BEGIN IF COL_LENGTH('natureza_juridica', 'codigo') IS NOT NULL
    ALTER TABLE natureza_juridica
    ALTER COLUMN codigo varchar(255) NOT NULL;
END

DROP INDEX tipo_processamento.uk_tipo_processamento_codigo

BEGIN IF COL_LENGTH('tipo_processamento', 'codigo') IS NOT NULL
    ALTER TABLE tipo_processamento
    ALTER COLUMN codigo varchar(255) NOT NULL;
    
    CREATE UNIQUE INDEX uk_tipo_processamento_codigo ON tipo_processamento (codigo);
END

BEGIN IF COL_LENGTH('historico_contabil', 'codigo') IS NOT NULL
    ALTER TABLE historico_contabil
    ALTER COLUMN codigo varchar(255) NOT NULL;
END

DROP INDEX verba.uk_verba_codigo

BEGIN IF COL_LENGTH('verba', 'codigo') IS NOT NULL
    ALTER TABLE verba
    ALTER COLUMN codigo varchar(255) NOT NULL;
    
    CREATE UNIQUE INDEX uk_verba_codigo ON verba (codigo);
END
