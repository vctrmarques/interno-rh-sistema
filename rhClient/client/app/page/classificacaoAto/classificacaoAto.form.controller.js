(function () {
    'use strict';

    angular.module('app.page')
        .controller('classificacaoAtoFormCtrl', ['$scope', '$mdToast', '$location', '$state', '$rootScope', 'GenericoService', classificacaoAtoFormCtrl]);

    function classificacaoAtoFormCtrl($scope, $mdToast, $location, $state, $rootScope, GenericoService) {

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
            GenericoService.GetById('/classificacaoAto/' + $state.params.id, function (response) {
                $rootScope.$broadcast('preloader:hide');
                if (response.status === 200) {
                    $scope.classificacaoAto = response.data;
                    // $scope.consignado.bancoId =  $scope.consignado.banco.id;
                    // $scope.consignado.centroCustoId = $scope.consignado.centroCusto.id; 
                } else {
                    $scope.showSimpleToast("Classificação de Ato não encontrada na base");
                }
            });
        }

        if ($state.params && $state.params.detalhes) {
            $scope.detalhes = true;
        }

        $scope.goBack = function () {
            $location.path('/classificacaoBeneficio/gestao');
        }

        $scope.save = function () {
            $rootScope.$broadcast('preloader:active');
            if ($scope.classificacaoAto.id) {
                GenericoService.Update('/classificacaoAto', $scope.classificacaoAto, function (response) {
                    $rootScope.$broadcast('preloader:hide');
                    if (response.status === 201 && response.data.success) {
                        $scope.showSimpleToast(response.data.message);
                        $location.path('/classificacaoBeneficio/gestao');
                    } else if (response.status === 400) {
                        $scope.showSimpleToast(response.data.message);
                        $location.path('classificacaoBeneficio/formulario/' + $state.params.id)
                    }
                });
            } else {

                GenericoService.Create('/classificacaoAto', $scope.classificacaoAto, function (response) {
                    $rootScope.$broadcast('preloader:hide');
                    if (response.status === 201 && response.data.success) {
                        $scope.showSimpleToast(response.data.message);
                        $location.path('/classificacaoBeneficio/gestao');
                    } else if (response.status === 400) {
                        $scope.showSimpleToast(response.data.message);
                        $location.path('/classificacaoBeneficio/formulario');
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