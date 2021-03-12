(function () {
    'use strict';

    angular.module('app.page')
        .controller('equipamentoProtecaoColetivaFormCtrl', ['$scope', '$mdToast', '$location', '$state', '$rootScope', 'GenericoService', equipamentoProtecaoColetivaFormCtrl]);

    function equipamentoProtecaoColetivaFormCtrl($scope, $mdToast, $location, $state, $rootScope, GenericoService) {

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
            GenericoService.GetById('/equipamentoProtecaoColetiva/' + $state.params.id, function (response) {
                $rootScope.$broadcast('preloader:hide');
                if (response.status === 200) {
                    $scope.equipamentoProtecaoColetiva = response.data;

                    $scope.equipamentoProtecaoColetiva.validade = moment(response.data.validade);
                } else {
                    $scope.showSimpleToast("Classificação não encontrada na base");
                }
            });
        }

        if ($state.params && $state.params.detalhes) {
            $scope.detalhes = true;
        }

        $scope.goBack = function () {
            $location.path('/equipamentoProtecaoColetiva/gestao');
        }

        $scope.save = function () {
            $rootScope.$broadcast('preloader:active');
            if ($scope.equipamentoProtecaoColetiva.id) {
                GenericoService.Update('/equipamentoProtecaoColetiva', $scope.equipamentoProtecaoColetiva, function (response) {
                    $rootScope.$broadcast('preloader:hide');
                    if (response.status === 201 && response.data.success) {
                        $scope.showSimpleToast(response.data.message);
                        $location.path('/equipamentoProtecaoColetiva/gestao');
                    } else if (response.status === 400) {
                        $scope.showSimpleToast(response.data.message);
                        $location.path('equipamentoProtecaoColetiva/formulario/' + $state.params.id)
                    }
                });
            } else {
                GenericoService.Create('/equipamentoProtecaoColetiva', $scope.equipamentoProtecaoColetiva, function (response) {
                    $rootScope.$broadcast('preloader:hide');
                    if (response.status === 201 && response.data.success) {
                        $scope.showSimpleToast(response.data.message);
                        $location.path('/equipamentoProtecaoColetiva/gestao');
                    } else if (response.status === 400) {
                        $scope.showSimpleToast(response.data.message);
                        $location.path('/equipamentoProtecaoColetiva/formulario');
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