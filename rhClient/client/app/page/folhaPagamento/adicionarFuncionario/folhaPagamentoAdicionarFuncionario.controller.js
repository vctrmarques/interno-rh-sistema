(function () {
    'use strict';

    angular.module('app.page')
        .controller('folhaPagamentoAdicionarFuncionarioCtrl', ['$state', '$q', '$location', 'configValue', '$scope', '$mdToast', '$mdDialog', '$rootScope', 'GenericoService', 'EnumService', folhaPagamentoAdicionarFuncionarioCtrl]);

    function folhaPagamentoAdicionarFuncionarioCtrl($state, $q, $location, configValue, $scope, $mdToast, $mdDialog, $rootScope, GenericoService, EnumService) {

        GenericoService.VerificaPermissao('/usuario/verificaPermissao', 'ROLE_ADMIN')
            .then(
                function (response) {
                    if (response.status === 200) {

                    }
                },
                function errorCallback(response) {
                    if (response.status === 400) {
                        $location.path('page/403');
                    }
                });



        $scope.list = {
            "count": 0,
            "data": []
        };

        $scope.query = {
            order: 'id',
            limit: 30,
            page: 1
        };

        $scope.selected = [];
        $scope.funcionarios = [];
        $scope.lotacaoId;
        $scope.nomeFuncionario;
        $scope.dataAdmissao;
        $scope.folhaPagamentoFuncionarioVerbaRequest = {};

        if ($state.params && $state.params.id) {
            $rootScope.$broadcast('preloader:active');
            GenericoService.GetById('/folhaPagamento/' + $state.params.id, function (response) {
                $rootScope.$broadcast('preloader:hide');
                if (response.status === 200) {
                    $scope.folha = response.data;
                    $scope.getFuncionarios();
                } else {
                    $scope.showSimpleToast("Folha não encontrada na base");
                }
            });
        }

        $scope.showAdicionarFuncionario = function (ev, idToDelete) {
            $scope.idToDelete = idToDelete;
            // Appending dialog to document.body to cover sidenav in docs app
            var confirm = $mdDialog.confirm()
                .title('Tem certeza que deseja adicionar estes funcionários a esta folha?')
                .ok('Sim')
                .cancel('Não');

            $mdDialog.show(confirm).then(function () {
                $scope.adicionarFuncionario();
            }, function () { });
        };

        $scope.adicionarFuncionario = function () {
            $scope.folhaPagamentoFuncionarioVerbaRequest.folhaPagamentoId = $scope.folha.id;
            $scope.folhaPagamentoFuncionarioVerbaRequest.funcionariosId = $scope.selected;
            GenericoService.Create('/contracheque/adicionarFuncionario', $scope.folhaPagamentoFuncionarioVerbaRequest, function (response) {
                $rootScope.$broadcast('preloader:hide');
                if (response.status === 201 && response.data.success) {
                    $scope.showSimpleToast(response.data.message);
                    $state.reload();
                } else if (response.status === 400) {
                    $scope.showSimpleToast(response.data.message);
                    //$location.path('/folhaPagamento/formulario');
                }
            });
        };

        $scope.getLotacao = function () {
            var deferred = $q.defer();
            GenericoService.GetAll('/lotacoes', null)
                .then(function (response) {
                    if (response.status === 200) {
                        $scope.lotacoes = response.data.content;
                    }
                });
            return deferred.promise;
        };
        $scope.getLotacao();

        $scope.getFuncionarios = function () {
            $rootScope.$broadcast('preloader:active');
            var config = {
                params: {
                    page: $scope.query.page - 1,
                    size: $scope.query.limit,
                    folhaPagamentoId: $state.params.id,
                    filialId: $scope.folha.filial.id,
                    nome: $scope.nomeFuncionario,
                    lotacaoId: $scope.lotacaoId,
                    dataAdmissao: $scope.dataAjustada,
                    order: $scope.query.order
                }
            };

            $scope.tratarData = function (dataAdmissaoFiltro) {
                $scope.dataAjustada = moment($scope.dataAdmissaoFiltro).format('DD/MM/YYYY');
                $scope.getFuncionarios();
            }


            var deferred = $q.defer();
            GenericoService.GetAll('/contracheque/funcionario/no/leaf/', config)
                .then(function (response) {
                    if (response.status === 200) {
                        $rootScope.$broadcast('preloader:hide');
                        $scope.list.data = response.data.content;
                        $scope.list.count = response.data.totalElements;
                    }
                });
            return deferred.promise;
        };
        $scope.limpaFiltro = function () {
            delete $scope.nomeFuncionario;
            delete $scope.lotacaoId;
            delete $scope.dataAdmissaoFiltro;
        }

        $scope.goBack = function () {
            $location.path('/folhaPagamento/detalhamento/' + $scope.folha.id);
        }

        $scope.showSimpleToast = function (textContent) {
            $mdToast.show(
                $mdToast.simple()
                    .textContent(textContent)
                    .position('top right')
                    .hideDelay(4000)
            );
        };

        $scope.toggle = function (item, list) {
            var idx = list.indexOf(item);
            if (idx > -1) {
                list.splice(idx, 1);
            } else {
                list.push(item);
            }
        };

        $scope.exists = function (item, list) {
            return list.indexOf(item) > -1;
        };

        $scope.isIndeterminate = function () {
            return ($scope.selected.length !== 0 &&
                $scope.selected.length !== $scope.list.data.length);
        };

        $scope.isChecked = function () {
            return $scope.selected.length === $scope.list.data.length;
        };

        $scope.toggleAll = function () {
            if ($scope.selected.length === $scope.list.data.length) {
                $scope.selected = [];
            } else if ($scope.selected.length === 0 || $scope.selected.length > 0) {

                angular.forEach($scope.list.data, function (f) {
                    if (!f.disableCheck)
                        $scope.selected.push(f.id);
                });
            }
        };
    }
})();
