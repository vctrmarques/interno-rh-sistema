(function () {
    'use strict';

    angular.module('app.page')
        .controller('classificacaoAgenteNocivoFormCtrl', ['$scope', '$mdToast', '$location', '$state', '$rootScope', 'GenericoService', classificacaoAgenteNocivoFormCtrl]);

    function classificacaoAgenteNocivoFormCtrl($scope, $mdToast, $location, $state, $rootScope, GenericoService) {

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
            GenericoService.GetById('/classificacaoAgenteNocivo/' + $state.params.id, function (response) {
                $rootScope.$broadcast('preloader:hide');
                if (response.status === 200) {
                    $scope.classificacaoAgenteNocivo = response.data;
                } else {
                    $scope.showSimpleToast("Classificação não encontrada na base");
                }
            });
        }

        if ($state.params && $state.params.detalhes) {
            $scope.detalhes = true;
        }

        $scope.goBack = function () {
            $location.path('/classificacaoAgenteNocivo/gestao');
        }

        $scope.save = function () {
            $rootScope.$broadcast('preloader:active');
            if ($scope.classificacaoAgenteNocivo.id) {
                GenericoService.Update('/classificacaoAgenteNocivo', $scope.classificacaoAgenteNocivo, function (response) {
                    $rootScope.$broadcast('preloader:hide');
                    if (response.status === 201 && response.data.success) {
                        $scope.showSimpleToast(response.data.message);
                        $location.path('/classificacaoAgenteNocivo/gestao');
                    } else if (response.status === 400) {
                        $scope.showSimpleToast(response.data.message);
                        $location.path('classificacaoAgenteNocivo/formulario/' + $state.params.id)
                    }
                });
            } else {
                GenericoService.Create('/classificacaoAgenteNocivo', $scope.classificacaoAgenteNocivo, function (response) {
                    $rootScope.$broadcast('preloader:hide');
                    if (response.status === 201 && response.data.success) {
                        $scope.showSimpleToast(response.data.message);
                        $location.path('/classificacaoAgenteNocivo/gestao');
                    } else if (response.status === 400) {
                        $scope.showSimpleToast(response.data.message);
                        $location.path('/classificacaoAgenteNocivo/formulario');
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