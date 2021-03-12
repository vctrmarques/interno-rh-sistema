(function () {
    'use strict';

    angular.module('app.page')
        .controller('referenciaSalarialFormCtrl', ['$scope', '$mdToast', '$location', '$state', '$rootScope', 'GenericoService', referenciaSalarialFormCtrl]);

    function referenciaSalarialFormCtrl($scope, $mdToast, $location, $state, $rootScope, GenericoService) {

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
            GenericoService.GetById('/nivelSalarial/' + $state.params.id, function (response) {
                $rootScope.$broadcast('preloader:hide');
                if (response.status === 200) {
                    $scope.referenciaSalarial = response.data;

                    if($scope.referenciaSalarial.mesAnoCompetencia)
                        $scope.referenciaSalarial.mesAnoCompetencia = moment(response.data.mesAnoCompetencia, 'YYYY/MM/DD');
                } else {
                    $scope.showSimpleToast("Categoria de Doença não encontrada na base");
                }
            });
        }

        if ($state.params && $state.params.detalhes) {
            $scope.detalhes = true;
        }

        $scope.goBack = function () {
            $location.path('/referenciaSalarial/gestao');
        }

        $scope.save = function () {
            $rootScope.$broadcast('preloader:active');
            if ($scope.referenciaSalarial.id) {
                GenericoService.Update('/nivelSalarial',$scope.referenciaSalarial, function (response) {
                    $rootScope.$broadcast('preloader:hide');
                    if (response.status === 201 && response.data.success) {
                        $scope.showSimpleToast(response.data.message);
                        $location.path('/referenciaSalarial/gestao');
                    } else if (response.status === 400) {
                        $scope.showSimpleToast(response.data.message);
                        $location.path('referenciaSalarial/formulario/' + $state.params.id)
                    }
                });
            } else {
                if($scope.referenciaSalarial.mesAnoCompetencia)
                    $scope.referenciaSalarial.mesAnoCompetencia = moment($scope.referenciaSalarial.mesAnoCompetencia).format('YYYY/MM').toString();
              
                
                GenericoService.Create('/nivelSalarial', $scope.referenciaSalarial, function (response) {
                    $rootScope.$broadcast('preloader:hide');
                    if (response.status === 201 && response.data.success) {
                        $scope.showSimpleToast(response.data.message);
                        $location.path('/referenciaSalarial/gestao');
                    } else if (response.status === 400) {
                        $scope.showSimpleToast(response.data.message);
                        $location.path('/referenciaSalarial/formulario');
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