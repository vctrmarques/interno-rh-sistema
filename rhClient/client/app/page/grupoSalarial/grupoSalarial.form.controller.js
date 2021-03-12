(function () {
    'use strict';

    angular.module('app.page')
        .controller('grupoSalarialFormCtrl', ['$scope', '$mdToast', '$location', '$state', '$rootScope', 'GenericoService', grupoSalarialFormCtrl]);

    function grupoSalarialFormCtrl($scope, $mdToast, $location, $state, $rootScope, GenericoService) {

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
        $scope.classeSalarial = {nome:''};

        $scope.acessaTela();
        $scope.detalhes = false;

        if ($state.params && $state.params.id) {
            $rootScope.$broadcast('preloader:active');
            GenericoService.GetById('/grupoSalarial/' + $state.params.id, function (response) {
                $rootScope.$broadcast('preloader:hide');
                if (response.status === 200) {
                    $scope.grupoSalarial = response.data;
                } else {
                    $scope.showSimpleToast("Grupo Salarial não encontrado na base");
                }
            });
        } else {
            $scope.grupoSalarial = { listClassesSalariais: [] };
        }

        if ($state.params && $state.params.detalhes) {
            $scope.detalhes = true;
        }

        $scope.insertListClassesSalariais = function () {
            if ($scope.grupoSalarial.nome && $scope.classeSalarial.nome ) {
                if (!$scope.grupoSalarial.listClassesSalariais) {
                    $scope.grupoSalarial.listClassesSalariais = [];
                }
                $scope.grupoSalarial.listClassesSalariais.push($scope.classeSalarial);
                $scope.classeSalarial = {nome:''};
            }
        }

        $scope.removeListClassesSalariais = function (index) {
            $scope.grupoSalarial.listClassesSalariais.splice(index, 1);
        }

        $scope.goBack = function () {
            $location.path('/faixaSalarial/formulario');
        }

        $scope.save = function () {
            $rootScope.$broadcast('preloader:active');
            if ($scope.grupoSalarial.id) {
                GenericoService.Update('/grupoSalarial', $scope.grupoSalarial, function (response) {
                    $rootScope.$broadcast('preloader:hide');
                    if (response.status === 201 && response.data.success) {
                        $scope.showSimpleToast(response.data.message);
                        window.history.back();
                        //$location.path('/faixaSalarial/formulario');
                    } else if (response.status === 400) {
                        $scope.showSimpleToast(response.data.message);
                        //$location.path('grupoSalarial/formulario/' + $state.params.id)
                    }
                });
            } else {
                GenericoService.Create('/grupoSalarial', $scope.grupoSalarial, function (response) {
                    $rootScope.$broadcast('preloader:hide');
                    if (response.status === 201 && response.data.success) {
                        $scope.showSimpleToast(response.data.message);
                        //$location.path('/faixaSalarial/formulario');
                        window.history.back();
                    } else if (response.status === 400) {

                        $scope.showSimpleToast(response.data.message);
                        //$location.path('/grupoSalarial/formulario');
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