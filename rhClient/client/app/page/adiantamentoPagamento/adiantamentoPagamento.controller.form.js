(function () {
    'use strict';

    angular.module('app.page')
        .controller('adiantamentoPagamentoFormCtrl', ['$scope', '$mdToast', '$location', '$state', '$rootScope', '$q', '$mdDialog', '$mdMedia', 'GenericoService', adiantamentoPagamentoFormCtrl]);

    function adiantamentoPagamentoFormCtrl($scope, $mdToast, $location, $state, $rootScope, $q, $mdDialog, $mdMedia, GenericoService) {

        $scope.acessaTela = function () {
            GenericoService.VerificaPermissao('/usuario/verificaPermissao', 'ROLE_ADMIN').then(
                function (response) {
                    if (response.status === 200) {}
                },
                function errorCallback(response) {
                    if (response.status === 400) {
                        $location.path('page/403');
                    }
                });
        }

        $scope.list = {
            "listFiliais": [],
            "listLotacoes": []
        };

        $scope.selectedList = [];
        $scope.listFuncionarios = {
            "count": 0,
            "data": []
        };

        $scope.acessaTela();

        $scope.adiantamentoPagamento = {
            "funcionariosIds": []
        };

        $scope.query = {
            order: 'id',
            limit: 10,
            page: 1
        };

        $scope.list = {
            "recebimentos": [],
            "tiposPagamentos": []
        };

        $scope.adiantamentosList = [];

        $scope.adiantamentos = [];

        $scope.limpaFiltro = function () {
            delete $scope.search;
        }

        $scope.loadList = function () {
            $rootScope.$broadcast('preloader:active');

            var config = {
                params: {
                    page: $scope.query.page - 1,
                    size: $scope.query.limit,
                    order: $scope.query.order,
                    search: $scope.search,
                    filialId: $scope.adiantamentoPagamento.empresaFilialId,
                    lotacaoId: $scope.adiantamentoPagamento.lotacaoId
                }
            };

            $scope.promise = GenericoService.GetAll('/listFuncionariosByfilial', config).then(
                function (response) {
                    if (response.status === 200) {
                        $scope.listFuncionarios.data = response.data.content;
                        $scope.listFuncionarios.count = response.data.totalElements;
                        $rootScope.$broadcast('preloader:hide');
                    } else {
                        $scope.showSimpleToast("Não foi possível carregar os dados.")
                    }
                });
        }

        $scope.checkAll = function (checked) {
            $scope.checkall = !checked;
        }

        $scope.recebimentos = function () {
            $rootScope.$broadcast('preloader:active');
            GenericoService.GetAllDropdown('listRecebimentos', function (response) {
                if (response.status === 200) {
                    $scope.list.recebimentos = response.data;
                    $rootScope.$broadcast('preloader:hide');
                } else if (response.status === 500) {
                    $scope.showSimpleToast("Falha no carregamento de dados, favor contate o administrador do sistema");
                }
            });
        }



        $scope.tiposPagamentos = function () {
            $rootScope.$broadcast('preloader:active');
            GenericoService.GetAllDropdown('listTiposPagamentos', function (response) {
                if (response.status === 200) {
                    $scope.list.tiposPagamentos = response.data;
                    $rootScope.$broadcast('preloader:hide');
                } else if (response.status === 500) {
                    $scope.showSimpleToast("Falha no carregamento de dados, favor contate o administrador do sistema");
                }
            });
        }

        if ($state.params && $state.params.id) {
            $rootScope.$broadcast('preloader:active');
            GenericoService.GetById('/adiantamentoPagamento/' + $state.params.id, function (response) {
                $rootScope.$broadcast('preloader:hide');
                if (response.status === 200) {
                    $scope.adiantamentoPagamento = response.data;
                    $scope.adiantamentoPagamento.dataInicio = moment($scope.adiantamentoPagamento.dataInicio);
                    $scope.adiantamentoPagamento.dataFim = moment($scope.adiantamentoPagamento.dataFim);
                    $scope.carregarListaLotacoes($scope.adiantamentoPagamento.empresaFilialId);
                    $scope.bloquear = false;
                } else {
                    $scope.showSimpleToast("Adiantamento não encontrado na base");
                }
            });
        } else {
            $scope.bloquear = true;
        }

        $scope.carregarListaFiliais = function () {
            GenericoService.GetAllDropdown('listaFiliais', function (response) {
                if (response.status === 200) {
                    $scope.list.listFiliais = response.data;
                    $rootScope.$broadcast('preloader:hide');
                } else {
                    $scope.showSimpleToast("Não foi possível carregar os dados.")
                }
            })
        };

        $scope.carregarListaLotacoes = function (filialId) {
            if ($scope.adiantamentoPagamento.lotacaoId && !$state.params.id)
                delete $scope.adiantamentoPagamento.lotacaoId;
            GenericoService.GetById('/listaLotacoes/' + filialId, function (response) {
                if (response.status === 200) {
                    $scope.list.listLotacoes = response.data;
                    $rootScope.$broadcast('preloader:hide');
                } else {
                    $scope.showSimpleToast("Não foi possível carregar os dados.")
                }
            })
        };

        $scope.validPercentual = function () {
            if ($scope.adiantamentoPagamento.percentualAdiantamento > 40) {
                $scope.showSimpleToast('Percentual poder no maximo até 40%');
                delete $scope.adiantamentoPagamento.percentualAdiantamento;
            }
        }

        $scope.adicionarAdiantamento = function () {
            $scope.adiantamentoPagamento.funcionariosIds = [];
            $scope.adiantamentoPagamento.competencia = moment($scope.adiantamentoPagamento.dataInicio).format('YYYY/MM');

            $scope.adiantamentoPagamento.lotacao = $scope.list.listLotacoes.find(function (e) {
                return e.id == $scope.adiantamentoPagamento.lotacaoId;
            });

            for (var index = 0; index < $scope.selectedList.length; index++) {
                var element = $scope.selectedList[index];
                for (var k in element) {
                    if (typeof element[k] !== 'function') {
                        if (element[k]) {
                            $scope.adiantamentoPagamento.funcionariosIds.push(k);
                        } else {
                            $scope.adiantamentoPagamento.funcionariosIds.splice(index, 1);
                        }
                    }
                }
            }

            angular.forEach($scope.adiantamentoPagamento.funcionariosIds, function (e) {
                $scope.adiantamentoPagamento.funcionario = $scope.listFuncionarios.data.find(function (i) {
                    return e.toString() === i.id.toString();
                });
                $scope.newObject = {};
                angular.copy($scope.adiantamentoPagamento, $scope.newObject)
                $scope.adiantamentosList.push($scope.newObject);     
            });


            $scope.selectedList = [];
            $scope.adiantamentos.push($scope.adiantamentoPagamento);
            $scope.adiantamentoPagamento = {};
            $scope.adiantamentoPagamentoForm.$setUntouched();
        }

        $scope.save = function () {
            $rootScope.$broadcast('preloader:active');

            if ($scope.adiantamentoPagamento.id) {
                GenericoService.Update('/adiantamentoPagamento', $scope.adiantamentoPagamento, function (response) {
                    $rootScope.$broadcast('preloader:hide');
                    if (response.status === 201 && response.data.success) {
                        $scope.showSimpleToast(response.data.message);
                        $location.path('/adiantamentoPagamento/gestao');
                    } else if (response.status === 400) {
                        $scope.showSimpleToast(response.data.message);
                    }
                });
            } else {
                GenericoService.Create('/adiantamentoPagamento', $scope.adiantamentos, function (response) {
                    $rootScope.$broadcast('preloader:hide');

                    if (response.status === 201 && response.data.success) {
                        $scope.showSimpleToast(response.data.message);
                        $location.path('/adiantamentoPagamento/gestao');
                    } else if (response.status === 400) {
                        $scope.showSimpleToast(response.data.message);
                        $location.path('/adiantamentoPagamento/formulario');
                    }
                });
            }
        }

        $scope.carregarListaFiliais();
        $scope.recebimentos();
        $scope.tiposPagamentos();

        $scope.goBack = function () {
            $location.path('/adiantamentoPagamento/gestao');
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
