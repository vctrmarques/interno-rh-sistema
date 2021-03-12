(function () {
    'use strict';

    angular.module('app.page')
        .controller('aliquotaCtrl', ['configValue', '$scope', '$mdToast', '$mdDialog', '$rootScope', 'GenericoService', aliquotaCtrl]);

    function aliquotaCtrl(configValue, $scope, $mdToast, $mdDialog, $rootScope, GenericoService) {


        GenericoService.VerificaPermissao('/usuario/verificaPermissao', 'ROLE_ADMIN').then(
            function (response) {
                if (response.status === 200) {
                    $scope.loadList(null, null);
                }
            }, function errorCallback(response) {
                if (response.status === 400) {
                    $location.path('page/403');
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


        $scope.listAnos = [];

        $scope.anos = function (faixa) {
            $rootScope.$broadcast('preloader:active');

            $scope.promise = GenericoService.GetAll('/aliquotas/anos', faixa).then(
                function (response) {
                    if (response.status === 200) {


                        $scope.anos = response.data;
                    } else {
                        $scope.showSimpleToast("Não foi possível carregar os dados.")
                    }
                });

        }
        $scope.buscar = function (faixa, ano) {
            $scope.ano = ano;
            $scope.loadListByAnoAndFaixa(faixa, ano)
        }

        $scope.loadListByAnoAndFaixa = function (faixa, ano) {
            var config = {
                params: {
                    faixa: faixa,
                    ano: ano
                }
            };

            $scope.promise = GenericoService.GetById("/aliquota/" + faixa + "/" + ano, function (response) {
                if (response.status === 200) {
                    $scope.list.data.forEach(function (faixa, index) {
                        if (faixa.faixa == response.data[0].faixa) {
                            this[index].ano = response.data[0].ano;
                            this[index].aliquotas = response.data;
                        }
                    }, $scope.list.data);
                    $rootScope.$broadcast('preloader:hide');
                } else {
                    $scope.showSimpleToast("Não foi possível carregar os dados.")
                }
            });
        }


        $scope.loadList = function () {
            $rootScope.$broadcast('preloader:active');
            $scope.promise = GenericoService.GetAll('/aliquotas', null).then(
                function (response) {
                    if (response.status === 200) {
                        $scope.list.data = response.data;
                        angular.forEach($scope.list.data, function (d) {
                            var config = {
                                params: {
                                    faixa: d.faixa
                                }
                            };
                            $scope.promise = GenericoService.GetAll('/aliquotas/anos', config).then(
                                function (response) {
                                    if (response.status === 200) {
                                        d.anos = response.data;
                                    } else {
                                        $scope.showSimpleToast("Não foi possível carregar os dados.")
                                    }
                                });
                        });
                        $scope.list.count = response.data.totalElements;
                        $rootScope.$broadcast('preloader:hide');
                    } else {
                        $scope.showSimpleToast("Não foi possível carregar os dados.")
                    }
                });
        }
        $scope.accordion = function (faixa) {
            var accordions = document.getElementById("accordion-aliquota" + faixa);

            var content = accordions.nextElementSibling;
            if (content.style.maxHeight) {
                accordions.classList.remove("is-open");
                // accordion is currently open, so close it
                content.style.maxHeight = null;
                content.style.paddingTop = null;
            } else {
                // accordion is currently closed, so open it
                accordions.classList.add("is-open");
                content.style.maxHeight = content.scrollHeight + "px";
                content.style.paddingTop = "5px";
            }
        };

        $scope.searchFuncionario = function searchFuncionario(nomeFuncionario) {
            $scope.historicoTemp = $scope.list.data;
            var config = {
                params: {
                    page: $scope.query.page - 1,
                    size: $scope.query.limit,
                    order: $scope.query.order,
                    nomeFuncionario: nomeFuncionario
                }
            };
            if (nomeFuncionario.length > 2) {
                $rootScope.$broadcast('preloader:active');
                $scope.promise = getAll(config);
            } else if (nomeFuncionario.length == 0) {
                $rootScope.$broadcast('preloader:active');
                getAll(config);
            }

        }

        $scope.limitOptions = [5, 10, 15];



        $scope.showSimpleToast = function (textContent) {
            $mdToast.show(
                $mdToast.simple()
                    .textContent(textContent)
                    .position('top right')
                    .hideDelay(3000)
            );
        };


        $scope.showRelatorio = function () {
            $rootScope.$broadcast('preloader:active');
            var config = {
                params: {
                    page: $scope.query.page - 1,
                    size: 200
                }
            };
            GenericoService.GetAll('/aliquotas', config).then(
                function (response) {
                    if (response.status === 200) {
                        pdfMake.createPdf(getDocDefinition(response.data)).open();
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

            var titulo = { text: 'Tabela de Alíquotas', alignment: 'center', margin: [0, 10] };
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


                body.push({ text: element.faixa, fontSize: 10, alignment: 'center', fillColor: 'lightgray' })

                angular.forEach(element.aliquotas, function (e) {
                    lista.table.body.push([{ text: 'Número do Processo: ' + e.numeroProcesso, fontSize: 10, alignment: 'center' },
                    ]);
                });
                lista.table.body.push(body)
            }
            docDefinition.content.push(lista);

            return docDefinition;

        }

    }

})();



