(function () {
    'use strict';

    angular.module('app.page')
        .controller('feriasProgramacaoFormCtrl', ['$scope', '$mdToast', '$location', '$state', '$rootScope', 'GenericoService', 'EnumService', feriasProgramacaoFormCtrl]);

    function feriasProgramacaoFormCtrl($scope, $mdToast, $location, $state, $rootScope, GenericoService, EnumService) {

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
        moment.locale('pt-BR');
        $scope.list = {};
        $scope.feriasProgramacao = {}
        $scope.funcionario = {};
        $scope.detalhes = false;
        $scope.validateForm = true;

        $scope.feriasProgramacao.quantFaltas = 0;

        $scope.validarFaltas = function (quantFaltas) {
            $scope.feriasProgramacao.quantDias = 30;
            if (quantFaltas >= 6 && quantFaltas <= 14) {
                $scope.feriasProgramacao.quantDias = 24;
            } else if (quantFaltas >= 15 && quantFaltas <= 23) {
                $scope.feriasProgramacao.quantDias = 18;
            } else if (quantFaltas >= 24 && quantFaltas <= 32) {
                $scope.feriasProgramacao.quantDias = 12;
            }
        };

        $scope.validarFaltas($scope.feriasProgramacao.quantFaltas);

        $scope.calcularDiasAGozarUm = function () {
            var dataInicio = moment($scope.feriasProgramacao.dataInicialUm);
            var dataFim = moment($scope.feriasProgramacao.dataFinalUm);

            $scope.feriasProgramacao.diasAGozarUm = dataFim.diff(dataInicio, 'days') + 1;

            $scope.validarFormulario();
        };

        $scope.calcularDiasAGozarDois = function () {

            var dataInicio = moment($scope.feriasProgramacao.dataInicialDois);
            var dataFim = moment($scope.feriasProgramacao.dataFinalDois);

            $scope.feriasProgramacao.diasAGozarDois = dataFim.diff(dataInicio, 'days') + 1;

            $scope.validarFormulario();
        };

        $scope.calcularDiasAGozarTres = function () {

            var dataInicio = moment($scope.feriasProgramacao.dataInicialTres);
            var dataFim = moment($scope.feriasProgramacao.dataFinalTres);

            $scope.feriasProgramacao.diasAGozarTres = dataFim.diff(dataInicio, 'days') + 1;

            $scope.validarFormulario();
        };


        $scope.validarFormulario = function () {
            var valorTotal = 0;
            if ($scope.feriasProgramacao.diasAGozarUm) {
                valorTotal = $scope.feriasProgramacao.diasAGozarUm;
                if ($scope.feriasProgramacao.diasAGozarDois) {
                    valorTotal += $scope.feriasProgramacao.diasAGozarDois;
                    if ($scope.feriasProgramacao.diasAGozarTres) {
                        valorTotal += $scope.feriasProgramacao.diasAGozarTres;
                    }
                }
                if (valorTotal > $scope.feriasProgramacao.quantDias || valorTotal < $scope.feriasProgramacao.quantDias) {
                    $scope.showSimpleToast("Quantidades de dias não batem com o disponível");
                } else {
                    $scope.validateForm = false;
                }
            }
        }

        if ($state.params && $state.params.funcionarioId) {
            $rootScope.$broadcast('preloader:active');
            GenericoService.GetById('/funcionarioSimplificado/' + $state.params.funcionarioId, function (response) {
                $rootScope.$broadcast('preloader:hide');
                if (response.status === 200) {
                    $scope.funcionario = response.data;

                    if ($scope.funcionario.dataPublicacaoDiarioOficial)
                        $scope.funcionario.dataPublicacaoDiarioOficial = moment($scope.funcionario.dataPublicacaoDiarioOficial);
                    if ($scope.funcionario.dataNomeacao)
                        $scope.funcionario.dataNomeacao = moment($scope.funcionario.dataNomeacao);

                    $scope.feriasProgramacao.funcionarioId = $scope.funcionario.id;

                    GenericoService.GetById('/feriasProgramacaoExercicioInicialMax/' + $scope.funcionario.id, function (responseMax) {
                        $rootScope.$broadcast('preloader:hide');
                        if (responseMax.status === 200) {
                            var exercicioInicialMaximo = responseMax.data;
                            if (exercicioInicialMaximo != null && exercicioInicialMaximo != "") {
                                $scope.feriasProgramacao.exercicioInicio = moment(exercicioInicialMaximo).add(1, 'years');
                                $scope.feriasProgramacao.exercicioFim = moment($scope.feriasProgramacao.exercicioInicio).add(1, 'years');
                                $scope.feriasProgramacao.dataLimite = moment($scope.feriasProgramacao.exercicioFim).add(11, 'months');
                            } else {
                                $scope.feriasProgramacao.exercicioInicio = moment($scope.funcionario.dataAdmissao).add(1, 'years');
                                $scope.feriasProgramacao.exercicioFim = moment($scope.feriasProgramacao.exercicioInicio).add(1, 'years');
                                $scope.feriasProgramacao.dataLimite = moment($scope.feriasProgramacao.exercicioFim).add(11, 'months');
                            }


                        }
                    });

                } else {
                    $scope.showSimpleToast("Funcionário não encontrado na base");
                }
            });
        }

        if ($state.params && $state.params.feriasProgramacaoId && ($state.params.detalhes || $state.params.editar)) {
            $rootScope.$broadcast('preloader:active');
            GenericoService.GetById('/feriasProgramacao/' + $state.params.feriasProgramacaoId, function (response) {
                $rootScope.$broadcast('preloader:hide');
                if (response.status === 200) {

                    $scope.feriasProgramacao = response.data;
                    $scope.feriasProgramacao.exercicioInicio = moment(response.data.exercicioInicio);
                    $scope.feriasProgramacao.exercicioFim = moment(response.data.exercicioFim);
                    $scope.feriasProgramacao.dataLimite = moment(response.data.dataLimite);


                    if (response.data.dataInicialUm) {
                        $scope.feriasProgramacao.dataInicialUm = moment(response.data.dataInicialUm);
                        $scope.feriasProgramacao.dataFinalUm = moment(response.data.dataFinalUm);
                        $scope.feriasProgramacao.mesCompetenciaParticaoUm = moment(response.data.mesCompetenciaParticaoUm, 'YYYY/MM/DD');
                    }

                    if (response.data.dataInicialDois) {
                        $scope.feriasProgramacao.dataInicialDois = moment(response.data.dataInicialDois);
                        $scope.feriasProgramacao.dataFinalDois = moment(response.data.dataFinalDois);
                        $scope.feriasProgramacao.mesCompetenciaParticaoDois = moment(response.data.mesCompetenciaParticaoDois, 'YYYY/MM/DD');
                    }

                    if (response.data.dataInicialTres) {
                        $scope.feriasProgramacao.dataInicialTres = moment(response.data.dataInicialTres);
                        $scope.feriasProgramacao.dataFinalTres = moment(response.data.dataFinalTres);
                        $scope.feriasProgramacao.mesCompetenciaParticaoTres = moment(response.data.mesCompetenciaParticaoTres, 'YYYY/MM/DD');
                    }


                    if ($state.params.detalhes) {
                        $scope.detalhes = true;
                    }

                    GenericoService.GetById('/funcionario/' + $scope.feriasProgramacao.funcionarioId, function (response) {
                        $rootScope.$broadcast('preloader:hide');
                        if (response.status === 200) {
                            $scope.funcionario = response.data;
                            $scope.funcionario.dataPublicacaoDiarioOficial = moment($scope.funcionario.dataPublicacaoDiarioOficial);
                            $scope.funcionario.dataNomeacao = moment($scope.funcionario.dataNomeacao);

                        }
                    });
                } else {
                    $scope.showSimpleToast("Férias Programação não encontrado na base");
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

        EnumService.Get("FeriasProgramacaoTipoFeriasEnum").then(function (dados) {
            $scope.list.tipoFerias = dados;
        });

        EnumService.Get("FeriasProgramacaoTipoProcessamentoEnum").then(function (dados) {
            $scope.list.TipoProcessamento = dados;
        });

        EnumService.Get("FeriasProgramacaoSituacaoEnum").then(function (dados) {
            $scope.list.situacao = dados;
        });

        $scope.updateDates = function () {
            $scope.funcionarioExercicio.dataInicio = "";
            $scope.funcionarioExercicio.dataFim = "";

            var exercicio = $scope.funcionarioExercicio.exercicio;
            var data = moment(exercicio + "/01/01");

            $scope.funcionarioExercicio.dataInicio = moment(data).subtract(5, 'years');
            $scope.funcionarioExercicio.dataFim = moment(data);

        };

        $scope.goBack = function () {
            $location.path('/feriasProgramacao/gestao');
        }

        $scope.save = function () {
            $rootScope.$broadcast('preloader:active');
            if ($state.params.editar) {
                GenericoService.Update('/feriasProgramacao', $scope.feriasProgramacao, function (response) {
                    $rootScope.$broadcast('preloader:hide');
                    if (response.status === 201 && response.data.success) {
                        $scope.showSimpleToast(response.data.message);
                        $location.path('/feriasProgramacao/gestao');
                    } else if (response.status === 400) {
                        $scope.showSimpleToast(response.data.message);
                        $location.path('/feriasProgramacao/formulario/' + $state.params.feriasProgramacaoId + '/' + $state.params.editar);
                    }
                });
            } else {

                $scope.feriasProgramacao.mesCompetenciaParticaoUm = moment($scope.feriasProgramacao.mesCompetenciaParticaoUm).format('YYYY/MM').toString();

                if ($scope.feriasProgramacao.mesCompetenciaParticaoDois) {
                    $scope.feriasProgramacao.mesCompetenciaParticaoDois = moment($scope.feriasProgramacao.mesCompetenciaParticaoDois).format('YYYY/MM').toString();
                }

                if ($scope.feriasProgramacao.mesCompetenciaParticaoTres) {
                    $scope.feriasProgramacao.mesCompetenciaParticaoTres = moment($scope.feriasProgramacao.mesCompetenciaParticaoTres).format('YYYY/MM').toString();
                }


                GenericoService.Create('/feriasProgramacao', $scope.feriasProgramacao, function (response) {
                    $rootScope.$broadcast('preloader:hide');
                    if (response.status === 201 && response.data.success) {
                        $scope.showSimpleToast(response.data.message);
                        $location.path('/feriasProgramacao/gestao');
                    } else if (response.status === 400) {
                        $scope.showSimpleToast(response.data.message);
                        $location.path('/feriasProgramacao/formulario/' + $state.params.funcionarioId);
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
