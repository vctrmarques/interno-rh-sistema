(function () {
    'use strict';

    angular.module('app.page')
        .controller('equipamentoProtecaoIndividualFormCtrl', ['$scope', '$mdToast', '$location', '$state', '$rootScope', 'GenericoService', equipamentoProtecaoIndividualFormCtrl]);

    function equipamentoProtecaoIndividualFormCtrl($scope, $mdToast, $location, $state, $rootScope, GenericoService) {

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
            GenericoService.GetById('/equipamentoProtecaoIndividual/' + $state.params.id, function (response) {
                $rootScope.$broadcast('preloader:hide');
                if (response.status === 200) {

                    $scope.equipamentoProtecaoIndividual = response.data;

                    $scope.equipamentoProtecaoIndividual.validade = moment(response.data.validade);
                    
                } else {
                    $scope.showSimpleToast("Classificação não encontrada na base");
                }
            });
        }

        if ($state.params && $state.params.detalhes) {
            $scope.detalhes = true;
        }

        $scope.goBack = function () {
            $location.path('/equipamentoProtecaoIndividual/gestao');
        }

        $scope.save = function () {
            $rootScope.$broadcast('preloader:active');
            if ($scope.equipamentoProtecaoIndividual.id) {
                GenericoService.Update('/equipamentoProtecaoIndividual', $scope.equipamentoProtecaoIndividual, function (response) {
                    $rootScope.$broadcast('preloader:hide');
                    if (response.status === 201 && response.data.success) {
                        $scope.showSimpleToast(response.data.message);
                        $location.path('/equipamentoProtecaoIndividual/gestao');
                    } else if (response.status === 400) {
                        $scope.showSimpleToast(response.data.message);
                        $location.path('equipamentoProtecaoIndividual/formulario/' + $state.params.id)
                    }
                });
            } else {
                GenericoService.Create('/equipamentoProtecaoIndividual', $scope.equipamentoProtecaoIndividual, function (response) {
                    $rootScope.$broadcast('preloader:hide');
                    if (response.status === 201 && response.data.success) {
                        $scope.showSimpleToast(response.data.message);
                        $location.path('/equipamentoProtecaoIndividual/gestao');
                    } else if (response.status === 400) {
                        $scope.showSimpleToast(response.data.message);
                        $location.path('/equipamentoProtecaoIndividual/formulario');
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