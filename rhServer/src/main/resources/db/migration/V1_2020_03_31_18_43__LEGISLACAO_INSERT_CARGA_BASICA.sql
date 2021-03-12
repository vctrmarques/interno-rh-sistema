-- Flávio Silva


-- inserts de carga básica de ente_federado
INSERT INTO ente_federado (created_at, updated_at, created_by, updated_by, codigo, descricao, vigencia) VALUES (SYSDATETIME(), SYSDATETIME(), 1, 1, 1, 'Município', '2017-01-01 00:00:00.000');
INSERT INTO ente_federado (created_at, updated_at, created_by, updated_by, codigo, descricao, vigencia) VALUES (SYSDATETIME(), SYSDATETIME(), 1, 1, 2, 'Estado', '2017-01-01 00:00:00.000');
INSERT INTO ente_federado (created_at, updated_at, created_by, updated_by, codigo, descricao, vigencia) VALUES (SYSDATETIME(), SYSDATETIME(), 1, 1, 3, 'Distrito Federal', '2017-01-01 00:00:00.000');
INSERT INTO ente_federado (created_at, updated_at, created_by, updated_by, codigo, descricao, vigencia) VALUES (SYSDATETIME(), SYSDATETIME(), 1, 1, 4, 'União', '2017-01-01 00:00:00.000');


-- inserts de carga básica de norma
INSERT INTO norma (created_at, updated_at, created_by, updated_by, codigo, descricao, vigencia) VALUES (SYSDATETIME(), SYSDATETIME(), 1, 1, 1, 'Lei Orgânica Municipal', '2017-01-01 00:00:00.000');
INSERT INTO norma (created_at, updated_at, created_by, updated_by, codigo, descricao, vigencia) VALUES (SYSDATETIME(), SYSDATETIME(), 1, 1, 2, 'Emenda à Lei Orgânica Municipal', '2017-01-01 00:00:00.000');
INSERT INTO norma (created_at, updated_at, created_by, updated_by, codigo, descricao, vigencia) VALUES (SYSDATETIME(), SYSDATETIME(), 1, 1, 3, 'Lei Complementar', '2017-01-01 00:00:00.000');
INSERT INTO norma (created_at, updated_at, created_by, updated_by, codigo, descricao, vigencia) VALUES (SYSDATETIME(), SYSDATETIME(), 1, 1, 4, 'Lei Ordinária', '2017-01-01 00:00:00.000');
INSERT INTO norma (created_at, updated_at, created_by, updated_by, codigo, descricao, vigencia) VALUES (SYSDATETIME(), SYSDATETIME(), 1, 1, 5, 'LDO', '2017-01-01 00:00:00.000');
INSERT INTO norma (created_at, updated_at, created_by, updated_by, codigo, descricao, vigencia) VALUES (SYSDATETIME(), SYSDATETIME(), 1, 1, 6, 'Decreto-lei', '2017-01-01 00:00:00.000');
INSERT INTO norma (created_at, updated_at, created_by, updated_by, codigo, descricao, vigencia) VALUES (SYSDATETIME(), SYSDATETIME(), 1, 1, 7, 'Decreto Executivo', '2017-01-01 00:00:00.000');
INSERT INTO norma (created_at, updated_at, created_by, updated_by, codigo, descricao, vigencia) VALUES (SYSDATETIME(), SYSDATETIME(), 1, 1, 8, 'Resolução', '2017-01-01 00:00:00.000');
INSERT INTO norma (created_at, updated_at, created_by, updated_by, codigo, descricao, vigencia) VALUES (SYSDATETIME(), SYSDATETIME(), 1, 1, 9, 'Decreto Legislativo', '2017-01-01 00:00:00.000');
INSERT INTO norma (created_at, updated_at, created_by, updated_by, codigo, descricao, vigencia) VALUES (SYSDATETIME(), SYSDATETIME(), 1, 1, 10, 'Portaria', '2017-01-01 00:00:00.000');
INSERT INTO norma (created_at, updated_at, created_by, updated_by, codigo, descricao, vigencia) VALUES (SYSDATETIME(), SYSDATETIME(), 1, 1, 11, 'Convenção / Acordo Trabalho', '2017-01-01 00:00:00.000');
INSERT INTO norma (created_at, updated_at, created_by, updated_by, codigo, descricao, vigencia) VALUES (SYSDATETIME(), SYSDATETIME(), 1, 1, 12, 'Instrução Normativa', '2017-01-01 00:00:00.000');
INSERT INTO norma (created_at, updated_at, created_by, updated_by, codigo, descricao, vigencia) VALUES (SYSDATETIME(), SYSDATETIME(), 1, 1, 13, 'Norma Empresa Pública', '2017-01-01 00:00:00.000');


