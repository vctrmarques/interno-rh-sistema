(function () {
    'use strict';

    angular.module('app.page')
        .controller('relatorioServidorPagBloqueadoCtrl', ['$scope', '$mdToast', '$rootScope', 'RestService', relatorioServidorPagBloqueadoCtrl]);

    function relatorioServidorPagBloqueadoCtrl($scope, $mdToast, $rootScope, RestService) {

        // Checa as regras de acesso da tela
        RestService.Get('/usuario/verificaPermissao/por/menu', { params: { menu: 'Rel. Aposentados e pensões' } })
            .then(function (response) {
                if (response.status === 200 && response.data) {

                    $scope.usuarioAdm = response.data.usuarioAdm;
                    $scope.podeGerenciar = false;

                    if (!$scope.usuarioAdm && !$scope.podeGerenciar)
                        $location.path('page/403');
                }
            }, function errorCallback(response) {
                if (response.status === 400) {
                    $scope.showSimpleToast(response.data.message);
                }
            });


        $scope.pensaoAlimenticia = null;
        $scope.pensionista = null;

        $scope.showRelatorio = function (formatoRelatorio) {
            $scope.formatoRelatorio = formatoRelatorio;

            // Validação dos filtros
            if (!$scope.fundosSelecionados || $scope.fundosSelecionados.length === 0) {
                $scope.showSimpleToast("Favor selecionar ao menos um fundo.");
                return;
            }

            var fundoIds = [];
            $scope.fundosSelecionados.forEach(element => {
                fundoIds.push(element.id);
            });

            var situacaoIds = [];
            if ($scope.situacaoFuncionalSelecionadas)
                $scope.situacaoFuncionalSelecionadas.forEach(element => {
                    situacaoIds.push(element.id);
                });

            var servidorSelecionadoId = null;
            if ($scope.servidorSelecionado && $scope.servidorSelecionado.id)
                servidorSelecionadoId = $scope.servidorSelecionado.id;

            var dataNascimento = null;
            if ($scope.dataNascimento)
                dataNascimento = moment($scope.dataNascimento).format("DD/MM/YYYY");

            var config = {
                params: {
                    fundosSelecionados: fundoIds,
                    situacaoFuncionalSelecionadas: situacaoIds,
                    servidorSelecionadoId: servidorSelecionadoId,
                    dataNascimentoSelecionada: dataNascimento,
                    pensaoAlimenticia: $scope.pensaoAlimenticia,
                    pensionista: $scope.pensionista
                },
                responseType: 'arraybuffer'
            };

            $scope.loadingDownload = true;
            $rootScope.$broadcast('preloader:active');

            RestService.Get('/relatorioServidorPagBloqueado/relatorio/' + $scope.formatoRelatorio, config)
                .then(function successCallback(response) {
                    if ($scope.formatoRelatorio === 'pdf') {
                        var file = new Blob([response.data], { type: 'application/pdf' });
                        var fileURL = URL.createObjectURL(file);

                        $rootScope.$broadcast('preloader:hide');
                        $scope.loadingDownload = false;
                        window.open(fileURL);

                    } else {
                        var file = new Blob([response.data], { type: 'application/vnd.ms-excel' });
                        var fileURL = URL.createObjectURL(file);

                        $rootScope.$broadcast('preloader:hide');
                        $scope.loadingDownload = false;
                        window.open(fileURL);

                    };
                }, function errorCallback(response) {
                    $rootScope.$broadcast('preloader:hide');
                    if (response.status === 400) {
                        $scope.showSimpleToast("Não foi possível carregar os dados.")
                    }
                });
        };

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



