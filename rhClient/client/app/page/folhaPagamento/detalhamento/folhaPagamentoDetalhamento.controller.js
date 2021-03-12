(function () {
    'use strict';

    angular.module('app.page')
        .controller('folhaPagamentoDetalhamentoCtrl', ['$state', '$sce', '$filter', '$location', 'configValue', '$scope', '$mdToast', '$mdDialog', '$rootScope', 'GenericoService', 'EnumService', 'RestService', folhaPagamentoDetalhamentoCtrl]);

    function folhaPagamentoDetalhamentoCtrl($state, $sce, $filter, $location, configValue, $scope, $mdToast, $mdDialog, $rootScope, GenericoService, EnumService, RestService) {

        $scope.selected = [];

        if ($state.params && $state.params.id) {
            $rootScope.$broadcast('preloader:active');
            GenericoService.GetById('/folhaPagamento/' + $state.params.id, function (response) {
                $rootScope.$broadcast('preloader:hide');
                if (response.status === 200) {
                    $scope.folha = response.data;
                    $scope.loadLotacoes();
                    $scope.loadList();
                    $scope.listaSituacoesFuncionais();
                } else {
                    $scope.showSimpleToast("Folha não encontrada na base");
                }
            });
        }

        // Busca dos Anos das Comptetências Fechadas
        $scope.listaSituacoesFuncionais = function () {
            $rootScope.$broadcast('preloader:active');
            RestService.Get('/contracheque/listaSituacoesFuncionais/porFolha/' + $scope.folha.id)
                .then(function (response) {
                    if (response.status === 200 && response.data) {
                        $scope.situacaoFuncionalList = response.data.obj;
                    } else if (response.status === 200 && !response.data) {
                        $scope.showSimpleToast("Não existem anos de competência disponíveis.");
                    }
                }, function errorCallback(response) {
                    if (response.status === 400) {
                        $scope.showSimpleToast(response.data.message);
                    }
                });
        }

        EnumService.Get("SituacaoProcessamentoFolha").then(function (dados) {
            $scope.situacaoList = dados;
        });

        $scope.detalhes = false;

        if ($state.params && $state.params.detalhes) {
            $scope.detalhes = true;
        }

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

        $scope.loadList = function () {
            $rootScope.$broadcast('preloader:active');
            var config = {
                params: {
                    folhaId: $scope.folha.id,
                    nome: $scope.filtro && $scope.filtro.nome ? $scope.filtro.nome : null,
                    lotacao: $scope.filtro && $scope.filtro.lotacao ? $scope.filtro.lotacao : null,
                    situacao: $scope.filtro && $scope.filtro.situacao ? $scope.filtro.situacao : null,
                    situacaoFuncional: $scope.filtro && $scope.filtro.situacaoFuncional ? $scope.filtro.situacaoFuncional : null,
                    page: $scope.query.page - 1,
                    size: $scope.query.limit,
                    order: $scope.query.order
                }
            };

            $scope.promise = GenericoService.GetAll('/contracheque/porFolha', config).then(
                function (response) {
                    $rootScope.$broadcast('preloader:hide');
                    if (response.status === 200) {
                        $scope.list.count = response.data.totalElements;
                        $scope.list.data = response.data.content;
                    }
                });
        }

        $scope.showDeleteContracheque = function (ev, item) {
            $scope.idToDelete = item.id;
            // Appending dialog to document.body to cover sidenav in docs app
            var confirm = $mdDialog.confirm()
                .title('Deseja confirmar a exclusão deste item?')
                .textContent('Esta ação não poderá ser desfeita.')
                // .ariaLabel('Lucky day')
                .targetEvent(ev)
                .ok('Sim')
                .cancel('Não');

            $mdDialog.show(confirm).then(function () {
                $scope.deleteContracheque();
            }, function () { });
        };

        $scope.deleteContracheque = function () {
            $rootScope.$broadcast('preloader:active');
            GenericoService.Delete('/contracheque/' + $scope.idToDelete, function (response) {
                $rootScope.$broadcast('preloader:hide');
                if (response.status === 200) {
                    $scope.showSimpleToast(response.data.message);
                    $scope.loadList();
                }
            });
        };


        $scope.trustAsHtml = function (string) {
            return $sce.trustAsHtml(string);
        };

        $scope.showContrachequeFuncionario = function (ev, item) {

            $rootScope.$broadcast('preloader:active');

            GenericoService.GetById('/lancamento/showDetalheFuncionarioFuncionario/' + item.id, function (response) {
                $rootScope.$broadcast('preloader:hide');
                if (response.status === 200) {
                    $scope.fPagContracheque = response.data;

                    $scope.fPagContracheque.lancamentosVantagens = $filter('orderBy')($scope.fPagContracheque.lancamentosVantagens, 'verba.codigo');
                    $scope.fPagContracheque.lancamentosDescontos = $filter('orderBy')($scope.fPagContracheque.lancamentosDescontos, 'verba.codigo');

                    $scope.fPagContracheque.lancamentosOutros = $scope.fPagContracheque.lancamentosOutros.filter(function (e) {
                        if (e.verba.descricaoVerba === 'BASE PREV')
                            $scope.basePrev = e.valor;
                        if (e.verba.descricaoVerba === 'BASE IRRF')
                            $scope.baseIrrf = e.valor;
                    });
                    $scope.fPagContracheque.lancamentosVantagens.filter(function (e) {
                        if (e.verba.codigo === '101')
                            $scope.salarioBase = e.valor;
                    });


                    $mdDialog.show({
                        scope: $scope,
                        preserveScope: true,
                        controller: ['$scope', '$mdDialog', function ($scope, $mdDialog) {
                            $scope.hide = function () {
                                $mdDialog.hide();
                            };

                            $scope.cancel = function () {
                                $mdDialog.cancel();
                            };

                            // $scope.removerVerbaFuncionario = function (evento, idFolhaPagamentoFuncVerba, idFolha, idFuncionario) {

                            //     let funcionarios = [];
                            //     funcionarios.push(idFuncionario);

                            //     let body = {
                            //         'folhaPagamentoId': idFolha,
                            //         'verbas': [],
                            //         'funcionariosId': funcionarios,
                            //         'folhaPagamentoFuncionarioVerbaId': idFolhaPagamentoFuncVerba
                            //     };

                            //     console.log(body);

                            //     GenericoService.Create('/lancamento/removerVerbaFuncionario', body, function (response) {
                            //         $rootScope.$broadcast('preloader:hide');
                            //         if (response.status === 201 && response.data.success) {
                            //             $scope.showSimpleToast(response.data.message);
                            //             $state.reload();

                            //         } else if (response.status === 400) {
                            //             $scope.showSimpleToast(response.data.message);
                            //         }
                            //     });
                            // }

                            $scope.showSimpleToast = function (textContent) {
                                $mdToast.show(
                                    $mdToast.simple()
                                        .textContent(textContent)
                                        .position('top right')
                                        .hideDelay(4000)
                                );
                            };


                        }],
                        templateUrl: 'app/page/folhaPagamento/detalhamento/folhaPagamentoDetalhamento.contracheque.tmpl.html',
                        parent: angular.element(document.body),
                        targetEvent: ev,
                        clickOutsideToClose: true,
                        fullscreen: true // Only for -xs, -sm breakpoints.
                    });

                } else {
                    $scope.showSimpleToast("Folha não encontrada na base");
                }
            });


        };

        $scope.loadingDownload = false;
        $scope.downloadAllContrachequesFilial = function (ev, folhaId) {
            if (folhaId) {
                $scope.loadingDownload = true;
                $rootScope.$broadcast('preloader:active');
                var config = {
                    params: {
                        folhaId: $scope.folha.id,
                        nome: $scope.filtro && $scope.filtro.nome ? $scope.filtro.nome : null,
                        lotacao: $scope.filtro && $scope.filtro.lotacao ? $scope.filtro.lotacao : null,
                        situacao: $scope.filtro && $scope.filtro.situacao ? $scope.filtro.situacao : null,
                        situacaoFuncional: $scope.filtro && $scope.filtro.situacaoFuncional ? $scope.filtro.situacaoFuncional : null
                    },
                    responseType: 'arraybuffer'
                };
                GenericoService.GetAll('/contracheque/downloadFile', config).then(function (response) {
                    $scope.loadingDownload = false;
                    var file = new Blob([response.data], { type: 'application/pdf' });
                    var fileURL = URL.createObjectURL(file);
                    $rootScope.$broadcast('preloader:hide');
                    window.open(fileURL);
                });
            }
        }

        $scope.downloadContrachequeFuncionario = function (ev, item) {

            if (item.id) {
                $rootScope.$broadcast('preloader:active');
                GenericoService.GetById('/lancamento/showDetalheFuncionarioFuncionario/' + item.id, function (response) {
                    $rootScope.$broadcast('preloader:hide');
                    if (response.status === 200) {

                        $scope.fPagContracheque = response.data;

                        $scope.fPagContracheque.lancamentosVantagens = $filter('orderBy')($scope.fPagContracheque.lancamentosVantagens, 'verba.codigo');
                        $scope.fPagContracheque.lancamentosDescontos = $filter('orderBy')($scope.fPagContracheque.lancamentosDescontos, 'verba.codigo');

                        $scope.fPagContracheque.lancamentosOutros = $scope.fPagContracheque.lancamentosOutros.filter(function (e) {
                            if (e.verba.descricaoVerba === 'BASE PREV')
                                $scope.fPagContracheque.basePrev = e.valor;
                            if (e.verba.descricaoVerba === 'BASE IRRF')
                                $scope.fPagContracheque.baseIrrf = e.valor;
                        });
                        $scope.fPagContracheque.lancamentosVantagens.filter(function (e) {
                            if (e.verba.codigo === '101')
                                $scope.fPagContracheque.salarioBase = e.valor;
                        });
                        GenericoService.ToDataURL($rootScope.defaultPath + configValue.logo,
                            function (dataURL) {
                                pdfMake.createPdf(getDocDefinition($scope.fPagContracheque, dataURL)).open()
                            }
                        );
                    } else {
                        $scope.showSimpleToast("Folha não encontrada na base");
                    }
                });
            }
        }

        $scope.limpaFiltro = function () {
            delete $scope.filtro;
            $scope.loadList();
        }

        function getDocDefinition(response, dataURL) {
            var docDefinition = {};
            docDefinition.content = [];

            var header = {
                alignment: 'left',
                columns: [
                    {
                        image: dataURL, width: 70, alignment: 'left'
                    },
                    {
                        text: 'INST.DE PREV.DOS SERV. DO MUNIC.DE GOIANIA- GOIANIAPREV \n 100500 - CAMARA MUNICIPAL - FUNPREV \n  31.711.157/0000-59',
                        margin: [15, 15, 0, 10],
                        fontSize: 11
                    }
                ]
            };

            docDefinition.content.push(header);

            var titulo = { text: 'Demonstrativo de Pagamento', alignment: 'right', margin: [0, 10], fontSize: 11 };
            docDefinition.content.push(titulo);

            var dadosFuncionario = {
                style: 'tableExample',
                color: '#444',
                table: {
                    widths: ['*', '*', '*', '*', 'auto'],
                    // keepWithHeaderRows: 1,
                    body: [
                        [
                            {
                                colSpan: 4, text: [
                                    { text: '1. Nome do Servidor\n', fontSize: 8, bold: true, alignment: 'left' },
                                    { text: response.nome, fontSize: 9, alignment: 'right' }
                                ]
                            },
                            '',
                            '',
                            '',
                            {
                                text: [
                                    { text: '2. Matrícula - Dig\n', fontSize: 8, bold: true, alignment: 'left' },
                                    { text: response.matricula, fontSize: 9, alignment: 'right' }
                                ]
                            }
                        ],
                        [
                            {
                                colSpan: 2, text: [
                                    { text: '4. Local de Trabalho\n', fontSize: 8, bold: true, alignment: 'left' },
                                    { text: response.lotacao, fontSize: 9, alignment: 'right' }
                                ]
                            },
                            '',
                            {
                                colSpan: 2, text: [
                                    { text: '5. Município\n', fontSize: 8, bold: true, alignment: 'left' },
                                    { text: response.municipio, fontSize: 9, alignment: 'right' }
                                ]
                            },
                            '',
                            {
                                text: [
                                    { text: '6. Data Admissão\n', fontSize: 8, bold: true, alignment: 'left' },
                                    { text: convertDate(response.dataAdmissao), fontSize: 9, alignment: 'right' }
                                ]
                            },

                        ],
                        [
                            {
                                colSpan: 4, text: [
                                    { text: '7. Cargo Efetivo\n', fontSize: 8, bold: true, alignment: 'left' },
                                    { text: response.cargoEfetivo, fontSize: 9, alignment: 'right' }
                                ]
                            },
                            '',
                            '',
                            '',
                            {
                                text: [
                                    { text: '8. Data Nascimento\n', fontSize: 8, bold: true, alignment: 'left' },
                                    { text: convertDate(response.dataNascimento), fontSize: 9, alignment: 'right' }
                                ]
                            },
                        ],
                        [
                            {
                                colSpan: 2, text: [
                                    { text: '12. Vínculo\n', fontSize: 8, bold: true, alignment: 'left' },
                                    { text: response.vinculo, fontSize: 9, alignment: 'right' }
                                ]
                            },
                            '',
                            {
                                text: [
                                    { text: '13. Situação\n', fontSize: 8, bold: true, alignment: 'left' },
                                    { text: response.tipoSituacaoFuncional, fontSize: 9, alignment: 'right' }
                                ]
                            },
                            {
                                text: [
                                    { text: '14. Identidade\n', fontSize: 8, bold: true, alignment: 'left' },
                                    { text: response.identidade, fontSize: 9, alignment: 'right' }
                                ]
                            },
                            {
                                text: [
                                    { text: '15. CPF\n', fontSize: 8, bold: true, alignment: 'left' },
                                    { text: response.cpf, fontSize: 9, alignment: 'right' }
                                ]
                            },
                        ],
                        [
                            {
                                text: [
                                    { text: '16. Órgão Pagador\n', fontSize: 8, bold: true, alignment: 'left' },
                                    { text: response.orgaoPagador, fontSize: 9, alignment: 'right' }
                                ]
                            },
                            {
                                text: [
                                    { text: '17. Dep. S.F\n', fontSize: 8, bold: true, alignment: 'left' },
                                    { text: response.depSf, fontSize: 9, alignment: 'right' }
                                ]
                            },
                            {
                                text: [
                                    { text: '18. Dep. I.R\n', fontSize: 8, bold: true, alignment: 'left' },
                                    { text: response.depIr, fontSize: 9, alignment: 'right' }
                                ]
                            },
                            {
                                text: [
                                    { text: '19. Carga Horária\n', fontSize: 8, bold: true, alignment: 'left' },
                                    { text: response.cargaHoraria, fontSize: 9, alignment: 'right' }
                                ]
                            },
                            {
                                text: [
                                    { text: '20. Competência\n', fontSize: 8, bold: true, alignment: 'left' },
                                    { text: response.folhaPagamento.folhaCompetencia.mesCompetencia + '/' + response.folhaPagamento.folhaCompetencia.anoCompetencia, fontSize: 9, alignment: 'right' }
                                ]
                            },
                        ],
                    ]
                }
            };

            docDefinition.content.push(dadosFuncionario);

            var listaTitulo = {
                style: 'tableExample',
                table: {
                    widths: ["auto", 187, "auto", 93, 93],
                    body: [
                        [
                            { text: 'Codigo', bold: true, fillColor: '#f2f4f5' },
                            { text: 'Descrição', bold: true, fillColor: '#f2f4f5' },
                            { text: 'Referência', bold: true, fillColor: '#f2f4f5' },
                            { text: 'Proventos', bold: true, fillColor: '#f2f4f5' },
                            { text: 'Descontos', bold: true, fillColor: '#f2f4f5' },
                        ]
                    ]
                },
                margin: [0, 15, 0, 10],
                // layout: 'noBorders'
            };
            var i;
            for (i = 0; i < response.lancamentosVantagens.length; i++) {
                var element = response.lancamentosVantagens[i];
                var body = [
                    { text: element.verba.codigo == null ? '' : element.verba.codigo, fontSize: 9, alignment: 'center', border: [true, false, true, false] },
                    { text: element.verba.descricaoVerba == null ? '' : element.verba.descricaoVerba, fontSize: 9, border: [true, false, true, false] },
                    { text: element.valorReferencia == null ? '' : element.valorReferencia, fontSize: 9, alignment: 'center', border: [true, false, true, false] },
                    { text: element.valor == null ? '' : element.valor.toString(), fontSize: 9, border: [true, false, true, false] },
                    { text: '', fontSize: 9, border: [true, false, true, false] }
                ];
                listaTitulo.table.body.push(body);
            }

            var j;
            for (j = 0; j < response.lancamentosDescontos.length; j++) {
                var element = response.lancamentosDescontos[j];
                var body = [
                    { text: element.verba.codigo == null ? '' : element.verba.codigo, fontSize: 9, alignment: 'center', border: [true, false, true, false] },
                    { text: element.verba.descricaoVerba == null ? '' : element.verba.descricaoVerba, fontSize: 9, border: [true, false, true, false] },
                    { text: element.valorReferencia == null ? '' : element.valorReferencia, fontSize: 9, alignment: 'center', border: [true, false, true, false] },
                    { text: '', fontSize: 9, border: [true, false, true, false] },
                    { text: element.valor == null ? '' : element.valor.toString(), fontSize: 9, border: [true, false, true, false] }
                ];
                listaTitulo.table.body.push(body);
            }

            var whiteLine = [{ text: '', border: [true, false, true, true] }, { text: '', border: [true, false, true, true] }, { text: '', border: [true, false, true, true] },
            { text: '', border: [true, false, true, true] }, { text: '', border: [true, false, true, true] }]
            listaTitulo.table.body.push(whiteLine);

            docDefinition.content.push(listaTitulo);

            var resumo = {
                style: 'tableExample',
                table: {
                    widths: ["*", "*", "*", "*", 93, 93],
                    body: [
                        [
                            { rowSpan: 2, colSpan: 4, text: '' },
                            '', '', '',
                            {
                                text: [
                                    { text: '29. Sal. Bruto\n', fontSize: 8, bold: true, alignment: 'left' },
                                    { text: response.valorBruto, fontSize: 9, alignment: 'right' }
                                ]
                            },
                            {
                                text: [
                                    { text: '30. Desconto\n', fontSize: 8, bold: true, alignment: 'left' },
                                    { text: response.valorDesconto, fontSize: 9, alignment: 'right' }
                                ]
                            },
                        ],
                        [
                            '', '', '', '', '',
                            {
                                colSpan: 1, text: [
                                    { text: '32. Líquido\n', fontSize: 8, bold: true, alignment: 'left' },
                                    { text: response.valorLiquido, fontSize: 9, alignment: 'right' }
                                ]
                            },
                        ],
                        [
                            {
                                text: [
                                    { text: '31. FGTS\n', fontSize: 8, bold: true, alignment: 'left' },
                                    { text: '0', fontSize: 9, alignment: 'right' }
                                ]
                            },
                            {
                                text: [
                                    { text: '28. Sal. Base\n', fontSize: 8, bold: true, alignment: 'left' },
                                    { text: response.salarioBase, fontSize: 9, alignment: 'right' }
                                ]
                            },
                            {
                                text: [
                                    { text: '33. Base Prev.\n', fontSize: 8, bold: true, alignment: 'left' },
                                    { text: response.basePrev, fontSize: 9, alignment: 'right' }
                                ]
                            },
                            {
                                text: [
                                    { text: '34. Base IRRF.\n', fontSize: 8, bold: true, alignment: 'left' },
                                    { text: response.baseIrrf, fontSize: 9, alignment: 'right' }
                                ]
                            },
                            '', ''
                        ]
                    ]
                },
                margin: [0, 15, 0, 10],
            };

            docDefinition.content.push(resumo);

            return docDefinition;

        }

        function convertDate(data, comHora) {
            var valor = moment(data);
            if (comHora) {
                return $filter('date')(new Date(valor), 'dd/MM/yyyy - HH:mm');
            } else {
                return $filter('date')(new Date(valor), 'dd/MM/yyyy');
            }
        }

        $scope.loadLotacoes = function () {
            GenericoService.GetAllDropdown('folhaPagamento/listaLotacoes/' + $scope.folha.filial.id, function (response) {
                if (response.status === 200) {
                    $scope.lotacoes = response.data;
                } else if (response.status === 500) {
                    $scope.showSimpleToast("Falha no carregamento de dados, favor contate o administrador do sistema");
                }
            });
        };

        $scope.goBack = function () {
            $location.path('/folhaPagamento/gestao');
        }

        $scope.showRecalculo = function (ev) {
            // Appending dialog to document.body to cover sidenav in docs app

            var title = 'Deseja confirmar o recalculo desta folha?';
            if ($scope.selected && $scope.selected.length > 0) {
                title = 'Deseja confirmar o recalculo dos item selecionados?';
            }

            var confirm = $mdDialog.confirm()
                .title(title)
                .textContent('Esta ação não poderá ser desfeita.')
                // .ariaLabel('Lucky day')
                .targetEvent(ev)
                .ok('Sim')
                .cancel('Não');

            $mdDialog.show(confirm).then(function () {
                if ($scope.selected && $scope.selected.length > 0) {
                    $scope.recalcularContracheque();
                } else {
                    $scope.reprocessarFolha();
                }
            }, function () { });
        };

        $scope.recalcularContracheque = function () {
            var payloadRequest = {
                contrachequeIds: $scope.selected
            };
            GenericoService.Create('/contracheque/recalcular', payloadRequest, function (response) {
                $rootScope.$broadcast('preloader:hide');
                $scope.showSimpleToast(response.data.message);
                if (response.status === 200) {
                    $location.path('/folhaPagamento/gestao');
                }
            });
        };

        $scope.reprocessarFolha = function () {
            $rootScope.$broadcast('preloader:active');
            GenericoService.Update('/folhaPagamento/reprocessar/' + $scope.folha.id, null, function (response) {
                $rootScope.$broadcast('preloader:hide');
                $scope.showSimpleToast(response.data.message);
                if (response.status === 200) {
                    $location.path('/folhaPagamento/gestao');
                }
            });
        }

        $scope.showSimpleToast = function (textContent) {
            $mdToast.show(
                $mdToast.simple()
                    .textContent(textContent)
                    .position('top right')
                    .hideDelay(4000)
            );
        };

    }
})();
