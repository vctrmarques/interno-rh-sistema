(function () {
	'use strict';

	angular.module('app.page')
		.controller('relatorioAposentadoPensionistaCtrl', ['$location', 'configValue', '$scope', '$mdToast', '$mdDialog', '$mdMedia', '$q', '$rootScope', '$timeout', 'GenericoService', 'EnumService', 'RestService', '$filter', relatorioAposentadoPensionistaCtrl]);

	function relatorioAposentadoPensionistaCtrl($location, configValue, $scope, $mdToast, $mdDialog, $mdMedia, $q, $rootScope, $timeout, GenericoService, EnumService, RestService, $filter) {

        // Checa as regras de acesso da tela
        RestService.Get('/usuario/verificaPermissao/por/menu', { params: { menu: 'Rel. Aposentados e pensões' } })
            .then(function (response) {
                if (response.status === 200 && response.data) {
                    $scope.permissao = response.data;
                    if (!$scope.permissao.podeGerenciar)
                        $location.path('page/403');
                }
            }, function errorCallback(response) {
                if (response.status === 400) {
                    $scope.showSimpleToast(response.data.message);
                }
        });

        $scope.titulo = "Relatório de aposentados e pensões";

        $scope.lista = {};
        $scope.lista.situacaoFuncional = ["APOSENTADO", "PENSIONISTA"];
        $scope.lista.status = ["ATIVO", "INATIVO"];
        $scope.lista.lotacao = [];

        $scope.filtro = {};
        $scope.filtro.lotacoes = [];
        
        //Atributos para aposentados
        $scope.apo = {};

        //Atributos para pensionista
        $scope.pen = {};
        
        // Busca das Filiais
        RestService.Get('/relatorioDadosAposentadoPensionista/filiais')
            .then(function (response) {
                if (response.status === 200 && response.data) {
                    $scope.lista.filial = response.data;
                } else if (response.status === 200 && !response.data) {
                    $scope.showSimpleToast("Não existem filiais disponíveis.");
                }
            }, function errorCallback(response) {
                if (response.status === 400) {
                    $scope.showSimpleToast(response.data.message);
                }
            });
        
        // Busca Lotação ao selecionar filial
        $scope.changeLotacao = function () {
            if ($scope.filtro.filial) {

                $rootScope.$broadcast('preloader:active');
                RestService.Get('/relatorioDadosAposentadoPensionista/lotacoes/' + $scope.filtro.filial)
                    .then(function (response) {
                        if (response.status === 200 && response.data) {
                            $scope.lista.lotacao = response.data;
                            $rootScope.$broadcast('preloader:hide');
                        } else if (response.status === 200 && !response.data) {
                            $scope.showSimpleToast("Não existem lotações para a filial escolhida.");
                            $rootScope.$broadcast('preloader:hide');
                        }
                    }, function errorCallback(response) {
                        if (response.status === 400) {
                            $scope.showSimpleToast(response.data.message);
                        }
                    });
            }
        };
        // Desabilita combo de situação funcional com base em filial e lotacao
        $scope.disabledSituacaoFuncional = function () {
            return $scope.filtro.filial ? ($scope.lista.lotacao.length == 0 ? false : ($scope.filtro.lotacoes.length > 0 ? false : true)) : true;
        }
        // Desabilita o botão de gerar o relatório se os filtros padrões não estiverem selecionados
        $scope.disabledGerarRelatorio = function () {
            return !($scope.filtro.filial ? (($scope.lista.lotacao.length == 0 || $scope.filtro.lotacoes.length > 0) ? ($scope.filtro.situacaoFuncional ? true : false) : false) : false);
        }

        $scope.checkNotSituacaoFuncional = function () {
            return $scope.filtro.situacaoFuncional ? false : true;
        }

        $scope.disabledLotacao = function(){
            return $scope.filtro.filial ? ($scope.lista.lotacao.length == 0) : true;
        }

   		$scope.limpaFiltro = function () {
			// colocar os filtros aqui da busca
            delete $scope.filtro.filial;
            delete $scope.filtro.lotacoes;
            delete $scope.filtro.situacaoFuncional;
            delete $scope.filtro.status;

            $scope.apo.selectedDadosPessoais = [];
            $scope.apo.selectedDadosProfissionais = [];
            $scope.apo.selectedDocsPessoais = [];
            $scope.apo.selectedDocsNomeacao = [];
            $scope.apo.selectedContatoEndereco = [];
            $scope.apo.selectedDadosBancoPessoal = [];
            $scope.apo.selectedDadosBancarios = [];

            $scope.pen.selectedDadosPessoais = [];
            $scope.pen.selectedDadosExSegurado = [];
            $scope.pen.selectedDadosDependente = [];
            $scope.pen.selectedDocsPessoal = [];
            $scope.pen.selectedContatoEndereco = [];
            $scope.pen.selectedDadosBeneficio = [];
            $scope.pen.selectedDadosContaCredito = [];
            $scope.pen.selectedDadosIsencao = [];
        }
        
        $scope.apo.selectedDadosPessoais = [];
        $scope.apo.selectedDadosProfissionais = [];
        $scope.apo.selectedDocsPessoais = [];
        $scope.apo.selectedDocsNomeacao = [];
        $scope.apo.selectedContatoEndereco = [];
        $scope.apo.selectedDadosBancoPessoal = [];
        $scope.apo.selectedDadosBancarios = [];

        $scope.pen.selectedDadosPessoais = [];
        $scope.pen.selectedDadosExSegurado = [];
        $scope.pen.selectedDadosDependente = [];
        $scope.pen.selectedDocsPessoal = [];
        $scope.pen.selectedContatoEndereco = [];
        $scope.pen.selectedDadosBeneficio = [];
        $scope.pen.selectedDadosContaCredito = [];
        $scope.pen.selectedDadosIsencao = [];
        
        $scope.apo.dadosPessoais = [
            {label: 'Nome', value: 'NOME'},
            {label: 'Gênero', value: 'GENERO'},
            {label: 'Data de nascimento', value: 'DATANASCIMENTO'},
            {label: 'Estado civil', value: 'ESTADOCIVIL'},
            {label: 'Naturalidade', value: 'NATURALIDADE'},
            {label: 'Nacionalidade', value: 'NACIONALIDADE'},
            {label: 'Cor da pele', value: 'CORPELE'},
            {label: 'Tipo Sanguíneo', value: 'TIPOSANGUINEO'},
            {label: 'Nome pai', value: 'NOMEPAI'},
            {label: 'Nome mãe', value: 'NOMEMAE'}
        ];

        $scope.apo.dadosProfissionais = [
            {label: 'Filial', value: 'FILIAL'},
            {label: 'Lotação', value: 'LOTACAO'},
            {label: 'Matrícula', value: 'MATRICULA'},
            {label: 'Centro de custo', value: 'CENTROCUSTO'},
            {label: 'Grau de instrução', value: 'GRAUINSTRUCAO'}
        ];

        $scope.apo.docsPessoais = [
            {label: 'CPF', value: 'CPF'},
            {label: 'Identidade', value: 'IDENTIDADE'},
            {label: 'Órgão expeditor', value: 'ORGAOEXPEDITORIDENTIDADE'},
            {label: 'UF Identidade', value: 'UFIDENTIDADE'},
            {label: 'Data expedição RG', value: 'DATAEXPEDICAOIDENTIDADE'},
            {label: 'Nº CTPS', value: 'NUMEROCTPS'},
            {label: 'Série CTPS', value: 'SERIECTPS'},
            {label: 'Pis/Pasep', value: 'PIS'},
            {label: 'Data emissão Pis/Pasep', value: 'DATAPIS'},
            {label: 'Nº SUS', value: 'NUMEROSUS'},
            {label: 'Nº Título eleitor', value: 'NUMEROTITULOELEITOR'},
            {label: 'Seção', value: 'SECAO'},
            {label: 'Zona', value: 'ZONA'},
            {label: 'UF Título eleitor', value: 'UFTITULOELEITOR'},
            {label: 'CNH', value: 'CNH'},
            {label: 'Data validade CNH', value: 'DATAVALIDADECNH'},
            {label: 'Registro alistamento', value: 'REGALISTAMENTO'},
            {label: 'Categoria alistamento', value: 'CATALISTAMENTO'}
        ];

        $scope.apo.docsNomeacao = [
            {label: 'Classificacao do ato', value: 'CLASSIFICACAOATO'},
            {label: 'Nº do processo', value: 'NUMPROCESSO'},
            {label: 'Nº do ato', value: 'NUMATO'},
            {label: 'Data de nomeação', value: 'DATANOMEACAO'},
            {label: 'Nº Diário Oficial', value: 'NUMDIARIOOFICIAL'},
            {label: 'Publicação Diário Oficial', value: 'PUBLICACAODIARIOOFICIAL'}
        ];

        $scope.apo.contatoEndereco = [
            {label: 'Logradouro', value: 'LOGRADOURO'},
            {label: 'Número', value: 'NUMERO'},
            {label: 'Complemento', value: 'COMPLEMENTO'},
            {label: 'UF', value: 'UF'},
            {label: 'Município', value: 'MUNICIPIO'},
            {label: 'Bairro', value: 'BAIRRO'},
            {label: 'CEP', value: 'CEP'},
            {label: 'E-mail pessoal', value: 'EMAILPESSOAL'},
            {label: 'E-mail coorporativo', value: 'EMAILCOORPORATIVO'},
            {label: 'Telefone principal', value: 'TELEFONEPRINCIPAL'},
            {label: 'Telefone opcional', value: 'TELEFONEOPCIONAL'}
        ];

        $scope.apo.dadosBancoPessoal = [
            {label: 'Jornada de trabalho', value: 'JORNADA'},
            {label: 'Vínculo', value: 'VINCULO'},
            {label: 'Data de admissão', value: 'DATAADMISSAO'},
            {label: 'Nº de dependentes IR', value: 'DEPENDENTESIR'},
            {label: 'Nº de dependentes SR', value: 'DEPENDENTESSR'},
            {label: 'Cargo', value: 'CARGO'},
            {label: 'Nível salarial', value: 'NIVELSALARIAL'},
            {label: 'Referência salarial', value: 'REFERENCIASALARIAL'}
        ];

        $scope.apo.dadosBancarios = [
            {label: 'Tipo de conta', value: 'TIPOCONTA'},
            {label: 'Banco', value: 'BANCO'},
            {label: 'Agência', value: 'AGENCIA'},
            {label: 'Nº da conta', value: 'NUMEROCONTA'},
            {label: 'Digito da conta', value: 'DIGITOCONTA'},
            {label: 'Operação', value: 'OPERAÇÃO'}
        ];


        //Pensionista

        $scope.pen.dadosPessoais = [
            {label: 'Matrícula', value: 'MATRICULA'},
            {label: 'Nome', value: 'NOME'},
            {label: 'Gênero', value: 'GENERO'},
            {label: 'Data de nascimento', value: 'DATANASCIMENTO'},
            {label: 'Estado civil', value: 'ESTADOCIVIL'},
            {label: 'Grau de instrução', value: 'GRAUINSTRUCAO'},
            {label: 'Naturalidade', value: 'NATURALIDADE'},
            {label: 'Nacionalidade', value: 'NACIONALIDADE'},
            {label: 'Cor da pele', value: 'CORPELE'},
            {label: 'Tipo Sanguíneo', value: 'TIPOSANGUINEO'},
            {label: 'Nome pai', value: 'NOMEPAI'},
            {label: 'Nome mãe', value: 'NOMEMAE'},
            {label: 'Status', value: 'STATUS'}
        ];

        $scope.pen.dadosExSegurado = [
            {label: 'Matrícula', value: 'MATRICULA'},
            {label: 'Nome', value: 'NOME'},
            {label: 'CPF', value: 'CPF'},
            {label: 'Identidade', value: 'IDENTIDADE'},
            {label: 'Data expedição RG', value: 'DATAEXPEDICAOIDENTIDADE'},
            {label: 'Data do Óbito', value: 'DATAOBITO'}
        ];

        $scope.pen.dadosDependente = [
            {label: 'Família', value: 'FAMILIA'},
            {label: 'CPF responsável', value: 'CPFRESPONSAVEL'},
            {label: 'Data de início', value: 'DATAINICIO'},
            {label: 'Grau parentesco', value: 'GRAUPARENTESCO'},
            {label: 'Responsável', value: 'RESPONSAVEL'},
            {label: 'Data de vencimento', value: 'DATAVENCIMENTO'},
            {label: 'Motivo', value: 'MOTIVO'},
            {label: 'Nº Processo', value: 'NUMPROCESSO'}
        ];

        $scope.pen.docsPessoal = [
            {label: 'CPF', value: 'CPF'},
            {label: 'Identidade', value: 'IDENTIDADE'},
            {label: 'Data expedição RG', value: 'DATAEXPEDICAOIDENTIDADE'},
            {label: 'Nº Título eleitor', value: 'NUMEROTITULOELEITOR'}
        ];

        $scope.pen.contatoEndereco = [
            {label: 'Logradouro', value: 'LOGRADOURO'},
            {label: 'Número', value: 'NUMERO'},
            {label: 'Complemento', value: 'COMPLEMENTO'},
            {label: 'UF', value: 'UF'},
            {label: 'Município', value: 'MUNICIPIO'},
            {label: 'Bairro', value: 'BAIRRO'},
            {label: 'CEP', value: 'CEP'},
            {label: 'E-mail', value: 'EMAILPESSOAL'},
            {label: 'Telefone principal', value: 'TELEFONEPRINCIPAL'},
            {label: 'Telefone opcional', value: 'TELEFONEOPCIONAL'}
        ];

        $scope.pen.beneficio = [
            {label: 'Primeiro pagamento', value: 'PRIMEIROPAGAMENTO'},
            {label: 'Tipo de rateio', value: 'TIPORATEIO'},
            {label: 'Tipo de pensão', value: 'TIPOPENSAO'},
            {label: 'Com paridade', value: 'COMPARIDADE'},
            {label: 'Nº do Processo', value: 'NUMPROCESSO'},
            {label: 'Fim benefício', value: 'FIMBENEFICIO'},
            {label: 'Limite retroativo', value: 'LIMITERETROATIVO'},
            {label: 'Tipo de cota', value: 'TIPOCOTA'},
            {label: 'Nº reserva', value: 'NUMRESERVA'}
        ];

        $scope.pen.contaCredito = [
            {label: 'Banco', value: 'BANCO'},
            {label: 'Agência', value: 'AGENCIA'},
            {label: 'Tipo de conta', value: 'TIPOCONTA'},
            {label: 'Nº da conta', value: 'NUMCONTA'},
            {label: 'Digito', value: 'DIGITO'},
            {label: 'Operação', value: 'OPERACAO'}
        ];

        $scope.pen.isencao = [
            {label: 'Condições', value: 'CONDICOES'},
            {label: 'Data inicial', value: 'DATAINICIAL'},
            {label: 'Data final', value: 'DATAFINAL'}
        ];

        $scope.toggle = function (item, list) {
            var idx = list.indexOf(item);
            if (idx > -1) {
              list.splice(idx, 1);
            }
            else {
              list.push(item);
            }
          };
        
        $scope.exists = function (item, list) {
            return list.indexOf(item) > -1;
        };

        $scope.selectOrToggle = function (valor){
            if($scope.filtro.situacaoFuncional) {
                if($scope.filtro.situacaoFuncional == 'APOSENTADO'){
                    switch (valor) {
                        case 'DADOSPESSOAIS':
                            selectedDadosPessoais();
                            break;
                        case 'DADOSPROFISSIONAIS':
                            selectedDadosProfissionais();
                            break;
                        case 'DOCSPESSOAIS':
                            selectedDocsPessoais();
                            break;
                        case 'DOCSNOMEACAO':
                            selectedDocsNomeacao();
                            break;
                        case 'CONTATOENDERECO':
                            selectedContatoEndereco();
                            break;
                        case 'BANCOPESSOAL':
                            selectedDadosBancoPessoal();
                            break;
                        case 'DADOSBANCARIOS':
                            selectedDadosBancarios();
                            break;
                    }
                } else {
                    switch (valor) {
                        case 'DADOSPESSOAL':
                            selectedPenDadosPessoal();
                            break;
                        case 'DADOSEXSEGURADO':
                            selectedPenDadosExSegurado();
                            break;
                        case 'DEPENDENTES':
                            selectedPenDadosDependente();
                            break;
                        case 'DOCUMENTOS':
                            selectedPenDocsPessoal();
                            break;
                        case 'CONTATOENDERECOPENSIONISTA':
                            selectedPenContatoEndereco();
                            break;
                        case 'BENEFICIO':
                            selectedPenDadosBeneficio();
                            break;
                        case 'CONTACREDITO':
                            selectedPenContaCredito();
                            break;
                        case 'ISENCAO':
                            selectedPenIsencao();
                            break;
                    }
                }
            }
        }

        function selectedDadosPessoais() {
            if ($scope.apo.selectedDadosPessoais.length === $scope.apo.dadosPessoais.length) {
                $scope.apo.selectedDadosPessoais = [];
              } else if ($scope.apo.selectedDadosPessoais.length === 0 || $scope.apo.selectedDadosPessoais.length > 0) {
                $scope.apo.selectedDadosPessoais = [];
                $scope.apo.dadosPessoais.forEach(a => {
                    $scope.apo.selectedDadosPessoais.push(a.value);
                });
              }
        }

        function selectedDadosProfissionais() {
            if ($scope.apo.selectedDadosProfissionais.length === $scope.apo.dadosProfissionais.length) {
                $scope.apo.selectedDadosProfissionais = [];
              } else if ($scope.apo.selectedDadosProfissionais.length === 0 || $scope.apo.selectedDadosProfissionais.length > 0) {
                $scope.apo.selectedDadosProfissionais = [];
                $scope.apo.dadosProfissionais.forEach(a => {
                    $scope.apo.selectedDadosProfissionais.push(a.value);
                });
              }
        }

        function selectedDocsPessoais() {
            if ($scope.apo.selectedDocsPessoais.length === $scope.apo.docsPessoais.length) {
                $scope.apo.selectedDocsPessoais = [];
              } else if ($scope.apo.selectedDocsPessoais.length === 0 || $scope.apo.selectedDocsPessoais.length > 0) {
                $scope.apo.selectedDocsPessoais = [];
                $scope.apo.docsPessoais.forEach(a => {
                    $scope.apo.selectedDocsPessoais.push(a.value);
                });
              }
        }

        function selectedDocsNomeacao() {
            if ($scope.apo.selectedDocsNomeacao.length === $scope.apo.docsNomeacao.length) {
                $scope.apo.selectedDocsNomeacao = [];
              } else if ($scope.apo.selectedDocsNomeacao.length === 0 || $scope.apo.selectedDocsNomeacao.length > 0) {
                $scope.apo.selectedDocsNomeacao = [];
                $scope.apo.docsNomeacao.forEach(a => {
                    $scope.apo.selectedDocsNomeacao.push(a.value);
                });
              }
        }

        function selectedContatoEndereco() {
            if ($scope.apo.selectedContatoEndereco.length === $scope.apo.contatoEndereco.length) {
                $scope.apo.selectedContatoEndereco = [];
              } else if ($scope.apo.selectedContatoEndereco.length === 0 || $scope.apo.selectedContatoEndereco.length > 0) {
                $scope.apo.selectedContatoEndereco = [];
                $scope.apo.contatoEndereco.forEach(a => {
                    $scope.apo.selectedContatoEndereco.push(a.value);
                });
              }
        }

        function selectedDadosBancoPessoal() {
            if ($scope.apo.selectedDadosBancoPessoal.length === $scope.apo.dadosBancoPessoal.length) {
                $scope.apo.selectedDadosBancoPessoal = [];
              } else if ($scope.apo.selectedDadosBancoPessoal.length === 0 || $scope.apo.selectedDadosBancoPessoal.length > 0) {
                $scope.apo.selectedDadosBancoPessoal = [];
                $scope.apo.dadosBancoPessoal.forEach(a => {
                    $scope.apo.selectedDadosBancoPessoal.push(a.value);
                });
              }
        }

        function selectedDadosBancarios() {
            if ($scope.apo.selectedDadosBancarios.length === $scope.apo.dadosBancarios.length) {
                $scope.apo.selectedDadosBancarios = [];
              } else if ($scope.apo.selectedDadosBancarios.length === 0 || $scope.apo.selectedDadosBancarios.length > 0) {
                $scope.apo.selectedDadosBancarios = [];
                $scope.apo.dadosBancarios.forEach(a => {
                    $scope.apo.selectedDadosBancarios.push(a.value);
                });
              }
        }

        //Pensionista

        function selectedPenDadosPessoal() {
            if ($scope.pen.selectedDadosPessoais.length === $scope.pen.dadosPessoais.length) {
                $scope.pen.selectedDadosPessoais = [];
              } else if ($scope.pen.selectedDadosPessoais.length === 0 || $scope.pen.selectedDadosPessoais.length > 0) {
                $scope.pen.selectedDadosPessoais = [];
                $scope.pen.dadosPessoais.forEach(a => {
                    $scope.pen.selectedDadosPessoais.push(a.value);
                });
              }
        }

        function selectedPenDadosExSegurado() {
            if ($scope.pen.selectedDadosExSegurado.length === $scope.pen.dadosExSegurado.length) {
                $scope.pen.selectedDadosExSegurado = [];
              } else if ($scope.pen.selectedDadosExSegurado.length === 0 || $scope.pen.selectedDadosExSegurado.length > 0) {
                $scope.pen.selectedDadosExSegurado = [];
                $scope.pen.dadosExSegurado.forEach(a => {
                    $scope.pen.selectedDadosExSegurado.push(a.value);
                });
              }
        }

        function selectedPenDadosDependente() {
            if ($scope.pen.selectedDadosDependente.length === $scope.pen.dadosDependente.length) {
                $scope.pen.selectedDadosDependente = [];
              } else if ($scope.pen.selectedDadosDependente.length === 0 || $scope.pen.selectedDadosDependente.length > 0) {
                $scope.pen.selectedDadosDependente = [];
                $scope.pen.dadosDependente.forEach(a => {
                    $scope.pen.selectedDadosDependente.push(a.value);
                });
              }
        }

        function selectedPenDocsPessoal() {
            if ($scope.pen.selectedDocsPessoal.length === $scope.pen.docsPessoal.length) {
                $scope.pen.selectedDocsPessoal = [];
              } else if ($scope.pen.selectedDocsPessoal.length === 0 || $scope.pen.selectedDocsPessoal.length > 0) {
                $scope.pen.selectedDocsPessoal = [];
                $scope.pen.docsPessoal.forEach(a => {
                    $scope.pen.selectedDocsPessoal.push(a.value);
                });
              }
        }

        function selectedPenContatoEndereco() {
            if ($scope.pen.selectedContatoEndereco.length === $scope.pen.contatoEndereco.length) {
                $scope.pen.selectedContatoEndereco = [];
              } else if ($scope.pen.selectedContatoEndereco.length === 0 || $scope.pen.selectedContatoEndereco.length > 0) {
                $scope.pen.selectedContatoEndereco = [];
                $scope.pen.contatoEndereco.forEach(a => {
                    $scope.pen.selectedContatoEndereco.push(a.value);
                });
              }
        }

        function selectedPenDadosBeneficio() {
            if ($scope.pen.selectedDadosBeneficio.length === $scope.pen.beneficio.length) {
                $scope.pen.selectedDadosBeneficio = [];
              } else if ($scope.pen.selectedDadosBeneficio.length === 0 || $scope.pen.selectedDadosBeneficio.length > 0) {
                $scope.pen.selectedDadosBeneficio = [];
                $scope.pen.beneficio.forEach(a => {
                    $scope.pen.selectedDadosBeneficio.push(a.value);
                });
              }
        }

        function selectedPenContaCredito() {
            if ($scope.pen.selectedDadosContaCredito.length === $scope.pen.contaCredito.length) {
                $scope.pen.selectedDadosContaCredito = [];
              } else if ($scope.pen.selectedDadosContaCredito.length === 0 || $scope.pen.selectedDadosContaCredito.length > 0) {
                $scope.pen.selectedDadosContaCredito = [];
                $scope.pen.contaCredito.forEach(a => {
                    $scope.pen.selectedDadosContaCredito.push(a.value);
                });
              }
        }

        function selectedPenIsencao() {
            if ($scope.pen.selectedDadosIsencao.length === $scope.pen.isencao.length) {
                $scope.pen.selectedDadosIsencao = [];
              } else if ($scope.pen.selectedDadosIsencao.length === 0 || $scope.pen.selectedDadosIsencao.length > 0) {
                $scope.pen.selectedDadosIsencao = [];
                $scope.pen.isencao.forEach(a => {
                    $scope.pen.selectedDadosIsencao.push(a.value);
                });
              }
        }

        $scope.iconeOrLabel = function(valor, icone) {

            switch (valor) {
                case 'DADOSPESSOAIS':
                    return getIconeOrLabel(icone, ($scope.apo.selectedDadosPessoais.length === $scope.apo.dadosPessoais.length));
                case 'DADOSPROFISSIONAIS':
                    return getIconeOrLabel(icone, ($scope.apo.selectedDadosProfissionais.length === $scope.apo.dadosProfissionais.length));
                case 'DOCSPESSOAIS':
                    return getIconeOrLabel(icone, ($scope.apo.selectedDocsPessoais.length === $scope.apo.docsPessoais.length));
                case 'DOCSNOMEACAO':
                    return getIconeOrLabel(icone, ($scope.apo.selectedDocsNomeacao.length === $scope.apo.docsNomeacao.length));
                case 'CONTATOENDERECO':
                    return getIconeOrLabel(icone, ($scope.apo.selectedContatoEndereco.length === $scope.apo.contatoEndereco.length));
                case 'BANCOPESSOAL':
                    return getIconeOrLabel(icone, ($scope.apo.selectedDadosBancoPessoal.length === $scope.apo.dadosBancoPessoal.length));
                case 'DADOSBANCARIOS':
                    return getIconeOrLabel(icone, ($scope.apo.selectedDadosBancarios.length === $scope.apo.dadosBancarios.length));
                case 'DADOSPESSOAL':
                    return getIconeOrLabel(icone, ($scope.pen.selectedDadosPessoais.length === $scope.pen.dadosPessoais.length));
                case 'DADOSEXSEGURADO':
                    return getIconeOrLabel(icone, ($scope.pen.selectedDadosExSegurado.length === $scope.pen.dadosExSegurado.length));
                case 'DEPENDENTES':
                    return getIconeOrLabel(icone, ($scope.pen.selectedDadosDependente.length === $scope.pen.dadosDependente.length));
                case 'DOCUMENTOS':
                    return getIconeOrLabel(icone, ($scope.pen.selectedDocsPessoal.length === $scope.pen.docsPessoal.length));
                case 'CONTATOENDERECOPENSIONISTA':
                    return getIconeOrLabel(icone, ($scope.pen.selectedContatoEndereco.length === $scope.pen.contatoEndereco.length));
                case 'BENEFICIO':
                    return getIconeOrLabel(icone, ($scope.pen.selectedDadosBeneficio.length === $scope.pen.beneficio.length));
                case 'CONTACREDITO':
                    return getIconeOrLabel(icone, ($scope.pen.selectedDadosContaCredito.length === $scope.pen.contaCredito.length));
                case 'ISENCAO':
                    return getIconeOrLabel(icone, ($scope.pen.selectedDadosIsencao.length === $scope.pen.isencao.length));
            }
        }

        function getIconeOrLabel(icone, desmarcar) {
            return desmarcar ? (icone ? 'fa fa-square' : 'Desmarcar todos') : (icone ? 'fa fa-check-square' : 'Selecionar todos');
        };

        $scope.$watch("filtro.situacaoFuncional",function(newValue,oldValue){
            if($scope.filtro.situacaoFuncional && $scope.filtro.situacaoFuncional != "PENSIONISTA"){
                delete $scope.filtro.status;
            }
        });

        $scope.showSimpleToast = function (textContent) {
			$mdToast.show(
				$mdToast.simple()
					.textContent(textContent)
					.position('top right')
					.hideDelay(3000)
			);
        };
        
        $scope.gerarRelatorio = function () {
            if(podeGerarRelatorio()){
                imprimirPdf();
            } else {
                $scope.showSimpleToast("Selecione algum item para customização do relatório");
            }
        }

        function podeGerarRelatorio(){
            if($scope.filtro.situacaoFuncional == 'APOSENTADO'){
                if($scope.apo.selectedDadosPessoais.length > 0 || $scope.apo.selectedDadosProfissionais.length > 0 || $scope.apo.selectedDocsPessoais.length > 0 
                    || $scope.apo.selectedDocsNomeacao.length > 0 || $scope.apo.selectedContatoEndereco.length > 0 || $scope.apo.selectedDadosBancoPessoal.length > 0 
                    || $scope.apo.selectedDadosBancarios.length > 0) {
                    return true;
                }
            }
            if($scope.filtro.situacaoFuncional == 'PENSIONISTA'){
                if($scope.pen.selectedDadosPessoais.length > 0 || $scope.pen.selectedDadosExSegurado.length > 0 || $scope.pen.selectedDadosDependente.length > 0 
                    || $scope.pen.selectedDocsPessoal.length > 0 || $scope.pen.selectedContatoEndereco.length > 0 || $scope.pen.selectedDadosBeneficio.length > 0 
                    || $scope.pen.selectedDadosContaCredito.length > 0 || $scope.pen.selectedDadosIsencao.length > 0){
                    return true;
                }
            }

            return false;
        }

        function imprimirPdf() {
            showCarregandoDialog();

            var config = {
                params: {},
                responseType: 'arraybuffer'
            };

            config.params.filial = $scope.filtro.filial;
            config.params.lotacoesId = $scope.filtro.lotacoes;
            config.params.situacao = $scope.filtro.situacaoFuncional;
            config.params.status = $scope.filtro.status;
            
            if($scope.filtro.situacaoFuncional == 'APOSENTADO'){
                if($scope.apo.selectedDadosPessoais.length > 0){
                    config.params.atributosDadoPessoalAposentado = $scope.apo.selectedDadosPessoais;
                }
                if($scope.apo.selectedDadosProfissionais.length > 0){
                    config.params.atributosDadoProfissionalAposentado = $scope.apo.selectedDadosProfissionais;
                }
    
                if($scope.apo.selectedDocsPessoais.length > 0){
                    config.params.atributosDocPessoalAposentado = $scope.apo.selectedDocsPessoais;
                }
    
                if($scope.apo.selectedDocsNomeacao.length > 0){
                    config.params.atributosDocNomeacaoAposentado = $scope.apo.selectedDocsNomeacao;
                }
    
                if($scope.apo.selectedContatoEndereco.length > 0){
                    config.params.atributosContatoEnderecoAposentado = $scope.apo.selectedContatoEndereco;
                }
    
                if($scope.apo.selectedDadosBancoPessoal.length > 0){
                    config.params.atributosBancoPessoalAposentado = $scope.apo.selectedDadosBancoPessoal;
                }
                
                if($scope.apo.selectedDadosBancarios.length > 0){
                    config.params.atributosDadoBancarioAposentado = $scope.apo.selectedDadosBancarios;
                }
            }

            //Dados do Pensionista
            if($scope.filtro.situacaoFuncional == 'PENSIONISTA'){
                if($scope.pen.selectedDadosPessoais.length > 0){
                    config.params.atributosDadoPessoalPensionista = $scope.pen.selectedDadosPessoais;
                }
    
                if($scope.pen.selectedDadosExSegurado.length > 0){
                    config.params.atributosDadoExSeguradoPensionista = $scope.pen.selectedDadosExSegurado;
                }
    
                if($scope.pen.selectedDadosDependente.length > 0){
                    config.params.atributosDadoDependentePensionista = $scope.pen.selectedDadosDependente;
                }
    
                if($scope.pen.selectedDocsPessoal.length > 0){
                    config.params.atributosDocPessoalPensionista = $scope.pen.selectedDocsPessoal;
                }
    
                if($scope.pen.selectedContatoEndereco.length > 0){
                    config.params.atributosContatoEnderecoPensionista = $scope.pen.selectedContatoEndereco;
                }
    
                if($scope.pen.selectedDadosBeneficio.length > 0){
                    config.params.atributosDadoBeneficioPensionista = $scope.pen.selectedDadosBeneficio;
                }
    
                if($scope.pen.selectedDadosContaCredito.length > 0){
                    config.params.atributosDadoContaCreditoPensionista = $scope.pen.selectedDadosContaCredito;
                }
    
                if($scope.pen.selectedDadosIsencao.length > 0){
                    config.params.atributosDadoIsencaoPensionista = $scope.pen.selectedDadosIsencao;
                }
            }

	        RestService.Get('/relatorioDadosAposentadoPensionista/relatorio/pdf', config)
                .then(function successCallback(response) {
                    $rootScope.$broadcast('preloader:hide');
                    var file = new Blob([response.data], { type: 'application/pdf' });
                    var fileURL = URL.createObjectURL(file);
                    $scope.cancel();
                    $timeout(function(){window.open(fileURL);}, 1000);
                }, function errorCallback(response) {
                    $rootScope.$broadcast('preloader:hide');
                    if (response.status === 400) {
                        $scope.showSimpleToast("Não foi possível carregar os dados.")
                    }
                    $scope.cancel();
                });

        }

        function showCarregandoDialog() {
			var useFullScreen = ($mdMedia('sm') || $mdMedia('xs'));

            $mdDialog.show({
                require: 'relatorioAposentadoPensionistaCtrl',
                scope: $scope,
                preserveScope: true,
                controller: function () {
					$scope.cancel = function () {
                        $mdDialog.cancel();
                    };
                },
                templateUrl: 'app/layout/dialogCarregando.html',
                parent: angular.element(document.body),
                clickOutsideToClose: false,
                fullscreen: useFullScreen
            });
		}

        /*
         * Recebe data e converte para valor com hora ou não
         * 
         * param data - recebe uma data tipo LocalDate
         * param comHora - recebe um valor boolean
         * 
         * */
		$scope.convertDate = function(data, comHora) {
			var valor = moment(data);
			if (comHora) {
				return $filter('date')(new Date(valor), 'dd/MM/yyyy - HH:mm');
			} else {
				return $filter('date')(new Date(valor), 'dd/MM/yyyy');
			}
		}


    }

})();
