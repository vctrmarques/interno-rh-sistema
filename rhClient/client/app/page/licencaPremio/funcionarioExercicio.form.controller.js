(function () {
    'use strict';

    angular.module('app.page')
        .controller('funcionarioExercicioFormCtrl', ['$scope', '$mdToast', '$location', '$state', '$rootScope', 'GenericoService', funcionarioExercicioFormCtrl]);

    function funcionarioExercicioFormCtrl($scope, $mdToast, $location, $state, $rootScope, GenericoService) {

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
        moment.locale('pt-BR');

        $scope.funcionarioExercicio = {}
        $scope.funcionario = {};
        $scope.detalhes = false;

        if ($state.params && $state.params.funcionarioId) {
            $rootScope.$broadcast('preloader:active');
            GenericoService.GetById('/funcionario/' + $state.params.funcionarioId, function (response) {
                $rootScope.$broadcast('preloader:hide');
                if (response.status === 200) {
                    $scope.funcionario = response.data;
                    $scope.funcionarioExercicio.funcionarioId = $scope.funcionario.id;
                } else {
                    $scope.showSimpleToast("Funcionário não encontrado na base");
                }
            });  
        } 

        if ($state.params && $state.params.funcionarioId && $state.params.exercicioId) {
            $rootScope.$broadcast('preloader:active');
            GenericoService.GetById('/funcionarioExercicio/' + $state.params.exercicioId, function (response) {
                $rootScope.$broadcast('preloader:hide');
                if (response.status === 200) {
                    $scope.funcionarioExercicio = response.data;
                    $scope.funcionarioExercicio.dataInicio = moment(response.data.dataInicio);
                    $scope.funcionarioExercicio.dataFim = moment(response.data.dataFim);
                    $scope.funcionarioExercicio.dataDiarioOficial = moment(response.data.dataDiarioOficial);
                    $scope.funcionarioExercicio.dataAto = moment(response.data.dataAto);
                    if($state.params.detalhes){
                        $scope.detalhes = true;
                    }
                } else {
                    $scope.showSimpleToast("Funcionário não encontrado na base");
                }
            });  
        }         

        GenericoService.GetAllDropdown('listaClassificacoesAtos', function (response) {
            if (response.status === 200) {
                $scope.classificacaoAtos = response.data;
                $rootScope.$broadcast('preloader:hide');
            } else if (response.status === 500) {
                $scope.showSimpleToast("Falha no carregamento de dados, favor contate o administrador do sistema");
            }
        });
     

        $scope.updateDates = function(){
            $scope.funcionarioExercicio.dataInicio = "";
            $scope.funcionarioExercicio.dataFim = "";

            var exercicio = $scope.funcionarioExercicio.exercicio;
            var data = moment(exercicio+"/01/01");

            $scope.funcionarioExercicio.dataInicio = moment(data).subtract(5, 'years');
            $scope.funcionarioExercicio.dataFim = moment(data);

        };

        $scope.goBack = function () {
            $location.path('/licencaPremio/gestao');
        }

        $scope.save = function () {
            $rootScope.$broadcast('preloader:active');
            if($state.params.editar){
                GenericoService.Update('/funcionarioExercicio', $scope.funcionarioExercicio, function (response) {
                    $rootScope.$broadcast('preloader:hide');
                    if (response.status === 201 && response.data.success) {
                        $scope.showSimpleToast(response.data.message);
                        $location.path('/licencaPremio/gestao');
                    } else if (response.status === 400) {
                        $scope.showSimpleToast(response.data.message);
                        $location.path('/funcionarioExercicio/formulario/funcionario'+ $state.params.funcionarioId + '/exercicio/'+ $state.params.exercicioId);
                    }
                }); 
            }else{
                GenericoService.Create('/funcionarioExercicio', $scope.funcionarioExercicio, function (response) {
                    $rootScope.$broadcast('preloader:hide');
                    if (response.status === 201 && response.data.success) {
                        $scope.showSimpleToast(response.data.message);
                        $location.path('/licencaPremio/gestao');
                    } else if (response.status === 400) {
                        $scope.showSimpleToast(response.data.message);
                        $location.path('/funcionarioExercicio/formulario/'+ $state.params.funcionarioId);
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