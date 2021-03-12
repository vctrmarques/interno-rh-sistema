(function () {
    'use strict';

    angular.module('app.page')
        .controller('medicoCtrl', ['configValue', '$scope', '$mdToast', '$mdDialog', '$rootScope', 'EnumService', 'GenericoService', 'RestService', medicoCtrl]);

    function medicoCtrl(configValue, $scope, $mdToast, $mdDialog, $rootScope, EnumService, GenericoService, RestService) {

        GenericoService.VerificaPermissao('/usuario/verificaPermissao', 'ROLE_ADMIN').then(
            function (response) {
                if (response.status === 200) {
                    $scope.loadList();
                }
            }, function errorCallback(response) {
                if (response.status === 400) {
                    $location.path('page/403');
                }
            });

        $scope.botoesGestao = false;
        GenericoService.VerificaPermissao('/usuario/verificaPermissao', 'ROLE_ADMIN').then(
            function (response) {
                if (response.status === 200) {
                    $scope.botoesGestao = true;
                }
            });

        $scope.campoBusca = "";
        $scope.selectedFilial = [];
        $scope.query = {
            order: 'nome',
            limit: 10,
            page: 1
        };

        $scope.list = {
            "count": 0,
            "data": [],
            "listFiliais": []
        };

        $scope.limitOptions = [5, 10, 15];

        $scope.limpaFiltro = function () {
            delete $scope.campoBusca;
            delete $scope.selectedFilial;
            delete $scope.statusMedico;
            $scope.loadList();
        }

        EnumService.Get("ColetivoEnum").then(function (dados) {
            $scope.list.simNao = dados;
        });

        $scope.loadList = function () {
            $rootScope.$broadcast('preloader:active');

            var filialList = [];
            if ($scope.selectedFilial && $scope.selectedFilial.length > 0) {
                $scope.selectedFilial.forEach(element => {
                    filialList.push(element.id);
                });
            }

            var config = {
                params: {
                    page: $scope.query.page - 1,
                    size: $scope.query.limit,
                    order: $scope.query.order,
                    nome: $scope.campoBusca,
                    filialList: filialList,
                    statusMedico: $scope.statusMedico
                }
            };

            $scope.promise = GenericoService.GetAll('/medicos', config).then(
                function (response) {
                    if (response.status === 200) {
                        $scope.list.data = response.data.content;
                        $scope.list.count = response.data.totalElements;
                        $rootScope.$broadcast('preloader:hide');
                    }else {
                        $scope.showSimpleToast("Não foi possível carregar os dados.")
                        $rootScope.$broadcast('preloader:hide');
                    }
                });
        }

        $scope.carregarListaFiliais = function() {
            GenericoService.GetAllDropdown('listaFiliais', function (response) {
                if (response.status === 200) {
                    $scope.list.listFiliais = response.data;
                    $rootScope.$broadcast('preloader:hide');
                } else {
                    $scope.showSimpleToast("Não foi possível carregar os dados.")
                }
            })
        };

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
            RestService.Delete('/medico/' + $scope.idToDelete)
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
            GenericoService.GetAllRelatorio('/medicos').then(
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

            var titulo = { text: 'Tabela de Médicos', alignment: 'center', margin: [0, 10] };
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
                    widths: ['auto', '*', '*', '*', '*', '*'],
                    body: [
                        [{ text: 'Matrícula', fontSize: 10, fillColor: 'lightgray', style: 'tableHeader', alignment: 'center' },
                        { text: 'Status', fontSize: 10, fillColor: 'lightgray', style: 'tableHeader', alignment: 'center' },
                        { text: 'Nomes', fontSize: 10, fillColor: 'lightgray', style: 'tableHeader', alignment: 'center' },
                        { text: 'CRM', fontSize: 10, fillColor: 'lightgray', style: 'tableHeader', alignment: 'center' },
                        { text: 'Especialidade', fontSize: 10, fillColor: 'lightgray', style: 'tableHeader', alignment: 'center' },
                        { text: 'Filial', fontSize: 10, fillColor: 'lightgray', style: 'tableHeader', alignment: 'center' }
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
            var j;
            for (i = 0; i < content.length; i++) {
                var element = content[i];
                var especialidade = "";
                var barra = "";

                var matricula = element.matricula;
                var status = element.status  ? 'Ativo' : 'Inativo';
                var nomeMedico = element.nome;
                var crm = element.crm;
                var filial = element.filial.nomeFilial;

                var nomeEspecialidade = element.especialidadeMedica;
                for (j = 0; j < nomeEspecialidade.length; j++) {
                    var element = nomeEspecialidade[j];
                    especialidade = especialidade + barra + element.nome;
                    barra = " / ";
                }
                
                var body = [
                    { text: matricula, fontSize: 10, alignment: 'center' },
                    { text: status, fontSize: 10 },
                    { text: nomeMedico, fontSize: 10 },
                    { text: crm, fontSize: 10 },
                    { text: especialidade, fontSize: 10 },
                    { text: filial, fontSize: 10 }
                ]
                lista.table.body.push(body)
            }
            docDefinition.content.push(lista);

            return docDefinition;

        }

        $scope.carregarListaFiliais(); 
        
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



