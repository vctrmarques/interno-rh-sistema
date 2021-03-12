package com.rhlinkcon.model;

public enum PapelNomeEnum {

	// OBS: QUALQUER PAPEL TEM QUE INICIAR COM O PREFIXO 'ROLE'.
	// PAPEL ADMIN. ESSE É O PAPEL MAIOR E NÃO ESTÁ VINCULADO A NENHUM MENU.
	ROLE_ADMIN("Administrador do Sistema"),

	// PAPEIS DO MENU 'Usuários'
	ROLE_USUARIOS_CADASTRAR("Cadastrar Usuários"), ROLE_USUARIOS_ATUALIZAR("Atualizar Usuários"),
	ROLE_USUARIOS_EXCLUIR("Excluir Usuários"), ROLE_USUARIOS_VISUALIZAR("Visualizar Usuários"),

	// PAPEIS DO MENU 'Área de Formação'
	ROLE_AREA_DE_FORMACAO_CADASTRAR("Cadastrar Área de Formação"),
	ROLE_AREA_DE_FORMACAO_ATUALIZAR("Atualizar Área de Formação"),
	ROLE_AREA_DE_FORMACAO_EXCLUIR("Excluir Área de Formação"),
	ROLE_AREA_DE_FORMACAO_VISUALIZAR("Visualizar Área de Formação"),

	// PAPEIS DO MENU 'Bancos'
	ROLE_BANCOS_CADASTRAR("Cadastrar Bancos"), ROLE_BANCOS_ATUALIZAR("Atualizar Bancos"),
	ROLE_BANCOS_EXCLUIR("Excluir Bancos"), ROLE_BANCOS_VISUALIZAR("Visualizar Bancos"),

	// PAPEIS DO MENU 'Atividade'
	ROLE_ATIVIDADE_CADASTRAR("Cadastrar Atividade"), ROLE_ATIVIDADE_ATUALIZAR("Atualizar Atividade"),
	ROLE_ATIVIDADE_EXCLUIR("Excluir Atividade"), ROLE_ATIVIDADE_VISUALIZAR("Visualizar Atividade"),

	// PAPEIS DO MENU 'Categorias Profissionais'
	ROLE_CATEGORIAS_PROFISSIONAIS_CADASTRAR("Cadastrar Categorias Profissionais"),
	ROLE_CATEGORIAS_PROFISSIONAIS_ATUALIZAR("Atualizar Categorias Profissionais"),
	ROLE_CATEGORIAS_PROFISSIONAIS_EXCLUIR("Excluir Categorias Profissionais"),
	ROLE_CATEGORIAS_PROFISSIONAIS_VISUALIZAR("Visualizar Categorias Profissionais"),

	// PAPEIS DO MENU 'Categorias de Doenças'
	ROLE_CATEGORIAS_DE_DOENCAS_CADASTRAR("Cadastrar Categorias de Doenças"),
	ROLE_CATEGORIAS_DE_DOENCAS_ATUALIZAR("Atualizar Categorias de Doenças"),
	ROLE_CATEGORIAS_DE_DOENCAS_EXCLUIR("Excluir Categorias de Doenças"),
	ROLE_CATEGORIAS_DE_DOENCAS_VISUALIZAR("Visualizar Categorias de Doenças"),

	// PAPEIS DO MENU 'Categoria Econômica'
	ROLE_CATEGORIA_ECONOMICA_CADASTRAR("Cadastrar Categoria Econômica"),
	ROLE_CATEGORIA_ECONOMICA_ATUALIZAR("Atualizar Categoria Econômica"),
	ROLE_CATEGORIA_ECONOMICA_EXCLUIR("Excluir Categoria Econômica"),
	ROLE_CATEGORIA_ECONOMICA_VISUALIZAR("Visualizar Categoria Econômica"),

	// PAPEIS DO MENU 'CBOs'
	ROLE_CBOS_CADASTRAR("Cadastrar CBOs"), ROLE_CBOS_ATUALIZAR("Atualizar CBOs"), ROLE_CBOS_EXCLUIR("Excluir CBOs"),
	ROLE_CBOS_VISUALIZAR("Visualizar CBOs"),

	// PAPEIS DO MENU 'Class. de Agentes Nocivos'
	ROLE_CLASS_DE_AGENTES_NOCIVOS_CADASTRAR("Cadastrar Class. de Agentes Nocivos"),
	ROLE_CLASS_DE_AGENTES_NOCIVOS_ATUALIZAR("Atualizar Class. de Agentes Nocivos"),
	ROLE_CLASS_DE_AGENTES_NOCIVOS_EXCLUIR("Excluir Class. de Agentes Nocivos"),
	ROLE_CLASS_DE_AGENTES_NOCIVOS_VISUALIZAR("Visualizar Class. de Agentes Nocivos"),

	// PAPEIS DO MENU 'Subcategorias de Doenças'
	ROLE_SUBCATEGORIAS_DE_DOENCAS_CADASTRAR("Cadastrar Subcategorias de Doenças"),
	ROLE_SUBCATEGORIAS_DE_DOENCAS_ATUALIZAR("Atualizar Subcategorias de Doenças"),
	ROLE_SUBCATEGORIAS_DE_DOENCAS_EXCLUIR("Excluir Subcategorias de Doenças"),
	ROLE_SUBCATEGORIAS_DE_DOENCAS_VISUALIZAR("Visualizar Subcategorias de Doenças"),

	// PAPEIS DO MENU 'Centro de Custo'
	ROLE_CENTRO_DE_CUSTO_CADASTRAR("Cadastrar Centro de Custo"),
	ROLE_CENTRO_DE_CUSTO_ATUALIZAR("Atualizar Centro de Custo"),
	ROLE_CENTRO_DE_CUSTO_EXCLUIR("Excluir Centro de Custo"),
	ROLE_CENTRO_DE_CUSTO_VISUALIZAR("Visualizar Centro de Custo"),

	// PAPEIS DO MENU 'Classificação de Doenças'
	ROLE_CLASSIFICACAO_DE_DOENCAS_CADASTRAR("Cadastrar Classificação de Doenças"),
	ROLE_CLASSIFICACAO_DE_DOENCAS_ATUALIZAR("Atualizar Classificação de Doenças"),
	ROLE_CLASSIFICACAO_DE_DOENCAS_EXCLUIR("Excluir Classificação de Doenças"),
	ROLE_CLASSIFICACAO_DE_DOENCAS_VISUALIZAR("Visualizar Classificação de Doenças"),

	// PAPEIS DO MENU 'CNAE'
	ROLE_CNAE_CADASTRAR("Cadastrar CNAE"), ROLE_CNAE_ATUALIZAR("Atualizar CNAE"), ROLE_CNAE_EXCLUIR("Excluir CNAE"),
	ROLE_CNAE_VISUALIZAR("Visualizar CNAE"),

	// PAPEIS DO MENU 'Contas Contábeis Simples'
	ROLE_CONTAS_CONTABEIS_SIMPLES_CADASTRAR("Cadastrar Contas Contábeis Simples"),
	ROLE_CONTAS_CONTABEIS_SIMPLES_ATUALIZAR("Atualizar Contas Contábeis Simples"),
	ROLE_CONTAS_CONTABEIS_SIMPLES_EXCLUIR("Excluir Contas Contábeis Simples"),
	ROLE_CONTAS_CONTABEIS_SIMPLES_VISUALIZAR("Visualizar Contas Contábeis Simples"),

	// PAPEIS DO MENU 'Convênios'
	ROLE_CONVENIOS_CADASTRAR("Cadastrar Convênios"), ROLE_CONVENIOS_ATUALIZAR("Atualizar Convênios"),
	ROLE_CONVENIOS_EXCLUIR("Excluir Convênios"), ROLE_CONVENIOS_VISUALIZAR("Visualizar Convênios"),

