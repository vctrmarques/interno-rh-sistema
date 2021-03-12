(function () {
    'use strict';

    angular.module('app.page')
        .controller('pensaoAlimenticiaCtrl', ['configValue', '$scope', '$mdToast', '$mdDialog', '$rootScope', 'GenericoService', 'RestService', pensaoAlimenticiaCtrl]);

    function pensaoAlimenticiaCtrl(configValue, $scope, $mdToast, $mdDialog, $rootScope, GenericoService, RestService) {

        // Checa as regras de acesso da tela
        RestService.Get('/usuario/verificaPermissao/por/menu', { params: { menu: 'Pensão Alimentícia' } })
            .then(function (response) {
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

        $scope.query = {
            order: 'id',
            limit: 10,
            page: 1
        };

        $scope.list = {
            "count": 0,
            "data": []
        };

        $scope.loadList = function () {
            $scope.changetab0();

            $rootScope.$broadcast('preloader:active');

            var config = {
                params: {
                    page: $scope.query.page - 1,
                    size: $scope.query.limit,
                    order: $scope.query.order,
                    search: $scope.search
                }
            };

            $scope.promise = GenericoService.GetAll('/pensaoAlimenticia/funcionarios', config).then(
                function (response) {
                    if (response.status === 200) {
                        $scope.list.data = response.data.content;
                        $scope.list.count = response.data.totalElements;
                        $rootScope.$broadcast('preloader:hide');
                        angular.forEach(response.data.content, function (c) {
                            c.vigentes = 0;
                            c.encerradas = 0;
                            angular.forEach(c.pensoes, function (p) {
                                if (p.dataFinalPagamento == null || moment(p.dataFinalPagamento) > new Date()) {
                                    c.vigentes += 1;
                                } else {
                                    c.encerradas += 1;
                                }
                            });
                        });
                    } else {
                        $scope.showSimpleToast("Não foi possível carregar os dados.")
                    }
                });
        }


        $scope.limitOptions = [5, 10, 15];

        $scope.search;

        $scope.limpaFiltro = function () {
            $scope.search = null;
            $scope.loadList();
        }

        $scope.onTab0Selected = function (tab) {
            $scope.bloquearTabAlimentando = true;
        }
        
        $scope.bloquearTabAlimentando = true;
        $scope.changetab1 = function (pensoes) {
            $scope.pensoes = pensoes;
            angular.forEach($scope.pensoes, function (p) {
                if (p.dataFinalPagamento == null || moment(p.dataFinalPagamento) > new Date()) {
                    p.situacao = 'Vigente';
                } else {
                    p.situacao = 'Encerrada';
                }
            });
            $scope.bloquearTabAlimentando = false;
            $scope.myTabIndex = 1;
        }

        $scope.changetab0 = function () {
            $scope.pensoes = [];
            $scope.bloquearTabAlimentando = true;
            $scope.myTabIndex = 0;
        }

        $scope.deleteItem = function () {
            $rootScope.$broadcast('preloader:active');
            GenericoService.Delete('/pensaoAlimenticia/' + $scope.idToDelete, function (response) {
                $rootScope.$broadcast('preloader:hide');
                if (response.status === 200) {
                    $scope.showSimpleToast(response.data.message);
                    $scope.loadList();
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

        $scope.showRelatorio = function () {
            $rootScope.$broadcast('preloader:active');
            var config = {
                params: {
                    page: $scope.query.page - 1,
                    size: 200
                }
            };
            GenericoService.GetAll('/pensaoAlimenticia/funcionarios', config).then(
                function (response) {
                    if (response.status === 200) {
                        GenericoService.ToDataURL($rootScope.defaultPath + configValue.logo,
                            function (dataURL) {
                                pdfMake.createPdf(getDocDefinition(response.data.content, dataURL)).open()
                            });
                        $rootScope.$broadcast('preloader:hide');
                    }
                });
        }

        function getDocDefinition(content, dataURL) {

            var docDefinition = {};
            docDefinition.content = [];

            var brasao = { image: dataURL, width: 70, alignment: 'center' };
            docDefinition.content.push(brasao);

            var orgao = { text: 'RH', alignment: 'center', margin: [0, 10], bold: true };
            docDefinition.content.push(orgao);

            var titulo = { text: 'Tabela de Processos', alignment: 'center', margin: [0, 10] };
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
                    // dontBreakRows: true,
                    // keepWithHeaderRows: 1,
                    widths: ['*'],
                    body: [
                        [
                            { text: 'Nome', fontSize: 10, fillColor: 'lightgray', style: 'tableHeader', alignment: 'center' },
                            // { text: 'Nome', fontSize: 10, fillColor: 'lightgray', style: 'tableHeader', alignment: 'center' },
                            // { text: 'Data Inspeção', fontSize: 10, fillColor: 'lightgray', style: 'tableHeader', alignment: 'center' },
                            // { text: 'Afastamento', fontSize: 10, fillColor: 'lightgray', style: 'tableHeader', alignment: 'center' }
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

            var i;
            for (i = 0; i < content.length; i++) {
                console.log(content);
                var body = []
                var element = content[i];


                body.push({ text: element.nome, fontSize: 10, alignment: 'center', fillColor: 'lightgray' })

                angular.forEach(element.processos, function (e) {
                    lista.table.body.push([{ text: 'Número do Processo: ' + e.numeroProcesso, fontSize: 10, alignment: 'center' }]);
                });
                lista.table.body.push(body)
            }
            docDefinition.content.push(lista);

            return docDefinition;

        }

    }

})();



