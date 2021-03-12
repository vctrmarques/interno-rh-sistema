(function () {
    'use strict';

    angular.module('app.page')
        .controller('relatorioFinanceiroFolhaPagamentoCtrl', ['$state', '$interval', '$q', '$location', 'configValue', '$scope', '$mdToast', '$mdMedia', '$mdDialog', '$rootScope', 'GenericoService', 'EnumService', relatorioFinanceiroFolhaPagamentoCtrl]);

    function relatorioFinanceiroFolhaPagamentoCtrl($state, $interval, $q, $location, configValue, $scope, $mdToast, $mdMedia, $mdDialog, $rootScope, GenericoService, EnumService) {

        // Checagem de permissões
        GenericoService.VerificaPermissao('/usuario/verificaPermissao', 'ROLE_ADMIN').then(
            function (response) {
                if (response.status === 200) {
                    $scope.getCompetencia();
                    $scope.loadlistNaoSalvo();
                }
            },
            function errorCallback(response) {
                if (response.status === 400) {
                    $location.path('page/403');
                }
            });


        //GERAR RELATÓRIO


        $scope.getCompetencia = function () {
            $rootScope.$broadcast('preloader:active');
            var deferred = $q.defer();
            GenericoService.GetAll('/competencia')
                .then(function (response) {
                    $rootScope.$broadcast('preloader:hide');
                    if (response.status === 200) {
                        $scope.folhaCompetencia = response.data;
                        // $scope.getFolhasPorCompetencia($scope.folhaCompetencia.id);
                    }
                });
            return deferred.promise;
        };

        // Get do enum de status da folha de pagamento
        EnumService.Get("StatusRelatorioFinanceiroEnum").then(function (dados) {
            $scope.statusRelatorioFinanceiroEnumList = dados;
        });


        $scope.empresas = [];
        $scope.filiais = [];
        $scope.situacoesFuncionais = [];
        $scope.lotacoes = [];
        $scope.folhaCompetencia = {};

        $scope.limparCampos = function () {
            if ($scope.relatorioFinanceiroForm) {
                $scope.relatorioFinanceiroForm.$setPristine();
                $scope.relatorioFinanceiroForm.$setUntouched();
            }
            $scope.relatorioFinanceiro = {};
            $scope.relatorioFinanceiro.filiais = [];
            $scope.relatorioFinanceiro.situacoesFuncionais = [];
            $scope.relatorioFinanceiro.lotacoes = [];
            $scope.relatorioFinanceiro.competencia = null;
            $scope.relatorioFinanceiro.status = null;
        }
        $scope.limparCampos();

        $scope.querySearchCompetencia = function (ano) {
            var deferred = $q.defer();
            var config = {
                params: {
                    ano: ano
                }
            };
            if (ano.length == 4) {
                GenericoService.GetAll('/competencia/porAno', config)
                    .then(function (response) {
                        if (response.data && response.data.length > 0) {
                            $scope.folhaCompetencias = response.data;
                            deferred.resolve(response.data);
                        }
                    });
            }
            return deferred.promise;
        };

        $scope.selectCompetencia = function () {
            if ($scope.relatorioFinanceiro.status == 'CALCULADA') {
                $scope.relatorioFinanceiro.competencia = $scope.relatorioFinanceiro.competenciaObject.id;
            } else {
                $scope.relatorioFinanceiro.competencia = $scope.folhaCompetencia.id;
            }
        }
        $scope.selectSituacaoRelatorio = function () {
            if ($scope.relatorioFinanceiro.status != 'CALCULADA') {
                $scope.relatorioFinanceiro.competenciaObject = null;
                $scope.relatorioFinanceiro.competencia = $scope.folhaCompetencia.id;
            }
        }

        $scope.loadLotacoesByFilial = function () {
            if ($scope.relatorioFinanceiro.filiais && $scope.relatorioFinanceiro.filiais.length > 0) {
                var config = { params: { empresasFiliaisId: $scope.relatorioFinanceiro.filiais } };
                $scope.loadedLotacao = false;
                GenericoService.Get('/listaLotacoes/filiais', config, function (response) {
                    if (response.status === 200) {
                        $scope.lotacoes = response.data;
                        $rootScope.$broadcast('preloader:hide');
                    } else if (response.status === 500) {
                        $scope.showSimpleToast("Falha no carregamento de dados, favor contate o administrador do sistema");
                    }
                    $scope.loadedLotacao = true;
                });
            } else {
                $scope.loadedLotacao = false;
                $scope.showSimpleToast("Marque ao menos 1 Filial que possua lotação.");
            }
        }


        $scope.loadEmpresa = function () {
            $scope.loadedEmpresa = false;
            GenericoService.GetAllDropdown('listaEmpresaMatrizComFiliais', function (response) {
                if (response.status === 200) {
                    $scope.empresas = response.data;
                    if ($scope.empresas) {
                        $scope.filiais = $scope.empresas[0].filiais;
                    }
                    $rootScope.$broadcast('preloader:hide');
                } else if (response.status === 500) {
                    $scope.showSimpleToast("Falha no carregamento de dados, favor contate o administrador do sistema");
                }
                $scope.loadedEmpresa = true;
            });
        }

        $scope.loadEmpresa();

        $scope.loadSituacoesFuncionais = function () {
            $scope.loadedSituacaoFuncional = false;
            GenericoService.GetAllDropdown('listaSituacoesFuncionais/entraFolha/true', function (response) {
                if (response.status === 200) {
                    $scope.situacoesFuncionais = response.data;
                    if ($scope.situacoesFuncionais) {
                        $scope.situacaoFuncionalId = $scope.situacoesFuncionais[0].id;
                    }
                    $rootScope.$broadcast('preloader:hide');
                } else if (response.status === 500) {
                    $scope.showSimpleToast("Falha no carregamento de dados, favor contate o administrador do sistema");
                }
                $scope.loadedSituacaoFuncional = true;
            });
        }

        $scope.loadSituacoesFuncionais();

        $scope.showInserirSalvarRelatorio = function (ev, idToSave) {
            var confirm = $mdDialog.confirm()
                .title('Deseja salvar este relatório?')
                .textContent('Esta ação não poderá ser desfeita.')
                .targetEvent(ev)
                .ok('Sim')
                .cancel('Não');
            $mdDialog.show(confirm).then(function () {
                $scope.inserirSalvarRelatorio(idToSave);
            }, function () { });
        };

        $scope.inserirSalvarRelatorio = function (idToSave) {
            $rootScope.$broadcast('preloader:active');
            GenericoService.Update('/relatorio/financeiro/salvar/' + idToSave, null, function (response) {
                $rootScope.$broadcast('preloader:hide');
                if (response.status === 201) {
                    $scope.showSimpleToast(response.data.message);
                    $scope.getCompetencia();
                    $scope.loadlistNaoSalvo();
                }
            });
        };


        $scope.showDeleteRelatorio = function (ev, idToDelete) {
            var confirm = $mdDialog.confirm()
                .title('Deseja confirmar a exclusão deste relatório?')
                .textContent('Esta ação não poderá ser desfeita.')
                .targetEvent(ev)
                .ok('Sim')
                .cancel('Não');
            $mdDialog.show(confirm).then(function () {
                $scope.deleteRelatorio(idToDelete);
            }, function () { });
        };

        $scope.deleteRelatorio = function (idToDelete) {
            $rootScope.$broadcast('preloader:active');
            GenericoService.Delete('/relatorio/financeiro/' + idToDelete, function (response) {
                $rootScope.$broadcast('preloader:hide');
                if (response.status === 200) {
                    $scope.showSimpleToast(response.data.message);
                    $scope.getCompetencia();
                    $scope.loadlistNaoSalvo();
                }
            });
        };

        $scope.showSimpleToast = function (textContent) {
            $mdToast.show(
                $mdToast.simple()
                    .textContent(textContent)
                    .position('top right')
                    .hideDelay(8000)
            );
        };

        $scope.save = function () {
            if ($scope.relatorioFinanceiro.status == 'PREVIA') {
                $scope.relatorioFinanceiro.competencia = $scope.folhaCompetencia.id;
            } else {

            }
            $rootScope.$broadcast('preloader:active');
            GenericoService.Create('/relatorio/financeiro/', $scope.relatorioFinanceiro, function (response) {
                $rootScope.$broadcast('preloader:hide');
                if (response.status === 201 && response.data.success) {
                    $scope.showSimpleToast(response.data.message);
                    $scope.loadlistNaoSalvo();
                    $scope.limparCampos();
                    $state.reload();
                } else if (response.status === 400) {
                    $scope.showSimpleToast(response.data.message);
                    $location.path('/relatorio/financeiro/gestao');
                }
            });
        }

        $scope.loadlistNaoSalvo = function () {
            $rootScope.$broadcast('preloader:active');
            GenericoService.GetAll('/relatorio/financeiro/naoSalvo').then(function (response) {
                if (response.status === 200) {
                    $scope.relatorios = response.data;
                }
                $rootScope.$broadcast('preloader:hide')
            });
        }

        $scope.dialogAlterarStatus = function (item) {
            var useFullScreen = ($mdMedia('sm') || $mdMedia('xs'));
            $scope.relatorioStatus = item;
            $scope.statusDialog = '';

            $mdDialog.show({
                require: { container: 'relatorioFinanceiroFolhaPagamentoCtrl' },
                scope: $scope,
                preserveScope: true,
                controller: ['$scope', '$q', function ($scope, $q) {

                    $scope.alterar = function () {
                        $scope.relatorioStatus.status = $scope.statusDialog;
                        $scope.alterarStatus($scope.relatorioStatus);
                        $scope.cancel();
                    };

                    $scope.cancel = function () {
                        $mdDialog.cancel();
                    };
                }],
                templateUrl: 'app/page/relatorioFinanceiroFolhaPagamento/dialogAlterarStatus.html',
                parent: angular.element(document.body),
                clickOutsideToClose: true,
                fullscreen: useFullScreen
            });

        }

        $scope.alterarStatus = function (relatorioFinanceiro) {
            $rootScope.$broadcast('preloader:active');
            GenericoService.Update('/relatorio/financeiro/' + relatorioFinanceiro.id + '/alterar/' + relatorioFinanceiro.status, null, function (response) {
                $rootScope.$broadcast('preloader:hide');
                if (response.status === 201 && response.data.success) {
                    $scope.showSimpleToast(response.data.message);
                    $scope.loadlistNaoSalvo();
                } else if (response.status === 400) {
                    $scope.showSimpleToast(response.data.message);
                    $scope.loadlistNaoSalvo();
                }
            });
        }



        // HISTÓRICO DE RELATÓRIOS

        $scope.competenciaSearch = null;
        $scope.relatoriosSalvos = [];

        $scope.limpaSearch = function () {
            $scope.competenciaSearch = null;
            $scope.relatoriosSalvos = [];
        }

        // $scope.anoCompetencias = function () {
        //     $rootScope.$broadcast('preloader:active');
        //     GenericoService.GetAll('/competencia/anos').then(function (response) {
        //         if (response.status === 200) {
        //             $scope.anosCompetencia = response.data;
        //         }
        //         $rootScope.$broadcast('preloader:hide')
        //     });
        // }

        $scope.selectCompetenciaSearch = function () {
            if ($scope.competenciaSearch) {
                $scope.loadlistSalvo($scope.competenciaSearch.id);
            }
        }

        $scope.loadlistSalvo = function (competenciaId) {
            $rootScope.$broadcast('preloader:active');
            GenericoService.GetAll('/relatorio/financeiro/salvo/' + competenciaId).then(function (response) {
                if (response.status === 200) {
                    $scope.relatoriosSalvos = response.data;
                }
                $rootScope.$broadcast('preloader:hide')
            });
        }

        // $scope.competenciasByAno = function (ano) {
        //     $rootScope.$broadcast('preloader:active');
        //     GenericoService.GetAll('/competencia/porAno/' + ano).then(function (response) {
        //         if (response.status === 200) {
        //             $scope.competencias = response.data;
        //             $scope.historicofolhas = [];
        //         }
        //         $rootScope.$broadcast('preloader:hide')
        //     });
        // }

    }
})();