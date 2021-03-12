(function () {
    'use strict';

    angular.module('app.page')
        .controller('crmCreaFormCtrl', ['$scope', '$timeout', '$q', '$mdToast', '$location', '$state', '$rootScope', 'GenericoService', crmCreaFormCtrl]);

    function crmCreaFormCtrl($scope, $timeout, $q, $mdToast, $location, $state, $rootScope, GenericoService) {

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
        $scope.crmCrea = { convenios: [] };

        if ($state.params && $state.params.id) {
            $rootScope.$broadcast('preloader:active');
            GenericoService.GetById('/crmCrea/' + $state.params.id, function (response) {
                $rootScope.$broadcast('preloader:hide');
                if (response.status === 200) {
                    $scope.crmCrea = response.data;
                } else {
                    $scope.showSimpleToast("CrmCrea não encontrado na base");
                }
            });
        }

        if ($state.params && $state.params.detalhes) {
            $scope.detalhes = true;
        }

        $scope.goBack = function () {
            $location.path('/crmCrea/gestao');
        }

        $scope.save = function () {
            $rootScope.$broadcast('preloader:active');

            $scope.crmCrea.convenioIds = [];
            var index;
            for (index = 0; index < $scope.crmCrea.convenios.length; index++) {
                var element = $scope.crmCrea.convenios[index];
                $scope.crmCrea.convenioIds.push(element.id);
            }

            if ($scope.crmCrea.id) {
                GenericoService.Update('/crmCrea', $scope.crmCrea, function (response) {
                    $rootScope.$broadcast('preloader:hide');
                    if (response.status === 201 && response.data.success) {
                        $scope.showSimpleToast(response.data.message);
                        $location.path('/crmCrea/gestao');
                    } else if (response.status === 400) {
                        $scope.showSimpleToast(response.data.message);
                        $location.path('crmCrea/formulario/' + $state.params.id)
                    }
                });
            } else {
                GenericoService.Create('/crmCrea', $scope.crmCrea, function (response) {
                    $rootScope.$broadcast('preloader:hide');
                    if (response.status === 201 && response.data.success) {
                        $scope.showSimpleToast(response.data.message);
                        $location.path('/crmCrea/gestao');
                    } else if (response.status === 400) {
                        $scope.showSimpleToast(response.data.message);
                        $location.path('/crmCrea/formulario');
                    }
                });
            }
        }

        // $scope.addConvenio = function () {
        //     if ($scope.crmCrea.convenioSelecionado) {
        //         var naoExiste = true;
        //         $scope.crmCrea.convenios.forEach(element => {
        //             if (element.id === $scope.crmCrea.convenioSelecionado.id) {
        //                 naoExiste = false;
        //             }
        //         });
        //         if (naoExiste) {
        //             $scope.crmCrea.convenios.push($scope.crmCrea.convenioSelecionado);
        //         }
        //         $scope.crmCrea.convenioSelecionado = null;
        //     }
        // }

        /**
         * Return the proper object when the append is called.
         */
        $scope.transformChip = function (chip) {
            // If it is an object, it's already a known chip
            if (angular.isObject(chip)) {
                return chip;
            }

        }

        $scope.querySearchConvenio = function (query) {
            var deferred = $q.defer();
            var config = { params: { search: query } }
            GenericoService.GetAll('/convenio/searchComplete', config).then(
                function (response) {
                    if (response.data && response.data.length > 0) {
                        $scope.itens = response.data;
                        deferred.resolve(response.data);
                    }
                });
            return deferred.promise;
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