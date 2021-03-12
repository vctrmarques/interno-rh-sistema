(function () {
    'use strict';

    angular.module('app.page').directive('ngDtLimit', function () {
        function dataLimite(scope, element, attrs) {  
            if(scope.item.dataLimite){
                var dt = new Date(scope.item.dataLimite);
                dt.setDate( dt.getDate() - 10 );
                if(dt<=new Date() ){
                    $(element).css('color', 'red');
                }  
            }  
        };
        return {
            link: dataLimite
        }
    }).controller('requisicaoPessoalCtrl', ['configValue', '$scope', '$mdToast', '$mdDialog', '$rootScope', 'GenericoService', 'EnumService', requisicaoPessoalCtrl]);

    function requisicaoPessoalCtrl(configValue, $scope, $mdToast, $mdDialog, $rootScope, GenericoService, EnumService) {

        $scope.botoesGestao = false;
        GenericoService.VerificaPermissao('/usuario/verificaPermissao', 'ROLE_ADMIN').then(
            function (response) {
                if (response.status === 200) {
                    $scope.botoesGestao = true;
                    $scope.loadList();
                }
            }, function errorCallback(response) {
                if (response.status === 400) {
                    $location.path('page/403');
                }
            });
            
        $scope.limitOptions = [5, 10, 15];
        $scope.busca;
        $scope.situacao
        $scope.query = {
            order: 'id',
            limit: 10,
            page: 1
        };

        $scope.list = {
            "count": 0,
            "data": []
        };

        EnumService.Get("SituacaoRequisicaoPessoalEnum").then(function (dados) {
            $scope.list.situacoes = dados;
        });

        $scope.loadList = function () {
            $rootScope.$broadcast('preloader:active');

            var config = {
                params: {
                    page: $scope.query.page - 1,
                    size: $scope.query.limit,
                    order: $scope.query.order,
                    processoSituacao: $scope.busca,
                    situacao:  $scope.situacao
                }
            };

            $scope.promise = GenericoService.GetAll('/requisicoesDePessoal', config).then(
                function (response) {
                    if (response.status === 200) {
                        console.log(response.data);
                        
                        $scope.list.data = response.data.content;
                        $scope.list.count = response.data.totalElements;
                        $rootScope.$broadcast('preloader:hide');
                    } else {
                        $scope.showSimpleToast("Não foi possível carregar os dados.")
                    }
                });
        }

        
        $scope.limpaFiltro = function () {
            $scope.busca = null;
            $scope.loadList();
        }

        $scope.deleteItem = function () {
            $rootScope.$broadcast('preloader:active');
            GenericoService.Delete('/requisicaoPessoal/' + $scope.idToDelete, function (response) {
                $rootScope.$broadcast('preloader:hide');
                if (response.status === 200) {
                    $scope.showSimpleToast(response.data.message);
                    $scope.loadList();
                }
            });
        }

        $scope.showSimpleToast = function (textContent) {
            $mdToast.show(
                $mdToast.simple()
                    .textContent(textContent)
                    .position('top right')
                    .hideDelay(3000)
            );
        };

       

        $scope.showConfirmEnviarConfirmacao = function (id) {
            // Appending dialog to document.body to cover sidenav in docs app
            var confirm = $mdDialog.confirm()
                .title('Deseja confirmar o envio desta Requisição para aprovação?')
                // .ariaLabel('Lucky day')
                .ok('Sim')
                .cancel('Não');
            $mdDialog.show(confirm).then(function () {
                $scope.enviarParaAprovacao(id);
            }, function () {
            });
        };

        $scope.enviarParaAprovacao = function (id){
            var situacao = 'Em processo';
            GenericoService.Update('/requisicaoPessoal/alterar/'+id, situacao, function (response) {
                $rootScope.$broadcast('preloader:hide');
                if (response.status === 201 && response.data.success) {
                    $scope.showSimpleToast(response.data.message);
                    $scope.loadList();
                }
            });
        }

        $scope.showConfirmCancelar = function (ev, idToCancelar) {
            $scope.idToCancelar = idToCancelar;
            // Appending dialog to document.body to cover sidenav in docs app
            var confirm = $mdDialog.confirm()
                .title('Deseja confirmar o cancelamento deste item?')
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

        $scope.cancelarRequisicao = function(){
            var situacao = 'Cancelada';
            GenericoService.Update('/requisicaoPessoal/alterar/'+$scope.idToCancelar, situacao, function (response) {
                $rootScope.$broadcast('preloader:hide');
                if (response.status === 201 && response.data.success) {
                    $scope.showSimpleToast(response.data.message);
                    $scope.loadList();
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

        $scope.showRelatorio = function () {
            $rootScope.$broadcast('preloader:active');
            var config = {
                params: {
                    page: $scope.query.page - 1,
                    size: 200
                }
            };
            GenericoService.GetAll('/processo/funcionarios', config).then(
                function (response) {
                    if (response.status === 200) {
                        pdfMake.createPdf(getDocDefinition(response.data.content)).open();
                        $rootScope.$broadcast('preloader:hide');
                    }
                });
        }

        function getAll(config) {
            GenericoService.GetAll('/processo/funcionarios', config).then(
                function (response) {
                    $scope.list.data = response.data.content;
                    $scope.list.count = response.data.totalElements;
                    $rootScope.$broadcast('preloader:hide');
                });
        }

        function getDocDefinition(content) {

            var docDefinition = {};
            docDefinition.content = [];

            var brasao = { image: configValue.logo, width: 70, alignment: 'center' };
            docDefinition.content.push(brasao);

            var orgao = { text: 'RH', alignment: 'center', margin: [0, 10], bold: true };
            docDefinition.content.push(orgao);

            var titulo = { text: 'Tabela de Processos', alignment: 'center', margin: [0, 10] };
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
                    widths: ['*'],
                    body: [
                        [
                            { text: 'Nome', fontSize: 10, fillColor: 'lightgray', style: 'tableHeader', alignment: 'center' },
                            // { text: 'Nome', fontSize: 10, fillColor: 'lightgray', style: 'tableHeader', alignment: 'center' },
                            // { text: 'Data Inspeção', fontSize: 10, fillColor: 'lightgray', style: 'tableHeader', alignment: 'center' },
                            // { text: 'Afastamento', fontSize: 10, fillColor: 'lightgray', style: 'tableHeader', alignment: 'center' }
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
                console.log(content);
                var body = []
                var element = content[i];


                body.push({ text: element.nome, fontSize: 10, alignment: 'center', fillColor: 'lightgray' })

                angular.forEach(element.processos, function (e) {
                    lista.table.body.push([{ text: 'Número do Processo: ' + e.numeroProcesso, fontSize: 10, alignment: 'center' }]);
                });
                lista.table.body.push(body)
            }
            docDefinition.content.push(lista);

            return docDefinition;

        }

        $scope.checkDataLimite = function(){
            
    
            console.log(document.getElementById('#requisicaoPessoalForm'));
            $('#teste8').css('color', 'red');
            
        }
        $scope.checkDataLimite();

    }

})();



