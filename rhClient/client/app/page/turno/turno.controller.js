(function () {
    'use strict';

    angular.module('app.page')
        .controller('turnoCtrl', ['configValue', '$scope', '$mdToast', '$mdDialog', '$rootScope', 'GenericoService', '$filter', turnoCtrl]);

    function turnoCtrl(configValue, $scope, $mdToast, $mdDialog, $rootScope, GenericoService, $filter) {

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

        $scope.descricaoBusca = "";
        $scope.query = {
            order: 'dataInicio',
            limit: 10,
            page: 1
        };

        $scope.list = {
            "count": 0,
            "data": []
        };

        $scope.limpaFiltro = function () {
            delete $scope.codigo;
        }

        $scope.loadList = function () {
            $rootScope.$broadcast('preloader:active');

            
            var config = {
                params: {
                    page: $scope.query.page - 1,
                    size: $scope.query.limit,
                    order: $scope.query.order,
                    codigo: $scope.codigo
                }
            };

            $scope.promise = GenericoService.GetAll('/turnos', config).then(
                function (response) {
                    if (response.status === 200) {
                        $scope.list.data = response.data.content;
                        $scope.list.count = response.data.totalElements;
                        $rootScope.$broadcast('preloader:hide');
                    } else {
                        $scope.showSimpleToast("N??o foi poss??vel carregar os dados.")
                    }
                });
        }

        $scope.limitOptions = [5, 10, 15];

        $scope.deleteItem = function () {
            $rootScope.$broadcast('preloader:active');
            GenericoService.Delete('/turno/' + $scope.idToDelete, function (response) {
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
                .title('Deseja confirmar a exclus??o deste item?')
                .textContent('Esta a????o n??o poder?? ser desfeita.')
                // .ariaLabel('Lucky day')
                .targetEvent(ev)
                .ok('Sim')
                .cancel('N??o');

            $mdDialog.show(confirm).then(function () {
                $scope.deleteItem();
            }, function () {
            });
        };

        $scope.showRelatorio = function () {
            $rootScope.$broadcast('preloader:active');
            GenericoService.GetAllRelatorio('/turnos').then(
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

            var titulo = { text: 'Tabela de Turno', alignment: 'center', margin: [0, 10] };
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
                    widths: ['auto', 'auto', 'auto', 'auto', 'auto', 'auto'],
                    body: [
                        [{ text: 'C??digo', fontSize: 10, fillColor: 'lightgray', style: 'tableHeader', alignment: 'center' },
                        { text: 'Ativo', fontSize: 10, fillColor: 'lightgray', style: 'tableHeader', alignment: 'center' },
                        { text: 'Hor??rio', fontSize: 10, fillColor: 'lightgray', style: 'tableHeader', alignment: 'center' },
                        { text: 'Jornada Di??ria (h)', fontSize: 10, fillColor: 'lightgray', style: 'tableHeader', alignment: 'center' },
                        { text: 'Intervalo', fontSize: 10, fillColor: 'lightgray', style: 'tableHeader', alignment: 'center' },
                        { text: 'Folgas na Semana', fontSize: 10, fillColor: 'lightgray', style: 'tableHeader', alignment: 'center' },
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
                var element = content[i];
                var body = [
                    { text: element.codigo, fontSize: 10, alignment: 'center' },
                    { text: element.ativo ? 'Sim' : 'N??o', fontSize: 10 }
                ];

                if(!element.horarioFlexivel && !element.intervaloFlexivel){
                    var text =  $filter('date')(element.horarioFlexivelInicio, 'HH:mm') + ' ??s ' + 
                    $filter('date')(element.horarioFlexivelInicio, 'HH:mm') + ' | ' +
                    $filter('date')(element.intervaloFlexivelFim, 'HH:mm') + ' ??s ' +
                    $filter('date')(element.horarioFlexivelFim, 'HH:mm');
                }

                if(!element.horarioFlexivel && element.intervaloFlexivel){
                    var text =  $filter('date')(element.horarioFlexivelInicio, 'HH:mm') + ' ??s ' + 
                    $filter('date')(element.horarioFlexivelFim, 'HH:mm') ;  
                }

                if(element.horarioFlexivel){
                    var text =  "Hor??rio Flex??vel";
                }

                body.push({
                    text: text, fontSize: 10
                });

                body.push({text: element.jornada, fontSize: 10,  alignment: 'center'});
                body.push({text: element.intervalo, fontSize: 10,  alignment: 'center'});

                var text = '';
                if(element.turnoFolgaResponse.length){
                    
                    for(var i2 = 0; i2 < element.turnoFolgaResponse.length; i2++){
                        text +=  element.turnoFolgaResponse[i2].stringDia;

                        if((i2 + 1) < element.turnoFolgaResponse.length){
                            text += ', ';
                        }
                    }
                }

                body.push({text: text, fontSize: 10});
                lista.table.body.push(body)
            }
            docDefinition.content.push(lista);

            return docDefinition;

        }

    }

  

})();