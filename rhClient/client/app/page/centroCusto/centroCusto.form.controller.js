(function () {
    'use strict';

    angular.module('app.page')
        .controller('centroCustoFormCtrl', ['$scope', '$mdToast', '$location', '$state', '$rootScope', 'GenericoService', centroCustoFormCtrl]);

    function centroCustoFormCtrl($scope, $mdToast, $location, $state, $rootScope, GenericoService) {

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
            GenericoService.GetById('/centroCusto/' + $state.params.id, function (response) {
                $rootScope.$broadcast('preloader:hide');
                console.log(response.data);
                if (response.status === 200) {
                    $scope.centroCusto = response.data;
                } else {
                    $scope.showSimpleToast("Centro de Custo encontrado na base");
                }
            });
        }

        if ($state.params && $state.params.detalhes) {
            $scope.detalhes = true;
        }

        $scope.tipos = [{nome: "Analítico"}, 
                        {nome: "Sintético"}];

        $scope.goBack = function () {
            $location.path('/centroCusto/gestao');
        }

        $scope.save = function () {
            $rootScope.$broadcast('preloader:active');
            if ($scope.centroCusto.id) {
                GenericoService.Update('/centroCusto', $scope.centroCusto, function (response) {
                    $rootScope.$broadcast('preloader:hide');   
                    if (response.status === 201 && response.data.success) {
                        $scope.showSimpleToast(response.data.message);
                        $location.path('/centroCusto/gestao');
                    } else if (response.status === 400) {
                        $scope.showSimpleToast(response.data.message);
                        $location.path('centroCusto/formulario/' + $state.params.id)
                    }
                });
            } else {
                GenericoService.Create('/centroCusto', $scope.centroCusto, function (response) {
                    $rootScope.$broadcast('preloader:hide');
                    if (response.status === 201 && response.data.success) {
                        $scope.showSimpleToast(response.data.message);
                        $location.path('/centroCusto/gestao');
                    } else if (response.status === 400) {
                        $scope.showSimpleToast(response.data.message);
                        $location.path('/centroCusto/formulario');
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