	// PAPEIS DO MENU 'Regras de Aposentadoria'
	ROLE_REGRAS_DE_APOSENTADORIA_CADASTRAR("Cadastrar Regras de Aposentadoria"),
	ROLE_REGRAS_DE_APOSENTADORIA_ATUALIZAR("Atualizar Regras de Aposentadoria"),
	ROLE_REGRAS_DE_APOSENTADORIA_EXCLUIR("Excluir Regras de Aposentadoria"),
	ROLE_REGRAS_DE_APOSENTADORIA_VISUALIZAR("Visualizar Regras de Aposentadoria"),

	// PAPEIS DO MENU 'Consignado'
	ROLE_CONSIGNADO_CADASTRAR("Cadastrar Consignado"), ROLE_CONSIGNADO_ATUALIZAR("Atualizar Consignado"),
	ROLE_CONSIGNADO_EXCLUIR("Excluir Consignado"), ROLE_CONSIGNADO_VISUALIZAR("Visualizar Consignado"),

	// PAPEIS DO MENU 'EPI'
	ROLE_EPI_CADASTRAR("Cadastrar EPI"), ROLE_EPI_ATUALIZAR("Atualizar EPI"), ROLE_EPI_EXCLUIR("Excluir EPI"),
	ROLE_EPI_VISUALIZAR("Visualizar EPI"),

	// PAPEIS DO MENU 'EPC'
	ROLE_EPC_CADASTRAR("Cadastrar EPC"), ROLE_EPC_ATUALIZAR("Atualizar EPC"), ROLE_EPC_EXCLUIR("Excluir EPC"),
	ROLE_EPC_VISUALIZAR("Visualizar EPC"),

	// PAPEIS DO MENU 'Evento'
	ROLE_EVENTO_CADASTRAR("Cadastrar Evento"), ROLE_EVENTO_ATUALIZAR("Atualizar Evento"),
	ROLE_EVENTO_EXCLUIR("Excluir Evento"), ROLE_EVENTO_VISUALIZAR("Visualizar Evento"),

	// PAPEIS DO MENU 'Etapas'
	ROLE_ETAPAS_CADASTRAR("Cadastrar Etapas"), ROLE_ETAPAS_ATUALIZAR("Atualizar Etapas"),
	ROLE_ETAPAS_EXCLUIR("Excluir Etapas"), ROLE_ETAPAS_VISUALIZAR("Visualizar Etapas"),

	// PAPEIS DO MENU 'Exames'
	ROLE_EXAMES_CADASTRAR("Cadastrar Exames"), ROLE_EXAMES_ATUALIZAR("Atualizar Exames"),
	ROLE_EXAMES_EXCLUIR("Excluir Exames"), ROLE_EXAMES_VISUALIZAR("Visualizar Exames"),

	// PAPEIS DO MENU 'Graus Acadêmicos'
	ROLE_GRAUS_ACADEMICOS_CADASTRAR("Cadastrar Graus Acadêmicos"),
	ROLE_GRAUS_ACADEMICOS_ATUALIZAR("Atualizar Graus Acadêmicos"),
	ROLE_GRAUS_ACADEMICOS_EXCLUIR("Excluir Graus Acadêmicos"),
	ROLE_GRAUS_ACADEMICOS_VISUALIZAR("Visualizar Graus Acadêmicos"),

	// PAPEIS DO MENU 'Faixas Salariais'
	ROLE_FAIXAS_SALARIAIS_CADASTRAR("Cadastrar Faixas Salariais"),
	ROLE_FAIXAS_SALARIAIS_ATUALIZAR("Atualizar Faixas Salariais"),
	ROLE_FAIXAS_SALARIAIS_EXCLUIR("Excluir Faixas Salariais"),
	ROLE_FAIXAS_SALARIAIS_VISUALIZAR("Visualizar Faixas Salariais"),

	// PAPEIS DO MENU 'Cursos'
	ROLE_CURSOS_CADASTRAR("Cadastrar Cursos"), ROLE_CURSOS_ATUALIZAR("Atualizar Cursos"),
	ROLE_CURSOS_EXCLUIR("Excluir Cursos"), ROLE_CURSOS_VISUALIZAR("Visualizar Cursos"),

	// PAPEIS DO MENU 'Situação Funcional'
	ROLE_SITUACAO_FUNCIONAL_CADASTRAR("Cadastrar Situação Funcional"),
	ROLE_SITUACAO_FUNCIONAL_ATUALIZAR("Atualizar Situação Funcional"),
	ROLE_SITUACAO_FUNCIONAL_EXCLUIR("Excluir Situação Funcional"),
	ROLE_SITUACAO_FUNCIONAL_VISUALIZAR("Visualizar Situação Funcional"),

	// PAPEIS DO MENU 'CRM / CREA'
	ROLE_CRM_CREA_CADASTRAR("Cadastrar CRM / CREA"), ROLE_CRM_CREA_ATUALIZAR("Atualizar CRM / CREA"),
	ROLE_CRM_CREA_EXCLUIR("Excluir CRM / CREA"), ROLE_CRM_CREA_VISUALIZAR("Visualizar CRM / CREA"),

	// PAPEIS DO MENU 'Causas de Afastamento'
	ROLE_CAUSAS_DE_AFASTAMENTO_CADASTRAR("Cadastrar Causas de Afastamento"),
	ROLE_CAUSAS_DE_AFASTAMENTO_ATUALIZAR("Atualizar Causas de Afastamento"),
	ROLE_CAUSAS_DE_AFASTAMENTO_EXCLUIR("Excluir Causas de Afastamento"),
	ROLE_CAUSAS_DE_AFASTAMENTO_VISUALIZAR("Visualizar Causas de Afastamento"),

	// PAPEIS DO MENU 'Motivo Afastamento'
	ROLE_MOTIVO_AFASTAMENTO_CADASTRAR("Cadastrar Motivo Afastamento"),
	ROLE_MOTIVO_AFASTAMENTO_ATUALIZAR("Atualizar Motivo Afastamento"),
	ROLE_MOTIVO_AFASTAMENTO_EXCLUIR("Excluir Motivo Afastamento"),
	ROLE_MOTIVO_AFASTAMENTO_VISUALIZAR("Visualizar Motivo Afastamento"),

	// PAPEIS DO MENU 'Motivos do Desligamento'
	ROLE_MOTIVOS_DO_DESLIGAMENTO_CADASTRAR("Cadastrar Motivos do Desligamento"),
	ROLE_MOTIVOS_DO_DESLIGAMENTO_ATUALIZAR("Atualizar Motivos do Desligamento"),
	ROLE_MOTIVOS_DO_DESLIGAMENTO_EXCLUIR("Excluir Motivos do Desligamento"),
	ROLE_MOTIVOS_DO_DESLIGAMENTO_VISUALIZAR("Visualizar Motivos do Desligamento"),

	// PAPEIS DO MENU 'Códigos do Recolhimento'
	ROLE_CODIGOS_DO_RECOLHIMENTO_CADASTRAR("Cadastrar Códigos do Recolhimento"),
	ROLE_CODIGOS_DO_RECOLHIMENTO_ATUALIZAR("Atualizar Códigos do Recolhimento"),
	ROLE_CODIGOS_DO_RECOLHIMENTO_EXCLUIR("Excluir Códigos do Recolhimento"),
	ROLE_CODIGOS_DO_RECOLHIMENTO_VISUALIZAR("Visualizar Códigos do Recolhimento"),

