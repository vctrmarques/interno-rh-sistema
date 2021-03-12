(function () {
    'use strict';

    angular.module('app.page')
        .controller('faixaSalarialFormCtrl', ['$scope', '$mdToast', '$location', '$state', '$rootScope', 'GenericoService', faixaSalarialFormCtrl]);

    function faixaSalarialFormCtrl($scope, $mdToast, $location, $state, $rootScope, GenericoService) {

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

        GenericoService.GetAll('/listaNiveisSalariais').then(
            function (response) {
                console.log(response)
                if (response.data && response.data.length > 0) {
                    $scope.niveis = response.data;
                }
            });

        $scope.faixaSalarial = {
            niveisSalariaisIds: []
        }

        $scope.list = {
            "grupoSalarial": [],
            "classeSalarial": []
        }

        $scope.acessaTela();
        $scope.detalhes = false;

        if ($state.params && $state.params.id) {
            $rootScope.$broadcast('preloader:active');
            GenericoService.GetById('/faixaSalarial/' + $state.params.id, function (response) {
                $rootScope.$broadcast('preloader:hide');
                if (response.status === 200) {
                    $scope.faixaSalarial = response.data;
                    $scope.faixaSalarial.grupoSalarialId = $scope.faixaSalarial.grupoSalarial.id;
                    $scope.ClasseSalarial($scope.faixaSalarial.grupoSalarialId)
                    $scope.faixaSalarial.classeSalarialId = $scope.faixaSalarial.classeSalarial.id;
                    $scope.faixaSalarial.niveisSalariaisIds = [];
                    if ($scope.faixaSalarial.niveisSalariais) {
                        var index;
                        for (index = 0; index < $scope.faixaSalarial.niveisSalariais.length; index++) {
                            var element = $scope.faixaSalarial.niveisSalariais[index];
                            $scope.faixaSalarial.niveisSalariaisIds.push(element.id);
                        }
                    }
                } else {
                    $scope.showSimpleToast("Faixa Salarial não encontrada na base");
                }
            });
        }



        if ($state.params && $state.params.detalhes) {
            $scope.detalhes = true;
        }

        $scope.goBack = function () {
            $location.path('/faixaSalarial/gestao');
        }

        $scope.deleteGrupoSalarial = function (id) {
            $rootScope.$broadcast('preloader:active');
            GenericoService.Delete('/grupoSalarial/' + id, function (response) {

                if (response.status === 200 || response.status === 400) {
                    $scope.showSimpleToast(response.data.message);
                }
                $rootScope.$broadcast('preloader:hide');
                $scope.GrupoSalarial();
                //$scope.ClasseSalarial();
            });
        }

        $scope.GrupoSalarial = function () {
            $rootScope.$broadcast('preloader:active');
            GenericoService.GetAllDropdown('listaGruposSalariais', function (response) {
                if (response.status === 200) {
                    $scope.list.grupoSalarial = response.data;
                    $rootScope.$broadcast('preloader:hide');
                } else if (response.status === 500) {
                    $scope.showSimpleToast("Falha no carregamento de dados, favor contate o administrador do sistema");
                }
            });
        }

        $scope.grupoSelecionado = function (id) {
            if (id)
                $scope.ClasseSalarial(id);


        }

        $scope.ClasseSalarial = function (grupoId) {
            $rootScope.$broadcast('preloader:active');
            GenericoService.GetAllDropdown('listaClassesSalariais/grupo/' + grupoId, function (response) {
                if (response.status === 200) {
                    $scope.list.classeSalarial = response.data;
                    $rootScope.$broadcast('preloader:hide');
                } else if (response.status === 500) {
                    $scope.showSimpleToast("Falha no carregamento de dados, favor contate o administrador do sistema");
                }
            });
        }

        $scope.GrupoSalarial();
        //$scope.ClasseSalarial();

        $scope.save = function () {
            $rootScope.$broadcast('preloader:active');
            if ($scope.faixaSalarial.id) {
                GenericoService.Update('/faixaSalarial', $scope.faixaSalarial, function (response) {
                    $rootScope.$broadcast('preloader:hide');
                    if (response.status === 201 && response.data.success) {
                        $scope.showSimpleToast(response.data.message);
                        $location.path('/faixaSalarial/gestao');
                    } else if (response.status === 400) {
                        $scope.showSimpleToast(response.data.message);
                        $location.path('faixaSalarial/formulario/' + $state.params.id)
                    }
                });
            } else {
                GenericoService.Create('/faixaSalarial', $scope.faixaSalarial, function (response) {
                    $rootScope.$broadcast('preloader:hide');
                    if (response.status === 201 && response.data.success) {
                        $scope.showSimpleToast(response.data.message);
                        $location.path('/faixaSalarial/gestao');
                    } else if (response.status === 400) {
                        $scope.showSimpleToast(response.data.message);
                        $location.path('/faixaSalarial/formulario' + $state.params.id);
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