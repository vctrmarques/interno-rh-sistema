(function () {
    'use strict';

    angular.module('app.page')
        .controller('agendaMedicaCtrl', ['configValue', '$q', '$mdDialog', '$mdMedia', '$scope', '$mdToast', '$rootScope', '$filter', 'GenericoService', 'RestService', agendaMedicaCtrl]);

    function agendaMedicaCtrl(configValue, $q, $mdDialog, $mdMedia, $scope, $mdToast, $rootScope, $filter, GenericoService, RestService) {

        // Checa as regras de acesso da tela
        RestService.Get('/usuario/verificaPermissao/por/menu', { params: { menu: 'Agenda Médica' } })
            .then(function (response) {
                if (response.status === 200 && response.data) {

                    $scope.usuarioAdm = response.data.usuarioAdm;
                    $scope.podeGerenciar = false;

                    if (response.data.papeis)
                        $scope.podeGerenciar = response.data.papeis.includes('ROLE_AGENDA_MEDICA_GESTAO') ? true : false;

                    if ($scope.usuarioAdm  || $scope.podeGerenciar)
                        $scope.loadList();
                    else
                        $location.path('page/403');
                }
            }, function errorCallback(response) {
                if (response.status === 400) {
                    $scope.showSimpleToast(response.data.message);
                }
            });

        var tabs = [];
        $scope.tabs = tabs;

        $scope.query = {
            order: 'medico.nome',
            limit: 10,
            page: 1
        };

        $scope.queryModal = {
            order: 'medico.nome',
            limit: 5,
            page: 1
        };

        $scope.list = {
            "especialidadeMedicaList": [],
            "count": 0,
            "data": []
        };

        $scope.listRelatorio = {
            "count": 0,
            "data": []
        };

        $scope.limitOptions = [5, 10, 15];
        $scope.limitOptionsModal = [5];
        $scope.selectedEspecialidadeMedica = [];

        $scope.limpaFiltro = function () {
            delete $scope.nomeBusca;
            $scope.loadList();
        }

        $scope.convertDate = function(data, comHora) {
			var valor = moment(data);
			if (comHora) {
				return $filter('date')(new Date(valor), 'dd/MM/yyyy - HH:mm');
			} else {
				return $filter('date')(new Date(valor), 'dd/MM/yyyy');
			}
        }

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

        $scope.showModalAgenda = function (id) {
            var useFullScreen = ($mdMedia('sm') || $mdMedia('xs'));

            GenericoService.GetById('/agendaMedica/' + id, function (response) {
                $rootScope.$broadcast('preloader:active');
                if (response.status === 200) {
                    $scope.agendaMedica = response.data;
                    $scope.medico = $scope.agendaMedica.medico;
                    if ($scope.medico) {
                        $scope.searchNomeMedico = $scope.medico.nome;
                        $scope.list.especialidadeMedicaList = $scope.medico.especialidadeMedica;
                        $scope.agendaMedica.medicoId = $scope.medico.id;
                        $scope.agendaMedica.medicoNome = $scope.medico.nome;
                        delete $scope.medico;
                    }

                    $scope.menorData = moment($scope.agendaMedica.dataInicial);
                    $scope.agendaMedica.dataInicial = moment($scope.agendaMedica.dataInicial);
                    $scope.agendaMedica.dataFinal = moment($scope.agendaMedica.dataFinal);

                    $scope.detalhes = true;
                    
                    var timeInicial = '1970-01-01T'+$scope.agendaMedica.horaInicial+'.000';
                    var timeFinal = '1970-01-01T'+$scope.agendaMedica.horaFinal+'.000';
                    $scope.agendaMedica.horaInicial = new Date(timeInicial);
                    $scope.agendaMedica.horaFinal = new Date(timeFinal);

                    $scope.list.dataModal =  response.data.agendaMedicaList;
                    $rootScope.$broadcast('preloader:hide');

                    $mdDialog.show({
                        require: 'agendaMedicaCtrl',
                        scope: $scope,
                        preserveScope: true,
                        bindToController: true,
                        controller: function () {
                            $scope.imprimir = function () {
                                var deferred = $q.defer();
                                var config = {
                                    params: {
                                        id: id,
                                        especialidadeMedicaList: null,
                                        dtInicial: null,
                                        dtFinal: null
                                    }
                                }

                                $rootScope.$broadcast('preloader:active');
                                GenericoService.GetAll('/agendaMedica/relatorio/search', config).then(
                                    function (response) {
                                        if (response.status === 200) {
                                            $scope.listRelatorio.data = response.data;
                    
                                            angular.forEach($scope.listRelatorio.data, function (l) {
                                                let mesLista = {
                                                    nome: l.nomeMedico,
                                                    especialidadeMedica: [],
                                                    lista: [],
                                                }
                                                angular.forEach(l.nmEspecialidade, function (especialidade) {
                                                    mesLista.especialidadeMedica.push(especialidade);
                                                });
                    
                                                angular.forEach(l.agendaMedicaDto.agendaMedicaList, function (f) {
                                                    let data ={
                                                        id : f.id,
                                                        data: f.data, 
                                                        diaSemana: f.diaSemana,
                                                        horarios: [],                               
                                                    }
                                                    angular.forEach(f.agendaMedicaData, function (lista) {
                                                        let horarios ={
                                                            id : lista.id,
                                                            data: lista.data,
                                                            diaSemana: lista.diaSemana,
                                                            hora: lista.hora,
                                                        }
                                                        data.horarios.push(horarios);
                                                    });
                    
                                                    mesLista.lista.push(data);
                                                });
                    
                                                tabs.push(mesLista);
                    
                                            });

                                            GenericoService.ToDataURL($rootScope.defaultPath + configValue.logo,
                                                function (dataURL) {
                                                    pdfMake.createPdf(getDocDefinitionModal(tabs, dataURL)).open()
                                                });
                                            $rootScope.$broadcast('preloader:hide');
                                        }
                                    });
                            };
                            $scope.cancel = function () {
                                $mdDialog.cancel();
                            };
                        },
                        templateUrl: 'app/page/agendaMedica/dialogAgenda.html',
                        parent: angular.element(document.body),
                        clickOutsideToClose: false,
                        fullscreen: useFullScreen
                    });
                    return deferred.promise;

                }
            });
           
        }

        $scope.loadList = function () {
            var especialidadeMedicaList = [];
            if ($scope.especialidadeMedicaId && $scope.especialidadeMedicaId.length > 0) {
                $scope.especialidadeMedicaId.forEach(id => {
                    especialidadeMedicaList.push(id);
                });
            }

            $rootScope.$broadcast('preloader:active');
            var config = {
                params: {
                    page: $scope.query.page - 1,
                    size: $scope.query.limit,
                    order: $scope.query.order,
                    nome: $scope.nomeBusca,
                    especialidadeMedicaList: especialidadeMedicaList
                }
            };

            $scope.promise = RestService.Get('/agendaMedica', config)
                .then(function successCallback(response) {
                    $rootScope.$broadcast('preloader:hide');
                    if (response.status === 200) {
                        $scope.list.data = response.data.content;
                        $scope.list.count = response.data.totalElements;
                    }
                }, function errorCallback(response) {
                    $rootScope.$broadcast('preloader:hide');
                    if (response.status === 400) {
                        $scope.showSimpleToast("Não foi possível carregar os dados.")
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
            RestService.Delete('/agendaMedica/' + $scope.idToDelete)
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



