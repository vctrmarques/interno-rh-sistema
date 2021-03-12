(function () {
    'use strict';

    angular.module('app.page')
        .controller('convenioFormCtrl', ['$scope', '$mdToast', '$location', '$state', '$rootScope', 'GenericoService', convenioFormCtrl]);

    function convenioFormCtrl($scope, $mdToast, $location, $state, $rootScope, GenericoService) {

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
            GenericoService.GetById('/convenio/' + $state.params.id, function (response) {
                $rootScope.$broadcast('preloader:hide');
                if (response.status === 200) {
                    $scope.convenio = response.data;
                } else {
                    $scope.showSimpleToast("Classificação não encontrada na base");
                }
            });
        }

        if ($state.params && $state.params.detalhes) {
            $scope.detalhes = true;
        }

        $scope.goBack = function () {
            $location.path('/convenio/gestao');
        }

        $scope.save = function () {
            $rootScope.$broadcast('preloader:active');
            if ($scope.convenio.id) {
                GenericoService.Update('/convenio', $scope.convenio, function (response) {
                    $rootScope.$broadcast('preloader:hide');
                    if (response.status === 201 && response.data.success) {
                        $scope.showSimpleToast(response.data.message);
                        $location.path('/convenio/gestao');
                    } else if (response.status === 400) {
                        $scope.showSimpleToast(response.data.message);
                        $location.path('convenio/formulario/' + $state.params.id)
                    }
                });
            } else {
                GenericoService.Create('/convenio', $scope.convenio, function (response) {
                    $rootScope.$broadcast('preloader:hide');
                    if (response.status === 201 && response.data.success) {
                        $scope.showSimpleToast(response.data.message);
                        $location.path('/convenio/gestao');
                    } else if (response.status === 400) {
                        $scope.showSimpleToast(response.data.message);
                        $location.path('/convenio/formulario');
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