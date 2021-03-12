(function () {
    'use strict';

    angular.module('app.page')
        .controller('certidaoCompensacaoFormCtrl', ['$state', '$interval', 'RestService', '$location', '$q', 'configValue', '$scope', '$mdToast', '$mdDialog', '$mdMedia', '$rootScope', 'GenericoService', 'EnumService', '$filter', 'FileUploader', '$http', certidaoCompensacaoFormCtrl]);

    function certidaoCompensacaoFormCtrl($state, $interval, RestService, $location, $q, configValue, $scope, $mdToast, $mdDialog, $mdMedia, $rootScope, GenericoService, EnumService, $filter, FileUploader, $http) {

        // Checa as regras de acesso da tela
        RestService.Get('/usuario/verificaPermissao/por/menu', { params: { menu: 'CTC - Compensação' } }).then(
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

        $scope.limitOptions = [5, 10, 15];
        $scope.list = {
            "count": 0,
            "data": []
        };
        $scope.detalhes = false;
        $scope.certidaoCompensacao = {};
        $scope.periodo = {};
        $scope.assinatura = {};
        $scope.lista = {};
        $scope.lista.anexos = [];
        $scope.certidaoCompensacao.periodos = [];
        $scope.certidaoCompensacao.assinaturas = [];
        $scope.certidaoCompensacao.anexos = [];

        if ($state.params && $state.params.detalhes) {
            $scope.detalhes = true;
        }

        $scope.limpaFiltro = function () {
            delete $scope.descricaoBusca;
        }

        /*Início - Aba DADOS PROCESSO*/

        $scope.querySearch = function (query, type) {
            if (query && query.length > 2) {
                var config = {
                    params: {
                    }
                }

                switch (type) {
                    case 'funcionario':
                        config.params.searchFuncionario = query;
                        break;
                    case 'matricula':
                        config.params.searchMatricula = query;
                        break;
                    case 'cpf':
                        config.params.searchCPF = query;
                        break;
                    case 'pis':
                        config.params.queryPis = query;
                        break;
                }

                var deferred = $q.defer();
                GenericoService.GetAll('/funcionarioAdmissao/searchComplete', config).then(
                    function (response) {
                        if (response.data && response.data.length > 0)
                            deferred.resolve(response.data);
                        else
                            $scope.showSimpleToast("Nenhum registro encontrado");
                    });
                return deferred.promise;
            }
        }

        $scope.funcionarioSelecionado = function () {

            if ($scope.funcionario) {

                $scope.certidaoCompensacao.funcionarioId = $scope.funcionario.id;

                if ($scope.funcionario.lotacao) {
                    $scope.certidaoCompensacao.lotacaoId = $scope.funcionario.lotacao.id;
                }

                if ($scope.funcionario.nomeMae && $scope.funcionario.nomePai) {
                    $scope.filiacao = $scope.funcionario.nomeMae + " e " + $scope.funcionario.nomePai;
                } else if ($scope.funcionario.nomeMae) {
                    $scope.filiacao = $scope.funcionario.nomeMae;
                } else {
                    $scope.filiacao = $scope.funcionario.nomePai;
                }

                if ($scope.funcionario.sexo) {
                    if ($scope.funcionario.sexo == 'F') {
                        $scope.genero = 'Feminino';
                    } else {
                        $scope.genero = 'Masculino';
                    }
                }

                $scope.enderecoEmpresa = $scope.funcionario.empresa.logradouro + ', ' + $scope.funcionario.empresa.numero;

                var config = {
                    params: {
                        funcionarioId: $scope.funcionario.id
                    }
                }
                GenericoService.GetAll('/getPrimeiraSituacaoFuncionalByFuncionario', config).then(
                    function (response) {
                        if (response.status === 200) {
                            if (response.data && response.data.situacaoFuncional) {
                                $scope.situacaoFuncional = response.data.situacaoFuncional;
                            } else {
                                if ($scope.funcionario.tipoSituacaoFuncionalId) {
                                    GenericoService.GetById('/situacaoFuncional/' + $scope.funcionario.tipoSituacaoFuncionalId, function (response2) {
                                        if (response2.status === 200) {
                                            $scope.situacaoFuncional = response2.data.descricao;
                                        }
                                    });
                                }
                            }

                        }
                    });
                $scope.podeSalvarRascunho = true;
            }
        };

        EnumService.Get("ClassificacaoCertidaoCompensacaoEnum").then(function (dados) {
            $scope.lista.classificacoes = dados;
            $scope.classificacoesMap = new Map();
            $scope.lista.classificacoes.forEach(element => {
                $scope.classificacoesMap.set(element.value, element.label);
            });
        });

        EnumService.Get("StatusCertidaoCompensacaoEnum").then(function (dados) {
            $scope.lista.status = dados;
        });

        EnumService.Get("FuncaoCertidaoCompensacaoEnum").then(function (dados) {
            $scope.lista.funcoes = $filter('orderBy')(dados, 'label');
        });

        $scope.validarBotaoPeriodo = function () {
            if ($scope.periodo) {
                return !($scope.periodo.periodoInicio && $scope.periodo.periodoFim);
            }
        }

        $scope.validarBotaoAssinatura = function () {
            if ($scope.assinatura) {
                return !($scope.assinatura.funcionario && $scope.assinatura.funcao);
            }
        }

        $scope.adicionarPeriodo = function () {
            if ($scope.certidaoCompensacao.id) {
                $scope.periodo.certidaoCompensacaoId = $scope.certidaoCompensacao.id;
            }
            $scope.certidaoCompensacao.periodos.push($scope.periodo);
            delete $scope.periodo;
        }

        $scope.removerPeriodo = function (item) {
            var index = $scope.certidaoCompensacao.periodos.indexOf(item);
            $scope.certidaoCompensacao.periodos.splice(index, 1);
        }

        $scope.querySearchFuncionario = function (query) {

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

        $scope.funcionarioAssinaturaSelecionado = function () {
            if ($scope.funcionarioAssinatura) {
                $scope.assinatura.funcionario = $scope.funcionarioAssinatura;
                $scope.assinatura.funcionarioId = $scope.funcionarioAssinatura.id;
            }
        };

        $scope.adicionarAssinatura = function () {

            var existe;
            angular.forEach($scope.certidaoCompensacao.assinaturas, function (e) {
                if (e.funcionarioId == $scope.assinatura.funcionario.id) {
                    $scope.showSimpleToast("Este funcionario já foi adicionado");
                    existe = true;
                    return;
                } else if (e.funcao == $scope.assinatura.funcao) {
                    $scope.showSimpleToast("Esta função já foi adicionada");
                    existe = true;
                    return;
                }
            });
            if (!existe) {
                $scope.certidaoCompensacao.assinaturas.push($scope.assinatura);
                $scope.podeSalvarRascunho = true;
            }

            delete $scope.assinatura;
            $scope.assinatura = {};
            delete $scope.funcionarioAssinatura;

        }

        $scope.removerAssinatura = function (item) {
            var index = $scope.certidaoCompensacao.assinaturas.indexOf(item);
            $scope.certidaoCompensacao.assinaturas.splice(index, 1);
        }


        /*Fim - Aba C*/

        /*Início - Aba Anexos*/

        var validateExtensions = ["application/pdf"];

        $scope.uploader = new FileUploader();
        $scope.uploader.url = configValue.baseUrl + "/api/anexo/certidaoCompensacao";
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
            GenericoService.Delete('/certidaoCompensacao/remover-anexo/' + item.id, function (response) {
                $rootScope.$broadcast('preloader:hide');
                if (response.status === 200) {
                    $scope.showSimpleToast(response.data.message);
                    var index = $scope.certidaoCompensacao.anexos.indexOf(item);
                    $scope.certidaoCompensacao.anexos.splice(index, 1);
                }
            });
        }

        /*Fim - Aba Anexos*/
        $scope.GetById = function (id, primeiroAcesso) {
            $rootScope.$broadcast('preloader:active');
            GenericoService.GetById('/certidaoCompensacao/' + id, function (response) {
                $rootScope.$broadcast('preloader:hide');
                if (response.status === 200) {

                    if (primeiroAcesso) {
                        $scope.certidaoCompensacao = response.data;

                        if (!$scope.certidaoCompensacao.periodos)
                            $scope.certidaoCompensacao.periodos = [];

                        if (!$scope.certidaoCompensacao.assinaturas)
                            $scope.certidaoCompensacao.assinaturas = [];

                        if (!$scope.certidaoCompensacao.classificacoes)
                            $scope.certidaoCompensacao.classificacoes = [];

                        $scope.lista.anexos = $scope.certidaoCompensacao.anexos;
                        $scope.funcionario = $scope.certidaoCompensacao.funcionario;
                        $scope.funcionarioSelecionado();
                    }
                    $scope.certidaoCompensacao.id = response.data.id;
                    $scope.certidaoCompensacao.rascunho = response.data.rascunho;
                    $scope.savarComoRascunho = $scope.certidaoCompensacao.rascunho;
                    $scope.numeroCertidao = response.data.numero + '/' + response.data.ano;

                    if ($scope.detalhes) {
                        var classificacoesConcat = '';
                        $scope.certidaoCompensacao.classificacoes.forEach(classificacao => {
                            if (classificacoesConcat.length === 0)
                                classificacoesConcat += $scope.classificacoesMap.get(classificacao);
                            else
                                classificacoesConcat += ', ' + $scope.classificacoesMap.get(classificacao);
                        });
                        classificacoesConcat += '.';
                        $scope.certidaoCompensacao.classificacoesConcat = classificacoesConcat;
                    }
                } else {
                    $scope.showSimpleToast("Certidão não encontrado na base");
                }
            });
        }

        /*
         * Recebe data e converte para valor com hora ou não
         * 
         * param data - recebe uma data tipo LocalDate
         * param comHora - recebe um valor boolean
         * 
         * */
        $scope.convertDate = function (data, comHora) {
            var valor = moment(data);
            if (comHora) {
                return $filter('date')(new Date(valor), 'dd/MM/yyyy - HH:mm');
            } else {
                return $filter('date')(new Date(valor), 'dd/MM/yyyy');
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

        function calularPeriodoReport(dataInicio, dataFim) {
            return $scope.calularPeriodo(dataInicio, dataFim, true)
        }

        /*
         * Retorna para a listagem
         * 
         * */
        $scope.goBack = function () {
            $location.path('/certidaoTempoContribuicaoCompensacao/gestao');
        }

        $scope.showSimpleToast = function (textContent) {
            $mdToast.show(
                $mdToast.simple()
                    .textContent(textContent)
                    .position('top right')
                    .hideDelay(3000)
            );
        };

        if ($state.params && $state.params.id)
            $scope.GetById($state.params.id, true);
        else {
            $scope.savarComoRascunho = true;
            $scope.certidaoCompensacao.rascunho = true;
        }

        $scope.loopRascunho = $interval(function () {
            var podeSalvarRascunho = $scope.podeSalvarRascunho;
            var dirty = false;
            if ($scope.certidaoCompensacaoForm)
                var dirty = $scope.certidaoCompensacaoForm && $scope.certidaoCompensacaoForm.$dirty;
            var rascunho = $scope.certidaoCompensacao.rascunho;

            if ($scope.savarComoRascunho && podeSalvarRascunho && dirty && rascunho)
                $scope.save(true);

        }, 120000);

        $scope.$on("$destroy", function () {
            if ($scope.loopRascunho) {
                $interval.cancel($scope.loopRascunho);
            }
        });

        $scope.save = function (salvarRascunho) {

            if (!$scope.certidaoCompensacao.rascunho || !$scope.savarComoRascunho) {

                if (!$scope.certidaoCompensacao.classificacoes || $scope.certidaoCompensacao.classificacoes.length === 0) {
                    $scope.showSimpleToast('Preencha ao menos uma classificação.');
                    return;
                }

                if (!$scope.certidaoCompensacao.periodos || $scope.certidaoCompensacao.periodos.length === 0) {
                    $scope.showSimpleToast('Preencha ao menos um período.');
                    return;
                }

                if (!$scope.certidaoCompensacao.assinaturas || $scope.certidaoCompensacao.assinaturas.length <= 1) {
                    $scope.showSimpleToast('Preencha ao menos duas assinaturas.');
                    return;
                }

            } else {
                if (!$scope.certidaoCompensacao.classificacoes || $scope.certidaoCompensacao.classificacoes.length === 0) {
                    $scope.showSimpleToast('Preencha ao menos uma classificação.');
                    return;
                }
            }

            if ($scope.lista.anexos) {
                $scope.certidaoCompensacao.anexos = [];
                angular.forEach($scope.lista.anexos, function (e) {
                    $scope.certidaoCompensacao.anexos.push(e.id);
                });
            }

            $rootScope.$broadcast('preloader:active');

            if ($scope.funcionario) {
                $scope.certidaoCompensacao.numeroCtps = $scope.funcionario.numeroCtps;
                $scope.certidaoCompensacao.serieCtps = $scope.funcionario.serieCtps;
            }

            if ($scope.certidaoCompensacao.id) {
                GenericoService.Update('/certidaoCompensacao', $scope.certidaoCompensacao, function (response) {
                    $rootScope.$broadcast('preloader:hide');
                    if (response.status === 201 && response.data.success) {
                        if (salvarRascunho) {
                            $scope.podeSalvarRascunho = false;
                            $scope.showSimpleToast('Rascunho salvo');
                            $scope.GetById(response.data.obj.id, false);
                        } else {
                            $scope.showSimpleToast(response.data.message);
                            $scope.goBack();
                        }
                    } else if (response.status === 400) {
                        $scope.showSimpleToast(response.data.message);
                        $location.path('certidaoCompensacao/formulario/' + $state.params.id)
                    }
                });
            } else {

                GenericoService.Create('/certidaoCompensacao', $scope.certidaoCompensacao, function (response) {
                    $rootScope.$broadcast('preloader:hide');
                    if (response.status === 201 && response.data.success) {
                        if (salvarRascunho) {
                            $scope.podeSalvarRascunho = false;
                            $scope.showSimpleToast('Rascunho salvo');
                            $scope.GetById(response.data.obj.id, false);
                        } else {
                            $scope.showSimpleToast(response.data.message);
                            $scope.goBack();
                        }
                    } else if (response.status === 400) {
                        $scope.showSimpleToast(response.data.message);
                        $location.path('/certidaoTempoContribuicaoCompensacao/formulario');
                    }
                });
            }
        }
    }

})();