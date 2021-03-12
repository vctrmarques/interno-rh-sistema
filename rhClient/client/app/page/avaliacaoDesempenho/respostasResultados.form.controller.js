(function () {
    'use strict';
    angular.module('app.page')
        .controller('respostasResultadosFormCtrl', ['$q', '$scope', '$mdToast', '$location', '$state', '$rootScope', 'GenericoService', '$mdDialog', respostasResultadosFormCtrl]);

    function respostasResultadosFormCtrl($q, $scope, $mdToast, $location, $state, $rootScope, GenericoService, $mdDialog) {
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
        };
        $scope.valesTransporte = [];
        $scope.list = {
            "empresa": [],
            "lotacao": [],
            "modelo": []
        };
        $scope.data = {
            group1: 'Sim/Não',
            group2: 'Certo/Errado',
            group4: 'Bom/Regular/Ruim',
            group5: 'Muito Bom/Bom/Regular/Ruim/Pessimo',
            group6: 'Nunca/As Vezes/Sempre',
            group7: 'Resposta Livre'
        };
        $scope.acessaTela();
        $scope.detalhes = false;
        $scope.edit = false;
        $scope.Lotacao = function (empresaFilialId) {
            $scope.loadedLotacao = false;
            $rootScope.$broadcast('preloader:active');
            GenericoService.GetById('/listaLotacoes/' + empresaFilialId, function (response) {
                $rootScope.$broadcast('preloader:hide');
                if (response.status === 200) {
                    $scope.list.lotacao = response.data;
                    $rootScope.$broadcast('preloader:hide');
                } else if (response.status === 500) {
                    $scope.showSimpleToast("Falha no carregamento de dados, favor contate o administrador do sistema");
                }
                $scope.loadedLotacao = true;
            });
        };
        $scope.Empresa = function () {
            $scope.loadedEmpresas = false;
            $rootScope.$broadcast('preloader:active');
            GenericoService.GetAllDropdown('listaEmpresasMatrizes', function (response) {
                if (response.status === 200) {
                    $scope.list.empresa = response.data;
                    $rootScope.$broadcast('preloader:hide');
                } else if (response.status === 500) {
                    $scope.showSimpleToast("Falha no carregamento de dados, favor contate o administrador do sistema");
                }
                $scope.loadedEmpresas = true;
            });
        };
        if ($state.params && $state.params.id) {
            $rootScope.$broadcast('preloader:active');
            GenericoService.GetById('/respostasResultados/' + $state.params.id, function (response) {
                $rootScope.$broadcast('preloader:hide');
                if (response.status === 200) {
                    $scope.avalicao = response.data;
                    // $scope.funcionario.funcaoId = $scope.funcionario.funcao.id;
                    // if ($scope.funcionario.filial)
                    //     $scope.Lotacao($scope.funcionario.filial.id);
                    // $scope.funcionario.empresaId = response.data.empresa.id;
                    // if ($scope.funcionario.filial)
                    //     $scope.funcionario.filialId = $scope.funcionario.filial.id;
                    // $scope.Empresa();
                    // $scope.Filial();
                } else {
                    $scope.showSimpleToast("Avaliação não encontrada na base");
                }
            });
        } else {
            $scope.Empresa();
        }
        if ($state.params && $state.params.detalhes) {
            $scope.detalhes = true;
        }
        $scope.goBack = function () {
            $location.path('/respostasResultados/gestao');
        };
        $scope.save = function () {
            $rootScope.$broadcast('preloader:active');
            if ($scope.funcionario.id) {
                GenericoService.Update('/funcionario', $scope.funcionario, function (response) {
                    $rootScope.$broadcast('preloader:hide');
                    if (response.status === 201 && response.data.success) {
                        $scope.showSimpleToast(response.data.message);
                        $location.path('/funcionario/gestao');
                    } else if (response.status === 400) {
                        $scope.showSimpleToast(response.data.message);
                        $location.path('funcionario/formulario/' + $state.params.id)
                    }
                });
            } else {
                GenericoService.Create('/funcionario', $scope.funcionario, function (response) {
                    $rootScope.$broadcast('preloader:hide');
                    if (response.status === 201 && response.data.success) {
                        $scope.showSimpleToast(response.data.message);
                        $location.path('/funcionario/gestao');
                    } else if (response.status === 400) {
                        $scope.showSimpleToast(response.data.message);
                        $location.path('/funcionario/formulario');
                    }
                });
            }
        };
        $scope.showSimpleToast = function (textContent) {
            $mdToast.show(
                $mdToast.simple()
                    .textContent(textContent)
                    .position('top right')
                    .hideDelay(3000)
            );
        };
        $scope.showDialog = function () {
            $mdDialog.show({
                scope: $scope,
                preserveScope: true,
                controller: ['$scope', '$q', function ($scope, $q) {
                    $scope.cancel = function () {
                        $mdDialog.cancel();
                    };
                }],
                templateUrl: 'app/page/funcionario/dialog1.tmpl.html',
                parent: angular.element(document.body),
                clickOutsideToClose: true
            });
        };
        $scope.searchTerm;
        $scope.clearSearchTerm = function () {
            $scope.searchTerm = '';
        };
    }
})();
