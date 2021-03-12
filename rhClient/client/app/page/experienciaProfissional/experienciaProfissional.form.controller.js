(function () {
    'use strict';

    angular.module('app.page')
        .controller('experienciaProfissionalFormCtrl', ['$scope', '$q', '$mdToast', '$location','$mdDialog', '$state', '$rootScope', 'GenericoService', experienciaProfissionalFormCtrl]);

    function experienciaProfissionalFormCtrl($scope, $q, $mdToast, $location,$mdDialog, $state, $rootScope, GenericoService) {

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
        $scope.funcaoSelecionada = {}
        $scope.detalhes = false;
        $scope.vinculos = [];
        var i = 0;

        if ($state.params && $state.params.id) {
            $scope.experienciaProfissional = {};
            $rootScope.$broadcast('preloader:active');
            GenericoService.GetById('/experienciaProfissional/' + $state.params.id, function (response) {
                $rootScope.$broadcast('preloader:hide');
                if (response.status === 200) {
                    $scope.experienciaProfissional = response.data;
                    $scope.funcionario = $scope.experienciaProfissional.funcionario;
                    $scope.funcaoSelecionada = $scope.experienciaProfissional.funcao;
                    $scope.funcaoSelecionadaId = $scope.experienciaProfissional.funcao.id;
                    
                } else {
                    $scope.showSimpleToast("Natureza da Experiência Profissional não encontrada na base");
                }
            });
        }else{
            $scope.experienciaProfissional = {};
        }

        $scope.list ={
            "funcao": []
        }

        $scope.funcionarioSearch = function (query) {
            var deferred = $q.defer();
            var config = { params: { search: query } }
            GenericoService.GetAll('/funcionario/searchComplete', config).then(
                function (response) {
                    if (response.data && response.data.length > 0) {
                        $scope.itens = response.data;
                        deferred.resolve(response.data);
                    }
                });
            return deferred.promise;
        }
        
        $scope.updateAbas = function () { 
            GenericoService.GetById('/funcao/' + $scope.funcaoSelecionadaId, function (response) {
                $rootScope.$broadcast('preloader:hide');
                if (response.status === 200) {
                    $scope.funcaoSelecionada = response.data;
                } else {
                    $scope.showSimpleToast("Natureza da Função não encontrado na base");
                }
            });
        }

        $scope.ExperienciaProfissional = function () {
            $rootScope.$broadcast('preloader:active');
            GenericoService.GetAllDropdown('funcoes', function (response) {
                if (response.status === 200) {
                    $scope.list.funcao = response.data.content;
                    $rootScope.$broadcast('preloader:hide');
                } else if (response.status === 500) {
                    $scope.showSimpleToast("Falha no carregamento de dados, favor contate o administrador do sistema");
                }
            });
        }

        $scope.ExperienciaProfissional();

        $scope.filtrarCursos = function () {
            $rootScope.$broadcast('preloader:active');

            var config = {
                params: {
                    classeSalarialId: $scope.experienciaProfissional.classeSalarialId,
                    grupoSalarialId: $scope.experienciaProfissional.grupoSalarialId
                }
            };

            $scope.promise = GenericoService.GetAll('/filtrarExperienciaProfissional', config).then(
                function (response) {
                    if (response.status === 200) {
                        $scope.list.curso = response.data;
                        $rootScope.$broadcast('preloader:hide');
                    } else {
                        $scope.showSimpleToast("Não foi possível carregar os dados.")
                    }
                });
        }

        if ($state.params && $state.params.detalhes) {
            $scope.detalhes = true;
        }

        $scope.goBack = function () {
            $location.path('/experienciaProfissional/gestao');
        }

        $scope.save = function () {
            $rootScope.$broadcast('preloader:active');
            
            $scope.experienciaProfissional.funcionarioId = $scope.funcionario.id;
            $scope.experienciaProfissional.funcaoId = $scope.funcaoSelecionadaId;

            if ($scope.experienciaProfissional.id) {
                GenericoService.Update('/experienciaProfissional', $scope.experienciaProfissional, function (response) {
                    $rootScope.$broadcast('preloader:hide');
                    if (response.status === 201 && response.data.success) {
                        $scope.showSimpleToast(response.data.message);
                        $location.path('/experienciaProfissional/gestao');
                    } else if (response.status === 400) {
                        $scope.showSimpleToast(response.data.message);
                        $location.path('experienciaProfissional/formulario/' + $state.params.id)
                    }
                });
            } else {
                GenericoService.Create('/experienciaProfissional', $scope.experienciaProfissional, function (response) {
                    $rootScope.$broadcast('preloader:hide');
                    if (response.status === 201 && response.data.success) {
                        $scope.showSimpleToast(response.data.message);
                        $location.path('/experienciaProfissional/gestao');
                    } else if (response.status === 400) {
                        $scope.showSimpleToast(response.data.message);
                        $location.path('/experienciaProfissional/formulario');
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