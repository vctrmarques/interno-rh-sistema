(function () {
    'use strict';

    angular.module('app.page')
        .controller('especialidadeMedicaFormCtrl', ['$scope', '$mdToast', '$location', '$state', '$rootScope', 'RestService', especialidadeMedicaFormCtrl]);

    function especialidadeMedicaFormCtrl($scope, $mdToast, $location, $state, $rootScope, RestService) {

        // Checa as regras de acesso da tela
        RestService.Get('/usuario/verificaPermissao/por/menu', { params: { menu: 'Especialidade Médica' } })
            .then(function (response) {
                if (response.status === 200 && response.data) {

                    $scope.usuarioAdm = response.data.usuarioAdm;
                    $scope.podeGerenciar = false;

                    if (response.data.papeis)
                    $scope.podeGerenciar = response.data.papeis.includes('ROLE_ESPECIALIDADE_MEDICA_GESTAO') ? true : false;

                    $scope.autorizado = ($scope.usuarioAdm || $scope.podeGerenciar);

                    if ($scope.autorizado){
                        $scope.loadList();
                    } else {
                        $location.path('page/403');
                    }
                }
            }, function errorCallback(response) {
                if (response.status === 400) {
                    $scope.showSimpleToast(response.data.message);
                }
            });

        //Construtor do objeto especialidadeMedica
        $scope.especialidadeMedicaInit = {
            codigo: null,
            nome: null
        }
        $scope.especialidadeMedica = angular.copy($scope.especialidadeMedicaInit);

        $scope.detalhes = false;
        if ($state.params && $state.params.detalhes) {
            $scope.detalhes = true;
        }

        if ($state.params && $state.params.id) {
            $rootScope.$broadcast('preloader:active');
            RestService.Get('/especialidadeMedica/' + $state.params.id)
                .then(function successCallback(response) {
                    $rootScope.$broadcast('preloader:hide');
                    $scope.especialidadeMedica = response.data;
                }, function errorCallback(response) {
                    $rootScope.$broadcast('preloader:hide');
                    if (response.status === 400) {
                        $scope.showSimpleToast(response.data.message);
                    }
                });
        }

        $scope.save = function () {
            $rootScope.$broadcast('preloader:active');
            if ($scope.especialidadeMedica.id) {
                RestService.Update('/especialidadeMedica', $scope.especialidadeMedica)
                    .then(function successCallback(response) {
                        $rootScope.$broadcast('preloader:hide');
                        if (response.status === 201 && response.data.success) {
                            $scope.showSimpleToast(response.data.message);
                            $location.path('/especialidadeMedica/gestao');
                        }
                    }, function errorCallback(response) {
                        $rootScope.$broadcast('preloader:hide');
                        if (response.status === 400) {
                            $scope.showSimpleToast(response.data.message);
                        }
                    });
            } else {
                RestService.Create('/especialidadeMedica', $scope.especialidadeMedica)
                    .then(function successCallback(response) {
                        $rootScope.$broadcast('preloader:hide');
                        if (response.status === 201 && response.data.success) {
                            $scope.showSimpleToast(response.data.message);
                            $location.path('/especialidadeMedica/gestao');
                        }
                    }, function errorCallback(response) {
                        $rootScope.$broadcast('preloader:hide');
                        if (response.status === 400) {
                            $scope.showSimpleToast(response.data.message);
                        }
                    });
            }
        }

        $scope.goBack = function () {
            $location.path('/especialidadeMedica/gestao');
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