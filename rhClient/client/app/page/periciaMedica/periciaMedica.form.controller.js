(function () {
    'use strict';

    angular.module('app.page')
        .controller('periciaMedicaFormCtrl', ['$q', '$mdDialog', '$mdMedia', '$scope', '$mdToast', '$location', '$state', '$rootScope', 'GenericoService', 'EnumService', 'RestService', periciaMedicaFormCtrl]);

    function periciaMedicaFormCtrl($q, $mdDialog, $mdMedia, $scope, $mdToast, $location, $state, $rootScope, GenericoService, EnumService, RestService) {

        // Checa as regras de acesso da tela
        RestService.Get('/usuario/verificaPermissao/por/menu', { params: { menu: 'Agenda Perícia Médica' } })
            .then(function (response) {
                if (response.status === 200 && response.data) {

                    $scope.usuarioAdm = response.data.usuarioAdm;
                    $scope.podeGerenciar = false;

                    if (response.data.papeis)
                        $scope.podeGerenciar = response.data.papeis.includes('ROLE_PERICIA_MEDICA_CADASTRAR', 'ROLE_PERICIA_MEDICA_ATUALIZAR', 'ROLE_PERICIA_MEDICA_EXCLUIR') ? true : false;

                    $scope.autorizado = ($scope.usuarioAdm || $scope.podeGerenciar);

                    if ($scope.autorizado) {
                        //$scope.loadLdtInicialist();
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

        $scope.menorData = new Date();
        $scope.detalhes = false;
        $scope.edit = false;
        $scope.agendaMedica = {};
        $scope.agendaMedica.agendaMedicaDataDto = [];
        $scope.periciaMedica = {};

        $scope.list = {
            "funcionariosList": [],
            "especialidadeMedicaList": [],
            "intervaloList": [],
            "data": [],
            "periodoAgendamento": [
                { value: "MANHÃ", label: "Manhã" },
                { value: "TARDE", label: "Tarde" }
            ]
        }
        $scope.limitOptions = [5, 10, 15];
        $scope.query = {
            order: 'name',
            limit: 5,
            page: 1
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

        $scope.getListaEspecialidadesMedicas();

        $scope.getEspecialidadeMedicaClinicoGeral = function() {
            GenericoService.GetAllDropdown('especialidadeMedica/clinicoGeral', function (response) {
                if (response.status === 200) {
                    $scope.periciaMedica.especialidadeMedicaId = response.data.id;
                    $rootScope.$broadcast('preloader:hide');
                } else {
                    $scope.showSimpleToast("Não foi possível carregar os dados.")
                }
            })
        };

        $scope.carregarListaIntervalor = function () {
            for (let intervalo = 5; intervalo <= 60; intervalo += 5) {
                $scope.list.intervaloList.push(intervalo);
            }
        };

        $scope.exibirRelatorio = false;
        $scope.exibirAlertaRelatorio = false;
        $scope.periciaMedica = {};
        
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

        $scope.visualizarAgenda = function () {
            var maiorQue = false;

            if (tabs.length > 0) {
                while (tabs.length > 0) {
                    tabs.pop();
                  }
            }
            
            if($scope.periciaMedica.dataAgenda == null){
                maiorQue = true;
            }

            var deferred = $q.defer();
            var config = {
                params: {
                    especialidadeMedicaId: $scope.periciaMedica.especialidadeMedicaId,
                    dataAgenda: moment($scope.periciaMedica.dataAgenda).format("YYYY-MM-DD"),
                    isMaiorQue: maiorQue
                }
            }
            
            $rootScope.$broadcast('preloader:active');
            GenericoService.GetAll('/consultaPericiaMedica/agenda/filtro', config).then(
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
                                    
                                    //Regra pra imprmir horario manhã ou tarde ou ambos.
                                    if($scope.periciaMedica.periodoAgendamento == "MANHÃ"){
                                        if(lista.hora < "12:00"){
                                            data.horarios.push(horarios);
                                        }
                                    }else if($scope.periciaMedica.periodoAgendamento == "TARDE"){
                                        if(lista.hora >= "12:00"){
                                            data.horarios.push(horarios);
                                        }
                                    }else{
                                        data.horarios.push(horarios);
                                    }
                                });

                                mesLista.lista.push(data);
                            });

                            tabs.push(mesLista);

                        });
                        $scope.exibirRelatorio = true; 
                    }else{
                        $scope.exibirAlertaRelatorio = true;  
                    }
                });
            $rootScope.$broadcast('preloader:hide');
            return deferred.promise;

        }

        $scope.limpar = function () {
            delete $scope.medico;
            delete $scope.agendaMedica;
            delete $scope.list.data;
        }

        EnumService.Get("TipoAnaliseEnum").then(function (dados) {
            $scope.list.tipoAnaliseList = dados;
        });
        
        $scope.showPersonaliza = function () {
            var useFullScreen = ($mdMedia('sm') || $mdMedia('xs'));

            $mdDialog.show({
                require: 'agendaMedicaFormCtrl',
                scope: $scope,
                preserveScope: true,
                bindToController: true,
                controller: function () {
                    $scope.salvar = function () {
                        $mdDialog.cancel();
                    };
                    $scope.cancel = function () {
                        $mdDialog.cancel();
                    };
                },
                templateUrl: 'app/page/agendaMedica/dialogPersonalizacao.html',
                parent: angular.element(document.body),
                clickOutsideToClose: false,
                fullscreen: useFullScreen
            });
        }
        $scope.isTipoPericia = false;
        $scope.agendaMedica = angular.copy($scope.agendaMedicaInit);

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

        $scope.getListaEspecialidadesMedicas = function () {
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
                        $rootScope.$broadcast('preloader:hide');
                    }
                });
        };

        if ($state.params && $state.params.id) {
            if($state.params.edit == "true"){
                GenericoService.GetById('/consultaPericiaMedica/' + $state.params.id, function (response) {
                    $rootScope.$broadcast('preloader:active');
                    if (response.status === 200) {
                        $state.edit = true;
                        $scope.consultasPericiasMedicasDto = response.data;
                        $scope.pacientePericiaMedica = $scope.consultasPericiasMedicasDto.pacientePericiaMedicaDto;

                        $scope.periciaMedica.dataAgenda = moment($scope.pacientePericiaMedica.dataConsulta);
                        $scope.periciaMedica.id = $scope.pacientePericiaMedica.id;
                        $scope.periciaMedica.especialidadeMedicaId = $scope.consultasPericiasMedicasDto.especialidadeMedicaId;
                        $scope.periciaMedica.observacaoMedica = $scope.consultasPericiasMedicasDto.observacaoMedico;
                        $scope.visualizarAgenda();

                        $rootScope.$broadcast('preloader:hide');
                    }
                });
            }else{
                GenericoService.GetById('/pacientePericiaMedica/' + $state.params.id, function (response) {
                    $rootScope.$broadcast('preloader:active');
                    if (response.status === 200) {
                        $scope.pacientePericiaMedica = response.data;

                        if($scope.pacientePericiaMedica.contador >= 3){
                            $scope.periciaMedica.tipoPericia = "Não Presencial";
                            $scope.isTipoPericia = true;
                        }else{
                            $scope.periciaMedica.tipoPericia = "Presencial";
                        }
                        $scope.getEspecialidadeMedicaClinicoGeral();
                        $rootScope.$broadcast('preloader:hide');
                    }
                });
            }
        }

        if ($state.params && $state.params.detalhes) {
            $scope.detalhes = true;
        }

        $scope.carregarListaIntervalor();

        $scope.medicoSelecionado = function () {
            if ($scope.medico) {
                $scope.agendaMedica.medicoId = $scope.medico.id;
                $scope.agendaMedica.medicoNome = $scope.medico.descricao;
                $scope.getListaEspecialidadesMedicas();
            }
        };

        $scope.save = function () {
            $scope.periciaMedica.pacientePericiaMedicaId = $scope.pacientePericiaMedica.id;
            $scope.periciaMedica.compareceu = true;

            if (!$scope.list.data.length > 0) {
                $scope.showSimpleToast("Necessário Gerar a Agenda!");
                return;
            } else {
                $scope.agendaMedica.agendaMedicaDataDto = [];
                angular.forEach($scope.list.data, function (a) {
                    angular.forEach(a.agendaMedicaData, function (b) {
                        // monta o objeto pra persistência
                        let agendaDataObj = {
                            diaSemana: a.diaSemana,
                            horario: b.hora,
                            data: b.data
                        }
                        // remove todos os chekcbox desmarcados
                        if (b.checkHorario) {
                            $scope.agendaMedica.agendaMedicaDataDto.push(agendaDataObj);
                        }
                    });
                });
            }

            $rootScope.$broadcast('preloader:active');
            if ($scope.agendaMedica.id) {
                RestService.Update('/consultaPericiaMedica', $scope.periciaMedica)
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
            } else {
                RestService.Create('/consultaPericiaMedica', $scope.periciaMedica)
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
        }

        $scope.showRecusouPericia = function (ev, idConsultaPericiaMedica) {
            $scope.idConsultaPericiaMedica = idConsultaPericiaMedica;
            // Appending dialog to document.body to cover sidenav in docs app
            var confirm = $mdDialog.confirm()
                .title('Deseja confirmar a recusa da Consulta da Perícia Médica?')
                .textContent('Esta ação não poderá ser desfeita.')
                // .ariaLabel('Lucky day')
                .targetEvent(ev)
                .ok('Sim')
                .cancel('Não');

            $mdDialog.show(confirm).then(function () {
                if($scope.idConsultaPericiaMedica == null){
                    $scope.updatePacientePericiaMedica();
                }else{
                    $scope.updateConsultaPericiaMedica();
                }
            }, function () {
            });
        };

        $scope.updatePacientePericiaMedica = function () {
            $rootScope.$broadcast('preloader:active');
                GenericoService.Update('/pacientePericiaMedica/recusouPericia', $scope.pacientePericiaMedica.cpf, function (response) {
                    $rootScope.$broadcast('preloader:hide');
                    if (response.status === 201 && response.data.success) {
                        $scope.showSimpleToast(response.data.message);
                        $location.path('/periciaMedica/gestao');
                    } else if (response.status === 400) {
                        $scope.showSimpleToast(response.data.message);
                        $location.path('periciaMedica/formulario/' + $state.params.id)
                    }
                });
        }

        $scope.updateConsultaPericiaMedica = function () {
            $rootScope.$broadcast('preloader:active');
                GenericoService.Update('/consultaPericiaMedica/recusouPericia', $scope.idConsultaPericiaMedica, function (response) {
                    $rootScope.$broadcast('preloader:hide');
                    if (response.status === 201 && response.data.success) {
                        $scope.showSimpleToast(response.data.message);
                        $location.path('/periciaMedica/gestao');
                    } else if (response.status === 400) {
                        $scope.showSimpleToast(response.data.message);
                        $location.path('periciaMedica/formulario/' + $state.params.id)
                    }
                });
        }

        $scope.goBack = function () {
            $location.path('/periciaMedica/gestao');
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