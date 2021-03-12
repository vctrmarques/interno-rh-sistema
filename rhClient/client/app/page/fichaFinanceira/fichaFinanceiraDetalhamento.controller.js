(function () {
    'use strict';

    angular.module('app.page')
        .controller('fichaFinanceiraDetalhamentoCtrl', ['$scope', 'configValue', '$mdToast', '$location', '$http', '$state', '$rootScope', 'FileUploader', 'GenericoService', fichaFinanceiraDetalhamentoCtrl]);

    function fichaFinanceiraDetalhamentoCtrl($scope, configValue, $mdToast, $location, $http, $state, $rootScope, FileUploader, GenericoService) {

        $scope.acessaTela = function () {
            GenericoService.VerificaPermissao('/usuario/verificaPermissao', 'ROLE_ADMIN').then(
                function (response) {
                    if (response.status === 200) {
                        if ($state.params && $state.params.idFuncionario && $state.params.ano) {
                            $rootScope.$broadcast('preloader:active');
                            $scope.anoSelecionado = $state.params.ano;
                            $scope.idFuncionario = $state.params.idFuncionario;
                            $scope.carregaAnos();
                        }
                    }
                }, function errorCallback(response) {
                    if (response.status === 400) {
                        $location.path('page/403');
                    }
                });
        }
        $scope.acessaTela();

        $scope.carregaAnos = function () {
            GenericoService.GetAllDropdown('competencia/anos', function (response) {
                if (response.status === 200) {
                    $scope.anos = response.data;
                    $rootScope.$broadcast('preloader:hide');
                }
            })
        }

        $scope.showSimpleToast = function (textContent) {
            $mdToast.show(
                $mdToast.simple()
                    .textContent(textContent)
                    .position('top right')
                    .hideDelay(3000)
            );
        };

        $scope.goBack = function () {
            $location.path('/fichaFinanceira/gestao');
        }
    }
})(); 