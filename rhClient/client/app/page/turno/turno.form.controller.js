(function () {
    'use strict';

    angular.module('app.page')
        .controller('turnoFormCtrl', ['$scope', '$mdToast', '$location', '$state', '$rootScope', 'GenericoService', turnoFormCtrl]);

    function turnoFormCtrl($scope, $mdToast, $location, $state, $rootScope, GenericoService) {

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
        $scope.turnoFolgas = {'Seg.' : false, 'Ter.' : false, 'Qua.' : false,  'Qui.' : false, 'Sex.' : false,  'Sáb.' : false, 'Dom.' : false};
        $scope.folgasList = ['Seg.', 'Ter.', 'Qua.',  'Qui.', 'Sex.',  'Sáb.','Dom.' ];
        $scope.minDateVigencia = new Date();
        $scope.turno = {horarioFlexivel : false, intervaloFlexivel : false};

        if ($state.params && $state.params.id) {
            $rootScope.$broadcast('preloader:active');
            GenericoService.GetById('/turno/' + $state.params.id, function (response) {
                $rootScope.$broadcast('preloader:hide');
                if (response.status === 200) {
                    $scope.turno = response.data;
                    $scope.turno.dataInicio = new Date($scope.turno.dataInicio);
                    $scope.turno.dataFim = new Date($scope.turno.dataFim);
                    $scope.turno.jornada = new Date($scope.turno.jornada);
                    $scope.turno.intervalo = new Date($scope.turno.intervalo);

                    if($scope.turno.dataInicio){
                        $scope.turno.dataInicio = moment(response.data.dataInicio);
                    }

                    if($scope.turno.dataFim){
                        $scope.turno.dataFim = moment(response.data.dataFim);
                    }

                    if(!$scope.turno.horarioFlexivel){
                        $scope.turno.horarioFlexivelInicio = new Date($scope.turno.horarioFlexivelInicio);
                        $scope.turno.horarioFlexivelFim = new Date($scope.turno.horarioFlexivelFim);
                    }
                    
                    if(!$scope.turno.intervaloFlexivel){
                        $scope.turno.intervaloFlexivelInicio = new Date($scope.turno.intervaloFlexivelInicio);
                        $scope.turno.intervaloFlexivelFim = new Date($scope.turno.intervaloFlexivelFim);
                    }

                    if($scope.turno.turnoFolgaResponse.length){
                        for(var i = 0; i < $scope.turno.turnoFolgaResponse.length; i++){
                            $scope.turnoFolgas[$scope.turno.turnoFolgaResponse[i].stringDia] = true;
                        }    
                    }

                } else {
                    $scope.showSimpleToast("Turno não encontrado na base");
                }
            });
        }

        if ($state.params && $state.params.detalhes) {
            $scope.detalhes = true;
        }

        $scope.goBack = function () {
            $location.path('/turno/gestao');
        }

        $scope.save = function () {
            $rootScope.$broadcast('preloader:active');

            $scope.turno.turnoFolga = [];
            angular.forEach($scope.turnoFolgas, function(value, key) {
                
                if(value){
                    $scope.turno.turnoFolga.push({dia : key});
                }
            });

            if ($scope.turno.id) {
                GenericoService.Update('/turno',$scope.turno, function (response) {
                    $rootScope.$broadcast('preloader:hide');
                    if (response.status === 201 && response.data.success) {
                        $scope.showSimpleToast(response.data.message);
                        $location.path('/turno/gestao');
                    } else if (response.status === 400) {
                        $scope.showSimpleToast(response.data.message);
                        $location.path('turno/formulario/' + $state.params.id)
                    }
                });
            } else {
                GenericoService.Create('/turno', $scope.turno, function (response) {
                    $rootScope.$broadcast('preloader:hide');
                    if (response.status === 201 && response.data.success) {
                        $scope.showSimpleToast(response.data.message);
                        $location.path('/turno/gestao');
                    } else if (response.status === 400) {
                        $scope.showSimpleToast(response.data.message);
                        $location.path('/turno/formulario');
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

        $scope.changeIntervaloFlexivel = function(){
            if($scope.turno.intervaloFlexivel){
                $scope.turno.intervalo = null;
            }else{
                
                $scope.turno.intervaloFlexivelInicio = null;
                $scope.turno.intervaloFlexivelFim = null;
            }
        }


        $scope.changeHorarioFlexivel = function(){
            if($scope.turno.horarioFlexivel){
                $scope.turno.jornada = null;               
            }else{
                $scope.turno.horarioFlexivelInicio = null;
                $scope.turno.horarioFlexivelFim = null;
            }
        }

        $scope.calcularJornada = function(){

            console.log($scope.turno.horarioFlexivel);
            if($scope.turno.horarioFlexivel){
                return;
            }

            if($scope.turno.horarioFlexivelInicio && $scope.turno.horarioFlexivelFim){
                var d1 = moment($scope.turno.horarioFlexivelInicio);
                var d2 = moment($scope.turno.horarioFlexivelFim);

                d2.subtract(d1);

                if($scope.turno.intervalo){
                    var d3 =  moment($scope.turno.intervalo);
                    d2.subtract(d3);

                }

                $scope.turno.jornada = d2.toDate();
            }

        }

        $scope.calcularIntervalo = function(){
            if($scope.turno.intervaloFlexivelInicio && $scope.turno.intervaloFlexivelFim){
                var d1 = moment($scope.turno.intervaloFlexivelInicio);
                var d2 = moment($scope.turno.intervaloFlexivelFim);

                d2.subtract(d1);
                $scope.turno.intervalo = d2.toDate();

               
                $scope.calcularJornada();
               
                
            }

        }

        

    }

   

})(); 