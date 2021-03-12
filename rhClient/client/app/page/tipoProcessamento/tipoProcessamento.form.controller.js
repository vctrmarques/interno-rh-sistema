(function () {
    'use strict';

    angular.module('app.page')
        .controller('tipoProcessamentoFormCtrl', ['$scope', '$mdToast', '$q', '$location', '$state', '$rootScope', 'GenericoService', tipoProcessamentoFormCtrl]);

    function tipoProcessamentoFormCtrl($scope, $mdToast, $q, $location, $state, $rootScope, GenericoService) {

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
        $scope.tipoProcessamento = {};
        $scope.motorCalculoRequest = {};

        // $scope.motorRegras = [];

        $scope.list = {
            "count": 0,
            "data": []
        };

        $scope.querySearchVerba = function (query) {

            var deferred = $q.defer();
            var config = { params: { search: query } }
            if (query) {
                if (query.length > 2) {
                    $rootScope.$broadcast('preloader:active');
                    GenericoService.GetAll('/verba/search', config).then(
                        function (response) {
                            if (response.data && response.data.length > 0) {
                                $scope.verbas = response.data;
                                deferred.resolve(response.data);

                            }
                        });
                } else {
                    $scope.verbas = [];
                }
                $rootScope.$broadcast('preloader:hide');
            }
            return deferred.promise;
        };

        $scope.adicionarVerba = function () {
            var exist;
            if (!$scope.tipoProcessamento.verbas)
                $scope.tipoProcessamento.verbas = []
            angular.forEach($scope.tipoProcessamento.verbas, function (e) {
                if (e.id == $scope.verba.id) {
                    $scope.showSimpleToast("Esta verba já foi adicionada");
                    exist = true;
                    return;
                }
            });
            if (!exist) {
                var verba = {
                    id: this.verba.id,
                    codigo: this.verba.codigo,
                    descricaoVerba: this.verba.descricaoVerba,
                    tipoVerba: this.verba.tipoVerba
                }
                $scope.tipoProcessamento.verbas.push(verba);
                this.verba = null;
            }
        }

        $scope.removerVerba = function (id) {
            $scope.tipoProcessamento.verbas = $scope.tipoProcessamento.verbas.filter(function (e) {
                return e.id != id;
            });
        }

        if ($state.params && $state.params.id) {
            $rootScope.$broadcast('preloader:active');
            GenericoService.GetById('/tipoProcessamento/' + $state.params.id, function (response) {
                $rootScope.$broadcast('preloader:hide');
                if (response.status === 200) {
                    $scope.tipoProcessamento = response.data;
                } else {
                    $scope.showSimpleToast("Tipo de Processamento não encontrado na base");
                }
            });
        }

        if ($state.params && $state.params.detalhes) {
            $scope.detalhes = true;
        }

        $scope.goBack = function () {
            $location.path('/tipoProcessamento/gestao');
        }

        $scope.save = function () {

            $scope.tipoProcessamento.verbaIds = [];

            if ($scope.tipoProcessamento.verbas)
                for (let index = 0; index < $scope.tipoProcessamento.verbas.length; index++) {
                    const element = $scope.tipoProcessamento.verbas[index];
                    $scope.tipoProcessamento.verbaIds.push(element.id)
                }

            $rootScope.$broadcast('preloader:active');
            if ($scope.tipoProcessamento.id) {
                GenericoService.Update('/tipoProcessamento', $scope.tipoProcessamento, function (response) {
                    $rootScope.$broadcast('preloader:hide');
                    if (response.status === 201 && response.data.success) {
                        $scope.showSimpleToast(response.data.message);
                        $location.path('/tipoProcessamento/gestao');
                    } else if (response.status === 400) {
                        $scope.showSimpleToast(response.data.message);
                        $location.path('tipoProcessamento/formulario/' + $state.params.id)
                    }
                });
            } else {
                GenericoService.Create('/tipoProcessamento', $scope.tipoProcessamento, function (response) {
                    $rootScope.$broadcast('preloader:hide');
                    if (response.status === 201 && response.data.success) {
                        $scope.showSimpleToast(response.data.message);
                        $location.path('/tipoProcessamento/gestao');
                    } else if (response.status === 400) {
                        $scope.showSimpleToast(response.data.message);
                        $location.path('/tipoProcessamento/formulario');
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