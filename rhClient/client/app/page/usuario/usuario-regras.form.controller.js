(function () {
    'use strict';

    angular.module('app.page')
        .controller('usuarioRegrasFormCtrl', ['$scope', 'RestService', '$mdToast', '$location', '$state', '$rootScope', 'UsuarioService', '$mdDialog', usuarioRegrasFormCtrl]);

    function usuarioRegrasFormCtrl($scope, RestService, $mdToast, $location, $state, $rootScope, UsuarioService, $mdDialog) {

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

        // Busca todos perfis de acesso
        RestService.Get('/perfis/search')
            .then(function successCallback(response) {
                $scope.perfisAcesso = response.data
            }, function errorCallback(response) {
                if (response.status === 400) {
                    $scope.showSimpleToast(response.data.message);
                }
            });

        // Busca todos perfis de acesso
        $scope.loadPerfisUsuario = function () {
            RestService.Get('/perfil/usuario/' + $state.params.id)
                .then(function successCallback(response) {
                    $scope.perfisAcessoUsuario = response.data;

                    $scope.perfisAcessoUsuario.forEach(perfil => {
                        perfil.funcionalidadesDetalhes = [];

                        perfil.papeis.forEach(papel => {
                            var funcionalidadeDetalhe = {
                                modulo: papel.menu.categoria,
                                funcionalidade: papel.menu.nome,
                                papelId: papel.id
                            }

                            if (!$scope.checkContains(perfil.funcionalidadesDetalhes, funcionalidadeDetalhe))
                                perfil.funcionalidadesDetalhes.push(funcionalidadeDetalhe);
                        });
                    });

                }, function errorCallback(response) {
                    if (response.status === 400) {
                        $scope.showSimpleToast(response.data.message);
                    }
                });

        }
        $scope.loadPerfisUsuario();

        $scope.checkContains = function (list, item) {
            var i;
            for (i = 0; i < list.length; i++) {
                var element = list[i];
                if (element.modulo === item.modulo && element.funcionalidade === item.funcionalidade)
                    return true;
            }
            return false;
        }

        $scope.usuarioPapeisPerfis = {
            usuarioId: null,
            papelIds: [],
            perfilIds: []
        };


        $scope.showPermissoesDetalhes = function (ev, papeis, funcionalidade) {

            $scope.permissoes = [];
            papeis.forEach(papel => {
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
                templateUrl: 'app/page/usuario/usuario-regras.FormPermissoes.tmpl.html',
                parent: angular.element(document.body),
                targetEvent: ev,
                clickOutsideToClose: true,
                fullscreen: true // Only for -xs, -sm breakpoints.
            });
        };


        $scope.accordion = function (perfilNome) {
            var accordions = document.getElementById("accordion-aliquota" + perfilNome);

            var content = accordions.nextElementSibling;
            if (content.style.maxHeight) {
                accordions.classList.remove("is-open");
                // accordion is currently open, so close it
                content.style.maxHeight = null;
                content.style.paddingTop = null;
            } else {
                // accordion is currently closed, so open it
                accordions.classList.add("is-open");
                content.style.maxHeight = content.scrollHeight + "px";
                content.style.paddingTop = "5px";
            }
        };

        // Construtor básico do objeto principal
        if ($state.params && $state.params.id) {
            $rootScope.$broadcast('preloader:active');
            UsuarioService.GetById($state.params.id, function (response) {
                $rootScope.$broadcast('preloader:hide');
                if (response.status === 200) {
                    $scope.usuario = response.data;
                    $scope.usuarioPapeisPerfis.usuarioId = response.data.id;
                    response.data.papeis.forEach(papel => {
                        $scope.usuarioPapeisPerfis.papelIds.push(papel.id);
                    });
                } else {
                    $scope.showSimpleToast("Usuário não encontrado na base");
                }
            });
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

        $scope.goBack = function () {
            $location.path('/usuario/gestao');
        }

        $scope.adicionarPerfil = function () {
            if (!$scope.perfilAcessoSelecionadoId) {
                $scope.showSimpleToast("Favor selecionar Perfil de Acesso.");
                return;
            }

            var perfilUsuarioRequest = {
                id: $scope.perfilAcessoSelecionadoId,
                usuarioId: $scope.usuario.id
            }

            $rootScope.$broadcast('preloader:active');
            RestService.Update('/perfil/usuario/adicionar', perfilUsuarioRequest)
                .then(function successCallback(response) {
                    $rootScope.$broadcast('preloader:hide');
                    delete $scope.perfilAcessoSelecionadoId;
                    $scope.loadPerfisUsuario();
                    $scope.showSimpleToast(response.data.message);
                }, function errorCallback(response) {
                    $rootScope.$broadcast('preloader:hide');
                    delete $scope.perfilAcessoSelecionadoId;
                    if (response.status === 400) {
                        $scope.showSimpleToast(response.data.message);
                    }
                });
        }

        $scope.showConfirm = function (ev, idToDelete) {
            $scope.idToDelete = idToDelete;
            // Appending dialog to document.body to cover sidenav in docs app
            var confirm = $mdDialog.confirm()
                .title('Deseja confirmar a exclusão deste item?')
                .textContent('Esta ação não poderá ser desfeita.')
                // .ariaLabel('Lucky day')
                .targetEvent(ev)
                .ok('Sim')
                .cancel('Não');

            $mdDialog.show(confirm).then(function () {
                $scope.removerPerfil();
            }, function () {
            });
        };

        $scope.removerPerfil = function () {
            $rootScope.$broadcast('preloader:active');
            var perfilUsuarioRequest = {
                id: $scope.idToDelete,
                usuarioId: $scope.usuario.id
            }
            RestService.Update('/perfil/usuario/remover', perfilUsuarioRequest)
                .then(function successCallback(response) {
                    $rootScope.$broadcast('preloader:hide');
                    $scope.loadPerfisUsuario();
                    $scope.showSimpleToast(response.data.message);
                }, function errorCallback(response) {
                    $rootScope.$broadcast('preloader:hide');
                    if (response.status === 400) {
                        $scope.showSimpleToast(response.data.message);
                    }
                });
        }

        $scope.save = function () {
            $rootScope.$broadcast('preloader:active');
            if ($scope.usuario.id) {
                RestService.Update('/usuario/atualizar/papeis/perfis', $scope.usuarioPapeisPerfis)
                    .then(function successCallback(response) {
                        $scope.goBack();
                        $scope.showSimpleToast(response.data.message);
                    }, function errorCallback(response) {
                        if (response.status === 400) {
                            $scope.showSimpleToast(response.data.message);
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