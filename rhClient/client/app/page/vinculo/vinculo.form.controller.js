(function () {
    'use strict';

    angular.module('app.page')
        .controller('vinculoFormCtrl', ['$scope', '$mdToast', '$location', '$state', '$rootScope', '$q', 'GenericoService', vinculoFormCtrl]);

    function vinculoFormCtrl($scope, $mdToast, $location, $state, $rootScope, $q, GenericoService) {

        GenericoService.VerificaPermissao('/usuario/verificaPermissao', 'ROLE_ADMIN').then(
            function (response) {
                if (response.status === 200) {
                }
            }, function errorCallback(response) {
                if (response.status === 400) {
                    $location.path('page/403');
                }
            });
        $scope.detalhes = false;
        $scope.loaded = false;

        $scope.Verba = function () {
            $rootScope.$broadcast('preloader:active');
            GenericoService.GetAllDropdown('listaVerbas', function (response) {
                if (response.status === 200) {
                    $scope.list.verba = response.data;
                    $rootScope.$broadcast('preloader:hide');
                } else if (response.status === 500) {
                    $scope.showSimpleToast("Falha no carregamento de dados, favor contate o administrador do sistema");
                }
            });
        }
        $scope.Verba();

        // $scope.vinculo = { exames: [] };
        if ($state.params && $state.params.id) {
            $rootScope.$broadcast('preloader:active');
            GenericoService.GetById('/vinculo/' + $state.params.id, function (response) {
                $rootScope.$broadcast('preloader:hide');
                if (response.status === 200) {
                    $scope.vinculo = response.data;
                    
                    if ($scope.vinculo.verbaIds){
                         $scope.selectedList = [];
                        for (var i = 0; i < $scope.vinculo.verbaIds.length; i++) {
                            var hash =  {}
                            var key = $scope.vinculo.verbaIds[i].toString();
                            hash[key] = true
                            $scope.selectedList.push(hash); 
                            //.push([$scope.vinculo.verbaIds[i].toString(), true]);
                        }

                    }else {
                        $scope.vinculo.verbaIds = [];
                    }


                    if ($scope.vinculo.dataInicial) {
                        $scope.vinculo.dataInicial = moment(response.data.dataInicial);
                    }

                    if ($scope.vinculo.dataFinal) {
                        $scope.vinculo.dataFinal = moment(response.data.dataFinal);
                    }

                } else {
                    $scope.showSimpleToast("Vinculo não encontrado na base");
                }
            });
        } else {
            $scope.selectedList = [];
            $scope.vinculo = {
                verbaIds: []
            }
        }
        $scope.list = {
            verba: []
        }

        $scope.vinculoGrupos = [
            { label: 'Efetivo', value: 'EFETIVO' },
            { label: 'Comissionado', value: 'COMISSIONADO' },
            { label: 'Estagiário', value: 'ESTAGIARIO' },
            { label: 'Policial', value: 'POLICIAL' },
            { label: 'Voluntário', value: 'VOLUNTARIO' },
            { label: 'Salário Família', value: 'SALARIO_FAMILIA' }
        ];

        if ($state.params && $state.params.detalhes) {
            $scope.detalhes = true;
        }

        $scope.goBack = function () {
            $location.path('/vinculo/gestao');
        }

        $scope.querySearch = function (query, resourcePath) {
            var deferred = $q.defer();
            var config = { params: { search: query } }
            GenericoService.GetAll(resourcePath + '/searchComplete', config).then(
                function (response) {
                    if (response.data && response.data.length > 0) {
                        $scope.itens = response.data;
                        deferred.resolve(response.data);
                    }
                });
            return deferred.promise;
        }

        $scope.save = function () {
            $rootScope.$broadcast('preloader:active');

            for (var index = 0; index < $scope.selectedList.length; index++) {
                var element = $scope.selectedList[index];

                for (var k in element) {
                    if (typeof element[k] !== 'function') {
                        if (element[k]) {
                            $scope.vinculo.verbaIds.push(parseInt(k));
                        } else {
                            $scope.vinculo.verbaIds.splice(index, 1);
                        }
                    }
                }

            }

            if ($scope.vinculo.id) {
                GenericoService.Update('/vinculo', $scope.vinculo, function (response) {
                    $rootScope.$broadcast('preloader:hide');
                    if (response.status === 201 && response.data.success) {
                        $scope.showSimpleToast(response.data.message);
                        $location.path('/vinculo/gestao');
                    } else if (response.status === 400) {
                        $scope.showSimpleToast(response.data.message);
                        $location.path('vinculo/formulario/' + $state.params.id)
                    }
                });
            } else {
                GenericoService.Create('/vinculo', $scope.vinculo, function (response) {
                    $rootScope.$broadcast('preloader:hide');
                    if (response.status === 201 && response.data.success) {
                        $scope.showSimpleToast(response.data.message);
                        $location.path('/vinculo/gestao');
                    } else if (response.status === 400) {
                        $scope.showSimpleToast(response.data.message);
                        $location.path('/vinculo/formulario');
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