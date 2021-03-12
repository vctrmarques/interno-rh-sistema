(function () {
    'use strict';

    angular.module('app.page')
        .controller('periciaMedicaCtrl', ['configValue', '$q', '$mdDialog', '$mdMedia', '$scope', '$mdToast', '$rootScope', '$filter', 'EnumService', 'GenericoService', 'RestService', periciaMedicaCtrl]);

    function periciaMedicaCtrl(configValue, $q, $mdDialog, $mdMedia, $scope, $mdToast, $rootScope, $filter, EnumService, GenericoService, RestService) {

        // Checa as regras de acesso da tela
        RestService.Get('/usuario/verificaPermissao/por/menu', { params: { menu: 'Agenda Perícia Médica' } })
            .then(function (response) {
                if (response.status === 200 && response.data) {

                    $scope.usuarioAdm = response.data.usuarioAdm;
                    $scope.podeGerenciar = false;

                    if (response.data.papeis)
                        $scope.podeGerenciar = response.data.papeis.includes('ROLE_PERICIA_MEDICA_GESTAO') ? true : false;

                    if ($scope.usuarioAdm  || $scope.podeGerenciar){
                        $scope.loadListPendenteAgendamento();
                        $scope.loadListAgendados();
                    } else{
                        $location.path('page/403');
                    }
                }
            }, function errorCallback(response) {
                if (response.status === 400) {
                    $scope.showSimpleToast(response.data.message);
                }
            });

        var tabs = [];
        $scope.tabs = tabs;
        $scope.search = "";
        $scope.situacoesFuncionais = "";

        //PA = Pendente de Agendamento
        $scope.queryPA = {
            order: '',
            limit: 10,
            page: 1
        };

        $scope.query = {
            order: '',
            limit: 10,
            page: 1
        };

        $scope.list = {
            "especialidadeMedicaList": [],
            "count": 0,
            "data": []
        };

        $scope.limitOptions = [5, 10, 15];
        $scope.selectedEspecialidadeMedica = [];

        $scope.limpaFiltroPendenteAgendamento = function () {
            delete $scope.search;
            delete $scope.situacoesFuncionais;
            delete $scope.tipoAnalise;
            $scope.loadListPendenteAgendamento();
        }

        $scope.limpaFiltroAgendado = function () {
            delete $scope.cpfAgendados;
            delete $scope.nomeBuscaAgendados;
            $scope.loadListAgendados();
        }

        EnumService.Get("TipoAnaliseEnum").then(function (dados) {
            $scope.list.tipoAnaliseList = dados;
        });

        $scope.getListaEspecialidadesMedicas = function() {
            GenericoService.GetAllDropdown('especialidadeMedica/search', function (response) {
                if (response.status === 200) {
                    $scope.list.especialidadeMedicaList = response.data;
                    $rootScope.$broadcast('preloader:hide');
                } else {
                    $scope.showSimpleToast("Não foi possível carregar os dados.")
                }
            })
        };

        $scope.getListaEspecialidadesMedicas();

        $scope.showRelatorio = function () {
            $rootScope.$broadcast('preloader:active');
            var config = {
                params: {
                    page: $scope.query.page - 1,
                    size: $scope.query.limit,
                    order: $scope.query.order,
                    nome: null,
                    especialidadeMedicaList: null
                }
            };

            $scope.promise = RestService.Get('/agendaMedica', config)
                .then(function successCallback(response) {
                    $rootScope.$broadcast('preloader:hide');
                    if (response.status === 200) {
                        GenericoService.ToDataURL($rootScope.defaultPath + configValue.logo,
                            function (dataURL) {
                                pdfMake.createPdf(getDocDefinition(response.data.content, dataURL)).open()
                            });
                        $rootScope.$broadcast('preloader:hide');
                    }
                });
        }

        $scope.loadListPendenteAgendamento = function () {
            $rootScope.$broadcast('preloader:active');
            var config = {
                params: {
                    page: $scope.query.page - 1,
                    size: $scope.query.limit,
                    order: $scope.query.order,
                    search: $scope.search,
                    tipoSituacaoFuncionalIds: [6],
                    motivoAfastamentoIds: [14]
                }
            };

            if($scope.tipoAnalise != "APOSENTADORIA_POR_INVALIDEZ"){
                $scope.promise = RestService.Get('/pacientesPericiasMedicas', config)
                .then(function successCallback(response) {
                    $rootScope.$broadcast('preloader:hide');
                    if (response.status === 200) {
                        $scope.list.pendenteAgendamentoList = response.data;
                        $scope.list.count = response.data.totalElements;
                        
                        //re ordena pelo contador
                        $scope.list.pendenteAgendamentoList.sort(function (a, b) {
                            return a.contadorComparecimento - b.contadorComparecimento;
                          });
                    }
                }, function errorCallback(response) {
                    $rootScope.$broadcast('preloader:hide');
                    if (response.status === 400) {
                        $scope.showSimpleToast("Não foi possível carregar os dados.")
                    }
                });
            }else{
                //Falta pegar do GED
                $rootScope.$broadcast('preloader:hide');
                $scope.list.pendenteAgendamentoList = [0];
                $scope.showSimpleToast("Não foi possível carregar os dados.")
            }
          
        }

        $scope.loadListAgendados = function () {
            $rootScope.$broadcast('preloader:active');
            var config = {
                params: {
                    page: $scope.query.page - 1,
                    size: $scope.query.limit,
                    order: $scope.query.order,
                    nome: $scope.nomeBusca,
                    cpf: $scope.cpf,
                    tipoAnalise: $scope.tipoAnalise,
                    tipoSituacaoFuncional: 32
                }
            };
            
            $scope.promise = RestService.Get('/pacientesPericiasMedicasAll', config)
            .then(function successCallback(response) {
                $rootScope.$broadcast('preloader:hide');
                if (response.status === 200) {
                    $scope.list.agendadosList = response.data.content;
                }
            }, function errorCallback(response) {
                $rootScope.$broadcast('preloader:hide');
                if (response.status === 400) {
                    $scope.showSimpleToast("Não foi possível carregar os dados.")
                }
            });
        }

        $scope.showTelefone = function (telefoneList) {
            var useFullScreen = ($mdMedia('sm') || $mdMedia('xs'));
            $scope.telefoneList = telefoneList; 
            $mdDialog.show({
                require: 'periciaMedicaCtrl',
                scope: $scope,
                preserveScope: true,
                bindToController: true,
                controller: function () {
                    $scope.cancel = function () {
                        $mdDialog.cancel();
                    };
                },
                templateUrl: 'app/page/periciaMedica/dialogTelefone.html',
                parent: angular.element(document.body),
                clickOutsideToClose: false,
                fullscreen: useFullScreen
            });
        }


        function getDocDefinition(content, dataURL) {
            var docDefinition = {};
            docDefinition.content = [];

            var brasao = { image: dataURL, width: 70, alignment: 'center' };
            docDefinition.content.push(brasao);

            var orgao = { text: 'RH', alignment: 'center', margin: [0, 10], bold: true };
            docDefinition.content.push(orgao);

            var titulo = { text: 'Tabela de Agendas Médicas', alignment: 'center', margin: [0, 10] };
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
                    widths: ['*', '*', 'auto', '*'],
                    body: [
                        [{ text: 'Nome do Médico', fontSize: 10, fillColor: 'lightgray', style: 'tableHeader', alignment: 'center' },
                        { text: 'Especialidade', fontSize: 10, fillColor: 'lightgray', style: 'tableHeader', alignment: 'center' },
                        { text: 'Periodicidade', fontSize: 10, fillColor: 'lightgray', style: 'tableHeader', alignment: 'center' },
                        { text: 'Datas', fontSize: 10, fillColor: 'lightgray', style: 'tableHeader', alignment: 'center' },
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
                var especialidade = "";
                var barra = "";

                var nomeMedico = element.medico.nome;

                angular.forEach(element.especialidadesMedicas, function (e) {
                    especialidade = especialidade + barra + e.nome;
                    barra = " / ";
                });

                var agendamento = element.agendamento;
                var periodicidade = element.periodicidade;

                var body = [
                    { text: nomeMedico, fontSize: 10 },
                    { text: especialidade, fontSize: 10 },
                    { text: agendamento, fontSize: 10 },
                    { text: periodicidade, fontSize: 10 }
                ]
                lista.table.body.push(body)
            }
            
            docDefinition.content.push(lista);

            return docDefinition;

        }

        function getDocDefinitionModal(content, dataURL) {
            var docDefinition = {};
            docDefinition.content = [];

            var brasao = { image: dataURL, width: 70, alignment: 'center' };
            docDefinition.content.push(brasao);

            var orgao = { text: 'RH', alignment: 'center', margin: [0, 10], bold: true };
            docDefinition.content.push(orgao);

            var titulo = { text: 'Tabela de Agendas Médicas', alignment: 'center', margin: [0, 10] };
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
                    widths: ['auto', '*'],
                    body: []
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

            angular.forEach(content, function (e) {
                lista.table.body.push(
                    [{ text:  e.nome, fontSize: 10, alignment: 'left', fillColor: 'lightgray', style: 'tableHeader' },
                     { text: e.especialidadeMedica, fontSize: 10, alignment: 'left', fillColor: 'lightgray', style: 'tableHeader' }]
                );
                angular.forEach(e.lista, function (a) {
                    lista.table.body.push(
                        [{ text: $scope.convertDate(a.data,false) + "   " + a.diaSemana, fontSize: 10, colSpan: 2, alignment: 'center' },{}]
                    );
                    
                    angular.forEach(a.horarios, function (amd) {
                        lista.table.body.push([
                            {text: amd.hora, fontSize: 10, colSpan: 2, alignment: 'center' },{}
                        ]);
                    });

                });

            });
            
            docDefinition.content.push(lista);

            return docDefinition;

        }

         /*
         * Recebe data e converte para valor com hora ou não
         * 
         * param data - recebe uma data tipo LocalDate
         * param comHora - recebe um valor boolean
         * 
         * */
		$scope.convertDate = function(data, comHora) {
			var valor = moment(data);
			if (comHora) {
				return $filter('date')(new Date(valor), 'dd/MM/yyyy - HH:mm');
			} else {
				return $filter('date')(new Date(valor), 'dd/MM/yyyy');
			}
		}

        $scope.showConfirm = function (ev, idToDelete) {
            $scope.idToDelete = idToDelete;
            // Appending dialog to document.body to cover sidenav in docs app
            var confirm = $mdDialog.confirm()
                .title('Deseja confirmar a exclusão desta Agenda Médica?')
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
            RestService.Delete('/consultaPericiaMedica/' + $scope.idToDelete)
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

        $scope.showConfirmNaoAtendimento = function (ev, cpfToUpdate) {
            $scope.cpfToUpdate = cpfToUpdate;
            // Appending dialog to document.body to cover sidenav in docs app
            var confirm = $mdDialog.confirm()
                .title('Deseja confirmar o não atendimento do Agendamento?')
                .textContent('Esta ação não poderá ser desfeita.')
                // .ariaLabel('Lucky day')
                .targetEvent(ev)
                .ok('Sim')
                .cancel('Não');

            $mdDialog.show(confirm).then(function () {
                $scope.updateItem();
                $scope.loadListPendenteAgendamento();
                $scope.loadListAgendados();
            }, function () {
            });
        };

        $scope.updateItem = function () {
            $rootScope.$broadcast('preloader:active');
                RestService.Update('/consultaPericiaMedica/naoCompareceu', $scope.cpfToUpdate)
                .then(function successCallback(response) {
                    $rootScope.$broadcast('preloader:hide');
                    if (response.status === 201 && response.data.success) {
                        $scope.showSimpleToast(response.data.message);
                        $location.path('/periciaMedica/gestao');
                    }
                }, function errorCallback(response) {
                    $rootScope.$broadcast('preloader:hide');
                    if (response.status === 400) {
                        $scope.showSimpleToast(response.data.message);
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

    }

})();



