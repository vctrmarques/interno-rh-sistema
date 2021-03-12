(function () {
    'use strict';

    angular.module('app.page')
        .controller('perfilAcessoFormCtrl', ['$scope', '$mdToast', '$location', '$state', '$mdDialog', '$rootScope', 'RestService', perfilAcessoFormCtrl]);

    function perfilAcessoFormCtrl($scope, $mdToast, $location, $state, $mdDialog, $rootScope, RestService) {

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
                    } else {
                        if ($state.params && $state.params.id) {
                            if (!$scope.usuarioAdm && !$scope.podeAtualizar)
                                $location.path('page/403');
                        } else {
                            if (!$scope.usuarioAdm && !$scope.podeCadastrar)
                                $location.path('page/403');
                        }
                    }
                }
            }, function errorCallback(response) {
                if (response.status === 400) {
                    $scope.showSimpleToast(response.data.message);
                }
            });


        $scope.categorias = [];
        $scope.categoriasOriginal = [];

        // Busca todos os menus por categoria
        RestService.Get('/menus/papeis/gestao')
            .then(function successCallback(response) {
                response.data.forEach(element => {
                    element.click = false;
                    $scope.categorias.push(element)
                    $scope.categoriasOriginal.push(element)
                });
            }, function errorCallback(response) {
                if (response.status === 400) {
                    $scope.showSimpleToast(response.data.message);
                }
            });

        // Construtor básico do objeto principal
        $scope.perfilAcessoInit = {
            nome: '',
            papelIds: []
        };

        $scope.detalhes = false;

        $scope.perfilAcesso = angular.copy($scope.perfilAcessoInit);
        if ($state.params.detalhes) {
            $scope.detalhes = true;
        }

        // Selecionar o menu
        $scope.selecionarMenu = function (menu) {
            $scope.menuSelecionado = menu;
        };

        // Processa a busca 
        $scope.buscarMenu = function () {
            if ($scope.menuBusca) {
                var resultMatch = [];

                var i;
                for (i = 0; i < $scope.categoriasOriginal.length; i++) {

                    var categoria = $scope.categoriasOriginal[i];

                    var cartegoriaMatch = null;

                    var j;
                    for (j = 0; j < categoria.subMenus.length; j++) {
                        var subMenu = categoria.subMenus[j];

                        if (subMenu.nome.toUpperCase().indexOf($scope.menuBusca.toUpperCase()) >= 0) {
                            if (!cartegoriaMatch) {
                                cartegoriaMatch = {
                                    id: categoria.id,
                                    nome: categoria.nome,
                                    click: true,
                                    subMenus: []
                                }
                            }
                            cartegoriaMatch.subMenus.push(subMenu);
                        }
                    }

                    if (cartegoriaMatch)
                        resultMatch.push(cartegoriaMatch);

                }

                if (resultMatch.length > 0)
                    $scope.categorias = resultMatch
                else
                    $scope.categorias = [];

            } else {
                $scope.categorias = $scope.categoriasOriginal;

            }
        };

        $scope.toggle = function (item, list) {
            var idx = list.indexOf(item);
            if (idx > -1) {
                list.splice(idx, 1);
            } else {
                list.push(item);
            }
        };

        $scope.exists = function (item, list) {
            return list.indexOf(item) > -1;
        };

        if ($state.params.id) {
            $rootScope.$broadcast('preloader:active');

            RestService.Get('/perfil/' + $state.params.id).then(
                function successCallback(response) {
                    $rootScope.$broadcast('preloader:hide');
                    if (response.status === 200) {
                        $scope.perfilAcesso = response.data;
                        $scope.perfilAcesso.papelIds = [];
                        response.data.papeis.forEach(papel => {
                            $scope.perfilAcesso.papelIds.push(papel.id);
                        });

                        if ($scope.detalhes) {
                            $scope.funcionalidadesDetalhes = [];

                            response.data.papeis.forEach(papel => {
                                var funcionalidadeDetalhe = {
                                    modulo: papel.menu.categoria,
                                    funcionalidade: papel.menu.nome,
                                    papelId: papel.id
                                }

                                if (!$scope.checkContains($scope.funcionalidadesDetalhes, funcionalidadeDetalhe))
                                    $scope.funcionalidadesDetalhes.push(funcionalidadeDetalhe);
                            });
                        }

                    } else {
                        $scope.showSimpleToast("Perfil de Acesso não encontrado na base");
                    }
                }, function errorCallback(response) {
                    if (response.status === 400) {
                        $scope.showSimpleToast(response.data.message);
                        $location.path('perfilAcesso/gestao')
                    }
                });
        }

        $scope.checkContains = function (list, item) {
            var i;
            for (i = 0; i < list.length; i++) {
                var element = list[i];
                if (element.modulo === item.modulo && element.funcionalidade === item.funcionalidade)
                    return true;
            }
            return false;
        }

        $scope.showPermissoesDetalhes = function (ev, funcionalidade) {

            $scope.permissoes = [];
            $scope.perfilAcesso.papeis.forEach(papel => {
                if (papel.menu.nome === funcionalidade)
                    $scope.permissoes.push(papel)
            });

            $mdDialog.show({
                scope: $scope,
                preserveScope: true,
                controller: ['$scope', '$mdDialog', function ($scope, $mdDialog) {
                    $scope.cancel = function () {
                        $mdDialog.cancel();
                    };
                }],
                templateUrl: 'app/page/perfilAcesso/perfilAcessoFormPermissoes.tmpl.html',
                parent: angular.element(document.body),
                targetEvent: ev,
                clickOutsideToClose: true,
                fullscreen: true // Only for -xs, -sm breakpoints.
            });
        };

        $scope.goBack = function () {
            $location.path('/perfilAcesso/gestao');
        }

        $scope.save = function () {
            
            if (!$scope.perfilAcesso.papelIds || $scope.perfilAcesso.papelIds.length === 0) {
                $scope.showSimpleToast("Favor selecionar ao menos uma permissão.");
                return;
            }

            $rootScope.$broadcast('preloader:active');
            if ($scope.perfilAcesso.id) {
                RestService.Update('/perfil', $scope.perfilAcesso).then(
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

            } else {
                RestService.Create('/perfil', $scope.perfilAcesso).then(
                    function successCallback(response) {
                        $rootScope.$broadcast('preloader:hide');
                        if (response.status === 201 && response.data.success) {
                            $scope.showSimpleToast(response.data.message);
                            $location.path('/perfilAcesso/gestao');
                        }
                    }, function errorCallback(response) {
                        $rootScope.$broadcast('preloader:hide');
                        if (response.status === 400) {
                            $scope.showSimpleToast(response.data.message);
                            $location.path('/perfilAcesso/formulario');
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