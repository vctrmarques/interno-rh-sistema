(function () {
    'use strict';

    angular.module('app.page')
        .controller('licencaPremioFormCtrl', ['$scope', '$mdToast', '$location', '$state', '$rootScope', 'GenericoService', licencaPremioFormCtrl]);

    function licencaPremioFormCtrl($scope, $mdToast, $location, $state, $rootScope, GenericoService) {

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
        $scope.list = {
            
        }

        $scope.licencaPremio = {};
        $scope.funcionarioExercicio = {};

        $scope.licencasPremio = [];

        $scope.verifyPermission = false;
        $scope.detalhes = false;
        $scope.edit = false;

        $scope.atualizarDataRetorno = function () {
            $scope.licencaPremio.dataRetorno = moment($scope.licencaPremio.dataInicio).add($scope.licencaPremio.dias, 'days');
        }

        $scope.adicionarLicencaPremio = function () {
            if($scope.licencaPremio.dataInicio == "" && $scope.licencaPremio.dias){
                $scope.showSimpleToast("Para Inserir, prencha a Data Inicio Licença e Dias");
            }else{
                if($scope.licencaPremio.dataInicio != "" && typeof($scope.licencaPremio.dataInicio) != "undefined"){
                    $scope.licencaPremio.dataInicio = moment($scope.licencaPremio.dataInicio).toDate();
                }
                if($scope.licencaPremio.dataRetorno != "" && typeof($scope.licencaPremio.dataRetorno) != "undefined"){
                    $scope.licencaPremio.dataRetorno = moment($scope.licencaPremio.dataRetorno).toDate();
                }

                $scope.licencaPremio.funcionarioExercicioId = $scope.funcionarioExercicio.funcionarioId;

                $scope.licencasPremio.push($scope.licencaPremio);
                $scope.licencaPremio = {};
            }
            $scope.verifyPermission = false;
        }

        $scope.removerLicencaPremio = function (index) {
            $scope.licencasPremio.splice(index, 1);
        } 
        
        $scope.editarLicencaPremio = function (index) {
            $scope.licencaPremio = $scope.licencasPremio[index];
            if($scope.licencasPremio[index].dataInicio != "" && typeof($scope.licencasPremio[index].dataInicio) != "undefined"){
                $scope.licencaPremio.dataInicio = moment($scope.licencasPremio[index].dataInicio);
                $scope.licencaPremio.dataRetorno = moment($scope.licencasPremio[index].dataRetorno);
            }

            $scope.licencasPremio.splice(index, 1);
            $scope.verifyPermission = true;
        }        


        $scope.validarForm = function() {
            if(
                $scope.licencaPremio.dataInicio &&
                $scope.licencaPremio.dias != ""
                ){
                $scope.verifyPermission = true;
            }
        };

        if ($state.params && $state.params.funcionarioId) {
            $rootScope.$broadcast('preloader:active');
            GenericoService.GetById('/funcionario/' + $state.params.funcionarioId, function (response) {
                $rootScope.$broadcast('preloader:hide');
                if (response.status === 200) {
                    $scope.funcionario = response.data;
                    $scope.funcionarioExercicio.funcionarioId = $scope.funcionario.id;
                    GenericoService.GetById('/funcionarioExercicio/' + $state.params.exercicioId, function (response) {
                        $rootScope.$broadcast('preloader:hide');
                        if (response.status === 200) {
                            $scope.funcionarioExercicio.exercicio = response.data.exercicio;
                        } else {
                            $scope.showSimpleToast("Exercício não encontrado na base");
                        }
                    }); 
                } else {
                    $scope.showSimpleToast("Funcionário não encontrado na base");
                }
            });  
        } 

        if ($state.params && $state.params.funcionarioId && $state.params.exercicioId) {
            $rootScope.$broadcast('preloader:active');
            GenericoService.GetById('/licencasPremio/' + $state.params.exercicioId, function (response) {
                $rootScope.$broadcast('preloader:hide');
                if (response.status === 200) {
                    $scope.licencasPremio = response.data;
                    if($state.params.detalhes){
                        $scope.detalhes = true;
                    }
                    if(response.data.length > 0){
                        $scope.edit = true;
                    }
                } else {
                    $scope.showSimpleToast("Licença Premio não encontrada na base");
                }
            });  
        } 

        // if ($state.params && $state.params.detalhes){
        //     $rootScope.$broadcast('preloader:active');
        //     // GenericoService.GetById('/funcionario/' + $state.params.funcionarioId, function (response) {
        //     //     $rootScope.$broadcast('preloader:hide');
        //     //     if (response.status === 200) {
        //     //         $scope.dependentes.funcionario = response.data;
        //     //     } else {
        //     //         $scope.showSimpleToast("Funcionário não encontrado na base");
        //     //     }
        //     // });

        //     // GenericoService.GetById('/dependentes/' + parseInt($state.params.funcionarioId), function (response) {
        //     //     if (response.status === 200) {
                    
        //     //         $scope.dependentes.dependentes = response.data;
        //     //         for (var i = 0; i < response.data.length; i++) {
        //     //             $scope.dependentes.dependentes[i].funcionarioId = $scope.dependentes.dependentes[i].funcionario.id;
        //     //             $scope.detalhes = true;
        //     //         }
        //     //     }
        //     // });                 
            
        // }        

        $scope.goBack = function () {
            $location.path('/licencaPremio/gestao');
        }

        $scope.save = function () {
            $rootScope.$broadcast('preloader:active');
            if($scope.edit){
                GenericoService.Update('/licencaPremio', $scope.licencasPremio, function (response) {
                    $rootScope.$broadcast('preloader:hide');
                    if (response.status === 201 && response.data.success) {
                        $scope.showSimpleToast(response.data.message);
                        $location.path('/licencaPremio/gestao');
                    } else if (response.status === 400) {
                        $scope.showSimpleToast(response.data.message);
                        $location.path('/dependente/formulario/funcionario'+$state.params.funcionarioId+'/exercicio/'+$state.params.exercicioId);
                    }
                }); 
            }else{
                GenericoService.Create('/licencaPremio', $scope.licencasPremio, function (response) {
                    $rootScope.$broadcast('preloader:hide');
                    if (response.status === 201 && response.data.success) {
                        $scope.showSimpleToast(response.data.message);
                        $location.path('/licencaPremio/gestao');
                    } else if (response.status === 400) {
                        $scope.showSimpleToast(response.data.message);
                        $location.path('/dependente/formulario/funcionario'+$state.params.funcionarioId+'/exercicio/'+$state.params.exercicioId);
                    }
                }); 
            }
        }

        $scope.delete = function (index) {
            if($scope.licencasPremio[index].id){
                GenericoService.Delete('/licencaPremio/' + $scope.licencasPremio[index].id, function (response) {
                    $rootScope.$broadcast('preloader:hide');
                    if (response.status === 200) {
                        $scope.showSimpleToast(response.data.message);
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