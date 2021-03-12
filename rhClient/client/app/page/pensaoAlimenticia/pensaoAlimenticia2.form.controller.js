(function () {
    'use strict';

    angular.module('app.page')
        .controller('pensaoAlimenticia2FormCtrl', ['$scope', '$mdToast', '$location', '$state', '$rootScope', '$q', 'RestService', 'EnumService', pensaoAlimenticia2FormCtrl]);

    function pensaoAlimenticia2FormCtrl($scope, $mdToast, $location, $state, $rootScope, $q, RestService, EnumService) {

        // Checa as regras de acesso da tela
        RestService.Get('/usuario/verificaPermissao/por/menu', { params: { menu: 'Pensão Alimentícia' } })
            .then(function (response) {
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

        //Construtor do objeto Pensão Alimentícia
        $scope.pensaoAlimenticiaInit = {
        }
        $scope.pensaoAlimenticia = angular.copy($scope.pensaoAlimenticiaInit);

        // Detalhamento
        $scope.detalhes = false;
        if ($state.params && $state.params.detalhes) {
            $scope.detalhes = true;
        }

        // Busca de Unidades Federativas
        RestService.Get('/unidadeFederativa/search')
            .then(function (response) {
                if (response.status === 200 && response.data)
                    $scope.unidadeFederativaList = response.data;
            }, function errorCallback(response) {
                if (response.status === 400)
                    $scope.showSimpleToast(response.data.message);
            });

        // Busca de Verbas de Pensão Alimnentícia
        RestService.Get('/verba/pensaoAlimenticia/search')
            .then(function (response) {
                if (response.status === 200 && response.data)
                    $scope.verbaList = response.data;
            }, function errorCallback(response) {
                if (response.status === 400)
                    $scope.showSimpleToast(response.data.message);
            });

        // Busca de Centro de Custos
        RestService.Get('/centroCusto/search')
            .then(function (response) {
                if (response.status === 200 && response.data)
                    $scope.centroCustoList = response.data;
            }, function errorCallback(response) {
                if (response.status === 400)
                    $scope.showSimpleToast(response.data.message);
            });

        // Busca do enum de TipoPensaoEnum
        EnumService.Get("TipoPensaoEnum").then(function (dados) {
            $scope.tipoPensaoEnumList = dados;
        });

        // Busca do enum de TipoValorEnum
        EnumService.Get("TipoValorEnum").then(function (dados) {
            $scope.tipoValorEnumList = dados;
        });

        // Busca do enum de TipoIncidenciaPrincipalPensaoEnum
        EnumService.Get("TipoIncidenciaPrincipalPensaoEnum").then(function (dados) {
            $scope.tipoIncidenciaPrincipalPensaoEnumList = dados;
        });

        if ($state.params && $state.params.id) {
            $rootScope.$broadcast('preloader:active');
            RestService.Get('/pensaoAlimenticia/' + $state.params.id)
                .then(function successCallback(response) {
                    $rootScope.$broadcast('preloader:hide');
                    $scope.pensaoAlimenticia = response.data;
                    $scope.pensaoAlimenticia.nascimentoAlimentando = moment($scope.pensaoAlimenticia.nascimentoAlimentando);
                    if ($scope.pensaoAlimenticia.dataInicial)
                        $scope.pensaoAlimenticia.dataInicial = moment($scope.pensaoAlimenticia.dataInicial);
                    if ($scope.pensaoAlimenticia.dataFinal)
                        $scope.pensaoAlimenticia.dataFinal = moment($scope.pensaoAlimenticia.dataFinal);
                    $scope.pensaoAlimenticia.dataInicialPagamento = moment($scope.pensaoAlimenticia.dataInicialPagamento);
                    if ($scope.pensaoAlimenticia.dataFinalPagamento)
                        $scope.pensaoAlimenticia.dataFinalPagamento = moment($scope.pensaoAlimenticia.dataFinalPagamento);
                    $scope.pensaoAlimenticia.vencimento = moment($scope.pensaoAlimenticia.vencimento);

                }, function errorCallback(response) {
                    $rootScope.$broadcast('preloader:hide');
                    if (response.status === 400) {
                        $scope.showSimpleToast(response.data.message);
                    }
                });
        }

        $scope.changeEnderecoAlimentandoUf = function () {
            if ($scope.pensaoAlimenticia.enderecoAlimentando.municipio)
                $scope.pensaoAlimenticia.enderecoAlimentando.municipio = null;
        }

        $scope.save = function () {
            // Validação de obrigatoriedade de anexos

            $rootScope.$broadcast('preloader:active');
            if ($scope.pensaoAlimenticia.id) {
                RestService.Update('/pensaoAlimenticia', $scope.pensaoAlimenticia)
                    .then(function successCallback(response) {
                        $rootScope.$broadcast('preloader:hide');
                        if (response.status === 201 && response.data.success) {
                            $scope.showSimpleToast(response.data.message);
                            $location.path('/pensaoAlimenticia/gestao');
                        }
                    }, function errorCallback(response) {
                        $rootScope.$broadcast('preloader:hide');
                        if (response.status === 400) {
                            $scope.showSimpleToast(response.data.message);
                        }
                    });
            } else {
                RestService.Create('/pensaoAlimenticia', $scope.pensaoAlimenticia)
                    .then(function successCallback(response) {
                        $rootScope.$broadcast('preloader:hide');
                        if (response.status === 201 && response.data.success) {
                            $scope.showSimpleToast(response.data.message);
                            $location.path('/pensaoAlimenticia/gestao');
                        }
                    }, function errorCallback(response) {
                        $rootScope.$broadcast('preloader:hide');
                        if (response.status === 400) {
                            $scope.showSimpleToast(response.data.message);
                        }
                    });
            }
        }

        // $scope.onSelectedResponsavel = function () {
        //     if ($scope.pensaoAlimenticia.responsavel.dadoBancario)
        //         $scope.pensaoAlimenticia.dadoBancario = $scope.pensaoAlimenticia.responsavel.dadoBancario;
        // }

        $scope.querySearchResponsavel = function (query) {
            var deferred = $q.defer();
            if (query && query.length > 2) {
                $rootScope.$broadcast('preloader:active');
                var config = { params: { search: query } };
                RestService.Get('/pensaoAlimenticia/responsavel/search', config)
                    .then(function successCallback(response) {
                        $rootScope.$broadcast('preloader:hide');
                        if (response.data && response.data.length > 0)
                            deferred.resolve(response.data);
                        else
                            deferred.resolve([]);
                    }, function errorCallback(response) {
                        $rootScope.$broadcast('preloader:hide');
                        if (response.status === 400)
                            $scope.showSimpleToast(response.data.message);
                    });
            }
            return deferred.promise;
        };

        $scope.querySearchFuncionario = function (query) {
            var deferred = $q.defer();
            if (query && query.length > 2) {
                $rootScope.$broadcast('preloader:active');
                var config = { params: { search: query } };
                RestService.Get('/pensaoAlimenticia/funcionario/search', config)
                    .then(function successCallback(response) {
                        $rootScope.$broadcast('preloader:hide');
                        if (response.data && response.data.length > 0)
                            deferred.resolve(response.data);
                        else
                            deferred.resolve([]);
                    }, function errorCallback(response) {
                        $rootScope.$broadcast('preloader:hide');
                        if (response.status === 400)
                            $scope.showSimpleToast(response.data.message);
                    });
            }
            return deferred.promise;
        };

        $scope.querySearchMunicipio = function (query) {
            var deferred = $q.defer();
            if ($scope.pensaoAlimenticia.enderecoAlimentando.uf)
                if (query && query.length > 2) {
                    $rootScope.$broadcast('preloader:active');
                    var config = {
                        params: {
                            search: query,
                            ufId: $scope.pensaoAlimenticia.enderecoAlimentando.uf.id
                        }
                    };
                    RestService.Get('/municipio/search', config)
                        .then(function successCallback(response) {
                            $rootScope.$broadcast('preloader:hide');
                            if (response.data && response.data.length > 0)
                                deferred.resolve(response.data);
                            else
                                deferred.resolve([]);
                        }, function errorCallback(response) {
                            $rootScope.$broadcast('preloader:hide');
                            if (response.status === 400)
                                $scope.showSimpleToast(response.data.message);
                        });
                }
            return deferred.promise;
        };

        $scope.goBack = function () {
            $location.path('/pensaoAlimenticia/gestao');
        }

        $scope.showSimpleToast = function (textContent) {
            $mdToast.show(
                $mdToast.simple()
                    .textContent(textContent)
                    .position('top right')
                    .hideDelay(10000)
            );
        };

    }

})(); 