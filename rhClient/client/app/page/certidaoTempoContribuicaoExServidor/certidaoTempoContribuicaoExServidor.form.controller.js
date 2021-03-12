(function () {
    'use strict';

    angular.module('app.page')
        .controller('certidaoTempoContribuicaoExServidorFormCtrl', ['$scope', '$mdToast', '$q', '$location', 'RestService', 'configValue', '$state', '$rootScope', 'GenericoService', 'EnumService', 'FileUploader', '$http', '$filter', '$mdDialog', '$mdMedia', '$interval', certidaoTempoContribuicaoExServidorFormCtrl]);

    function certidaoTempoContribuicaoExServidorFormCtrl($scope, $mdToast, $q, $location, RestService, configValue, $state, $rootScope, GenericoService, EnumService, FileUploader, $http, $filter, $mdDialog, $mdMedia, $interval) {

        $scope.detalhes = false;
        $scope.retificar = false;

        if ($state.params && $state.params.detalhes) {
            $scope.detalhes = true;
        };

        if ($state.params && $state.params.retificar) {
            $scope.retificar = true;
        };

        // Checa as regras de acesso da tela
        RestService.Get('/usuario/verificaPermissao/por/menu', { params: { menu: 'CTC - Ex-Servidor' } }).then(
            function (response) {
                if (response.status === 200 && response.data) {

                    $scope.usuarioAdm = response.data.usuarioAdm;
                    $scope.podeCadastrar = response.data.usuarioAdm ? true : response.data.podeCadastrar;
                    $scope.podeAtualizar = response.data.usuarioAdm ? true : response.data.podeAtualizar;
                    $scope.podeVisualizar = response.data.usuarioAdm ? true : response.data.podeVisualizar;
                    $scope.podeExcluir = response.data.usuarioAdm ? true : response.data.podeExcluir;

                    if ($scope.detalhes) {
                        if (!$scope.usuarioAdm && !$scope.podeCadastrar && !$scope.podeAtualizar && !$scope.podeVisualizar && !$scope.podeExcluir)
                            $location.path('page/403');
                    } else {
                        if ($state.params && $state.params.id) {
                            if (!$scope.usuarioAdm && !$scope.podeAtualizar)
                                $location.path('page/403');
                        } else {
                            if (!$scope.usuarioAdm && !$scope.podeCadastrar)
                                $location.path('page/403');
                        }
                    }
                }
            }, function errorCallback(response) {
                if (response.status === 400) {
                    $scope.showSimpleToast(response.data.message);
                }
            });

        /*Início - Variáveis de apoio*/
        $scope.frequencia = {};
        $scope.cesp = {};
        $scope.ff = {};
        $scope.lsv = {};
        $scope.lsd = {};
        $scope.sus = {};
        $scope.disp = {};
        $scope.outro = {};
        $scope.tei = {};
        $scope.tei.tipoTempoEspecial = {};
        $scope.lista = {};
        $scope.lista.anexos = [];
        $scope.lista.anosFrequencia = [];
        $scope.lista.tempoBruto = [];
        $scope.lista.graus = [];
        $scope.lista.uf = [];
        $scope.lista.municipio = [];
        $scope.salvarComoRascunho = true;
        $scope.naoRascunho = false;
        $scope.podeSalvarRascunho = false;
        $scope.contaLoop = 0;
        $scope.bloqueiaCampoGrau = true;

        /*Final - Variáveis de apoio*/

        $scope.ctc = {
            statusSituacaoCertidao: 'RASCUNHO',
            periodos: [],
            frequencias: [],
            detalhamentosFrequencia: [],
            assinaturas: [],
            tempoEspecial: [],
            relacaoRemuneracoes: [],

            tempoEspecial: [],

            cargos: [],

            orgaosLotacao: [],
            rascunho: true,
        };

        $scope.list = {
            tiposFuncao: [],
            tiposTempoEspecial: [],
            graus: [],
        };

        function initTotalTempoLiquidoFrequencia(faltas, licencas, licencasSemVenc, suspensoes, disponibilidade, outros) {
            $scope.total = {
                faltas: faltas,
                licencas: licencas,
                licencasSemVenc: licencasSemVenc,
                suspensoes: suspensoes,
                disponibilidade: disponibilidade,
                outros: outros,
            };
        };

        initTotalTempoLiquidoFrequencia(0, 0, 0, 0, 0, 0);

        $scope.getUnidadesFederativas = function () {
            $rootScope.$broadcast('preloader:active');
            GenericoService.GetAllDropdown('listaUfs', function (response) {
                if (response.status === 200) {
                    $scope.lista.uf = response.data;
                    $rootScope.$broadcast('preloader:hide');
                } else if (response.status === 500) {
                    $scope.showSimpleToast("Falha no carregamento de dados, favor contate o administrador do sistema");
                }
            });
        }

        $scope.getMunicipiosByUf = function (id) {
            GenericoService.GetAllDropdownById(id, function (response) {
                if (response.status === 200) {
                    $scope.lista.municipio = response.data;
                } else if (response.status === 500) {
                    $scope.showSimpleToast("Não existem municípios cadastrados para este estado");
                }
            });
        }

        $scope.relacaoRemuneracoes = {
            colunas: ['ANO', 'JANEIRO', 'FEVEREIRO', 'MARÇO', 'ABRIL', 'MAIO', 'JUNHO', 'JULHO', 'AGOSTO', 'SETEMBRO', 'OUTUBRO', 'NOVEMBRO', 'DEZEMBRO', '13º',],
            linhas: []
        };

        /*
         * Retorna para a listagem
         * 
         * */
        $scope.goBack = function () {
            $location.path('/certidaoTempoContribuicaoExServidor/gestao');
        }

        $scope.getMapKeyByIndex = function (obj, idx) {
            return Object.keys(obj)[idx];
        }

        $scope.handleGrau = function (value) {
            if (value != 'EXERCIDO_CONDICAO_DEFICIENCIA') {
                $scope.bloqueiaCampoGrau = true;
                delete $scope.tei.grau;
            } else {
                $scope.bloqueiaCampoGrau = false;
            }
        }

        $scope.querySearchAssinatura = function (query) {

            var deferred = $q.defer();
            var config = { params: { search: query } }
            if (query) {
                if (query.length > 2) {
                    GenericoService.GetAll('/funcionario/searchComplete', config).then(
                        function (response) {
                            if (response.data && response.data.length > 0) {
                                deferred.resolve(response.data);
                            } else {
                                $scope.showSimpleToast("Nenhum registro encontrado");
                            }
                        });
                }
            }
            return deferred.promise;
        };

        $scope.querySearchFuncionario = function (queryFuncionario, queryMatricula, queryCPF, queryPis) {

            var deferred = $q.defer();
            var config = { params: { searchFuncionario: queryFuncionario, searchMatricula: queryMatricula, searchCPF: queryCPF, searchPis: queryPis } }
            if (queryFuncionario || queryMatricula || queryCPF || queryPis) {
                if (queryFuncionario.length > 2 || queryMatricula.length > 0 || queryCPF.length > 2 || queryPis.length > 2) {
                    GenericoService.GetAll('/funcionarioExonerado/searchComplete', config).then(
                        function (response) {
                            if (response.data && response.data.length > 0) {
                                deferred.resolve(response.data);
                            } else {
                                $scope.showSimpleToast("Nenhum registro encontrado");
                            }
                        });
                }
            }
            return deferred.promise;
        };

        $scope.funcionarioSelecionado = function () {
            if ($scope.funcionario) {
                if ($scope.ctc.funcionarioId != $scope.funcionario.id) {
                    $scope.rgFuncionario = $scope.funcionario.identidade;
                    $scope.cpfFuncionario = $scope.funcionario.cpf;
                    $scope.matriculaFuncionario = $scope.funcionario.matricula;
                    $scope.sexoFuncionario = $scope.funcionario.sexo;
                    $scope.situacaoFuncionalFuncionario = $scope.funcionario.situacaoFuncional;
                    $scope.pisPasepFuncionario = $scope.funcionario.pisPasep;
                    $scope.dtNascimentoFuncionario = moment($scope.funcionario.dataNascimento);
                    $scope.empresaFilialFuncionario = $scope.funcionario.empresa.nomeFilial;;
                    $scope.orgaoFuncionario = $scope.funcionario.lotacao;
                    $scope.enderecoFuncionario = $scope.handleEndereco();
                    $scope.filiacaoFuncionario = $scope.handleFiliacao();
                    $scope.dtAdmissaoFuncionario = moment($scope.funcionario.dataAdmissao);

                    $scope.ctc.funcionarioId = $scope.funcionario.id;
                    $scope.ctc.lotacaoId = $scope.funcionario.lotacaoId;

                    $scope.getUltimoHistoricoSituacaoFuncional($scope.funcionario.id);

                    salvarAutomatico();
                }
            }
        };

        $scope.getUltimoHistoricoSituacaoFuncional = function (funcionarioId) {
            $rootScope.$broadcast('preloader:active');
            GenericoService.GetById('/certidaoExServidor/funcionarioHistorico/' + funcionarioId, function (response) {
                if (response.status === 200) {
                    $scope.historicoSituacaoFuncional = response.data;
                    $scope.ctc.dataExoneracao = moment($scope.historicoSituacaoFuncional.dataDiarioOficial);
                    $rootScope.$broadcast('preloader:hide');
                } else if (response.status === 500) {
                    $scope.showSimpleToast("Falha no carregamento de dados, favor contate o administrador do sistema");
                }
            });
        }

        $scope.handleEndereco = function () {
            let fullAdress = '';

            if ($scope.funcionario.logradouro) {
                fullAdress += $scope.funcionario.logradouro;
            }
            if ($scope.funcionario.numero) {
                fullAdress += ', ' + $scope.funcionario.numero;
            }
            if ($scope.funcionario.bairro) {
                fullAdress += ' - ' + $scope.funcionario.bairro;
            }

            return fullAdress;
        }

        $scope.handleFiliacao = function () {
            let filiacao = '';

            if ($scope.funcionario.nomeMae && $scope.funcionario.nomePai) {
                filiacao = $scope.funcionario.nomeMae + " e " + $scope.funcionario.nomePai;
            } else if ($scope.funcionario.nomeMae) {
                filiacao = $scope.funcionario.nomeMae;
            } else {
                filiacao = $scope.funcionario.nomePai;
            }

            return filiacao;
        }

        $scope.getListaAnosFrequencia = function () {
            $scope.lista.anosFrequencia = [];
            if ($scope.relacaoRemuneracoes.linhas.length > 0) {
                angular.forEach($scope.relacaoRemuneracoes.linhas, function (e) {
                    $scope.lista.anosFrequencia.push(e.ano);
                });
            }
            if ($scope.lista.anosFrequencia.length > 0) {
                return $filter('orderBy')($scope.lista.anosFrequencia);
            } else {
                return $scope.lista.anosFrequencia;
            }
        }

        $scope.validaDetalhamentoFrequência = function () {
            $scope.validaDetalhamento = {};
            $scope.validaDetalhamento.faltas = false;
            $scope.validaDetalhamento.licencas = false;
            $scope.validaDetalhamento.licencasSemVenc = false;
            $scope.validaDetalhamento.suspensoes = false;
            $scope.validaDetalhamento.disponibilidade = false;
            $scope.validaDetalhamento.outros = false;

            angular.forEach($scope.ctc.frequencias, function (e) {
                if (e.faltas > 0) {
                    $scope.validaDetalhamento.faltas = true;
                }

                if (e.licencas > 0) {
                    $scope.validaDetalhamento.licencas = true;
                }

                if (e.licencasSemVenc > 0) {
                    $scope.validaDetalhamento.licencasSemVenc = true;
                }

                if (e.suspensoes > 0) {
                    $scope.validaDetalhamento.suspensoes = true;
                }

                if (e.disponibilidade > 0) {
                    $scope.validaDetalhamento.disponibilidade = true;
                }

                if (e.outros > 0) {
                    $scope.validaDetalhamento.outros = true;
                }

            });

            calcularTempoLiquidoFrequencias();
        }

        function getTempoDetalhamento(valor) {
            let soma = 0
            if ($scope.ctc.detalhamentosFrequencia.length > 0) {
                angular.forEach($scope.ctc.detalhamentosFrequencia, function (e) {
                    if (e.tipo == valor) {
                        soma += e.tempo;
                    }
                });
            }

            return soma;
        };

        $scope.somaFaltas = function () {
            return getTempoDetalhamento('FALTA');
        };

        $scope.somaLicencas = function () {
            return getTempoDetalhamento('LICENCA_SEM_DEDUCAO');
        }

        $scope.somaLicencasSemVenc = function () {
            return getTempoDetalhamento('LICENCA_SEM_VENCIMENTO');
        }

        $scope.somaSuspensoes = function () {
            return getTempoDetalhamento('SUSPENSAO');
        }

        $scope.somaDisponibilidade = function () {
            return getTempoDetalhamento('DISPONIBILIDADE');
        }

        $scope.somaOutros = function () {
            return getTempoDetalhamento('OUTROS');
        }

        $scope.calculaLimite = function (tamanho) {
            var valor = Math.ceil(tamanho / 5);
            return valor;
        }

        function calculoAnosRemuneracao(remove) {
            if (remove) {
                $scope.relacaoRemuneracoes.linhas = [];
            }

            angular.forEach($scope.ctc.periodos, function (e) {
                var mesInicio = moment(e.periodoInicio).month();
                var anoInicio = moment(e.periodoInicio).year();
                var mesFim = moment(e.periodoFinal).month();
                var anoFim = moment(e.periodoFinal).year();

                let zeraMes = false;

                for (var i = anoInicio; i <= anoFim; i++) {

                    if (!$scope.validaAnoRelacaoRemuneracoes(i)) {

                        let obj = {
                            ano: i,
                            janeiro: null, fevereiro: null, marco: null, abril: null, maio: null,
                            junho: null, julho: null, agosto: null, setembro: null,
                            outubro: null, novembro: null, dezembro: null, decimoTerceiro: 0
                        };

                        if (zeraMes) {
                            mesInicio = 0;
                        }
                        if (i != anoFim) {
                            for (var j = mesInicio; j <= 11; j++) {
                                obj[$scope.getMapKeyByIndex(obj, j + 1)] = 0;
                            }
                            zeraMes = true;
                        } else {
                            for (var k = mesInicio; k <= mesFim; k++) {
                                obj[$scope.getMapKeyByIndex(obj, k + 1)] = 0;
                            }
                        }
                        $scope.relacaoRemuneracoes.linhas.push(obj);
                    } else {
                        angular.forEach($scope.relacaoRemuneracoes.linhas, function (f) {
                            if (f.ano == i) {
                                if (zeraMes) {
                                    mesInicio = 0;
                                }
                                if (i != anoFim) {
                                    for (var r = mesInicio; r <= 11; r++) {
                                        if (f[$scope.getMapKeyByIndex(f, r + 1)] == null) {
                                            f[$scope.getMapKeyByIndex(f, r + 1)] = 0;
                                        }
                                    }
                                    zeraMes = true;
                                } else {
                                    for (var o = mesInicio; o <= mesFim; o++) {
                                        if (f[$scope.getMapKeyByIndex(f, o + 1)] == null) {
                                            f[$scope.getMapKeyByIndex(f, o + 1)] = 0;
                                        }
                                    }
                                }
                            }
                        });
                    }
                }
            });
        }


        $scope.validaAnoRelacaoRemuneracoes = function (ano) {
            var existe = false;
            angular.forEach($scope.relacaoRemuneracoes.linhas, function (e) {
                if (e.ano == ano) {
                    existe = true;
                }
            });

            return existe;
        }

        $scope.validaCamposAssinatura = function () {
            if (!$scope.assinatura) {
                $scope.showSimpleToast("Por favor informe um funcionário");
                return false;
            }

            if (!$scope.tipoFuncao) {
                $scope.showSimpleToast("Por favor informe uma função");
                return false;
            }

            return true;
        }

        $scope.existeAssinatura = function (aba) {
            var existe = false;

            angular.forEach($scope.ctc.assinaturas, function (e) {
                if (!existe) {
                    if (e.abaAssinaturaCertidao == aba) {
                        if (e.funcionarioId == $scope.assinatura.id) {
                            $scope.showSimpleToast("Este funcionário já foi adicionado");
                            existe = true;
                        } else if (e.funcaoAssinaturaCertidao == $scope.tipoFuncao) {
                            $scope.showSimpleToast("Esta função já foi adicionada");
                            existe = true;
                        }
                    }
                }
            });

            return existe;
        }

        $scope.removerAssinatura = function (item) {
            var index = $scope.ctc.assinaturas.indexOf(item);
            $scope.ctc.assinaturas.splice(index, 1);
            salvarAutomatico();
        }

        $scope.filtrarAbaCertidao = function (item) {
            return item.abaAssinaturaCertidao == 'CERTIDAO';
        };

        $scope.filtrarAbaRelacao = function (item) {
            return item.abaAssinaturaCertidao == 'RELACAO';
        };

        $scope.filtrarAbaFrequencia = function (item) {
            return item.abaAssinaturaCertidao == 'DETALHAMENTO';
        };

        function adicionarAssinatura(aba) {
            if ($scope.validaCamposAssinatura()) {
                var existe = $scope.existeAssinatura(aba);
                if (!existe) {
                    let payload = {
                        funcionario: $scope.assinatura,
                        funcionarioId: $scope.assinatura.id,
                        funcaoAssinaturaCertidao: $scope.tipoFuncao,
                        abaAssinaturaCertidao: aba
                    };

                    $scope.ctc.assinaturas.push(payload);

                    delete $scope.assinatura;
                    delete $scope.tipoFuncao;
                    salvarAutomatico();
                }
            }
        }

        $scope.adicionarAssinaturaDadosFuncionais = function () {
            adicionarAssinatura('CERTIDAO');
        }

        $scope.adicionarAssinaturaRelacaoRemuneracao = function () {
            adicionarAssinatura('RELACAO');
        }

        $scope.adicionarAssinaturaFrequencia = function () {
            adicionarAssinatura('DETALHAMENTO');
        }

        $scope.adicionarDetalhamentoPeriodoContribuido = function () {
            if (($scope.cesp.periodoInicio >= $scope.dtAdmissaoFuncionario) && ($scope.cesp.periodoFinal < $scope.ctc.dataExoneracao)) {
                if ($scope.cesp.aproveitamento) {
                    $scope.ctc.periodos.push($scope.cesp);
                    $scope.cesp = {};
                    calculoAnosRemuneracao(false);
                    salvarAutomatico();
                } else {
                    $scope.showSimpleToast("Aproveitamento é obrigatório");
                }
            } else {
                $scope.showSimpleToast("Contribuição não pertence ao período de adimissão e exoneração");
            }
        }

        $scope.removerDetalhamentoPeriodoContribuido = function (index) {
            $scope.ctc.periodos.splice(index, 1);
            calculoAnosRemuneracao(true);
            salvarAutomatico();
        }

        $scope.adicionarCargo = function () {

            if ($scope.cargoExercido) {
                let cargo = { descricaoCargo: $scope.cargoExercido };
                $scope.ctc.cargos.push(cargo);

                delete $scope.cargoExercido;

                salvarAutomatico();
            }

        }

        $scope.removerCargo = function (index) {
            $scope.ctc.cargos.splice(index, 1);
            salvarAutomatico();
        }

        $scope.adicionarOrgaoLotacao = function () {
            if ($scope.orgaoLotacao) {
                let orgao = { descricaoOrgaoLotacao: $scope.orgaoLotacao };
                $scope.ctc.orgaosLotacao.push(orgao);

                delete $scope.orgaoLotacao;

                salvarAutomatico();
            }

        }

        $scope.removerOrgaoLotacao = function (index) {
            $scope.ctc.orgaosLotacao.splice(index, 1);
            salvarAutomatico();
        }

        function isNumber(n) {
            return !isNaN(parseFloat(n)) && isFinite(n);
        }

        $scope.adicionarFrequencia = function () {
            if ($scope.ctc.periodos && $scope.ctc.periodos.length > 0 && $scope.frequencia.ano) {
                if (!$scope.validaAnoFrequencia($scope.frequencia.ano)) {
                    $scope.frequencia.tempoBruto = Number($scope.frequencia.tempoBruto | 0);
                    $scope.frequencia.faltas = Number($scope.frequencia.faltas | 0);
                    $scope.frequencia.licencas = Number($scope.frequencia.licencas | 0);
                    $scope.frequencia.licencasSemVenc = Number($scope.frequencia.licencasSemVenc | 0);
                    $scope.frequencia.suspensoes = Number($scope.frequencia.suspensoes | 0);
                    $scope.frequencia.disponibilidade = Number($scope.frequencia.disponibilidade | 0);
                    $scope.frequencia.outros = Number($scope.frequencia.outros | 0);

                    $scope.frequencia.tempoLiquido = $scope.getTempoLiquidoFrequencia($scope.frequencia);
                    $scope.ctc.frequencias.push($scope.frequencia);

                    $scope.validaDetalhamentoFrequência();

                    delete $scope.frequencia;

                    salvarAutomatico();

                    calcularTempoLiquidoFrequencias();
                } else {
                    $scope.showSimpleToast("O ano de " + $scope.frequencia.ano + " já foi adicionado.");
                }

            } else {
                if ($scope.ctc.periodos && $scope.ctc.periodos.length == 0) {
                    $scope.showSimpleToast("Nenhum período de contribuição adicionado");
                } else if (!$scope.frequencia.ano) {
                    $scope.showSimpleToast("Selecione um ano para a frequência");
                }
            }
        }

        function calcularTempoLiquidoFrequencias() {
            initTotalTempoLiquidoFrequencia(0, 0, 0, 0, 0, 0);
            if ($scope.ctc.frequencias.length > 0) {
                let faltas = 0;
                let licencas = 0;
                let licencasSemVenc = 0;
                let suspensoes = 0;
                let disponibilidade = 0;
                let outros = 0;
                angular.forEach($scope.ctc.frequencias, function (e) {
                    faltas += e.faltas;
                    licencas += e.licencas;
                    licencasSemVenc += e.licencasSemVenc;
                    suspensoes += e.suspensoes;
                    disponibilidade += e.disponibilidade;
                    outros += e.outros;
                });
                initTotalTempoLiquidoFrequencia(faltas, licencas, licencasSemVenc, suspensoes, disponibilidade, outros);
            }
        };

        $scope.validaAnoFrequencia = function (ano) {
            var existe = false;
            angular.forEach($scope.ctc.frequencias, function (e) {
                if (e.ano == ano) {
                    existe = true;
                }
            });

            return existe;
        };

        $scope.removerFrequencia = function (index) {
            $scope.ctc.frequencias.splice(index, 1);
            salvarAutomatico();
        };

        $scope.getTempoLiquidoFrequencia = function (obj) {
            var valor = Number(obj.tempoBruto | 0) - Number(obj.faltas | 0) - Number(obj.licencasSemVenc | 0) - Number(obj.suspensoes | 0) + Number(obj.disponibilidade | 0) + Number(obj.outros | 0);
            if (valor > 0) {
                return valor;
            } else {
                return 0;
            }
        };

        $scope.filtrarDetalhamentoFalta = function (item) {
            return item.tipo == 'FALTA';
        };

        $scope.filtrarDetalhamentoLincenca = function (item) {
            return item.tipo == 'LICENCA_SEM_VENCIMENTO';
        };

        $scope.filtrarDetalhamentoSuspensao = function (item) {
            return item.tipo == 'SUSPENSAO';
        };

        $scope.filtrarDetalhamentoDisponibilidade = function (item) {
            return item.tipo == 'DISPONIBILIDADE';
        };

        $scope.filtrarDetalhamentoOutros = function (item) {
            return item.tipo == 'OUTROS';
        };

        $scope.filtrarDetalhamentoLicencaSemDeducao = function (item) {
            return item.tipo == 'LICENCA_SEM_DEDUCAO';
        };

        $scope.removerDetalhamento = function (item) {
            var index = $scope.ctc.detalhamentosFrequencia.indexOf(item);
            $scope.ctc.detalhamentosFrequencia.splice(index, 1);
        }

        function validaDetalhamentoFrequencia(periodoInicial, periodoFinal, descricao) {
            if (!periodoInicial && !periodoFinal && !descricao) {
                $scope.showSimpleToast("Período e identificação são obrigatórios");
                return false;
            } else if (!periodoInicial || !periodoFinal) {
                $scope.showSimpleToast("Períodos são obrigatórios");
                return false;
            } else if (!descricao) {
                $scope.showSimpleToast("Campo Identificação é obrigatório");
                return false;
            } else {
                return true;
            }

        }

        function adicionaDetalhamento(periodoInicial, periodoFinal, descricao, tipo) {
            if (validaDetalhamentoFrequencia(periodoInicial, periodoFinal, descricao)) {
                let payload = {
                    periodoInicio: periodoInicial,
                    periodoFinal: periodoFinal,
                    tempo: $scope.calularPeriodoDias(periodoInicial, periodoFinal),
                    descricao: descricao,
                    tipo: tipo
                }

                $scope.ctc.detalhamentosFrequencia.push(payload);
                salvarAutomatico();
            }
        }

        $scope.adicionarDetalhamentoFrequenciaFaltas = function () {
            adicionaDetalhamento($scope.ff.periodoInicial, $scope.ff.periodoFinal, $scope.ff.identificacao, 'FALTA');
            delete $scope.ff;
        }


        $scope.adicionarDetalhamentoFrequenciaLicencas = function () {
            adicionaDetalhamento($scope.lsv.periodoInicial, $scope.lsv.periodoFinal, $scope.lsv.identificacao, 'LICENCA_SEM_VENCIMENTO');
            delete $scope.lsv;
        }

        $scope.adicionarDetalhamentoSuspensao = function () {
            adicionaDetalhamento($scope.sus.periodoInicial, $scope.sus.periodoFinal, $scope.sus.identificacao, 'SUSPENSAO');
            delete $scope.sus;
        }

        $scope.adicionarDetalhamentoFrequenciaDisponibilidade = function () {
            adicionaDetalhamento($scope.disp.periodoInicial, $scope.disp.periodoFinal, $scope.disp.identificacao, 'DISPONIBILIDADE');
            delete $scope.disp;
        }

        $scope.adicionarDetalhamentoFrequenciaOutros = function () {
            adicionaDetalhamento($scope.outro.periodoInicial, $scope.outro.periodoFinal, $scope.outro.identificacao, 'OUTROS');
            delete $scope.outro;
        }

        $scope.adicionarDetalhamentoFrequenciaLicensaSemDeducao = function () {
            adicionaDetalhamento($scope.lsd.periodoInicial, $scope.lsd.periodoFinal, $scope.lsd.identificacao, 'LICENCA_SEM_DEDUCAO');
            delete $scope.lsd;
        }

        function validaTempoEspecial(periodoInicial, periodoFinal, tipo) {
            if (!periodoInicial && !periodoFinal && !descricao) {
                $scope.showSimpleToast("Período e identificação são obrigatórios");
                return false;
            } else if (!periodoInicial || !periodoFinal) {
                $scope.showSimpleToast("Períodos são obrigatórios");
                return false;
            } else if (!tipo) {
                $scope.showSimpleToast("Campo Tipo é obrigatório");
                return false;
            } else {
                return true;
            }

        }

        $scope.adicionarDetalhamentoFrequenciaTempoEspecial = function () {
            if (validaTempoEspecial($scope.tei.periodoInicial, $scope.tei.periodoFinal, $scope.tei.tipoTempoEspecial)) {
                $scope.tei.dias = $scope.calularPeriodoDias($scope.tei.periodoInicial, $scope.tei.periodoFinal);

                let payload = {
                    periodoInicial: $scope.tei.periodoInicial,
                    periodoFinal: $scope.tei.periodoFinal,
                    tempo: $scope.tei.dias,
                    tipoTempo: $scope.tei.tipoTempoEspecial.label,
                    grauDeficiencia: $scope.tei.grau
                }
                $scope.ctc.tempoEspecial.push(payload);
                delete $scope.tei;
                salvarAutomatico();
            }
        }

        $scope.removerDetalhamentoFrequenciaTempoEspecial = function (item) {
            var index = $scope.ctc.tempoEspecial.indexOf(item);
            $scope.ctc.tempoEspecial.splice(index, 1);
            salvarAutomatico();
        }

        EnumService.Get("FuncaoDeclaracaoAposentadoriaEnum").then(function (dados) {
            $scope.list.tiposFuncao = dados;
        });

        EnumService.Get("TipoTempoEspecialEnum").then(function (dados) {
            $scope.list.tiposTempoEspecial = dados;
        });

        EnumService.Get("GrauDeficienciaEnum").then(function (dados) {
            $scope.list.graus = dados;
            $scope.lista.graus = [{ label: 'Leve' }, { label: 'Moderada' }, { label: 'Grave' }];
        });

        function salvarAutomatico() {
            if ($scope.naoRascunho || !$scope.salvarComoRascunho) {
                $scope.podeSalvarRascunho = false;
                cancelaLoop();
            } else {
                $scope.podeSalvarRascunho = true;
            }
        }

        $scope.startLoop = function () {

            $scope.loopRascunho = $interval(function () {
                if ($scope.contaLoop < 10) {
                    $scope.contaLoop++;
                } else {
                    restartLoop();
                }
                console.log('loop ' + $scope.contaLoop);
                var condicao1 = $scope.salvarComoRascunho;
                var condicao2 = !$scope.naoRascunho;
                var condicao3 = $scope.podeSalvarRascunho;

                if (condicao1 && condicao2 && condicao3)
                    $scope.save();

            }, 120000);
        }

        function restartLoop() {
            cancelaLoop();
            $scope.contaLoop = 0;
            $scope.startLoop();
        }


        $scope.$on("$destroy", function () {
            cancelaLoop();
        });

        function cancelaLoop() {
            if ($scope.loopRascunho) {
                $interval.cancel($scope.loopRascunho);
            }
        }

        $scope.startLoop();

        $scope.save = function () {

            if ($scope.funcionario) {
                if ($scope.lista.anexos) {
                    $scope.ctc.anexosRequest = [];
                    angular.forEach($scope.lista.anexos, function (e) {
                        $scope.ctc.anexosRequest.push(e.id);
                    });
                }

                $scope.ctc.relacaoRemuneracoes = $scope.relacaoRemuneracoes.linhas;

                $scope.ctc.rascunho = $scope.salvarComoRascunho;

                $scope.ctc.retificacao = $scope.retificar;

                $rootScope.$broadcast('preloader:active');
                if ($scope.ctc.id) {
                    GenericoService.Update('/certidaoExSegurado', $scope.ctc, function (response) {
                        $rootScope.$broadcast('preloader:hide');
                        if (response.status === 201 && response.data.success) {
                            $scope.podeSalvarRascunho = false;
                            if ($scope.salvarComoRascunho) {
                                $scope.showSimpleToast('Rascunho atualizado');
                                getCertidaoById(response.data.obj);
                            } else {
                                $scope.showSimpleToast(response.data.message);
                                $scope.goBack();
                            }
                        } else if (response.status === 400) {
                            $scope.podeSalvarRascunho = false;
                            $scope.showSimpleToast(response.data.message);
                            $location.path('/certidaoTempoContribuicaoExServidor/formulario/' + $state.params.id);
                        }
                    });
                } else {
                    GenericoService.Create('/certidaoExSegurado', $scope.ctc, function (response) {
                        $rootScope.$broadcast('preloader:hide');
                        if (response.status === 201 && response.data.success) {
                            $scope.podeSalvarRascunho = false;
                            if ($scope.salvarComoRascunho) {
                                $scope.showSimpleToast('Rascunho salvo');
                                getCertidaoById(response.data.obj);
                            } else {
                                $scope.showSimpleToast(response.data.message);
                                $scope.goBack();
                            }
                        } else if (response.status === 400) {
                            $scope.showSimpleToast(response.data.message);
                            $location.path('/certidaoTempoContribuicaoExServidor/formulario');
                            $scope.podeSalvarRascunho = false;
                        }
                    });
                }
            } else {
                $scope.showSimpleToast('Funcionário obrigatório!');
            }

        }

        $scope.editarFuncionario = function () {
            $scope.getUnidadesFederativas();

            $scope.funcionarioEditar = {};

            $scope.funcionarioEditar = $scope.funcionario;

            $scope.funcionarioEditar.dataNascimento = moment($scope.funcionario.dataNascimento);
            $scope.funcionarioEditar.dataExpedicaoRg = moment($scope.funcionario.dataExpedicaoRg);
            $scope.funcionarioEditar.dataAdmissao = moment($scope.funcionario.dataAdmissao);

            if ($scope.funcionario.municipio) {
                $scope.funcionarioEditar.municipioId = $scope.funcionarioEditar.municipio.id;
                $scope.getMunicipiosByUf($scope.funcionarioEditar.municipio.uf.id);
            }

            var useFullScreen = ($mdMedia('sm') || $mdMedia('xs'));

            $mdDialog.show({
                require: 'certidaoTempoContribuicaoExServidorFormCtrl',
                scope: $scope,
                preserveScope: true,
                controller: function () {

                    $scope.salvarEdicao = function () {
                        $scope.salvarEdicaoFuncionario();
                    };

                    $scope.atualizaFuncionario = function () {
                        getFuncionarioById($scope.funcionario.id);
                    }

                    $scope.cancel = function () {
                        $scope.funcionarioEditar = {};
                        $mdDialog.cancel();
                    };
                },
                templateUrl: 'app/page/certidaoTempoContribuicaoExServidor/dialogEditarFuncionario.html',
                parent: angular.element(document.body),
                clickOutsideToClose: true,
                fullscreen: useFullScreen
            });
        };

        $scope.salvarEdicaoFuncionario = function () {

            $scope.funcionarioCertidaoRequest = {};
            $scope.funcionarioCertidaoRequest.funcionario = $scope.funcionarioEditar;

            $rootScope.$broadcast('preloader:active');
            if ($scope.funcionarioEditar.id) {
                GenericoService.Update('/funcionarioCertidao', $scope.funcionarioCertidaoRequest, function (response) {
                    $rootScope.$broadcast('preloader:hide');
                    if (response.status === 201 && response.data.success) {
                        $scope.showSimpleToast(response.data.message);
                        $scope.atualizaFuncionario();
                    } else if (response.status === 400) {
                        $scope.showSimpleToast(response.data.message);
                    }
                });
            }
        };

        function getFuncionarioById(funcionarioId) {
            GenericoService.GetById('/funcionarioCertidao/' + funcionarioId, function (response) {
                if (response.status === 200) {
                    $scope.funcionario = response.data;
                    $scope.funcionarioSelecionado();
                    $scope.cancel();
                } else if (response.status === 400) {
                    $scope.showSimpleToast('Funcionário não encontrado');
                }
            });
        }

        /*Início - Aba Anexos*/

        var validateExtensions = ["application/pdf"];

        $scope.uploader = new FileUploader();
        $scope.uploader.url = configValue.baseUrl + "/api/anexo/certidaoExSegurado";
        $scope.uploader.headers = $http.defaults.headers.common;
        $scope.uploader.removeAfterUpload = true;
        $scope.uploader.onSuccessItem = function (fileItem, response, status, headers) {
            $scope.anexo = response.obj;
            if ($scope.anexo && $scope.anexo.id) {
                $scope.lista.anexos.push($scope.anexo);
            }
        };

        $scope.uploader.onAfterAddingFile = function (fileItem) {
            if (validateExtensions.indexOf(fileItem.file.type) == -1) {
                $scope.showSimpleToast('So é aceito arquivos do tipo: .pdf');
                $scope.uploader.removeFromQueue(fileItem);
            }
        };

        $scope.removerAnexo = function (item) {
            $rootScope.$broadcast('preloader:active');
            GenericoService.Delete('/certidaoExSegurado/remover-anexo/' + item.id, function (response) {
                $rootScope.$broadcast('preloader:hide');
                if (response.status === 200) {
                    $scope.showSimpleToast(response.data.message);
                    var index = $scope.lista.anexos.indexOf(item);
                    $scope.lista.anexos.splice(index, 1);
                }
            });
        }


        /*Fim - Aba Anexos*/

        $scope.showSimpleToast = function (textContent) {
            $mdToast.show(
                $mdToast.simple()
                    .textContent(textContent)
                    .position('top right')
                    .hideDelay(3000)
            );
        };

        /*
          * Recebe data e converte para valor com hora ou não
          * 
          * param data - recebe uma data tipo LocalDate
          * param comHora - recebe um valor boolean
          * 
          * */
        $scope.convertDate = function (data, comHora) {
            if (data) {
                var valor = moment(data);
                if (comHora) {
                    return $filter('date')(new Date(valor), 'dd/MM/yyyy - HH:mm');
                } else {
                    return $filter('date')(new Date(valor), 'dd/MM/yyyy');
                }
            } else {
                return '';
            }
        }

        $scope.calularPeriodoDias = function (dataInicio, dataFim) {
            if (dataInicio && dataFim) {
                dataInicio = moment(dataInicio);
                dataFim = moment(dataFim);
                var diferenca = moment.duration({
                    years: dataFim.year() - dataInicio.year(),
                    months: dataFim.month() - dataInicio.month(),
                    days: (dataFim.date() - dataInicio.date()) + 1
                });

                return diferenca.asDays();
            } else {
                return 0;
            }
        }

        /*
         * Recebe duas datas e retorna o tempo entre elas
         * 
         * param dataInicio - recebe uma data tipo LocalDate
         * param dataFim - recebe uma data tipo LocalDate
         * 
         * */
        $scope.calularPeriodo = function (dataInicio, dataFim, report) {
            dataInicio = moment(dataInicio);
            dataFim = moment(dataFim);

            dataFim = dataFim.add(1, 'days');
            var dias = dataFim.diff(dataInicio, 'days');

            var texto = '';

            if (dias >= 365) {
                var anos = dias / 365;
                anos = parseInt(anos);
                texto += anos < 10 ? '0' + anos : anos;
                if (report) {
                    texto += '-';
                } else {
                    texto += anos == 1 ? ' Ano, ' : ' Anos, ';
                }
                dias = dias % 365;
            } else {
                if (report) {
                    texto += '00-';
                } else {
                    texto += '00 Anos, ';
                }
            }

            if (dias >= 30) {
                var meses = dias / 30;
                meses = parseInt(meses);
                texto += meses < 10 ? '0' + meses : meses;
                if (report) {
                    texto += '-';
                } else {
                    texto += meses == 1 ? ' mês e ' : ' meses e ';
                }
                dias = dias % 30;
            } else {
                if (report) {
                    texto += '00-';
                } else {
                    texto += '00 meses e ';
                }
            }

            if (dias > 0) {
                texto += dias < 10 ? '0' + dias : dias;
                if (!report) {
                    texto += dias == 1 ? ' dia.' : ' dias.';
                }
            } else {
                if (report) {
                    texto += '00';
                } else {
                    texto += '00 dias.';
                }
            }

            return texto;
        }

        function organizaListaRemuneracoes(lista) {
            var valores = [];
            angular.forEach(lista, function (e) {
                var linha = {
                    ano: e.ano,
                    janeiro: e.janeiro,
                    fevereiro: e.fevereiro,
                    marco: e.marco,
                    abril: e.abril,
                    maio: e.maio,
                    junho: e.junho,
                    julho: e.julho,
                    agosto: e.agosto,
                    setembro: e.setembro,
                    outubro: e.outubro,
                    novembro: e.novembro,
                    dezembro: e.dezembro,
                    decimoTerceiro: e.decimoTerceiro
                };

                valores.push(linha);
            });
            return $filter('orderBy')(valores, 'ano');
        }

        function getHistoricoCertidao(id) {
            $rootScope.$broadcast('preloader:active');
            GenericoService.GetById('/certidaoExSegurado/historico/' + id, function (response) {
                $rootScope.$broadcast('preloader:hide');
                if (response.status === 200) {
                    $scope.historico = response.data;
                } else {
                    $scope.showSimpleToast("Não foi possível carregar o histórico da certidão");
                }
            });
        }

        if ($state.params && $state.params.id) {
            getCertidaoById($state.params.id);
        }

        function getCertidaoById(certidaoId) {
            $rootScope.$broadcast('preloader:active');
            GenericoService.GetById('/certidaoExSegurado/' + certidaoId, function (response) {
                $rootScope.$broadcast('preloader:hide');

                if (response.status === 200) {

                    $scope.ctc = response.data;
                    $scope.salvarComoRascunho = $scope.ctc.rascunho;
                    if (!$scope.salvarComoRascunho) {
                        $scope.naoRascunho = true;
                    }
                    $scope.numeroCertidao = $scope.ctc.numeroCertidao + '/' + $scope.ctc.anoCertidao;
                    $scope.numeroRetificacao = $scope.ctc.numeroRetificacao;

                    if ($scope.ctc.funcionario) {
                        $scope.funcionario = $scope.ctc.funcionario;
                        $scope.funcionarioSelecionado();
                    }

                    if ($scope.ctc.frequencias) {
                        $scope.validaDetalhamentoFrequência();
                    }
                    if ($scope.ctc.relacaoRemuneracoes) {
                        $scope.relacaoRemuneracoes.linhas = [];

                        $scope.relacaoRemuneracoes.linhas = organizaListaRemuneracoes($scope.ctc.relacaoRemuneracoes);
                    }

                    if (null != response.data.anexos) {
                        $scope.lista.anexos = response.data.anexos;
                    }

                    $scope.getListaAnosFrequencia();

                    if ($scope.detalhes) {
                        getHistoricoCertidao($scope.ctc.id);
                    }

                    $scope.podeSalvarRascunho = false;

                }
            });
        }

    }

})();
