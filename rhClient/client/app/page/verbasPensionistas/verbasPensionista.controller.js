(function () {
    'use strict';

    angular.module('app.page')
        .controller('verbasPensionistaCtrl', ['configValue', '$scope', '$mdToast', '$mdDialog', '$rootScope', '$location', 'GenericoService', verbasPensionistaCtrl]);

    function verbasPensionistaCtrl(configValue, $scope, $mdToast, $mdDialog, $rootScope, $location, GenericoService) {

        $scope.acessaTela = function () {
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

        $scope.query = {
            order: 'matricula',
            limit: 10,
            page: 1
        };


        $scope.list = {
            "count": 0,
            "data": []
        };

        $scope.limpaFiltro = function () {
            delete $scope.searchFuncionario;
            delete $scope.searchPensionista;
            delete $scope.pensionistaVerbaAssociada;
            $scope.loadList();
        }

        $scope.carregarPensionista = function () {
            $scope.query.page = 1;
            $scope.loadList();
        }

        $scope.accordionBefore = function () {
            var accordionsBefore = document.getElementsByClassName("accordionBefore");

            for (var i = 0; i < accordionsBefore.length; i++) {
                accordionsBefore[i].onclick = function () {
                    this.classList.toggle('is-open');

                    var content = this.nextElementSibling;
                    if (content.style.maxHeight) {
                        // accordionBefore is currently open, so close it
                        content.style.maxHeight = null;
                        content.style.paddingTop = null;
                    } else {
                        // accordionBefore is currently closed, so open it
                        content.style.maxHeight = content.scrollHeight + "px";
                        content.style.paddingTop = "5px";
                    }
                }
            }
        };

        $scope.loadList = function () {
            $rootScope.$broadcast('preloader:active');

            var config = {
                params: {
                    page: $scope.query.page - 1,
                    size: $scope.query.limit,
                    order: $scope.query.order,
                    searchFuncionario: $scope.searchFuncionario,
                    searchPensionista: $scope.searchPensionista,
                    pensionistaVerbaAssociada: $scope.pensionistaVerbaAssociada
                }
            };

            $scope.promise = GenericoService.GetAll('/pensionistaVerbas', config).then(
                function (response) {
                    if (response.status === 200) {
                        $scope.list.data = response.data.content;
                        $scope.list.count = response.data.totalElements;
                        $rootScope.$broadcast('preloader:hide');
                        console.log($scope.list.data);
                    } else {
                        $scope.showSimpleToast("Não foi possível carregar os dados.")
                    }
                });

            $scope.accordionBefore();
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
            $rootScope.$broadcast('preloader:active');
            GenericoService.GetAllRelatorio('/dependentes').then(
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

            var titulo = { text: 'Tabela de Dependentes', alignment: 'center', margin: [0, 10] };
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
                    widths: ['*', '*', '*'],
                    body: [
                        [{ text: 'Matrícula', fontSize: 10, fillColor: 'lightgray', style: 'tableHeader', alignment: 'center' },
                        { text: 'Nome Pensionista', fontSize: 10, fillColor: 'lightgray', style: 'tableHeader', alignment: 'center' },
                        { text: 'Quantidade', fontSize: 10, fillColor: 'lightgray', style: 'tableHeader', alignment: 'center' }
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
                    { text: element.matricula, fontSize: 10, alignment: 'center' },
                    { text: element.nome, fontSize: 10 },
                    { text: element.quantidade, fontSize: 10 }
                ]
                lista.table.body.push(body)
            }
            docDefinition.content.push(lista);

            return docDefinition;
        }
    }
})();