-- inserts de carga básica de detalhamento norma
INSERT INTO detalhamento_norma (created_at, updated_at, created_by, updated_by, codigo, descricao, vigencia) VALUES (SYSDATETIME(), SYSDATETIME(), 1, 1, 1, 'Estatuto dos Servidores Públicos', '2017-01-01 00:00:00.000');
INSERT INTO detalhamento_norma (created_at, updated_at, created_by, updated_by, codigo, descricao, vigencia) VALUES (SYSDATETIME(), SYSDATETIME(), 1, 1, 2, 'Plano de Cargos e Salários', '2017-01-01 00:00:00.000');
INSERT INTO detalhamento_norma (created_at, updated_at, created_by, updated_by, codigo, descricao, vigencia) VALUES (SYSDATETIME(), SYSDATETIME(), 1, 1, 3, 'Plano de Cargos e Salários do Magistério', '2017-01-01 00:00:00.000');
INSERT INTO detalhamento_norma (created_at, updated_at, created_by, updated_by, codigo, descricao, vigencia) VALUES (SYSDATETIME(), SYSDATETIME(), 1, 1, 4, 'Estrutura / Organização Administrativa', '2017-01-01 00:00:00.000');
INSERT INTO detalhamento_norma (created_at, updated_at, created_by, updated_by, codigo, descricao, vigencia) VALUES (SYSDATETIME(), SYSDATETIME(), 1, 1, 5, 'Lei de Contratação Temporária', '2017-01-01 00:00:00.000');
INSERT INTO detalhamento_norma (created_at, updated_at, created_by, updated_by, codigo, descricao, vigencia) VALUES (SYSDATETIME(), SYSDATETIME(), 1, 1, 6, 'Lei RPPS (Plano de Benefícios do RPPS)', '2017-01-01 00:00:00.000');
INSERT INTO detalhamento_norma (created_at, updated_at, created_by, updated_by, codigo, descricao, vigencia) VALUES (SYSDATETIME(), SYSDATETIME(), 1, 1, 7, 'Quadro de Pessoal', '2017-01-01 00:00:00.000');
INSERT INTO detalhamento_norma (created_at, updated_at, created_by, updated_by, codigo, descricao, vigencia) VALUES (SYSDATETIME(), SYSDATETIME(), 1, 1, 8, 'Lei da Revisão Geral Anual (Política Inflacionária)', '2017-01-01 00:00:00.000');
INSERT INTO detalhamento_norma (created_at, updated_at, created_by, updated_by, codigo, descricao, vigencia) VALUES (SYSDATETIME(), SYSDATETIME(), 1, 1, 9, 'Lei da Revisão Geral Anual - Específica', '2017-01-01 00:00:00.000');
INSERT INTO detalhamento_norma (created_at, updated_at, created_by, updated_by, codigo, descricao, vigencia) VALUES (SYSDATETIME(), SYSDATETIME(), 1, 1, 10, 'Fixação dos Subsídios dos Agentes Políticos', '2017-01-01 00:00:00.000');
INSERT INTO detalhamento_norma (created_at, updated_at, created_by, updated_by, codigo, descricao, vigencia) VALUES (SYSDATETIME(), SYSDATETIME(), 1, 1, 11, 'Regulamentação de Concurso Público / Seleção', '2017-01-01 00:00:00.000');
INSERT INTO detalhamento_norma (created_at, updated_at, created_by, updated_by, codigo, descricao, vigencia) VALUES (SYSDATETIME(), SYSDATETIME(), 1, 1, 12, 'Regulamentação Cargos / Salário / Benefício', '2017-01-01 00:00:00.000');
INSERT INTO detalhamento_norma (created_at, updated_at, created_by, updated_by, codigo, descricao, vigencia) VALUES (SYSDATETIME(), SYSDATETIME(), 1, 1, 13, 'Empesa Pública - Regulamentação Cargos/Salários/Benefícios', '2017-01-01 00:00:00.000');


