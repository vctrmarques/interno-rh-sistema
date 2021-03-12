(function () {
    'use strict';

    angular.module('app.page')
        .controller('prontuarioPericiaMedicaVisualizarCtrl', ['$q', '$mdDialog', '$mdMedia', '$scope', '$mdToast', '$location', '$state', '$rootScope', 'GenericoService', 'EnumService', 'RestService', prontuarioPericiaMedicaVisualizarCtrl]);

    function prontuarioPericiaMedicaVisualizarCtrl($q, $mdDialog, $mdMedia, $scope, $mdToast, $location, $state, $rootScope, GenericoService, EnumService, RestService) {

        // Checa as regras de acesso da tela
        RestService.Get('/usuario/verificaPermissao/por/menu', { params: { menu: 'Perícias Médicas Agendadas' } })
            .then(function (response) {
                if (response.status === 200 && response.data) {

                    $scope.usuarioAdm = response.data.usuarioAdm;
                    $scope.podeGerenciar = false;

                    if (response.data.papeis)
                        $scope.podeGerenciar = response.data.papeis.includes('ROLE_PRONTUARIO_PERICIA_MEDICA_CADASTRAR', 'ROLE_PRONTUARIO_PERICIA_MEDICA_ATUALIZAR', 'ROLE_PRONTUARIO_PERICIA_MEDICA_EXCLUIR') ? true : false;

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

        $scope.list = {
            "funcionariosList": [],
            "especialidadeMedicaList": [],
            "intervaloList": [],
            "data": []
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

        $scope.carregarListaIntervalor = function () {
            for (let intervalo = 5; intervalo <= 60; intervalo += 5) {
                $scope.list.intervaloList.push(intervalo);
            }
        };

        EnumService.Get("TipoAnaliseEnum").then(function (dados) {
            $scope.list.tipoAnaliseList = dados;
        })

        $scope.exibirRelatorio = false;
        $scope.exibirAlertaRelatorio = false;
        $scope.periciaMedica = {};

        $scope.limpar = function () {
            delete $scope.medico;
            delete $scope.agendaMedica;
            delete $scope.list.data;
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
            GenericoService.GetById('/prontuarioPericiaMedica/ByIdConsultaPericia/' + $state.params.id, function (response) {
                $rootScope.$broadcast('preloader:active');
                if (response.status === 200) {
                    $state.edit = true;
                    $scope.prontuarioPericiaMedica = response.data;
                    $scope.prontuarioPericiaMedica.numeroProcessoConcatenado =  $scope.prontuarioPericiaMedica.numeroPericia + "/" + $scope.prontuarioPericiaMedica.anoPericia;
                    $scope.prontuarioPericiaMedica.dataNascimento = moment($scope.prontuarioPericiaMedica.dataNascimento);
                    $scope.prontuarioPericiaMedica.dataProximaPericia = moment($scope.prontuarioPericiaMedica.dataProximaPericia);

                    $rootScope.$broadcast('preloader:hide');
                }
            });
            
        }
        
        if ($state.params && $state.params.visualizar) {
            $scope.visualizar = $state.params.visualizar;
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

        $scope.goBack = function () {
            if ($scope.visualizar == 'true') {
                $location.path('/prontuarioPericiaMedica/gestao');
            }else{
                debugger
                var id = $scope.prontuarioPericiaMedica.id;
                $location.path('/prontuarioPericiaMedica/formulario/'+ id);
            }
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