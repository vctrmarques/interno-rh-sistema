(function () {
    'use strict';

    angular.module('app.page')
        .controller('folhaPagamentoCtrl', ['$state', '$interval', '$q', '$location', 'RestService', '$scope', '$mdToast', '$mdDialog', '$rootScope', 'GenericoService', 'EnumService', folhaPagamentoCtrl]);

    function folhaPagamentoCtrl($state, $interval, $q, $location, RestService, $scope, $mdToast, $mdDialog, $rootScope, GenericoService, EnumService) {

        // Checa as regras de acesso da tela
        RestService.Get('/usuario/verificaPermissao/por/menu', { params: { menu: 'Folha de Pgt' } })
            .then(function (response) {
                if (response.status === 200 && response.data) {

                    $scope.usuarioAdm = response.data.usuarioAdm;
                    $scope.podeGerenciar = false;

                    if (response.data.papeis)
                        $scope.podeGerenciar = response.data.papeis.includes('ROLE_FOLHA_DE_PGT_GESTAO') ? true : false;

                    if ($scope.usuarioAdm || $scope.podeGerenciar) {
                        if ($state.params.id == undefined)
                            $scope.getCompetencia();
                    } else {
                        $location.path('page/403');
                    }
                }
            }, function errorCallback(response) {
                if (response.status === 400) {
                    $scope.showSimpleToast(response.data.message);
                }
            });

        // Demais variaveis 
        $scope.filiais = [];
        $scope.tipoProcessamentos = [];
        $scope.folhaCompetencia = {};
        $scope.folhaPagamento = {};

        // Busca a competencia corrente.
        $scope.getCompetencia = function () {
            $rootScope.$broadcast('preloader:active');
            var deferred = $q.defer();
            GenericoService.GetAll('/competencia')
                .then(function (response) {
                    $rootScope.$broadcast('preloader:hide');
                    if (response.status === 200) {
                        $scope.folhaCompetencia = response.data;
                        $scope.anoCompetencias();
                        $scope.getFolhasPorCompetencia($scope.folhaCompetencia.id);
                    }
                });
            return deferred.promise;
        };

        // Busca os anos das compet??ncias dispon??veis 
        $scope.anoCompetencias = function () {
            $rootScope.$broadcast('preloader:active');
            GenericoService.GetAll('/competencia/anos').then(function (response) {
                if (response.status === 200) {
                    $scope.anosCompetencia = response.data;
                }
                $rootScope.$broadcast('preloader:hide')
            });
        }

        // Busca as folhas de pagamento da compet??ncia atual
        $scope.getFolhasPorCompetencia = function (id) {
            $rootScope.$broadcast('preloader:active');
            var config = {
                params: {
                    competenciaId: id
                }
            };
            var deferred = $q.defer();
            GenericoService.GetAll('/folhaPagamento/porCompetencia', config)
                .then(function (response) {
                    $rootScope.$broadcast('preloader:hide');
                    if (response.status === 200) {
                        if (!$scope.isHistorico)
                            $scope.folhas = response.data;
                        else
                            $scope.historicofolhas = response.data;
                    }
                });
            return deferred.promise;
        };

        // Get do enum de status da folha de pagamento
        EnumService.Get("StatusFolhaEnum").then(function (dados) {
            $scope.statusFolhaEnumList = dados;
        });

        // Rotina que busca a empresa matriz e suas filiais
        GenericoService.GetAllDropdown('folhaPagamento/listaEmpresaMatrizComFiliais', function (response) {
            if (response.status === 200) {
                $scope.matriz = response.data[0];
                $scope.filiais = response.data[0].filiais;
                $rootScope.$broadcast('preloader:hide');
            } else if (response.status === 500) {
                $scope.showSimpleToast("Falha no carregamento de dados, favor contate o administrador do sistema");
            }
        });

        // Rotina que busca os tipos de processamento dispon??veis
        GenericoService.GetAllDropdown('folhaPagamento/listaTiposProcessamentos', function (response) {
            if (response.status === 200) {
                $scope.tipoProcessamentos = response.data;
                $rootScope.$broadcast('preloader:hide');
            } else if (response.status === 500) {
                $scope.showSimpleToast("Falha no carregamento de dados, favor contate o administrador do sistema");
            }
        });

        // Rotina respons??vel por exibir o dialog de confirma????o da exclus??o da folha de pagamento.
        $scope.showDeleteFolha = function (ev, idToDelete) {
            $scope.idToDelete = idToDelete;
            var confirm = $mdDialog.confirm()
                .title('Deseja confirmar a exclus??o desta folha?')
                .textContent('Esta a????o n??o poder?? ser desfeita.')
                .targetEvent(ev)
                .ok('Sim')
                .cancel('N??o');
            $mdDialog.show(confirm).then(function () {
                $scope.deleteFolha();
            }, function () { });
        };

        // Rotina que efetua a exclus??o da folha de pagamento
        $scope.deleteFolha = function () {
            $rootScope.$broadcast('preloader:active');
            GenericoService.Delete('/folhaPagamento/' + $scope.idToDelete, function (response) {
                $rootScope.$broadcast('preloader:hide');
                if (response.status === 200) {
                    $scope.showSimpleToast(response.data.message);
                    // Recarregar os dados da tela.
                    // $scope.getCompetencia();
                    $state.reload();
                }
            });
        };

        // Rotina respons??vel por salvar ou atualizar a folha de pagamento.
        $scope.save = function () {
            if ($scope.folhaPagamentoForm.$valid) {
                $rootScope.$broadcast('preloader:active');

                $scope.folhaPagamento.folhaCompetenciaId = $scope.folhaCompetencia.id;

                if ($scope.folhaPagamento.id) {
                    GenericoService.Update('/folhaPagamento', $scope.folhaPagamento, function (response) {
                        $rootScope.$broadcast('preloader:hide');
                        if (response.status === 201 && response.data.success) {
                            $scope.showSimpleToast(response.data.message);
                            $state.reload();
                            // $location.path('/folhaPagamento/gestao');
                        } else if (response.status === 400) {
                            $scope.showSimpleToast(response.data.message);
                            $state.reload();
                            // $location.path('folhaPagamento/formulario/' + $state.params.id);
                        }
                    });
                } else {
                    GenericoService.Create('/folhaPagamento', $scope.folhaPagamento, function (response) {
                        $rootScope.$broadcast('preloader:hide');
                        if (response.status === 201 && response.data.success) {
                            $scope.showSimpleToast(response.data.message);
                            $state.reload();
                        } else if (response.status === 400) {
                            $scope.showSimpleToast(response.data.message);
                            $state.reload();
                            // $location.path('/folhaPagamento/formulario');
                        }
                    });
                }
            }
        }

        // Rotina de feedback na tela.
        $scope.showSimpleToast = function (textContent) {
            $mdToast.show(
                $mdToast.simple()
                    .textContent(textContent)
                    .position('top right')
                    .hideDelay(8000)
            );
        };

        // Rotina respons??vel por checar o status de processamento das folhas
        $scope.loopStatus = $interval(checarStatusFolhas, 5000);
        function checarStatusFolhas() {
            if ($scope.folhas && $scope.folhas.length > 0) {
                angular.forEach($scope.folhas, function (folha) {
                    if (!folha.progress || folha.situacao === 'Pendente') {
                        GenericoService.GetAll('/folhaPagamento/concluidos/' + folha.id)
                            .then(function (response) {
                                var progress = (100 * response.data.count) / folha.processamentos;
                                folha.progress = progress.toFixed(0) + "%";
                                folha.situacao = response.data.situacao;
                            });
                    }
                });
            }
        }
        $scope.$on("$destroy", function () {
            if ($scope.loopStatus) {
                $interval.cancel($scope.loopStatus);
            }
        });

        // Rotina respons??vel pela edi????o da folha de pagamento
        $scope.editarFolhaPagamento = function (folhaPagamentoId) {
            $rootScope.$broadcast('preloader:active');
            GenericoService.GetById('/folhaPagamento/' + folhaPagamentoId, function (response) {
                $rootScope.$broadcast('preloader:hide');
                if (response.status === 200) {
                    $scope.folhaPagamento = response.data;
                    $scope.folhaPagamento.periodoProcessamentoInicio = moment(response.data.periodoProcessamentoInicio);
                    $scope.folhaPagamento.periodoProcessamentoFim = moment(response.data.periodoProcessamentoFim);
                    $scope.folhaPagamento.filialId = response.data.filial.id;
                    $scope.folhaPagamento.tipoProcessamentoId = response.data.tipoProcessamento.id;
                    $scope.folhaPagamento.folhaCompetenciaId = response.data.folhaCompetencia.id;
                } else {
                    $scope.showSimpleToast("Folha pagamento n??o encontrada na base");
                }
            });
        }

        // Rotina respons??vel pelo cancelamento da edi????o da folha de pagamento
        $scope.cancelarEdicao = function () {
            $scope.folhaPagamento = {};
        }







        // REFATORAR / REANALIZAR 
        $scope.fecharCompetencia = function () {
            GenericoService.Update('/competencia/fechar/' + $scope.folhaCompetencia.id, null, function (response) {

                $rootScope.$broadcast('preloader:hide');
                if (response.status === 201 && response.data.success) {
                    $scope.showSimpleToast(response.data.message);
                    $scope.getCompetencia();
                } else if (response.status === 400) {
                    $scope.showSimpleToast(response.data.message);
                }

            });
        };

        $scope.showFecharCompetenciaHoje = function () {

            var confirm = $mdDialog.confirm()
                .title('Deseja confirmar o fechamento da Compet??ncia?')
                .textContent('Esta a????o n??o poder?? ser desfeita.')
                .ok('Sim')
                .cancel('N??o');
            $mdDialog.show(confirm).then(function () {
                $scope.fecharCompetencia();
            }, function () { });
        };

        $scope.showDeleteProgramacaoFechamento = function (ev) {
            var confirm = $mdDialog.confirm()
                .title('Deseja confirmar a remo????o da programa????o de fechamento desta compet??ncia?')
                .textContent('Esta a????o n??o poder?? ser desfeita.')
                .targetEvent(ev)
                .ok('Sim')
                .cancel('N??o');
            $mdDialog.show(confirm).then(function () {
                $scope.deleteProgramcaoFechamento();
            }, function () { });
        }

        $scope.deleteProgramcaoFechamento = function () {
            $rootScope.$broadcast('preloader:active');
            GenericoService.Update('/competencia/cancelar/programar/fechar/' + $scope.folhaCompetencia.id, null, function (response) {
                $rootScope.$broadcast('preloader:hide');
                $scope.showSimpleToast(response.data.message);
                $scope.getCompetencia();
            });
        }

        $scope.programarFechamentoCompetencia = function (ev) {
            $mdDialog.show({
                scope: $scope,
                preserveScope: true,
                controller: ['$scope', '$q', function ($scope, $q) {

                    $scope.hoje = new Date();

                    $scope.hide = function () {
                        $mdDialog.hide();
                    };
                    $scope.cancel = function () {
                        $mdDialog.cancel();
                    };

                    $scope.agendamentoFechamento = function (programacaoFechamento) {
                        $scope.programarFechamento(programacaoFechamento);
                        $scope.cancel();
                    }

                    $scope.checarDataMenorQueHoje = function (programacaoFechamento) {
                        var now = moment(new Date()).hour(23).minute(59).second(59).milliseconds(0);
                        var varProgramacaoFechamento = moment(programacaoFechamento).hour(23).minute(59).second(59).milliseconds(0);
                        if (varProgramacaoFechamento < now) {
                            $scope.programacaoFechamento = now;
                        }
                    }

                    $scope.programarFechamento = function (programacaoFechamento) {
                        $scope.folhaCompetencia.programacaoFechamento = programacaoFechamento;
                        GenericoService.Update('/competencia/programar/fechar/' + $scope.folhaCompetencia.id, $scope.folhaCompetencia.programacaoFechamento, function (response) {
                            $rootScope.$broadcast('preloader:hide');
                            if (response.status === 201 && response.data.success) {
                                $scope.showSimpleToast(response.data.message);
                            } else if (response.status === 400) {
                                $scope.showSimpleToast(response.data.message);
                            }

                        });
                    };

                }],
                templateUrl: 'app/page/folhaPagamento/gestao/folhaPagamento.programarFechComp.tmpl.html',
                parent: angular.element(document.body),
                clickOutsideToClose: true
            });
        };

        // HIST??RICO DE COMPET??NCIAS
        // REFATORAR
        $scope.competenciasByAno = function (ano) {
            $rootScope.$broadcast('preloader:active');
            GenericoService.GetAll('/competencia/porAno/' + ano).then(function (response) {
                if (response.status === 200) {
                    $scope.competencias = response.data;
                    $scope.historicofolhas = [];
                }
                $rootScope.$broadcast('preloader:hide')
            });
        }

        $scope.folhaCompetenciaHistoricoSelect = function (id) {
            $scope.getFolhasPorCompetencia(id);
            $scope.isHistorico = true;
        }


    }
})();