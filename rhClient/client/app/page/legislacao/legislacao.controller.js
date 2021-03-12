(function () {
    'use strict';

    angular.module('app.page')
        .controller('legislacaoCtrl', ['configValue', '$scope', '$mdToast', '$mdDialog', '$rootScope', '$location', 'RestService', legislacaoCtrl]);

    function legislacaoCtrl(configValue, $scope, $mdToast, $mdDialog, $rootScope, $location, RestService) {

        // Checa as regras de acesso da tela
        RestService.Get('/usuario/verificaPermissao/por/menu', { params: { menu: 'Legislação' } })
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
            order: 'createdAt',
            limit: 10,
            page: 1
        };

        $scope.list = {
            "count": 0,
            "data": []
        };

        $scope.limitOptions = [5, 10, 15];

        $scope.limpaFiltro = function () {
            delete $scope.legislacaoFiltro;
        }

        $scope.loadList = function () {
            $rootScope.$broadcast('preloader:active');

            var config = {
                params: {
                    page: $scope.query.page - 1,
                    size: $scope.query.limit,
                    order: $scope.query.order
                }
            };

            // Aplicação dos filtros
            if ($scope.legislacaoFiltro) {
                if ($scope.legislacaoFiltro.enteFederado)
                    config.params.enteFederadoId = $scope.legislacaoFiltro.enteFederado.id;
                if ($scope.legislacaoFiltro.norma)
                    config.params.normaId = $scope.legislacaoFiltro.norma.id;
                if ($scope.legislacaoFiltro.detalhamentoNorma)
                    config.params.detalhamentoNormaId = $scope.legislacaoFiltro.detalhamentoNorma.id;
                if ($scope.legislacaoFiltro.numero)
                    config.params.numero = $scope.legislacaoFiltro.numero;
                if ($scope.legislacaoFiltro.ano)
                    config.params.ano = $scope.legislacaoFiltro.ano;
            }

            $scope.promise = RestService.Get('/legislacao', config)
                .then(function successCallback(response) {
                    $rootScope.$broadcast('preloader:hide');
                    if (response.status === 200) {
                        $scope.list.data = response.data.content;
                        $scope.list.count = response.data.totalElements;
                    }
                }, function errorCallback(response) {
                    $rootScope.$broadcast('preloader:hide');
                    if (response.status === 400) {
                        $scope.showSimpleToast("Não foi possível carregar os dados.")
                    }
                });
        }

        // Busca dos Ente Federados
        RestService.Get('/legislacao/enteFederado/search')
            .then(function (response) {
                if (response.status === 200 && response.data) {
                    $scope.enteFederadoList = response.data;
                }
            }, function errorCallback(response) {
                if (response.status === 400) {
                    $scope.showSimpleToast(response.data.message);
                }
            });

        // Busca dos Normas
        RestService.Get('/legislacao/norma/search')
            .then(function (response) {
                if (response.status === 200 && response.data) {
                    $scope.normaList = response.data;
                }
            }, function errorCallback(response) {
                if (response.status === 400) {
                    $scope.showSimpleToast(response.data.message);
                }
            });

        // Busca dos Detalhamentos Normas
        RestService.Get('/legislacao/detalhamentoNorma/search')
            .then(function (response) {
                if (response.status === 200 && response.data) {
                    $scope.detalhamentoNormaList = response.data;
                }
            }, function errorCallback(response) {
                if (response.status === 400) {
                    $scope.showSimpleToast(response.data.message);
                }
            });

        $scope.deleteItem = function () {
            $rootScope.$broadcast('preloader:active');
            RestService.Delete('/legislacao/' + $scope.idToDelete)
                .then(function successCallback(response) {
                    $rootScope.$broadcast('preloader:hide');
                    if (response.status === 200) {
                        $scope.showSimpleToast(response.data.message);
                        $scope.loadList();
                    }
                }, function errorCallback(response) {
                    $rootScope.$broadcast('preloader:hide');
                    if (response.status === 400) {
                        $scope.showSimpleToast(response.data.message);
                    }
                });
        }

        $scope.showSimpleToast = function (textContent) {
            $mdToast.show(
                $mdToast.simple()
                    .textContent(textContent)
                    .position('top right')
                    .hideDelay(10000)
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
                    page: 0,
                    size: 5000
                }
            };

            // Aplicação dos filtros
            if ($scope.legislacaoFiltro) {
                if ($scope.legislacaoFiltro.enteFederado)
                    config.params.enteFederadoId = $scope.legislacaoFiltro.enteFederado.id;
                if ($scope.legislacaoFiltro.norma)
                    config.params.normaId = $scope.legislacaoFiltro.norma.id;
                if ($scope.legislacaoFiltro.detalhamentoNorma)
                    config.params.detalhamentoNormaId = $scope.legislacaoFiltro.detalhamentoNorma.id;
                if ($scope.legislacaoFiltro.numero)
                    config.params.numero = $scope.legislacaoFiltro.numero;
                if ($scope.legislacaoFiltro.ano)
                    config.params.ano = $scope.legislacaoFiltro.ano;
            }

            RestService.Get('/legislacao', config)
                .then(function successCallback(response) {
                    $rootScope.$broadcast('preloader:hide');
                    if (response.status === 200) {
                        RestService.ToDataURL($rootScope.defaultPath + configValue.logo,
                            function (dataURL) {
                                pdfMake.createPdf(getDocDefinition(response.data.content, dataURL)).open()
                            });
                    }
                }, function errorCallback(response) {
                    $rootScope.$broadcast('preloader:hide');
                    if (response.status === 400) {
                        $scope.showSimpleToast("Não foi possível carregar os dados.")
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

            var titulo = { text: 'Tabela de Legislação / Norma', alignment: 'center', margin: [0, 10] };
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
                    widths: ['auto', '*', '*', 'auto', 'auto'],
                    body: [
                        [
                            { text: 'Ente Federado', fontSize: 10, fillColor: 'lightgray', style: 'tableHeader', alignment: 'center' },
                            { text: 'Norma', fontSize: 10, fillColor: 'lightgray', style: 'tableHeader', alignment: 'center' },
                            { text: 'Detalhamento da Norma', fontSize: 10, fillColor: 'lightgray', style: 'tableHeader', alignment: 'center' },
                            { text: 'Número', fontSize: 10, fillColor: 'lightgray', style: 'tableHeader', alignment: 'center' },
                            { text: 'Ano', fontSize: 10, fillColor: 'lightgray', style: 'tableHeader', alignment: 'center' }
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
                var element = content[i];
                var body = [
                    { text: element.enteFederado.descricao, fontSize: 10 },
                    { text: element.norma.descricao, fontSize: 10 },
                    { text: element.detalhamentoNorma.descricao, fontSize: 10 },
                    { text: element.numeroNorma, fontSize: 10 },
                    { text: element.anoNorma, fontSize: 10, alignment: 'center' }
                ]
                lista.table.body.push(body)
            }
            docDefinition.content.push(lista);

            return docDefinition;

        }

    }

})();



