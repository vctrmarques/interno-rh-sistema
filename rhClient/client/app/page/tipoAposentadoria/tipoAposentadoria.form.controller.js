(function () {
    'use strict';

    angular.module('app.page')
        .controller('tipoAposentadoriaFormCtrl', ['$scope', '$mdToast', '$q', '$location', '$state', '$rootScope', 'GenericoService', tipoAposentadoriaFormCtrl]);

    function tipoAposentadoriaFormCtrl($scope, $mdToast, $q, $location, $state, $rootScope, GenericoService) {

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
            GenericoService.GetById('/tiposAposentadoria/' + $state.params.id, function (response) {
                $rootScope.$broadcast('preloader:hide');
                if (response.status === 200) {
                    $scope.tipoFolha = response.data;
                } else {
                    $scope.showSimpleToast("Tipo de Aposentadoria não encontrado na base");
                }
            });
        }

        if ($state.params && $state.params.detalhes) {
            $scope.detalhes = true;
        }

        $scope.goBack = function () {
            $location.path('/tipoAposentadoria/gestao');
        }

        $scope.save = function () {
        	console.log($scope.tipoFolha);
            $rootScope.$broadcast('preloader:active');
            if ($scope.tipoFolha.id) {
                GenericoService.Update('/tiposAposentadoria', $scope.tipoFolha, function (response) {
                    $rootScope.$broadcast('preloader:hide');
                    if (response.status === 201 && response.data.success) {
                        $scope.showSimpleToast(response.data.message);
                        $location.path('/tipoAposentadoria/gestao');
                    } else if (response.status === 400) {
                        $scope.showSimpleToast(response.data.message);
                        $location.path('tipoAposentadoria/formulario/' + $state.params.id)
                    }
                });
            } else {

                GenericoService.Create('/tiposAposentadoria', $scope.tipoFolha, function (response) {
                    $rootScope.$broadcast('preloader:hide');
                    if (response.status === 201 && response.data.success) {
                        $scope.showSimpleToast(response.data.message);
                        $location.path('/tipoAposentadoria/gestao');
                    } else if (response.status === 400) {
                        $scope.showSimpleToast(response.data.message);
                        $location.path('/tipoAposentadoria/formulario');
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
