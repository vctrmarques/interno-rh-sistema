(function () {
    'use strict';

    angular.module('app.page')
        .controller('subCategoriaDoencaFormCtrl', ['$scope', '$mdToast', '$location', '$state', '$rootScope', 'GenericoService', subCategoriaDoencaFormCtrl]);

    function subCategoriaDoencaFormCtrl($scope, $mdToast, $location, $state, $rootScope, GenericoService) {

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
            GenericoService.GetById('/subCategoriaDoenca/' + $state.params.id, function (response) {
                $rootScope.$broadcast('preloader:hide');
                if (response.status === 200) {
                    $scope.subCategoriaDoenca = response.data;
                } else {
                    $scope.showSimpleToast("SubCategoriaDoenca não encontrado na base");
                }
            });
        }

        if ($state.params && $state.params.detalhes) {
            $scope.detalhes = true;
        }

        $scope.goBack = function () {
            $location.path('/subCategoriaDoenca/gestao');
        }

        $scope.save = function () {
            $rootScope.$broadcast('preloader:active');
            if ($scope.subCategoriaDoenca.id) {
                GenericoService.Update('/subCategoriaDoenca', $scope.subCategoriaDoenca, function (response) {
                    $rootScope.$broadcast('preloader:hide');
                    if (response.status === 201 && response.data.success) {
                        $scope.showSimpleToast(response.data.message);
                        $location.path('/subCategoriaDoenca/gestao');
                    } else if (response.status === 400) {
                        $scope.showSimpleToast(response.data.message);
                        $location.path('subCategoriaDoenca/formulario/' + $state.params.id)
                    }
                });
            } else {
                GenericoService.Create('/subCategoriaDoenca', $scope.subCategoriaDoenca, function (response) {
                    $rootScope.$broadcast('preloader:hide');
                    if (response.status === 201 && response.data.success) {
                        $scope.showSimpleToast(response.data.message);
                        $location.path('/subCategoriaDoenca/gestao');
                    } else if (response.status === 400) {
                        $scope.showSimpleToast(response.data.message);
                        $location.path('/subCategoriaDoenca/formulario');
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