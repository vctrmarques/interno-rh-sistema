(function () {
    'use strict';

    angular.module('app.page')
        .controller('certidaoCompensacaoCtrl', ['configValue', '$scope', '$mdToast', '$mdDialog', '$mdMedia', '$rootScope', 'GenericoService', 'RestService', '$filter', 'EnumService', '$location', certidaoCompensacaoCtrl]);

    function certidaoCompensacaoCtrl(configValue, $scope, $mdToast, $mdDialog, $mdMedia, $rootScope, GenericoService, RestService, $filter, EnumService, $location) {

        // Checa as regras de acesso da tela
        RestService.Get('/usuario/verificaPermissao/por/menu', { params: { menu: 'CTC - Compensação' } }).then(
            function (response) {
                if (response.status === 200 && response.data) {

                    $scope.usuarioAdm = response.data.usuarioAdm;
                    $scope.podeCadastrar = response.data.usuarioAdm ? true : response.data.podeCadastrar;
                    $scope.podeAtualizar = response.data.usuarioAdm ? true : response.data.podeAtualizar;
                    $scope.podeVisualizar = response.data.usuarioAdm ? true : response.data.podeVisualizar;
                    $scope.podeExcluir = response.data.usuarioAdm ? true : response.data.podeExcluir;

                    if ($scope.usuarioAdm || $scope.podeCadastrar || $scope.podeAtualizar || $scope.podeVisualizar || $scope.podeExcluir)
                        $scope.loadList();
                    else
                        $location.path('page/403');
                }
            }, function errorCallback(response) {
                if (response.status === 400) {
                    $scope.showSimpleToast(response.data.message);
                }
            });

        $scope.limitOptions = [5, 10, 15];
        $scope.descricaoBusca = "";
        $scope.query = {
            order: '-createdBy',
            limit: 10,
            page: 1
        };

        $scope.quantidadePaginas = 0;

        $scope.list = {
            "count": 0,
            "data": []
        };
        $scope.aba = 'TODOS';
        $scope.lista = {
            classificacoesEspecial: []
        };

        $scope.selectedClassificacoes = [];
        $scope.selectedClassificacoesEspecial = [];
        EnumService.Get("ClassificacaoCertidaoCompensacaoEnum").then(function (dados) {
            $scope.lista.classificacoes = dados;
            $scope.abaActive = $scope.lista.classificacoes.length;

            $scope.classificacoesMap = new Map();
            $scope.lista.classificacoes.forEach(element => {
                $scope.classificacoesMap.set(element.value, element.label);
                if (element.value === 'APOSENTADORIA')
                    $scope.lista.classificacoesEspecial.push(element);
                if (element.value === 'APOSENTADORIA_SEM_COMPENSACAO')
                    $scope.lista.classificacoesEspecial.push(element);
            });
        });

        $scope.selectedStatus = [];
        EnumService.Get("StatusCertidaoCompensacaoEnum").then(function (dados) {
            $scope.lista.status = dados;
        });

        $scope.selectedFundos = [];
        EnumService.Get("FundoCertidaoCompensacaoEnum").then(function (dados) {
            $scope.lista.fundos = dados;
        });

        $scope.abaAtual = function (valor) {
            $scope.aba = valor;
            if ($scope.aba == 'TODOS') {
                delete $scope.classificacao;
            } else {
                $scope.classificacao = $scope.aba;
            }
            $scope.loadList();
        };

        $scope.filtrarAba = function (item) {
            return item.classificacaoName == $scope.aba;
        };

        $scope.limpaFiltro = function () {
            delete $scope.descricaoBusca;
            delete $scope.selectedStatus;
            delete $scope.selectedFundos;
            delete $scope.selectedClassificacoes;
            delete $scope.selectedClassificacoesEspecial;
            delete $scope.dataInicial;
            delete $scope.dataFinal;
            $scope.loadList();
        };

        $scope.deleteFeedback = function () {
            delete $scope.tootipFeedback;
        }

        $scope.loadFeedback = function (type) {
            $scope.tootipFeedback = {
                title: '',
                description: ''
            }

            if (type === 'status') {
                if ($scope.selectedStatus && $scope.selectedStatus.length > 0) {
                    $scope.tootipFeedback.title = ' * Status Selecionados: '
                    $scope.selectedStatus.forEach(element => {
                        $scope.tootipFeedback.description += $scope.tootipFeedback.description.length === 0 ? element.label : ', ' + element.label;
                    });
                    $scope.tootipFeedback.description += '.';
                } else {
                    $scope.tootipFeedback.title = ' * Busca por Status: ';
                    $scope.tootipFeedback.description = 'Selecione os status.';
                }

            } else if (type === 'fundo') {
                if ($scope.selectedFundos && $scope.selectedFundos.length > 0) {
                    $scope.tootipFeedback.title = ' * Fundos Selecionados: '
                    $scope.selectedFundos.forEach(element => {
                        $scope.tootipFeedback.description += $scope.tootipFeedback.description.length === 0 ? element.label : ', ' + element.label;
                    });
                    $scope.tootipFeedback.description += '.';
                } else {
                    $scope.tootipFeedback.title = ' * Busca por Fundos: ';
                    $scope.tootipFeedback.description = 'Selecione os fundos.';
                }

            } else if (type === 'classificacao') {
                if ($scope.selectedClassificacoes && $scope.selectedClassificacoes.length > 0) {
                    $scope.tootipFeedback.title = ' * Classificações Selecionadas: '
                    $scope.selectedClassificacoes.forEach(element => {
                        $scope.tootipFeedback.description += $scope.tootipFeedback.description.length === 0 ? element.label : ', ' + element.label;
                    });
                    $scope.tootipFeedback.description += '.';
                } else {
                    $scope.tootipFeedback.title = ' * Busca por Classificações: ';
                    $scope.tootipFeedback.description = 'Selecione as classificações.';
                }

            } else if (type === 'classificacaoEspecial') {
                if ($scope.selectedClassificacoesEspecial && $scope.selectedClassificacoesEspecial.length > 0) {
                    $scope.tootipFeedback.title = ' * Classificações Selecionadas: '
                    $scope.selectedClassificacoesEspecial.forEach(element => {
                        $scope.tootipFeedback.description += $scope.tootipFeedback.description.length === 0 ? element.label : ', ' + element.label;
                    });
                    $scope.tootipFeedback.description += '.';
                } else {
                    $scope.tootipFeedback.title = ' * Busca por Classificações: ';
                    $scope.tootipFeedback.description = 'Selecione as classificações.';
                }

            } else if (type === 'descricao') {
                if ($scope.descricaoBusca && $scope.descricaoBusca.length > 0) {
                    $scope.tootipFeedback.title = ' * Busca textual: ';
                    $scope.tootipFeedback.description = $scope.descricaoBusca + '.';
                } else {
                    $scope.tootipFeedback.title = ' * Busca textual: ';
                    $scope.tootipFeedback.description = 'Busque pelo nome do funcionário, matrícula, CPF ou PIS/PASEP.';
                }

            } else if (type === 'dataInicial') {
                if ($scope.dataInicial) {
                    $scope.tootipFeedback.title = ' * Data Inicial Selecionada: ';
                    $scope.tootipFeedback.description = $filter('date')(new Date($scope.dataInicial), "dd/MM/yyyy") + '.';
                } else {
                    $scope.tootipFeedback.title = ' * Busca por Data Inicial: ';
                    $scope.tootipFeedback.description = 'Busque pela data inicial de cadastro.';
                }

            } else if (type === 'dataFinal') {
                if ($scope.dataFinal) {
                    $scope.tootipFeedback.title = ' * Data Final Selecionada: ';
                    $scope.tootipFeedback.description = $filter('date')(new Date($scope.dataFinal), "dd/MM/yyyy") + '.';
                } else {
                    $scope.tootipFeedback.title = ' * Busca por Data Final: ';
                    $scope.tootipFeedback.description = 'Busque pela data final de cadastro.';
                }

            }
        }

        $scope.loadList = function (report) {
            $rootScope.$broadcast('preloader:active');

            var aposentComAposentSemCompens = false;

            // Filtro de classificações
            var classificacoes = [];
            if ($scope.classificacao) {
                if ($scope.classificacao === 'APOSENTADORIA') {

                    // Para listar na aba de aposentado apenas certidões aposentadoria e aposentadoria sem compensação
                    aposentComAposentSemCompens = true;

                    if ($scope.selectedClassificacoesEspecial && $scope.selectedClassificacoesEspecial.length > 0)
                        $scope.selectedClassificacoesEspecial.forEach(element => {
                            classificacoes.push(element.value);
                        });
                } else {
                    classificacoes.push($scope.classificacao);
                }
            } else if ($scope.selectedClassificacoes && $scope.selectedClassificacoes.length > 0) {
                $scope.selectedClassificacoes.forEach(element => {
                    classificacoes.push(element.value);
                });
            }

            // Filtro de fundos
            var fundoList = [];
            if ($scope.selectedFundos && $scope.selectedFundos.length > 0) {
                $scope.selectedFundos.forEach(element => {
                    fundoList.push(element.value);
                });
            }

            // Filtro de status
            var statusList = [];
            if ($scope.selectedStatus && $scope.selectedStatus.length > 0) {
                $scope.selectedStatus.forEach(element => {
                    statusList.push(element.value);
                });
            }

            if (report && (!$scope.list.count || $scope.list.count === 0))
                report = false;

            var config = {
                params: {
                    page: report ? 0 : $scope.query.page - 1,
                    size: report ? $scope.list.count : $scope.query.limit,
                    order: $scope.query.order,
                    descricao: $scope.descricaoBusca,
                    classificacaoList: classificacoes,
                    fundoList: fundoList,
                    statusList: statusList,
                    dataInicialStr: $scope.dataInicial ? $scope.dataInicial.format('DD/MM/YYYY') : null,
                    dataFinalStr: $scope.dataFinal ? $scope.dataFinal.format('DD/MM/YYYY') : null,
                    aposentComAposentSemCompens: aposentComAposentSemCompens
                }
            };

            $scope.promise = GenericoService.GetAll('/certidoesCompensacao', config).then(
                function (response) {
                    if (response.status === 200) {

                        //Gerando lista concatenada de classificações
                        response.data.content.forEach(certidao => {
                            var classificacoesConcat = '';

                            certidao.classificacoes.forEach(classificacao => {
                                if (classificacoesConcat.length === 0)
                                    classificacoesConcat += $scope.classificacoesMap.get(classificacao);
                                else
                                    classificacoesConcat += ', ' + $scope.classificacoesMap.get(classificacao);
                            });
                            classificacoesConcat += '.';
                            certidao.classificacoesConcat = classificacoesConcat;
                        });

                        if (report)
                            GenericoService.ToDataURL($rootScope.defaultPath + configValue.logo,
                                function (dataURL) {
                                    pdfMake.createPdf(getDocDefinition(response.data.content, dataURL)).open()
                                });
                        else
                            $scope.list.data = response.data.content;

                        $scope.list.count = response.data.totalElements;

                        $rootScope.$broadcast('preloader:hide');
                    } else {
                        $scope.showSimpleToast("Não foi possível carregar os dados.")
                    }
                });
        }

        $scope.showConfirm = function (ev, idToDelete) {
            $scope.idToDelete = idToDelete;
            // Appending dialog to document.body to cover sidenav in docs app
            var confirm = $mdDialog.confirm()
                .title('Deseja confirmar a exclusão deste item?')
                .textContent('Esta ação não poderá ser desfeita.')
                // .ariaLabel('Lucky day')
                .targetEvent(ev)
                .ok('Sim')
                .cancel('Não');

            $mdDialog.show(confirm).then(function () {
                $scope.deleteItem();
            }, function () {
            });
        };


        $scope.deleteItem = function () {
            $rootScope.$broadcast('preloader:active');
            GenericoService.Delete('/certidaoCompensacao/' + $scope.idToDelete, function (response) {
                $rootScope.$broadcast('preloader:hide');
                if (response.status === 200) {
                    $scope.showSimpleToast(response.data.message);
                    $scope.loadList();
                }
            });
        }

        $scope.showConfirmRetificar = function (ev, idToRetificar) {
            $scope.idToRetificar = idToRetificar;
            // Appending dialog to document.body to cover sidenav in docs app
            var confirm = $mdDialog.confirm()
                .title('Deseja confirmar a retificação deste item?')
                .textContent('Esta ação não poderá ser desfeita.')
                // .ariaLabel('Lucky day')
                .targetEvent(ev)
                .ok('Sim')
                .cancel('Não');

            $mdDialog.show(confirm).then(function () {
                $scope.retificarItem();
            }, function () {
            });
        };

        $scope.retificarItem = function () {
            $rootScope.$broadcast('preloader:active');
            GenericoService.Create('/certidaoCompensacao/retificar', { id: $scope.idToRetificar }, function (response) {
                $rootScope.$broadcast('preloader:hide');
                if (response.status === 201 && response.data.success) {
                    $scope.showSimpleToast(response.data.message);
                    $location.path('/certidaoTempoContribuicaoCompensacao/formulario/' + response.data.obj.id);
                }
            });
        }

        $scope.showSimpleToast = function (textContent) {
            $mdToast.show(
                $mdToast.simple()
                    .textContent(textContent)
                    .position('top right')
                    .hideDelay(3000)
            );
        };

        $scope.showRelatorio = function () {
            $scope.loadList(true);
        }

        $scope.imprimirCertidao = function (id) {
            $rootScope.$broadcast('preloader:active');
            GenericoService.GetById('/certidaoCompensacao/' + id, function (response) {
                $rootScope.$broadcast('preloader:hide');
                if (response.status === 200) {
                    GenericoService.ToDataURL($rootScope.defaultPath + configValue.logoReport1,
                        function (dataURL) {
                            pdfMake.createPdf(getCertidaoDefinition(response.data, dataURL)).open()
                        });
                } else {
                    $scope.showSimpleToast("Declaração não encontrado na base");
                }
            });
        }

        $scope.showRelatorioStatus = function (ev, formatoRelatorio) {
            $scope.formatoRelatorio = formatoRelatorio;
            $mdDialog.show({
                scope: $scope,
                preserveScope: true,
                controller: ['$scope', '$mdDialog', '$mdSelect', function ($scope, $mdDialog, $mdSelect) {

                    $scope.selectedStatusReport = [];
                    $scope.selectedFundosReport = [];
                    $scope.dataInicialReport = null;
                    $scope.dataFinalReport = null;

                    $scope.cancel = function () {
                        $mdDialog.cancel();
                    };
                    $scope.hideSelects = function () {
                        $mdSelect.hide();
                    }
                    $scope.gerar = function () {

                        $scope.loadingDownload = true;
                        $rootScope.$broadcast('preloader:active');

                        // Filtro de status
                        var statusList = [];
                        if ($scope.selectedStatusReport && $scope.selectedStatusReport.length > 0) {
                            $scope.selectedStatusReport.forEach(element => {
                                statusList.push(element.value);
                            });
                        }

                        // Filtro de fundos
                        var fundoList = [];
                        if ($scope.selectedFundosReport && $scope.selectedFundosReport.length > 0) {
                            $scope.selectedFundosReport.forEach(element => {
                                fundoList.push(element.value);
                            });
                        }

                        var config = {
                            params: {
                                statusList: statusList,
                                fundoList: fundoList,
                                dataInicialStr: $scope.dataInicialReport ? $scope.dataInicialReport.format('DD/MM/YYYY') : null,
                                dataFinalStr: $scope.dataFinalReport ? $scope.dataFinalReport.format('DD/MM/YYYY') : null
                            },
                            responseType: 'arraybuffer'
                        };

                        RestService.Get('/certidoesCompensacao/relatorio/' + $scope.formatoRelatorio, config)
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
                        $mdDialog.cancel();
                    }
                }],
                templateUrl: 'app/page/certidaoCompensacao/relatorioStatus.tmpl.html',
                parent: angular.element(document.body),
                targetEvent: ev,
                clickOutsideToClose: true
            });
        };

        $scope.dialogAlterarStatus = function (item) {
            var useFullScreen = ($mdMedia('sm') || $mdMedia('xs'));
            $scope.certidaoStatus = item;
            $scope.statusDialog = '';

            $mdDialog.show({
                require: { container: 'certidaoCompensacaoCtrl' },
                scope: $scope,
                preserveScope: true,
                controller: ['$scope', '$q', function ($scope, $q) {

                    $scope.alterar = function () {
                        $scope.certidaoStatus.id;
                        $scope.certidaoStatus.statusAtual = $scope.statusDialog;

                        $scope.alterarStatus($scope.certidaoStatus);

                        $scope.cancel();
                    };

                    $scope.cancel = function () {
                        $mdDialog.cancel();
                    };
                }],
                templateUrl: 'app/page/certidaoCompensacao/dialogAlterarStatus.html',
                parent: angular.element(document.body),
                clickOutsideToClose: true,
                fullscreen: useFullScreen
            });

        }

        $scope.alterarStatus = function (certidao) {

            $rootScope.$broadcast('preloader:active');

            GenericoService.Update('/certidaoCompensacao/status', certidao, function (response) {
                $rootScope.$broadcast('preloader:hide');
                if (response.status === 201 && response.data.success) {
                    $scope.showSimpleToast(response.data.message);
                    $location.path('/certidaoTempoContribuicaoCompensacao/gestao');
                } else if (response.status === 400) {
                    $scope.showSimpleToast(response.data.message);
                    $location.path('certidaoCompensacao/formulario/' + $state.params.id)
                }
            });
        }

        /*
         * Recebe data e converte para valor com hora ou não
         * 
         * param data - recebe uma data tipo LocalDate
         * param comHora - recebe um valor boolean
         * 
         * */
        function convertDate(data, comHora) {
            var valor = moment(data);
            if (comHora) {
                return $filter('date')(new Date(valor), 'dd/MM/yyyy - HH:mm');
            } else {
                return $filter('date')(new Date(valor), 'dd/MM/yyyy');
            }
        }

        /*
         * Recebe duas datas e retorna o tempo entre elas
         * 
         * param dataInicio - recebe uma data tipo LocalDate
         * param dataFim - recebe uma data tipo LocalDate
         * 
         * */
        $scope.calularPeriodo = function (dataInicio, dataFim, formato) {
            dataInicio = moment(dataInicio);
            dataFim = moment(dataFim);

            dataFim = dataFim.add(1, 'days');
            var dias = dataFim.diff(dataInicio, 'days');

            return carregarAnosMesesDias(dias, formato);

        }

        function carregarAnosMesesDias(dias, formato) {
            var texto = '';

            if (dias >= 365) {
                var anos = dias / 365;
                anos = parseInt(anos);

                if (formato === 'numerico') {
                    texto += anos < 10 ? '0' + anos : anos;
                    texto += '-';
                } else if (formato === 'extenso') {
                    texto += getExtenso(anos);
                    texto += anos == 1 ? ' Ano, ' : ' Anos, ';
                } else {
                    texto += anos < 10 ? '0' + anos : anos;
                    texto += anos == 1 ? ' Ano, ' : ' Anos, ';
                }
                dias = dias % 365;
            } else {
                if (formato === 'numerico') {
                    texto += '00-';
                } else if (formato === 'extenso') {
                    texto += 'zero Anos, ';
                } else {
                    texto += '00 Anos, ';
                }
            }

            if (dias >= 30) {
                var meses = dias / 30;
                meses = parseInt(meses);
                if (formato === 'numerico') {
                    texto += meses < 10 ? '0' + meses : meses;
                    texto += '-';
                } else if (formato === 'extenso') {
                    texto += getExtenso(meses);
                    texto += meses == 1 ? ' mês e ' : ' meses e ';
                } else {
                    texto += meses < 10 ? '0' + meses : meses;
                    texto += meses == 1 ? ' mês e ' : ' meses e ';
                }
                dias = dias % 30;
            } else {
                if (formato === 'numerico') {
                    texto += '00-';
                } else if (formato === 'extenso') {
                    texto += 'zero meses e ';
                } else {
                    texto += '00 meses e ';
                }
            }

            if (dias > 0) {
                if (formato === 'numerico') {
                    texto += dias < 10 ? '0' + dias : dias;
                } else if (formato === 'extenso') {
                    texto += getExtenso(dias);
                    texto += dias == 1 ? ' dia' : ' dias';
                } else {
                    texto += dias < 10 ? '0' + dias : dias;
                    texto += dias == 1 ? ' dia' : ' dias';
                }

            } else {
                if (formato === 'numerico') {
                    texto += '00';
                } else if (formato === 'extenso') {
                    texto += 'zero dias';
                } else {
                    texto += '00 dias';
                }
            }

            return texto;
        }

        function calularPeriodoReport(dataInicio, dataFim, formato) {
            return $scope.calularPeriodo(dataInicio, dataFim, formato)
        }

        function somatorioPeriodos(lista, extensao) {
            var dias = 0;
            $scope.totalDias = 0;
            angular.forEach(lista, function (e) {

                var dataInicio = moment(e.periodoInicio);
                var dataFim = moment(e.periodoFim)

                dataFim = dataFim.add(1, 'days');
                dias += dataFim.diff(dataInicio, 'days');
                $scope.totalDias = dias;

            });

            return carregarAnosMesesDias(dias, extensao).toUpperCase();
        }

        function getDocDefinition(content, dataURL) {

            var docDefinition = {};
            docDefinition.content = [];

            var brasao = { image: dataURL, width: 70, alignment: 'center' };
            docDefinition.content.push(brasao);

            var orgao = { text: 'RH', alignment: 'center', margin: [0, 10], bold: true };
            docDefinition.content.push(orgao);

            var titulo = { text: 'Certidões de tempo de contribuição (Compensação)', alignment: 'center', margin: [0, 10] };
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

            var lista = {
                style: 'tableExample',
                table: {
                    headerRows: 1,
                    widths: ['*', 'auto', 'auto', 'auto', 'auto', 'auto'],
                    body: [
                        [{ text: 'Funcionário', fontSize: 10, fillColor: 'lightgray', style: 'tableHeader', alignment: 'center' },
                        { text: 'Matrícula', fontSize: 10, fillColor: 'lightgray', style: 'tableHeader', alignment: 'center' },
                        { text: 'PIS/PASEP', fontSize: 10, fillColor: 'lightgray', style: 'tableHeader', alignment: 'center' },
                        { text: 'Fundo', fontSize: 10, fillColor: 'lightgray', style: 'tableHeader', alignment: 'center' },
                        { text: 'Classificação', fontSize: 10, fillColor: 'lightgray', style: 'tableHeader', alignment: 'center' },
                        { text: 'Status', fontSize: 10, fillColor: 'lightgray', style: 'tableHeader', alignment: 'center' }]
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
            for (i = 0; i < content.length; i++) {
                var element = content[i];
                var body = [
                    { text: element.funcionario.nome, fontSize: 10, alignment: 'left' },
                    { text: element.funcionario.matricula, fontSize: 10, alignment: 'center' },
                    { text: element.funcionario.pisPasep, fontSize: 10, alignment: 'center' },
                    { text: element.fundo, fontSize: 10, alignment: 'center' },
                    { text: element.classificacao, fontSize: 10, alignment: 'center' },
                    { text: element.statusAtual, fontSize: 10, alignment: 'center' }
                ]
                lista.table.body.push(body)
            }
            docDefinition.content.push(lista);

            return docDefinition;

        }

        function getFiliacao(funcionario) {
            if (funcionario.nomeMae) {
                return funcionario.nomeMae;
            } else if (funcionario.nomePai) {
                return funcionario.nomePai;
            } else {
                return 'Não Declarado';
            }
        }

        function getLotacao(lotacao) {
            if (lotacao) {
                return lotacao.descricao.toUpperCase();
            } else {
                return '';
            }
        }

        function getFuncao(funcionario) {
            if (funcionario && funcionario.funcao) {
                return funcionario.funcao.nome.toUpperCase();
            } else {
                return '';
            }
        }

        function setPaginas(valor) {
            $scope.quantidadePaginas = valor;
        }


        String.prototype.extenso = function (c) {
            var ex = [
                ["zero", "um", "dois", "três", "quatro", "cinco", "seis", "sete", "oito", "nove", "dez", "onze", "doze", "treze", "quatorze", "quinze", "dezesseis", "dezessete", "dezoito", "dezenove"],
                ["dez", "vinte", "trinta", "quarenta", "cinqüenta", "sessenta", "setenta", "oitenta", "noventa"],
                ["cem", "cento", "duzentos", "trezentos", "quatrocentos", "quinhentos", "seiscentos", "setecentos", "oitocentos", "novecentos"],
                ["mil", "milhão", "bilhão", "trilhão", "quadrilhão", "quintilhão", "sextilhão", "setilhão", "octilhão", "nonilhão", "decilhão", "undecilhão", "dodecilhão", "tredecilhão", "quatrodecilhão", "quindecilhão", "sedecilhão", "septendecilhão", "octencilhão", "nonencilhão"]
            ];
            var a, n, v, i, n = this.replace(c ? /[^,\d]/g : /\D/g, "").split(","), e = " e ", $ = "real", d = "centavo", sl;
            for (var f = n.length - 1, l, j = -1, r = [], s = [], t = ""; ++j <= f; s = []) {
                j && (n[j] = (("." + n[j]) * 1).toFixed(2).slice(2));
                if (!(a = (v = n[j]).slice((l = v.length) % 3).match(/\d{3}/g), v = l % 3 ? [v.slice(0, l % 3)] : [], v = a ? v.concat(a) : v).length) continue;
                for (a = -1, l = v.length; ++a < l; t = "") {
                    if (!(i = v[a] * 1)) continue;
                    i % 100 < 20 && (t += ex[0][i % 100]) ||
                        i % 100 + 1 && (t += ex[1][(i % 100 / 10 >> 0) - 1] + (i % 10 ? e + ex[0][i % 10] : ""));
                    s.push((i < 100 ? t : !(i % 100) ? ex[2][i == 100 ? 0 : i / 100 >> 0] : (ex[2][i / 100 >> 0] + e + t)) +
                        ((t = l - a - 2) > -1 ? " " + (i > 1 && t > 0 ? ex[3][t].replace("ão", "ões") : ex[3][t]) : ""));
                }
                a = ((sl = s.length) > 1 ? (a = s.pop(), s.join(" ") + e + a) : s.join("") || ((!j && (n[j + 1] * 1 > 0) || r.length) ? "" : ex[0][0]));
                a && r.push(a + (c ? (" " + (v.join("") * 1 > 1 ? j ? d + "s" : (/0{6,}$/.test(n[0]) ? "de " : "") + $.replace("l", "is") : j ? d : $)) : ""));
            }
            return r.join(e);
        }

        function getExtenso(valor) {
            return String(valor).extenso(false).toUpperCase();
        }
        /*
         * Montagem Declaração para impressão
         * */
        function getCertidaoDefinition(content, dataURL) {

            var docDefinition = {
                pageSize: 'A4',
                pageOrientation: 'portrait',
                pageMargins: [40, 40, 40, 40],
                footer: { text: 'Av. Professor Alfredo de Castro, Qd - C, Lt - 16/18\n Setor Oeste - Goiânia-GO - CEP: 74110-030\nTel: 55 62 3524-5800 - ipsm@goiania.go.gov.br', style: 'footer' },
                info: {
                    title: 'Certidão de tempo de contribuição'
                }
            };

            docDefinition.content = [];

            var titulo = {
                style: 'tableTop',
                table: {
                    widths: ['*', 'auto'],
                    body: [
                        [
                            {
                                image: dataURL, width: 140, alignment: 'left',
                                style: 'logo'
                            },
                            {
                                text: 'Instituto de Previdência dos Servidores do Município de Goiânia',
                                style: 'tituloSupDirLogo'
                            }
                        ]
                    ]
                },
                layout: 'noBorders'
            };

            docDefinition.content.push(titulo);

            docDefinition.content.push({ text: 'CERTIDÃO DE TEMPO DE CONTRIBUIÇÃO', style: 'subtituloSemMargem', alignment: 'center', decoration: 'underline' });
            if (content.tipoCertidaoCompensacao === 'RETIFICACAO')
                docDefinition.content.push({ text: 'NÚMERO DA CERTIDÃO Nº ' + content.numero + '/' + content.ano + ' - RETIFICAÇÃO Nº ' + content.numeroRetificacao, style: 'subtituloSemMargem', alignment: 'center' });
            else
                docDefinition.content.push({ text: 'NÚMERO DA CERTIDÃO Nº ' + content.numero + '/' + content.ano, style: 'subtituloSemMargem', alignment: 'center' });
            docDefinition.content.push({ text: 'INSTITUTO DE PREVIDÊNCIA DE GOIÂNIA - GOIANIAPREV', style: 'subtituloUltimo', alignment: 'center' });
            if (content.tipoCertidaoCompensacao === 'RASCUNHO')
                docDefinition.content.push({ text: 'RASCUNHO', style: 'subtituloUltimo', alignment: 'center' });


            var dadosFuncionais = {
                style: 'tableTop',
                table: {
                    widths: ['*', 'auto', 'auto', 'auto'],
                    headerRows: 1,
                    body: [
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
            };

            if (content.funcionario) {
                dadosFuncionais.table.body.push([{ text: [{ text: 'ÓRGÃO EXPEDIDOR: ', bold: true }, 'GOIANIAPREV'], colSpan: 4, alignment: 'left' }, {}, {}, {}])
                dadosFuncionais.table.body.push([{ text: [{ text: 'NOME DO REQUERENTE: ', bold: true }, content.funcionario.nome.toUpperCase()], colSpan: 4, alignment: 'left' }, {}, {}, {}])
                dadosFuncionais.table.body.push([{ text: [{ text: 'FILIAÇÃO: ', bold: true }, getFiliacao(content.funcionario).toUpperCase()], colSpan: 4, alignment: 'left' }, {}, {}, {}])
                dadosFuncionais.table.body.push([{ text: 'DATA DE NASCIMENTO', colSpan: 1, alignment: 'center', bold: true }, { text: 'CPF', colSpan: 1, alignment: 'center', bold: true }, { text: 'ÓRGÃO DE LOTAÇÃO', colSpan: 1, alignment: 'center', bold: true }, { text: 'MATRÍCULA', colSpan: 1, alignment: 'center', bold: true }])
                dadosFuncionais.table.body.push([{ text: convertDate(content.funcionario.dataNascimento, false), colSpan: 1, alignment: 'center' }, { text: $filter('cpf')(content.funcionario.cpf), colSpan: 1, alignment: 'center' }, { text: getLotacao(content.funcionario.lotacao), colSpan: 1, alignment: 'center' }, { text: content.funcionario.matricula, colSpan: 1, alignment: 'center' }])
            } else {
                dadosFuncionais.table.body.push([{ text: 'Observação: Dados funcionais indisponíveis. ', fontSize: 10, alignment: 'left', bold: false, colSpan: 4 }, {}, {}, {}]);
            }

            docDefinition.content.push(dadosFuncionais);

            docDefinition.content.push({ text: '', style: 'subtitulo' });

            var tempoContribuicao = {
                style: 'tableTop',
                table: {
                    widths: ['*', '*', '*', 'auto'],
                    headerRows: 1,
                    body: [
                        [
                            { text: [{ text: 'TEMPO DE CONTRIBUIÇÃO', bold: true }], colSpan: 4, fillColor: 'lightgray', alignment: 'center' },
                            {},
                            {},
                            {}
                        ],
                        [
                            { text: [{ text: 'EMPREGADOR: ', bold: true }, 'PREFEITURA MUNICIPAL DE GOIÂNIA'], colSpan: 3, alignment: 'left' },
                            {},
                            {},
                            { text: [{ text: 'CNPJ: ', bold: true }, '01.612.092/0001-23'], alignment: 'left' },
                        ],
                        [
                            {
                                text: [
                                    { text: 'ENDEREÇO: ', bold: true },
                                    { text: 'AVENIDA DO CERRADO, Nº 999 PARK LOZANDES, GOIÂNIA', fontSize: 10 }
                                ],
                                colSpan: 3,
                                alignment: 'left'
                            },
                            {},
                            {},
                            {
                                text: [
                                    { text: 'CEP: ', bold: true },
                                    '74884–092'],
                                alignment: 'left'
                            }
                        ],
                        [
                            {
                                text: 'DOCUMENTO: ',
                                bold: true
                            },
                            {
                                text: [
                                    { text: 'CTPS: ', bold: true },
                                    content.funcionario && content.funcionario.numeroCtps ? content.funcionario.numeroCtps : 'Não Declarado'
                                ]
                            },
                            {
                                text: [
                                    { text: 'SÉRIE: ', bold: true },
                                    content.funcionario && content.funcionario.serieCtps ? content.funcionario.serieCtps : 'Não Declarado'
                                ]
                            },
                            {
                                text: [
                                    { text: 'PASEP: ', bold: true },
                                    content.funcionario && content.funcionario.pisPasep ? content.funcionario.pisPasep : 'Não Declarado'
                                ]
                            }
                        ],
                        [
                            {
                                text: [{ text: 'FUNÇÃO: ', bold: true }, getFuncao(content.funcionario)],
                                colSpan: 4,
                                alignment: 'left'
                            },
                            {},
                            {},
                            {}
                        ],
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
            };

            docDefinition.content.push(tempoContribuicao);

            docDefinition.content.push({ text: '', style: 'subtitulo' });

            var periodos = {
                style: 'tableTop',
                table: {
                    widths: ['*', 'auto'],
                    headerRows: 1,
                    body: [
                        [
                            { text: 'Período', fontSize: 10, style: 'tableHeader', alignment: 'center' },
                            { text: 'Tempo Líquido', fontSize: 10, style: 'tableHeader', alignment: 'center' }]
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
            };

            content.periodos = $filter('orderBy')(content.periodos, 'periodoInicio');

            angular.forEach(content.periodos, function (e) {
                periodos.table.body.push([{ text: convertDate(e.periodoInicio, false) + ' a ' + convertDate(e.periodoFim, false), fontSize: 10, alignment: 'center' },
                { text: calularPeriodoReport(e.periodoInicio, e.periodoFim, 'numerico'), fontSize: 10, alignment: 'center' }]);
            });

            periodos.table.body.unshift([{ text: [{ text: 'PERÍODOS', bold: true }], colSpan: 2, fillColor: 'lightgray', alignment: 'center' }, {}]);

            periodos.table.body.push(
                [
                    {
                        text: [{
                            text: 'OBSERVAÇÕES: PERÍODO COMPREENDIDO AO REGIME GERAL DE PREVIDÊNCIA SOCIAL '
                                + '- DISTINÇÃO DA CONTRIBUIÇÃO PREVIDENCIÁRIA - INSS FONTE DE INFORMAÇÕES: '
                                + 'PROCESSO DE APOSENTADORIA: Nº ' + content.processo ? content.processo : 'Não Disponível' + ' – DOSSIÊ', bold: true
                        }],
                        colSpan: 2,
                        alignment: 'justify',
                        fontSize: 10
                    }, {}
                ]
            );

            var data = new Date();
            var dia = $filter('date')(data, 'dd');
            var mes = $filter('date')(data, 'MMMM');
            var ano = $filter('date')(data, 'yyyy');

            periodos.table.body.push(
                [
                    {
                        text: [
                            {
                                text: 'ESTA CERTIDÃO NÃO CONTÉM EMENDAS, NEM RASURAS, FOI EMITIDA DE ACORDO COM O PROCESSO ACIMA CITADO. CERTIFICO QUE O(A) INTERESSADO(A) CONTA COM ' + somatorioPeriodos(content.periodos) + ' (' + somatorioPeriodos(content.periodos, 'extenso') + ')' +
                                    ' CORRESPONDENDO A ' + $scope.totalDias + ' (' + getExtenso($scope.totalDias) + ') DIAS DE EXERCÍCIO VINCULADO AO REGIME GERAL DE PREVIDÊNCIA SOCIAL CALCULADO CONFORME AS NORMAS LEGAIS DO INSS, ' +
                                    'PARA FINS DE COMPENSAÇÃO PREVIDENCIÁRIA ENTRE O REGIME GERAL DE PREVIDÊNCIA SOCIAL E OS REGIMES PRÓPRIOS DE PREVIDÊNCIA SOCIAL DOS SERVIDORES PÚBLICOS DE ACORDO COM, INCISO V, ART. 10 DO DECRETO N.º '
                            },
                            { text: '3.112/99.\n\n', bold: true },
                            {
                                text: 'DECLARO QUE NO PERÍODO CERTIFICADO NÃO FOI INCLUÍDO TEMPO DE REGIME ESPECIAL DE CONTRIBUIÇÃO EM QUE TINHA GARANTIDO APENAS OS BENEFÍCIOS DE FAMÍLIA, NA FORMA DO PARÁGRAFO ÚNICO DO ART. 3º DA LEI Nº 3.808, DE 26 DE AGOSTO DE 1960, ' +
                                    'CONFORME ESTABELECIDO NO 2º DO ART. 3º DA PORTARIA MPAS Nº 6.209 DE 16 DE DEZEMBRO DE 1999, SOB PENA DE APLICAÇÃO DAS PENALIDADES PREVISTAS NO ART. 299 DO CÓDIGO PENAL.' +
                                    '\n\nPREFEITURA MUNICIPAL DE GOIÂNIA, AOS ' + dia + ' DO MÊS DE ' + mes.toUpperCase() + ' DO ANO DE ' + ano + '.'
                            }
                        ],
                        colSpan: 2,
                        alignment: 'justify',
                        fontSize: 8
                    }, {}
                ]
            );

            docDefinition.content.push(periodos);

            docDefinition.content.push({
                text: '', alignment: 'justify', fontSize: 8
            });


            var primeiraLinha = {
                style: 'coluna',
                columns: []
            }
            var segundaLinha = {
                style: 'coluna',
                columns: []
            }
            var terceiraLinha = {
                style: 'coluna',
                columns: []
            }

            angular.forEach(content.assinaturas, function (e, key) {
                if (key < 2) {
                    primeiraLinha.columns.push({ text: ['______________________________________________\n', e.funcionario.nome.toUpperCase() + '\n', { text: e.funcao.toUpperCase() + '\n' }, { text: 'MATRÍCULA: ' + e.funcionario.matricula }], style: 'responsavel', alignment: 'center' });
                } else if (key < 4) {
                    segundaLinha.columns.push({ text: ['______________________________________________\n', e.funcionario.nome.toUpperCase() + '\n', { text: e.funcao.toUpperCase() + '\n' }, { text: 'MATRÍCULA: ' + e.funcionario.matricula }], style: 'responsavel', alignment: 'center' });
                } else {
                    terceiraLinha.columns.push({ text: ['______________________________________________\n', e.funcionario.nome.toUpperCase() + '\n', { text: e.funcao.toUpperCase() + '\n' }, { text: 'MATRÍCULA: ' + e.funcionario.matricula }], style: 'responsavel', alignment: 'center' });
                }
            });

            docDefinition.content.push(primeiraLinha);

            docDefinition.content.push(segundaLinha);

            docDefinition.content.push(terceiraLinha);

            docDefinition.styles = {
                header: {
                    fontSize: 18,
                    bold: true,
                    margin: [0, 0, 0, 10]
                },
                subheader: {
                    fontSize: 28,
                    bold: false,
                    margin: [0, 10, 0, 5]
                },
                subheaderName: {
                    fontSize: 28,
                    bold: true,
                    italics: true,
                    margin: [0, 10, 0, 5]
                },
                tableTop: {
                    margin: [0, 0, 0, 0]
                },
                tableMiddle: {
                    margin: [0, 50, 0, 50]
                },
                tituloSupDirLogo: {
                    bold: true,
                    fontSize: 10,
                    decoration: 'underline',
                    color: 'grey',
                    margin: [0, 0, 0, 0],
                    alignment: 'right'
                },
                tableBottom: {
                    margin: [0, 60, 0, 0],
                    alignment: 'center'
                },
                tableHeader: {
                    bold: true,
                    color: 'black'
                },
                responsavel: {
                    bold: true,
                    italics: true,
                    color: 'black',
                    fontSize: 7
                },
                responsavelLabel: {
                    bold: false,
                    italics: true,
                    color: 'black'
                },
                tituloLogo: {
                    bold: true,
                    margin: [0, 0, 0, 0],
                    alignment: 'right',
                    decoration: 'underline',
                    color: 'grey'
                },
                titulo: {
                    bold: true,
                    fontSize: 14,
                    margin: [100, 20, 0, 0]
                },
                subtitulo: {
                    bold: true,
                    alignment: 'left',
                    margin: [0, 10, 0, 5]
                },
                subtituloSemMargem: {
                    bold: true,
                    alignment: 'center',
                    margin: [0, 0, 0, 0]
                },
                subtituloUltimo: {
                    bold: true,
                    alignment: 'center',
                    margin: [0, 0, 0, 10]
                },
                footer: {
                    fontSize: 8,
                    alignment: 'left',
                    height: 200,
                    margin: [40, 0, 0, 10]
                },
                coluna: {
                    alignment: 'center',
                    margin: [0, 20, 0, 0]
                },
                observacao: {
                    alignment: 'justify',
                    margin: [0, 10, 0, 10]
                }
            };

            docDefinition.defaultStyle = {
                alignment: 'justify',
                fontSize: 11
            };
            return docDefinition;
        }


    }

})();
