(function () {
    'use strict';

    angular.module('app.page')
        .controller('simuladorNivelSalarialFormCtrl', ['$scope', '$mdToast', '$location', '$state', '$rootScope', 'GenericoService', '$filter', simuladorNivelSalarialFormCtrl]);

    function simuladorNivelSalarialFormCtrl($scope, $mdToast, $location, $state, $rootScope, GenericoService, $filter) {

        GenericoService.VerificaPermissao('/usuario/verificaPermissao', 'ROLE_ADMIN').then(
            function (response) {
                if (response.status === 200) {
                }
            }, function errorCallback(response) {
                if (response.status === 400) {
                    $location.path('page/403');
                }
            });

        $scope.niveisSalariaisSelecionados = [];

        $scope.detalhes = false;
        if ($state.params && $state.params.detalhes) {
            $scope.detalhes = true;
        }

        //INIT BASICO DO OBJETO PRINCIPAL

        // esse dia será configuradao no futuro
        $scope.diaCompetencia = 20;

        var diaAtual = moment().format('D');
        var ano = moment().format('YYYY');
        var mes = moment().format('MM');
        var dia = '01'
        var stringData = ano + '-' + mes + '-' + dia;
        if (diaAtual >= $scope.diaCompetencia) {
            var mes = moment().add(1, 'M').format('MM');
            stringData = ano + '-' + mes + '-' + dia;
        }

        $scope.dataMinCompetencia = moment(stringData).toDate();
        $scope.dataMaxCompetencia = moment().add(12, 'M').toDate();

        $scope.simuladorNivelSalarial = {
            dataCompetencia: moment(stringData),
            id: null,
            simuladorNivelSalarialValoresList: []
        }



        $scope.save = function () {

            $rootScope.$broadcast('preloader:active');
            if ($scope.simuladorNivelSalarial.id) {
                GenericoService.Update('/simulardorNivelSalarial', $scope.simuladorNivelSalarial, function (response) {
                    $rootScope.$broadcast('preloader:hide');
                    if (response.status === 201 && response.data.success) {
                        $scope.showSimpleToast(response.data.message);
                        $location.path('/simuladorNivelSalarial/gestao');
                    } else if (response.status === 400) {
                        $scope.showSimpleToast(response.data.message);
                        $location.path('simuladorNivelSalarial/formulario/' + $state.params.id)
                    }
                });
            } else {

                GenericoService.Create('/simulardorNivelSalarial', $scope.simuladorNivelSalarial, function (response) {
                    $rootScope.$broadcast('preloader:hide');
                    if (response.status === 201 && response.data.success) {
                        $scope.showSimpleToast(response.data.message);
                        $location.path('/simuladorNivelSalarial/gestao');
                    } else if (response.status === 400) {
                        $scope.showSimpleToast(response.data.message);
                        $location.path('/simuladorNivelSalarial/formulario');
                    }
                });
            }
        }

        $scope.carregarNiveis = function () {

            if (!$scope.simuladorNivelSalarial.id) {
                $scope.simuladorNivelSalarial.id = 0;
            }

            var mes = moment($scope.simuladorNivelSalarial.dataCompetencia).format('MM');
            if (moment($scope.simuladorNivelSalarial.dataCompetencia).format('D') > $scope.diaCompetencia) {
                mes = moment($scope.simuladorNivelSalarial.dataCompetencia).add(1, 'M').format('MM');
            }

            var config = {
                params: {
                    simuladorNivelSalarialId: $scope.simuladorNivelSalarial.id,
                    mes: mes,
                    ano: moment($scope.simuladorNivelSalarial.dataCompetencia).format('YYYY')
                }
            };

            $scope.promise = GenericoService.GetAll('/simulardorNivelSalarials/listaNiveis', config).then(
                function (response) {
                    if (response.status === 200) {

                        $scope.niveisSalariais = response.data;

                        $rootScope.$broadcast('preloader:hide');
                    } else {
                        $scope.showSimpleToast("Não foi possível carregar os Nívels Salariais.")
                    }
                });
        }

        if ($state.params && $state.params.id) {
            $rootScope.$broadcast('preloader:active');
            GenericoService.GetById('/simuladorNivelSalarial/' + $state.params.id, function (response) {
                $rootScope.$broadcast('preloader:hide');
                if (response.status === 200) {

                    $scope.simuladorNivelSalarial = response.data;
                    $scope.simuladorNivelSalarial.dataCompetencia = moment($scope.simuladorNivelSalarial.dataCompetencia);
                    $scope.simuladorNivelSalarial.motivoAjuste = $scope.simuladorNivelSalarial.motivoAjusteString;
                    $scope.simuladorNivelSalarial.tipoAjuste = $scope.simuladorNivelSalarial.tipoAjusteString;
                    if ($scope.simuladorNivelSalarial.dataAjuste)
                        $scope.simuladorNivelSalarial.dataAjuste = moment($scope.simuladorNivelSalarial.dataAjuste);

                    $scope.carregarNiveis();

                    if ($scope.simuladorNivelSalarial.acordo && $scope.simuladorNivelSalarial.acordo.id) {
                        $scope.simuladorNivelSalarial.acordo.dataInicial = moment($scope.simuladorNivelSalarial.acordo.dataInicial);
                        $scope.simuladorNivelSalarial.acordo.dataFinal = moment($scope.simuladorNivelSalarial.acordo.dataFinal);
                        if ($scope.simuladorNivelSalarial.acordo.dataAcordo)
                            $scope.simuladorNivelSalarial.acordo.dataAcordo = moment($scope.simuladorNivelSalarial.acordo.dataAcordo);
                    }

                } else {
                    $scope.showSimpleToast("Simulador Nivel Salarial não encontrad na base");
                }
            });
        } else {
            $scope.carregarNiveis();
        }

        $scope.selecionarConcluirNiveis = function () {
            if ($scope.showSelecionarNiveis) {
                concluirNiveisSalariais();
            } else {
                selecionarNiveisSalariais();
            }
            $scope.showSelecionarNiveis = !$scope.showSelecionarNiveis;
            $scope.calcularValorAjustado();
        }

        function selecionarNiveisSalariais() {
            $scope.niveisSalariaisSelecionados = [];

            for (var index = 0; index < $scope.simuladorNivelSalarial.simuladorNivelSalarialValoresList.length; index++) {
                var element = $scope.simuladorNivelSalarial.simuladorNivelSalarialValoresList[index];
                var nivelSalarialSelecionado = adicionarNivelSalarialSelecionadoById(element.nivelSalarialId);
                if (nivelSalarialSelecionado) {
                    $scope.niveisSalariaisSelecionados.push(nivelSalarialSelecionado);
                }
            }

        }

        function adicionarNivelSalarialSelecionadoById(nivelSalarialId) {
            for (var index = 0; index < $scope.niveisSalariais.length; index++) {
                var element = $scope.niveisSalariais[index];
                if (element.id === nivelSalarialId) {
                    return element;
                }
            }
            return null;
        }

        function concluirNiveisSalariais() {
            if ($scope.niveisSalariaisSelecionados.length > 0) {
                for (var index = 0; index < $scope.niveisSalariaisSelecionados.length; index++) {
                    var nivelSalarial = $scope.niveisSalariaisSelecionados[index];
                    adicionaNivelSalarialNoSimulador(nivelSalarial);
                }
            }
        }

        function adicionaNivelSalarialNoSimulador(nivelSalarial) {

            var existe = false;
            for (var index = 0; index < $scope.simuladorNivelSalarial.simuladorNivelSalarialValoresList.length; index++) {
                var element = $scope.simuladorNivelSalarial.simuladorNivelSalarialValoresList[index];
                if (element.nivelSalarialId === nivelSalarial.id) {
                    existe = true;
                    break;
                }
            }
            if (!existe) {
                var simuladorNivelSalarialValor = {
                    'id': null,
                    'descricao': nivelSalarial.descricao,
                    'valor': nivelSalarial.valor,
                    'valorAjustado': null,
                    'valorRetroativo': null,
                    'nivelSalarialId': nivelSalarial.id
                };
                $scope.simuladorNivelSalarial.simuladorNivelSalarialValoresList.push(simuladorNivelSalarialValor);
            }

        }

        $scope.calcularValorAjustado = function () {

            if ($scope.simuladorNivelSalarial.dataAjuste && $scope.simuladorNivelSalarial.dataCompetencia) {
                var dataAjuste = moment($scope.simuladorNivelSalarial.dataAjuste);
                var dataCompetencia = moment($scope.simuladorNivelSalarial.dataCompetencia);

                //calculando quantos meses retrotivo do data de ajuste até data de compentencia
                var meses = dataCompetencia.diff(dataAjuste, 'months');

            }

            for (var index = 0; index < $scope.simuladorNivelSalarial.simuladorNivelSalarialValoresList.length; index++) {
                var element = $scope.simuladorNivelSalarial.simuladorNivelSalarialValoresList[index];

                if ($scope.simuladorNivelSalarial.tipoAjuste == 'Percentual') {
                    element.valorAjustado = element.valor * (1 + ($scope.simuladorNivelSalarial.valorAjuste / 100));

                } else {
                    element.valorAjustado = element.valor + $scope.simuladorNivelSalarial.valorAjuste;
                }

                if ($scope.simuladorNivelSalarial.dataAjuste && $scope.simuladorNivelSalarial.dataCompetencia) {
                    element.valorRetroativo = (element.valorAjustado - element.valor) * meses;
                    element.valorRetroativo = element.valorRetroativo.toFixed(2);
                }

                element.valorAjustado = element.valorAjustado.toFixed(2);

            }

        }

        $scope.showHideAcordo = function () {
            if ($scope.showAcordo) {
                $scope.simuladorNivelSalarial.acordo = null
            }
            $scope.showAcordo = !$scope.showAcordo;
        };

        $scope.excluirNivel = function (index) {
            $scope.simuladorNivelSalarial.simuladorNivelSalarialValoresList.splice(index, 1);
        }

        //METODO GENERICO FEEDBACK
        $scope.showSimpleToast = function (textContent) {
            $mdToast.show(
                $mdToast.simple()
                    .textContent(textContent)
                    .position('top right')
                    .hideDelay(3000)
            );
        };

        // METODO GENÉRICO GO BACK
        $scope.goBack = function () {
            $location.path('/simuladorNivelSalarial/gestao');
        }
    }

})(); 