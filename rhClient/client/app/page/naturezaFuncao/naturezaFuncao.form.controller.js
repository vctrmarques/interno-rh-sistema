(function () {
    'use strict';

    angular.module('app.page')
        .controller('naturezaFuncaoFormCtrl', ['$scope', '$mdToast', '$location', '$state', '$rootScope', 'GenericoService', naturezaFuncaoFormCtrl]);

    function naturezaFuncaoFormCtrl($scope, $mdToast, $location, $state, $rootScope, GenericoService) {

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
            GenericoService.GetById('/naturezaFuncao/' + $state.params.id, function (response) {
                $rootScope.$broadcast('preloader:hide');
                if (response.status === 200) {
                    $scope.naturezaFuncao = response.data;
                } else {
                    $scope.showSimpleToast("Natureza da Função não encontrado na base");
                }
            });
        }

        if ($state.params && $state.params.detalhes) {
            $scope.detalhes = true;
        }

        $scope.goBack = function () {
            $location.path('/naturezaFuncao/gestao');
        }

        $scope.save = function () {
            $rootScope.$broadcast('preloader:active');
            if ($scope.naturezaFuncao.id) {
                GenericoService.Update('/naturezaFuncao', $scope.naturezaFuncao, function (response) {
                    $rootScope.$broadcast('preloader:hide');
                    if (response.status === 201 && response.data.success) {
                        $scope.showSimpleToast(response.data.message);
                        $location.path('/naturezaFuncao/gestao');
                    } else if (response.status === 400) {
                        $scope.showSimpleToast(response.data.message);
                        $location.path('naturezaFuncao/formulario/' + $state.params.id)
                    }
                });
            } else {
                GenericoService.Create('/naturezaFuncao', $scope.naturezaFuncao, function (response) {
                    $rootScope.$broadcast('preloader:hide');
                    if (response.status === 201 && response.data.success) {
                        $scope.showSimpleToast(response.data.message);
                        $location.path('/naturezaFuncao/gestao');
                    } else if (response.status === 400) {
                        $scope.showSimpleToast(response.data.message);
                        $location.path('/naturezaFuncao/formulario');
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