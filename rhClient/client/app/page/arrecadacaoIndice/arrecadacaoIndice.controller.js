(function () {
    'use strict';

    angular.module('app.page')
        .controller('arrecadacaoIndiceCtrl', ['configValue', '$q', '$scope', '$mdToast', '$mdDialog', '$rootScope', 'EnumService', 'RestService', '$filter', arrecadacaoIndiceCtrl]);

    function arrecadacaoIndiceCtrl(configValue, $q, $scope, $mdToast, $mdDialog, $rootScope, EnumService, RestService, $filter) {

        // Checa as regras de acesso da tela
        RestService.Get('/usuario/verificaPermissao/por/menu', { params: { menu: 'Índice' } })
            .then(function (response) {
                if (response.status === 200 && response.data) {

                    $scope.usuarioAdm = response.data.usuarioAdm;
                    $scope.podeGerenciar = false;

                    if (response.data.papeis)
                        $scope.podeGerenciar = response.data.papeis.includes('ROLE_ARRECADACAO_INDICE_GESTAO') ? true : false;

                    if ($scope.usuarioAdm || $scope.podeGerenciar)
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
            order: '-anos.ano',
            limit: 10,
            page: 1
        };

        $scope.list = {
            "count": 0,
            "data": []
        };

        $scope.selectedPeriodicidade = [];
        $scope.limitOptions = [5, 10, 15];

        $scope.limpaFiltro = function () {
            delete $scope.anoTxt;
            delete $scope.indiceTxt;
            delete $scope.selectedPeriodicidade;

            delete $scope.anoSearch;
            delete $scope.indiceSearch;
            
            $scope.loadList();
        }

        $scope.anoSelecionado = function () {
            if ($scope.anoTxt) {
                $scope.anoSearch = $scope.anoTxt.ano;
            }else{
                delete $scope.anoSearch;
            }
        };

        $scope.indiceSelecionado = function () {
            if ($scope.indiceTxt) {
                $scope.indiceSearch = $scope.indiceTxt.indice;
            }else{
                delete $scope.indiceSearch;
            }
        };


        $scope.loadList = function () {
            $rootScope.$broadcast('preloader:active');
            var periodicidadeList = [];
            if ($scope.selectedPeriodicidade && $scope.selectedPeriodicidade.length > 0) {
                $scope.selectedPeriodicidade.forEach(element => {
                    periodicidadeList.push(element.value);
                });
            }
            var config = {
                params: {
                    page: $scope.query.page - 1,
                    size: $scope.query.limit,
                    order: $scope.query.order,
                    ano: $scope.anoSearch,
                    indice: $scope.indiceSearch,
                    periodicidades: periodicidadeList
                }
            };
            $scope.promise = RestService.Get('/arrecadacaoIndice', config)
                .then(function successCallback(response) {
                    $rootScope.$broadcast('preloader:hide');
                    if (response.status === 200) {
                        $scope.list.data = response.data.content;
                        $scope.list.count = response.data.totalElements;
                        montarLista($scope.list.data);
                    }
                }, function errorCallback(response) {
                    $rootScope.$broadcast('preloader:hide');
                    if (response.status === 400) {
                        $scope.showSimpleToast("Não foi possível carregar os dados.")
                    }
                });
        }

        function montarLista(list) {
            $scope.listData = [];
            var naoTem = true;
            angular.forEach(list, function (l) {
                angular.forEach(l.anos, function (a) {
                    let ano = {
                        anoId: a.id,
                        ano: a.ano,
                        periodicidade: a.periodicidade,
                        indices: []
                    }
                    naoTem = true;
                    angular.forEach($scope.listData, function (e) {
                        if (e.ano == a.ano) {
                            naoTem = false;
                        }
                    });

                    if (naoTem) {
                        /*
                        só crio o acordion se for com anos diferentes
                        */
                        if($scope.anoTxt){
                            if(ano.ano == $scope.anoTxt.ano){
                                $scope.listData.push(ano);
                            }
                        }else if($scope.selectedPeriodicidade && $scope.selectedPeriodicidade.length > 0){
                            /*
                            só crio o acordion se for com periodicidades diferentes em um mesmo ano
                            */
                            var vlBooleano = true;
                            debugger
                            $scope.selectedPeriodicidade.forEach(element => {
                                if(ano.periodicidade.toUpperCase() != element.value){
                                    vlBooleano = false;
                                }
                            });

                            if(vlBooleano){
                                $scope.listData.push(ano);
                            }
                        }else{
                             /*
                             O 1º acordion sempre vai criar.
                            */
                            $scope.listData.push(ano);
                        }
                    }
                });
                angular.forEach(l.anos, function (a) {
                    let indice = {
                        id: l.id,
                        label: l.indice,
                        periodicidade: a.periodicidade
                    }
                    angular.forEach($scope.listData, function (e) {
                        /*
                        só inclui na listagem os anos que possuem periodicidades diferentes
                        */
                        if (e.ano == a.ano) {
                            var ix = $scope.listData.indexOf(e);
                            if($scope.listData[ix].indices.length > 0){
                                var valorRepetido = true;
                                angular.forEach($scope.listData[ix].indices, function (o) {
                                    if(o.id == indice.id){
                                        valorRepetido = false;
                                    }
                                });

                                if(valorRepetido){
                                    $scope.listData[ix].indices.push(indice);
                                }
                            }else{
                                $scope.listData[ix].indices.push(indice);
                            }
                        }
                    });
                });
            });
             // ordena a lista por ordem Descrecente de 'ANOS'.
             $scope.listData = $filter('orderBy')($scope.listData, 'ano', 'DESC');
        }

        EnumService.Get("PeriodicidadeMesEnum").then(function (dados) {
            $scope.list.periodicidade = dados;
        });

        $scope.querySearchAno = function (anoSearch) {

            var deferred = $q.defer();
            if (anoSearch) {
                if (anoSearch.length > 1) {
                    $rootScope.$broadcast('preloader:active');
                    RestService.Get('/arrecadacaoIndice/ano/search', { params: { search: anoSearch } }).then(
                        function (response) {
                            if (response.data && response.data.length > 0) {
                                response.data.forEach(item => {
                                    item.ano = item.ano + '';
                                })
                                deferred.resolve(response.data);
                            }
                        });
                } else {
                    $scope.ano = [];
                }
                $rootScope.$broadcast('preloader:hide');
            }
            return deferred.promise;
        }

        $scope.querySearchIndice = function (indiceSearch) {
            var deferred = $q.defer();
            if (indiceSearch) {
                if (indiceSearch.length > 2) {
                    $rootScope.$broadcast('preloader:active');
                    RestService.Get('/arrecadacaoIndice/indice/search', { params: { search: indiceSearch } }).then(
                        function (response) {
                            if (response.data && response.data.length > 0) {
                                deferred.resolve(response.data);
                            }
                        });
                } else {
                    $scope.indice = [];
                }
                $rootScope.$broadcast('preloader:hide');
            }
            return deferred.promise;
        }

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

        $scope.deleteItem = function () {
            $rootScope.$broadcast('preloader:active');
            RestService.Delete('/arrecadacaoIndice/' + $scope.idToDelete)
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

        $scope.showRelatorio = function () {
            $rootScope.$broadcast('preloader:active');
            var config = {
                params: {
                    page: $scope.query.page - 1,
                    size: 200
                }
            };
            RestService.Get('/arrecadacaoIndice', config)
                .then(function successCallback(response) {
                    $rootScope.$broadcast('preloader:hide');
                    if (response.status === 200) {
                        RestService.ToDataURL($rootScope.defaultPath + configValue.logo,
                            function (dataURL) {
                                pdfMake.createPdf(getDocDefinition($scope.listData, dataURL)).open()
                            });
                        $rootScope.$broadcast('preloader:hide');
                    }
                }, function errorCallback(response) {
                    $rootScope.$broadcast('preloader:hide');
                    if (response.status === 400) {
                        $scope.showSimpleToast("Não foi possível carregar os dados.")
                    }
                });
        }

        $scope.accordion = function (ano) {
            var accordions = document.getElementById("accordion-indices" + ano);

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

        $scope.getAccordionClass = function (index) {
            if (index % 2 == 0) {
                return 'accordion col-lg-12';
            } else {
                return 'accordion odd col-lg-12';
            }
        }

        function getDocDefinition(content, dataURL) {
            var docDefinition = {};
            docDefinition.content = [];

            var brasao = { image: dataURL, width: 70, alignment: 'center' };
            docDefinition.content.push(brasao);

            var orgao = { text: 'RH', alignment: 'center', margin: [0, 10], bold: true };
            docDefinition.content.push(orgao);

            var titulo = { text: 'Tabela de Arrecadacao Índice', alignment: 'center', margin: [0, 10] };
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
                    widths: ['*', 'auto'],
                    body: []
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

            angular.forEach(content, function (e) {
                lista.table.body.push(
                    [{ text: 'Ano - ' + e.ano, fontSize: 10, colSpan: 2, alignment: 'left', fillColor: 'lightgray', style: 'tableHeader' }, {}],
                    [{ text: 'Índice', fontSize: 10, alignment: 'left' }, { text: 'Periodicidade', fontSize: 10, alignment: 'right' }]
                );
                angular.forEach(e.indices, function (a) {
                    lista.table.body.push([
                        { text: a.label, fontSize: 10, alignment: 'left' },
                        { text: a.periodicidade, fontSize: 10, alignment: 'right' }
                    ]);
                });

            });

            docDefinition.content.push(lista);

            return docDefinition;

        }

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



