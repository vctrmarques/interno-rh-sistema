(function () {
    'use strict';

    angular.module('app.page')
        .controller('simuladorAposentadoriaFormCtrl', ['$scope', '$mdToast', '$location', 'SimuladorService', '$rootScope', 'GenericoService', simuladorAposentadoriaFormCtrl]);

    function simuladorAposentadoriaFormCtrl($scope, $mdToast, $location, SimuladorService, $rootScope, GenericoService) {

        // $document.scrollTopAnimated(0, 300).then(function () { });

        var section3 = angular.element(document.getElementById('section-3'));
        $scope.toSection3 = function () {
            // $document.scrollToElementAnimated(section3);
        }

        //Construtor básico do objeto principal
        $scope.simuladorAposentadoriaInit = {
            deducoes: [],
            outrosPeriodos: [],
            outrosPeriodosPublicos: [],
            averbacoes: [],
            licencasPremio: [],
            qtdLicencaPremioFator1: 0,
            qtdLicencaPremioFator2: 0
        };

        $scope.reset = function () {
            $scope.simuladorAposentadoria = angular.copy($scope.simuladorAposentadoriaInit);
            if ($scope.simuAposForm) {
                $scope.simuAposForm.$setPristine();
                $scope.simuAposForm.$setUntouched();
            }
            SimuladorService.setSimuladorAposentadoria(null);
            window.scrollTo(0, 0);
        };

        //Construtor do objeto deducao
        $scope.deducaoInit = {
            inicio: null,
            fim: null,
            ano: null,
            mes: null,
            totalDias: null,
            tipo: 'PERIODO',
            contribuicao: null
        }
        $scope.deducao = angular.copy($scope.deducaoInit);

        //Construtor do objeto outroPeriodo
        $scope.outroPeriodoInit = {
            inicio: null,
            fim: null,
            ano: null,
            mes: null,
            totalDias: null,
            tipo: 'PERIODO'
        }
        $scope.outroPeriodo = angular.copy($scope.outroPeriodoInit);

        //Construtor do objeto outroPeriodoPublico
        $scope.outroPeriodoPublicoInit = {
            inicio: null,
            fim: null,
            ano: null,
            mes: null,
            totalDias: null,
            tipo: 'PERIODO',
            atividade: null
        }
        $scope.outroPeriodoPublico = angular.copy($scope.outroPeriodoPublicoInit);

        //Construtor do objeto averbacao
        $scope.averbacaoInit = {
            inicio: null,
            fim: null,
            ano: null,
            mes: null,
            totalDias: null,
            tipo: 'PERIODO',
            atividade: null
        }
        $scope.averbacao = angular.copy($scope.averbacaoInit);

        //Array de sexos
        $scope.sexos = [{ label: 'Masculino', value: 'masculino' }, { label: 'Feminino', value: 'feminino' }]

        //Array true/false para contribuição
        $scope.contribuicoes = [{ label: 'Sim', value: 'sim' }, { label: 'Não', value: 'nao' }]

        //Get das modalidades disponiveis 
        $rootScope.$broadcast('preloader:active');
        GenericoService.GetAll('/publico/regraAposentadoria/findAllModalidadesAposentadorias').then(
            function (response) {
                if (response.status === 200) {
                    $scope.modalidadesAposentadorias = response.data;
                    $rootScope.$broadcast('preloader:hide');
                } else {
                    $scope.showSimpleToast("Não foi possível carregar os dados das modalidades de aposentadorias.")
                }
            });

        //Valida a data inicio de tempo de contribuição
        $scope.validaTempoContribuicaoInicio = function () {
            var inicio = $scope.simuladorAposentadoria.tempoContribuicaoCargoAtualInicio;
            var fim = $scope.simuladorAposentadoria.tempoContribuicaoCargoAtualFim;
            if (inicio && fim) {
                if (inicio > fim) {
                    $scope.tempoContribuicaoCargoAtualInicioError = "A data de início não deve ser maior que a data de fim";
                    return;
                }

                //Checa se o inicio e o fim já foram adicionados anteriormente.
                if (existeDuplicidade(inicio, fim)) {
                    $scope.showSimpleToast("Este período já foi adicionado anteriormente.");
                    return;
                }

                var totalDias = fim.diff(inicio, 'days') + 1;
                $scope.simuladorAposentadoria.tempoContribuicaoCargoAtualDias = totalDias;
                if (totalDias < 1) {
                    $scope.tempoContribuicaoCargoAtualInicioError = "A data de início não deve ser maior que a data de fim";
                    return;
                }
                $scope.tempoContribuicaoCargoAtualInicioError = null;
                $scope.tempoContribuicaoCargoAtualFimError = null;
            }
        }

        //Valida a data inicio de tempo de contribuição
        $scope.validaTempoContribuicaoFim = function () {
            var inicio = $scope.simuladorAposentadoria.tempoContribuicaoCargoAtualInicio;
            var fim = $scope.simuladorAposentadoria.tempoContribuicaoCargoAtualFim;
            if (inicio && fim) {
                if (fim < inicio) {
                    $scope.tempoContribuicaoCargoAtualFimError = "A data final não deve ser menor que a data de inicio";
                    return;
                }

                //Checa se o inicio e o fim já foram adicionados anteriormente.
                if (existeDuplicidade(inicio, fim)) {
                    $scope.showSimpleToast("Este período já foi adicionado anteriormente.");
                    return;
                }

                var totalDias = fim.diff(inicio, 'days') + 1;
                $scope.simuladorAposentadoria.tempoContribuicaoCargoAtualDias = totalDias;
                if (totalDias < 1) {
                    $scope.tempoContribuicaoCargoAtualFimError = "A data final não deve ser menor que a data de inicio";
                    return;
                }
                $scope.tempoContribuicaoCargoAtualInicioError = null;
                $scope.tempoContribuicaoCargoAtualFimError = null;
            }
        }

        //Adiciona deduçao
        $scope.adicionarDeducao = function () {

            if ($scope.deducao.tipo === 'PERIODO') {

                // Zerando variáveis do tipo 'TEMPO'
                $scope.deducao.ano = null;
                $scope.deducao.mes = null;
                $scope.deducao.dia = null;

                //Checa se os campos estão devidamente preenchidos
                if (!$scope.deducao.inicio) {
                    $scope.showSimpleToast("Favor preencher o campo de inicio da dedução.");
                    return;
                }
                if (!$scope.deducao.fim) {
                    $scope.showSimpleToast("Favor preencher o campo de fim da dedução.");
                    return;
                }
                if (!$scope.deducao.contribuicao) {
                    $scope.showSimpleToast("Favor preencher o campo de contribuição da dedução.");
                    return;
                }

                //Checa se os valores são validaos
                if ($scope.deducao.inicio.isAfter($scope.deducao.fim, 'day')) {
                    $scope.showSimpleToast("A data de início não deve ser maior que a data de fim.");
                    return;
                }
                if ($scope.deducao.fim.isBefore($scope.deducao.inicio, 'day')) {
                    $scope.showSimpleToast("A data final não deve ser menor que a data de inicio.");
                    return;
                }

                //Checa se o inicio e o fim já foram adicionados anteriormente. 
                for (var index = 0; index < $scope.simuladorAposentadoria.deducoes.length; index++) {
                    var element = $scope.simuladorAposentadoria.deducoes[index];
                    var range = moment().range(element.inicio, element.fim);
                    if (range.contains($scope.deducao.inicio)) {
                        $scope.showSimpleToast("Este dedução já foi adicionada para as datas selecionadas.");
                        return;
                    } else if (range.contains($scope.deducao.fim)) {
                        $scope.showSimpleToast("Este dedução já foi adicionada para as datas selecionadas.");
                        return;
                    }
                }

                //Insere a dedução na lista de deduções do objeto principal
                $scope.deducao.totalDias = $scope.deducao.fim.diff($scope.deducao.inicio, 'days') + 1;

            } else {

                // Zerando variáveis do tipo 'PERIODO'
                $scope.deducao.inicio = null;
                $scope.deducao.fim = null;

                if (!$scope.deducao.ano && !$scope.deducao.mes && !$scope.deducao.dia) {
                    $scope.showSimpleToast("Favor preencher os campos de ano, mês ou dia da dedução.");
                    return;
                }

                var totalDias = 0;
                if ($scope.deducao.ano)
                    totalDias += $scope.deducao.ano * 365;
                if ($scope.deducao.mes)
                    totalDias += $scope.deducao.mes * 30;
                if ($scope.deducao.dia)
                    totalDias += $scope.deducao.dia;

                $scope.deducao.totalDias = totalDias;

            }

            //Insere a dedução na lista de deduções do objeto principal
            $scope.simuladorAposentadoria.deducoes.push($scope.deducao);
            $scope.deducao = angular.copy($scope.deducaoInit);

        }

        //Valida a data de inicio da deducao
        $scope.validaDeducaoInicio = function () {
            var inicio = $scope.deducao.inicio;
            var fim = $scope.deducao.fim;
            if (inicio && fim) {
                if (inicio.isAfter(fim, 'day')) {
                    $scope.showSimpleToast("A data de início não deve ser maior que a data de fim.");
                }
            }
        }

        //Valida a data de fim da deducao
        $scope.validaDeducaoFim = function () {
            var inicio = $scope.deducao.inicio;
            var fim = $scope.deducao.fim;
            if (inicio && fim) {
                if (fim.isBefore(inicio, 'day')) {
                    $scope.showSimpleToast("A data final não deve ser menor que a data de inicio.");
                }
            }
        }

        //Remove o item da lista
        $scope.removeItemDeducoes = function (index) {
            $scope.simuladorAposentadoria.deducoes.splice(index, 1)
        }

        //Adiciona outro periodo
        $scope.adicionarOutroPeriodo = function () {

            if ($scope.outroPeriodo.tipo === 'PERIODO') {

                // Zerando variáveis do tipo 'TEMPO'
                $scope.outroPeriodo.ano = null;
                $scope.outroPeriodo.mes = null;
                $scope.outroPeriodo.dia = null;

                //Checa se os campos estão devidamente preenchidos
                if (!$scope.outroPeriodo.inicio) {
                    $scope.showSimpleToast("Favor preencher o campo de inicio da dedução.");
                    return;
                }
                if (!$scope.outroPeriodo.fim) {
                    $scope.showSimpleToast("Favor preencher o campo de fim da dedução.");
                    return;
                }

                //Checa se os valores são validaos
                if ($scope.outroPeriodo.inicio.isAfter($scope.outroPeriodo.fim, 'day')) {
                    $scope.showSimpleToast("A data de início não deve ser maior que a data de fim.");
                    return;
                }
                if ($scope.outroPeriodo.fim.isBefore($scope.outroPeriodo.inicio, 'day')) {
                    $scope.showSimpleToast("A data final não deve ser menor que a data de inicio.");
                    return;
                }

                //Checa se o início e o fim estão contidos no início e fim do tempo de contribuição cargo atual. 
                var inicio = $scope.simuladorAposentadoria.tempoContribuicaoCargoAtualInicio;
                var fim = $scope.simuladorAposentadoria.tempoContribuicaoCargoAtualFim;
                if (inicio && fim) {
                    var range = moment().range(inicio, fim);
                    if (range.contains($scope.outroPeriodo.inicio)) {
                        $scope.showSimpleToast("Este outro período já foi adicionado para as datas selecionadas.");
                        return;
                    } else if (range.contains($scope.outroPeriodo.fim)) {
                        $scope.showSimpleToast("Este outro período já foi adicionado para as datas selecionadas.");
                        return;
                    }
                }

                //Checa se o inicio e o fim já foram adicionados anteriormente. 
                if (existeDuplicidade($scope.outroPeriodo.inicio, $scope.outroPeriodo.fim)) {
                    $scope.showSimpleToast("Este outro período já foi adicionado para as datas selecionadas.");
                    return;
                }

                $scope.outroPeriodo.totalDias = $scope.outroPeriodo.fim.diff($scope.outroPeriodo.inicio, 'days') + 1;

            } else {

                // Zerando variáveis do tipo 'PERIODO'
                $scope.outroPeriodo.inicio = null;
                $scope.outroPeriodo.fim = null;

                if (!$scope.outroPeriodo.ano && !$scope.outroPeriodo.mes && !$scope.outroPeriodo.dia) {
                    $scope.showSimpleToast("Favor preencher os campos de ano, mês ou dia da dedução.");
                    return;
                }

                var totalDias = 0;
                if ($scope.outroPeriodo.ano)
                    totalDias += $scope.outroPeriodo.ano * 365;
                if ($scope.outroPeriodo.mes)
                    totalDias += $scope.outroPeriodo.mes * 30;
                if ($scope.outroPeriodo.dia)
                    totalDias += $scope.outroPeriodo.dia;

                $scope.outroPeriodo.totalDias = totalDias;

            }

            //Insere a outro periodo na lista de deduções do objeto principal
            $scope.simuladorAposentadoria.outrosPeriodos.push($scope.outroPeriodo);
            $scope.outroPeriodo = angular.copy($scope.outroPeriodoInit);

        }

        //Valida a data de inicio do outro periodo
        $scope.validaOutroPeriodoInicio = function () {
            var inicio = $scope.outroPeriodo.inicio;
            var fim = $scope.outroPeriodo.fim;
            if (inicio && fim) {
                if (inicio.isAfter(fim, 'day')) {
                    $scope.showSimpleToast("A data de início não deve ser maior que a data de fim.");
                }
            }
        }

        //Valida a data de fim do outro periodo
        $scope.validaOutroPeriodoFim = function () {
            var inicio = $scope.outroPeriodo.inicio;
            var fim = $scope.outroPeriodo.fim;
            if (inicio && fim) {
                if (fim.isBefore(inicio, 'day')) {
                    $scope.showSimpleToast("A data final não deve ser menor que a data de inicio.");
                }
            }
        }

        //Remove o item da lista
        $scope.removeItemOutrosPeriodos = function (index) {
            $scope.simuladorAposentadoria.outrosPeriodos.splice(index, 1)
        }

        //Adiciona outro Período Público
        $scope.adicionarOutroPeriodoPublico = function () {

            if ($scope.outroPeriodoPublico.tipo === 'PERIODO') {

                // Zerando variáveis do tipo 'TEMPO'
                $scope.outroPeriodoPublico.ano = null;
                $scope.outroPeriodoPublico.mes = null;
                $scope.outroPeriodoPublico.dia = null;

                //Checa se os campos estão devidamente preenchidos
                if (!$scope.outroPeriodoPublico.inicio) {
                    $scope.showSimpleToast("Favor preencher o campo de inicio da dedução.");
                    return;
                }
                if (!$scope.outroPeriodoPublico.fim) {
                    $scope.showSimpleToast("Favor preencher o campo de fim da dedução.");
                    return;
                }

                if (!$scope.outroPeriodoPublico.atividade) {
                    $scope.showSimpleToast("Favor preencher o campo de atividade do outro Período Público.");
                    return;
                }

                //Checa se os valores são validaos
                if ($scope.outroPeriodoPublico.inicio.isAfter($scope.outroPeriodoPublico.fim, 'day')) {
                    $scope.showSimpleToast("A data de início não deve ser maior que a data de fim.");
                    return;
                }
                if ($scope.outroPeriodoPublico.fim.isBefore($scope.outroPeriodoPublico.inicio, 'day')) {
                    $scope.showSimpleToast("A data final não deve ser menor que a data de inicio.");
                    return;
                }

                //Checa se o início e o fim estão contidos no início e fim do tempo de contribuição cargo atual. 
                var inicio = $scope.simuladorAposentadoria.tempoContribuicaoCargoAtualInicio;
                var fim = $scope.simuladorAposentadoria.tempoContribuicaoCargoAtualFim;
                if (inicio && fim) {
                    var range = moment().range(inicio, fim);
                    if (range.contains($scope.outroPeriodoPublico.inicio)) {
                        $scope.showSimpleToast("Este outro período público já foi adicionado para as datas selecionadas.");
                        return;
                    } else if (range.contains($scope.outroPeriodoPublico.fim)) {
                        $scope.showSimpleToast("Este outro período público já foi adicionado para as datas selecionadas.");
                        return;
                    }
                }

                //Checa se o inicio e o fim já foram adicionados anteriormente. 
                if (existeDuplicidade($scope.outroPeriodoPublico.inicio, $scope.outroPeriodoPublico.fim)) {
                    $scope.showSimpleToast("Este outro período público já foi adicionado para as datas selecionadas.");
                    return;
                }

                $scope.outroPeriodoPublico.totalDias = $scope.outroPeriodoPublico.fim.diff($scope.outroPeriodoPublico.inicio, 'days') + 1;

            } else {

                // Zerando variáveis do tipo 'PERIODO'
                $scope.outroPeriodoPublico.inicio = null;
                $scope.outroPeriodoPublico.fim = null;

                if (!$scope.outroPeriodoPublico.ano && !$scope.outroPeriodoPublico.mes && !$scope.outroPeriodoPublico.dia) {
                    $scope.showSimpleToast("Favor preencher os campos de ano, mês ou dia da dedução.");
                    return;
                }

                if (!$scope.outroPeriodoPublico.atividade) {
                    $scope.showSimpleToast("Favor preencher o campo de atividade do outro Período Público.");
                    return;
                }

                var totalDias = 0;
                if ($scope.outroPeriodoPublico.ano)
                    totalDias += $scope.outroPeriodoPublico.ano * 365;
                if ($scope.outroPeriodoPublico.mes)
                    totalDias += $scope.outroPeriodoPublico.mes * 30;
                if ($scope.outroPeriodoPublico.dia)
                    totalDias += $scope.outroPeriodoPublico.dia;

                $scope.outroPeriodoPublico.totalDias = totalDias;

            }

            //Insere a outro periodo na lista de deduções do objeto principal
            $scope.simuladorAposentadoria.outrosPeriodosPublicos.push($scope.outroPeriodoPublico);
            $scope.outroPeriodoPublico = angular.copy($scope.outroPeriodoPublicoInit);

        }

        //Valida a data de inicio da outroPeriodoPublico
        $scope.validaOutroPeriodoPublicoInicio = function () {
            var inicio = $scope.outroPeriodoPublico.inicio;
            var fim = $scope.outroPeriodoPublico.fim;
            if (inicio && fim) {
                if (inicio.isAfter(fim, 'day')) {
                    $scope.showSimpleToast("A data de início não deve ser maior que a data de fim.");
                }
            }
        }

        //Valida a data de fim da outroPeriodoPublico
        $scope.validaOutroPeriodoPublicoFim = function () {
            var inicio = $scope.outroPeriodoPublico.inicio;
            var fim = $scope.outroPeriodoPublico.fim;
            if (inicio && fim) {
                if (fim.isBefore(inicio, 'day')) {
                    $scope.showSimpleToast("A data final não deve ser menor que a data de inicio.");
                }
            }
        }

        //Remove o item da lista
        $scope.removeItemOutrosPeriodosPublicos = function (index) {
            $scope.simuladorAposentadoria.outrosPeriodosPublicos.splice(index, 1)
        }


        //Adiciona outro Período Público
        $scope.adicionarAverbacao = function () {


            if ($scope.averbacao.tipo === 'PERIODO') {

                // Zerando variáveis do tipo 'TEMPO'
                $scope.averbacao.ano = null;
                $scope.averbacao.mes = null;
                $scope.averbacao.dia = null;

                //Checa se os campos estão devidamente preenchidos
                if (!$scope.averbacao.inicio) {
                    $scope.showSimpleToast("Favor preencher o campo de inicio da dedução.");
                    return;
                }
                if (!$scope.averbacao.fim) {
                    $scope.showSimpleToast("Favor preencher o campo de fim da dedução.");
                    return;
                }
                if (!$scope.averbacao.atividade) {
                    $scope.showSimpleToast("Favor preencher o campo de atividade da averbação.");
                    return;
                }

                //Checa se os valores são validaos
                if ($scope.averbacao.inicio.isAfter($scope.averbacao.fim, 'day')) {
                    $scope.showSimpleToast("A data de início não deve ser maior que a data de fim.");
                    return;
                }
                if ($scope.averbacao.fim.isBefore($scope.averbacao.inicio, 'day')) {
                    $scope.showSimpleToast("A data final não deve ser menor que a data de inicio.");
                    return;
                }

                //Checa se o início e o fim estão contidos no início e fim do tempo de contribuição cargo atual. 
                var inicio = $scope.simuladorAposentadoria.tempoContribuicaoCargoAtualInicio;
                var fim = $scope.simuladorAposentadoria.tempoContribuicaoCargoAtualFim;
                if (inicio && fim) {
                    var range = moment().range(inicio, fim);
                    if (range.contains($scope.averbacao.inicio)) {
                        $scope.showSimpleToast("Este outro período público já foi adicionado para as datas selecionadas.");
                        return;
                    } else if (range.contains($scope.averbacao.fim)) {
                        $scope.showSimpleToast("Este outro período público já foi adicionado para as datas selecionadas.");
                        return;
                    }
                }

                //Checa se o inicio e o fim já foram adicionados anteriormente. 
                if (existeDuplicidade($scope.averbacao.inicio, $scope.averbacao.fim)) {
                    $scope.showSimpleToast("Esta averbação já foi adicionada para as datas selecionadas.");
                    return;
                }

                $scope.averbacao.totalDias = $scope.averbacao.fim.diff($scope.averbacao.inicio, 'days') + 1;

            } else {

                // Zerando variáveis do tipo 'PERIODO'
                $scope.averbacao.inicio = null;
                $scope.averbacao.fim = null;

                if (!$scope.averbacao.ano && !$scope.averbacao.mes && !$scope.averbacao.dia) {
                    $scope.showSimpleToast("Favor preencher os campos de ano, mês ou dia da dedução.");
                    return;
                }

                if (!$scope.averbacao.atividade) {
                    $scope.showSimpleToast("Favor preencher o campo de atividade da averbação.");
                    return;
                }

                var totalDias = 0;
                if ($scope.averbacao.ano)
                    totalDias += $scope.averbacao.ano * 365;
                if ($scope.averbacao.mes)
                    totalDias += $scope.averbacao.mes * 30;
                if ($scope.averbacao.dia)
                    totalDias += $scope.averbacao.dia;

                $scope.averbacao.totalDias = totalDias;

            }

            //Insere a outro periodo na lista de deduções do objeto principal
            $scope.simuladorAposentadoria.averbacoes.push($scope.averbacao);
            $scope.averbacao = angular.copy($scope.averbacaoInit);

        }

        //Valida a data de inicio da averbacao
        $scope.validaAverbacaoInicio = function () {
            var inicio = $scope.averbacao.inicio;
            var fim = $scope.averbacao.fim;
            if (inicio && fim) {
                if (inicio.isAfter(fim, 'day')) {
                    $scope.showSimpleToast("A data de início não deve ser maior que a data de fim.");
                }
            }
        }

        //Valida a data de fim da averbacao
        $scope.validaAverbacaoFim = function () {
            var inicio = $scope.averbacao.inicio;
            var fim = $scope.averbacao.fim;
            if (inicio && fim) {
                if (fim.isBefore(inicio, 'day')) {
                    $scope.showSimpleToast("A data final não deve ser menor que a data de inicio.");
                }
            }
        }

        //Remove o item da lista
        $scope.removeItemAverbacoes = function (index) {
            $scope.simuladorAposentadoria.averbacoes.splice(index, 1)
        }

        // MÉTODO QUE EFETUA A SIMULAÇÃO 
        $scope.simular = function () {
            $rootScope.$broadcast('preloader:active');

            if ($scope.simuAposForm.$valid) {
                GenericoService.Create('/publico/simulador/aposentadoria', $scope.simuladorAposentadoria, function (response) {
                    $rootScope.$broadcast('preloader:hide');
                    if (response.status === 200 && response.data.success) {
                        window.scrollTo(0, 0);
                        $scope.showSimpleToast(response.data.message);

                        //Para que possa ser recuperado para refazer a simulação
                        SimuladorService.setSimuladorAposentadoria($scope.simuladorAposentadoria);

                        //Para que possa ser recuperado na tela de resultado
                        SimuladorService.setSimuladorAposentadoriaResultado(response.data.obj);

                        $location.path('/simulador/aposentadoria/resultado');
                    } else if (response.status === 400) {
                        $scope.showSimpleToast(response.data.message);

                    }
                });
            }
        }


        //Seta o dataIngressoServicoPublico no tempoContribuicaoCargoAtualInicio, por regra os valores devem ser os mesmos.
        $scope.setTempoContribuicaoInicio = function () {
            $scope.simuladorAposentadoria.tempoContribuicaoCargoAtualInicio = $scope.simuladorAposentadoria.dataIngressoServicoPublico;
            $scope.validaTempoContribuicaoInicio();
        };


        //MÉTODO GENÉRICO USADO PARA O FEEDBACK AO USUÁRIO
        $scope.showSimpleToast = function (textContent) {
            $mdToast.show(
                $mdToast.simple()
                    .textContent(textContent)
                    .position('top right')
                    .hideDelay(5000)
            );
        };


        //INÍCIO BÁSICO
        $scope.init = function () {
            var simuladorAposentadoria = SimuladorService.getSimuladorAposentadoria();
            if (simuladorAposentadoria) {
                $scope.simuladorAposentadoria = simuladorAposentadoria;
                simuladorAposentadoria.dataNascimento = moment($scope.simuladorAposentadoria.dataNascimento);
                simuladorAposentadoria.dataIngressoServicoPublico = moment($scope.simuladorAposentadoria.dataIngressoServicoPublico);
                simuladorAposentadoria.tempoContribuicaoCargoAtualInicio = moment($scope.simuladorAposentadoria.tempoContribuicaoCargoAtualInicio);
                simuladorAposentadoria.tempoContribuicaoCargoAtualFim = moment($scope.simuladorAposentadoria.tempoContribuicaoCargoAtualFim);
            } else {
                $scope.simuladorAposentadoria = angular.copy($scope.simuladorAposentadoriaInit);
            }
        }
        $scope.init();

        //MÉTODO GENÉRICO DE CHECAGEM DE EXISTENCIA DE DUPLICIDADE EM LISTA
        function existeDuplicidade(inicio, fim) {

            for (var index = 0; index < $scope.simuladorAposentadoria.outrosPeriodos.length; index++) {
                var element = $scope.simuladorAposentadoria.outrosPeriodos[index];
                var range = moment().range(element.inicio, element.fim);
                if (range.contains(inicio)) {
                    return true;
                } else if (range.contains(fim)) {
                    return true;
                }
            }

            for (var index = 0; index < $scope.simuladorAposentadoria.outrosPeriodosPublicos.length; index++) {
                var element = $scope.simuladorAposentadoria.outrosPeriodosPublicos[index];
                var range = moment().range(element.inicio, element.fim);
                if (range.contains(inicio)) {
                    return true;
                } else if (range.contains(fim)) {
                    return true;
                }
            }

            for (var index = 0; index < $scope.simuladorAposentadoria.averbacoes.length; index++) {
                var element = $scope.simuladorAposentadoria.averbacoes[index];
                var range = moment().range(element.inicio, element.fim);
                if (range.contains(inicio)) {
                    return true;
                } else if (range.contains(fim)) {
                    return true;
                }
            }

            return false;
        }

        //MÉTIDO USADO PARA LIMPAR COMPLETAMENTE O FORMULÁRIO 
        $scope.limpar = function () {
            $scope.simuladorAposentadoria = angular.copy($scope.simuladorAposentadoriaInit);
        }

    }

})(); 