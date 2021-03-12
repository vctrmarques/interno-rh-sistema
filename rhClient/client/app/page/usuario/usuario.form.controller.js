(function () {
    'use strict';

    angular.module('app.page')
        .controller('usuarioFormCtrl', ['$scope', '$mdToast', '$location', '$state', '$rootScope', 'UsuarioService', 'GenericoService', usuarioFormCtrl]);

    function usuarioFormCtrl($scope, $mdToast, $location, $state, $rootScope, UsuarioService, GenericoService) {

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
        $scope.list = {};

        GenericoService.GetAllDropdown('listaEmpresasNaoMatrizes', function (response) {
            if (response.status === 200) {
                $scope.list.empresas = response.data;
                $rootScope.$broadcast('preloader:hide');
            } else if (response.status === 500) {
                $scope.showSimpleToast("Falha no carregamento de dados, favor contate o administrador do sistema");
            }
        });        

        if ($state.params && $state.params.id) {
            $rootScope.$broadcast('preloader:active');
            UsuarioService.GetById($state.params.id, function (response) {
                $rootScope.$broadcast('preloader:hide');
                if (response.status === 200) {
                    $scope.usuario = response.data;
                } else {
                    $scope.showSimpleToast("Usuário não encontrado na base");
                }
            });
        }

        if ($state.params && $state.params.detalhes) {
            $scope.detalhes = true;
        }

        $scope.goBack = function () {
            $location.path('/usuario/gestao');
        }

        $scope.save = function () {
            $rootScope.$broadcast('preloader:active');
            if ($scope.usuario.id) {
                UsuarioService.Update($scope.usuario, function (response) {
                    $rootScope.$broadcast('preloader:hide');
                    if (response.status === 201 && response.data.success) {
                        $scope.showSimpleToast(response.data.message);
                        $location.path('/usuario/gestao');
                    } else if (response.status === 400) {
                        $scope.showSimpleToast(response.data.message);
                        $location.path('usuario/formulario/' + $state.params.id)
                    }
                });
            } else {
                UsuarioService.Create($scope.usuario, function (response) {
                    $rootScope.$broadcast('preloader:hide');
                    if (response.status === 201 && response.data.success) {
                        $scope.showSimpleToast(response.data.message);
                        $location.path('/usuario/gestao');
                    } else if (response.status === 400) {
                        $scope.showSimpleToast(response.data.message);
                        $location.path('/usuario/formulario');
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