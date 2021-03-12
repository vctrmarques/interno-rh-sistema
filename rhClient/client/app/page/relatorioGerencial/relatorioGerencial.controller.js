(function () {
    'use strict';

    angular.module('app.page')
        .controller('relatorioGerencialCtrl', ['$scope', '$mdToast', '$rootScope', 'RestService', '$mdDialog', relatorioGerencialCtrl]);

    function relatorioGerencialCtrl($scope, $mdToast, $rootScope, RestService, $mdDialog) {

        // Checa as regras de acesso da tela
        RestService.Get('/usuario/verificaPermissao/por/menu', { params: { menu: 'Rel. Gerencial' } })
            .then(function (response) {
                if (response.status === 200 && response.data) {

                    $scope.usuarioAdm = response.data.usuarioAdm;
                    $scope.podeGerenciar = false;

                    if (response.data.papeis)
                        $scope.podeGerenciar = response.data.papeis.includes('ROLE_RELATORIO_GERENCIAL_GESTAO') ? true : false;

                    if (!$scope.usuarioAdm && !$scope.podeGerenciar)
                        $location.path('page/403');
                }
            }, function errorCallback(response) {
                if (response.status === 400) {
                    $scope.showSimpleToast(response.data.message);
                }
            });


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

        // Busca Comptetências Fechadas por Ano
        $scope.changeAno = function () {
            if ($scope.relatorioGerencialFiltro && $scope.relatorioGerencialFiltro.ano) {
                $scope.competenciaList = [];
                $scope.relatorioGerencialFiltro.competencia = null;
                $scope.tipoProcessamentoList = [];
                $scope.relatorioGerencialFiltro.tipoProcessamento = null;

                $rootScope.$broadcast('preloader:active');
                RestService.Get('/competencia/porAno/folhaBloqueadaConcluida/' + $scope.relatorioGerencialFiltro.ano)
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
            if ($scope.relatorioGerencialFiltro && $scope.relatorioGerencialFiltro.competencia) {
                $scope.tipoProcessamentoList = [];
                $scope.relatorioGerencialFiltro.tipoProcessamento = null;

                $rootScope.$broadcast('preloader:active');
                RestService.Get('/tipoProcessamento/folha/pagamento/competencia/' + $scope.relatorioGerencialFiltro.competencia.id)
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
            if (!$scope.relatorioGerencialFiltro
                || !$scope.relatorioGerencialFiltro.ano
                || !$scope.competenciaList
                || $scope.competenciaList.length === 0
                || $scope.selectedTabIndex !== 0)
                return true;
            return false;
        }

        // Rotina responsável por desabilitar o combo de tipo de processamento.
        $scope.tipoProcessamentoDisabled = function () {
            if (!$scope.relatorioGerencialFiltro
                || !$scope.relatorioGerencialFiltro.ano
                || !$scope.relatorioGerencialFiltro.competencia
                || !$scope.tipoProcessamentoList
                || $scope.tipoProcessamentoList.length === 0
                || $scope.selectedTabIndex !== 0)
                return true;
            return false;
        }

        // Rotina responsável por desabilitar o botão de comparaçaõ
        $scope.compareDisabled = function () {
            if (!$scope.relatorioGerencialFiltro
                || !$scope.relatorioGerencialFiltro.ano
                || !$scope.relatorioGerencialFiltro.competencia
                || !$scope.relatorioGerencialFiltro.tipoProcessamento)
                return true;
            return false;
        }

        // Carga das dependências da paginação do objeto principal
        $scope.query = {
            order: 'nome',
            limit: 10,
            page: 1
        };

        $scope.list = {
            "count": 0,
            "data": []
        };

        $scope.limitOptions = [5, 10, 15];

        $scope.limpaFiltro = function () {
            delete $scope.relatorioGerencialFiltro;
            delete $scope.mesComparacao;
            $scope.list.data = [];
            $scope.list.count = 0;
            $scope.tabs = [{ value: 'filial', selectedItem: null }];
            $scope.selectedTabIndex = 0;
        }

        //Adicionando a primeira tab.
        $scope.tabs = [{ value: 'filial', selectedItem: null }];
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
                if ($scope.relatorioGerencialFiltro
                    && $scope.relatorioGerencialFiltro.ano
                    && $scope.relatorioGerencialFiltro.competencia
                    && $scope.relatorioGerencialFiltro.tipoProcessamento) {
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

        $scope.loadList = function () {

            if (!$scope.relatorioGerencialFiltro || !$scope.relatorioGerencialFiltro.ano) {
                $scope.showSimpleToast("Favor selecionar o ano da competência.");
                return;
            }

            if (!$scope.relatorioGerencialFiltro || !$scope.relatorioGerencialFiltro.competencia) {
                $scope.showSimpleToast("Favor selecionar o mês da competência.");
                return;
            }

            if (!$scope.relatorioGerencialFiltro || !$scope.relatorioGerencialFiltro.tipoProcessamento) {
                $scope.showSimpleToast("Favor selecionar o tipo de processamento.");
                return;
            }

            var config = {
                params: {
                    page: $scope.query.page - 1,
                    size: $scope.query.limit,
                    folhaCompetenciaId: $scope.relatorioGerencialFiltro.competencia.id,
                    tipoProcessamentoId: $scope.relatorioGerencialFiltro.tipoProcessamento.id,
                    aba: $scope.tabs[$scope.selectedTabIndex].value,
                    abaLabel: $scope.getLabelTab($scope.tabs[$scope.selectedTabIndex].value),
                    filialId: getSelectedFilter('filial'),
                    cargoEfetivo: getSelectedFilter('cargo'),
                    cargoFuncao: getSelectedFilter('funcao'),
                    funcionarioId: getSelectedFilter('funcionario'),
                    lotacao: getSelectedFilter('lotacao'),
                    vinculo: getSelectedFilter('vinculo'),
                    folhaCompetenciaComparacaoId: $scope.mesComparacao ? $scope.mesComparacao.id : null,
                }
            };

            $scope.list.data = [];

            $rootScope.$broadcast('preloader:active');
            $scope.promise = RestService.Get('/relatorioGerencial', config)
                .then(function successCallback(response) {
                    $rootScope.$broadcast('preloader:hide');
                    if (response.status === 200) {

                        $scope.list.count = response.data.totalElements;

                        if ($scope.tabs[$scope.selectedTabIndex].value === 'resumo') {

                            var somatorioVantagem = {
                                descricaoVerba: 'TOTAL DE VANTAGENS',
                                somatorioResumo: true,
                                colorStyle: { 'color': 'rgb(16,108,200)' }
                            };
                            response.data.content.forEach(item => {
                                if (item.tipoVerba === 'VANTAGEM') {
                                    $scope.list.data.push(item);
                                    if (!somatorioVantagem.valor)
                                        somatorioVantagem.valor = 0;
                                    somatorioVantagem.valor += item.valor ? item.valor : 0.0;

                                    if (!somatorioVantagem.valorCompare)
                                        somatorioVantagem.valorCompare = 0;
                                    somatorioVantagem.valorCompare += item.valorCompare ? item.valorCompare : 0.0;
                                }
                            });
                            $scope.list.data.push(somatorioVantagem);

                            var somatorioDesconto = {
                                descricaoVerba: 'TOTAL DE DESCONTOS',
                                somatorioResumo: true,
                                colorStyle: { 'color': 'rgb(255,87,34)' }
                            };
                            response.data.content.forEach(item => {
                                if (item.tipoVerba === 'DESCONTO') {
                                    $scope.list.data.push(item);
                                    if (!somatorioDesconto.valor)
                                        somatorioDesconto.valor = 0;
                                    somatorioDesconto.valor += item.valor ? item.valor : 0.0;

                                    if (!somatorioDesconto.valorCompare)
                                        somatorioDesconto.valorCompare = 0;
                                    somatorioDesconto.valorCompare += item.valorCompare ? item.valorCompare : 0.0;
                                }
                            });
                            $scope.list.data.push(somatorioDesconto);

                            var somatorioLiquido = {
                                descricaoVerba: 'TOTAL LÍQUIDO',
                                somatorioResumo: true,
                                valor: somatorioVantagem.valor - somatorioDesconto.valor
                            };
                            if (somatorioVantagem.valorCompare && somatorioDesconto.valorCompare) {
                                somatorioLiquido.valorCompare = somatorioVantagem.valorCompare - somatorioDesconto.valorCompare
                            }
                            $scope.list.data.push(somatorioLiquido);

                        } else {
                            $scope.list.data = response.data.content;

                            if ($scope.list.count <= 10) {
                                //Somatório dos valores cuja lista não vai paginar (até 10 itens)
                                var somatorio = {};
                                $scope.list.data.forEach(item => {
                                    if (item.qtdFuncionario) {
                                        if (!somatorio.qtdFuncionario)
                                            somatorio.qtdFuncionario = 0;
                                        somatorio.qtdFuncionario += item.qtdFuncionario;
                                    }
                                    if (item.totalProvento) {
                                        if (!somatorio.totalProvento)
                                            somatorio.totalProvento = 0;
                                        somatorio.totalProvento += item.totalProvento;
                                    }
                                    if (item.totalDesconto) {
                                        if (!somatorio.totalDesconto)
                                            somatorio.totalDesconto = 0;
                                        somatorio.totalDesconto += item.totalDesconto;
                                    }
                                    if (item.totalLiquido) {
                                        if (!somatorio.totalLiquido)
                                            somatorio.totalLiquido = 0;
                                        somatorio.totalLiquido += item.totalLiquido;
                                    }
                                    if (item.qtdFuncionarioCompare) {
                                        if (!somatorio.qtdFuncionarioCompare)
                                            somatorio.qtdFuncionarioCompare = 0;
                                        somatorio.qtdFuncionarioCompare += item.qtdFuncionarioCompare;
                                    }
                                    if (item.totalProventoCompare) {
                                        if (!somatorio.totalProventoCompare)
                                            somatorio.totalProventoCompare = 0;
                                        somatorio.totalProventoCompare += item.totalProventoCompare;
                                    }
                                    if (item.totalDescontoCompare) {
                                        if (!somatorio.totalDescontoCompare)
                                            somatorio.totalDescontoCompare = 0;
                                        somatorio.totalDescontoCompare += item.totalDescontoCompare;
                                    }
                                    if (item.totalLiquidoCompare) {
                                        if (!somatorio.totalLiquidoCompare)
                                            somatorio.totalLiquidoCompare = 0;
                                        somatorio.totalLiquidoCompare += item.totalLiquidoCompare;
                                    }
                                });
                                if (somatorio.totalProvento && somatorio.qtdFuncionario)
                                    somatorio.valorMedio = somatorio.totalProvento / somatorio.qtdFuncionario;
                                if (somatorio.totalProventoCompare && somatorio.qtdFuncionarioCompare)
                                    somatorio.valorMedioCompare = somatorio.totalProventoCompare / somatorio.qtdFuncionarioCompare;
                                $scope.list.data.push(somatorio);

                            } else {
                                //Somatório dos valores via backend.
                                $rootScope.$broadcast('preloader:active');
                                RestService.Get('/relatorioGerencial/somatorio', config)
                                    .then(function successCallback(response) {
                                        $rootScope.$broadcast('preloader:hide');
                                        if (response.status === 200) {
                                            $scope.list.data.push(response.data);
                                        }
                                    }, function errorCallback(response) {
                                        $rootScope.$broadcast('preloader:hide');
                                        if (response.status === 400) {
                                            $scope.showSimpleToast("Não foi possível carregar os dados.")
                                        }
                                    });
                            }
                        }

                    }
                }, function errorCallback(response) {
                    $rootScope.$broadcast('preloader:hide');
                    if (response.status === 400) {
                        $scope.showSimpleToast("Não foi possível carregar os dados.")
                    }
                });
        }

        $scope.getLabelTab = function (value) {
            if (value === "filial") {
                return "Filiais";
            } else if (value === "cargo") {
                return "Cargos";
            } else if (value === "lotacao") {
                return "Lotações";
            } else if (value === "funcao") {
                return "Funções";
            } else if (value === "vinculo") {
                return "Vínculos";
            } else if (value === "resumo") {
                return "Resumo";
            } else if (value === "funcionario") {
                return "Funcionários";
            }
        }

        $scope.showRelatorio = function (formatoRelatorio) {
            $scope.formatoRelatorio = formatoRelatorio;

            // Validação dos filtros
            if (!$scope.relatorioGerencialFiltro || !$scope.relatorioGerencialFiltro.ano) {
                $scope.showSimpleToast("Favor selecionar o ano da competência.");
                return;
            }

            if (!$scope.relatorioGerencialFiltro || !$scope.relatorioGerencialFiltro.competencia) {
                $scope.showSimpleToast("Favor selecionar o mês da competência.");
                return;
            }

            if (!$scope.relatorioGerencialFiltro || !$scope.relatorioGerencialFiltro.tipoProcessamento) {
                $scope.showSimpleToast("Favor selecionar o tipo de processamento.");
                return;
            }

            var config = {
                params: {
                    folhaCompetenciaId: $scope.relatorioGerencialFiltro.competencia.id,
                    tipoProcessamentoId: $scope.relatorioGerencialFiltro.tipoProcessamento.id,
                    aba: $scope.tabs[$scope.selectedTabIndex].value,
                    abaLabel: $scope.getLabelTab($scope.tabs[$scope.selectedTabIndex].value),
                    anoCompetencia: $scope.relatorioGerencialFiltro.ano,
                    mesCompetencia: $scope.relatorioGerencialFiltro.competencia.mesCompetenciaLabel,
                    tipoProcessamento: $scope.relatorioGerencialFiltro.tipoProcessamento.codigo + ' - ' + $scope.relatorioGerencialFiltro.tipoProcessamento.descricao,
                    filialId: getSelectedFilter('filial'),
                    cargoEfetivo: getSelectedFilter('cargo'),
                    cargoFuncao: getSelectedFilter('funcao'),
                    funcionarioId: getSelectedFilter('funcionario'),
                    lotacao: getSelectedFilter('lotacao'),
                    vinculo: getSelectedFilter('vinculo')
                },
                responseType: 'arraybuffer'
            };

            $scope.loadingDownload = true;
            $rootScope.$broadcast('preloader:active');

            RestService.Get('/relatorioGerencial/relatorio/' + $scope.formatoRelatorio, config)
                .then(function successCallback(response) {
                    if ($scope.formatoRelatorio === 'pdf') {
                        var file = new Blob([response.data], { type: 'application/pdf' });
                        var fileURL = URL.createObjectURL(file);

                        $rootScope.$broadcast('preloader:hide');
                        $scope.loadingDownload = false;
                        window.open(fileURL);

                    } else {
                        var file = new Blob([response.data], { type: 'application/vnd.ms-excel' });
                        var fileURL = URL.createObjectURL(file);

                        $rootScope.$broadcast('preloader:hide');
                        $scope.loadingDownload = false;
                        window.open(fileURL);

                    };
                }, function errorCallback(response) {
                    $rootScope.$broadcast('preloader:hide');
                    if (response.status === 400) {
                        $scope.showSimpleToast("Não foi possível carregar os dados.")
                    }
                });

        };

        $scope.showMesCompare = function (ev) {

            if (!$scope.compareDisabled()) {
                $mdDialog.show({
                    scope: $scope,
                    preserveScope: true,
                    controller: ['$scope', '$mdDialog', function ($scope, $mdDialog) {
                        $scope.cancel = function () {
                            delete $scope.mesComparacaoPre;
                            $mdDialog.cancel();
                        };
                        $scope.comparar = function () {
                            $scope.mesComparacao = $scope.mesComparacaoPre;
                            delete $scope.mesComparacaoPre;
                            $scope.loadList();
                            $mdDialog.hide();
                        }
                    }],
                    template: `
                    <md-dialog aria-label="Selecione o Mês de Comparação">
                        <form ng-cloak>
                            <md-toolbar>
                                <div class="md-toolbar-tools">
                                    <h2>Selecione o Mês de Comparação</h2>
                                    <span flex></span>
                                    <md-button class="md-icon-button" ng-click="cancel()">
                                        <md-icon class="material-icons">close</md-icon>
                                    </md-button>
                                </div>
                            </md-toolbar>
                            <md-dialog-content>
                                <div class="md-dialog-content">
                                    <div layout layout-sm="column">
                                        <md-input-container flex>
                                            <label>Mês de Comparação</label>
                                            <md-select ng-model="mesComparacaoPre" placeholder="Selecione o mês">
                                                <md-option ng-disabled="obj.id === relatorioGerencialFiltro.competencia.id" ng-value="obj" ng-repeat="obj in competenciaList">
                                                    {{obj.mesCompetenciaLabel}}
                                                </md-option>
                                            </md-select>
                                        </md-input-container>
                                    </div>
                                </div>
                            </md-dialog-content>
                            <md-dialog-actions layout="row">
                                <md-button md-no-ink class="md-primary" ng-click="comparar()">
                                    Comparar
                                </md-button>
                                <md-button ng-click="cancel()">
                                    Cancelar
                                </md-button>
                            </md-dialog-actions>
                        </form>
                    </md-dialog>
                `,
                    parent: angular.element(document.body),
                    targetEvent: ev,
                    clickOutsideToClose: true
                });
            }
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



