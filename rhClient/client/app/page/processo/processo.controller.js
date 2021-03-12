(function () {
    'use strict';

    angular.module('app.page')
        .controller('processoCtrl', ['configValue', '$scope', '$mdToast', '$mdDialog', '$rootScope', 'GenericoService', processoCtrl]);

    function processoCtrl(configValue, $scope, $mdToast, $mdDialog, $rootScope, GenericoService) {

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
            $rootScope.$broadcast('preloader:active');

            var config = {
                params: {
                    page: $scope.query.page - 1,
                    size: $scope.query.limit,
                    order: $scope.query.order,
                    search: $scope.search
                }
            };

            $scope.promise = GenericoService.GetAll('/processo/funcionarios', config).then(
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
        $scope.accordion = function (id) {
            var accordions = document.getElementById("accordion-processo"+id);
            
            var content = accordions.nextElementSibling;
            if (content.style.maxHeight) {
                accordions.classList.remove("is-open");
                content.style.maxHeight = null;
                content.style.paddingTop = null;
            } else {
                accordions.classList.add("is-open");
                content.style.maxHeight = content.scrollHeight + "px";
                content.style.paddingTop = "5px";
            }
        };

        $scope.searchFuncionario = function searchFuncionario(search) {
            $scope.historicoTemp = $scope.list.data;
            var config = {
                params: {
                    page: $scope.query.page - 1,
                    size: $scope.query.limit,
                    order: $scope.query.order,
                    search: search
                }
            };
            if (search.length > 2) {
                $rootScope.$broadcast('preloader:active');
                $scope.promise = getAll(config);
            } else if (search.length == 0) {
                $rootScope.$broadcast('preloader:active');
                getAll(config);
            }

        }

        $scope.limitOptions = [5, 10, 15];

        $scope.search;


        $scope.limpaFiltro = function () {
            $scope.search = null;
            $scope.loadList();
        }

        $scope.deleteItem = function () {
            $rootScope.$broadcast('preloader:active');
            GenericoService.Delete('/processo/' + $scope.idToDelete, function (response) {
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
            GenericoService.GetAll('/processo/funcionarios', config).then(
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

        function getAll(config) {
            GenericoService.GetAll('/processo/funcionarios', config).then(
                function (response) {
                    $scope.list.data = response.data.content;
                    $scope.list.count = response.data.totalElements;
                    $rootScope.$broadcast('preloader:hide');
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



