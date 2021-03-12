(function () {
    'use strict';
    angular.module('app.page')
        .controller('frequenciaFormCtrl', ['$scope', '$http', 'configValue', '$timeout', '$mdDialog', '$q', 'FileUploader', '$mdToast', '$location', '$state', '$rootScope', 'GenericoService', frequenciaFormCtrl]);

    function frequenciaFormCtrl($scope, $http, configValue, $timeout, $mdDialog, $q, FileUploader, $mdToast, $location, $state, $rootScope, GenericoService) {

        $scope.acessaTela = function () {
            GenericoService.VerificaPermissao('/usuario/verificaPermissao', 'ROLE_ADMIN').then(
                function (response) {
                    if (response.status === 200) {}
                },
                function errorCallback(response) {
                    if (response.status === 400) {
                        $location.path('page/403');
                    }
                });
        }
        $scope.acessaTela();
        $scope.detalhes = false;
        $scope.frequencia = {};
        $scope.funcionarios = [];
        $scope.jaRegistrado = false;


        $scope.list = {
            "count": 0,
            "data": []
        };

        $scope.list = {
            "afastamento": [],
            "motivoAfastamento": [],
            "crm": [],
        }

        if ($state.params && $state.params.id && $state.params.ano && $state.params.mes) {
            $rootScope.$broadcast('preloader:active');
            GenericoService.GetById('/frequencias/' + $state.params.id +"/" + $state.params.mes + "/" + $state.params.ano, function (response) {
                $rootScope.$broadcast('preloader:hide');
                if (response.status === 200) {
                    $scope.funcionario = response.data
                    $scope.frequencias = $scope.funcionario.frequencias;
                    $scope.frequenciaJaRegistrada($scope.funcionario.id);
                } else {
                    $scope.showSimpleToast("Frequencia não encontrada na base");
                }
            });
        }

        if ($state.params && $state.params.detalhes) {
            $scope.detalhes = true;
        }

        $scope.goBack = function () {
            $location.path('/frequencia/gestao');
        }

        $scope.funcionarioSelect = function () {
    
            if ($scope.funcionario) {
                setHorasDiarias($scope.funcionario.jornadaTrabalho);
                $scope.frequencia.funcionarioId = $scope.funcionario.id;
                if (!$state.params.id && !$state.params.ano) {
                    $scope.getFrequenciasHoje($scope.funcionario.id);
                    $scope.frequenciaJaRegistrada($scope.funcionario.id);
                }
            }
        };

        function setHorasDiarias(jornada){
            if(jornada){
                if(jornada.horarioFlexivel){
                    $scope.horas = moment(jornada.jornada).format("HH:mm:ss");
                }else{
                    $scope.horas =  moment(jornada.horarioFlexivelInicio).format("HH:mm:ss")+
                    " às " + moment(jornada.intervaloFlexivelInicio).format("HH:mm:ss")+
                    " | " + moment(jornada.intervaloFlexivelFim).format("HH:mm:ss")+
                    " às " +moment(jornada.horarioFlexivelFim).format("HH:mm:ss");
                }
                   
                
            }
        }

        $scope.frequenciaClicada = function (frequencia) {
            if ($scope.detalhes) {
                $scope.frequencia = frequencia;
            } else {
                $scope.frequencia.id = frequencia.id ;

                $scope.frequencia.entradaUm = moment(frequencia.entradaUm);
                $scope.frequencia.entradaDois = moment(frequencia.entradaDois);
                $scope.frequencia.saidaDois = moment(frequencia.saidaDois);
                $scope.frequencia.saidaUm = moment(frequencia.saidaUm);
            }

        };

        $scope.faltaFormulario = function () {
            $location.path('/falta/formulario');
        }

        $scope.save = function () {
            $rootScope.$broadcast('preloader:active');
            if ($scope.frequencia.id) {

                GenericoService.Update('/frequencia', $scope.frequencia, function (response) {
                    $rootScope.$broadcast('preloader:hide');
                    if (response.status === 201 && response.data.success) {
                        $scope.showSimpleToast(response.data.message);
                        $location.path('/frequencia/gestao');
                    } else if (response.status === 400) {
                        $scope.showSimpleToast(response.data.message);
                        $location.path('frequencia/formulario/' + $state.params.id+"/"+new Date().getFullYear())
                    }
                });
            } else {
                GenericoService.Create('/frequencia', $scope.frequencia, function (response) {
                    $rootScope.$broadcast('preloader:hide');
                    if (response.status === 201 && response.data.success) {
                        $scope.showSimpleToast(response.data.message);
                        $location.path('/frequencia/gestao');
                    } else if (response.status === 400) {
                        $scope.showSimpleToast(response.data.message);
                        $location.path('/frequencia/formulario');
                    }
                });
            }
        }

        $scope.getFrequenciasHoje = function (funcionarioId) {
            $rootScope.$broadcast('preloader:active');
            var config = {
                params: {
                    funcionarioId: funcionarioId
                }
            };
            $scope.promise = GenericoService.GetAll('/frequencias/funcionario', config).then(
                function (response) {
                    if (response.status === 200) {
                        $scope.frequencias = response.data;
                        $rootScope.$broadcast('preloader:hide');
                    } else {
                        $scope.showSimpleToast("Não foi possível carregar os dados.")
                    }
                });
        }

        $scope.frequenciaJaRegistrada = function (funcionarioId) {
            var config = {
                params: {
                    funcionarioId: funcionarioId
                }
            };
            $scope.promise = GenericoService.GetAll('/frequencia/hoje', config).then(
                function (response) {
                    if (response.status === 200) { 
                        if (response.data == true) {
                            if($state.params && !$state.params.id && !$state.params.ano){
                                $mdDialog.show(
                                    $mdDialog.alert()
                                    .parent(angular.element(document.querySelector('#popupContainer')))
                                    .clickOutsideToClose(true)
                                    .title('Alerta!')
                                    .textContent('Ja existe um registro para este funcionário hoje')
                                    .ariaLabel('Alert Dialog Demo')
                                    .ok('OK')
    
                                );
                                $scope.jaRegistrado = true;
                            }
                            if($state.params && !$state.params.id && !$state.params.ano){
                                
                            }
                            
                        }
                        $rootScope.$broadcast('preloader:hide');
                    } else {
                        $scope.showSimpleToast("Não foi possível carregar os dados.")
                    }
                });
        }

        $scope.limparCampos = function () {
            $scope.frequencia = {};
            $scope.funcionario = null;
            $scope.frequencias = [];
            $scope.horas= null;
            $scope.jaRegistrado = false;
            $location.path('/frequencia/formulario');
        }
        $scope.querySearchFuncionario = function (query) {

            var deferred = $q.defer();
            var config = {
                params: {
                    search: query
                }
            }
            if (query) {
                if (query.length > 2) {
                    GenericoService.GetAll('/funcionario/searchNomeOrMatricula', config).then(
                        function (response) {
                            if (response.data && response.data.length > 0) {
                                $scope.funcionarios = response.data;
                                deferred.resolve(response.data);
                            }
                        });
                } else {
                    $scope.funcionarios = [];
                }
            }
            return deferred.promise;
        };


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
