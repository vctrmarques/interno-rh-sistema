(function () {
    'use strict';

    angular.module('app.page')
        .controller('bancoCtrl', ['configValue', '$location', '$scope', '$mdToast', '$mdDialog', '$rootScope', 'GenericoService', '$q', bancoCtrl]);

    function bancoCtrl(configValue, $location, $scope, $mdToast, $mdDialog, $rootScope, GenericoService, $q) {

        $scope.botoesGestao = false;
        GenericoService.VerificaPermissao('/usuario/verificaPermissao', 'ROLE_ADMIN').then(
            function (response) {
                if (response.status === 200) {
                    carregarListaBancos();
                    $scope.botoesGestao = true;
                }
            }, function errorCallback(response) {
                if (response.status === 400) {
                    $location.path('page/403');
                }
            });

        $scope.query = {
            order: 'nome',
            limit: 10,
            page: 1
        };

        $scope.list = {
            "count": 0,
            "data": []
        };

        $scope.novoBanco = function () {
            $location.path('/banco/formulario');
        }

        $scope.novaAgencia = function () {
            $location.path('/agencia/formulario/' + $scope.bancoSelecionadoId);
        }

        function carregarListaBancos() {
            GenericoService.GetAllDropdown('listaBancos', function (response) {
                if (response.status === 200) {
                    $scope.bancos = response.data;
                    $scope.bancoSelecionadoId = sessionStorage.getItem("bancoSelecionadoId");
                    $rootScope.$broadcast('preloader:hide');
                } else {
                    $scope.showSimpleToast("Não foi possível carregar os dados.")
                }
            })
        }

        $scope.carregarAgencias = function () {
            $scope.query.page = 1;
            $scope.loadList();
        }

        $scope.loadList = function () {
            
            if ($scope.bancoSelecionadoId) {
                $rootScope.$broadcast('preloader:active');

                var config = {
                    params: {
                        page: $scope.query.page - 1,
                        size: $scope.query.limit,
                        order: $scope.query.order,
                        bancoId: $scope.bancoSelecionadoId,
                        nome: $scope.agenciaBancariaDescricao
                    }
                };
                sessionStorage.setItem("bancoSelecionadoId",  $scope.bancoSelecionadoId)
    
                $scope.promise = GenericoService.GetAll('/agencias', config).then(
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
        }

        $scope.querySearchAgencia = function (query) {
            var deferred = $q.defer();
            var config = { params: { search: query, bancoId: $scope.bancoSelecionadoId } }
            if (query) {
                if (query.length > 2) {
                    $rootScope.$broadcast('preloader:active');
                    GenericoService.GetAll('/agencia/search', config).then(
                        function (response) {
                            if (response.data && response.data.length > 0) {
                                deferred.resolve(response.data);

                            }
                        });
                } else {
                    $scope.agencia = [];
                }
                $rootScope.$broadcast('preloader:hide');
            }
            return deferred.promise;
        };

        $scope.agenciaSelecionada = function () {
            if ($scope.agencia) {
                $scope.agenciaBancariaId = $scope.agencia.id;
                $scope.agenciaBancariaDescricao = $scope.agencia.descricao;
                $scope.loadList();
            }
        };

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
                    size: 200,
                    projecao: "COMPLETA"
                }
            };
            GenericoService.GetAll('/bancos', config).then(
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

            var titulo = { text: 'Tabela de Bancos', alignment: 'center', margin: [0, 10] };
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
                    widths: ['auto', '*'],
                    body: [
                        [
                            { text: 'Código', fontSize: 10, fillColor: 'lightgray', style: 'tableHeader', alignment: 'center' },
                            { text: 'Nome', fontSize: 10, fillColor: 'lightgray', style: 'tableHeader', alignment: 'center' }
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
                    { text: element.codigo, fontSize: 10 },
                    { text: element.nome, fontSize: 10, alignment: 'center' }
                ]
                lista.table.body.push(body)
            }
            docDefinition.content.push(lista);

            return docDefinition;

        }

    }

})();



