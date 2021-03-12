(function () {
    'use strict';

    angular.module('app.page')
        .controller('classificacaoInternacionalDoencaFormCtrl', ['$scope', '$mdToast', '$location', '$state', '$rootScope', '$q', 'GenericoService', classificacaoInternacionalDoencaFormCtrl]);

    function classificacaoInternacionalDoencaFormCtrl($scope, $mdToast, $location, $state, $rootScope, $q, GenericoService) {

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

        if ($state.params && $state.params.id) {
            $rootScope.$broadcast('preloader:active');
            GenericoService.GetById('/classificacaoInternacionalDoenca/' + $state.params.id, function (response) {
                $rootScope.$broadcast('preloader:hide');
                if (response.status === 200) {
                    $scope.classificacaoInternacionalDoenca = response.data;
                } else {
                    $scope.showSimpleToast("ClassificacaoInternacionalDoenca não encontrado na base");
                }
            });
        }

        if ($state.params && $state.params.detalhes) {
            $scope.detalhes = true;
        }

        $scope.goBack = function () {
            $location.path('/classificacaoInternacionalDoenca/gestao');
        }

        $scope.querySearchCategoria = function (query) {
            var deferred = $q.defer();
            var config = { params: { search: query } }
            GenericoService.GetAll('/categoriaDoenca/searchComplete', config).then(
                function (response) {
                    if (response.data && response.data.length > 0) {
                        // results = response.data;
                        $scope.itens = response.data;
                        deferred.resolve(response.data);
                    }
                });
            return deferred.promise;
        }

        $scope.querySearchSubCategoria = function (query) {
            var deferred = $q.defer();
            var config = { params: { search: query } }
            GenericoService.GetAll('/subCategoriaDoenca/searchComplete', config).then(
                function (response) {
                    if (response.data && response.data.length > 0) {
                        // results = response.data;
                        $scope.itens = response.data;
                        deferred.resolve(response.data);
                    }
                });
            return deferred.promise;
        }

        $scope.save = function () {
            $rootScope.$broadcast('preloader:active');

            try {
                var categoriaDoencaId = $scope.classificacaoInternacionalDoenca.categoriaDoenca.id;
                if (categoriaDoencaId) {
                    $scope.classificacaoInternacionalDoenca.categoriaDoencaId = categoriaDoencaId;
                }
                var subCategoriaDoencaId = $scope.classificacaoInternacionalDoenca.subCategoriaDoenca.id;
                if (subCategoriaDoencaId) {
                    $scope.classificacaoInternacionalDoenca.subCategoriaDoencaId = subCategoriaDoencaId;
                }
            } catch (error) {

            }

            if ($scope.classificacaoInternacionalDoenca.id) {
                GenericoService.Update('/classificacaoInternacionalDoenca', $scope.classificacaoInternacionalDoenca, function (response) {
                    $rootScope.$broadcast('preloader:hide');
                    if (response.status === 201 && response.data.success) {
                        $scope.showSimpleToast(response.data.message);
                        $location.path('/classificacaoInternacionalDoenca/gestao');
                    } else if (response.status === 400) {
                        $scope.showSimpleToast(response.data.message);
                        $location.path('classificacaoInternacionalDoenca/formulario/' + $state.params.id)
                    }
                });
            } else {
                GenericoService.Create('/classificacaoInternacionalDoenca', $scope.classificacaoInternacionalDoenca, function (response) {
                    $rootScope.$broadcast('preloader:hide');
                    if (response.status === 201 && response.data.success) {
                        $scope.showSimpleToast(response.data.message);
                        $location.path('/classificacaoInternacionalDoenca/gestao');
                    } else if (response.status === 400) {
                        $scope.showSimpleToast(response.data.message);
                        $location.path('/classificacaoInternacionalDoenca/formulario');
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