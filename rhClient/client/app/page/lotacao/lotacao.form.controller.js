(function () {
    'use strict';

    angular.module('app.page')
        .controller('lotacaoFormCtrl', ['$scope', '$mdToast', '$location', '$state', '$rootScope', 'GenericoService', 'EnumService', lotacaoFormCtrl]);

    function lotacaoFormCtrl($scope, $mdToast, $location, $state, $rootScope, GenericoService, EnumService) {

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

        $scope.acessaTela();
        $scope.detalhes = false;

        $scope.tiposConta = [{ nome: "Crédito" },
        { nome: "Débito" }];

        $scope.limpaTipoConta = function () {
            delete $scope.lotacao.tipoConta;
            delete $scope.lotacao.numeroConta;
        }

        $scope.alteraNumero = function (creditoDebito, centroCustoId) {
            $scope.getConta(creditoDebito, centroCustoId)
        }

        $scope.getConta = function (creditoDebito, centroCustoId) {
            GenericoService.GetNumeroConta(creditoDebito, centroCustoId, function (response) {
                if (response.status === 200) {
                    $scope.lotacao.numeroConta = response.data;

                    if (!$scope.lotacao.numeroConta) {
                        $scope.showSimpleToast("Não existe uma Conta de " + creditoDebito + " para este Centro de Custo");
                    }
                } else if (response.status === 500) {
                    $scope.lotacao.numeroConta = undefined;
                }
            });
        }


        if ($state.params && $state.params.id) {
            $rootScope.$broadcast('preloader:active');
            GenericoService.GetById('/lotacao/' + $state.params.id, function (response) {
                $rootScope.$broadcast('preloader:hide');
                if (response.status === 200) {
                    $scope.lotacao = response.data;
                    if ($scope.lotacao.vigenciaInicial) {
                        $scope.lotacao.vigenciaInicial = moment(response.data.vigenciaInicial);
                    }

                    if ($scope.lotacao.vigenciaFinal) {
                        $scope.lotacao.vigenciaFinal = moment(response.data.vigenciaFinal);
                    }
                    if ($scope.lotacao.centroCusto)
                        $scope.lotacao.centroCustoId = $scope.lotacao.centroCusto.id;
                } else {
                    $scope.showSimpleToast("Lotação não encontrada na base");
                }
            });
        }

        if ($state.params && $state.params.detalhes) {
            $scope.detalhes = true;
        }

        $scope.listCentroCusto = {
            "data": []
        };

        $scope.CentroCusto = function () {
            $rootScope.$broadcast('preloader:active');
            GenericoService.GetAllDropdown('listaCentroCustos', function (response) {
                if (response.status === 200) {
                    $scope.listCentroCusto.data = response.data;
                    $rootScope.$broadcast('preloader:hide');
                } else if (response.status === 500) {
                    $scope.showSimpleToast("Falha no carregamento de dados, favor contate o administrador do sistema");
                }
            });
        }

        $scope.CentroCusto();

        $scope.TiposLotacao = function () {
            $rootScope.$broadcast('preloader:active');

            // Busca do enum de TipoLotacaoEnum
            EnumService.Get("TipoLotacaoEnum").then(function (dados) {
                $scope.tipos = dados;
                $rootScope.$broadcast('preloader:hide');
            });

        }

        $scope.TiposLotacao();



        $scope.goBack = function () {
            $location.path('/lotacao/gestao');
        }

        $scope.save = function () {
            $rootScope.$broadcast('preloader:active');
            if ($scope.lotacao.id) {
                GenericoService.Update('/lotacao', $scope.lotacao, function (response) {
                    $rootScope.$broadcast('preloader:hide');
                    if (response.status === 201 && response.data.success) {
                        $scope.showSimpleToast(response.data.message);
                        $location.path('/lotacao/gestao');
                    } else if (response.status === 400) {
                        $scope.showSimpleToast(response.data.message);
                        $location.path('lotacao/formulario/' + $state.params.id)
                    }
                });
            } else {
                GenericoService.Create('/lotacao', $scope.lotacao, function (response) {
                    $rootScope.$broadcast('preloader:hide');
                    if (response.status === 201 && response.data.success) {
                        $scope.showSimpleToast(response.data.message);
                        $location.path('/lotacao/gestao');
                    } else if (response.status === 400) {
                        $scope.showSimpleToast(response.data.message);
                        $location.path('/lotacao/formulario');
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