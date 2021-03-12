(function () {
    'use strict';

    angular.module('app.page')
        .controller('diaUtilCtrl', ['configValue', '$scope', '$mdToast', '$mdDialog', '$rootScope', 'GenericoService', '$filter', diaUtilCtrl]);

    function diaUtilCtrl(configValue, $scope, $mdToast, $mdDialog, $rootScope, GenericoService, $filter) {

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

        $scope.list = {
            "data": {
                "ano": ""
              , "dadosMeses": []
            }
        };
        $scope.total = {};
        $scope.anoTotal = 0;
        $scope.segundaTotal = 0;
        $scope.tercaTotal = 0;
        $scope.quartaTotal = 0;
        $scope.quintaTotal = 0;
        $scope.sextaTotal = 0;
        $scope.sabadoTotal = 0;
        $scope.domingoTotal = 0;
        $scope.temDadosDoAno;

        $scope.mesesDoAno =
            { 
                 "01": "Janeiro"
                ,"02": "Fevereiro"
                ,"03": "Março"
                ,"04": "Abril"
                ,"05": "Maio"
                ,"06": "Junho"
                ,"07": "Julho"
                ,"08": "Agosto"
                ,"09": "Setembro"
                ,"10": "Outubro"
                ,"11": "Novembro"
                ,"12": "Dezembro"
            }
        
        
        $scope.calculandoDiaUtil = function (dadosDosMeses) {
            var keys = Object.keys(dadosDosMeses);
            var len = keys.length;
            $scope.total = {"0": 0, "1": 0, "2": 0, "3": 0, "4": 0, "5": 0, "6": 0, "7": 0, "8": 0, "9": 0, "10": 0, "11": 0};
            $scope.anoTotal = 0;
            $scope.segundaTotal = 0;
            $scope.tercaTotal = 0;
            $scope.quartaTotal = 0;
            $scope.quintaTotal = 0;
            $scope.sextaTotal = 0;
            $scope.sabadoTotal = 0;
            $scope.domingoTotal = 0;
            
            for(var i = 0; i < len; i++){

                $scope.total[i] += dadosDosMeses[i].segunda;
                $scope.segundaTotal += dadosDosMeses[i].segunda;
                 
                $scope.total[i] += dadosDosMeses[i].terca;
                $scope.tercaTotal += dadosDosMeses[i].terca;
                $scope.total[i] += dadosDosMeses[i].quarta;
                $scope.quartaTotal += dadosDosMeses[i].quarta;
                $scope.total[i] += dadosDosMeses[i].quinta;
                $scope.quintaTotal += dadosDosMeses[i].quinta;
                $scope.total[i] += dadosDosMeses[i].sexta;
                $scope.sextaTotal += dadosDosMeses[i].sexta;
                $scope.total[i] += dadosDosMeses[i].sabado;
                $scope.sabadoTotal += dadosDosMeses[i].sabado;
                $scope.anoTotal += $scope.total[i] += dadosDosMeses[i].domingo;
                $scope.domingoTotal += dadosDosMeses[i].domingo;
            };
            $scope.mostrarMensagemDeDadosVazios(len);
        }

        $scope.mostrarMensagemDeDadosVazios = function(dadosSize){
            
            if(dadosSize > 0){
                $scope.temDadosDoAno = false; 
            }else{
                $scope.temDadosDoAno = true;
            }  
        }        

        $scope.ano = moment().format('YYYY');
        $scope.anoBusca = "";
        $scope.limpaFiltro = function () {
            delete $scope.anoBusca;
        }

        $scope.loadList = function () {
            $rootScope.$broadcast('preloader:active');
            var config = {
                params: {
                    ano: ($scope.anoBusca == "" || typeof($scope.anoBusca) == "undefined")  ? $scope.ano : $scope.anoBusca
                }
            };

            $scope.mostrarAnoNaIndex = config.params.ano;            

            $scope.promise = GenericoService.GetAll('/diasUteis', config).then(
                function (response) {
                    if (response.status === 200) {
                        $scope.list.data.ano = response.data.ano;
                        $scope.list.data.dadosMeses = response.data.dadosDosMeses;
                        $scope.calculandoDiaUtil($scope.list.data.dadosMeses);
                        $rootScope.$broadcast('preloader:hide');
                    } else {
                        $scope.showSimpleToast("Não foi possível carregar os dados.")
                    }
                });
        }


      

        $scope.limitOptions = [5, 10, 15];

        $scope.deleteItem = function () {
            $rootScope.$broadcast('preloader:active');
            GenericoService.Delete('/diaUtil/' + $scope.AnoToDelete, function (response) {
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

        $scope.showConfirm = function (ev, AnoToDelete) {
            $scope.AnoToDelete = AnoToDelete;

            var confirm = $mdDialog.confirm()
                .title('Deseja confirmar a exclusão deste item?')
                .textContent('Esta ação não poderá ser desfeita.')
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
            GenericoService.ToDataURL($rootScope.defaultPath + configValue.logo,
                function (dataURL) {
                    pdfMake.createPdf(getDocDefinition($scope.list.data, dataURL)).open()
                });
            $rootScope.$broadcast('preloader:hide');
   
        }

        function getDocDefinition(content, dataURL) {

            var docDefinition = {};
            docDefinition.content = [];

            var brasao = { image: dataURL, width: 70, alignment: 'center' };
            docDefinition.content.push(brasao);

            var orgao = { text: 'RH', alignment: 'center', margin: [0, 10], bold: true };
            docDefinition.content.push(orgao);

            var titulo = { text: 'Tabela de Dia Util', alignment: 'center', margin: [0, 10] };
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
                    widths: ['*', '*', '*', '*', '*', '*', '*', '*', '*'],
                    body: [
                        [{ text: 'Ano/Mês', fontSize: 10, fillColor: 'lightgray', style: 'tableHeader', alignment: 'center' },
                        { text: 'Dias Úteis', fontSize: 10, fillColor: 'lightgray', style: 'tableHeader', alignment: 'center' },
                        { text: 'Seg.', fontSize: 10, fillColor: 'lightgray', style: 'tableHeader', alignment: 'center' },
                        { text: 'Ter.', fontSize: 10, fillColor: 'lightgray', style: 'tableHeader', alignment: 'center' },
                        { text: 'Qua.', fontSize: 10, fillColor: 'lightgray', style: 'tableHeader', alignment: 'center' },
                        { text: 'Qui.', fontSize: 10, fillColor: 'lightgray', style: 'tableHeader', alignment: 'center' },
                        { text: 'Sex.', fontSize: 10, fillColor: 'lightgray', style: 'tableHeader', alignment: 'center' },
                        { text: 'Sab.', fontSize: 10, fillColor: 'lightgray', style: 'tableHeader', alignment: 'center' },
                        { text: 'Dom.', fontSize: 10, fillColor: 'lightgray', style: 'tableHeader', alignment: 'center' },                        
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
            var body = [
                { text: content.ano, fontSize: 10, alignment: 'center' },
                { text: $scope.anoTotal, fontSize: 10, alignment: 'center'  },
                { text: $scope.segundaTotal, fontSize: 10, alignment: 'center'  },
                { text: $scope.tercaTotal, fontSize: 10, alignment: 'center'  },
                { text: $scope.quartaTotal, fontSize: 10, alignment: 'center'  },
                { text: $scope.quintaTotal, fontSize: 10, alignment: 'center'  },
                { text: $scope.sextaTotal, fontSize: 10, alignment: 'center'  },
                { text: $scope.sabadoTotal, fontSize: 10, alignment: 'center'  },
                { text: $scope.domingoTotal, fontSize: 10, alignment: 'center'  }  
            ];     
            
            lista.table.body.push(body)
         
            for (var i = 0; i < Object.keys(content.dadosMeses).length; i++) {
                var element = content.dadosMeses[i];
                var body = [
                    { text: $scope.mesesDoAno[element.mes], fontSize: 10, alignment: 'center' },
                    { text: $scope.total[i], fontSize: 10, alignment: 'center'  },
                    { text: element.segunda, fontSize: 10, alignment: 'center'  },
                    { text: element.terca, fontSize: 10, alignment: 'center'  },
                    { text: element.quarta, fontSize: 10, alignment: 'center'  },
                    { text: element.quinta, fontSize: 10, alignment: 'center'  },
                    { text: element.sexta, fontSize: 10, alignment: 'center'  },
                    { text: element.sabado, fontSize: 10, alignment: 'center'  },
                    { text: element.domingo, fontSize: 10, alignment: 'center'  }
                ];
                lista.table.body.push(body)
            }
            docDefinition.content.push(lista);

            return docDefinition;

        }

    }

  

})();