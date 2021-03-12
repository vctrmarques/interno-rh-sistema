(function () {
    'use strict';

    angular.module('app.page')
        .controller('folhaPagamentoAdicionarLancamentoCtrl', ['$state', '$q', '$location', '$scope', '$mdToast', '$rootScope', 'GenericoService', folhaPagamentoAdicionarLancamentoCtrl]);

    function folhaPagamentoAdicionarLancamentoCtrl($state, $q, $location, $scope, $mdToast, $rootScope, GenericoService) {

        //Declaração de variáveis 
        $scope.folha = {};
        $scope.search;
        $scope.filialFuncionarioFiltro;
        $scope.lotacaoFuncionarioFiltro;
        $scope.codigoDesricaoVerbaFiltro;
        $scope.verbas = [];
        $scope.verbasSelecionadas = [];
        $scope.funcionariosSelecionados = [];

        //Busca de lotações - Filtro funcionário
        $scope.loadLotacoes = function () {
            GenericoService.GetAllDropdown('folhaPagamento/listaLotacoes/' + $scope.folha.filial.id, function (response) {
                if (response.status === 200) {
                    $scope.lotacoes = response.data;
                } else if (response.status === 500) {
                    $scope.showSimpleToast("Falha no carregamento de dados, favor contate o administrador do sistema");
                }
            });
        };

        $scope.limitOptions = [5, 10, 15];

        $scope.listFuncionario = {
            "count": 0,
            "data": []
        };

        $scope.listVerba = {
            "count": 0,
            "data": []
        };

        $scope.queryFuncionario = {
            order: 'nome',
            limit: 10,
            page: 1
        };

        $scope.queryVerba = {
            order: 'descricaoVerba',
            limit: 10,
            page: 1
        };

        if ($state.params && $state.params.id) {
            $rootScope.$broadcast('preloader:active');
            GenericoService.GetById('/folhaPagamento/' + $state.params.id, function (response) {
                $rootScope.$broadcast('preloader:hide');
                if (response.status === 200) {
                    $scope.folha = response.data;
                    $scope.loadLotacoes();
                    $scope.getFuncionarios();
                    $scope.getVerbas();
                } else {
                    $scope.showSimpleToast("Folha não encontrada na base");
                }
            });
        } else {
            $scope.goBack();
        }

        $scope.getVerbas = function () {
            $rootScope.$broadcast('preloader:active');
            var config = {
                params: {
                    page: $scope.queryVerba.page - 1,
                    size: $scope.queryVerba.limit,
                    order: $scope.queryVerba.order,
                    descricao: $scope.codigoDesricaoVerbaFiltro ? $scope.codigoDesricaoVerbaFiltro : null
                }
            };
            $scope.promise = GenericoService.GetAll('/folhaPagamento/verbas', config).then(
                function (response) {
                    if (response.status === 200) {
                        $scope.listVerba.data = response.data.content;
                        $scope.listVerba.count = response.data.totalElements;
                        $rootScope.$broadcast('preloader:hide');
                    } else {
                        $scope.showSimpleToast("Não foi possível carregar os dados.")
                    }
                });
        }

        $scope.limpaFiltro = function () {
            $scope.descricaoVerbaBusca = null;
        }

        $scope.goBack = function () {
            $location.path('/folhaPagamento/detalhamento/' + $scope.folha.id);
        }

        $scope.adicionarVerbasAoFuncionario = function () {

            //Validações
            if (!$scope.funcionariosSelecionados || $scope.funcionariosSelecionados.length === 0) {
                $scope.showSimpleToast("Favor Selecionar Funcionários.");
                return;
            }

            if (!$scope.verbasSelecionadas || $scope.verbasSelecionadas.length === 0) {
                $scope.showSimpleToast("Favor Selecionar Verbas.");
                return;
            } else {
                var verbaSemValor = false;
                for (let index = 0; index < $scope.verbasSelecionadas.length; index++) {
                    const verbaSelecionada = $scope.verbasSelecionadas[index];
                    if (!verbaSelecionada.valor || verbaSelecionada.valor === 0) {
                        verbaSemValor = true;
                    }
                }
                if (verbaSemValor) {
                    $scope.showSimpleToast("Existem Verbas Sem Valor.");
                    return;
                }
            }

            var funcionariosId = [];
            angular.forEach($scope.funcionariosSelecionados, function (funcionarioSelecionado) {
                funcionariosId.push(funcionarioSelecionado.id);
            });

            var verbaManualList = [];
            angular.forEach($scope.verbasSelecionadas, function (verbaSelecionada) {
                var verbaManual = {
                    verbaId: verbaSelecionada.id,
                    valor: verbaSelecionada.valor
                }
                verbaManualList.push(verbaManual);
            })

            var body = {
                folhaPagamentoId: $scope.folha.id,
                funcionariosId: funcionariosId,
                verbaManualList: verbaManualList
            };

            $rootScope.$broadcast('preloader:active');
            var deferred = $q.defer();

            GenericoService.Create('/lancamento/adicionarVerbaAoFuncionario', body, function (response) {
                $rootScope.$broadcast('preloader:hide');
                if (response.status === 201 && response.data.success) {
                    $scope.showSimpleToast(response.data.message);
                    $location.path('/folhaPagamento/detalhamento/' + $scope.folha.id);
                } else if (response.status === 400) {
                    $scope.showSimpleToast(response.data.message);
                }
            });
            return deferred.promise;
        }

        $scope.limpaFiltroVerba = function () {
            delete $scope.codigoDesricaoVerbaFiltro;
            $scope.getVerbas();
        }

        $scope.limpaFiltroFuncionario = function () {
            delete $scope.search;
            delete $scope.filialFuncionarioFiltro;
            delete $scope.lotacaoFuncionarioFiltro;
            $scope.getFuncionarios();
        }

        $scope.getFuncionarios = function () {
            $rootScope.$broadcast('preloader:active');
            var config = {
                params: {
                    page: $scope.queryFuncionario.page - 1,
                    size: $scope.queryFuncionario.limit,
                    order: $scope.queryFuncionario.order,
                    search: $scope.search,
                    filialId: $scope.folha.filial.id,
                    lotacaoId: $scope.lotacaoFuncionarioFiltro ? $scope.lotacaoFuncionarioFiltro.id : null
                }
            };
            var deferred = $q.defer();
            GenericoService.GetAll('/folhaPagamento/funcionarios', config)
                .then(function (response) {
                    $rootScope.$broadcast('preloader:hide');
                    if (response.status === 200) {
                        $scope.listFuncionario.data = response.data.content;
                        $scope.listFuncionario.count = response.data.totalElements;
                    }
                });
            return deferred.promise;
        };

        $scope.showSimpleToast = function (textContent) {
            $mdToast.show(
                $mdToast.simple()
                    .textContent(textContent)
                    .position('top right')
                    .hideDelay(4000)
            );
        };

    }
})();