	// PAPEIS DO MENU 'Códigos do Pagamento GPS'
	ROLE_CODIGOS_DO_PAGAMENTO_GPS_CADASTRAR("Cadastrar Códigos do Pagamento GPS"),
	ROLE_CODIGOS_DO_PAGAMENTO_GPS_ATUALIZAR("Atualizar Códigos do Pagamento GPS"),
	ROLE_CODIGOS_DO_PAGAMENTO_GPS_EXCLUIR("Excluir Códigos do Pagamento GPS"),
	ROLE_CODIGOS_DO_PAGAMENTO_GPS_VISUALIZAR("Visualizar Códigos do Pagamento GPS"),

	// PAPEIS DO MENU 'Motivos'
	ROLE_MOTIVOS_CADASTRAR("Cadastrar Motivos"), ROLE_MOTIVOS_ATUALIZAR("Atualizar Motivos"),
	ROLE_MOTIVOS_EXCLUIR("Excluir Motivos"), ROLE_MOTIVOS_VISUALIZAR("Visualizar Motivos"),

	// PAPEIS DO MENU 'Lotação'
	ROLE_LOTACAO_CADASTRAR("Cadastrar Lotação"), ROLE_LOTACAO_ATUALIZAR("Atualizar Lotação"),
	ROLE_LOTACAO_EXCLUIR("Excluir Lotação"), ROLE_LOTACAO_VISUALIZAR("Visualizar Lotação"),

	// PAPEIS DO MENU 'Habilidades'
	ROLE_HABILIDADES_CADASTRAR("Cadastrar Habilidades"), ROLE_HABILIDADES_ATUALIZAR("Atualizar Habilidades"),
	ROLE_HABILIDADES_EXCLUIR("Excluir Habilidades"), ROLE_HABILIDADES_VISUALIZAR("Visualizar Habilidades"),

	// PAPEIS DO MENU 'País'
	ROLE_PAIS_CADASTRAR("Cadastrar País"), ROLE_PAIS_ATUALIZAR("Atualizar País"), ROLE_PAIS_EXCLUIR("Excluir País"),
	ROLE_PAIS_VISUALIZAR("Visualizar País"),

	// PAPEIS DO MENU 'Município'
	ROLE_MUNICIPIO_CADASTRAR("Cadastrar Município"), ROLE_MUNICIPIO_ATUALIZAR("Atualizar Município"),
	ROLE_MUNICIPIO_EXCLUIR("Excluir Município"), ROLE_MUNICIPIO_VISUALIZAR("Visualizar Município"),

	// PAPEIS DO MENU 'Tomador de Serviço'
	ROLE_TOMADOR_DE_SERVICO_CADASTRAR("Cadastrar Tomador de Serviço"),
	ROLE_TOMADOR_DE_SERVICO_ATUALIZAR("Atualizar Tomador de Serviço"),
	ROLE_TOMADOR_DE_SERVICO_EXCLUIR("Excluir Tomador de Serviço"),
	ROLE_TOMADOR_DE_SERVICO_VISUALIZAR("Visualizar Tomador de Serviço"),

	// PAPEIS DO MENU 'Unidades Federativas'
	ROLE_UNIDADES_FEDERATIVAS_CADASTRAR("Cadastrar Unidades Federativas"),
	ROLE_UNIDADES_FEDERATIVAS_ATUALIZAR("Atualizar Unidades Federativas"),
	ROLE_UNIDADES_FEDERATIVAS_EXCLUIR("Excluir Unidades Federativas"),
	ROLE_UNIDADES_FEDERATIVAS_VISUALIZAR("Visualizar Unidades Federativas"),

	// PAPEIS DO MENU 'SEFIP'
	ROLE_SEFIP_CADASTRAR("Cadastrar SEFIP"), ROLE_SEFIP_ATUALIZAR("Atualizar SEFIP"),
	ROLE_SEFIP_EXCLUIR("Excluir SEFIP"), ROLE_SEFIP_VISUALIZAR("Visualizar SEFIP"),

	// PAPEIS DO MENU 'eSocial'
	ROLE_ESOCIAL_CADASTRAR("Cadastrar eSocial"), ROLE_ESOCIAL_ATUALIZAR("Atualizar eSocial"),
	ROLE_ESOCIAL_EXCLUIR("Excluir eSocial"), ROLE_ESOCIAL_VISUALIZAR("Visualizar eSocial"),

	// PAPEIS DO MENU 'Tipos de Férias'
	ROLE_TIPOS_DE_FERIAS_CADASTRAR("Cadastrar Tipos de Férias"),
	ROLE_TIPOS_DE_FERIAS_ATUALIZAR("Atualizar Tipos de Férias"),
	ROLE_TIPOS_DE_FERIAS_EXCLUIR("Excluir Tipos de Férias"),
	ROLE_TIPOS_DE_FERIAS_VISUALIZAR("Visualizar Tipos de Férias"),

	// PAPEIS DO MENU 'Tipos de Folhas'
	ROLE_TIPOS_DE_FOLHAS_CADASTRAR("Cadastrar Tipos de Folhas"),
	ROLE_TIPOS_DE_FOLHAS_ATUALIZAR("Atualizar Tipos de Folhas"),
	ROLE_TIPOS_DE_FOLHAS_EXCLUIR("Excluir Tipos de Folhas"),
	ROLE_TIPOS_DE_FOLHAS_VISUALIZAR("Visualizar Tipos de Folhas"),

	// PAPEIS DO MENU 'Classificações dos Atos'
	ROLE_CLASSIFICACOES_DOS_ATOS_CADASTRAR("Cadastrar Classificações dos Atos"),
	ROLE_CLASSIFICACOES_DOS_ATOS_ATUALIZAR("Atualizar Classificações dos Atos"),
	ROLE_CLASSIFICACOES_DOS_ATOS_EXCLUIR("Excluir Classificações dos Atos"),
	ROLE_CLASSIFICACOES_DOS_ATOS_VISUALIZAR("Visualizar Classificações dos Atos"),

	// PAPEIS DO MENU 'Naturezas Jurídicas'
	ROLE_NATUREZAS_JURIDICAS_CADASTRAR("Cadastrar Naturezas Jurídicas"),
	ROLE_NATUREZAS_JURIDICAS_ATUALIZAR("Atualizar Naturezas Jurídicas"),
	ROLE_NATUREZAS_JURIDICAS_EXCLUIR("Excluir Naturezas Jurídicas"),
	ROLE_NATUREZAS_JURIDICAS_VISUALIZAR("Visualizar Naturezas Jurídicas"),

	// PAPEIS DO MENU 'Tipos de Contrato'
	ROLE_TIPOS_DE_CONTRATO_CADASTRAR("Cadastrar Tipos de Contrato"),
	ROLE_TIPOS_DE_CONTRATO_ATUALIZAR("Atualizar Tipos de Contrato"),
	ROLE_TIPOS_DE_CONTRATO_EXCLUIR("Excluir Tipos de Contrato"),
	ROLE_TIPOS_DE_CONTRATO_VISUALIZAR("Visualizar Tipos de Contrato"),

	// PAPEIS DO MENU 'Tipos de Averbações'
	ROLE_TIPOS_DE_AVERBACOES_CADASTRAR("Cadastrar Tipos de Averbações"),
	ROLE_TIPOS_DE_AVERBACOES_ATUALIZAR("Atualizar Tipos de Averbações"),
	ROLE_TIPOS_DE_AVERBACOES_EXCLUIR("Excluir Tipos de Averbações"),
	ROLE_TIPOS_DE_AVERBACOES_VISUALIZAR("Visualizar Tipos de Averbações"),

