(function () {
    'use strict';

    angular.module('app.page')
        .controller('recisaoContratoCtrl', ['configValue', '$scope', '$mdToast', '$mdDialog', '$rootScope', 'GenericoService', recisaoContratoCtrl]);

    function recisaoContratoCtrl(configValue, $scope, $mdToast, $mdDialog, $rootScope, GenericoService) {

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

        $scope.statusBusca = false;
        $scope.query = {
            order: 'id',
            limit: 10,
            page: 1
        };

        $scope.list = {
            "count": 0,
            "data": [],
            "status": []
        };

        $scope.object = [];

        $scope.limpaFiltro = function () {
            delete $scope.contratoBusca;
        }

        $scope.loadList = function () {
            $rootScope.$broadcast('preloader:active');

            var config = {
                params: {
                    page: $scope.query.page - 1,
                    size: $scope.query.limit,
                    order: $scope.query.order,
                    matricula: $scope.matriculaBusca,
                    nome: $scope.nomeBusca,
                    status: $scope.statusBusca,
                }
        };

        $scope.promise = GenericoService.GetAll('/recisoes', config).then(
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

        $scope.showConfirmDelete = function (ev, idToDelete) {
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

        $scope.deleteItem = function () {
            $rootScope.$broadcast('preloader:active');
            GenericoService.Delete('/recisaoContrato/' + $scope.idToDelete, function (response) {
                $rootScope.$broadcast('preloader:hide');
                if (response.status === 200) {
                    $scope.showSimpleToast(response.data.message);
                }
                $scope.loadList();  
            });
        }

        $scope.CancelarItem = function () {
            $rootScope.$broadcast('preloader:active');
            GenericoService.Update('/cancelarRecisao', $scope.object, function (response) {
                $rootScope.$broadcast('preloader:hide');
                if (response.status === 200) {
                    $scope.showSimpleToast(response.data.message);
                }
                $scope.loadList();
            });
        }

        $scope.showConfirmCancellation = function (ev, object) {
            $scope.object = object;
            // Appending dialog to document.body to cover sidenav in docs app
            var confirm = $mdDialog.confirm()
                .title('Deseja cancelar essa recis??o de contrato?')
                .textContent('Esta a????o n??o poder?? ser desfeita.')
                // .ariaLabel('Lucky day')
                .targetEvent(ev)
                .ok('Sim')
                .cancel('N??o');

            $mdDialog.show(confirm).then(function () {
                $scope.CancelarItem();
            }, function () {
            });
        };

        $scope.EfetivarItem = function () {
            $rootScope.$broadcast('preloader:active');
            GenericoService.Update('/efetivarRecisao', $scope.object, function (response) {
                $rootScope.$broadcast('preloader:hide');
                if (response.status === 200) {
                    $scope.showSimpleToast(response.data.message);
                }
                $scope.loadList();
            });
        }

        $scope.Status = function () {
            $rootScope.$broadcast('preloader:active');
            GenericoService.GetAllDropdown('listStatus', function (response) {
                if (response.status === 200) {
                    $scope.list.status = response.data;
                    $rootScope.$broadcast('preloader:hide');
                } else if (response.status === 500) {
                    $scope.showSimpleToast("Falha no carregamento de dados, favor contate o administrador do sistema");
                }
            });
        }

        $scope.showConfirmEfetivacao = function (ev, object) {
            $scope.object = object;
            // Appending dialog to document.body to cover sidenav in docs app
            var confirm = $mdDialog.confirm()
                .title('Deseja efetivar essa recis??o de contrato?')
                .textContent('Esta a????o n??o poder?? ser desfeita.')
                // .ariaLabel('Lucky day')
                .targetEvent(ev)
                .ok('Sim')
                .cancel('N??o');

            $mdDialog.show(confirm).then(function () {
                $scope.EfetivarItem();
            }, function () {
            });
        };

        $scope.showTermoRecisao = function(object) {
            pdfMake.createPdf(getDocDefinition(object)).open();
        };

        function getDocDefinition(content, dataURL) {

            var enderecoEmpresa = content.funcionarioResponse.filial.logradouro + ', n?? ' + content.funcionarioResponse.filial.numero + ', ' + content.funcionarioResponse.filial.complemento;
            var enderecoTrabalhador = content.funcionarioResponse.logradouro + ', n?? ' + content.funcionarioResponse.numero + ', ' + content.funcionarioResponse.complemento;
            var docDefinition = {};
            
            docDefinition.content = [];

            content.funcionarioResponse.dataNascimento = moment(content.funcionarioResponse.dataNascimento).format("DD/MM/YYYY");
            content.funcionarioResponse.dataAdmissao = moment(content.funcionarioResponse.dataAdmissao).format("DD/MM/YYYY");
            content.dataAviso = (content.dataAviso != null ? moment(content.dataAviso).format("DD/MM/YYYY") : null);
            content.dataDesligamento = moment(content.dataDesligamento).format("DD/MM/YYYY");

            var titulo = { 
                style: 'tableExample',
                color: '#444',
                table: {
                    widths: ['*'],
                    headerRows: 1,

                    body: [[{text: 'TERMO DE RESCIS??O DO CONTRATO DE TRABALHO', alignment: 'center', fillColor: 'gray'}]]
                }
            }
            docDefinition.content.push(titulo);

            var breakLine = { text: '\n' }
            docDefinition.content.push(breakLine);

            var tituloIdEmpregador = { 
                style: 'tableExample',
                color: '#444',
                table: {
                    widths: ['*'],
                    headerRows: 1,

                    body: [[{ text: 'IDENTIFICA????O DO EMPREGADOR', alignment: 'center', fillColor: '#808080'}]]
                }
            }
            docDefinition.content.push(tituloIdEmpregador);

            var tableEmpregador = {
                style: 'tableExample',
                color: '#444',
                table: {
                    widths: ['*', 'auto', '*', '*', '*'],
                    headerRows: 3,

                    body: [
                        [   
                            { text: [
                                        { text: '01 CNPJ/CEI/CPF\n\n', fontSize: 6 },
                                        { text: content.funcionarioResponse.filial.cnpj, fontSize: 8 }
                                    ] 
                            },
                            { text: [
                                        { text: '02 Raz??o social/Nome\n\n', fontSize: 6 },
                                        { text: content.funcionarioResponse.filial.nomeFilial, fontSize: 8 }
                                    ]
                                    , colSpan: 4 
                            },
                            { },
                            { },
                            { }
                        ],
                        [
                            { text: [
                                        { text: '03 Endere??o (logradouro, n??, andar, apartamento)\n\n', fontSize: 6 },
                                        { text: enderecoEmpresa, fontSize: 8 }
                                    ]
                                    , colSpan: 4
                            },
                            { },
                            { },
                            { },
                            { text: [
                                        { text: '04 Bairro\n\n', fontSize: 6 },
                                        { text: content.funcionarioResponse.filial.bairro, fontSize: 8 }
                                    ]
                            }                              
                        ],
                        [
                            { text: [
                                        { text : '05 Munic??pio\n\n', fontSize: 6 },
                                        { text: (content.funcionarioResponse.filial.municipio != null ? content.funcionarioResponse.filial.municipio.descricao : ''), fontSize: 8 }
                                    ] 
                            },
                            { text: [
                                        { text: '06 UF\n\n', fontSize: 6 },
                                        { text: (content.funcionarioResponse.filial.uf != null ? content.funcionarioResponse.filial.uf.sigla : ''), fontSize: 8 }
                                    ] 
                            },
                            { text: [
                                        { text: '07 CEP\n\n', fontSize: 6 },
                                        { text: content.funcionarioResponse.filial.cep, fontSize: 8 }
                                    ] 
                            },
                            { text: [
                                        { text: '08 CNAE\n\n', fontSize: 6 },
                                        { text: (content.funcionarioResponse.filial.cnae != null ? content.funcionarioResponse.filial.cnae.codigoSecao : ''), fontSize: 8 }
                                    ] },
                            { text: [
                                        { text: '09 CNPJ/CEI Tomador/obra\n\n', fontSize: 6},
                                        { text: '', fontSize: 8 }
                                    ] 
                            }
                        ]
                    ]
                }
            }
            docDefinition.content.push(tableEmpregador);

            var tituloIdTrabalhador = { 
                style: 'tableExample',
                color: '#444',
                table: {
                    widths: ['*'],
                    headerRows: 1,

                    body: [[{ text: 'IDENTIFICA????O DO TRABALHADOR', alignment: 'center', fillColor: '#808080'}]]
                }
            }
            docDefinition.content.push(tituloIdTrabalhador);

            var tableTrabalhador = {
                style: 'tableExample',
                color: '#444',
                table: {
                    widths: ['*', 'auto', '*', '*', '*'],
                    headerRows: 4,

                    body: [
                        [   
                            { text: [
                                        { text: '10 PIS/PASEP\n\n', fontSize: 6 },
                                        { text: content.funcionarioResponse.pisPasep, fontSize: 8 }
                                    ] 
                            },
                            { text: [
                                        { text: '11 Nome\n\n', fontSize: 6 },
                                        { text: content.funcionarioResponse.nome, fontSize: 8 }
                                    ]
                                    , colSpan: 4 
                            },
                            { },
                            { },
                            { }
                        ],
                        [
                            { text: [
                                        { text: '12 Endere??o do trabalhador (logradouro, n??, andar, apartamento)\n\n', fontSize: 6 },
                                        { text: enderecoTrabalhador, fontSize: 8 }
                                    ]
                                    , colSpan: 4
                            },
                            { },
                            { },
                            { },
                            { text: [
                                        { text: '13 Bairro\n\n', fontSize: 6 },
                                        { text: content.funcionarioResponse.bairro, fontSize: 8 }
                                    ]
                            }                              
                        ],
                        [
                            { text: [
                                        { text : '14 Munic??pio\n\n', fontSize: 6 },
                                        { text: content.funcionarioResponse.municipioNome, fontSize: 8 }
                                    ] 
                            },
                            { text: [
                                        { text: '15 UF\n\n', fontSize: 6 },
                                        { text: content.funcionarioResponse.ufNome, fontSize: 8 }
                                    ] 
                            },
                            { text: [
                                        { text: '16 CEP\n\n', fontSize: 6 },
                                        { text: content.funcionarioResponse.cep, fontSize: 8 }
                                    ] 
                            },
                            { text: [
                                        { text: '17 CTPS (n??. s??rie, UF)\n\n', fontSize: 6 },
                                        { text: content.funcionarioResponse.serieCtps + ' ' + content.funcionarioResponse.ufCtpsNome, fontSize: 8 }
                                    ] },
                            { text: [
                                        { text: '18 CPF\n\n', fontSize: 6},
                                        { text: content.funcionarioResponse.cpf, fontSize: 8 }
                                    ] 
                            }
                        ],
                        [
                            { text : [
                                        { text: '19 Data de nascimento\n\n', fontSize: 6 },
                                        { text: content.funcionarioResponse.dataNascimento, fontSize: 8 }
                                     ]
                            },
                            { text : [
                                        { text: '20 Nome da m??e\n\n', fontSize: 6 },
                                        { text: content.funcionarioResponse.nomeMae, fontSize: 8 }
                                     ]
                                     , colSpan: 4
                            },
                            { },
                            { },
                            { }
                        ]
                    ]
                }
            }
            docDefinition.content.push(tableTrabalhador);

            var tituloDadosContrato = { 
                style: 'tableExample',
                color: '#444',
                table: {
                    widths: ['*'],
                    headerRows: 1,

                    body: [[{ text: 'DADOS DO CONTRATO', alignment: 'center', fillColor: '#808080' }]]
                }
            }
            docDefinition.content.push(tituloDadosContrato);

            var tableDadosContrato = {
                style: 'tableExample',
                color: '#444',
                table: {
                    widths: ['*', 'auto', '*', '*', '*'],
                    headerRows: 5,

                    body: [
                        [
                            { text: [
                                        { text: '21 Tipo do contrato\n\n', fontSize: 6 },
                                        { text: content.funcionarioResponse.tipoContratoNome, fontSize: 8 } 
                                    ]
                                    , colSpan: 5
                            },
                            { },
                            { },
                            { },
                            { }
                        ],
                        [
                            { text: [
                                        { text: '22 Causa do afastamento\n\n', fontSize:6 },
                                        { text: content.motivoNome, fontSize: 8 }
                                    ]
                                    , colSpan: 5
                            },
                            { },
                            { },
                            { },
                            { }
                        ],
                        [
                            { text: [
                                        { text: '23 Remunera????o M??s Ant\n\n', fontSize: 6 },
                                        // preencher remune????o
                                        { text: 'R$ xx,xx', fontSize: 8 }
                                    ]
                            },
                            { text: [
                                        { text: '24 Data de Admiss??o\n\n', fontSize: 6 },
                                        { text: content.funcionarioResponse.dataAdmissao, fontSize: 8 }
                                    ]
                            },
                            { text: [
                                        { text: '25 Data do Aviso Pr??vio\n\n', fontSize: 6 },
                                        { text: (content.dataAviso != null ? content.dataAviso : ''), fontSize: 8 }
                                    ]
                            },
                            { text: [
                                        { text: '26 Data de afastamento\n\n', fontSize: 6 },
                                        { text: content.dataDesligamento, fontSize: 8 }
                                    ]
                            }, 
                            { text: [
                                        { text: '27 C??d afastamento\n\n', fontSize: 6 },
                                        { text: content.codMotivo, fontSize: 8 }
                                    ]
                            }
                        ],
                        [
                            { text: [
                                        { text: '28 Pens??o alim. (%) (TRCT)\n\n', fontSize: 6 },
                                        { text: 'xx,xx %', fontSize: 8 }
                                    ]
                            },
                            { text: [
                                        { text: '29 Pens??o alim. (%) (FGTS)\n\n', fontSize: 6 },
                                        { text: 'xx,xx %', fontSize: 8 }
                                    ]
                            },
                            { text: [
                                        { text: '30 Categoria do trabalhador\n\n', fontSize: 6 },
                                        { text: 'xxxxxxxxxxxxxxx', fontSize: 8}
                                    ]
                                    , colSpan: 3
                            },
                            { },
                            { }
                        ],
                        [
                            { text: [
                                        { text: '31 C??digo sindical\n\n', fontSize: 6 },
                                        { text: 'xxx', fontSize: 8 }
                                    ]
                            },
                            { text: [
                                        { text: '32 CNPJ e nome da entidade sindical laboral\n\n', fontSize: 6 },
                                        { text: 'xx.xxx.xxx/xxxx-xx', fontSize: 8 }
                                    ]
                                    , colSpan: 4
                            },
                            { },
                            { },
                            { }
                        ]
                    ]
                }   
            }
            docDefinition.content.push(tableDadosContrato);

            var tituloDiscVerbaResc = { 
                style: 'tableExample',
                color: '#444',
                table: {
                    widths: ['*'],
                    headerRows: 1,

                    body: [[{ text: 'DISCRIMINA????O DAS VERBAS RESCIS??RIAS', alignment: 'center', fillColor: '#808080' }]]
                }
            }
            docDefinition.content.push(tituloDiscVerbaResc);

            var tableDiscVerbaResc = {
                style: 'tableExample',
                color: '#444',
                table: {
                    widths: ['*', 67, '*', 67, '*', 67],
                    headerRows: 9,

                    body: [
                        [
                            { text: 'VERBAS RESCIS??RIAS', fontSize: 12, bold: true, colSpan: 6 },
                            { },
                            { },
                            { },
                            { },
                            { }
                        ],
                        [
                            { text: 'Rubrica', fontSize: 10, bold: true },
                            { text: 'Valor', fontSize: 10, bold: true },
                            { text: 'Rubrica', fontSize: 10, bold: true },
                            { text: 'Valor', fontSize: 10, bold: true },
                            { text: 'Rubrica', fontSize: 10, bold: true },
                            { text: 'Valor', fontSize: 10, bold: true }
                        ],
                        [
                            { text: '50 Saldo de xx/dias sal??rio (liqu??do de xx/falta e DSR)', fontSize: 6 },
                            { text: 'R$ xx,xx', alignment: 'right', fontSize: 8 }, 
                            { text: '51 comiss??es', fontSize: 6 },
                            { text: 'R$ xx,xx', alignment: 'right', fontSize: 8 }, 
                            { text: '52 Gratifica????o', fontSize: 6 },
                            { text: 'R$ xx,xx', alignment: 'right', fontSize: 8 } 
                        ],
                        [
                            { text: '53 Adic. de insalubridade xx,xx%', fontSize: 6 },
                            { text: 'R$ xx,xx', alignment: 'right', fontSize: 8 }, 
                            { text: '54 Adic. de periculosidade xx,xx%', fontSize: 6 },
                            { text: 'R$ xx,xx', alignment: 'right', fontSize: 8 }, 
                            { text: '55 Adic. de noturno xx horas a xx,xx%', fontSize: 6 },
                            { text: 'R$ xx,xx', alignment: 'right', fontSize: 8 }
                        ],
                        [
                            { text: '56.1 Horas extras xx horas a xx,xx%', fontSize: 6 },
                            { text: 'R$ xx,xx', alignment: 'right', fontSize: 8 },
                            { text: '57 Gorjetas', fontSize: 6 },
                            { text: 'R$ xx,xx', alignment: 'right', fontSize: 8 },
                            { text: '58 Descanso semanal remunerado (DSR)', fontSize: 6 },
                            { text: 'R$ xx,xx', alignment: 'right', fontSize: 8 }
                        ],
                        [
                            { text: '59 Reflexo do DSR sobre sal??rio varri??vel', fontSize: 6 },
                            { text: 'R$ xx,xx', alignment: 'right', fontSize: 8 },
                            { text: '60 Multa Art. 477. ?? 8??/CLT', fontSize: 6},
                            { text: 'R$ xx,xx', alignment: 'right', fontSize: 8 },
                            { text: '62 Sal??rio-Familia', fontSize: 6},
                            { text: 'R$ xx,xx', alignment: 'right', fontSize: 8 }
                        ],
                        [
                            { text: '63 13?? Sal??rio proporcional xx/12 avos', fontSize: 6 },
                            { text: 'R$ xx,xx', alignment: 'right', fontSize: 8 },
                            { text: '64.1 13?? Sal??rio-Exerc. xxxx - xx/12 avos', fontSize: 6},
                            { text: 'R$ xx,xx', alignment: 'right', fontSize: 8 },
                            { text: 'F??rias proporc xx/12 avos', fontSize: 6 },
                            { text: 'R$ xx,xx', alignment: 'right', fontSize: 8 }
                        ],
                        [
                            { text: '66.1 F??rias venc. per. aquisitivo xx/xx/xxxx a xx/xx/xxxx', fontSize: 6 },
                            { text: 'R$ xx,xx', alignment: 'right', fontSize: 8 },
                            { text: '68 Ter??o constituc. de f??rias', fontSize: 6 },
                            { text: 'R$ xx,xx', alignment: 'right', fontSize: 8 },
                            { text: '69 Aviso pr??vio indenizado xx/dias', fontSize: 6 },
                            { text: 'R$ xx,xx', alignment: 'right', fontSize: 8 }
                        ],
                        [
                            { text: '70 13?? Sal??rio (Aviso pr??vio indenizado)', fontSize: 6 },
                            { text: 'R$ xx,xx', alignment: 'right', fontSize: 8 },
                            { text: '71 F??rias (Aviso pr??vio indenizado)', fontSize: 6 },
                            { text: 'R$ xx,xx', alignment: 'right', fontSize: 8 },
                            { },
                            { }
                        ],
                        [
                            { },
                            { },
                            { text: '99 Ajuste do saldo devedor', fontSize: 6 },
                            { },
                            { text: 'TOTAL BRUTO', alignment: 'center', bold: true, fontSize: 10, fillColor: '#808080' },
                            { text: 'R$ xx,xx', alignment: 'right', fontSize: 8, fillColor: '#808080' }
                        ]
                    ]    
                }  
            }     
            docDefinition.content.push(tableDiscVerbaResc);  

            var tableDeducoes = {
                style: 'tableExample',
                color: '#444',
                table: {
                    widths: ['*', 67, '*', 67, '*', 67],
                    headerRows: 7,

                    body: [
                        [
                            { text: 'DEDU????ES', fontSize: 12, bold: true, colSpan: 6 },
                            { },
                            { },
                            { },
                            { },
                            { }
                        ],
                        [
                            { text: 'Desconto', fontSize: 10, bold: true },
                            { text: 'Valor', fontSize: 10, bold: true },
                            { text: 'Desconto', fontSize: 10, bold: true },
                            { text: 'Valor', fontSize: 10, bold: true },
                            { text: 'Desconto', fontSize: 10, bold: true },
                            { text: 'Valor', fontSize: 10, bold: true }
                        ],
                        [
                            { text: '100.1 Pens??o alimenticia - sal??rio', fontSize: 6 },
                            { text: 'R$ xx,xx', alignment: 'right', fontSize: 8 },
                            { text: '101 Adiantamento sal??rial', fontSize: 6 },
                            { text: 'R$ xx,xx', alignment: 'right', fontSize: 8 },
                            { text: '103 Adiantamento 13?? sal??rio', fontSize: 6 },
                            { text: 'R$ xx,xx', alignment: 'right', fontSize: 8 }
                        ],
                        [
                            { text: '103 Aviso pr??vio indenizado xx/dias', fontSize: 6 },
                            { text: 'R$ xx,xx', alignment: 'right', fontSize: 8 },
                            { text: '112.1 Previd??ncia social', fontSize: 6 },
                            { text: 'R$ xx,xx', alignment: 'right', fontSize: 8 },
                            { text: '112.2 Prev social - 13?? sal??rio', fontSize: 6 },
                            { text: 'R$ xx,xx', alignment: 'right', fontSize: 8 }
                        ],
                        [
                            { text: '114.1 IRPF', fontSize: 6 },
                            { text: 'R$ xx,xx', alignment: 'right', fontSize: 8 },
                            { text: '114.2 IRPF sobre 13?? sal??rio', fontSize: 6 },
                            { text: 'R$ xx,xx', alignment: 'right', fontSize: 8 },
                            { },
                            { }
                        ],
                        [
                            { },
                            { },
                            { },
                            { },
                            { text: 'TOTAL DEDU????ES', alignment: 'center', bold: true, fontSize: 10, fillColor: '#808080' },
                            { text: 'R$ xx,xx', alignment: 'right', fontSize: 8, fillColor: '#808080' }
                        ],
                        [
                            { },
                            { },
                            { },
                            { },
                            { text: 'VALOR RESCIS??RIO L??QUIDO', bold: true, fontSize: 10, fillColor: '#808080' },
                            { text: 'R$ xx,xx', alignment: 'right', fontSize: 8, fillColor: '#808080' }
                        ]
                    ]
                }
            }   
            docDefinition.content.push(tableDeducoes);

            var tituloFormalizacao = { 
                style: 'tableExample',
                color: '#444',
                table: {
                    widths: ['*'],
                    headerRows: 1,

                    body: [[{ text: 'FORMALIZA????O DA RESCIS??O', alignment: 'center', fillColor: '#808080'}]]
                }
            }
            docDefinition.content.push(tituloFormalizacao);

            var tableFormalizacao = {
                style: 'tableExample',
                color: '#444',
                table: {
                    widths: ['*', 67, '*'],
                    headerRows: 5,

                    body: [
                        [
                            { text: [
                                        { text: '150 Local e data do recebimento\n\n', fontSize: 6 },
                                        { text: 'xx/xx/xxxx', fontSize: 8 }
                                    ]
                                    , colSpan: 2
                            },
                            { },
                            { text: '151 Carimbo e assinatura do empregador ou preposto\n\n', fontSize: 6 }
                        ],
                        [
                            { text: '152 Assinatura do trabalhador\n\n', fontSize: 6, colSpan: 2 },
                            { },
                            { text: '153 Assinatura do responsavel legal do trabalhador\n\n', fontSize: 6 }
                        ],
                        [
                            { text: '154 HOMOLOGA????O foi prestada, gratuitamente, assist??ncia ao trabalhador, nos termos do art. 477. ?? 1??, da Consolida????o das Leis do Trabalho - CLT, sendo comprovado, neste ato, o efetivo pagamento das verbas rescis??rias acima especificada\n_________________________________________\n local e data\n_________________________________________\nCarimbo e assinatura do assistente', fontSize: 6 },
                            { text: '155 Digital do trabalhador\n\n', fontSize: 6 },
                            { text: '156 Digital do respons??vel legal\n\n', fontSize: 6 }                            
                        ],
                        [
                            { text: '157 Identifica????o do ??rg??o homologador\n\n', fontSize: 6 },
                            { text: '158 Recep????o pelo banco (data e carimbo)\n\n', fontSize: 6 },
                            { }
                        ],
                        [
                            { text: 'A ASSIST??NCIA NO ATO DE RESCIS??O CONTRATUAL ?? GRATUITA. Pode o trabalhador iniciar a????o judicial quanto aos cr??ditos resultantes das rela????es de trabalho at?? o limite de dois anos ap??s a extin????o do contrato de trabalho (inciso XXIX, art. 7?? da Constitui????o Federal/1988).', fontSize: 6, colSpan: 3 },
                            { },
                            { }
                        ]
                    ]
                }
            }
            docDefinition.content.push(tableFormalizacao);

            return docDefinition;
        }

        $scope.showAvisoPrevio = function(object) {
            GenericoService.ToDataURL($rootScope.defaultPath + configValue.logo,
                function (dataURL) {
                    pdfMake.createPdf(getDocDefinitionAvisoPrevio(object, dataURL)).open()
                });
        };

        function getDocDefinitionAvisoPrevio(content, dataURL) {

            var docDefinition = {};
            var dataAtual = moment().format("DD/MM/YYYY");
            docDefinition.content = [];

            content.dataDesligamento = moment(content.dataDesligamento).format("DD/MM/YYYY");

            var titulo = { text: 'Aviso Pr??vio\n\n', alignment: 'center', bold: true, fontSize: 18 };
            docDefinition.content.push(titulo);

            var comunicado = { text: 'Comunico o(a) Sr(a) ' + content.funcionarioResponse.nome + ' que a partir do dia ' + content.dataDesligamento + ', os seus servi??os n??o ser??o necess??rios nesta Casa, servindo, pois, a presente como aviso de recis??o contratual.', alignment: 'justify', fontSize: 10 };
            docDefinition.content.push(comunicado);

            var checkCumprir = { text: '\n\n( ) Deve cumprir aviso-pr??vio trabalhando at?? ' + content.dataDesligamento, alignment: 'center', fontSize: 10 };
            docDefinition.content.push(checkCumprir);         

            var checkDisp = { text: '\n( ) Fica dispensado de cumprir o aviso, que ser?? indenizado.', alignment: 'center', fontSize: 10 };
            docDefinition.content.push(checkDisp);

            var local = { text: '\n\n\nLocal _________________________________________ data: ' + dataAtual, alignment: 'center', fontSize: 10 };
            docDefinition.content.push(local);

            var assinatura = { text: '\n\n\n_________________________________________\n', alignment: 'center' };
            docDefinition.content.push(assinatura);

            var nomeEmpregado = { text: 'Nome Empregado', alignment: 'center', fontSize: 10 };
            docDefinition.content.push(nomeEmpregado);

            return docDefinition;
        }

        $scope.Status();
    }
})();



