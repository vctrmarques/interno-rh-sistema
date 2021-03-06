(function () {
    'use strict';

    angular.module('app.page')
        .controller('cursoCtrl', ['configValue', '$scope', '$mdToast', '$mdDialog', '$rootScope', 'GenericoService', cursoCtrl]);

    function cursoCtrl(configValue, $scope, $mdToast, $mdDialog, $rootScope, GenericoService) {

        GenericoService.VerificaPermissao('/usuario/verificaPermissao', 'ROLE_ADMIN').then(
            function (response) {
                if (response.status === 200) {
                    $scope.loadList();
                    window.sessionStorage.removeItem('filtros');
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


        $scope.nomeCursoBusca = "";
        $scope.query = {
            order: 'nomeCurso',
            limit: 10,
            page: 1
        };

        $scope.codigoMecBusca = "";

        $scope.list = {
            "count": 0,
            "data": []
        };

        $scope.limpaFiltro = function () {
            delete $scope.nomeCursoBusca;
            delete $scope.codigoMecBusca;
            window.sessionStorage.removeItem('filtros');
            $scope.loadList();
        }

        $scope.loadList = function () {
            $rootScope.$broadcast('preloader:active');

            $scope.filtros = JSON.parse(window.sessionStorage.getItem('filtros'));

            if ($scope.filtros) {

                $scope.filtros.page = $scope.query.page;
                $scope.filtros.size = $scope.query.limit;
                $scope.filtros.order = $scope.query.order;
                $scope.filtros.nomeCursoBusca = $scope.nomeCursoBusca;
                $scope.filtros.codigoMecBusca = $scope.codigoMecBusca;

                $scope.promise = GenericoService.Filtro('/filtrarCursos', $scope.filtros, function (response) {
                        $rootScope.$broadcast('preloader:hide');
                        if (response.status === 200) {
                            $scope.list.data = response.data.content;
                            $scope.list.count = response.data.totalElements;
                            $scope.limpaFiltro();
                            $scope.filtroPersonalizado = false;
                            $rootScope.$broadcast('preloader:hide');
                        } else if (response.status === 400) {
                            $scope.showSimpleToast("N??o foi poss??vel carregar os dados.")
                        }
                    });

            } else {

                var config = {
                    params: {
                        page: $scope.query.page - 1,
                        size: $scope.query.limit,
                        order: $scope.query.order,
                        nomeCurso: $scope.nomeCursoBusca,
                        codigoMec: $scope.codigoMecBusca,
                    }
                };

                $scope.promise = GenericoService.GetAll('/cursos', config).then(
                    function (response) {
                        if (response.status === 200) {
                            $scope.list.data = response.data.content;
                            $scope.list.count = response.data.totalElements;
                            $rootScope.$broadcast('preloader:hide');
                        } else {
                            $scope.showSimpleToast("N??o foi poss??vel carregar os dados.")
                        }
                    });
            }
        }

        $scope.limitOptions = [5, 10, 15];

        $scope.deleteItem = function () {
            $rootScope.$broadcast('preloader:active');
            GenericoService.Delete('/curso/' + $scope.idToDelete, function (response) {
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
                .title('Deseja confirmar a exclus??o deste item?')
                .textContent('Esta a????o n??o poder?? ser desfeita.')
                // .ariaLabel('Lucky day')
                .targetEvent(ev)
                .ok('Sim')
                .cancel('N??o');

            $mdDialog.show(confirm).then(function () {
                $scope.deleteItem();
            }, function () {
            });
        };

        $scope.showRelatorio = function () {
            $rootScope.$broadcast('preloader:active');
            GenericoService.GetAllRelatorio('/cursos').then(
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

            var titulo = { text: 'Tabela de Curso', alignment: 'center', margin: [0, 10] };
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
                    widths: ['*', '*', '*', '*'],
                    body: [
                        [{ text: 'Nome do Curso', fontSize: 10, fillColor: 'lightgray', style: 'tableHeader', alignment: 'center' },
                        { text: 'Codigo do Mec', fontSize: 10, fillColor: 'lightgray', style: 'tableHeader', alignment: 'center' },
                        { text: 'Grau Acad??mico', fontSize: 10, fillColor: 'lightgray', style: 'tableHeader', alignment: 'center' },
                        { text: '??rea de Forma????o', fontSize: 10, fillColor: 'lightgray', style: 'tableHeader', alignment: 'center' }
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
                    { text: element.nomeCurso, fontSize: 10 },
                    { text: element.codigoMec, fontSize: 10, alignment: 'center' },
                    { text: element.grauAcademico.nome, fontSize: 10 },
                    { text: element.areaFormacao.areaFormacao, fontSize: 10 }
                ]
                lista.table.body.push(body)
            }
            docDefinition.content.push(lista);

            return docDefinition;

        }

        $scope.showDialog = function (ev, id) {
            var $this =  $scope;
            $mdDialog.show({
                //locals: { af: $scope.areaFormacao },
                require: { container: '^^cursoCtrl' },
                controller: ['$scope', '$q', function ($scope, $q) {

                    $scope.filtro = { areasFormacao: [], grausAcademicos: [] };

                    $scope.clickDialog = function () {
                        window.sessionStorage.setItem('filtros', JSON.stringify($scope.filtro));                        
                        $mdDialog.cancel(); 
                        $this.loadList();
                    }

                    $scope.querySearchAreaFormacao = function (query) {
                        var deferred = $q.defer();
                        var config = { params: { search: query } }
                        GenericoService.GetAll('/areaFormacao/searchComplete', config).then(
                            function (response) {
                                if (response.data && response.data.length > 0) {
                                    $scope.itens = response.data;
                                    deferred.resolve(response.data);
                                }
                            });
                        return deferred.promise;
                    }

                    $scope.querySearchGrauAcademico = function (query) {
                        var deferred = $q.defer();
                        var config = { params: { search: query } }
                        GenericoService.GetAll('/grauAcademico/searchComplete', config).then(
                            function (response) {
                                if (response.data && response.data.length > 0) {
                                    $scope.itens = response.data;
                                    deferred.resolve(response.data);
                                }
                            });
                        return deferred.promise;
                    }

                    $scope.cancel = function () {
                        $mdDialog.cancel();
                    };
                }],
                templateUrl: 'app/page/curso/dialog1.tmpl.html',
                parent: angular.element(document.body),
                targetEvent: ev,
                clickOutsideToClose: true,
            });
            
        }
    }

})();