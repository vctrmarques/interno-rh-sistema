(function () {
    'use strict';

    angular.module('app.page')
        .controller('contaContabilFormCtrl', ['$scope', '$mdToast', '$location', '$state', '$rootScope', 'GenericoService', 'EnumService', contaContabilFormCtrl]);

    function contaContabilFormCtrl($scope, $mdToast, $location, $state, $rootScope, GenericoService, EnumService) {

        $scope.acessaTela = function () {
            GenericoService.VerificaPermissao('/usuario/verificaPermissao', 'ROLE_ADMIN').then(
                function (response) {
                    if (response.status === 200) {
                    }
                }, function errorCallback(response) {
                    if (response.status === 400) {
                        $location.path('page/403');
                    }
                });
        }
        $scope.lotacoes = [];
        $scope.list = {
            "empresa": [],
            "filial": [],
            "lotacao": [],
            "centroCusto": [],
            "verba": []
        }

        EnumService.Get("TipoContaLotacaoEnum").then(function (dados) {
            $scope.list.tipoConta = dados;
        });

        $scope.acessaTela();
        $scope.detalhes = false;
        var i = null;

        if ($state.params && $state.params.id) {
            $rootScope.$broadcast('preloader:active');
            GenericoService.GetById('/contaContabil/' + $state.params.id, function (response) {
                $rootScope.$broadcast('preloader:hide');
                if (response.status === 200) {
                    $scope.contaContabil = response.data;

                    if ($scope.contaContabil.centroCusto) {
                        $scope.contaContabil.centroCustoId = $scope.contaContabil.centroCusto.id;
                    }

                    $scope.contaContabil.lotacoesIds = [];

                    $scope.lotacoes = $scope.contaContabil.lotacoes;

                    for (i = 0; i < $scope.contaContabil.lotacoes.length; i++) {
                        $scope.contaContabil.lotacoesIds.push($scope.contaContabil.lotacoes[i].id)
                    }
                } else {
                    $scope.showSimpleToast("Conta Contábil não encontrado na base");
                }
            });
        } else {
            $scope.contaContabil = { lotacoesIds: [] };
        }

        $scope.verba = function () {
            $rootScope.$broadcast('preloader:active');
            GenericoService.GetAllDropdown('listaVerbas', function (response) {
                if (response.status === 200) {
                    $scope.list.verba = response.data;
                    $rootScope.$broadcast('preloader:hide');
                } else if (response.status === 500) {
                    $scope.showSimpleToast("Falha no carregamento de dados, favor contate o administrador do sistema");
                }
            });
        }
        $scope.verba();

        $scope.Empresa = function () {
            $rootScope.$broadcast('preloader:active');
            GenericoService.GetAllDropdown('listaEmpresasMatrizes', function (response) {
                if (response.status === 200) {
                    $scope.list.empresa = response.data;
                    $rootScope.$broadcast('preloader:hide');
                } else if (response.status === 500) {
                    $scope.showSimpleToast("Falha no carregamento de dados, favor contate o administrador do sistema");
                }
            });
        }
        $scope.Empresa();

        $scope.Filial = function () {
            $rootScope.$broadcast('preloader:active');
            GenericoService.GetAllDropdown('listaEmpresasNaoMatrizes', function (response) {
                if (response.status === 200) {
                    $scope.list.filial = response.data;
                    $rootScope.$broadcast('preloader:hide');
                } else if (response.status === 500) {
                    $scope.showSimpleToast("Falha no carregamento de dados, favor contate o administrador do sistema");
                }
            });
        }
        $scope.Filial()

        $scope.Lotacao = function (empresaFilialId) {
            if (empresaFilialId && empresaFilialId > 0) {
                $rootScope.$broadcast('preloader:active');
                GenericoService.GetAllDropdown('listaLotacoes/' + empresaFilialId, function (response) {
                    if (response.status === 200) {
                        $scope.list.lotacao = response.data;
                        $rootScope.$broadcast('preloader:hide');
                    } else if (response.status === 500) {
                        $scope.showSimpleToast("Falha no carregamento de dados, favor contate o administrador do sistema");
                    }
                });
            }
        }

        $scope.CentroCusto = function () {
            $rootScope.$broadcast('preloader:active');
            GenericoService.GetAllDropdown('listaCentroCustos', function (response) {
                if (response.status === 200) {
                    $scope.list.centroCusto = response.data;
                    $rootScope.$broadcast('preloader:hide');
                } else if (response.status === 500) {
                    $scope.showSimpleToast("Falha no carregamento de dados, favor contate o administrador do sistema");
                }
            });
        }
        $scope.CentroCusto();

        if ($state.params && $state.params.detalhes) {
            $scope.detalhes = true;
        }

        $scope.goBack = function () {
            $location.path('/contaContabil/gestao');
        }

        $scope.addItem = function () {
            $scope.lotac = JSON.parse($scope.lotacao);
            $scope.lotacoes.push($scope.lotac);
            $scope.contaContabil.lotacoesIds.push($scope.lotac.id)
        }
        $scope.removeItem = function (index) {
            $scope.lotacoes.splice(index, 1);
            $scope.contaContabil.lotacoesIds.splice(index, 1);
        }

        $scope.save = function () {
            $rootScope.$broadcast('preloader:active');
            if ($scope.contaContabil.id) {
                GenericoService.Update('/contaContabil', $scope.contaContabil, function (response) {
                    $rootScope.$broadcast('preloader:hide');
                    if (response.status === 201 && response.data.success) {
                        $scope.showSimpleToast(response.data.message);
                        $location.path('/contaContabil/gestao');
                    } else if (response.status === 400) {
                        $scope.showSimpleToast(response.data.message);
                        $location.path('contaContabil/formulario/' + $state.params.id)
                    }
                });
            } else {
                GenericoService.Create('/contaContabil', $scope.contaContabil, function (response) {
                    $rootScope.$broadcast('preloader:hide');
                    if (response.status === 201 && response.data.success) {
                        $scope.showSimpleToast(response.data.message);
                        $location.path('/contaContabil/gestao');
                    } else if (response.status === 400) {
                        $scope.showSimpleToast(response.data.message);
                        $location.path('/contaContabil/formulario');
                    }
                });
            }
        }

        $scope.showSimpleToast = function (textContent) {
            $mdToast.show(
                $mdToast.simple()
                    .textContent(textContent)
                    .position('top right')
                    .hideDelay(3000)
            );
        };

    }

})(); 