(function () {
    'use strict';

    angular.module('app.page')
        .controller('sindicatoFormCtrl', ['$scope', '$mdToast', '$location', '$state', '$rootScope', 'GenericoService', sindicatoFormCtrl]);

    function sindicatoFormCtrl($scope, $mdToast, $location, $state, $rootScope, GenericoService) {

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

        $scope.patronais = [
            { nome: "Sim" },
            { nome: "Não" }
        ];

        $scope.acessaTela();
        $scope.detalhes = false;  

        if ($state.params && $state.params.id) {
            $rootScope.$broadcast('preloader:active');
            GenericoService.GetById('/sindicato/' + $state.params.id, function (response) {
                $rootScope.$broadcast('preloader:hide');
                if (response.status === 200) {
                    $scope.sindicato = response.data;
                    $scope.sindicato.unidadeFederativaId = $scope.sindicato.unidadeFederativa.id;
                } else {
                    $scope.showSimpleToast("Sindicato não encontrado na base");
                }
            });
        }

        if ($state.params && $state.params.detalhes) {
            $scope.detalhes = true;
        }

        $scope.goBack = function () {
            $location.path('/sindicato/gestao');
        }

        $scope.listUnidadeFederativa = {
            "data": []
        };

        $scope.unidadeFederativa = function () {
            $rootScope.$broadcast('preloader:active');
            GenericoService.GetAllDropdown('listaUnidadesFederativas', function (response) {
                if (response.status === 200) {
                    $scope.listUnidadeFederativa.data = response.data;
                    $rootScope.$broadcast('preloader:hide');
                } else if (response.status === 500) {
                    $scope.showSimpleToast("Falha no carregamento de dados, favor contate o administrador do sistema");
                }
            });
        }

        $scope.unidadeFederativa();

        $scope.save = function () {
            $rootScope.$broadcast('preloader:active');
            if ($scope.sindicato.id) {
                GenericoService.Update('/sindicato',$scope.sindicato, function (response) {
                    $rootScope.$broadcast('preloader:hide');
                    if (response.status === 201 && response.data.success) {
                        $scope.showSimpleToast(response.data.message);
                        $location.path('/sindicato/gestao');
                    } else if (response.status === 400) {
                        $scope.showSimpleToast(response.data.message);
                        $location.path('sindicato/formulario/' + $state.params.id)
                    }
                });
            } else {

                GenericoService.Create('/sindicato', $scope.sindicato, function (response) {
                    $rootScope.$broadcast('preloader:hide');
                    if (response.status === 201 && response.data.success) {
                        $scope.showSimpleToast(response.data.message);
                        $location.path('/sindicato/gestao');
                    } else if (response.status === 400) {
                        $scope.showSimpleToast(response.data.message);
                        $location.path('/sindicato/formulario');
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