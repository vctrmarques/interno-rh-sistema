(function () {
    'use strict';

    angular.module('app.page')
        .controller('transFuncionarioFormCtrl', ['$scope', '$mdToast', '$location', '$state', '$rootScope', 'GenericoService', transFuncionarioFormCtrl]);

    function transFuncionarioFormCtrl($scope, $mdToast, $location, $state, $rootScope, GenericoService) {

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

        $scope.nomeFuncionarioBusca;

        $scope.acessaTela();
        $scope.detalhes = false;
        $scope.list = {
            "empresas": [],
            "lotacoes": [],
            "transferencias": []
        }

        if ($state.params && $state.params.id) {
            $rootScope.$broadcast('preloader:active');
            GenericoService.GetById('/funcionario/' + $state.params.id, function (response) {
                $rootScope.$broadcast('preloader:hide');
                if (response.status === 200) {
                    $scope.funcionario = response.data;                    
                } else {
                    $scope.showSimpleToast("Transferência não encontrada.");
                }
            });
        }

        if ($state.params && $state.params.detalhes) {
            $scope.detalhes = true;
            $rootScope.$broadcast('preloader:active');
            GenericoService.GetById('/transferencias/' + $state.params.id, function (response) {
                $rootScope.$broadcast('preloader:hide');
                if (response.status === 200) {
                    $scope.list.transferencias = response.data;
                } else {
                    $scope.showSimpleToast("Transferência não encontrada.");
                }
            });
        }

        $scope.goBack = function () {
            $location.path('/transferenciaFuncionario/gestao');
        }

        $scope.empresas = function (){
            $rootScope.$broadcast('preloader:active');
            GenericoService.GetAllDropdown('listaEmpresasNaoMatrizes', function (response) {
                if (response.status === 200) {
                    $scope.list.empresas = response.data;
                    $rootScope.$broadcast('preloader:hide');
                } else if (response.status === 500) {
                    $scope.showSimpleToast("Falha no carregamento de dados, favor contate o administrador do sistema");
                }
            });
        }
        $scope.empresas();
        
        $scope.getLocacoesByEmpresa = function (id) {
            $rootScope.$broadcast('preloader:active');

            GenericoService.GetAllDropdown('listaLotacoes/'+id, function (response) {
                if (response.status === 200) {
                    console.log(response.data);
                    
                    $scope.list.lotacoes = response.data;
                    $rootScope.$broadcast('preloader:hide');
                } else if (response.status === 500) {
                    $scope.showSimpleToast("Falha no carregamento de dados, favor contate o administrador do sistema");
                }
            });
        }
    

        $scope.save = function () {            
            $rootScope.$broadcast('preloader:active');
            $scope.transferencia = {
                "funcionarioId": $scope.funcionario.id,
                "empresaId": $scope.empresaFilialDestino,
                "lotacaoId": $scope.lotacaoDestino,
            }
            GenericoService.Create('/transferenciaFuncionario', $scope.transferencia, function (response) {
                $rootScope.$broadcast('preloader:hide');
                if (response.status === 201 && response.data.success) {
                    $scope.showSimpleToast(response.data.message);
                    $location.path('/transferenciaFuncionario/gestao');
                } else if (response.status === 400) {
                    $scope.showSimpleToast(response.data.message);
                    $location.path('/transferenciaFuncionario/gestao');
                }
            });
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
