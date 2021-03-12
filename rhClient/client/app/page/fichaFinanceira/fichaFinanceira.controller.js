(function () {
    'use strict';

    angular.module('app.page')
        .controller('fichaFinanceiraCtrl', ['$q', '$mdToast', '$mdDialog', 'configValue', '$scope', '$rootScope', '$location', '$filter', 'GenericoService', fichaFinanceiraCtrl]);

    function fichaFinanceiraCtrl($q, $mdToast, $mdDialog, configValue, $scope, $rootScope, $location, $filter, GenericoService) {

        $scope.search = "";
        $scope.filialId = null;
        $scope.lotacaoId = null;
        $scope.query = {
            order: 'id',
            limit: 10,
            page: 1
        };

        $scope.list = {
            "count": 0,
            "data": []
        };

        $scope.acessaTela = function () {
            GenericoService.VerificaPermissao('/usuario/verificaPermissao', 'ROLE_ADMIN').then(
                function (response) {
                    if (response.status === 200) {
                        $scope.loadlist();
                    }
                },
                function errorCallback(response) {
                    if (response.status === 400) {
                        $location.path('page/403');
                    }
                });
        }

        $scope.acessaTela();
        $scope.botoesGestao = false;

        $scope.habilitaBotoesGestao = function () {
            GenericoService.VerificaPermissao('/usuario/verificaPermissao', 'ROLE_ADMIN').then(
                function (response) {
                    if (response.status === 200) {
                        $scope.botoesGestao = true;
                    }
                });
        }

        $scope.habilitaBotoesGestao();

        $scope.limpaFiltro = function () {
            delete $scope.search;
            $scope.filialId = null;
            $scope.lotacaoId = null;
            $scope.loadlist();
        }

        $scope.carregarListaFiliais = function () {
            GenericoService.GetAllDropdown('listaEmpresasFiliais', function (response) {
                if (response.status === 200) {
                    $scope.listFiliais = response.data;
                    $rootScope.$broadcast('preloader:hide');
                } else {
                    $scope.showSimpleToast("Não foi possível carregar os dados.")
                }
            })
        };

        $scope.carregarListaFiliais();

        $scope.carregarLotacoes = function (empresaFilialId) {
            delete $scope.listLotacoes;
            GenericoService.GetById('/listaLotacoes/' + empresaFilialId, function (response) {
                if (response.status === 200) {
                    $scope.listLotacoes = response.data;
                    $rootScope.$broadcast('preloader:hide');
                } else {
                    $scope.showSimpleToast("Não foi possível carregar os dados.")
                }
            });
            $scope.lotacao.id = null;
        };

        $scope.redirectDetail = function (idFuncionario) {
            $location.path('/fichaFinanceira/detalhamento/' + idFuncionario + '/ano/' + $scope.folhaCompetencia.anoCompetencia);
        }

        $scope.loadlist = function () {
            $rootScope.$broadcast('preloader:active');
            var config = {
                params: {
                    filialId: $scope.filialId,
                    lotacaoId: $scope.lotacaoId,
                    search: $scope.search,
                    page: $scope.query.page - 1,
                    size: $scope.query.limit,
                    order: $scope.query.order
                }
            };
            $scope.promise = GenericoService.GetAll('/funcionarios', config).then(
                function (response) {
                    if (response.status === 200) {
                        $scope.list.data = response.data.content;
                        $scope.list.count = response.data.totalElements;
                    } else {
                        $scope.showSimpleToast("Não foi possível carregar os dados.")
                    }
                    $rootScope.$broadcast('preloader:hide');
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
            }, function () { });
        };



        $scope.status = '  ';
        $scope.showTabDialog = function (ev, funcionarioId) {
            $mdDialog.show({
                scope: $scope,
                preserveScope: true,
                controller: ['$scope', '$q', 'GenericoService', '$mdToast', function ($scope, $q, GenericoService, $mdToast) {

                    $scope.selectRadioButton = "anoCompleto";
                    $scope.funcionarioId = funcionarioId;

                    $scope.hide = function () {
                        $mdDialog.hide();
                    };
                    $scope.cancel = function () {
                        $mdDialog.cancel();
                    };

                    $scope.cleanAtributes = function () {
                        $scope.search = {
                            ano: null,
                            dataInicial: null,
                            dataFinal: null
                        }
                    }
                    $scope.cleanAtributes();

                    $scope.btnImprimir = function () {
                        if ($scope.selectRadioButton === 'anoCompleto') {
                            $scope.search.ano = new Date().getFullYear();
                            delete $scope.search.dataInicial;
                            delete $scope.search.dataFinal;
                        } else {
                            delete $scope.search.ano;
                            $scope.search.dataInicial = new Date($scope.search.dataInicial);
                            $scope.search.dataFinal = new Date($scope.search.dataFinal);
                        }

                        var config = {
                            params: {
                                ano: $scope.search.ano,
                                periodoInicial: $scope.search.dataInicial,
                                periodoFinal: $scope.search.dataFinal
                            }
                        }
                        $rootScope.$broadcast('preloader:active');
                        GenericoService.GetAll('/contracheque/' + $scope.funcionarioId + '/periodo', config).then(function (response) {
                            if (response.status === 200) {
                                $scope.fichaFinanceira = response.data;
                                $scope.fichaFinanceira.verbasProventos = $filter('orderBy')($scope.fichaFinanceira.verbasProventos, 'codigo');
                                $scope.fichaFinanceira.verbasDescontos = $filter('orderBy')($scope.fichaFinanceira.verbasDescontos, 'codigo');


                                GenericoService.ToDataURL($rootScope.defaultPath + configValue.logo,
                                    function (dataURL) {
                                        pdfMake.createPdf(getDocDefinition($scope.fichaFinanceira, dataURL)).open()
                                        $rootScope.$broadcast('preloader:hide');
                                    }
                                );
                            } else {
                                $scope.showSimpleToast("Folha não encontrada na base");
                            }
                        });
                    }

                    $scope.changeData = function () {
                        if ($scope.selectRadioButton === 'dozeMeses') {
                            $scope.search.dataInicial = moment($scope.search.dataInicial).date(1).month(0);
                            $scope.search.dataFinal = moment($scope.search.dataInicial).date(31).month(11);
                        } else   if ($scope.selectRadioButton === 'periodos') {
                            $scope.search.dataInicial = moment($scope.search.dataInicial);
                            $scope.search.dataFinal = moment($scope.search.dataFinal);
                            if( $scope.search.dataInicial.year() != $scope.search.dataFinal.year()){
                                $scope.search.dataFinal.year($scope.search.dataInicial.year());
                            }
                        }
                        if ($scope.search.dataFinal && ($scope.search.dataFinal < $scope.search.dataInicial)) {
                            $scope.search.dataFinal = new Date($scope.search.dataInicial);
                        }
                    }



                    function getDocDefinition(response, dataURL) {
                        var docDefinition = {
                            pageSize: 'A4',
                            pageOrientation: 'landscape',
                            pageMargins: [40, 40, 40, 40],
                            info: {
                                title: 'Ficha Financeira'
                            }
                        };
                        docDefinition.content = [];
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
                                                { text: 'Nome do Servidor\n', fontSize: 8, bold: true, alignment: 'left' },
                                                { text: response.funcionario.nome, fontSize: 9, alignment: 'right' }
                                            ]
                                        },
                                        {
                                            text: [
                                                { text: 'Filial\n', fontSize: 8, bold: true, alignment: 'left' },
                                                { text: response.funcionario.matricula, fontSize: 9, alignment: 'right' }
                                            ]
                                        },
                                        '',
                                        {
                                            text: [
                                                { text: 'Matrícula - Dig\n', fontSize: 8, bold: true, alignment: 'left' },
                                                { text: response.funcionario.matricula, fontSize: 9, alignment: 'right' }
                                            ]
                                        },
                                        {
                                            text: [
                                                { text: 'Data Admissão\n', fontSize: 8, bold: true, alignment: 'left' },
                                                { text: convertDate(response.funcionario.dataAdmissao), fontSize: 9, alignment: 'right' }
                                            ]
                                        },
                                    ],
                                    [
                                        {
                                            colSpan: 2, text: [
                                                { text: 'Lotação\n', fontSize: 8, bold: true, alignment: 'left' },
                                                { text: response.funcionario.lotacao == null  ? '' : response.funcionario.lotacao.descricao , fontSize: 9, alignment: 'right' }
                                            ]
                                        },
                                        '',
                                        '',
                                        {
                                            colSpan: 2, text: [
                                                { text: 'Vínculo\n', fontSize: 8, bold: true, alignment: 'left' },
                                                { text:  response.funcionario.vinculo == null  ? '' : response.funcionario.vinculo.descricao, fontSize: 9, alignment: 'right' }
                                            ]
                                        },

                                    ],
                                    [
                                        {
                                            colSpan: 4, text: [
                                                { text: 'Função\n', fontSize: 8, bold: true, alignment: 'left' },
                                                { text: response.funcionario.funcao.nome, fontSize: 9, alignment: 'right' }
                                            ]
                                        },
                                        '',
                                        '',
                                        '',
                                        {
                                            text: [
                                                { text: 'Cargo\n', fontSize: 8, bold: true, alignment: 'left' },
                                                { text: response.funcionario.cargo.nome, fontSize: 9, alignment: 'right' }
                                            ]
                                        },
                                    ],
                                ]
                            }
                        };
                        var header = {
                            alignment: 'left',
                            columns: [
                                {
                                    image: dataURL, width: 70, alignment: 'left'
                                },
                                {
                                    text: 'INST.DE PREV.DOS SERV. DO MUNIC.DE GOIANIA- GOIANIAPREV \n ' + (response.funcionario.lotacao == null ? '' : response.funcionario.lotacao.descricao.toUpperCase()) + ' \n  31.711.157/0000-59',
                                    margin: [15, 15, 0, 10],
                                    fontSize: 11
                                },
                                dadosFuncionario
                            ]
                        };

                        docDefinition.content.push(header);

                        var titulo = { text: 'Ficha Financeira', alignment: 'center', margin: [0, 10], fontSize: 11 };
                        docDefinition.content.push(titulo);


                        var bodyCompetencia = [];
                        var widthTableVerbaMeses = [];
                        bodyCompetencia.push({ text: 'RÚBRICA', fontSize: 9, bold: true });
                        widthTableVerbaMeses.push('*');
                        var index;
                        for (index = 0; index < response.listFolhaCompetenciaResponse.length; index++) {
                            var element = response.listFolhaCompetenciaResponse[index];
                            bodyCompetencia.push({ text: element.mesCompetencia + "/" + element.anoCompetencia, fontSize: 9 });
                            widthTableVerbaMeses.push('auto');
                        }
                        bodyCompetencia.push({ text: 'Total', fontSize: 9 });
                        widthTableVerbaMeses.push('auto');

                        var tableVerbasMeses = {
                            style: 'tableExample',
                            table: {
                                widths: widthTableVerbaMeses,
                                body: [bodyCompetencia]
                            },
                            margin: [0, 15, 0, 10],
                            // layout: 'noBorders'
                        };

                        var i;
                        for (i = 0; i < response.verbasProventos.length; i++) {
                            var row = [];
                            var totalVerba = 0.0;
                            var element = response.verbasProventos[i];
                            row.push({ text: response.verbasProventos[i].codigo + " - " + response.verbasProventos[i].descricaoResumida, fontSize: 8 });
                            var index
                            for (index = 0; index < response.listFolhaCompetenciaResponse.length; index++) {
                                var elementCompetencia = response.listFolhaCompetenciaResponse[index];
                                var index2;
                                var temMesAno = false;
                                for (index2 = 0; index2 < response.verbasProventos[i].listMesesValores.length; index2++) {
                                    var elementMeses = response.verbasProventos[i].listMesesValores[index2];
                                    if (elementMeses.mesAno === elementCompetencia.mesAno) {
                                        row.push({ text: elementMeses.valor.toFixed(2), fontSize: 9, alignment: 'center' });
                                        totalVerba+=elementMeses.valor;
                                        temMesAno = true;
                                        break;
                                    }
                                }
                                if (!temMesAno) {
                                    row.push({ text: ' - ', fontSize: 9, alignment: 'center' });
                                }
                            }
                            row.push({text: totalVerba.toFixed(2), fontSize: 9, alignment: 'center'});
                            tableVerbasMeses.table.body.push(row);
                        }

                        var row = [];
                        row.push({ text: '** TOTAL DE PROVENTOS **', fontSize: 9, bold: true});
                        var index;
                        for (index = 0; index < response.listFolhaCompetenciaResponse.length; index++) {
                            const element = response.listFolhaCompetenciaResponse[index];
                            row.push({ text: element.valorTotalProventos.toFixed(2), fontSize: 9, alignment: 'center' })
                            if(index == response.listFolhaCompetenciaResponse.length-1){
                                row.push({text: response.valorBruto.toFixed(2), fontSize: 9, alignment: 'center'})
                            }
                        }
                        tableVerbasMeses.table.body.push(row);

                        var i;
                        for (i = 0; i < response.verbasDescontos.length; i++) {
                            var row = [];
                            var totalVerba = 0.0;
                            var element = response.verbasDescontos[i];
                            row.push({ text: response.verbasDescontos[i].codigo + " - " + response.verbasDescontos[i].descricaoResumida, fontSize: 8 });
                            var index
                            for (index = 0; index < response.listFolhaCompetenciaResponse.length; index++) {
                                var elementCompetencia = response.listFolhaCompetenciaResponse[index];
                                var index2;
                                var temMesAno = false;
                                for (index2 = 0; index2 < response.verbasDescontos[i].listMesesValores.length; index2++) {
                                    var elementMeses = response.verbasDescontos[i].listMesesValores[index2];
                                    if (elementMeses.mesAno === elementCompetencia.mesAno) {
                                        row.push({ text: elementMeses.valor.toFixed(2), fontSize: 9, alignment: 'center' });
                                        totalVerba+=elementMeses.valor;
                                        temMesAno = true;
                                        break;
                                    }
                                }
                                if (!temMesAno) {
                                    row.push({ text: ' - ', fontSize: 9, alignment: 'center' });
                                }
                            }
                            row.push({text: totalVerba.toFixed(2), fontSize: 9, alignment: 'center'});
                            tableVerbasMeses.table.body.push(row);
                        }

                        var row = [];
                        row.push({ text: '** TOTAL DE DESCONTOS **', fontSize: 9, bold: true });
                        var index;
                        for (index = 0; index < response.listFolhaCompetenciaResponse.length; index++) {
                            const element = response.listFolhaCompetenciaResponse[index];
                            row.push({ text: element.valorTotalDescontos.toFixed(2), fontSize: 9, alignment: 'center' })
                            if(index == response.listFolhaCompetenciaResponse.length-1){
                                row.push({text: response.valorDesconto.toFixed(2), fontSize: 9, alignment: 'center'})
                            }
                        }
                        tableVerbasMeses.table.body.push(row);

                        var row = [];
                        row.push({ text: '** LÍQUIDO **', fontSize: 9, bold: true});
                        var index;
                        for (index = 0; index < response.listFolhaCompetenciaResponse.length; index++) {
                            const element = response.listFolhaCompetenciaResponse[index];
                            row.push({ text: element.valorTotalLiquido.toFixed(2), fontSize: 9, alignment: 'center' })
                            if(index == response.listFolhaCompetenciaResponse.length-1){
                                row.push({text: response.valorLiquido.toFixed(2), fontSize: 9, alignment: 'center'})
                            }
                        }
                        tableVerbasMeses.table.body.push(row);

                        docDefinition.content.push(tableVerbasMeses);

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

                }],
                templateUrl: 'app/page/fichaFinanceira/tabDialog.tmpl.html',
                parent: angular.element(document.body),
                clickOutsideToClose: true
            });
        };



        $scope.showFichaFinanceiraDetalhe = function (ev, funcionarioId) {

            $scope.funcionarioId = funcionarioId;

            $mdDialog.show({
                scope: $scope,
                preserveScope: true,
                controller: ['$scope', '$mdDialog', '$filter', function ($scope, $mdDialog, $filter) {
                    $scope.hide = function () {
                        $mdDialog.hide();
                    };

                    $scope.cancel = function () {
                        $mdDialog.cancel();
                    };

                    $scope.answer = function (answer) {
                        $mdDialog.hide(answer);
                    };

                    $scope.loadCompetenciaAtualFechada = function () {
                        GenericoService.GetAll('/competencia/atual/fechada')
                            .then(function (response) {
                                if (response.data) {
                                    $scope.folhaCompetencia = response.data;
                                    // $scope.folhaCompetenciaSelect();
                                }
                            });
                    }

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

                    $scope.folhaCompetenciaSelect = function () {
                        delete $scope.fPagContracheque;
                        if ($scope.funcionarioId && $scope.folhaCompetencia) {
                            $rootScope.$broadcast('preloader:active');
                            GenericoService.GetById('/contracheque/' + $scope.funcionarioId + '/competencia/' + $scope.folhaCompetencia.id, function (response) {
                                $rootScope.$broadcast('preloader:hide');
                                if (response.status === 200) {
                                    $scope.fPagContracheque = response.data;
                                    $scope.fPagContracheque.funcionario.dataAdmissao = moment(response.data.funcionario.dataAdmissao);

                                    if ($scope.fPagContracheque.lancamentosDescontos || $scope.fPagContracheque.lancamentosVantagens) {
                                        $scope.fPagContracheque.lancamentosVantagens = $filter('orderBy')($scope.fPagContracheque.lancamentosVantagens, 'verba.codigo');
                                        $scope.fPagContracheque.lancamentosDescontos = $filter('orderBy')($scope.fPagContracheque.lancamentosDescontos, 'verba.codigo');
                                    }
                                    if ($scope.fPagContracheque.lancamentosOutros) {

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
                                    }

                                } else {
                                    $scope.showSimpleToast("Folha não encontrada na base");
                                }
                            });
                        }
                    }
                    $scope.loadCompetenciaAtualFechada();



                    $scope.showSimpleToast = function (textContent) {
                        $mdToast.show(
                            $mdToast.simple()
                                .textContent(textContent)
                                .position('top right')
                                .hideDelay(4000)
                        );
                    };


                }],
                templateUrl: 'app/page/fichaFinanceira/tabFichaFinanceiraDetalhe.tmpl.html',
                parent: angular.element(document.body),
                targetEvent: ev,
                clickOutsideToClose: true,
                fullscreen: true // Only for -xs, -sm breakpoints.
            });
        };

    }


})();
