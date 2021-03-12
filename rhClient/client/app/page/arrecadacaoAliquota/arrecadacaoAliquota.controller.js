(function () {
    'use strict';

    angular.module('app.page')
        .controller('arrecadacaoAliquotaCtrl', ['configValue', '$scope', '$mdToast', '$mdDialog', '$rootScope', '$filter', '$location', 'RestService', 'EnumService', arrecadacaoAliquotaCtrl]);

    function arrecadacaoAliquotaCtrl(configValue, $scope, $mdToast, $mdDialog, $rootScope, $filter, $location, RestService, EnumService) {

        // Checa as regras de acesso da tela
        RestService.Get('/usuario/verificaPermissao/por/menu', { params: { menu: 'Alíquota e encagos' } })
            .then(function (response) {
                if (response.status === 200 && response.data) {
                    $scope.permissao = response.data;
                    if ($scope.permissao.podeGerenciar)
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
            order: '-fimVigencia',
            limit: 10,
            page: 1
        };

        $scope.list = {
            "count": 0,
            "data": []
        };

        $scope.lista = {};
        $scope.lista.encargo = [];

        $scope.limitOptions = [5, 10, 15];

        $scope.limpaFiltro = function () {
            $scope.selectedEncargos = [];
            delete $scope.inicioVigencia;
            delete $scope.fimVigencia;
            $scope.loadList();
        }
        
        $scope.selectedEncargos = [];
        EnumService.Get("ArrecadacaoAliquotaEncargoEnum").then(function (dados) {
            $scope.lista.encargo = $filter('orderBy')(dados, 'label');
		});

        $scope.loadList = function () {
            $rootScope.$broadcast('preloader:active');

            var config = {
                params: {
                    page: $scope.query.page - 1,
                    size: $scope.query.limit,
                    order: $scope.query.order,
                    inicioVigencia: $scope.inicioVigencia ? $scope.inicioVigencia.format('YYYY-MM-DD') : null,
                    fimVigencia: $scope.fimVigencia ? $scope.fimVigencia.format('YYYY-MM-DD') : null,
                    encargos: $scope.selectedEncargos
                }
            };

            $scope.promise = RestService.Get('/arrecadacaoAliquota', config)
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
            RestService.Delete('/arrecadacaoAliquota/' + $scope.idToDelete)
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
                    inicioVigencia: $scope.inicioVigencia ? $scope.inicioVigencia.format('YYYY-MM-DD') : null,
                    fimVigencia: $scope.fimVigencia ? $scope.fimVigencia.format('YYYY-MM-DD') : null,
                    encargos: $scope.selectedEncargos
                }
            };
            RestService.Get('/arrecadacaoAliquota/search', config)
                .then(function successCallback(response) {
                    $rootScope.$broadcast('preloader:hide');
                    if (response.status === 200) {
                        RestService.ToDataURL($rootScope.defaultPath + configValue.logo,
                            function (dataURL) {
                                pdfMake.createPdf(getDocDefinition(response.data, dataURL)).open()
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

        $scope.accordion = function (id) {
            var accordions = document.getElementById("accordion-aliquota" + id);

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

        $scope.getAccordionClass = function (index){
            if(index % 2 == 0){
                return 'accordion col-lg-12';
            } else {
                return 'accordion odd col-lg-12';
            }
        }

        /*
         * Recebe data e converte para valor com hora ou não
         * 
         * param data - recebe uma data tipo LocalDate
         * param comHora - recebe um valor boolean
         * 
         * */
		$scope.convertDate = function(data, comHora) {
			var valor = moment(data);
			if (comHora) {
				return $filter('date')(new Date(valor), 'dd/MM/yyyy - HH:mm');
			} else {
				return $filter('date')(new Date(valor), 'dd/MM/yyyy');
			}
		}

        function getDocDefinition(content, dataURL) {

            var docDefinition = {};
            docDefinition.content = [];

            var brasao = { image: dataURL, width: 70, alignment: 'center' };
            docDefinition.content.push(brasao);

            var orgao = { text: 'RH', alignment: 'center', margin: [0, 10], bold: true };
            docDefinition.content.push(orgao);

            var titulo = { text: 'Tabela de Arrecadação - Alíquotas e Encargos', alignment: 'center', margin: [0, 10] };
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
            docDefinition.content.push(listaTitulo);

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

            };

            angular.forEach(content, function (e) {
                lista.table.body.push(
                    [{ text: 'Vigência - ' + $scope.convertDate(e.inicioVigencia,false) + ' à ' + $scope.convertDate(e.fimVigencia,false), fontSize: 10, colSpan: 2, alignment: 'left',fillColor: 'lightgray', style: 'tableHeader' }, {}],
                    [{ text: 'Alíquota/Encargo', fontSize: 10, alignment: 'left' },{ text: 'Alíquota', fontSize: 10, alignment: 'center' }]
                    );
                angular.forEach(e.encargos, function (a) {
                    lista.table.body.push([
                        { text: a.aliquotaEncargo, fontSize: 10, alignment: 'left' },
                        { text: a.aliquota.toLocaleString() + '%', fontSize: 10, alignment: 'center' }
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



