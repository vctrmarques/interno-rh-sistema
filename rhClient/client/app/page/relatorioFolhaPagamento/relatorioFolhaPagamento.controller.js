(function () {
    'use strict';

    angular.module('app.page')
        .controller('relatorioFolhaPagamentoCtrl', ['configValue', '$scope', '$mdToast', '$mdDialog', '$rootScope', '$q', '$timeout', 'GenericoService', 'EnumService', 'RestService', relatorioFolhaPagamentoCtrl]);

    function relatorioFolhaPagamentoCtrl(configValue, $scope, $mdToast, $mdDialog, $rootScope, $q, $timeout, GenericoService, EnumService, RestService) {

        // Checa as regras de acesso da tela
        RestService.Get('/usuario/verificaPermissao/por/menu', { params: { menu: 'Rel. Folha de Pgt' } })
        .then(function (response) {
            if (response.status === 200 && response.data) {

                $scope.usuarioAdm = response.data.usuarioAdm;
                $scope.podeGerenciar = false;

                if (!$scope.usuarioAdm && !$scope.podeGerenciar)
                    $location.path('page/403');
            }
        }, function errorCallback(response) {
            if (response.status === 400) {
                $scope.showSimpleToast(response.data.message);
            }
        });

        $scope.selectedFilial = [];
        $scope.selectedFuncional = [];
        $scope.statusBusca = false;
        $scope.query = {
            order: 'id',
            limit: 10,
            page: 1
        };

        $scope.list = {
            "count": 0,
            "data": [],
            "listFiliais": [],
            "situacaoFuncional": []
        };

        $scope.object = [];
        $scope.selectedList = [];

        $scope.loadList = function () {
            $rootScope.$broadcast('preloader:active');

            var config = {
                params: {
                    page: $scope.query.page - 1,
                    size: $scope.query.limit,
                    order: $scope.query.order,
                    status: $scope.statusBusca,
                }
        };

        $scope.promise = GenericoService.GetAll('/adiantamentos', config).then(
            function (response) {
                if (response.status === 200) {
                    $scope.list.data = response.data.content;
                    $scope.list.count = response.data.totalElements;
                    $rootScope.$broadcast('preloader:hide');
                } else {
                    $scope.showSimpleToast("Não foi possível carregar os dados.")
                }
            });
        }

        // Busca dos Anos das Comptetências Fechadas
        RestService.Get('/competencia/anos')
        .then(function (response) {
            if (response.status === 200 && response.data) {
                $scope.anoCompetenciaList = response.data;
            } else if (response.status === 200 && !response.data) {
                $scope.showSimpleToast("Não existem anos de competência disponíveis.");
            }
        }, function errorCallback(response) {
            if (response.status === 400) {
                $scope.showSimpleToast(response.data.message);
            }
        });
     
        $scope.carregarListaFiliais = function() {
            GenericoService.GetAllDropdown('listaFiliais', function (response) {
                if (response.status === 200) {
                    $scope.list.listFiliais = response.data;
                    $rootScope.$broadcast('preloader:hide');
                } else {
                    $scope.showSimpleToast("Não foi possível carregar os dados.")
                }
            })
        };

        $scope.loadSituacoesFuncionais = function () {
            $scope.loadedSituacaoFuncional = false;
            GenericoService.GetAllDropdown('listaSituacoesFuncionais/entraFolha/true', function (response) {
                if (response.status === 200) {
                    $scope.list.situacaoFuncional = response.data;
                    $rootScope.$broadcast('preloader:hide');
                } else if (response.status === 500) {
                    $scope.showSimpleToast("Falha no carregamento de dados, favor contate o administrador do sistema");
                }
            });
        }

        $scope.selectedTabIndex = 0;

        $scope.addTab = function (valueTab, item) {
            //Adicionando o item selecionado na tab atual.
            $scope.tabs[$scope.selectedTabIndex].selectedItem = item;

            //Navegando para a próxima tab.
            $scope.tabs.push({ value: valueTab, selectedItem: null });
            $scope.selectedTabIndex = $scope.tabs.length - 1;
        };

        $scope.onTabSelected = function () {
            // Deleta tabs da migalha de pão de tabs.
            if ($scope.selectedTabIndex !== $scope.tabs.length - 1) {
                $scope.tabs.splice($scope.selectedTabIndex + 1, $scope.tabs.length - 1);
            } else {
                if ($scope.relatorioFolhaPagamento
                    && $scope.relatorioFolhaPagamento.ano
                    && $scope.relatorioFolhaPagamento.competencia
                    && $scope.relatorioFolhaPagamento.tipoProcessamento) {
                    $scope.loadList();
                }
            }

            //Limpando o item selecionado da última aba
            $scope.tabs[$scope.tabs.length - 1].selectedItem = null;

        };

        function getSelectedFilter(abaValue) {
            for (var index = 0; index < $scope.tabs.length; index++) {
                var element = $scope.tabs[index];
                if (element.value === abaValue && element.selectedItem && element.selectedItem.tipo) {
                    if (element.selectedItem.tipo.id)
                        return element.selectedItem.tipo.id;
                    return element.selectedItem.tipo.descricao;
                } else if (element.value === abaValue && element.selectedItem && element.selectedItem.funcionario) {
                    if (element.selectedItem.funcionario.id)
                        return element.selectedItem.funcionario.id;
                }
            }
            return null;
        };

        // Carga de meses 
        var meseLabel = {}
        function add(mes, mesLabel) {
            meseLabel[mes] = mesLabel
        }
        function get(mes) {
            return meseLabel[mes]
        }
        add(1, 'Janeiro');
        add(2, 'Fevereiro');
        add(3, 'Março');
        add(4, 'Abril');
        add(5, 'Maio');
        add(6, 'Junho');
        add(7, 'Julho');
        add(8, 'Agosto');
        add(9, 'Setembro');
        add(10, 'Outubro');
        add(11, 'Novembro');
        add(12, 'Dezembro');


        $scope.changeAno = function () {
            if ($scope.relatorioFolhaPagamento && $scope.relatorioFolhaPagamento.ano) {
                $scope.competenciaList = [];
                $scope.relatorioFolhaPagamento.competencia = null;
                $scope.tipoProcessamentoList = [];
                $scope.relatorioFolhaPagamento.tipoProcessamento = null;

                $rootScope.$broadcast('preloader:active');
                RestService.Get('/competencia/porAno/folhaBloqueadaConcluida/' + $scope.relatorioFolhaPagamento.ano)
                    .then(function (response) {
                        if (response.status === 200 && response.data) {
                            $scope.competenciaList = response.data;
                            $scope.competenciaList.forEach(comp => {
                                comp.mesCompetenciaLabel = get(comp.mesCompetencia);
                            });
                            $rootScope.$broadcast('preloader:hide');
                        } else if (response.status === 200 && !response.data) {
                            $scope.showSimpleToast("Não existem meses de competência para o filtro aplicado.");
                        }
                    }, function errorCallback(response) {
                        if (response.status === 400) {
                            $scope.showSimpleToast(response.data.message);
                        }
                    });
            }
        };

        // Busca Tipos de Processamentos por Competencia
        $scope.changeMes = function () {
            if ($scope.relatorioFolhaPagamento && $scope.relatorioFolhaPagamento.competencia) {
                $scope.tipoProcessamentoList = [];
                $scope.relatorioFolhaPagamento.tipoProcessamento = null;

                $rootScope.$broadcast('preloader:active');
                RestService.Get('/tipoProcessamento/folha/pagamento/competencia/' + $scope.relatorioFolhaPagamento.competencia.id)
                    .then(function (response) {
                        $rootScope.$broadcast('preloader:hide');
                        if (response.status === 200 && response.data) {
                            $scope.tipoProcessamentoList = response.data;
                        } else if (response.status === 200 && !response.data) {
                            $scope.showSimpleToast("Não existem tipos de processamentos para o filtro aplicado.");
                        }
                    }, function errorCallback(response) {
                        if (response.status === 400) {
                            $scope.showSimpleToast(response.data.message);
                        }
                    });
            }
        };

        // Rotina responsável por desabilitar o combo de ano da competência.
        $scope.anoDisabled = function () {
            if (!$scope.anoCompetenciaList || $scope.anoCompetenciaList.length === 0 || $scope.selectedTabIndex !== 0)
                return true;
            return false;
        }

        // Rotina responsável por desabilitar o combo de mês da competencia
        $scope.competenciaDisabled = function () {
            if (!$scope.relatorioFolhaPagamento
                || !$scope.relatorioFolhaPagamento.ano
                || !$scope.competenciaList
                || $scope.competenciaList.length === 0
                || $scope.selectedTabIndex !== 0)
                return true;
            return false;
        }

        // Rotina responsável por desabilitar o combo de tipo de processamento.
        $scope.tipoProcessamentoDisabled = function () {
            if (!$scope.relatorioFolhaPagamento
                || !$scope.relatorioFolhaPagamento.ano
                || !$scope.relatorioFolhaPagamento.competencia
                || !$scope.tipoProcessamentoList
                || $scope.tipoProcessamentoList.length === 0
                || $scope.selectedTabIndex !== 0)
                return true;
            return false;
        }


        $scope.checkAll = function(checked) {
           $scope.checkall = !checked;
        }

        $scope.showRelatorio = function(formatoRelatorio) {
            $scope.formatoRelatorio = formatoRelatorio;

            // Validação dos filtros
            if (!$scope.selectedFilial.length > 0) {
                $scope.showSimpleToast("Favor selecionar ao menos uma Filial.");
                return;
            }

            if (!$scope.relatorioFolhaPagamento || !$scope.relatorioFolhaPagamento.ano) {
                $scope.showSimpleToast("Favor selecionar o ano da competência.");
                return;
            }

            if (!$scope.relatorioFolhaPagamento || !$scope.relatorioFolhaPagamento.competencia) {
                $scope.showSimpleToast("Favor selecionar o mês da competência.");
                return;
            }

            if (!$scope.relatorioFolhaPagamento || !$scope.relatorioFolhaPagamento.tipoProcessamento) {
                $scope.showSimpleToast("Favor selecionar o tipo de processamento.");
                return;
            }
            
            var filialList = [];
            var filialString = [];
            if ($scope.selectedFilial && $scope.selectedFilial.length > 0) {
                $scope.selectedFilial.forEach(element => {
                    filialList.push(element.id);
                    filialString.push(element.nomeFilial);
                });
            }

                // Filtro de fundos
            var funcionalList = [];
            if ($scope.selectedFuncional && $scope.selectedFuncional.length > 0) {
                $scope.selectedFuncional.forEach(element => {
                    funcionalList.push(element.descricao);
                });
            }
            var config = {
                params: {
                    filialList: filialList,
                    filialString: filialString,
                    situacaoFuncionalList: funcionalList,
                    competencia: $scope.relatorioFolhaPagamento.competencia.mesCompetencia,
                    ano: $scope.relatorioFolhaPagamento.ano,
                    tipoProcessamentoId: $scope.relatorioFolhaPagamento.tipoProcessamento.id,
                    tipoProcessamento: $scope.relatorioFolhaPagamento.tipoProcessamento.descricao
                },
                responseType: 'arraybuffer'
            };

            $rootScope.$broadcast('preloader:active');
            RestService.Get('/relatorioFolhaPagamento/relatorio/' + $scope.formatoRelatorio, config)
                .then(function successCallback(response) {
                    if ($scope.formatoRelatorio === 'pdf') {
                        $rootScope.$broadcast('preloader:hide');
                        var file = new Blob([response.data], { type: 'application/pdf' });
                        var fileURL = URL.createObjectURL(file);
                        window.open(fileURL);

                    } else {
                        $rootScope.$broadcast('preloader:hide');
                        var file = new Blob([response.data], { type: 'application/vnd.ms-excel' });
                        var fileURL = URL.createObjectURL(file);
                        window.open(fileURL);

                    };
                }, function errorCallback(response) {
                    $rootScope.$broadcast('preloader:hide');
                    if (response.status === 400) {
                        $scope.showSimpleToast("Não foi possível carregar os dados.")
                    }
                });
        };

        $scope.carregarListaFiliais();        
        $scope.loadSituacoesFuncionais();

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



