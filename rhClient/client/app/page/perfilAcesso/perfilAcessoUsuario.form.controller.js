(function () {
    'use strict';

    angular.module('app.page')
        .controller('perfilAcessoUsuarioCtrl', ['$scope', '$location', '$state', '$mdToast', '$rootScope', 'RestService','GenericoService', perfilAcessoUsuarioCtrl]);

    function perfilAcessoUsuarioCtrl($scope, $location, $state, $mdToast, $rootScope, RestService, GenericoService) {

        // Checa as regras de acesso da tela

        RestService.Get('/usuario/verificaPermissao/por/menu', { params: { menu: 'Perfis de Acesso' } }).then(
            function (response) {
                if (response.status === 200 && response.data) {

                    $scope.usuarioAdm = response.data.usuarioAdm;
                    $scope.podeCadastrar = response.data.usuarioAdm ? true : response.data.podeCadastrar;
                    $scope.podeAtualizar = response.data.usuarioAdm ? true : response.data.podeAtualizar;
                    $scope.podeVisualizar = response.data.usuarioAdm ? true : response.data.podeVisualizar;
                    $scope.podeExcluir = response.data.usuarioAdm ? true : response.data.podeExcluir;

                    if ($scope.detalhes) {
                        if (!$scope.usuarioAdm && !$scope.podeCadastrar && !$scope.podeAtualizar && !$scope.podeVisualizar && !$scope.podeExcluir)
                            $location.path('page/403');
                        else
                            $scope.getUsuarios();
                    } else {
                        if ($state.params && $state.params.id) {
                            if (!$scope.usuarioAdm && !$scope.podeAtualizar)
                                $location.path('page/403');
                            else
                                $scope.getUsuarios();
                        } else {
                            if (!$scope.usuarioAdm && !$scope.podeCadastrar)
                                $location.path('page/403');
                            else
                                $scope.getUsuarios();
                        }
                    }
                }
            }, function errorCallback(response) {
                if (response.status === 400) {
                    $scope.showSimpleToast(response.data.message);
                }
            });

        $scope.usuariosSelecionadosIds = [];

        $scope.limitOptions = [5, 10, 15];

        $scope.listUsuario = {
            "count": 0,
            "data": []
        };

        $scope.idFilialFiltro = "";

        $scope.queryUsuario = {
            order: 'nome',
            limit: 10,
            page: 1
        };

        $scope.limpaFiltroUsuario = function () {
            delete $scope.nomeUsuarioFiltro;
            delete $scope.idFilialFiltro;
            $scope.getUsuarios();
        }

        $scope.carregarListaFiliais = function () {
            GenericoService.GetAllDropdown('listaEmpresasFiliais', function (response) {
                if (response.status === 200) {
                    $scope.listFiliais = response.data;
                    $rootScope.$broadcast('preloader:hide');
                } else {
                    $scope.showSimpleToast("Não foi possível carregar os dados.")
                }
            })
        };

        $scope.carregarListaFiliais();

        $scope.getUsuarios = function () {
            $rootScope.$broadcast('preloader:active');
            var config = {
                params: {
                    page: $scope.queryUsuario.page - 1,
                    size: $scope.queryUsuario.limit,
                    order: $scope.queryUsuario.order,
                    nome: $scope.nomeUsuarioFiltro,
                    idFilialFiltro: $scope.idFilialFiltro
                }
            };

            $scope.promise = RestService.Get('/usuarios', config)
                .then(function successCallback(response) {
                    $rootScope.$broadcast('preloader:hide');
                    if (response.status === 200) {
                        $scope.listUsuario.data = response.data.content;
                        $scope.listUsuario.count = response.data.totalElements;
                    }
                }, function errorCallback(response) {
                    $rootScope.$broadcast('preloader:hide');
                    if (response.status === 400) {
                        $scope.showSimpleToast(response.data.message);
                    }
                });
        };

        if ($state.params && $state.params.perfilId) {
            $rootScope.$broadcast('preloader:active');
            RestService.Get('/perfil/' + $state.params.perfilId)
                .then(function successCallback(response) {
                    $rootScope.$broadcast('preloader:hide');
                    if (response.status === 200) {
                        $scope.perfil = response.data;
                    }
                }, function errorCallback(response) {
                    $rootScope.$broadcast('preloader:hide');
                    if (response.status === 400) {
                        $scope.showSimpleToast(response.data.message);
                    }
                });

            RestService.Get('/perfil/' + $state.params.perfilId + '/usuarios')
                .then(function successCallback(response) {
                    $rootScope.$broadcast('preloader:hide');
                    if (response.status === 200 && response.data && response.data.length > 0) {
                        $scope.usuariosSelecionadosIds = response.data;
                    }
                }, function errorCallback(response) {
                    $rootScope.$broadcast('preloader:hide');
                    if (response.status === 400) {
                        $scope.showSimpleToast(response.data.message);
                    }
                });
        }

        $scope.save = function () {
            $rootScope.$broadcast('preloader:active');

            var perfilComUsuarios = {
                id: $scope.perfil.id,
                usuarioIds: $scope.usuariosSelecionadosIds
            };

            RestService.Update('/perfil/usuario', perfilComUsuarios).then(
                function successCallback(response) {
                    $rootScope.$broadcast('preloader:hide');
                    if (response.status === 201 && response.data.success) {
                        $scope.showSimpleToast(response.data.message);
                        $location.path('/perfilAcesso/gestao');
                    }
                }, function errorCallback(response) {
                    if (response.status === 400) {
                        $scope.showSimpleToast(response.data.message);
                        $location.path('perfilAcesso/formulario/' + $state.params.id)
                    }
                });

        }

        $scope.goBack = function () {
            $location.path('/perfilAcesso/gestao');
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



