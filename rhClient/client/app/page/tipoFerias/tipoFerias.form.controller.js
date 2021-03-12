(function () {
    'use strict';

    angular.module('app.page')
        .controller('tipoFeriasFormCtrl', ['$scope', '$mdToast', '$location', '$state', '$rootScope', 'GenericoService', tipoFeriasFormCtrl]);

    function tipoFeriasFormCtrl($scope, $mdToast, $location, $state, $rootScope, GenericoService) {

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

        $scope.decimosTerceiros = [
            { nome: "Sim" },
            { nome: "Não" }
        ];

        $scope.coletivos = [
            { nome: "Sim" },
            { nome: "Não"}
        ];

        $scope.licencasPremios = [
            { nome: "Sim" },
            { nome: "Não"}
        ];

        $scope.acessaTela();
        $scope.detalhes = false;

        if ($state.params && $state.params.id) {
            $rootScope.$broadcast('preloader:active');
            GenericoService.GetById('/tipoFerias/' + $state.params.id, function (response) {
                $rootScope.$broadcast('preloader:hide');
                if (response.status === 200) {
                    $scope.tipoFerias = response.data;
                } else {
                    $scope.showSimpleToast("Tipo de Férias não encontrado na base");
                }
            });
        }
        
        if ($state.params && $state.params.detalhes) {
            $scope.detalhes = true;
        }

        $scope.goBack = function () {
            $location.path('/tipoFerias/gestao');
        }

        $scope.save = function () {
            $rootScope.$broadcast('preloader:active');
            if ($scope.tipoFerias.id) {
                GenericoService.Update('/tipoFerias', $scope.tipoFerias, function (response) {
                    $rootScope.$broadcast('preloader:hide');
                    if (response.status === 201 && response.data.success) {
                        $scope.showSimpleToast(response.data.message);
                        $location.path('/tipoFerias/gestao');
                    } else if (response.status === 400) {
                        $scope.showSimpleToast(response.data.message);
                        $location.path('tipoFerias/formulario/' + $state.params.id)
                    }
                });
            } else {

                GenericoService.Create('/tipoFerias', $scope.tipoFerias, function (response) {
                    $rootScope.$broadcast('preloader:hide');
                    if (response.status === 201 && response.data.success) {
                        $scope.showSimpleToast(response.data.message);
                        $location.path('/tipoFerias/gestao');
                    } else if (response.status === 400) {
                        $scope.showSimpleToast(response.data.message);
                        $location.path('/tipoFerias/formulario');
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