	// PAPEIS DO MENU 'Modelo de Documento'
	ROLE_MODELO_DE_DOCUMENTO_CADASTRAR("Cadastrar Modelo de Documento"),
	ROLE_MODELO_DE_DOCUMENTO_ATUALIZAR("Atualizar Modelo de Documento"),
	ROLE_MODELO_DE_DOCUMENTO_EXCLUIR("Excluir Modelo de Documento"),
	ROLE_MODELO_DE_DOCUMENTO_VISUALIZAR("Visualizar Modelo de Documento"),

	// PAPEIS DO MENU 'Empresa Filial'
	ROLE_EMPRESA_FILIAL_CADASTRAR("Cadastrar Empresa Filial"),
	ROLE_EMPRESA_FILIAL_ATUALIZAR("Atualizar Empresa Filial"), ROLE_EMPRESA_FILIAL_EXCLUIR("Excluir Empresa Filial"),
	ROLE_EMPRESA_FILIAL_VISUALIZAR("Visualizar Empresa Filial"),

	// PAPEIS DO MENU 'Sindicatos'
	ROLE_SINDICATOS_CADASTRAR("Cadastrar Sindicatos"), ROLE_SINDICATOS_ATUALIZAR("Atualizar Sindicatos"),
	ROLE_SINDICATOS_EXCLUIR("Excluir Sindicatos"), ROLE_SINDICATOS_VISUALIZAR("Visualizar Sindicatos"),

	// PAPEIS DO MENU 'Verbas'
	ROLE_VERBAS_CADASTRAR("Cadastrar Verbas"), ROLE_VERBAS_ATUALIZAR("Atualizar Verbas"),
	ROLE_VERBAS_EXCLUIR("Excluir Verbas"), ROLE_VERBAS_VISUALIZAR("Visualizar Verbas"),

	// PAPEIS DO MENU 'Tipo de Processamento'
	ROLE_TIPO_DE_PROCESSAMENTO_CADASTRAR("Cadastrar Tipo de Processamento"),
	ROLE_TIPO_DE_PROCESSAMENTO_ATUALIZAR("Atualizar Tipo de Processamento"),
	ROLE_TIPO_DE_PROCESSAMENTO_EXCLUIR("Excluir Tipo de Processamento"),
	ROLE_TIPO_DE_PROCESSAMENTO_VISUALIZAR("Visualizar Tipo de Processamento"),

	// PAPEIS DO MENU 'Vales Transporte'
	ROLE_VALES_TRANSPORTE_CADASTRAR("Cadastrar Vales Transporte"),
	ROLE_VALES_TRANSPORTE_ATUALIZAR("Atualizar Vales Transporte"),
	ROLE_VALES_TRANSPORTE_EXCLUIR("Excluir Vales Transporte"),
	ROLE_VALES_TRANSPORTE_VISUALIZAR("Visualizar Vales Transporte"),

	// PAPEIS DO MENU 'Histórico Contábil'
	ROLE_HISTORICO_CONTABIL_CADASTRAR("Cadastrar Histórico Contábil"),
	ROLE_HISTORICO_CONTABIL_ATUALIZAR("Atualizar Histórico Contábil"),
	ROLE_HISTORICO_CONTABIL_EXCLUIR("Excluir Histórico Contábil"),
	ROLE_HISTORICO_CONTABIL_VISUALIZAR("Visualizar Histórico Contábil"),

	// PAPEIS DO MENU 'Entidades de Exames'
	ROLE_ENTIDADES_DE_EXAMES_CADASTRAR("Cadastrar Entidades de Exames"),
	ROLE_ENTIDADES_DE_EXAMES_ATUALIZAR("Atualizar Entidades de Exames"),
	ROLE_ENTIDADES_DE_EXAMES_EXCLUIR("Excluir Entidades de Exames"),
	ROLE_ENTIDADES_DE_EXAMES_VISUALIZAR("Visualizar Entidades de Exames"),

	// PAPEIS DO MENU 'Turno'
	ROLE_TURNO_CADASTRAR("Cadastrar Turno"), ROLE_TURNO_ATUALIZAR("Atualizar Turno"),
	ROLE_TURNO_EXCLUIR("Excluir Turno"), ROLE_TURNO_VISUALIZAR("Visualizar Turno"),

	// PAPEIS DO MENU 'Responsável Legal'
	ROLE_RESPONSAVEL_LEGAL_CADASTRAR("Cadastrar Responsável Legal"),
	ROLE_RESPONSAVEL_LEGAL_ATUALIZAR("Atualizar Responsável Legal"),
	ROLE_RESPONSAVEL_LEGAL_EXCLUIR("Excluir Responsável Legal"),
	ROLE_RESPONSAVEL_LEGAL_VISUALIZAR("Visualizar Responsável Legal"),

	// PAPEIS DO MENU 'Requisito'
	ROLE_REQUISITO_CADASTRAR("Cadastrar Requisito"), ROLE_REQUISITO_ATUALIZAR("Atualizar Requisito"),
	ROLE_REQUISITO_EXCLUIR("Excluir Requisito"), ROLE_REQUISITO_VISUALIZAR("Visualizar Requisito"),

	// PAPEIS DO MENU 'Natureza da Função'
	ROLE_NATUREZA_DA_FUNCAO_CADASTRAR("Cadastrar Natureza da Função"),
	ROLE_NATUREZA_DA_FUNCAO_ATUALIZAR("Atualizar Natureza da Função"),
	ROLE_NATUREZA_DA_FUNCAO_EXCLUIR("Excluir Natureza da Função"),
	ROLE_NATUREZA_DA_FUNCAO_VISUALIZAR("Visualizar Natureza da Função"),

	// PAPEIS DO MENU 'Vínculos'
	ROLE_VINCULOS_CADASTRAR("Cadastrar Vínculos"), ROLE_VINCULOS_ATUALIZAR("Atualizar Vínculos"),
	ROLE_VINCULOS_EXCLUIR("Excluir Vínculos"), ROLE_VINCULOS_VISUALIZAR("Visualizar Vínculos"),

	// PAPEIS DO MENU 'Referência Salarial'
	ROLE_REFERENCIA_SALARIAL_CADASTRAR("Cadastrar Referência Salarial"),
	ROLE_REFERENCIA_SALARIAL_ATUALIZAR("Atualizar Referência Salarial"),
	ROLE_REFERENCIA_SALARIAL_EXCLUIR("Excluir Referência Salarial"),
	ROLE_REFERENCIA_SALARIAL_VISUALIZAR("Visualizar Referência Salarial"),

	// PAPEIS DO MENU 'Processo de Função'
	ROLE_PROCESSO_DE_FUNCAO_CADASTRAR("Cadastrar Processo de Função"),
	ROLE_PROCESSO_DE_FUNCAO_ATUALIZAR("Atualizar Processo de Função"),
	ROLE_PROCESSO_DE_FUNCAO_EXCLUIR("Excluir Processo de Função"),
	ROLE_PROCESSO_DE_FUNCAO_VISUALIZAR("Visualizar Processo de Função"),

	// PAPEIS DO MENU 'Dia Util'
	ROLE_DIA_UTIL_CADASTRAR("Cadastrar Dia Util"), ROLE_DIA_UTIL_ATUALIZAR("Atualizar Dia Util"),
	ROLE_DIA_UTIL_EXCLUIR("Excluir Dia Util"), ROLE_DIA_UTIL_VISUALIZAR("Visualizar Dia Util"),

	// PAPEIS DO MENU 'Função'
	ROLE_FUNCAO_CADASTRAR("Cadastrar Função"), ROLE_FUNCAO_ATUALIZAR("Atualizar Função"),
	ROLE_FUNCAO_EXCLUIR("Excluir Função"), ROLE_FUNCAO_VISUALIZAR("Visualizar Função"),