-- inserts de carga básica de detalhamento norma
INSERT INTO assunto_norma (created_at, updated_at, created_by, updated_by, codigo, descricao, vigencia) VALUES (SYSDATETIME(), SYSDATETIME(), 1, 1, 1, 'Provimento', '2017-01-01 00:00:00.000');
INSERT INTO assunto_norma (created_at, updated_at, created_by, updated_by, codigo, descricao, vigencia) VALUES (SYSDATETIME(), SYSDATETIME(), 1, 1, 2, 'Vacância', '2017-01-01 00:00:00.000');
INSERT INTO assunto_norma (created_at, updated_at, created_by, updated_by, codigo, descricao, vigencia) VALUES (SYSDATETIME(), SYSDATETIME(), 1, 1, 3, 'Remoção / Redistribuição', '2017-01-01 00:00:00.000');
INSERT INTO assunto_norma (created_at, updated_at, created_by, updated_by, codigo, descricao, vigencia) VALUES (SYSDATETIME(), SYSDATETIME(), 1, 1, 4, 'Substituição', '2017-01-01 00:00:00.000');
INSERT INTO assunto_norma (created_at, updated_at, created_by, updated_by, codigo, descricao, vigencia) VALUES (SYSDATETIME(), SYSDATETIME(), 1, 1, 5, 'Férias de Servidor Público', '2017-01-01 00:00:00.000');
INSERT INTO assunto_norma (created_at, updated_at, created_by, updated_by, codigo, descricao, vigencia) VALUES (SYSDATETIME(), SYSDATETIME(), 1, 1, 6, 'Licença Remunerada', '2017-01-01 00:00:00.000');
INSERT INTO assunto_norma (created_at, updated_at, created_by, updated_by, codigo, descricao, vigencia) VALUES (SYSDATETIME(), SYSDATETIME(), 1, 1, 7, 'Licença NÃO Remunerada', '2017-01-01 00:00:00.000');
INSERT INTO assunto_norma (created_at, updated_at, created_by, updated_by, codigo, descricao, vigencia) VALUES (SYSDATETIME(), SYSDATETIME(), 1, 1, 8, 'Afastamentos', '2017-01-01 00:00:00.000');
INSERT INTO assunto_norma (created_at, updated_at, created_by, updated_by, codigo, descricao, vigencia) VALUES (SYSDATETIME(), SYSDATETIME(), 1, 1, 9, 'Concessões', '2017-01-01 00:00:00.000');
INSERT INTO assunto_norma (created_at, updated_at, created_by, updated_by, codigo, descricao, vigencia) VALUES (SYSDATETIME(), SYSDATETIME(), 1, 1, 10, 'Tempo de Serviço', '2017-01-01 00:00:00.000');
INSERT INTO assunto_norma (created_at, updated_at, created_by, updated_by, codigo, descricao, vigencia) VALUES (SYSDATETIME(), SYSDATETIME(), 1, 1, 11, 'Direito de Petição', '2017-01-01 00:00:00.000');
INSERT INTO assunto_norma (created_at, updated_at, created_by, updated_by, codigo, descricao, vigencia) VALUES (SYSDATETIME(), SYSDATETIME(), 1, 1, 12, 'Regime Disciplinar (Deveres, Proibições, Responsabilidades e Penalidades)', '2017-01-01 00:00:00.000');
INSERT INTO assunto_norma (created_at, updated_at, created_by, updated_by, codigo, descricao, vigencia) VALUES (SYSDATETIME(), SYSDATETIME(), 1, 1, 13, 'Acumulação', '2017-01-01 00:00:00.000');
INSERT INTO assunto_norma (created_at, updated_at, created_by, updated_by, codigo, descricao, vigencia) VALUES (SYSDATETIME(), SYSDATETIME(), 1, 1, 14, 'Processo Administrativo Disciplinar', '2017-01-01 00:00:00.000');
INSERT INTO assunto_norma (created_at, updated_at, created_by, updated_by, codigo, descricao, vigencia) VALUES (SYSDATETIME(), SYSDATETIME(), 1, 1, 15, 'Regime de Benefícios do RPPS', '2017-01-01 00:00:00.000');
INSERT INTO assunto_norma (created_at, updated_at, created_by, updated_by, codigo, descricao, vigencia) VALUES (SYSDATETIME(), SYSDATETIME(), 1, 1, 16, 'Beneficiários', '2017-01-01 00:00:00.000');
INSERT INTO assunto_norma (created_at, updated_at, created_by, updated_by, codigo, descricao, vigencia) VALUES (SYSDATETIME(), SYSDATETIME(), 1, 1, 17, 'Aposentadoria', '2017-01-01 00:00:00.000');
INSERT INTO assunto_norma (created_at, updated_at, created_by, updated_by, codigo, descricao, vigencia) VALUES (SYSDATETIME(), SYSDATETIME(), 1, 1, 18, 'Pensão por Morte', '2017-01-01 00:00:00.000');
INSERT INTO assunto_norma (created_at, updated_at, created_by, updated_by, codigo, descricao, vigencia) VALUES (SYSDATETIME(), SYSDATETIME(), 1, 1, 19, 'Auxílio Doença', '2017-01-01 00:00:00.000');
INSERT INTO assunto_norma (created_at, updated_at, created_by, updated_by, codigo, descricao, vigencia) VALUES (SYSDATETIME(), SYSDATETIME(), 1, 1, 20, 'Auxílio Reclusão', '2017-01-01 00:00:00.000');
INSERT INTO assunto_norma (created_at, updated_at, created_by, updated_by, codigo, descricao, vigencia) VALUES (SYSDATETIME(), SYSDATETIME(), 1, 1, 21, 'Salário Maternidade', '2017-01-01 00:00:00.000');
INSERT INTO assunto_norma (created_at, updated_at, created_by, updated_by, codigo, descricao, vigencia) VALUES (SYSDATETIME(), SYSDATETIME(), 1, 1, 22, 'Salário Família', '2017-01-01 00:00:00.000');
INSERT INTO assunto_norma (created_at, updated_at, created_by, updated_by, codigo, descricao, vigencia) VALUES (SYSDATETIME(), SYSDATETIME(), 1, 1, 23, 'Alíquota Previdenciária', '2017-01-01 00:00:00.000');
INSERT INTO assunto_norma (created_at, updated_at, created_by, updated_by, codigo, descricao, vigencia) VALUES (SYSDATETIME(), SYSDATETIME(), 1, 1, 24, 'Organização do RPPS', '2017-01-01 00:00:00.000');
INSERT INTO assunto_norma (created_at, updated_at, created_by, updated_by, codigo, descricao, vigencia) VALUES (SYSDATETIME(), SYSDATETIME(), 1, 1, 25, 'Custeio RPPS', '2017-01-01 00:00:00.000');
INSERT INTO assunto_norma (created_at, updated_at, created_by, updated_by, codigo, descricao, vigencia) VALUES (SYSDATETIME(), SYSDATETIME(), 1, 1, 26, 'Concurso Público', '2017-01-01 00:00:00.000');
INSERT INTO assunto_norma (created_at, updated_at, created_by, updated_by, codigo, descricao, vigencia) VALUES (SYSDATETIME(), SYSDATETIME(), 1, 1, 27, 'Processo Seletivo Público - PSP', '2017-01-01 00:00:00.000');
INSERT INTO assunto_norma (created_at, updated_at, created_by, updated_by, codigo, descricao, vigencia) VALUES (SYSDATETIME(), SYSDATETIME(), 1, 1, 28, 'Processo Seletivo Simplificado - PSS', '2017-01-01 00:00:00.000');
INSERT INTO assunto_norma (created_at, updated_at, created_by, updated_by, codigo, descricao, vigencia) VALUES (SYSDATETIME(), SYSDATETIME(), 1, 1, 29, 'Cotas PcD', '2017-01-01 00:00:00.000');
INSERT INTO assunto_norma (created_at, updated_at, created_by, updated_by, codigo, descricao, vigencia) VALUES (SYSDATETIME(), SYSDATETIME(), 1, 1, 30, 'Cotas Raciais', '2017-01-01 00:00:00.000');
INSERT INTO assunto_norma (created_at, updated_at, created_by, updated_by, codigo, descricao, vigencia) VALUES (SYSDATETIME(), SYSDATETIME(), 1, 1, 31, 'Cotas Socioeconômicas', '2017-01-01 00:00:00.000');
INSERT INTO assunto_norma (created_at, updated_at, created_by, updated_by, codigo, descricao, vigencia) VALUES (SYSDATETIME(), SYSDATETIME(), 1, 1, 32, 'Autorização para Contratação Temporária', '2017-01-01 00:00:00.000');
INSERT INTO assunto_norma (created_at, updated_at, created_by, updated_by, codigo, descricao, vigencia) VALUES (SYSDATETIME(), SYSDATETIME(), 1, 1, 33, 'Casos de Excepcional Interesse Público', '2017-01-01 00:00:00.000');
INSERT INTO assunto_norma (created_at, updated_at, created_by, updated_by, codigo, descricao, vigencia) VALUES (SYSDATETIME(), SYSDATETIME(), 1, 1, 34, 'Criação de Cargos/Empregos/Funções', '2017-01-01 00:00:00.000');
INSERT INTO assunto_norma (created_at, updated_at, created_by, updated_by, codigo, descricao, vigencia) VALUES (SYSDATETIME(), SYSDATETIME(), 1, 1, 35, 'Modificação do Quantitativo Cargos/Empregos/Funções', '2017-01-01 00:00:00.000');
INSERT INTO assunto_norma (created_at, updated_at, created_by, updated_by, codigo, descricao, vigencia) VALUES (SYSDATETIME(), SYSDATETIME(), 1, 1, 36, 'Extinção Cargos/Empregos/Funções', '2017-01-01 00:00:00.000');
INSERT INTO assunto_norma (created_at, updated_at, created_by, updated_by, codigo, descricao, vigencia) VALUES (SYSDATETIME(), SYSDATETIME(), 1, 1, 37, 'Definição / Ajustes de Carga horária', '2017-01-01 00:00:00.000');
INSERT INTO assunto_norma (created_at, updated_at, created_by, updated_by, codigo, descricao, vigencia) VALUES (SYSDATETIME(), SYSDATETIME(), 1, 1, 38, 'Atribuições', '2017-01-01 00:00:00.000');
INSERT INTO assunto_norma (created_at, updated_at, created_by, updated_by, codigo, descricao, vigencia) VALUES (SYSDATETIME(), SYSDATETIME(), 1, 1, 39, 'Requisitos de Provimento', '2017-01-01 00:00:00.000');
INSERT INTO assunto_norma (created_at, updated_at, created_by, updated_by, codigo, descricao, vigencia) VALUES (SYSDATETIME(), SYSDATETIME(), 1, 1, 40, 'Disposição / Cessão', '2017-01-01 00:00:00.000');
INSERT INTO assunto_norma (created_at, updated_at, created_by, updated_by, codigo, descricao, vigencia) VALUES (SYSDATETIME(), SYSDATETIME(), 1, 1, 41, 'Promoção / Progressão', '2017-01-01 00:00:00.000');
INSERT INTO assunto_norma (created_at, updated_at, created_by, updated_by, codigo, descricao, vigencia) VALUES (SYSDATETIME(), SYSDATETIME(), 1, 1, 42, 'Transformação / Transposição / Alteração de Nomenclatura', '2017-01-01 00:00:00.000');
INSERT INTO assunto_norma (created_at, updated_at, created_by, updated_by, codigo, descricao, vigencia) VALUES (SYSDATETIME(), SYSDATETIME(), 1, 1, 43, 'Enquadramento / Aproveitamento', '2017-01-01 00:00:00.000');
INSERT INTO assunto_norma (created_at, updated_at, created_by, updated_by, codigo, descricao, vigencia) VALUES (SYSDATETIME(), SYSDATETIME(), 1, 1, 44, 'Revisão Geral Anual', '2017-01-01 00:00:00.000');
INSERT INTO assunto_norma (created_at, updated_at, created_by, updated_by, codigo, descricao, vigencia) VALUES (SYSDATETIME(), SYSDATETIME(), 1, 1, 45, 'Fixação de Subsídios', '2017-01-01 00:00:00.000');
INSERT INTO assunto_norma (created_at, updated_at, created_by, updated_by, codigo, descricao, vigencia) VALUES (SYSDATETIME(), SYSDATETIME(), 1, 1, 46, 'Revisão (Reposição Inflacionária)', '2017-01-01 00:00:00.000');
INSERT INTO assunto_norma (created_at, updated_at, created_by, updated_by, codigo, descricao, vigencia) VALUES (SYSDATETIME(), SYSDATETIME(), 1, 1, 47, 'Reajuste', '2017-01-01 00:00:00.000');
INSERT INTO assunto_norma (created_at, updated_at, created_by, updated_by, codigo, descricao, vigencia) VALUES (SYSDATETIME(), SYSDATETIME(), 1, 1, 48, 'Índice Inflacionário', '2017-01-01 00:00:00.000');
INSERT INTO assunto_norma (created_at, updated_at, created_by, updated_by, codigo, descricao, vigencia) VALUES (SYSDATETIME(), SYSDATETIME(), 1, 1, 49, 'Descontos', '2017-01-01 00:00:00.000');
INSERT INTO assunto_norma (created_at, updated_at, created_by, updated_by, codigo, descricao, vigencia) VALUES (SYSDATETIME(), SYSDATETIME(), 1, 1, 50, 'Cria Vantagem Pecuniária', '2017-01-01 00:00:00.000');
INSERT INTO assunto_norma (created_at, updated_at, created_by, updated_by, codigo, descricao, vigencia) VALUES (SYSDATETIME(), SYSDATETIME(), 1, 1, 51, 'Modifica Vantagem Pecuniária', '2017-01-01 00:00:00.000');
INSERT INTO assunto_norma (created_at, updated_at, created_by, updated_by, codigo, descricao, vigencia) VALUES (SYSDATETIME(), SYSDATETIME(), 1, 1, 52, 'Extingue Vantagem Pecuniária', '2017-01-01 00:00:00.000');
INSERT INTO assunto_norma (created_at, updated_at, created_by, updated_by, codigo, descricao, vigencia) VALUES (SYSDATETIME(), SYSDATETIME(), 1, 1, 53, '13º Salário de Agente Político', '2017-01-01 00:00:00.000');
INSERT INTO assunto_norma (created_at, updated_at, created_by, updated_by, codigo, descricao, vigencia) VALUES (SYSDATETIME(), SYSDATETIME(), 1, 1, 54, 'Férias de Agente Político', '2017-01-01 00:00:00.000');
INSERT INTO assunto_norma (created_at, updated_at, created_by, updated_by, codigo, descricao, vigencia) VALUES (SYSDATETIME(), SYSDATETIME(), 1, 1, 55, 'Piso do Magistério', '2017-01-01 00:00:00.000');
INSERT INTO assunto_norma (created_at, updated_at, created_by, updated_by, codigo, descricao, vigencia) VALUES (SYSDATETIME(), SYSDATETIME(), 1, 1, 56, 'Salário Mínimo Municipal', '2017-01-01 00:00:00.000');
INSERT INTO assunto_norma (created_at, updated_at, created_by, updated_by, codigo, descricao, vigencia) VALUES (SYSDATETIME(), SYSDATETIME(), 1, 1, 57, 'Cargos, Empregos e Funções Públicas', '2017-01-01 00:00:00.000');
INSERT INTO assunto_norma (created_at, updated_at, created_by, updated_by, codigo, descricao, vigencia) VALUES (SYSDATETIME(), SYSDATETIME(), 1, 1, 58, 'Vencimento / Remuneração / Subsídios', '2017-01-01 00:00:00.000');
INSERT INTO assunto_norma (created_at, updated_at, created_by, updated_by, codigo, descricao, vigencia) VALUES (SYSDATETIME(), SYSDATETIME(), 1, 1, 59, 'Processo Administrativo', '2017-01-01 00:00:00.000');
INSERT INTO assunto_norma (created_at, updated_at, created_by, updated_by, codigo, descricao, vigencia) VALUES (SYSDATETIME(), SYSDATETIME(), 1, 1, 60, 'Ficha Limpa', '2017-01-01 00:00:00.000');
INSERT INTO assunto_norma (created_at, updated_at, created_by, updated_by, codigo, descricao, vigencia) VALUES (SYSDATETIME(), SYSDATETIME(), 1, 1, 61, 'Diárias', '2017-01-01 00:00:00.000');
INSERT INTO assunto_norma (created_at, updated_at, created_by, updated_by, codigo, descricao, vigencia) VALUES (SYSDATETIME(), SYSDATETIME(), 1, 1, 62, 'Controle Interno', '2017-01-01 00:00:00.000');
INSERT INTO assunto_norma (created_at, updated_at, created_by, updated_by, codigo, descricao, vigencia) VALUES (SYSDATETIME(), SYSDATETIME(), 1, 1, 63, 'Gratificações', '2017-01-01 00:00:00.000');
INSERT INTO assunto_norma (created_at, updated_at, created_by, updated_by, codigo, descricao, vigencia) VALUES (SYSDATETIME(), SYSDATETIME(), 1, 1, 64, 'Auxílios', '2017-01-01 00:00:00.000');
INSERT INTO assunto_norma (created_at, updated_at, created_by, updated_by, codigo, descricao, vigencia) VALUES (SYSDATETIME(), SYSDATETIME(), 1, 1, 65, 'Adicionais', '2017-01-01 00:00:00.000');
INSERT INTO assunto_norma (created_at, updated_at, created_by, updated_by, codigo, descricao, vigencia) VALUES (SYSDATETIME(), SYSDATETIME(), 1, 1, 66, 'Abonos', '2017-01-01 00:00:00.000');
INSERT INTO assunto_norma (created_at, updated_at, created_by, updated_by, codigo, descricao, vigencia) VALUES (SYSDATETIME(), SYSDATETIME(), 1, 1, 67, 'Premiação/Bonificação', '2017-01-01 00:00:00.000');
INSERT INTO assunto_norma (created_at, updated_at, created_by, updated_by, codigo, descricao, vigencia) VALUES (SYSDATETIME(), SYSDATETIME(), 1, 1, 68, 'Alteração de Jornada de Trabalho', '2017-01-01 00:00:00.000');
INSERT INTO assunto_norma (created_at, updated_at, created_by, updated_by, codigo, descricao, vigencia) VALUES (SYSDATETIME(), SYSDATETIME(), 1, 1, 69, 'REPT - Regime Especial de Trabalho (Guarda Municipal)', '2017-01-01 00:00:00.000');
INSERT INTO assunto_norma (created_at, updated_at, created_by, updated_by, codigo, descricao, vigencia) VALUES (SYSDATETIME(), SYSDATETIME(), 1, 1, 70, 'Descontos Legais', '2017-01-01 00:00:00.000');
INSERT INTO assunto_norma (created_at, updated_at, created_by, updated_by, codigo, descricao, vigencia) VALUES (SYSDATETIME(), SYSDATETIME(), 1, 1, 71, 'Desconto (Convênios, consignações)', '2017-01-01 00:00:00.000');

-- inserts de carga básica de unidade gestora
INSERT INTO unidade_gestora (created_at, updated_at, created_by, updated_by, codigo, descricao) VALUES (SYSDATETIME(), SYSDATETIME(), 1, 1, 1906, 'GoiâniaPrev');

-- inserts de carga básica de texto_documento
INSERT INTO texto_documento (created_at, updated_at, created_by, updated_by, codigo, descricao, vigencia) VALUES (SYSDATETIME(), SYSDATETIME(), 1, 1, 1, 'Texto Principal da Lei/Norma', '2017-01-01 00:00:00.000');
INSERT INTO texto_documento (created_at, updated_at, created_by, updated_by, codigo, descricao, vigencia) VALUES (SYSDATETIME(), SYSDATETIME(), 1, 1, 2, 'Anexo da Lei/Norma', '2017-01-01 00:00:00.000');

