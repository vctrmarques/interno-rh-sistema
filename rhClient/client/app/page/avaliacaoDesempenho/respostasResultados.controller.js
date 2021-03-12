(function () {
    'use strict';
    angular.module('app.page')
        .controller('respostasResultadosCtrl', ['configValue', '$scope', '$mdToast', '$mdDialog', '$rootScope', 'GenericoService', respostasResultadosCtrl]);

    function respostasResultadosCtrl(configValue, $scope, $mdToast, $mdDialog, $rootScope, GenericoService) {
        $scope.botoesGestao = false;
        GenericoService.VerificaPermissao('/usuario/verificaPermissao', 'ROLE_ADMIN').then(
            function (response) {
                if (response.status === 200) {
                    $scope.botoesGestao = true;
                    $scope.loadList();
                }
            }, function errorCallback(response) {
                if (response.status === 400) {
                    $location.path('page/403');
                }
            });
        $scope.query = {
            order: 'codAvaliacao',
            limit: 10,
            page: 1
        };
        $scope.list = {
            "count": 0,
            "data": []
        };
        $scope.loadList = function () {
            $rootScope.$broadcast('preloader:active');
            var config = {
                params: {
                    page: $scope.query.page - 1,
                    size: $scope.query.limit,
                    order: $scope.query.order,
                    nome: $scope.nomeBusca
                }
            };
            $scope.promise = GenericoService.GetAll('/respostasResultados', config).then(
                function (response) {
                    if (response.status === 200) {
                        $scope.list.data = response.data.content;
                        $scope.list.count = response.data.totalElements;
                        $rootScope.$broadcast('preloader:hide');
                    } else {
                        $scope.showSimpleToast("Não foi possível carregar os dados.");
                    }
                });
        };
        $scope.limitOptions = [5, 10, 15];
        $scope.deleteItem = function () {
            $rootScope.$broadcast('preloader:active');
            GenericoService.Delete('/respostasResultados/' + $scope.idToDelete, function (response) {
                $rootScope.$broadcast('preloader:hide');
                if (response.status === 200) {
                    $scope.showSimpleToast(response.data.message);
                    $scope.loadList();
                }
            });
        };
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
            GenericoService.GetAll('/respostasResultados/relatorio', config).then(function (response) {
                if (response.status === 200) {
                    GenericoService.ToDataURL($rootScope.defaultPath + configValue.logo,
                        function (dataURL) {
                            pdfMake.createPdf(getDocDefinition(response.data.content, dataURL)).open()
                        });
                    $rootScope.$broadcast('preloader:hide');
                }
            });
        };

        function getDocDefinition(content, dataURL) {
            var docDefinition = {};
            docDefinition.content = [];
            var brasao = {image: dataURL, width: 70, alignment: 'center'};
            docDefinition.content.push(brasao);
            var orgao = {text: $rootScope.pageTitle, alignment: 'center', margin: [0, 10], bold: true};
            docDefinition.content.push(orgao);
            var titulo = {text: 'Tabela de Avaliações Desempenho', alignment: 'center', margin: [0, 10]};
            docDefinition.content.push(titulo);
            var listaTitulo = {
                style: 'tableExample',
                table: {
                    widths: ["*"],
                    body: [
                        [
                            {text: 'Listagem', bold: true, fillColor: 'lightblue'}
                        ]
                    ]
                },
                margin: [0, 15, 0, 10],
                layout: 'noBorders'
            };
            docDefinition.content.push(listaTitulo);
            var lista = {
                style: 'tableExample',
                table: {
                    headerRows: 1,
                    // dontBreakRows: true,
                    // keepWithHeaderRows: 1,
                    widths: ['auto', '*'],
                    body: [
                        [{text: 'Código', fontSize: 10, fillColor: 'lightgray', style: 'tableHeader', alignment: 'center'},
                            {text: 'Nome', fontSize: 10, fillColor: 'lightgray', style: 'tableHeader', alignment: 'center'},
                            {text: 'Modelo', fontSize: 10, fillColor: 'lightgray', style: 'tableHeader', alignment: 'center'}
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
            };
            var i;
            for (i = 0; i < content.length; i++) {
                var element = content[i];
                var body = [
                    {text: element.codAvaliacao, fontSize: 10, alignment: 'center'},
                    {text: element.nome, fontSize: 10},
                    {text: element.modelo, fontSize: 10}
                ];
                lista.table.body.push(body);
            }
            docDefinition.content.push(lista);
            return docDefinition;
        }
    }
})();
