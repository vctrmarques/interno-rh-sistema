(function () {
    'use strict';

    angular.module('app.page')
        .controller('relatorioRecrutamentoESelecaoCtrl',
            ['configValue', '$scope', '$mdToast', '$mdDialog', '$rootScope', '$location', '$state', 'GenericoService', '$filter', relatorioRecrutamentoESelecaoCtrl]
        );

    function relatorioRecrutamentoESelecaoCtrl(configValue, $scope, $mdToast, $mdDialog, $rootScope, $location, $state, GenericoService, $filter) {
        function init() {
            
            $scope.filtro = {
                tipo: null,
                tipoRelatorio: null,
                tipoGrafico: null,
                processoOuTermo: '',
                dataInicio: undefined,
                dataFim: undefined,
            };

            $scope.resultado = {
                count: 0,
                dados: []
            };
            $scope.listaTipoGrafico = [
                {id: 1, nome: 'Barra'},
                {id: 2, nome: 'Pizza'},
            ];
        };

        // $scope.gotToFiltros = function() {
        //     if ($scope.tipo != undefined && $scope.tipo != '') {

        //         if ($scope.tipo == 'sintetico') {
        //             $scope.filtro.tipoSintetico = true;
        //             $scope.filtro.tipoAnalitico = false;

        //         } else if ($scope.tipo == 'analitico') {
        //             $scope.filtro.tipoSintetico = false;
        //             $scope.filtro.tipoAnalitico = true;
        //         }
        //     }

        //     $rootScope.relatorioRecrutamentoESelecaoFiltro = $scope.filtro;
        //     $location.path('/relatorios/recrutamentoESelecao/filtro');
        // };

        $scope.getBack = function() {
            $location.path('/relatorios/recrutamentoESelecao/relatorioRecrutamentoESelecao');
        };

        $scope.gotToGraficos = function() {
            $scope.mostrarGraficoPizza = false;
            $scope.mostrarGraficoBarra = false;

            $scope.tipoGraficoSelecionado = undefined;
            $scope.dadosGrafico = undefined;

            $location.path('/relatorios/recrutamentoESelecao/grafico');
        };

        $scope.exibirInformacoes = function(){
            if($scope.filtro.tipo == 'grafico'){
                $scope.trocarGrafico();
            }else if($scope.filtro.tipo == 'relatorio'){
                $scope.imrprimir();
            }
        }

        $scope.cleanFiltroRelatorios = function(){
             $scope.filtro.concluidoAntes = false;
            $scope.filtro.concluidoNoLimite = false
            $scope.filtro.concluidoDepois = false;
            $scope.filtro.todosProcessos = false
            $scope.filtro.tempoAtendimentoVaga = false;
            $scope.filtro.vagasAbertas = false;
            $scope.filtro.efetivosAposContratoExperiencia = false;
            $scope.filtro.processoOuTermo = '';
        }

        $scope.imprimir = function() {
            $rootScope.$broadcast('preloader:active');
            var parametros = {
                tipoSintetico: ($scope.filtro.tipoRelatorio == 'sintetico' ? true : false),
                tipoAnalitico: ($scope.filtro.tipoRelatorio == 'analitico' ? true : false),
                concluidoAntes: $scope.filtro.concluidoAntes,
                concluidoNoLimite: $scope.filtro.concluidoNoLimite,
                concluidoDepois: $scope.filtro.concluidoDepois,
                todosProcessos: $scope.filtro.todosProcessos,
                tempoAtendimentoVaga: $scope.filtro.tempoAtendimentoVaga,
                vagasAbertas: $scope.filtro.vagasAbertas,
                efetivosAposContratoExperiencia: $scope.filtro.efetivosAposContratoExperiencia,
                processoOuTermo: $scope.filtro.processoOuTermo,
                dataInicio: $scope.filtro.dataInicio,
                dataFim: $scope.filtro.dataInicio
            }
            GenericoService.Create('/relatorio/requisicaoPessoal', parametros, function (response) {
                GenericoService.ToDataURL($rootScope.defaultPath + configValue.logo, function (dataURL) {
                    pdfMake.createPdf(getDocDefinitionRecrutamentoESelecao(response.data, dataURL)).open();
                });
                $scope.getBack();
                $rootScope.$broadcast('preloader:hide');
            });
        };

        $scope.trocarGrafico = function() {

            var parametros = {
                tipoSintetico: false,
                tipoAnalitico: false,
                concluidoAntes: true,
                concluidoNoLimite: true,
                concluidoDepois: true,
                todosProcessos: true,
                tempoAtendimentoVaga: false,
                vagasAbertas: false,
                efetivosAposContratoExperiencia: false,
                processoOuTermo: false,
                dataInicio: $scope.filtro.dataInicio,
                dataFim: $scope.filtro.dataInicio

            }
            $scope.dadosGrafico = undefined;
            var nomePartes = [
                'Concluídos antes da Data Limite',
                'Concluídos na Data Limite',
                'Concluídos até 10 dias após a Data Limite',
                'Concluídos com mais de 10 dias após a Data Limite'
            ];

            $rootScope.$broadcast('preloader:active');
            GenericoService.Create('/relatorio/requisicaoPessoal/grafico', parametros, function (response) {
                $scope.dadosGrafico = response;
                $rootScope.$broadcast('preloader:hide');
                if ($scope.filtro.tipoGrafico == 1) {
                    var chart = echarts.init(document.getElementById('graficoBarra'));
                    chart.setOption(getRelatorioRecrutamentoGraficoBarras(nomePartes, $scope.dadosGrafico));
                    $scope.mostrarGraficoBarra = true;
                    $scope.mostrarGraficoPizza = false;
                } else if ($scope.filtro.tipoGrafico == 2) {
                    var chart = echarts.init(document.getElementById('graficoPizza'));
                    chart.setOption(getRelatorioRecrutamentoGraficoPizza(nomePartes, $scope.dadosGrafico));
                    $scope.mostrarGraficoBarra = false;
                    $scope.mostrarGraficoPizza = true;
                }
            });

            
        };

        $scope.showDialog = function (ev) {
            var $this =  $scope;
            $mdDialog.show({
                require: { container: '^^relatorioRecrutamentoESelecaoCtrl' },
                controller: ['$scope', '$q', function ($scope, $q) {

                    $scope.filtro = $this.filtro;

                    $scope.clickDialog = function () {
                        $mdDialog.cancel(); 
                    }

                    $scope.cancel = function () {
                        $mdDialog.cancel();
                    };
                }],
                templateUrl: 'app/page/relatorios/recrutamentoESelecao/filtro.tmpl.html',
                parent: angular.element(document.body),
                targetEvent: ev,
                clickOutsideToClose: true,
            });
        }
        // GRÁFICO BARRA
        function getRelatorioRecrutamentoGraficoBarras(nomePartes, dados) {
            var dadosComoSerie = [
                getSerieParaGraficoBarras(nomePartes[0], dados.totalConcluidosAntes),
                getSerieParaGraficoBarras(nomePartes[1], dados.totalConcluidosNoLimite),
                getSerieParaGraficoBarras(nomePartes[2], dados.totalAte10diasDataLimite),
                getSerieParaGraficoBarras(nomePartes[3], dados.totalAlem10diasDataLimite)
            ];
        
            return  {
                tooltip: {
                    trigger: 'axis'
                },
                legend: {
                    data: nomePartes
                },
                toolbox: {
                    show: true,
                    feature: {
                        restore: {show: true, title: "restaurar"},
                        saveAsImage: {show: true, title: "salvar imagem"}
                    }
                },
                calculable: true,
                xAxis: [{ type: 'category', data: ['1'] }],
                yAxis: [{ type: 'value' }],
                series: dadosComoSerie
            };
        }
        
        function getSerieParaGraficoBarras(nome, total) {
            return {
                name: nome,
                type: 'bar',
                data: [total],
                markPoint: {
                    data: [
                        {type: 'max', name: 'Max'},
                        {type: 'min', name: 'Min'}
                    ]
                },
                markLine: {
                    data: [{type: 'average', name: 'Average'}]
                }
            };
        }

        // GRÁFICO PIZZA
        function getRelatorioRecrutamentoGraficoPizza(nomePartes, dados) {
            var dados = [
                {name:nomePartes[0], value:dados.totalConcluidosAntes},
                {name:nomePartes[1], value:dados.totalConcluidosNoLimite},
                {name:nomePartes[2], value:dados.totalAte10diasDataLimite},
                {name:nomePartes[3], value:dados.totalAlem10diasDataLimite}
            ];
        
            return {
                title : {
                    text: 'Informações',
                    x:'center'
                },
                tooltip : {
                    trigger: 'item',
                    formatter: "{a} <br/>{b} : {c} ({d}%)"
                },
                legend: {
                    orient : 'vertical',
                    x : 'left',
                    data:nomePartes
                },
                toolbox: {
                    show : true,
                    feature : {
                        restore : {show: true, title: "restaurar"},
                        saveAsImage : {show: true, title: "Salvar imagem"}
                    }
                },
                calculable : true,
                series : [{
                    name:'Vist source',
                    type:'pie',
                    radius : '55%',
                    center: ['50%', '60%'],
                    data:dados
                }]
            };
        }

        init();
    };
})();
