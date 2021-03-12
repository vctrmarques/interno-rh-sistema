(function () {
    'use strict';
    angular.module('app.page')
        .controller('avaliacaoDesempenhoFormCtrl', ['$q', '$scope', '$mdToast', '$location', '$state', '$rootScope', 'GenericoService', 'EnumService', '$mdDialog', avaliacaoDesempenhoFormCtrl]);

    function avaliacaoDesempenhoFormCtrl($q, $scope, $mdToast, $location, $state, $rootScope, GenericoService, EnumService, $mdDialog) {
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
        $scope.edit = false;
        $scope.list = {
            'empresa': [],
            'filial': [],
            'lotacao': [],
            'modelo': [],
            'funcao': [],
            'cargo': [],
            'tipoQuestao': [],
            'formaAvaliacao': []
        };
        $scope.detalhes = false;
        $scope.acessaTela();
        //
        $scope.Empresa = () => {
            $scope.loadedEmpresas = false;
            $rootScope.$broadcast('preloader:active');
            GenericoService.GetAllDropdown('listaEmpresasMatrizes', function (response) {
                if (response.status === 200) {
                    $scope.list.empresa = response.data;
                    $rootScope.$broadcast('preloader:hide');
                } else if (response.status === 500) {
                    $scope.showSimpleToast('Falha no carregamento de dados, favor contate o administrador do sistema');
                }
                $scope.loadedEmpresas = true;
            });
        };
        $scope.changeEmpresa = () => {
            $scope.Lotacao($scope.avaliacao.empresaFilialId);
        };
        $scope.Lotacao = (empresaFilialId) => {
            $scope.loadedLotacao = false;
            $rootScope.$broadcast('preloader:active');
            GenericoService.GetAllDropdown('listaLotacoes/' + empresaFilialId, function (response) {
                if (response.status === 200) {
                    $scope.list.lotacao = response.data;
                    $rootScope.$broadcast('preloader:hide');
                } else if (response.status === 500) {
                    $scope.showSimpleToast('Falha no carregamento de dados, favor contate o administrador do sistema');
                }
                $scope.loadedLotacao = true;
            });
        };
        $scope.Modelo = () => {
            $scope.loadedModelos = false;
            $rootScope.$broadcast('preloader:active');

            // Busca do enum de AvaliacaoDesempenhoModeloEnum
            EnumService.Get("AvaliacaoDesempenhoModeloEnum").then(function (dados) {
                $scope.list.modelo = dados;
                $scope.loadedModelos = true;
                $rootScope.$broadcast('preloader:hide');
            });

        };
        $scope.Funcao = () => {
            $scope.loadedFuncoes = false;
            $rootScope.$broadcast('preloader:active');
            GenericoService.GetAllDropdown('listaFuncoes', function (response) {
                if (response.status === 200) {
                    $scope.list.funcao = response.data;
                    $rootScope.$broadcast('preloader:hide');
                } else if (response.status === 500) {
                    $scope.showSimpleToast('Falha no carregamento de dados, favor contate o administrador do sistema');
                }
                $scope.loadedFuncoes = true;
            });
        };
        $scope.Cargo = () => {
            $scope.loadedCargos = false;
            $rootScope.$broadcast('preloader:active');
            GenericoService.GetAllDropdown('listaCargos', function (response) {
                if (response.status === 200) {
                    $scope.list.cargo = response.data;
                    $rootScope.$broadcast('preloader:hide');
                } else if (response.status === 500) {
                    $scope.showSimpleToast('Falha no carregamento de dados, favor contate o administrador do sistema');
                }
                $scope.loadedCargos = true;
            });
        };
        $scope.TipoQuestao = () => {
            $scope.loadedTiposQuestoes = false;
            $rootScope.$broadcast('preloader:active');

            // Busca do enum de AvaliacaoDesempenhoItemTipoQuestaoEnum
            EnumService.Get("AvaliacaoDesempenhoItemTipoQuestaoEnum").then(function (dados) {
                $scope.list.tipoQuestao = dados;
                $scope.loadedTiposQuestoes = true;
                $rootScope.$broadcast('preloader:hide');
            });

        };
        $scope.FormaAvaliacao = () => {
            $scope.loadedFormasAvaliacao = false;
            $rootScope.$broadcast('preloader:active');

             // Busca do enum de AvaliacaoDesempenhoItemFormaAvaliacaoEnum
             EnumService.Get("AvaliacaoDesempenhoItemFormaAvaliacaoEnum").then(function (dados) {
                $scope.list.formaAvaliacao = dados;
                $scope.loadedFormasAvaliacao = true;
                $rootScope.$broadcast('preloader:hide');
            });
           
        };
        //
        $scope.Empresa();
        $scope.Modelo();
        $scope.Funcao();
        $scope.Cargo();
        $scope.TipoQuestao();
        $scope.FormaAvaliacao();
        if ($state.params && $state.params.id) {
            $rootScope.$broadcast('preloader:active');
            GenericoService.GetById('/avaliacaoDesempenho/' + $state.params.id, function (response) {
                $rootScope.$broadcast('preloader:hide');
                if (response.status === 200) {
                    $scope.avaliacao = response.data;
                    $scope.Lotacao($scope.avaliacao.empresaFilialId);
                } else {
                    $scope.showSimpleToast('Avaliação não encontrada na base');
                    $location.path('/avaliacaoDesempenho/gestao');
                }
            });
        }
        if ($state.params && $state.params.detalhes) {
            $scope.detalhes = true;
        }
        $scope.goBack = function () {
            $location.path('/avaliacaoDesempenho/gestao');
        };
        $scope.save = function () {
            $rootScope.$broadcast('preloader:active');
            if ($scope.avaliacao.id) {
                GenericoService.Update('/avaliacaoDesempenho', $scope.avaliacao, function (response) {
                    $rootScope.$broadcast('preloader:hide');
                    if (response.status === 201 && response.data.success) {
                        $scope.showSimpleToast(response.data.message);
                        $location.path('/avaliacaoDesempenho/gestao');
                    } else if (response.status === 400) {
                        $scope.showSimpleToast(response.data.message);
                        $location.path('avaliacaoDesempenho/formulario/' + $state.params.id);
                    }
                });
            } else {
                GenericoService.Create('/avaliacaoDesempenho', $scope.avaliacao, function (response) {
                    $rootScope.$broadcast('preloader:hide');
                    if (response.status === 201 && response.data.success) {
                        $scope.showSimpleToast(response.data.message);
                        $location.path('/avaliacaoDesempenho/gestao');
                    } else if (response.status === 400) {
                        $scope.showSimpleToast(response.data.message);
                        $location.path('/avaliacaoDesempenho/formulario');
                    }
                });
            }
        };
        $scope.adicionarItem = () => {
            $rootScope.$broadcast('preloader:active');
            if ($scope.avaliacao.id) {
                GenericoService.Create('/avaliacaoDesempenho/item', $scope.avaliacao.item, function (response) {
                    $rootScope.$broadcast('preloader:hide');
                    if (response.status === 201 && response.data.success) {
                        $scope.showSimpleToast(response.data.message);
                        $location.path('/avaliacaoDesempenho/gestao');
                    } else if (response.status === 400) {
                        $scope.showSimpleToast(response.data.message);
                        $location.path('/avaliacaoDesempenho/formulario');
                    }
                });
            } else {
                $scope.showSimpleToast('Falha no carregamento de dados, favor contate o administrador do sistema');
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
                templateUrl: 'app/page/avaliacaoDesempenho/dialog1.tmpl.html',
                parent: angular.element(document.body),
                clickOutsideToClose: true
            });
        };
        //$scope.searchTerm;
        $scope.clearSearchTerm = function () {
            $scope.searchTerm = '';
        };
    }
})();
