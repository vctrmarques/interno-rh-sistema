(function () {
    'use strict';

    angular.module('app.page')
        .controller('agendaMedicaFormCtrl', ['$q', '$mdDialog', '$mdMedia', '$scope', '$mdToast', '$location', '$state', '$rootScope', 'GenericoService', 'RestService', agendaMedicaFormCtrl]);

    function agendaMedicaFormCtrl($q, $mdDialog, $mdMedia, $scope, $mdToast, $location, $state, $rootScope, GenericoService, RestService) {

        // Checa as regras de acesso da tela
        RestService.Get('/usuario/verificaPermissao/por/menu', { params: { menu: 'Agenda Médica' } })
            .then(function (response) {
                if (response.status === 200 && response.data) {

                    $scope.usuarioAdm = response.data.usuarioAdm;
                    $scope.podeGerenciar = false;

                    if (response.data.papeis)
                        $scope.podeGerenciar = response.data.papeis.includes('ROLE_AGENDA_MEDICA_CADASTRAR', 'ROLE_AGENDA_MEDICA_ATUALIZAR', 'ROLE_AGENDA_MEDICA_EXCLUIR') ? true : false;

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

        $scope.detalhes = false;
        $scope.edit = false;
        $scope.agendaMedica = {};
        $scope.agendaMedica.agendaMedicaDataDto = [];

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

        $scope.gerar = function () {
            if (!$scope.agendaMedica.dataInicial) {
                $scope.showSimpleToast("Favor selecionar uma data inicial!");
                return;
            }

            if (!$scope.agendaMedica.dataFinal) {
                $scope.showSimpleToast("Favor selecionar uma data final!");
                return;
            }

            if (!$scope.agendaMedica.horaInicial) {
                $scope.showSimpleToast("Favor selecionar um horário inicial!");
                return;
            }

            if (!$scope.agendaMedica.horaFinal) {
                $scope.showSimpleToast("Favor selecionar um horário final!");
                return;
            }

            if (!$scope.agendaMedica.intervalo) {
                $scope.showSimpleToast("Favor selecionar o um intervalo!");
                return;
            }

            var horaInicial = moment($scope.agendaMedica.horaInicial).format("HH:mm");
            var horaFinal = moment($scope.agendaMedica.horaFinal).format("HH:mm");


            if (!$scope.agendaMedica.domingo) {
                $scope.agendaMedica.domingo = false;
            }
            if (!$scope.agendaMedica.segunda) {
                $scope.agendaMedica.segunda = false;
            }
            if (!$scope.agendaMedica.terca) {
                $scope.agendaMedica.terca = false;
            }
            if (!$scope.agendaMedica.quarta) {
                $scope.agendaMedica.quarta = false;
            }
            if (!$scope.agendaMedica.quinta) {
                $scope.agendaMedica.quinta = false;
            }
            if (!$scope.agendaMedica.sexta) {
                $scope.agendaMedica.sexta = false;
            }
            if (!$scope.agendaMedica.sabado) {
                $scope.agendaMedica.sabado = false;
            }

            if ($scope.agendaMedica.domingo || $scope.agendaMedica.segunda || $scope.agendaMedica.terca || $scope.agendaMedica.quarta ||
                $scope.agendaMedica.quinta || $scope.agendaMedica.sexta || $scope.agendaMedica.sabado) {
                $scope.agendaMedica.semanal = true;
            } else {
                $scope.agendaMedica.semanal = false;
            }

            var deferred = $q.defer();
            var config = {
                params: {
                    dtInicial: $scope.agendaMedica.dataInicial ? $scope.agendaMedica.dataInicial.format('YYYY-MM-DD') : null,
                    dtFinal: $scope.agendaMedica.dataFinal ? $scope.agendaMedica.dataFinal.format('YYYY-MM-DD') : null,
                    semanal: $scope.agendaMedica.semanal,
                    domingo: $scope.agendaMedica.domingo,
                    segunda: $scope.agendaMedica.segunda,
                    terca: $scope.agendaMedica.terca,
                    quarta: $scope.agendaMedica.quarta,
                    quinta: $scope.agendaMedica.quinta,
                    sexta: $scope.agendaMedica.sexta,
                    sabado: $scope.agendaMedica.sabado,
                    horaInicial: horaInicial,
                    horaFinal: horaFinal,
                    intervalo: $scope.agendaMedica.intervalo
                }
            }
            $rootScope.$broadcast('preloader:active');
            GenericoService.GetAll('/agendaMedica/calendario/search', config).then(
                function (response) {
                    if (response.data && response.data.length > 0) {
                        $scope.list.data = response.data;
                        $scope.agendaMedica.checkeAllClean = true;
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

        $scope.isAllSelected = function (item) {
            if (item.checkeAll) {
                angular.forEach(item.agendaMedicaData, function (m) {
                    m.checkHorario = false;
                });
                $scope.agendaMedica.checkeAll = false;
            } else {
                angular.forEach(item.agendaMedicaData, function (m) {
                    m.checkHorario = true;
                });
                $scope.agendaMedica.checkeAll = true;
            }
        }

        $scope.isAllCleanSelected = function () {
            angular.forEach($scope.list.data, function (d) {
                if ($scope.agendaMedica.checkeAllClean) {
                    angular.forEach(d.agendaMedicaData, function (a) {
                        a.checkHorario = true;
                    });
                    d.checkeAll = true;
                    d.checkeAllClean = true;
                } else {
                    angular.forEach(d.agendaMedicaData, function (a) {
                        a.checkHorario = false;
                    });
                    d.checkeAll = false;
                    d.checkeAllClean = false;
                }

            });
        }

        $scope.isAllSemanalSelected = function () {
            if ($scope.checkeAllSemana) {
                $scope.agendaMedica.domingo = true;
                $scope.agendaMedica.segunda = true;
                $scope.agendaMedica.terca = true;
                $scope.agendaMedica.quarta = true;
                $scope.agendaMedica.quinta = true;
                $scope.agendaMedica.sexta = true;
                $scope.agendaMedica.sabado = true;
            } else {
                $scope.agendaMedica.domingo = false;
                $scope.agendaMedica.segunda = false;
                $scope.agendaMedica.terca = false;
                $scope.agendaMedica.quarta = false;
                $scope.agendaMedica.quinta = false;
                $scope.agendaMedica.sexta = false;
                $scope.agendaMedica.sabado = false;
            }
        }

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
            GenericoService.GetById('/agendaMedica/' + $state.params.id, function (response) {
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

                    $scope.edit = true;
                    
                    var timeInicial = '1970-01-01T'+$scope.agendaMedica.horaInicial+'.000';
                    var timeFinal = '1970-01-01T'+$scope.agendaMedica.horaFinal+'.000';
                    $scope.agendaMedica.horaInicial = new Date(timeInicial);
                    $scope.agendaMedica.horaFinal = new Date(timeFinal);


                    $scope.list.data =  response.data.agendaMedicaList;
                    $scope.agendaMedica.checkeAllClean = true;

                    $rootScope.$broadcast('preloader:hide');

                }
            });
        }else{
            $scope.menorData = new Date();
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

            if (!$scope.agendaMedica.medicoId) {
                $scope.showSimpleToast("Favor preencher o nome do Médico(a)!");
                return;
            }

            if (!$scope.agendaMedica.especialidadeMedicaId.length > 0) {
                $scope.showSimpleToast("Favor selecionar ao menos uma Especialidade Médica.");
                return;
            }

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

            $scope.agendaMedica.horaInicial = moment($scope.agendaMedica.horaInicial).format("HH:mm");
            $scope.agendaMedica.horaFinal = moment($scope.agendaMedica.horaFinal).format("HH:mm");
            $rootScope.$broadcast('preloader:active');
            if ($scope.agendaMedica.id) {
                RestService.Update('/agendaMedica', $scope.agendaMedica)
                    .then(function successCallback(response) {
                        $rootScope.$broadcast('preloader:hide');
                        if (response.status === 201 && response.data.success) {
                            $scope.showSimpleToast(response.data.message);
                            $location.path('/agendaMedica/gestao');
                        }
                    }, function errorCallback(response) {
                        $rootScope.$broadcast('preloader:hide');
                        if (response.status === 400) {
                            $scope.showSimpleToast(response.data.message);
                        }
                    });
            } else {
                RestService.Create('/agendaMedica', $scope.agendaMedica)
                    .then(function successCallback(response) {
                        $rootScope.$broadcast('preloader:hide');
                        if (response.status === 201 && response.data.success) {
                            $scope.showSimpleToast(response.data.message);
                            $location.path('/agendaMedica/gestao');
                        }
                    }, function errorCallback(response) {
                        $rootScope.$broadcast('preloader:hide');
                        if (response.status === 400) {
                            $scope.showSimpleToast(response.data.message);
                        }
                    });
            }
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