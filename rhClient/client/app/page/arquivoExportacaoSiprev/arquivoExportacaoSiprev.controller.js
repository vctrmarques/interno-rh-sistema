(function () {
    'use strict';

    angular.module('app.page')
        .controller('arquivoExportacaoSiprevCtrl', ['$scope', '$mdToast', '$rootScope', 'RestService', arquivoExportacaoSiprevCtrl]);

    function arquivoExportacaoSiprevCtrl($scope, $mdToast, $rootScope, RestService) {

        // Checa as regras de acesso da tela
        RestService.Get('/usuario/verificaPermissao/por/menu', { params: { menu: 'Exportação SIPREV' } })
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

        // Lista de tipos de exportação para a situação funcional "Pensionista"
        $scope.tipoExportacaoPensionistaList = [];
        $scope.tipoExportacaoPensionistaList.push("Pensionista");
        $scope.tipoExportacaoPensionistaList.push("Benefício pensionista");
        $scope.tipoExportacaoPensionistaList.push("Dependente");
        $scope.tipoExportacaoPensionistaList.push("Alíquota");
        $scope.tipoExportacaoPensionistaList.push("CNIS - RPPS");

        // Lista de tipos de exportação para a situação funcional "Aposentado e Ativo"
        $scope.tipoExportacaoAposOuAtivoList = [];
        $scope.tipoExportacaoAposOuAtivoList.push("Servidor");
        $scope.tipoExportacaoAposOuAtivoList.push("Dependente");
        $scope.tipoExportacaoAposOuAtivoList.push("Cargo");
        $scope.tipoExportacaoAposOuAtivoList.push("Histórico funcional");
        $scope.tipoExportacaoAposOuAtivoList.push("Tempo Contribuição RGPS");
        $scope.tipoExportacaoAposOuAtivoList.push("Tempo Contribuição RPPS");
        $scope.tipoExportacaoAposOuAtivoList.push("Tempo sem contribuição");
        $scope.tipoExportacaoAposOuAtivoList.push("Benefício servidor");
        $scope.tipoExportacaoAposOuAtivoList.push("Histórico Financeiro");
        $scope.tipoExportacaoAposOuAtivoList.push("Alíquota");
        $scope.tipoExportacaoAposOuAtivoList.push("Função gratificada");
        $scope.tipoExportacaoAposOuAtivoList.push("Tempo fictício");
        $scope.tipoExportacaoAposOuAtivoList.push("CNIS - RPPS");
        $scope.tipoExportacaoAposOuAtivoList.push("Órgão");
        $scope.tipoExportacaoAposOuAtivoList.push("Carreira");

        $scope.formatoArqSelecionado = "xml";

        $scope.changeSitFuncional = function () {
            $scope.tipoExportacaoSelecionada = null;
            if ($scope.situacaoFuncionalSelecionada.label === 'PENSIONISTA') {
                $scope.tipoExportacaoList = $scope.tipoExportacaoPensionistaList;
            } else {
                $scope.tipoExportacaoList = $scope.tipoExportacaoAposOuAtivoList;
            }
        }

        // Busca dos Anos das Comptetências Fechadas
        RestService.Get('/arquivoExportacaoSiprev/situacao/funcional/search')
            .then(function (response) {
                if (response.status === 200 && response.data) {
                    $scope.situacaoFuncionalList = response.data;
                } else if (response.status === 200 && !response.data) {
                    $scope.showSimpleToast("Não existem anos de competência disponíveis.");
                }
            }, function errorCallback(response) {
                if (response.status === 400) {
                    $scope.showSimpleToast(response.data.message);
                }
            });

        $scope.showRelatorio = function () {

            // Validação dos filtros
            if (!$scope.situacaoFuncionalSelecionada) {
                $scope.showSimpleToast("Favor selecionar uma situação funcional.");
                return;
            }

            if (!$scope.tipoExportacaoSelecionada) {
                $scope.showSimpleToast("Favor selecionar um tipo de exportação.");
                return;
            }

            if (!$scope.formatoArqSelecionado) {
                $scope.showSimpleToast("Favor selecionar um formato de arquivo.");
                return;
            }

            if (!$scope.operacaoSelecionada) {
                $scope.showSimpleToast("Favor selecionar a operação.");
                return;
            }

            if (!$scope.nomeArquivo) {
                $scope.showSimpleToast("Favor inserir o nome do arquivo.");
                return;
            }

            var config = {
                params: {
                    situacaoFuncionalId: $scope.situacaoFuncionalSelecionada.id,
                    tipoExportacaoSelecionada: $scope.tipoExportacaoSelecionada,
                    operacaoSelecionada: $scope.operacaoSelecionada,
                    nomeArquivo: $scope.nomeArquivo
                },
                responseType: 'arraybuffer'
            };

            $scope.loadingDownload = true;
            $rootScope.$broadcast('preloader:active');

            RestService.Get('/arquivoExportacaoSiprev/' + $scope.formatoArqSelecionado, config)
                .then(function successCallback(response) {
                    if ($scope.formatoArqSelecionado === 'xml') {

                        var bb = new Blob([response.data], { type: 'text/plain' });

                        var pom = document.createElement('a');
                        pom.setAttribute('href', window.URL.createObjectURL(bb));
                        pom.setAttribute('download', $scope.nomeArquivo + "_" + moment(new Date()).format("DD_MM_YYYY") + ".xml");
                        pom.dataset.downloadurl = ['text/plain', pom.download, pom.href].join(':');
                        pom.draggable = true;
                        pom.classList.add('dragout');

                        pom.click();

                        $rootScope.$broadcast('preloader:hide');
                        $scope.loadingDownload = false;

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



