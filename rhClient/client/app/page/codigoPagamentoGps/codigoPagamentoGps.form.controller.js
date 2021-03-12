(function () {
    'use strict';

    angular.module('app.page')
        .controller('codigoPagamentoGpsFormCtrl', ['$scope', '$mdToast', '$location', '$state', '$rootScope', 'GenericoService', codigoPagamentoGpsFormCtrl]);

    function codigoPagamentoGpsFormCtrl($scope, $mdToast, $location, $state, $rootScope, GenericoService) {

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

        if ($state.params && $state.params.id) {
            $rootScope.$broadcast('preloader:active');
            GenericoService.GetById('/codigoPagamentoGps/' + $state.params.id, function (response) {
                $rootScope.$broadcast('preloader:hide');
                if (response.status === 200) {
                    $scope.codigoPagamentoGps = response.data;
                    // $scope.consignado.bancoId =  $scope.consignado.banco.id;
                    // $scope.consignado.centroCustoId = $scope.consignado.centroCusto.id; 
                } else {
                    $scope.showSimpleToast("Código do Pagamento Gps não encontrado na base");
                }
            });
        }

        if ($state.params && $state.params.detalhes) {
            $scope.detalhes = true;
        }

        $scope.goBack = function () {
            $location.path('/codigoPagamentoGps/gestao');
        }

        $scope.save = function () {
            $rootScope.$broadcast('preloader:active');
            if ($scope.codigoPagamentoGps.id) {
                GenericoService.Update('/codigoPagamentoGps', $scope.codigoPagamentoGps, function (response) {
                    $rootScope.$broadcast('preloader:hide');
                    if (response.status === 201 && response.data.success) {
                        $scope.showSimpleToast(response.data.message);
                        $location.path('/codigoPagamentoGps/gestao');
                    } else if (response.status === 400) {
                        $scope.showSimpleToast(response.data.message);
                        $location.path('codigoPagamentoGps/formulario/' + $state.params.id)
                    }
                });
            } else {

                GenericoService.Create('/codigoPagamentoGps', $scope.codigoPagamentoGps, function (response) {
                    $rootScope.$broadcast('preloader:hide');
                    if (response.status === 201 && response.data.success) {
                        $scope.showSimpleToast(response.data.message);
                        $location.path('/codigoPagamentoGps/gestao');
                    } else if (response.status === 400) {
                        $scope.showSimpleToast(response.data.message);
                        $location.path('/codigoPagamentoGps/formulario');
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