	// PAPEIS DO MENU 'Prestadores de Serviço'
	ROLE_PRESTADORES_DE_SERVICO_CADASTRAR("Cadastrar Prestadores de Serviço"),
	ROLE_PRESTADORES_DE_SERVICO_ATUALIZAR("Atualizar Prestadores de Serviço"),
	ROLE_PRESTADORES_DE_SERVICO_EXCLUIR("Excluir Prestadores de Serviço"),
	ROLE_PRESTADORES_DE_SERVICO_VISUALIZAR("Visualizar Prestadores de Serviço"),

	// PAPEIS DO MENU 'Correção Salarial'
	ROLE_CORRECAO_SALARIAL_CADASTRAR("Cadastrar Correção Salarial"),
	ROLE_CORRECAO_SALARIAL_ATUALIZAR("Atualizar Correção Salarial"),
	ROLE_CORRECAO_SALARIAL_EXCLUIR("Excluir Correção Salarial"),
	ROLE_CORRECAO_SALARIAL_VISUALIZAR("Visualizar Correção Salarial"),

	// PAPEIS DO MENU 'Cargos'
	ROLE_CARGOS_CADASTRAR("Cadastrar Cargos"), ROLE_CARGOS_ATUALIZAR("Atualizar Cargos"),
	ROLE_CARGOS_EXCLUIR("Excluir Cargos"), ROLE_CARGOS_VISUALIZAR("Visualizar Cargos"),

	// PAPEIS DO MENU 'Conta Contábil'
	ROLE_CONTA_CONTABIL_CADASTRAR("Cadastrar Conta Contábil"),
	ROLE_CONTA_CONTABIL_ATUALIZAR("Atualizar Conta Contábil"), ROLE_CONTA_CONTABIL_EXCLUIR("Excluir Conta Contábil"),
	ROLE_CONTA_CONTABIL_VISUALIZAR("Visualizar Conta Contábil"),

	// PAPEIS DO MENU 'Funcionário'
	ROLE_FUNCIONARIO_CADASTRAR("Cadastrar Funcionário"), ROLE_FUNCIONARIO_ATUALIZAR("Atualizar Funcionário"),
	ROLE_FUNCIONARIO_EXCLUIR("Excluir Funcionário"), ROLE_FUNCIONARIO_VISUALIZAR("Visualizar Funcionário"),

	// PAPEIS DO MENU 'Dependentes'
	ROLE_DEPENDENTES_CADASTRAR("Cadastrar Dependentes"), ROLE_DEPENDENTES_ATUALIZAR("Atualizar Dependentes"),
	ROLE_DEPENDENTES_EXCLUIR("Excluir Dependentes"), ROLE_DEPENDENTES_VISUALIZAR("Visualizar Dependentes"),

	// PAPEIS DO MENU 'Simu. Nível Salarial'
	ROLE_SIMU_NIVEL_SALARIAL_CADASTRAR("Cadastrar Simu. Nível Salarial"),
	ROLE_SIMU_NIVEL_SALARIAL_ATUALIZAR("Atualizar Simu. Nível Salarial"),
	ROLE_SIMU_NIVEL_SALARIAL_EXCLUIR("Excluir Simu. Nível Salarial"),
	ROLE_SIMU_NIVEL_SALARIAL_VISUALIZAR("Visualizar Simu. Nível Salarial"),

	// PAPEIS DO MENU 'Definição de Orgânico'
	ROLE_DEFINICAO_DE_ORGANICO_CADASTRAR("Cadastrar Definição de Orgânico"),
	ROLE_DEFINICAO_DE_ORGANICO_ATUALIZAR("Atualizar Definição de Orgânico"),
	ROLE_DEFINICAO_DE_ORGANICO_EXCLUIR("Excluir Definição de Orgânico"),
	ROLE_DEFINICAO_DE_ORGANICO_VISUALIZAR("Visualizar Definição de Orgânico"),

	// PAPEIS DO MENU 'Contribuição Sindical'
	ROLE_CONTRIBUICAO_SINDICAL_CADASTRAR("Cadastrar Contribuição Sindical"),
	ROLE_CONTRIBUICAO_SINDICAL_ATUALIZAR("Atualizar Contribuição Sindical"),
	ROLE_CONTRIBUICAO_SINDICAL_EXCLUIR("Excluir Contribuição Sindical"),
	ROLE_CONTRIBUICAO_SINDICAL_VISUALIZAR("Visualizar Contribuição Sindical"),

	// PAPEIS DO MENU 'Dados Cadastrais'
	ROLE_DADOS_CADASTRAIS_CADASTRAR("Cadastrar Dados Cadastrais"),
	ROLE_DADOS_CADASTRAIS_ATUALIZAR("Atualizar Dados Cadastrais"),
	ROLE_DADOS_CADASTRAIS_EXCLUIR("Excluir Dados Cadastrais"),
	ROLE_DADOS_CADASTRAIS_VISUALIZAR("Visualizar Dados Cadastrais"),

	// PAPEIS DO MENU 'Hist. Nível Salarial'
	ROLE_HIST_NIVEL_SALARIAL_CADASTRAR("Cadastrar Hist. Nível Salarial"),
	ROLE_HIST_NIVEL_SALARIAL_ATUALIZAR("Atualizar Hist. Nível Salarial"),
	ROLE_HIST_NIVEL_SALARIAL_EXCLUIR("Excluir Hist. Nível Salarial"),
	ROLE_HIST_NIVEL_SALARIAL_VISUALIZAR("Visualizar Hist. Nível Salarial"),

	// PAPEIS DO MENU 'Licenca Médica'
	ROLE_LICENCA_MEDICA_CADASTRAR("Cadastrar Licenca Médica"),
	ROLE_LICENCA_MEDICA_ATUALIZAR("Atualizar Licenca Médica"), ROLE_LICENCA_MEDICA_EXCLUIR("Excluir Licenca Médica"),
	ROLE_LICENCA_MEDICA_VISUALIZAR("Visualizar Licenca Médica"),

	// PAPEIS DO MENU 'Dossiê do Servidor'
	ROLE_DOSSIE_DO_SERVIDOR_CADASTRAR("Cadastrar Dossiê do Servidor"),
	ROLE_DOSSIE_DO_SERVIDOR_ATUALIZAR("Atualizar Dossiê do Servidor"),
	ROLE_DOSSIE_DO_SERVIDOR_EXCLUIR("Excluir Dossiê do Servidor"),
	ROLE_DOSSIE_DO_SERVIDOR_VISUALIZAR("Visualizar Dossiê do Servidor"),

	// PAPEIS DO MENU 'Transferência Funcionário'
	ROLE_TRANSFERENCIA_FUNCIONARIO_CADASTRAR("Cadastrar Transferência Funcionário"),
	ROLE_TRANSFERENCIA_FUNCIONARIO_ATUALIZAR("Atualizar Transferência Funcionário"),
	ROLE_TRANSFERENCIA_FUNCIONARIO_EXCLUIR("Excluir Transferência Funcionário"),
	ROLE_TRANSFERENCIA_FUNCIONARIO_VISUALIZAR("Visualizar Transferência Funcionário"),

	// PAPEIS DO MENU 'Processo'
	ROLE_PROCESSO_CADASTRAR("Cadastrar Processo"), ROLE_PROCESSO_ATUALIZAR("Atualizar Processo"),
	ROLE_PROCESSO_EXCLUIR("Excluir Processo"), ROLE_PROCESSO_VISUALIZAR("Visualizar Processo"),

