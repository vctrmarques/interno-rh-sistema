(function () {
    'use strict';

    angular.module('app.page')
        .controller('codigoRecolhimentoFormCtrl', ['$scope', '$mdToast', '$location', '$state', '$rootScope', 'GenericoService', codigoRecolhimentoFormCtrl]);

    function codigoRecolhimentoFormCtrl($scope, $mdToast, $location, $state, $rootScope, GenericoService) {

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
            GenericoService.GetById('/codigoRecolhimento/' + $state.params.id, function (response) {
                $rootScope.$broadcast('preloader:hide');
                if (response.status === 200) {
                    $scope.codigoRecolhimento = response.data;
                    // $scope.consignado.bancoId =  $scope.consignado.banco.id;
                    // $scope.consignado.centroCustoId = $scope.consignado.centroCusto.id; 
                } else {
                    $scope.showSimpleToast("Código do Recolhimento não encontrado na base");
                }
            });
        }

        if ($state.params && $state.params.detalhes) {
            $scope.detalhes = true;
        }

        $scope.goBack = function () {
            $location.path('/codigoRecolhimento/gestao');
        }

        $scope.save = function () {
            $rootScope.$broadcast('preloader:active');
            if ($scope.codigoRecolhimento.id) {
                GenericoService.Update('/codigoRecolhimento', $scope.codigoRecolhimento, function (response) {
                    $rootScope.$broadcast('preloader:hide');
                    if (response.status === 201 && response.data.success) {
                        $scope.showSimpleToast(response.data.message);
                        $location.path('/codigoRecolhimento/gestao');
                    } else if (response.status === 400) {
                        $scope.showSimpleToast(response.data.message);
                        $location.path('codigoRecolhimento/formulario/' + $state.params.id)
                    }
                });
            } else {

                GenericoService.Create('/codigoRecolhimento', $scope.codigoRecolhimento, function (response) {
                    $rootScope.$broadcast('preloader:hide');
                    if (response.status === 201 && response.data.success) {
                        $scope.showSimpleToast(response.data.message);
                        $location.path('/codigoRecolhimento/gestao');
                    } else if (response.status === 400) {
                        $scope.showSimpleToast(response.data.message);
                        $location.path('/codigoRecolhimento/formulario');
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