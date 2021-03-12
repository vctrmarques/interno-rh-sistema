(function () {
    'use strict';

    angular.module('app.page')
        .controller('nivelSalarialHistoricoFormCtrl', ['$scope', '$mdToast', '$location', '$state', '$rootScope', 'GenericoService', nivelSalarialHistoricoFormCtrl]);

    function nivelSalarialHistoricoFormCtrl($scope, $mdToast, $location, $state, $rootScope, GenericoService) {

        GenericoService.VerificaPermissao('/usuario/verificaPermissao', 'ROLE_ADMIN').then(
            function (response) {
                if (response.status === 200) {
                }
            }, function errorCallback(response) {
                if (response.status === 400) {
                    $location.path('page/403');
                }
            });


        $scope.detalhes = false;
        if ($state.params && $state.params.detalhes) {
            $scope.detalhes = true;
        }

        $scope.origemAjusteSelecionado = 'Todas';
        $scope.nivelSalarialHistoricosSelecionados = [];

        $scope.query = {
            order: 'nivelSalarial.descricao',
            limit: 10,
            page: 1
        };

        $scope.list = {
            "count": 0,
            "data": []
        };

        $scope.limitOptions = [5, 10, 15];

        $scope.loadList = function () {
            var config = {
                params: {
                    page: $scope.query.page - 1,
                    size: $scope.query.limit,
                    order: $scope.query.order,
                    nivelSalarialId: $state.params.nivelSalarialId,
                    origemAjuste: $scope.origemAjusteSelecionado
                }
            };

            $scope.promise = GenericoService.GetAll('/nivelSalarialHistoricos', config).then(
                function (response) {
                    if (response.status === 200) {
                        $scope.list.data = response.data.content;
                        $scope.list.count = response.data.totalElements;
                        $rootScope.$broadcast('preloader:hide');
                        $scope.nivelSalarialHistoricosSelecionados = [];
                    } else {
                        $scope.showSimpleToast("Não foi possível carregar os dados.")
                    }
                });
        }

        $rootScope.$broadcast('preloader:active');
        GenericoService.GetById('/nivelSalarial/' + $state.params.nivelSalarialId, function (response) {
            if (response.status === 200) {
                $scope.nivelSalarial = response.data;
                $scope.loadList();
            } else {
                $scope.showSimpleToast("NivelSalarialHistorico não encontrado na base");
            }
        });

        $scope.carregarSimuladorNivelSalarial = function () {
            var simuladorNivelSalarialId = $scope.nivelSalarialHistoricosSelecionados[0].simuladorNivelSalarialId;
            if (simuladorNivelSalarialId) {
                $rootScope.$broadcast('preloader:active');
                GenericoService.GetById('/simuladorNivelSalarial/' + simuladorNivelSalarialId, function (response) {
                    if (response.status === 200) {
                        $scope.simuladorNivelSalarial = response.data;
                    } else {
                        $scope.showSimpleToast("NivelSalarialHistorico não encontrado na base");
                    }
                });
            }
        }

        $scope.goBack = function () {
            $location.path('/nivelSalarialHistorico/gestao');
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