	// PAPEIS DO MENU 'Experiência Profissional'
	ROLE_EXPERIENCIA_PROFISSIONAL_CADASTRAR("Cadastrar Experiência Profissional"),
	ROLE_EXPERIENCIA_PROFISSIONAL_ATUALIZAR("Atualizar Experiência Profissional"),
	ROLE_EXPERIENCIA_PROFISSIONAL_EXCLUIR("Excluir Experiência Profissional"),
	ROLE_EXPERIENCIA_PROFISSIONAL_VISUALIZAR("Visualizar Experiência Profissional"),

	// PAPEIS DO MENU 'Acidente de Trabalho'
	ROLE_ACIDENTE_DE_TRABALHO_CADASTRAR("Cadastrar Acidente de Trabalho"),
	ROLE_ACIDENTE_DE_TRABALHO_ATUALIZAR("Atualizar Acidente de Trabalho"),
	ROLE_ACIDENTE_DE_TRABALHO_EXCLUIR("Excluir Acidente de Trabalho"),
	ROLE_ACIDENTE_DE_TRABALHO_VISUALIZAR("Visualizar Acidente de Trabalho"),

	// PAPEIS DO MENU 'Tempo de Serviço'
	ROLE_TEMPO_DE_SERVICO_CADASTRAR("Cadastrar Tempo de Serviço"),
	ROLE_TEMPO_DE_SERVICO_ATUALIZAR("Atualizar Tempo de Serviço"),
	ROLE_TEMPO_DE_SERVICO_EXCLUIR("Excluir Tempo de Serviço"),
	ROLE_TEMPO_DE_SERVICO_VISUALIZAR("Visualizar Tempo de Serviço"),

	// PAPEIS DO MENU 'Licença Prêmio'
	ROLE_LICENCA_PREMIO_CADASTRAR("Cadastrar Licença Prêmio"),
	ROLE_LICENCA_PREMIO_ATUALIZAR("Atualizar Licença Prêmio"), ROLE_LICENCA_PREMIO_EXCLUIR("Excluir Licença Prêmio"),
	ROLE_LICENCA_PREMIO_VISUALIZAR("Visualizar Licença Prêmio"),

	// PAPEIS DO MENU 'Pensão Alimentícia'
	ROLE_PENSAO_ALIMENTICIA_CADASTRAR("Cadastrar Pensão Alimentícia"),
	ROLE_PENSAO_ALIMENTICIA_ATUALIZAR("Atualizar Pensão Alimentícia"),
	ROLE_PENSAO_ALIMENTICIA_EXCLUIR("Excluir Pensão Alimentícia"),
	ROLE_PENSAO_ALIMENTICIA_VISUALIZAR("Visualizar Pensão Alimentícia"),

	// PAPEIS DO MENU 'Alíquotas'
	ROLE_ALIQUOTAS_CADASTRAR("Cadastrar Alíquotas"), ROLE_ALIQUOTAS_ATUALIZAR("Atualizar Alíquotas"),
	ROLE_ALIQUOTAS_EXCLUIR("Excluir Alíquotas"), ROLE_ALIQUOTAS_VISUALIZAR("Visualizar Alíquotas"),

	// PAPEIS DO MENU 'Histórico Situação Funcional'
	ROLE_HISTORICO_SITUACAO_FUNCIONAL_CADASTRAR("Cadastrar Histórico Situação Funcional"),
	ROLE_HISTORICO_SITUACAO_FUNCIONAL_ATUALIZAR("Atualizar Histórico Situação Funcional"),
	ROLE_HISTORICO_SITUACAO_FUNCIONAL_EXCLUIR("Excluir Histórico Situação Funcional"),
	ROLE_HISTORICO_SITUACAO_FUNCIONAL_VISUALIZAR("Visualizar Histórico Situação Funcional"),

	// PAPEIS DO MENU 'Program. de Férias'
	ROLE_PROGRAM_DE_FERIAS_CADASTRAR("Cadastrar Program. de Férias"),
	ROLE_PROGRAM_DE_FERIAS_ATUALIZAR("Atualizar Program. de Férias"),
	ROLE_PROGRAM_DE_FERIAS_EXCLUIR("Excluir Program. de Férias"),
	ROLE_PROGRAM_DE_FERIAS_VISUALIZAR("Visualizar Program. de Férias"),

	// PAPEIS DO MENU 'Reg. de Frequêcia'
	ROLE_REG_DE_FREQUECIA_CADASTRAR("Cadastrar Reg. de Frequêcia"),
	ROLE_REG_DE_FREQUECIA_ATUALIZAR("Atualizar Reg. de Frequêcia"),
	ROLE_REG_DE_FREQUECIA_EXCLUIR("Excluir Reg. de Frequêcia"),
	ROLE_REG_DE_FREQUECIA_VISUALIZAR("Visualizar Reg. de Frequêcia"),

	// PAPEIS DO MENU 'Verbas do Funcionário'
	ROLE_VERBAS_DO_FUNCIONARIO_CADASTRAR("Cadastrar Verbas do Funcionário"),
	ROLE_VERBAS_DO_FUNCIONARIO_ATUALIZAR("Atualizar Verbas do Funcionário"),
	ROLE_VERBAS_DO_FUNCIONARIO_EXCLUIR("Excluir Verbas do Funcionário"),
	ROLE_VERBAS_DO_FUNCIONARIO_VISUALIZAR("Visualizar Verbas do Funcionário"),

	// PAPEIS DO MENU 'Rescisão contrato'
	ROLE_RESCISAO_CONTRATO_CADASTRAR("Cadastrar Rescisão contrato"),
	ROLE_RESCISAO_CONTRATO_ATUALIZAR("Atualizar Rescisão contrato"),
	ROLE_RESCISAO_CONTRATO_EXCLUIR("Excluir Rescisão contrato"),
	ROLE_RESCISAO_CONTRATO_VISUALIZAR("Visualizar Rescisão contrato"),

	// PAPEIS DO MENU 'Folha de Pgt'
	ROLE_FOLHA_DE_PGT_CADASTRAR("Cadastrar Folha de Pgt"), ROLE_FOLHA_DE_PGT_ATUALIZAR("Atualizar Folha de Pgt"),
	ROLE_FOLHA_DE_PGT_EXCLUIR("Excluir Folha de Pgt"), ROLE_FOLHA_DE_PGT_VISUALIZAR("Visualizar Folha de Pgt"),
	ROLE_FOLHA_DE_PGT_GESTAO("Gerenciar Folha de Pgt"),

	// PAPEIS DO MENU 'Ficha Financeira'
	ROLE_FICHA_FINANCEIRA_CADASTRAR("Cadastrar Ficha Financeira"),
	ROLE_FICHA_FINANCEIRA_ATUALIZAR("Atualizar Ficha Financeira"),
	ROLE_FICHA_FINANCEIRA_EXCLUIR("Excluir Ficha Financeira"),
	ROLE_FICHA_FINANCEIRA_VISUALIZAR("Visualizar Ficha Financeira"),

	// PAPEIS DO MENU 'Adiantamento Salarial'
	ROLE_ADIANTAMENTO_SALARIAL_CADASTRAR("Cadastrar Adiantamento Salarial"),
	ROLE_ADIANTAMENTO_SALARIAL_ATUALIZAR("Atualizar Adiantamento Salarial"),
	ROLE_ADIANTAMENTO_SALARIAL_EXCLUIR("Excluir Adiantamento Salarial"),
	ROLE_ADIANTAMENTO_SALARIAL_VISUALIZAR("Visualizar Adiantamento Salarial"),

