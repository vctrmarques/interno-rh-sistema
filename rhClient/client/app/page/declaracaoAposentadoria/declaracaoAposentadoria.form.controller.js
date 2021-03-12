(function () {
    'use strict';

    angular.module('app.page')
        .controller('declaracaoAposentadoriaFormCtrl', ['$interval', '$state', 'RestService', '$location', '$q', 'configValue', '$scope', '$mdToast', '$mdDialog', '$mdMedia', '$rootScope', 'GenericoService', 'EnumService', '$filter', 'FileUploader', '$http', declaracaoAposentadoriaFormCtrl]);

    function declaracaoAposentadoriaFormCtrl($interval, $state, RestService, $location, $q, configValue, $scope, $mdToast, $mdDialog, $mdMedia, $rootScope, GenericoService, EnumService, $filter, FileUploader, $http) {

        // Checa as regras de acesso da tela
        RestService.Get('/usuario/verificaPermissao/por/menu', { params: { menu: 'Declaração para Aposentados' } }).then(
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
        $scope.averbacao = {};
        $scope.naoAverbacao = {};
        $scope.assinatura = {};
        $scope.lista = {};
        $scope.lista.anexos = [];

        // Init Basic
        $scope.declaracaoAposentadoria = {
            averbacoes: [],
            assinaturas: [],
            anexos: []
        };

        if ($state.params && $state.params.detalhes) {
            $scope.detalhes = true;
        }

        $scope.limpaFiltro = function () {
            delete $scope.descricaoBusca;
        }

        $scope.filtrarAverbado = function (item) {
            return item.averbado == true;
        };

        $scope.filtrarNaoAverbado = function (item) {
            return item.averbado == false;
        };

        /*Início - Aba Declaração*/

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
                GenericoService.GetAll('/funcionarioAposentado/searchComplete', config).then(
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
                $scope.declaracaoAposentadoria.funcionarioId = $scope.funcionario.id;

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
                var config = {
                    params: {
                        funcionarioId: $scope.funcionario.id
                    }
                }
                GenericoService.GetAll('/getPrimeiraSituacaoFuncionalByFuncionario', config).then(
                    function (response) {
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
                    });

                $scope.podeSalvarRascunho = true;
            }
        };

        EnumService.Get("FuncaoDeclaracaoAposentadoriaEnum").then(function (dados) {
            $scope.list.funcoes = $filter('orderBy')(dados, 'label');
        });

        $scope.validarBotaoAverbacao = function () {
            if ($scope.averbacao) {
                return (!$scope.averbacao.empregador || !$scope.averbacao.periodoInicio || !$scope.averbacao.periodoFim);
            } else {
                return true;
            }
        }

        $scope.validarBotaoNaoAverbacao = function () {
            if ($scope.naoAverbacao) {
                return !($scope.naoAverbacao.empregador && $scope.naoAverbacao.periodoInicio && $scope.naoAverbacao.periodoFim);
            } else {
                return true;
            }
        }

        $scope.validarBotaoAssinatura = function () {
            if ($scope.assinatura) {
                return !($scope.assinatura.funcionario && $scope.assinatura.funcao);
            } else {
                return true;
            }
        }

        $scope.adicionarAverbacao = function (tipo) {

            if (tipo && !$scope.validarBotaoAverbacao()) {
                if ($scope.declaracaoAposentadoria.id) {
                    $scope.averbacao.declaracaoAposentadoriaId = $scope.declaracaoAposentadoria.id;
                }
                $scope.averbacao.averbado = true;
                $scope.declaracaoAposentadoria.averbacoes.push($scope.averbacao);
                $scope.podeSalvarRascunho = true;
                delete $scope.averbacao;
            } else if (!tipo && !$scope.validarBotaoNaoAverbacao()) {
                if ($scope.declaracaoAposentadoria.id) {
                    $scope.naoAverbacao.declaracaoAposentadoriaId = $scope.declaracaoAposentadoria.id;
                }
                $scope.naoAverbacao.averbado = false;
                $scope.declaracaoAposentadoria.averbacoes.push($scope.naoAverbacao);
                $scope.podeSalvarRascunho = true;
                delete $scope.naoAverbacao;

            }

        }

        $scope.removerAnexo = function (item) {
            $rootScope.$broadcast('preloader:active');
            GenericoService.Delete('/declaracaoAposentadoria/remover-anexo/' + item.id, function (response) {
                $rootScope.$broadcast('preloader:hide');
                if (response.status === 200) {
                    $scope.showSimpleToast(response.data.message);
                    var index = $scope.declaracaoAposentadoria.anexos.indexOf(item);
                    $scope.declaracaoAposentadoria.anexos.splice(index, 1);
                    $scope.podeSalvarRascunho = true;
                }
            });
        }

        $scope.removerAverbacao = function (item) {
            var index = $scope.declaracaoAposentadoria.averbacoes.indexOf(item);
            $scope.declaracaoAposentadoria.averbacoes.splice(index, 1);
            $scope.podeSalvarRascunho = true;
        }

        $scope.removerAssinatura = function (item) {
            var index = $scope.declaracaoAposentadoria.assinaturas.indexOf(item);
            $scope.declaracaoAposentadoria.assinaturas.splice(index, 1);
            $scope.podeSalvarRascunho = true;
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
            if (!$scope.validarBotaoAssinatura()) {
                var existe;
                angular.forEach($scope.declaracaoAposentadoria.assinaturas, function (e) {
                    if (e.funcionarioId == $scope.assinatura.funcionario.id) {
                        $scope.showSimpleToast("Este funcionario já foi adicionado");
                        existe = true;
                        return;
                    } else if (e.funcao == $scope.assinatura.funcao) {
                        $scope.showSimpleToast("Este funcao já foi adicionada");
                        existe = true;
                        return;
                    }
                });
                if (!existe) {
                    $scope.declaracaoAposentadoria.assinaturas.push($scope.assinatura);
                    $scope.podeSalvarRascunho = true;
                }

                delete $scope.assinatura;
                $scope.assinatura = {};
                delete $scope.funcionarioAssinatura;
            }
        }




        /*Fim - Aba Declaração*/

        /*Início - Aba Anexos*/

        var validateExtensions = ["application/pdf"];

        $scope.uploader = new FileUploader();
        $scope.uploader.url = configValue.baseUrl + "/api/anexo/declaracaoAposentadoria";
        $scope.uploader.headers = $http.defaults.headers.common;
        $scope.uploader.removeAfterUpload = true;
        $scope.uploader.onSuccessItem = function (fileItem, response, status, headers) {
            $scope.anexo = response.obj;
            if ($scope.anexo && $scope.anexo.id) {
                $scope.lista.anexos.push($scope.anexo);
                $scope.podeSalvarRascunho = true;
            }
        };

        $scope.uploader.onAfterAddingFile = function (fileItem) {
            if (validateExtensions.indexOf(fileItem.file.type) == -1) {
                $scope.showSimpleToast('So é aceito arquivos do tipo: .pdf');
                $scope.uploader.removeFromQueue(fileItem);
            }
        };

        /*Fim - Aba Anexos*/

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
            $location.path('/declaracaoParaAposentados/gestao');
        }

        $scope.showSimpleToast = function (textContent) {
            $mdToast.show(
                $mdToast.simple()
                    .textContent(textContent)
                    .position('top right')
                    .hideDelay(3000)
            );
        };

        $scope.GetById = function (id, primeiroAcesso) {
            $rootScope.$broadcast('preloader:active');
            GenericoService.GetById('/declaracaoAposentadoria/' + id, function (response) {
                $rootScope.$broadcast('preloader:hide');
                if (response.status === 200) {
                    if (primeiroAcesso) {
                        $scope.declaracaoAposentadoria = response.data;

                        if (!$scope.declaracaoAposentadoria.averbacoes)
                            $scope.declaracaoAposentadoria.averbacoes = [];

                        if (!$scope.declaracaoAposentadoria.assinaturas)
                            $scope.declaracaoAposentadoria.assinaturas = [];

                        $scope.lista.anexos = $scope.declaracaoAposentadoria.anexos;
                        $scope.funcionario = $scope.declaracaoAposentadoria.funcionario;
                        $scope.funcionarioSelecionado();
                    }
                    $scope.declaracaoAposentadoria.id = response.data.id;
                    $scope.declaracaoAposentadoria.rascunho = response.data.rascunho;
                    $scope.savarComoRascunho = $scope.declaracaoAposentadoria.rascunho;
                    $scope.numeroDeclaracao = response.data.numero + '/' + response.data.ano;
                } else {
                    $scope.showSimpleToast("Declaração não encontrado na base");
                }
            });
        }

        if ($state.params && $state.params.id)
            $scope.GetById($state.params.id, true);
        else {
            $scope.savarComoRascunho = true;
            $scope.declaracaoAposentadoria.rascunho = true;
        }

        $scope.loopRascunho = $interval(function () {
            var podeSalvarRascunho = $scope.podeSalvarRascunho;
            var dirty = $scope.declaracaoAposentadoriaForm && $scope.declaracaoAposentadoriaForm.$dirty;
            var rascunho = $scope.declaracaoAposentadoria.rascunho;

            if ($scope.savarComoRascunho && podeSalvarRascunho && dirty && rascunho)
                $scope.save(true);

        }, 120000);

        $scope.$on("$destroy", function () {
            if ($scope.loopRascunho) {
                $interval.cancel($scope.loopRascunho);
            }
        });

        $scope.save = function (salvarRascunho) {

            if (!$scope.declaracaoAposentadoria.rascunho || !$scope.savarComoRascunho) {
                if (!$scope.declaracaoAposentadoria.funcionarioId) {
                    $scope.showSimpleToast('Preencha os dados do funcionário.');
                    return;
                }

                if (!$scope.declaracaoAposentadoria.averbacoes || $scope.declaracaoAposentadoria.averbacoes.length === 0) {
                    $scope.showSimpleToast('Preencha ao menos um período averbado.');
                    return;
                }

                if (!$scope.declaracaoAposentadoria.assinaturas || $scope.declaracaoAposentadoria.assinaturas.length <= 1) {
                    $scope.showSimpleToast('Preencha ao menos duas assinaturas.');
                    return;
                }
            } else {
                if (!$scope.declaracaoAposentadoria.averbacoes || $scope.declaracaoAposentadoria.averbacoes.length === 0) {
                    $scope.showSimpleToast('Preencha ao menos um período averbado.');
                    return;
                }
            }

            $rootScope.$broadcast('preloader:active');

            if ($scope.lista.anexos) {
                $scope.declaracaoAposentadoria.anexos = [];
                angular.forEach($scope.lista.anexos, function (e) {
                    $scope.declaracaoAposentadoria.anexos.push(e.id);
                });
            }
            if ($scope.declaracaoAposentadoria.id) {
                GenericoService.Update('/declaracaoAposentadoria', $scope.declaracaoAposentadoria, function (response) {
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
                        $location.path('declaracaoParaAposentados/formulario/' + $state.params.id)
                    }
                });
            } else {

                GenericoService.Create('/declaracaoAposentadoria', $scope.declaracaoAposentadoria, function (response) {
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
                        $location.path('/declaracaoParaAposentados/formulario');
                    }
                });
            }
        }
    }

})();