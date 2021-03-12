(function () {
    'use strict';

    angular.module('app.page')
        .controller('valeTransporteFormCtrl', ['$scope', '$mdToast', '$location', '$state', '$rootScope', 'GenericoService', valeTransporteFormCtrl]);

    function valeTransporteFormCtrl($scope, $mdToast, $location, $state, $rootScope, GenericoService) {

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
            GenericoService.GetById('/valeTransporte/' + $state.params.id, function (response) {
                $rootScope.$broadcast('preloader:hide');
                if (response.status === 200) {
                    $scope.valeTransporte = response.data;
                    // $scope.consignado.bancoId =  $scope.consignado.banco.id;
                    // $scope.consignado.centroCustoId = $scope.consignado.centroCusto.id; 
                } else {
                    $scope.showSimpleToast("Tipo de Folha não encontrado na base");
                }
            });
        }

        if ($state.params && $state.params.detalhes) {
            $scope.detalhes = true;
        }

        $scope.goBack = function () {
            $location.path('/valeTransporte/gestao');
        }

        $scope.save = function () {
            $rootScope.$broadcast('preloader:active');
            if ($scope.valeTransporte.id) {
                GenericoService.Update('/valeTransporte', $scope.valeTransporte, function (response) {
                    $rootScope.$broadcast('preloader:hide');
                    if (response.status === 201 && response.data.success) {
                        $scope.showSimpleToast(response.data.message);
                        $location.path('/valeTransporte/gestao');
                    } else if (response.status === 400) {
                        $scope.showSimpleToast(response.data.message);
                        $location.path('valeTransporte/formulario/' + $state.params.id)
                    }
                });
            } else {

                GenericoService.Create('/valeTransporte', $scope.valeTransporte, function (response) {
                    $rootScope.$broadcast('preloader:hide');
                    if (response.status === 201 && response.data.success) {
                        $scope.showSimpleToast(response.data.message);
                        $location.path('/valeTransporte/gestao');
                    } else if (response.status === 400) {
                        $scope.showSimpleToast(response.data.message);
                        $location.path('/valeTransporte/formulario');
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