	// PAPEIS DO MENU 'Requisição de Pessoal'
	ROLE_REQUISICAO_DE_PESSOAL_CADASTRAR("Cadastrar Requisição de Pessoal"),
	ROLE_REQUISICAO_DE_PESSOAL_ATUALIZAR("Atualizar Requisição de Pessoal"),
	ROLE_REQUISICAO_DE_PESSOAL_EXCLUIR("Excluir Requisição de Pessoal"),
	ROLE_REQUISICAO_DE_PESSOAL_VISUALIZAR("Visualizar Requisição de Pessoal"),

	// PAPEIS DO MENU 'Gestão de Avaliação'
	ROLE_GESTAO_DE_AVALIACAO_CADASTRAR("Cadastrar Gestão de Avaliação"),
	ROLE_GESTAO_DE_AVALIACAO_ATUALIZAR("Atualizar Gestão de Avaliação"),
	ROLE_GESTAO_DE_AVALIACAO_EXCLUIR("Excluir Gestão de Avaliação"),
	ROLE_GESTAO_DE_AVALIACAO_VISUALIZAR("Visualizar Gestão de Avaliação"),

	// PAPEIS DO MENU 'Programação de Adiantamento de 13º Salário'
	ROLE_PROGRAMACAO_DE_ADIANTAMENTO_DE_13º_SALARIO_CADASTRAR("Cadastrar Programação de Adiantamento de 13º Salário"),
	ROLE_PROGRAMACAO_DE_ADIANTAMENTO_DE_13º_SALARIO_ATUALIZAR("Atualizar Programação de Adiantamento de 13º Salário"),
	ROLE_PROGRAMACAO_DE_ADIANTAMENTO_DE_13º_SALARIO_EXCLUIR("Excluir Programação de Adiantamento de 13º Salário"),
	ROLE_PROGRAMACAO_DE_ADIANTAMENTO_DE_13º_SALARIO_VISUALIZAR("Visualizar Programação de Adiantamento de 13º Salário"),

	// PAPEIS DO MENU 'Treinamento Sugerido'
	ROLE_TREINAMENTO_SUGERIDO_CADASTRAR("Cadastrar Treinamento Sugerido"),
	ROLE_TREINAMENTO_SUGERIDO_ATUALIZAR("Atualizar Treinamento Sugerido"),
	ROLE_TREINAMENTO_SUGERIDO_EXCLUIR("Excluir Treinamento Sugerido"),
	ROLE_TREINAMENTO_SUGERIDO_VISUALIZAR("Visualizar Treinamento Sugerido"),

	// PAPEIS DO MENU 'Gestão de Requisições'
	ROLE_GESTAO_DE_REQUISICOES_CADASTRAR("Cadastrar Gestão de Requisições"),
	ROLE_GESTAO_DE_REQUISICOES_ATUALIZAR("Atualizar Gestão de Requisições"),
	ROLE_GESTAO_DE_REQUISICOES_EXCLUIR("Excluir Gestão de Requisições"),
	ROLE_GESTAO_DE_REQUISICOES_VISUALIZAR("Visualizar Gestão de Requisições"),

	// PAPEIS DO MENU 'Relatórios Gerenciais do Recrutamento e Seleção'
	ROLE_RELATORIOS_GERENCIAIS_DO_RECRUTAMENTO_E_SELECAO_CADASTRAR(
			"Cadastrar Relatórios Gerenciais do Recrutamento e Seleção"),
	ROLE_RELATORIOS_GERENCIAIS_DO_RECRUTAMENTO_E_SELECAO_ATUALIZAR(
			"Atualizar Relatórios Gerenciais do Recrutamento e Seleção"),
	ROLE_RELATORIOS_GERENCIAIS_DO_RECRUTAMENTO_E_SELECAO_EXCLUIR(
			"Excluir Relatórios Gerenciais do Recrutamento e Seleção"),
	ROLE_RELATORIOS_GERENCIAIS_DO_RECRUTAMENTO_E_SELECAO_VISUALIZAR(
			"Visualizar Relatórios Gerenciais do Recrutamento e Seleção"),

	// PAPEIS DO MENU 'Respostas e Resultados da Avaliação'
	ROLE_RESPOSTAS_E_RESULTADOS_DA_AVALIACAO_CADASTRAR("Cadastrar Respostas e Resultados da Avaliação"),
	ROLE_RESPOSTAS_E_RESULTADOS_DA_AVALIACAO_ATUALIZAR("Atualizar Respostas e Resultados da Avaliação"),
	ROLE_RESPOSTAS_E_RESULTADOS_DA_AVALIACAO_EXCLUIR("Excluir Respostas e Resultados da Avaliação"),
	ROLE_RESPOSTAS_E_RESULTADOS_DA_AVALIACAO_VISUALIZAR("Visualizar Respostas e Resultados da Avaliação"),

	// PAPEIS DO MENU 'Tipos de Aposentadorias'
	ROLE_TIPOS_DE_APOSENTADORIAS_CADASTRAR("Cadastrar Tipos de Aposentadorias"),
	ROLE_TIPOS_DE_APOSENTADORIAS_ATUALIZAR("Atualizar Tipos de Aposentadorias"),
	ROLE_TIPOS_DE_APOSENTADORIAS_EXCLUIR("Excluir Tipos de Aposentadorias"),
	ROLE_TIPOS_DE_APOSENTADORIAS_VISUALIZAR("Visualizar Tipos de Aposentadorias"),

	// PAPEIS DO MENU 'Transferência'
	ROLE_TRANSFERENCIA_CADASTRAR("Cadastrar Transferência"), ROLE_TRANSFERENCIA_ATUALIZAR("Atualizar Transferência"),
	ROLE_TRANSFERENCIA_EXCLUIR("Excluir Transferência"), ROLE_TRANSFERENCIA_VISUALIZAR("Visualizar Transferência"),

	// PAPEIS DO MENU 'Pensão Previdenciária'
	ROLE_PENSAO_PREVIDENCIARIA_CADASTRAR("Cadastrar Pensão Previdenciária"),
	ROLE_PENSAO_PREVIDENCIARIA_ATUALIZAR("Atualizar Pensão Previdenciária"),
	ROLE_PENSAO_PREVIDENCIARIA_EXCLUIR("Excluir Pensão Previdenciária"),
	ROLE_PENSAO_PREVIDENCIARIA_VISUALIZAR("Visualizar Pensão Previdenciária"),

	// PAPEIS DO MENU 'Declaração para Aposentados'
	ROLE_DECLARACAO_PARA_APOSENTADOS_CADASTRAR("Cadastrar Declaração para Aposentados"),
	ROLE_DECLARACAO_PARA_APOSENTADOS_ATUALIZAR("Atualizar Declaração para Aposentados"),
	ROLE_DECLARACAO_PARA_APOSENTADOS_EXCLUIR("Excluir Declaração para Aposentados"),
	ROLE_DECLARACAO_PARA_APOSENTADOS_VISUALIZAR("Visualizar Declaração para Aposentados"),

	// PAPEIS DO MENU 'CTC - Compensação'
	ROLE_CTC_COMPENSACAO_CADASTRAR("Cadastrar CTC - Compensação"),
	ROLE_CTC_COMPENSACAO_ATUALIZAR("Atualizar CTC - Compensação"),
	ROLE_CTC_COMPENSACAO_EXCLUIR("Excluir CTC - Compensação"),
	ROLE_CTC_COMPENSACAO_VISUALIZAR("Visualizar CTC - Compensação"),

