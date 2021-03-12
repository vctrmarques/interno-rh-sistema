(function () {
    'use strict';

    angular.module('app.page')
        .controller('compensacaoFormCtrl', ['$scope', '$mdToast', '$location', '$state', '$rootScope', 'GenericoService', compensacaoFormCtrl]);

    function compensacaoFormCtrl($scope, $mdToast, $location, $state, $rootScope, GenericoService) {

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
            localStorage.setItem('idTomador', JSON.stringify($state.params.id));
            $rootScope.$broadcast('preloader:active');
            GenericoService.GetById('/compensacao/' + $state.params.id, function (response) {
                $rootScope.$broadcast('preloader:hide');
                if (response.status === 200) {
                    $scope.compensacao = response.data;

                    $scope.compensacao.competencia = moment().add(response.data.competencia);

                    if($scope.compensacao.dataCompensacaoInicial){
                        $scope.compensacao.dataCompensacaoInicial = moment().add(response.data.dataCompensacaoInicial);
                    }

                    if($scope.compensacao.dataCompensacaoFinal){
                        $scope.compensacao.dataCompensacaoFinal = moment().add(response.data.dataCompensacaoFinal);
                    }

                } else {
                    $location.path('/compensacao/formulario');
                }
            });
        }

        if ($state.params && $state.params.detalhes) {
            $scope.detalhes = true;
        }

        $scope.goBack = function () {
            $location.path('/tomadorServico/gestao');
        }

        $scope.save = function () {
            $rootScope.$broadcast('preloader:active');
            if ($scope.compensacao.id) {
                GenericoService.Update('/compensacao', $scope.compensacao, function (response) {
                    $rootScope.$broadcast('preloader:hide');
                    if (response.status === 201 && response.data.success) {
                        $scope.showSimpleToast(response.data.message);
                        $location.path('/tomadorServico/gestao/');
                    } else if (response.status === 400) {
                        $scope.showSimpleToast(response.data.message);
                        $location.path('/compensacao/formulario/');
                    }
                });
            } else {
                $scope.compensacao.tomadorServicoId = JSON.parse(localStorage.getItem('idTomador'));
                GenericoService.Create('/compensacao', $scope.compensacao, function (response) {
                    $rootScope.$broadcast('preloader:hide');
                    if (response.status === 201 && response.data.success) {
                        $scope.showSimpleToast(response.data.message);
                        $location.path('/tomadorServico/gestao');
                    } else if (response.status === 400) {
                        $scope.showSimpleToast(response.data.message);
                        $location.path('/compensacao/formulario');
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