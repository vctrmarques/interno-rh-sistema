(function () {
    'use strict';

    angular.module('app.page')
        .controller('adiantamentoPagamentoCtrl', ['configValue', '$scope', '$mdToast', '$mdDialog', '$rootScope', 'GenericoService', adiantamentoPagamentoCtrl]);

    function adiantamentoPagamentoCtrl(configValue, $scope, $mdToast, $mdDialog, $rootScope, GenericoService) {

        GenericoService.VerificaPermissao('/usuario/verificaPermissao', 'ROLE_ADMIN').then(
            function (response) {
                if (response.status === 200) {
                    $scope.loadList();
                }
            }, function errorCallback(response) {
                if (response.status === 400) {
                    $location.path('page/403');
                }
            });

        $scope.botoesGestao = false;
        GenericoService.VerificaPermissao('/usuario/verificaPermissao', 'ROLE_ADMIN').then(
            function (response) {
                if (response.status === 200) {
                    $scope.botoesGestao = true;
                }
            });

        $scope.statusBusca = false;
        $scope.query = {
            order: 'id',
            limit: 10,
            page: 1
        };

        $scope.list = {
            "count": 0,
            "data": [],
            "statusAdiantamento" : [],
            "listFiliais": [],
            "listLotacoes": []
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
                    matricula: $scope.matriculaBusca,
                    nome: $scope.nomeBusca,
                    status: $scope.statusBusca,
                    filialId: $scope.filialIdBusca,
                    lotacaoId: $scope.lotacaoIdBusca,
                    competencia: $scope.competenciaBusca
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

        $scope.statusAdiantamento = function () {
            $rootScope.$broadcast('preloader:active');
            GenericoService.GetAllDropdown('listStatusAdiantamento', function (response) {
                if (response.status === 200) {
                    $scope.list.statusAdiantamento = response.data;
                    $rootScope.$broadcast('preloader:hide');
                } else if (response.status === 500) {
                    $scope.showSimpleToast("Falha no carregamento de dados, favor contate o administrador do sistema");
                }
            });
        }

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

        $scope.carregarListaLotacoes = function(filialIdBusca) {
            if($scope.lotacaoIdBusca)
                delete $scope.lotacaoIdBusca;
            GenericoService.GetById('/listaLotacoes/' + filialIdBusca, function (response) {
                if (response.status === 200) {
                    $scope.list.listLotacoes = response.data;
                    $rootScope.$broadcast('preloader:hide');
                } else {
                    $scope.showSimpleToast("Não foi possível carregar os dados.")
                }
            })
        };

        $scope.limpaFiltro = function () {
            delete $scope.nomeBusca;
            delete $scope.matriculaBusca;
            delete $scope.statusBusca;
            delete $scope.filialIdBusca;
            delete $scope.lotacaoIdBusca;
            delete $scope.competenciaBusca;
        }

        $scope.checkAll = function(checked) {
           $scope.checkall = !checked;
        }

        $scope.showRelatorioGeral = function() {
            if($scope.filialIdBusca == null) {
                $scope.showSimpleToast('Preencher a filial');
            }

            if($scope.lotacaoIdBusca == null) {
                $scope.showSimpleToast('Preencher a lotação');
            }

            if($scope.filialIdBusca != null && $scope.lotacaoIdBusca != null) {
                GenericoService.GetById('/listByFilialAndLotacao/' + $scope.filialIdBusca + "/" + $scope.lotacaoIdBusca, function (response) {
                        if (response.status === 200) {
                            GenericoService.ToDataURL($rootScope.defaultPath + configValue.logo,
                                function (dataURL) {
                                    pdfMake.createPdf(getDocDefinition(response.data, dataURL)).open()
                                });

                            $rootScope.$broadcast('preloader:hide');
                        }
                    }
                );
            }
        };

        function getDocDefinition(content, dataURL) {
            var docDefinition = {
                pageOrientation: 'landscape'
            };
            docDefinition.content = [];

            var brasao = { image: dataURL, width: 70, alignment: 'center' };
            docDefinition.content.push(brasao);

            var orgao = { text: $rootScope.pageTitle, alignment: 'center', margin: [0, 10], bold: true };
            docDefinition.content.push(orgao);

            var titulo = { text: 'Relatório Geral', alignment: 'center', margin: [0, 10] };
            docDefinition.content.push(titulo);

            var filialLotacao = { text: 'Filial: ' + content[0].funcionarioResponse.filial.nomeFilial + '   Lotação: ' + content[0].funcionarioResponse.lotacao.descricaoCompleta }
            docDefinition.content.push(filialLotacao);

            var lista = {
                style: 'tableExample',
                table: {
                    headerRows: 1,
                    widths: [60, '*', 65, 50, 70, 50, 50, 50, 55, 55],
                    body: [
                        [
                            { text: 'Matrícula', fontSize: 10, fillColor: 'lightgray', style: 'tableHeader', alignment: 'center' },
                            { text: 'Funcionario', fontSize: 10, fillColor: 'lightgray', style: 'tableHeader', alignment: 'center' },
                            { text: 'Filial', fontSize: 10, fillColor: 'lightgray', style: 'tableHeader', alignment: 'center' },
                            { text: 'Lotação', fontSize: 10, fillColor: 'lightgray', style: 'tableHeader', alignment: 'center' },
                            { text: 'Vinculo empregaticio', fontSize: 10, fillColor: 'lightgray', style: 'tableHeader', alignment: 'center' },
                            { text: '% Adiantam.', fontSize: 10, fillColor: 'lightgray', style: 'tableHeader', alignment: 'center' },
                            { text: 'Valor salarial', fontSize: 10, fillColor: 'lightgray', style: 'tableHeader', alignment: 'center' },
                            { text: 'Valor adiantamento', fontSize: 10, fillColor: 'lightgray', style: 'tableHeader', alignment: 'center' },
                            { text: 'Data inicio', fontSize: 10, fillColor: 'lightgray', style: 'tableHeader', alignment: 'center' },
                            { text: 'Data fim', fontSize: 10, fillColor: 'lightgray', style: 'tableHeader', alignment: 'center' }
                        ]
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

            for (var i = 0; i < content.length; i++) {
                var element = content[i];
                element.dataInicio = moment(element.dataInicio).format("DD/MM/YYYY");
                element.dataFim = moment(element.dataInicio).format("DD/MM/YYYY");

                var body = [
                    { text: element.funcionarioResponse.matricula, fontSize: 10 },
                    { text: element.funcionarioResponse.nome, fontSize: 10 },
                    { text: element.funcionarioResponse.filial.nomeFilial, fontSize: 10 },
                    { text: element.funcionarioResponse.lotacao.descricaoCompleta, fontSize: 10 },
                    { text: element.funcionarioResponse.vinculoResponse.descricao, fontSize: 10 },
                    { text: element.percentualAdiantamento, fontSize: 10 },
                    { text: element.funcionarioResponse.referenciaSalarialResponse.valor, fontSize: 10 },
                    { text: element.valorAdiantamento, fontSize: 10 },
                    { text: element.dataInicio, fontSize: 10 },
                    { text: element.dataFim, fontSize: 10 }
                ]

                lista.table.body.push(body)
            }
            docDefinition.content.push(lista);

            return docDefinition;
        }

        $scope.showRelatorioIndividual = function(object) {
            GenericoService.GetById('/listByFuncionario/' + object, function (response) {
                    if (response.status === 200) {
                        GenericoService.ToDataURL($rootScope.defaultPath + configValue.logo,
                            function (dataURL) {
                                pdfMake.createPdf(getDocDefinitionIndividual(response.data, dataURL)).open()
                            });

                        $rootScope.$broadcast('preloader:hide');
                    }
                }
            );
        }

        function getDocDefinitionIndividual(content, dataURL) {
            var docDefinition = {
                pageOrientation: 'landscape'
            };
            docDefinition.content = [];

            var brasao = { image: dataURL, width: 70, alignment: 'center' };
            docDefinition.content.push(brasao);

            var orgao = { text: $rootScope.pageTitle, alignment: 'center', margin: [0, 10], bold: true };
            docDefinition.content.push(orgao);

            var titulo = { text: 'Relatório Geral', alignment: 'center', margin: [0, 10] };
            docDefinition.content.push(titulo);

            var dadosFuncionario = {
                text: [
                    { text: 'Funcionario: ' + content[0].funcionarioResponse.nome + '                  Filial: ' + content[0].funcionarioResponse.filial.nomeFilial + '\n' },
                    { text: 'Matrícula: ' + content[0].funcionarioResponse.matricula + '                                                   Lotação: ' + content[0].funcionarioResponse.lotacao.descricaoCompleta + '\n' },
                    { text: 'Vinculo empregaticio: ' + content[0].funcionarioResponse.vinculoResponse.descricao }
                ]
            }
            docDefinition.content.push(dadosFuncionario);

            var lista = {
                style: 'tableExample',
                table: {
                    headerRows: 1,
                    widths: ['*', '*', '*', '*', '*'],
                    body: [
                        [
                            { text: '% Adiantam.', fontSize: 10, fillColor: 'lightgray', style: 'tableHeader', alignment: 'center' },
                            { text: 'Valor salarial', fontSize: 10, fillColor: 'lightgray', style: 'tableHeader', alignment: 'center' },
                            { text: 'Valor adiantamento', fontSize: 10, fillColor: 'lightgray', style: 'tableHeader', alignment: 'center' },
                            { text: 'Data inicio', fontSize: 10, fillColor: 'lightgray', style: 'tableHeader', alignment: 'center' },
                            { text: 'Data fim', fontSize: 10, fillColor: 'lightgray', style: 'tableHeader', alignment: 'center' }
                        ]
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

            for (var i = 0; i < content.length; i++) {
                var element = content[i];
                element.dataInicio = moment(element.dataInicio).format("DD/MM/YYYY");
                element.dataFim = moment(element.dataInicio).format("DD/MM/YYYY");

                var body = [
                    { text: element.percentualAdiantamento, fontSize: 10 },
                    { text: element.funcionarioResponse.referenciaSalarialResponse.valor, fontSize: 10 },
                    { text: element.valorAdiantamento, fontSize: 10 },
                    { text: element.dataInicio, fontSize: 10 },
                    { text: element.dataFim, fontSize: 10 }
                ]

                lista.table.body.push(body)
            }
            docDefinition.content.push(lista);

            return docDefinition;
        }

        $scope.showConfirmDelete = function (ev) {
            // Appending dialog to document.body to cover sidenav in docs app
            var confirm = $mdDialog.confirm()
                .title('Deseja confirmar a exclusão destes itens?')
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
            for (var i = 0; i < $scope.selectedList.length; i++) {
                var element = $scope.selectedList[i];

                for (var k in element) {
                    if (typeof element[k] !== 'function') {
                        if (element[k]) {
                            GenericoService.Delete('/adiantamentoPagamento/' + k, function (response) { });
                        }
                    }
                }
            }

            $scope.loadList();  
            $scope.showSimpleToast('Adiantamentos deletedaos com sucesso');
        }

        $scope.statusAdiantamento();
        $scope.carregarListaFiliais();

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



