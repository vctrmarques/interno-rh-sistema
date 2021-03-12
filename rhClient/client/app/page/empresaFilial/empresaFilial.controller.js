(function () {
    'use strict';

    angular.module('app.page')
        .controller('empresaFilialCtrl', ['configValue', '$scope', '$mdToast', '$mdDialog', '$rootScope', 'GenericoService', empresaFilialCtrl]);

    function empresaFilialCtrl(configValue, $scope, $mdToast, $mdDialog, $rootScope, GenericoService) {

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


        $scope.siglaNomeBusca = "";
        $scope.query = {
            order: 'sigla',
            limit: 10,
            page: 1
        };

        $scope.list = {
            "count": 0,
            "data": []
        };
        $scope.listForTree = {
            "count": 0,
            "data": []
        };

        $scope.limpaFiltro = function () {
            delete $scope.siglaNomeBusca;
        }

        $scope.loadList = function () {
            $rootScope.$broadcast('preloader:active');


            var config = {
                params: {
                    page: $scope.query.page - 1,
                    size: $scope.query.limit,
                    order: $scope.query.order,
                    siglaNome: $scope.siglaNomeBusca
                }
            };

            $scope.promise = GenericoService.GetAll('/empresasFiliais', config).then(
                function (response) {
                    if (response.status === 200) {
                        $scope.list.data = response.data.content;
                        $scope.list.count = response.data.totalElements;
                        $rootScope.$broadcast('preloader:hide');
                    } else {
                        $scope.showSimpleToast("Não foi possível carregar os dados.")
                    }
                });

                $scope.promise = GenericoService.GetAll('/empresasFiliaisNoEmpresaMatriz', config).then(
                    function (response) {
                        if (response.status === 200) {
                            $scope.listForTree.data = response.data.content;
                            $scope.listForTree.count = response.data.totalElements;
                            $rootScope.$broadcast('preloader:hide');
                        } else {
                            $scope.showSimpleToast("Não foi possível carregar os dados da Arvore.")
                        }
                    });
        }

        $scope.limitOptions = [5, 10, 15];

        $scope.deleteItem = function () {
            $rootScope.$broadcast('preloader:active');
            GenericoService.Delete('/empresaFilial/' + $scope.idToDelete, function (response) {
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
            GenericoService.GetAllRelatorio('/empresasFiliais').then(
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

            var orgao = { text: $rootScope.pageTitle, alignment: 'center', margin: [0, 10], bold: true };
            docDefinition.content.push(orgao);

            var titulo = { text: 'Relatório de Empresas Filiais', alignment: 'center', margin: [0, 10] };
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
                    widths: ['auto', 'auto', '*', 'auto', '*'],
                    body: [
                        [{ text: 'CNPJ/CEI', fontSize: 10, fillColor: 'lightgray', style: 'tableHeader', alignment: 'center' },
                        { text: 'Sigla', fontSize: 10, fillColor: 'lightgray', style: 'tableHeader', alignment: 'center' },
                        { text: 'Nome da Filial', fontSize: 10, fillColor: 'lightgray', style: 'tableHeader', alignment: 'center' },
                        { text: 'Tipo de Filial', fontSize: 10, fillColor: 'lightgray', style: 'tableHeader', alignment: 'center' },
                        { text: 'Tipo de Estabelecimento', fontSize: 10, fillColor: 'lightgray', style: 'tableHeader', alignment: 'center' }
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
                    { text: element.cnpjCei, fontSize: 10 },
                    { text: element.sigla, fontSize: 10 },
                    { text: element.nomeFilial, fontSize: 10 },
                    { text: element.tipoFilial, fontSize: 10 },
                    { text: element.tipoEstabelecimento, fontSize: 10 }
                ]
                lista.table.body.push(body)
            }
            docDefinition.content.push(lista);

            return docDefinition;

        }

        $scope.tree = function (ev) {
            $mdDialog.show({
                scope: $scope,
                preserveScope: true,
                controller: ['$scope', '$q', function ($scope, $q) {

                    $scope.hide = function () {
                        $mdDialog.hide();
                    };
                    $scope.cancel = function () {
                        $mdDialog.cancel();
                    };


                    $scope.showteste = function () {

                        console.log($scope.lis.data);
                        // console.log(ev.empresaMatriz);
                        // if (ev.empresaMatriz) {
                        //     return false;
                        // } else {
                        //     $scope.lis.data + ($scope.data = ev);
                        //     return true;
                        // }
                        // console.log($scope.lis.data);
                    }









                }],
                templateUrl: 'app/page/empresaFilial/tree.tmpl.html',
                parent: angular.element(document.body),
                clickOutsideToClose: true
            });
        };
    }

})();