	// PAPEIS DO MENU 'Verbas do Pensionista'
	ROLE_VERBAS_DO_PENSIONISTA_CADASTRAR("Cadastrar Verbas do Pensionista"),
	ROLE_VERBAS_DO_PENSIONISTA_ATUALIZAR("Atualizar Verbas do Pensionista"),
	ROLE_VERBAS_DO_PENSIONISTA_EXCLUIR("Excluir Verbas do Pensionista"),
	ROLE_VERBAS_DO_PENSIONISTA_VISUALIZAR("Visualizar Verbas do Pensionista"),

	// PAPEIS DO MENU 'DTC - Ex-Servidor'
	ROLE_DTC_EX_SERVIDOR_CADASTRAR("Cadastrar DTC - Ex-Servidor"),
	ROLE_DTC_EX_SERVIDOR_ATUALIZAR("Atualizar DTC - Ex-Servidor"),
	ROLE_DTC_EX_SERVIDOR_EXCLUIR("Excluir DTC - Ex-Servidor"),
	ROLE_DTC_EX_SERVIDOR_VISUALIZAR("Visualizar DTC - Ex-Servidor"),

	// PAPEIS DO MENU 'CTC - Ex-Servidor'
	ROLE_CTC_EX_SERVIDOR_CADASTRAR("Cadastrar CTC - Ex-Servidor"),
	ROLE_CTC_EX_SERVIDOR_ATUALIZAR("Atualizar CTC - Ex-Servidor"),
	ROLE_CTC_EX_SERVIDOR_EXCLUIR("Excluir CTC - Ex-Servidor"),
	ROLE_CTC_EX_SERVIDOR_VISUALIZAR("Visualizar CTC - Ex-Servidor"),

	// PAPEIS DO MENU 'Perfis de Acesso'
	ROLE_PERFIS_DE_ACESSO_CADASTRAR("Cadastrar Perfis de Acesso"),
	ROLE_PERFIS_DE_ACESSO_ATUALIZAR("Atualizar Perfis de Acesso"),
	ROLE_PERFIS_DE_ACESSO_EXCLUIR("Excluir Perfis de Acesso"),
	ROLE_PERFIS_DE_ACESSO_VISUALIZAR("Visualizar Perfis de Acesso"),

	// PAPEIS DO MENU 'Legislação'
	ROLE_LEGISLACAO_CADASTRAR("Cadastrar Legislação"), ROLE_LEGISLACAO_ATUALIZAR("Atualizar Legislação"),
	ROLE_LEGISLACAO_EXCLUIR("Excluir Legislação"), ROLE_LEGISLACAO_VISUALIZAR("Visualizar Legislação"),

	ROLE_RELATORIO_FINANCEIRO_CADASTRAR("Cadastrar Relatório Financeiro"),
	ROLE_RELATORIO_FINANCEIRO_ATUALIZAR("Atualizar Relatório Financeiro"),
	ROLE_RELATORIO_FINANCEIRO_VISUALIZAR("Visualizar Relatório Financeiro"),
	ROLE_RELATORIO_FINANCEIRO_EXCLUIR("Excluir Relatório Financeiro"),

	// PAPEIS DO MENU 'Auditoria'
	ROLE_AUDITORIA_GESTAO("Gerenciar Auditoria"),

	// PAPEIS DO MENU 'Relatório Gerencial'
	ROLE_RELATORIO_GERENCIAL_GESTAO("Gerenciar Relatório Gerencial"),

	// PAPEIS DO MENU 'DIRF'
	ROLE_DIRF_GESTAO("Gestão DIRF"), ROLE_DIRF_CADASTRAR("Cadastrar DIRF"), ROLE_DIRF_ATUALIZAR("Atualizar DIRF"),
	ROLE_DIRF_EXCLUIR("Excluir DIRF"), ROLE_DIRF_VISUALIZAR("Visualizar DIRF"),

	// PAPEL PARA ARRECADAÇÃO ALIQUOTA PERIODO E ENCARGOS
	ROLE_ARRECADACAO_ALIQUOTA_GESTAO("Gestão arrecadação alíquota"),

	// PAPEL PARA ARRECADAÇÃO ÍNDICE ANO E MES
	ROLE_ARRECADACAO_INDICE_GESTAO("Gestão arrecadação índices"),
	ROLE_ARRECADACAO_INDICE_CADASTRAR("Cadastrar arrecadação índices"),
	ROLE_ARRECADACAO_INDICE_ATUALIZAR("Atualizar arrecadação índices"),
	ROLE_ARRECADACAO_INDICE_EXCLUIR("Excluir arrecadação índices"),
	ROLE_ARRECADACAO_INDICE_VISUALIZAR("Visualizar arrecadação índices"),

	// PAPEL PARA JUNTA MÉDICA
	ROLE_ESPECIALIDADE_MEDICA_GESTAO("Gestão especialidade médica"),

	// PAPEIS DO MENU 'AGENDA MÉDICA' - JUNTA MÉDICA
	ROLE_AGENDA_MEDICA_GESTAO("Gestão de Agenda Médica"), ROLE_AGENDA_MEDICA_CADASTRAR("Cadastrar Agenda Médica"),
	ROLE_AGENDA_MEDICA_ATUALIZAR("Atualizar Agenda Médica"), ROLE_AGENDA_MEDICA_EXCLUIR("Excluir Agenda Médica"),
	ROLE_AGENDA_MEDICA_VISUALIZAR("Visualizar Agenda Médica"),

	// PAPEIS DO MENU 'PERÍCIA MÉDICA' - JUNTA MÉDICA
	ROLE_PERICIA_MEDICA_GESTAO("Gestão de Perícia Médica"), ROLE_PERICIA_MEDICA_CADASTRAR("Cadastrar Perícia Médica"),
	ROLE_PERICIA_MEDICA_ATUALIZAR("Atualizar Perícia Médica"), ROLE_PERICIA_MEDICA_EXCLUIR("Excluir Perícia Médica"),
	ROLE_PERICIA_MEDICA_VISUALIZAR("Visualizar Perícia Médica"),

	// PAPEIS DO MENU 'PRONTUÁRIO PERÍCIA MÉDICA' - JUNTA MÉDICA
	ROLE_PRONTUARIO_PERICIA_MEDICA_GESTAO("Gestão de Prontuário Perícia Médica"),
	ROLE_PRONTUARIO_PERICIA_MEDICA_CADASTRAR("Cadastrar Prontuário Perícia Médica"),
	ROLE_PRONTUARIO_PERICIA_MEDICA_ATUALIZAR("Atualizar Prontuário Perícia Médica"),
	ROLE_PRONTUARIO_PERICIA_MEDICA_EXCLUIR("Excluir Prontuário Perícia Médica"),
	ROLE_PRONTUARIO_PERICIA_MEDICA_VISUALIZAR("Visualizar Prontuário Perícia Médica"),

	// PAPEL PARA RELATORIO DE APOSENTADOS E PENSÕES
	ROLE_RELATORIO_APOSENTADO_PENSAO_GESTAO("Gestão relatório aposentado e pensão"),

	// PAPEL PARA RELATORIO DE SERVIDORES COM PAGAMENTO BLOQUEADO
	ROLE_RELATORIO_SERV_PAG_BLOQUEADO_GESTAO("Gestão relatório de Servidores com Pagamento Bloqueado"),

	// PAPEL PARA GERAÇÃO DE ARQUIVO DE EXPORTAÇÃO SIPREV
	ROLE_ARQUIVO_EXPORTACAO_SIPREV_GESTAO("Gestão de Arquivo de Exportação - SIPREV"),

	// PAPEL PARA GESTÃO DE MÉDICOS
	ROLE_MEDICO_GESTAO("Gestão de Médicos");

	private String label;

	private PapelNomeEnum(String label) {
		this.label = label;
	}

	public String getLabel() {
		return label;
	}

}
