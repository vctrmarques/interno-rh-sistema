(function () {
    'use strict';

    angular.module('app.page')
        .controller('pensaoPrevidenciariaCtrl', ['configValue', '$scope', '$mdToast', '$mdDialog', '$rootScope', 'GenericoService', pensaoPrevidenciariaCtrl]);

    function pensaoPrevidenciariaCtrl(configValue, $scope, $mdToast, $mdDialog, $rootScope, GenericoService) {

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


        $scope.query = {
            order: null,
            limit: 10,
            page: 1
        };

        $scope.list = {
            "count": 0,
            "data": []
        };

        $scope.limpaFiltro = function () {
            delete $scope.searchFuncionario;
            delete $scope.searchPensionista;
        }

        $scope.loadList = function () {
            $rootScope.$broadcast('preloader:active');


            var config = {
                params: {
                    page: $scope.query.page - 1,
                    size: $scope.query.limit,
                    order: $scope.query.order,
                    searchFuncionario: $scope.searchFuncionario,
                    searchPensionista: $scope.searchPensionista
                }
            };

            $scope.promise = GenericoService.GetAll('/pensoesPrevidenciarias', config).then(
                function (response) {
                    if (response.status === 200) {
                        $scope.list.data = response.data.content;
                        $scope.list.count = response.data.totalElements;
                        console.log($scope.list.data);
                        $rootScope.$broadcast('preloader:hide');
                    } else {
                        $scope.showSimpleToast("Não foi possível carregar os dados.")
                    }
                });
        }

        $scope.limitOptions = [5, 10, 15];

        $scope.deleteItem = function () {
            $rootScope.$broadcast('preloader:active');
            GenericoService.Delete('/pensaoPrevidenciaria/' + $scope.idToDelete, function (response) {
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
            GenericoService.GetAllRelatorio('/pensoesPrevidenciarias').then(
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

            var titulo = { text: 'Pensões Previdênciarias', alignment: 'center', margin: [0, 10] };
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
                    widths: ['auto', 'auto', '*'],

                    body: [
                        [{ text: 'Matrícula', fontSize: 10, fillColor: 'lightgray', style: 'tableHeader', alignment: 'center' },
                        { text: 'Ex-segurado', fontSize: 10, fillColor: 'lightgray', style: 'tableHeader', alignment: 'center' },
                        { text: 'Pensionista', fontSize: 10, fillColor: 'lightgray', style: 'tableHeader', alignment: 'center' }
                        ]
                    ]
                },
            }

            var matriculaPensionista = [];
            var nomePensionista = [];
            var i = 0;
            var contador = 0;
            var span;
            var element = [];
            var auxiliar = [];
            var centralizar;
            var parametroCentralizar = '\n';

            $scope.loop = function () {

                centralizar = '';
                span = 0;

                for (contador; contador < content.length; contador++) {

                    auxiliar = content[contador];
                    span++;

                    if (!funcionarioAnterior) {

                        var funcionarioAnterior = auxiliar;

                    }

                    if (auxiliar.funcionario.matricula != funcionarioAnterior.funcionario.matricula) {

                        auxiliar = content[contador - 1];
                        span--;
                        break;

                    }

                }

                for (var contadorDoCentralizador = 0; contadorDoCentralizador < span; contadorDoCentralizador++) {

                    centralizar = centralizar + parametroCentralizar;

                }

            }

            for (i; i < content.length; i++) {

                if (!auxiliar.funcionario) {

                    $scope.loop();

                }

                element = content[i];

                if (auxiliar.funcionario.matricula != element.funcionario.matricula) {

                    $scope.loop();

                }

                if (element.funcionario.matricula != matriculaAnterior) {

                    var matriculaAnterior = element.funcionario.matricula;
                    var matriculaFuncionario = { rowSpan: span, text: centralizar + element.funcionario.matricula, fontSize: 10, alignment: 'center' };
                    var nomeFuncionario = { rowSpan: span, text: centralizar + element.funcionario.nome, fontSize: 10, alignment: 'center' };

                }

                if (element.funcionario.matricula == matriculaAnterior) {

                    matriculaPensionista = { text: element.matricula, fontSize: 10 };
                    nomePensionista = { text: element.nome, fontSize: 10 };

                }

                var body = [
                    matriculaFuncionario, nomeFuncionario,
                    [{
                        table:
                        {
                            widths: ['auto', '*'],
                            body:
                                [[
                                    { text: 'Matricula:\nNome:', fontSize: 10, fillColor: 'lightgray', style: 'tableHeader', alignment: 'center' },
                                    [matriculaPensionista, nomePensionista]
                                ]]
                        }
                    }]
                ]

                lista.table.body.push(body)
            }

            docDefinition.content.push(lista);

            return docDefinition;

        }

    }

})();
