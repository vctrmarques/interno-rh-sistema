(function () {
    'use strict';

    angular.module('app.page')
        .controller('auditoriaCtrl', ['EnumService', '$scope', '$mdToast', '$mdDialog', '$rootScope', '$location', 'RestService', auditoriaCtrl]);

    function auditoriaCtrl(EnumService, $scope, $mdToast, $mdDialog, $rootScope, $location, RestService) {

        // Checa as regras de acesso da tela
        RestService.Get('/usuario/verificaPermissao/por/menu', { params: { menu: 'Auditoria' } })
            .then(function (response) {
                if (response.status === 200 && response.data) {

                    $scope.usuarioAdm = response.data.usuarioAdm;
                    $scope.podeGerenciar = false;

                    if (response.data.papeis)
                        $scope.podeGerenciar = response.data.papeis.includes('ROLE_AUDITORIA_GESTAO') ? true : false;

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

        $scope.isArray = angular.isArray;

        $scope.query = {
            order: '-createdAt',
            limit: 10,
            page: 1
        };

        $scope.list = {
            "count": 0,
            "data": []
        };

        $scope.limitOptions = [5, 10, 15];

        $scope.limpaFiltro = function () {
            delete $scope.auditoriaFiltro;
            $scope.loadList();
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
            if ($scope.auditoriaFiltro) {

                if ($scope.auditoriaFiltro.periodoInicial)
                    config.params.periodoInicialStr = $scope.auditoriaFiltro.periodoInicial.format('DD/MM/YYYY');

                if ($scope.auditoriaFiltro.periodoFinal)
                    config.params.periodoFinalStr = $scope.auditoriaFiltro.periodoFinal.format('DD/MM/YYYY');

                if ($scope.auditoriaFiltro.nome)
                    config.params.nome = $scope.auditoriaFiltro.nome;

                if ($scope.auditoriaFiltro.tabelasSelecionadas) {
                    // Filtro de status
                    config.params.entidadeNomeList = [];
                    if ($scope.auditoriaFiltro.tabelasSelecionadas && $scope.auditoriaFiltro.tabelasSelecionadas.length > 0) {
                        $scope.auditoriaFiltro.tabelasSelecionadas.forEach(element => {
                            config.params.entidadeNomeList.push(element.value);
                        });
                    }
                }
            }

            $scope.promise = RestService.Get('/auditoria', config)
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

        // Busca das Entidades Auditadas
        RestService.Get('/auditoria/entidade/search')
            .then(function (response) {
                if (response.status === 200 && response.data) {
                    $scope.auditoriaEntityList = response.data;
                }
            }, function errorCallback(response) {
                if (response.status === 400) {
                    $scope.showSimpleToast(response.data.message);
                }
            });

        EnumService.Get("AcaoAuditoriaEnum").then(function (dados) {
            $scope.acaoAuditoriaEnumList = dados;
        });

        $scope.showRelatorio = function () {
            $scope.loadingDownload = true;
            $rootScope.$broadcast('preloader:active');

            var config = {
                params: {},
                responseType: 'arraybuffer'
            };

            if ($scope.query.order)
                config.params.order = $scope.query.order;

            // Aplicação dos filtros
            if ($scope.auditoriaFiltro) {

                if ($scope.auditoriaFiltro.periodoInicial)
                    config.params.periodoInicialStr = $scope.auditoriaFiltro.periodoInicial.format('DD/MM/YYYY');

                if ($scope.auditoriaFiltro.periodoFinal)
                    config.params.periodoFinalStr = $scope.auditoriaFiltro.periodoFinal.format('DD/MM/YYYY');

                if ($scope.auditoriaFiltro.nome)
                    config.params.nome = $scope.auditoriaFiltro.nome;

                if ($scope.auditoriaFiltro.tabelasSelecionadas) {
                    // Filtro de status
                    config.params.entidadeNomeList = [];
                    if ($scope.auditoriaFiltro.tabelasSelecionadas && $scope.auditoriaFiltro.tabelasSelecionadas.length > 0) {
                        $scope.auditoriaFiltro.tabelasSelecionadas.forEach(element => {
                            config.params.entidadeNomeList.push(element.value);
                        });
                    }
                }
            }

            RestService.Get('/auditoria/relatorio/pdf', config)
                .then(function successCallback(response) {
                    $rootScope.$broadcast('preloader:hide');
                    var file = new Blob([response.data], { type: 'application/pdf' });
                    var fileURL = URL.createObjectURL(file);

                    $scope.loadingDownload = false;
                    window.open(fileURL);
                }, function errorCallback(response) {
                    $rootScope.$broadcast('preloader:hide');
                    if (response.status === 400) {
                        $scope.showSimpleToast("Não foi possível carregar os dados.")
                    }
                });

        };

        $scope.showDetalhe = function (ev, auditoria) {
            $scope.auditoria = angular.copy(auditoria);
            $scope.auditoria.criadoEm = moment(auditoria.criadoEm);

            $mdDialog.show({
                scope: $scope,
                preserveScope: true,
                controller: ['$scope', '$mdDialog', function ($scope, $mdDialog) {
                    $scope.cancel = function () {
                        $mdDialog.cancel();
                    };
                }],
                templateUrl: 'app/page/auditoria/auditoria.detalhe.tmpl.html',
                parent: angular.element(document.body),
                targetEvent: ev,
                clickOutsideToClose: true
            });
        };

        $scope.showSimpleToast = function (textContent) {
            $mdToast.show(
                $mdToast.simple()
                    .textContent(textContent)
                    .position('top right')
                    .hideDelay(10000)
            );
        };

    }

})();



