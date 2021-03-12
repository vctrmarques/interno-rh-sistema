(function () {
    'use strict';

    angular.module('app.page')
        .controller('motivoFormCtrl', ['$scope', '$mdToast', '$location', '$state', '$rootScope', 'GenericoService', motivoFormCtrl]);

    function motivoFormCtrl($scope, $mdToast, $location, $state, $rootScope, GenericoService) {

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

        $scope.tipos = [
            { nome: "Efetivo" },
            { nome: "Comissão" }
        ];

        $scope.eventos = [
            { nome: "Nomeação" },
            { nome: "Promoção"},
            { nome: "Exoneração"}
        ];

        $scope.acessaTela();
        $scope.detalhes = false;

        if ($state.params && $state.params.id) {
            $rootScope.$broadcast('preloader:active');
            GenericoService.GetById('/motivo/' + $state.params.id, function (response) {
                $rootScope.$broadcast('preloader:hide');
                if (response.status === 200) {
                    $scope.motivo = response.data;
                } else {
                    $scope.showSimpleToast("Motivo não encontrado na base");
                }
            });
        }
        
        if ($state.params && $state.params.detalhes) {
            $scope.detalhes = true;
        }

        $scope.goBack = function () {
            $location.path('/motivo/gestao');
        }

        $scope.save = function () {
            $rootScope.$broadcast('preloader:active');
            if ($scope.motivo.id) {
                GenericoService.Update('/motivo', $scope.motivo, function (response) {
                    $rootScope.$broadcast('preloader:hide');
                    if (response.status === 201 && response.data.success) {
                        $scope.showSimpleToast(response.data.message);
                        $location.path('/motivo/gestao');
                    } else if (response.status === 400) {
                        $scope.showSimpleToast(response.data.message);
                        $location.path('motivo/formulario/' + $state.params.id)
                    }
                });
            } else {

                GenericoService.Create('/motivo', $scope.motivo, function (response) {
                    $rootScope.$broadcast('preloader:hide');
                    if (response.status === 201 && response.data.success) {
                        $scope.showSimpleToast(response.data.message);
                        $location.path('/motivo/gestao');
                    } else if (response.status === 400) {
                        $scope.showSimpleToast(response.data.message);
                        $location.path('/motivo/formulario');
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