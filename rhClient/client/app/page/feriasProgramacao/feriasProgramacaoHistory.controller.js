(function () {
    'use strict';

    angular.module('app.page')
        .controller('feriasProgramacaoHistoryCtrl', ['configValue', '$scope', '$mdToast', '$location', '$state', '$rootScope', 'GenericoService', feriasProgramacaoHistoryCtrl]);

    function feriasProgramacaoHistoryCtrl(configValue, $scope, $mdToast, $location, $state, $rootScope, GenericoService) {

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

        $scope.feriasProgramacoes = {}
        $scope.funcionario = {};

        if ($state.params && $state.params.funcionarioId) {
            $rootScope.$broadcast('preloader:active');
            GenericoService.GetById('/funcionario/' + $state.params.funcionarioId, function (response) {
                $rootScope.$broadcast('preloader:hide');
                if (response.status === 200) {
                    $scope.funcionario = response.data;
                    
                    GenericoService.GetById('/feriasProgramacoes/' + $state.params.funcionarioId, function (response) {
                        $rootScope.$broadcast('preloader:hide');
                        if (response.status === 200) {
                            $scope.feriasProgramacoes = response.data;
                        } else {
                            $scope.showSimpleToast("Férias Programadas não encontradas na base");
                        }
                    });  

                } else {
                    $scope.showSimpleToast("Funcionário não encontrado na base");
                }
            });  
        } 

        $scope.goBack = function () {
            $location.path('/feriasProgramacao/gestao');
        }

        $scope.showRelatorio = function () {
            $rootScope.$broadcast('preloader:active');
            GenericoService.ToDataURL($rootScope.defaultPath + configValue.logo,
                function (dataURL) {
                    pdfMake.createPdf(getDocDefinition($scope.funcionario, $scope.feriasProgramacoes, dataURL)).open()
                });
            $rootScope.$broadcast('preloader:hide');
        }

        function getDocDefinition(funcionario, feriasprogramacoes, dataURL) {

            var docDefinition = {};
            docDefinition.content = [];

            var brasao = { image: dataURL, width: 70, alignment: 'center' };
            docDefinition.content.push(brasao);

            var orgao = { text: $rootScope.pageTitle, alignment: 'center', margin: [0, 10], bold: true };
            docDefinition.content.push(orgao);

            var titulo = { text: 'Tabela de Férias de Programação', alignment: 'center', margin: [0, 10] };
            docDefinition.content.push(titulo);

            var listaTitulo = {
                style: 'tableExample',
                table: {
                    widths: ["*"],
                    body: [
                        [
                            { text: 'Listagem', bold: true, fillColor: 'lightblue' }
                        ]
                    ]
                },
                margin: [0, 15, 0, 10],
                layout: 'noBorders'
            }
            docDefinition.content.push(listaTitulo)

            var listaFuncionario = {
                style: 'tableExample',
                table: {
                    widths: ["*", "*", "*"],
                    body: [
                        [
                            { text: 'Filial', bold: true, fillColor: 'lightgray' },
                            { text: 'Matrícula', bold: true, fillColor: 'lightgray' },
                            { text: 'Nome', bold: true, fillColor: 'lightgray' },
                        ]
                    ]
                },
                layout: {
                    hLineWidth: function (i, node) {
                        return 1;
                    },
                    vLineWidth: function (i, node) {
                        return 1;
                    },
                    hLineColor: function (i, node) {
                        return 'gray';
                    },
                    vLineColor: function (i, node) {
                        return 'gray';
                    }
                }
            }

            var body = [
                { text: funcionario.empresa.nomeFilial , fontSize: 10, alignment: 'center'},
                { text: funcionario.matricula, fontSize: 10, alignment: 'center'},
                { text: funcionario.nome, fontSize: 10, alignment: 'center'}
            ] 

            listaFuncionario.table.body.push(body)
            docDefinition.content.push(listaFuncionario)            

            var lista = {
                style: 'tableExample',
                table: {
                    headerRows: 1,
                    // dontBreakRows: true,
                    // keepWithHeaderRows: 1,
                    widths: [ 'auto', '*', 'auto', 'auto', 'auto'],
                    body: [
                        [{ text: 'Período Aquisitivo', fontSize: 10, fillColor: 'lightgray', style: 'tableHeader', alignment: 'center' },
                        { text: 'Status', fontSize: 10, fillColor: 'lightgray', style: 'tableHeader', alignment: 'center' },
                        { text: 'Partição 1º', fontSize: 10, fillColor: 'lightgray', style: 'tableHeader', alignment: 'center' },
                        { text: 'Partição 2º', fontSize: 10, fillColor: 'lightgray', style: 'tableHeader', alignment: 'center' },
                        { text: 'Partição 3º', fontSize: 10, fillColor: 'lightgray', style: 'tableHeader', alignment: 'center' }
                        ]
                    ]
                },
                layout: {
                    hLineWidth: function (i, node) {
                        return 1;
                    },
                    vLineWidth: function (i, node) {
                        return 1;
                    },
                    hLineColor: function (i, node) {
                        return 'gray';
                    },
                    vLineColor: function (i, node) {
                        return 'gray';
                    }
                }
            }

            var i;
            for (i = 0; i < feriasprogramacoes.length; i++) {
                var exercicioInicio = moment(feriasprogramacoes[i].exercicioInicio).format('DD/MM/YYYY');
                var exercicioFim = moment(feriasprogramacoes[i].exercicioFim).format('DD/MM/YYYY');
                var dataInicialUm = "";
                var dataFinalUm = "";
                var dataInicialDois = "";
                var dataFinalDois = "";
                var dataInicialTres = "";
                var dataFinalTres = "";

                if(feriasprogramacoes[i].dataInicialUm){
                    dataInicialUm = moment(feriasprogramacoes[i].dataInicialUm).format('DD/MM/YYYY');
                    dataFinalUm = moment(feriasprogramacoes[i].dataFinalUm).format('DD/MM/YYYY');                    
                }

                if(feriasprogramacoes[i].dataInicialDois){
                    dataInicialDois = moment(feriasprogramacoes[i].dataInicialDois).format('DD/MM/YYYY');
                    dataFinalDois = moment(feriasprogramacoes[i].dataFinalDois).format('DD/MM/YYYY');
                }
                
                if(feriasprogramacoes[i].dataInicialTres){
                    dataInicialTres = moment(feriasprogramacoes[i].dataInicialTres).format('DD/MM/YYYY');
                    dataFinalTres = moment(feriasprogramacoes[i].dataFinalTres).format('DD/MM/YYYY');
                }

                var body = [
                    { text: exercicioInicio + "-" + exercicioFim , fontSize: 10, alignment: 'center'},
                    { text: feriasprogramacoes[i].situacaoLabel, fontSize: 8, alignment: 'center'},
                    { text: dataInicialUm + "-" + dataFinalUm, fontSize: 10, alignment: 'center'},
                    { text: dataInicialDois + "-" + dataFinalDois, fontSize: 10, alignment: 'center'},
                    { text: dataInicialTres + "-" + dataFinalTres, fontSize: 10, alignment: 'center'}
                ]  

                lista.table.body.push(body)
            }
            docDefinition.content.push(lista);

            return docDefinition;
        }        
    }
})(); 