(function () {
    'use strict';

    angular.module('app.page')
        .controller('agendaMedicaRelatorioCtrl', ['configValue','$q', '$scope', '$mdToast', '$location', '$state', '$rootScope', '$filter', 'GenericoService', 'RestService', agendaMedicaRelatorioCtrl]);

    function agendaMedicaRelatorioCtrl(configValue, $q, $scope, $mdToast, $location, $state, $rootScope, $filter, GenericoService, RestService) {

        // Checa as regras de acesso da tela
        RestService.Get('/usuario/verificaPermissao/por/menu', { params: { menu: 'Agenda Médica' } })
            .then(function (response) {
                if (response.status === 200 && response.data) {

                    $scope.usuarioAdm = response.data.usuarioAdm;
                    $scope.podeGerenciar = false;

                    if (response.data.papeis)
                        $scope.podeGerenciar = response.data.papeis.includes('ROLE_AGENDA_MEDICA_VISUALIZAR') ? true : false;

                    $scope.autorizado = ($scope.usuarioAdm || $scope.podeGerenciar);

                    if ($scope.autorizado) {
                        //$scope.loadList();
                    } else {
                        $location.path('page/403');
                    }
                }
            }, function errorCallback(response) {
                if (response.status === 400) {
                    $scope.showSimpleToast(response.data.message);
                }
            });

        //Construtor do objeto especialidadeMedica
        $scope.agendaMedicaInit = {
            codigo: null
        }

        $scope.agendaMedica = {};
        $scope.agendaMedica.agendaMedicaData = [];

        $scope.list = {
            "especialidadeMedicaList": [],
            "intervaloList": [],
            "data": [],
        }

        $scope.limitOptions = [5, 10, 15];
        $scope.query = {
            order: 'name',
            limit: 5,
            page: 1
        };
        
        $scope.exibirRelatorio = false;
        $scope.dataHoje = new Date();

        $scope.validarHorario = function (horaInicial, horaFinal) {
            if (horaInicial.getTime() === horaFinal.getTime()) {
                $scope.showSimpleToast("Os horários são iguai, Favor selecionar o um horario superior!");
                delete $scope.agendaMedica.horaFinal;
                return;
            } else if (horaInicial.getTime() > horaFinal.getTime()) {
                $scope.showSimpleToast("O horários final não pode ser menor que o inicial!");
                delete $scope.agendaMedica.horaFinal;
                return;
            }
        }

        $scope.carregarListaIntervalor = function () {
            for (let intervalo = 5; intervalo <= 60; intervalo += 5) {
                $scope.list.intervaloList.push(intervalo);
            }
        };

        var tabs = [],
        selected = null,
        previous = null;
        $scope.tabs = tabs;
        $scope.selectedIndex = 2;
        $scope.$watch('selectedIndex', function(current, old){
            previous = selected;
            selected = tabs[current];
        });
        $scope.addTab = function (title, view) {
            view = view || title + " Content View";
            tabs.push({ nome: '', especialidadeMedica: {}, lista: {}});
        };
        $scope.removeTab = function (tab) {
            var index = tabs.indexOf(tab);
            tabs.splice(index, 1);
        };


        $scope.gerar = function () {
            if (tabs.length > 0) {
                while (tabs.length > 0) {
                    tabs.pop();
                  }
            }

            var especialidadeMedicaList = [];
            if ($scope.agendaMedica.especialidadeMedicaId && $scope.agendaMedica.especialidadeMedicaId.length > 0) {
                $scope.agendaMedica.especialidadeMedicaId.forEach(id => {
                    especialidadeMedicaList.push(id);
                });
            }

            if (!$scope.agendaMedica.dataInicial) {
                $scope.showSimpleToast("Favor selecionar uma data inicial!");
                return;
            }

            if (!$scope.agendaMedica.dataFinal) {
                $scope.showSimpleToast("Favor selecionar uma data final!");
                return;
            }

            var deferred = $q.defer();
            var config = {
                params: {
                    nome: $scope.agendaMedica.medicoNome,
                    especialidadeMedicaList: especialidadeMedicaList,
                    dtInicial: $scope.agendaMedica.dataInicial ? $scope.agendaMedica.dataInicial.format('YYYY-MM-DD') : null,
                    dtFinal: $scope.agendaMedica.dataFinal ? $scope.agendaMedica.dataFinal.format('YYYY-MM-DD') : null
                }
            }
            
            $rootScope.$broadcast('preloader:active');
            GenericoService.GetAll('/agendaMedica/relatorio/search', config).then(
                function (response) {
                    if (response.data && response.data.length > 0) {
                        $scope.list.data = response.data;

                        angular.forEach($scope.list.data, function (l) {
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
                        $scope.exibirRelatorio = true;        
                    }
                });
            $rootScope.$broadcast('preloader:hide');
            return deferred.promise;

        }

        $scope.querySearchMedico = function (query) {
            var deferred = $q.defer();
            var config = { params: { search: query } }
            if (query) {
                if (query.length > 2) {
                    $rootScope.$broadcast('preloader:active');
                    GenericoService.GetAll('/medico/search', config).then(
                        function (response) {
                            if (response.data && response.data.length > 0) {
                                deferred.resolve(response.data);

                            }
                        });
                } else {
                    $scope.medico = [];
                }
                $rootScope.$broadcast('preloader:hide');
            }
            return deferred.promise;
        }

        $scope.getListaMedicoEspecialidadeMedica = function () {
            var config = {
                params: { nome: $scope.agendaMedica.medicoNome }
            };

            $scope.promise = GenericoService.GetAll('/medicoEspecialiade/search', config).then(
                function (response) {
                    if (response.status === 200) {
                        $scope.list.especialidadeMedicaList = response.data;
                        $rootScope.$broadcast('preloader:hide');
                    } else {
                        $scope.showSimpleToast("Não foi possível carregar os dados.")
                    }
                });

        };

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

        $scope.detalhes = false;
        if ($state.params && $state.params.detalhes) {
            $scope.detalhes = true;
        }

        $scope.carregarListaIntervalor();
        $scope.getListaEspecialidadesMedicas();

        $scope.medicoSelecionado = function () {
            if ($scope.medico) {
                $scope.agendaMedica.medicoId = $scope.medico.id;
                $scope.agendaMedica.medicoNome = $scope.medico.descricao;
                $scope.getListaMedicoEspecialidadeMedica();
            }
        };

        $scope.convertDate = function(data, comHora) {
			var valor = moment(data);
			if (comHora) {
				return $filter('date')(new Date(valor), 'dd/MM/yyyy - HH:mm');
			} else {
				return $filter('date')(new Date(valor), 'dd/MM/yyyy');
			}
        }
        
        $scope.save = function () {
            GenericoService.ToDataURL($rootScope.defaultPath + configValue.logo,
                function (dataURL) {
                    pdfMake.createPdf(getDocDefinition(tabs, dataURL)).open()
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

        $scope.goBack = function () {
            $location.path('/agendaMedica/gestao');
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