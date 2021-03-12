(function () {
    'use strict';

    angular.module('app.page')
        .controller('cursoFormCtrl', ['$scope', '$mdToast', '$location', '$state', '$rootScope', 'GenericoService', cursoFormCtrl]);

    function cursoFormCtrl($scope, $mdToast, $location, $state, $rootScope, GenericoService) {

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
            GenericoService.GetById('/curso/' + $state.params.id, function (response) {
                $rootScope.$broadcast('preloader:hide');
                if (response.status === 200) {
                    $scope.curso = response.data;
                    $scope.curso.grauAcademicoId =  $scope.curso.grauAcademico.id;
                    $scope.curso.areaFormacaoId = $scope.curso.areaFormacao.id; 
                } else {
                    $scope.showSimpleToast("Curso não encontrado na base");
                }
            });
        }

        if ($state.params && $state.params.detalhes) {
            $scope.detalhes = true;
        }

        $scope.goBack = function () {
            $location.path('/curso/gestao');
        }

        $scope.listGrauAcademico = {
            "data": []
        };

        $scope.listAreaFormacao = {
            "data": []
        };

        $scope.GrauAcademico = function () {
            $rootScope.$broadcast('preloader:active');
            GenericoService.GetAllDropdown('listaGrausAcademicos', function (response) {
                if (response.status === 200) {
                    $scope.listGrauAcademico.data = response.data;
                    $rootScope.$broadcast('preloader:hide');
                } else if (response.status === 500) {
                    $scope.showSimpleToast("Falha no carregamento de dados, favor contate o administrador do sistema");
                }
            });
        }

        $scope.AreaFormacao = function () {
            $rootScope.$broadcast('preloader:active');
            GenericoService.GetAllDropdown('listaAreasFormacao', function (response) {
                if (response.status === 200) {
                    $scope.listAreaFormacao.data = response.data;
                    $rootScope.$broadcast('preloader:hide');
                } else if (response.status === 500) {
                    $scope.showSimpleToast("Falha no carregamento de dados, favor contate o administrador do sistema");
                }
            });
        }

        $scope.GrauAcademico();
        $scope.AreaFormacao();

        $scope.save = function () {
            $rootScope.$broadcast('preloader:active');
            if ($scope.curso.id) {
                GenericoService.Update('/curso',$scope.curso, function (response) {
                    $rootScope.$broadcast('preloader:hide');
                    if (response.status === 201 && response.data.success) {
                        $scope.showSimpleToast(response.data.message);
                        $location.path('/curso/gestao');
                    } else if (response.status === 400) {
                        $scope.showSimpleToast(response.data.message);
                        $location.path('curso/formulario/' + $state.params.id)
                    }
                });
            } else {

                GenericoService.Create('/curso', $scope.curso, function (response) {
                    $rootScope.$broadcast('preloader:hide');
                    if (response.status === 201 && response.data.success) {
                        $scope.showSimpleToast(response.data.message);
                        $location.path('/curso/gestao');
                    } else if (response.status === 400) {
                        $scope.showSimpleToast(response.data.message);
                        $location.path('/curso/formulario');
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