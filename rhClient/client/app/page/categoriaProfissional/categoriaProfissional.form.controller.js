(function () {
    'use strict';

    angular.module('app.page')
        .controller('categoriaProfissionalFormCtrl', ['$scope', '$mdToast', '$location', '$state', '$rootScope', 'GenericoService', categoriaProfissionalFormCtrl]);

    function categoriaProfissionalFormCtrl($scope, $mdToast, $location, $state, $rootScope, GenericoService) {

        $scope.acessaTela = function () {
            GenericoService.VerificaPermissao('/usuario/verificaPermissao', 'ROLE_ADMIN').then(
                function (response) {
                    if (response.status === 200) {}
                },
                function errorCallback(response) {
                    if (response.status === 400) {
                        $location.path('page/403');
                    }
                });
        }
        $scope.selected = [];
        $scope.selectedList = [];
        $scope.categoriaProfissional = {
            verbasId: []
        }
        $scope.acessaTela();
        $scope.detalhes = false;

        if ($state.params && $state.params.id) {
            $rootScope.$broadcast('preloader:active');
            GenericoService.GetById('/categoriaProfissional/' + $state.params.id, function (response) {
                $rootScope.$broadcast('preloader:hide');

                if (response.status === 200) {
                    $scope.categoriaProfissional = response.data;
                } else {
                    $scope.showSimpleToast("CategoriaProfissional não encontrado na base");
                }
            });
        }
        $scope.listVerbas = function () {
            if ($state.params && $state.params.id) {
                $rootScope.$broadcast('preloader:active');
                GenericoService.GetById('/verbas/porCategoriaProfissional/' + $state.params.id, function (response) {
                    $rootScope.$broadcast('preloader:hide');
                    if (response.status === 200) {
                        console.log("mano", response.data);
                        if (response.data) {
                            $scope.categoriaProfissional.verbasId = [];
                            $scope.selectedList = [];
                            angular.forEach(response.data, function (e) {
                                $scope.categoriaProfissional.verbasId.push(e.id);
                                $scope.selected = $scope.categoriaProfissional.verbasId;
                            });

                        } else {
                            $scope.categoriaProfissional.verbasId = [];
                        }
                    } else {
                        $scope.showSimpleToast("CategoriaProfissional não encontrado na base");
                    }
                });
            } else
                $scope.categoriaProfissional.verbasId = [];
        }

        if ($state.params && $state.params.detalhes) {
            $scope.detalhes = true;
        }

        $scope.goBack = function () {
            $location.path('/categoriaProfissional/gestao');
        }

        $scope.verbas = function () {
            $rootScope.$broadcast('preloader:active');
            GenericoService.GetAllDropdown('listaVerbas', function (response) {
                if (response.status === 200) {
                    $scope.verbas = response.data;

                    $rootScope.$broadcast('preloader:hide');
                } else if (response.status === 500) {
                    $scope.showSimpleToast("Falha no carregamento de dados, favor contate o administrador do sistema");
                }
            });
        }
        $scope.verbas();

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

        $scope.isChecked = function () {
            return $scope.selected.length === $scope.verbas.length;
        };

        $scope.save = function () {

            if ($scope.selected)
                $scope.categoriaProfissional.verbasId = $scope.selected;

            $rootScope.$broadcast('preloader:active');
            if ($scope.categoriaProfissional.id) {
                GenericoService.Update('/categoriaProfissional', $scope.categoriaProfissional, function (response) {
                    $rootScope.$broadcast('preloader:hide');
                    if (response.status === 201 && response.data.success) {
                        $scope.showSimpleToast(response.data.message);
                        $location.path('/categoriaProfissional/gestao');
                    } else if (response.status === 400) {
                        $scope.showSimpleToast(response.data.message);
                        $location.path('categoriaProfissional/formulario/' + $state.params.id)
                    }
                });
            } else {
                GenericoService.Create('/categoriaProfissional', $scope.categoriaProfissional, function (response) {
                    $rootScope.$broadcast('preloader:hide');
                    if (response.status === 201 && response.data.success) {
                        $scope.showSimpleToast(response.data.message);
                        $location.path('/categoriaProfissional/gestao');
                    } else if (response.status === 400) {
                        $scope.showSimpleToast(response.data.message);
                        $location.path('/categoriaProfissional/formulario');
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
