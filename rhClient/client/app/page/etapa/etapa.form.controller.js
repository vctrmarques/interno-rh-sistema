(function () {
    'use strict';

    angular.module('app.page')
        .controller('etapaFormCtrl', ['$scope', '$mdToast', '$location', '$state', '$rootScope', 'GenericoService', etapaFormCtrl]);

    function etapaFormCtrl($scope, $mdToast, $location, $state, $rootScope, GenericoService) {

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
        $scope.processos = [{nome: "Avaliação"}, 
        {nome: "Admissão"},
        {nome: "Demissão"},
        {nome: "Promoção"},
        {nome: "Transferênia"},
        {nome: "Treinamento"},
        {nome: "Seleção"}];

        if ($state.params && $state.params.id) {
            $rootScope.$broadcast('preloader:active');
            GenericoService.GetById('/etapa/' + $state.params.id, function (response) {
                $rootScope.$broadcast('preloader:hide');
                if (response.status === 200) {
                    $scope.etapa = response.data;
                } else {
                    $scope.showSimpleToast("Etapa não encontrada na base");
                }
            });
        }

        if ($state.params && $state.params.detalhes) {
            $scope.detalhes = true;
        }

        $scope.goBack = function () {
            $location.path('/etapa/gestao');
        }

        $scope.save = function () {
            $rootScope.$broadcast('preloader:active');
            if ($scope.etapa.id) {
                GenericoService.Update('/etapa',$scope.etapa, function (response) {
                    $rootScope.$broadcast('preloader:hide');
                    if (response.status === 201 && response.data.success) {
                        $scope.showSimpleToast(response.data.message);
                        $location.path('/etapa/gestao');
                    } else if (response.status === 400) {
                        $scope.showSimpleToast(response.data.message);
                        $location.path('etapa/formulario/' + $state.params.id)
                    }
                });
            } else {

                GenericoService.Create('/etapa', $scope.etapa, function (response) {
                    $rootScope.$broadcast('preloader:hide');
                    if (response.status === 201 && response.data.success) {
                        $scope.showSimpleToast(response.data.message);
                        $location.path('/etapa/gestao');
                    } else if (response.status === 400) {
                        $scope.showSimpleToast(response.data.message);
                        $location.path('/etapa/formulario');
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