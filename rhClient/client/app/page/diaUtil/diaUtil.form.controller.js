(function () {
    'use strict';
    angular.module('app.page')
        .controller('diaUtilFormCtrl', ['$scope', '$mdToast', '$location', '$state', '$rootScope', 'GenericoService', diaUtilFormCtrl]);

    function diaUtilFormCtrl($scope, $mdToast, $location, $state, $rootScope, GenericoService) {

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

        $scope.botoesGestao = false;
        GenericoService.VerificaPermissao('/usuario/verificaPermissao', 'ROLE_ADMIN').then(
            function (response) {
                if (response.status === 200) {
                    $scope.botoesGestao = true;
                }
            });        

        $scope.acessaTela();

        $scope.daysOff =  [];
        $scope.diaUtil = {};
        $scope.arrayDiasParaSalvar = {}
        $scope.highlightDays = [];
        $scope.selecionarMes = true;
        
        $scope.ano = moment().year();     

        $scope.clickSabado = function (event, click) {
            if(event){
                var saturday = moment().year($scope.ano).startOf('year').day(6);

                if (saturday.date() > 7) saturday.add(7,'d');
                var year = saturday.year();

                while(year === saturday.year()){
                    $scope.diaUtil.arrayListDiasUteis.push(moment(saturday.toString()));
                    saturday.add(7,'d');
                } 
            }else{
                var saturday = moment().year($scope.ano).startOf('year').day(6);

                if (saturday.date() > 7) saturday.add(7,'d');
                var year = saturday.year();

                while(year === saturday.year()){
                    
                    var index = $scope.diaUtil.arrayListDiasUteis.indexOf(moment(saturday.toString()));
                    $scope.diaUtil.arrayListDiasUteis.splice(index, 1);

                    saturday.add(7,'d');
                } 
            }

        };  

        $scope.clickDomingo = function (event, click) {
            if(event){
                var sunday = moment().year($scope.ano).startOf('year').day(0);

                if (sunday.date() > 7) sunday.add(7,'d');
                var year = sunday.year();

                while(year === sunday.year()){
                    $scope.diaUtil.arrayListDiasUteis.push(moment(sunday.toString()));
                    sunday.add(7,'d');
                } 
            }else{
                var sunday = moment().startOf('year').day(0);

                if (sunday.date() > 7) sunday.add(7,'d');
                var year = sunday.year();

                while(year === sunday.year()){

                    var index = $scope.diaUtil.arrayListDiasUteis.indexOf(moment(sunday.toString()));
                    $scope.diaUtil.arrayListDiasUteis.splice(index, 1);
              
                    sunday.add(7,'d');
                }                

            }

        };        

        $scope.setDatas = function (datas){
            for (var index = 0; index < datas.length; index++) {
                var data = datas[index].ano+""+datas[index].mes+""+datas[index].dia;
                $scope.diaUtil.arrayListDiasUteis[index] = moment(data, "YYYYMMDD");
            }         
        }

        $scope.mes = "";
        $scope.anosPassados = 5;
        $scope.anosFuturos = 10;
        $scope.desabilitarNavegador = false;      

        $scope.setDatasDetalhadosPorMes = function (datas){
            for (var index = 0; index < datas.length; index++) {
                var data = datas[index].ano+""+datas[index].mes+""+datas[index].dia;
                $scope.highlightDays[index] = {date: moment(data, "YYYYMMDD"), css: 'off', selectable: false, title: 'Não trabalhamos este dia'};
            }
            
            if(datas.length == 0){
                var mesAno = config.params.ano+""+config.params.mes;
                $scope.mes = moment(mesAno, "YYYYMM");   
            }else{
                var mesAno = datas[0].ano+""+datas[0].mes;
                $scope.mes = moment(mesAno, "YYYYMM"); 
            }

            $scope.selecionarMes = false;
            $scope.anosPassados = 0;
            $scope.anosFuturos = 0;
            $scope.desabilitarNavegador = true;      
        }  
        
        
        $scope.setDatasParaEdicao = function (datas){
            for (var index = 0; index < datas.length; index++) {
                var data = datas[index].ano+""+datas[index].mes+""+datas[index].dia;
                $scope.diaUtil.arrayListDiasUteis[index] = moment(data, "YYYYMMDD");
            }  

            if(datas.length == 0){
                var mesAno = config.params.ano+""+config.params.mes;
                $scope.mes = moment(mesAno, "YYYYMM");   
            }else{
                var mesAno = datas[0].ano+""+datas[0].mes;
                $scope.mes = moment(mesAno, "YYYYMM"); 
            }            
   
            $scope.selecionarMes = false;
            $scope.anosPassados = 0;
            $scope.anosFuturos = 0;
            $scope.desabilitarNavegador = true;                    
        }     
        
        $scope.diasUteis = [];

        if ($state.params && $state.params.detalhes) {
            $scope.detalhes = true;
            $scope.editar = false;
            var config = {
                params: {
                    ano: $state.params.ano,
                    mes: $state.params.mes
                }
            };    
            
            $scope.oneDaySelectionOnly = function (event, date) {
                $scope.diaUtil.arrayListDiasUteis.length = -1;
            };  
                        
            GenericoService.GetAll('/diasUteisParaDetalhar/', config).then(
                function (response) {
                    if (response.status === 200) {
                        $scope.setDatasDetalhadosPorMes(response.data);
                        $rootScope.$broadcast('preloader:hide');
                    } else {
                        $scope.showSimpleToast("Não foi possível carregar os dados.")
                    }
                });            
        }else if ($state.params && $state.params.editar){
            $scope.editar = true;
            $scope.detalhes = false;
            var config = {
                params: {
                    ano: $state.params.ano,
                    mes: $state.params.mes
                }
            };    
                        
            GenericoService.GetAll('/diasUteisParaDetalhar/', config).then(
                function (response) {
                    if (response.status === 200) {
                        $scope.setDatasParaEdicao(response.data);
                        $rootScope.$broadcast('preloader:hide');
                    } else {
                        $scope.showSimpleToast("Não foi possível carregar os dados.")
                    }
                });             
        }else{
            GenericoService.GetAll('/diasUteisParaCadastrar').then(
                function (response) {
                    if (response.status === 200) {
                        $scope.setDatas(response.data);
                        $rootScope.$broadcast('preloader:hide');
                    } else {
                        $scope.showSimpleToast("Não foi possível carregar os dados.")
                    }
                });            
        }

        $scope.goBack = function () {
            $location.path('/diaUtil/gestao');
        }
        $scope.save = function () {
            $rootScope.$broadcast('preloader:active');
            if ($scope.editar) {

                var paramsParaUpdate = {
                    ano: $state.params.ano,
                    mes: $state.params.mes,
                    arrayListDiasUteis: $scope.diaUtil.arrayListDiasUteis
                };      
                
                GenericoService.Create('/atualiarDiasUteisPorMes',paramsParaUpdate, function (response) {
                    $rootScope.$broadcast('preloader:hide');
                    if (response.status === 201 && response.data.success) {
                        $scope.showSimpleToast('Dias Úteis atualizados com sucesso.');
                        $location.path('/diaUtil/gestao');
                    } else if (response.status === 400) {
                        $scope.showSimpleToast('Não foi possível atualizar os Dias Úteis');
                        $location.path('/diaUtil/gestao')
                    }
                });
            } else if (!$scope.destalhes) {               
                GenericoService.Delete('/diasUteis/', function (response) {
                    $rootScope.$broadcast('preloader:hide');
                    if (response.status === 200) {
  
                    }
                });
                      
                GenericoService.Create('/diaUtil', $scope.diaUtil, function (response) {
                    if (response.status === 201 && response.data.success) {
                        $scope.showSimpleToast('Dias Úteis cadastrado com sucesso.');
                        $location.path('/diaUtil/gestao');
                    } else if (response.status === 400) {
                        $scope.showSimpleToast('Não foi possível salvar Dias Úteis');
                        $location.path('/diaUtil/formulario');  
                    }
                });                
            }
        }

        $scope.logYearChanged = function(newYear){
            $scope.ano = newYear.format('YYYY');
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