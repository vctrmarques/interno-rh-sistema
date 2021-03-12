(function () {
    'use strict';

    angular.module('app.page')
        .controller('agenciaFormCtrl', ['$scope', '$mdToast', '$location', '$state', '$rootScope', 'GenericoService', agenciaFormCtrl]);

    function agenciaFormCtrl($scope, $mdToast, $location, $state, $rootScope, GenericoService) {

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

        $scope.agencia = {};

        $scope.acessaTela();
        $scope.detalhes = false;

        if ($state.params && $state.params.id) {
            $rootScope.$broadcast('preloader:active');
            GenericoService.GetById('/agencia/' + $state.params.id, function (response) {
                $rootScope.$broadcast('preloader:hide');
                if (response.status === 200) {
                    $scope.agencia = response.data;
                    $scope.agencia.ufId = response.data.uf.id;
                    $scope.agencia.municipioId = response.data.municipio.id;
                    $scope.getMunicipiosByUf();
                } else {
                    $scope.showSimpleToast("Agencia não encontrado na base");
                }
            });
        }
        $scope.municipios = [];

        $scope.Estados = function () {
            GenericoService.GetAllDropdown('listaUfs', function (response) {
                if (response.status === 200) {
                    $scope.estados = response.data;
                }
                $rootScope.$broadcast('preloader:hide');
            });
        }
        $scope.Estados();

        $scope.getMunicipiosByUf = function () {
            $rootScope.$broadcast('preloader:active');
            var config = { params: { id: $scope.agencia.ufId } };
            $scope.loadedMunicipios = false;
            GenericoService.GetAll('/listaMunicipios', config).then(
                function (response) {
                    if (response.data && response.data.length > 0) {
                        $scope.municipios = response.data;
                        $rootScope.$broadcast('preloader:hide');
                    } else {
                        $scope.showSimpleToast("Não existem municípios cadastrados para este estado");
                    }
                    $scope.loadedMunicipios = true;
                });
        }


        if ($state.params && $state.params.bancoId) {
            $scope.agencia = {
                bancoId: $state.params.bancoId
            };
            GenericoService.GetById('/banco/' + $state.params.bancoId, function (response) {
                $rootScope.$broadcast('preloader:hide');
                if (response.status === 200) {
                    $scope.agencia.banco = response.data;
                } else {
                    $scope.showSimpleToast("Banco não encontrado na base");
                }
            });
        }

        if ($state.params && $state.params.detalhes) {
            $scope.detalhes = true;
        }

        $scope.goBack = function () {
            window.history.back();
        }

        $scope.save = function () {
            $rootScope.$broadcast('preloader:active');
            if ($scope.agencia.id) {
                GenericoService.Update('/agencia', $scope.agencia, function (response) {
                    $rootScope.$broadcast('preloader:hide');
                    if (response.status === 201 && response.data.success) {
                        $scope.showSimpleToast(response.data.message);
                        $location.path('/banco/gestao');
                    } else if (response.status === 400) {
                        $scope.showSimpleToast(response.data.message);
                        $location.path('agencia/formulario/' + $state.params.id)
                    }
                });
            } else {
                GenericoService.Create('/agencia', $scope.agencia, function (response) {
                    $rootScope.$broadcast('preloader:hide');
                    if (response.status === 201 && response.data.success) {
                        $scope.showSimpleToast(response.data.message);
                        $location.path('/banco/gestao');
                    } else if (response.status === 400) {
                        $scope.showSimpleToast(response.data.message);
                        $location.path('/agencia/formulario');
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
