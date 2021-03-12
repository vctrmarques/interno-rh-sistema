(function () {
    'use strict';

    angular.module('app.page')
        .controller('prontuarioPericiaMedicaFormCtrl', ['configValue','$q', '$mdDialog', '$mdMedia', '$scope', '$mdToast', '$location', '$state', '$rootScope', 'GenericoService', 'EnumService', 'RestService', prontuarioPericiaMedicaFormCtrl]);

    function prontuarioPericiaMedicaFormCtrl(configValue, $q, $mdDialog, $mdMedia, $scope, $mdToast, $location, $state, $rootScope, GenericoService, EnumService, RestService) {

        // Checa as regras de acesso da tela
        RestService.Get('/usuario/verificaPermissao/por/menu', { params: { menu: 'Perícias Médicas Agendadas'} })
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

        $scope.menorData = new Date();
        $scope.detalhes = false;
        $scope.edit = false;
        $scope.prontuarioPericiaMedica = {};
        $scope.prontuarioPericiaMedica.pacientePericiaMedicaId = 0;
        $scope.prontuarioPericiaMedica.consultaPericiaMedicaId = 0;
        $scope.prontuarioPericiaMedica.medicoId = 0;
        $scope.prontuarioPericiaMedica.ano = 0;
        $scope.prontuarioPericiaMedica.numeroPericia = 0;

        $scope.list = {
            "funcionariosList": [],
            "especialidadeMedicaList": [],
            "intervaloList": [],
            "data": [],
            "periodoAgendamento": [
                { value: "MANHÃ", label: "Manhã" },
                { value: "TARDE", label: "Tarde" }
            ],
            "periodoProximaPericia": [
                { value: "6_meses", label: "6 meses" },
                { value: "1_anos", label: "1 ano" },
                { value: "2_anos", label: "2 anos" },
                { value: "3_anos", label: "3 anos" },
                { value: "4_anos", label: "4 anos" },
                { value: "5_anos", label: "5 anos" }
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

        $scope.limpar = function () {
            delete $scope.medico;
            delete $scope.agendaMedica;
            delete $scope.list.data;
        }

        EnumService.Get("TipoAcaoPericiaMedicaEnum").then(function (dados) {
            $scope.list.tipoAcaoPerciaMedicaList = dados;
        });
        
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
            GenericoService.GetById('/consultaPericiaMedica/' + $state.params.id, function (response) {
                $rootScope.$broadcast('preloader:active');
                if (response.status === 200) {
                    $state.edit = true;
                    $scope.consultaPericiaMedica = response.data;
                    $scope.prontuarioPericiaMedica.consultaPericiaMedicaId = $scope.consultaPericiaMedica.id;
                    $scope.prontuarioPericiaMedica.pacientePericiaMedicaId = $scope.consultaPericiaMedica.pacientePericiaMedicaDto.id
                    $scope.prontuarioPericiaMedica.medicoId = 1;
                    var data = new Date();
                    $scope.prontuarioPericiaMedica.numeroPericiaConcatenado = $state.params.id + "/ "+ data.getFullYear();
                    $scope.prontuarioPericiaMedica.numeroPericia = $state.params.id;
                    $scope.prontuarioPericiaMedica.anoPericia = data.getFullYear();
                    $scope.consultaPericiaMedica.dataNascimento = moment($scope.consultaPericiaMedica.pacientePericiaMedicaDto.dataNascimento);
                    $scope.consultaPericiaMedica.paciente = $scope.consultaPericiaMedica.pacientePericiaMedicaDto.paciente;
                    $scope.consultaPericiaMedica.nomeMae = $scope.consultaPericiaMedica.pacientePericiaMedicaDto.nomeMae;
                    $scope.consultaPericiaMedica.sexo = $scope.consultaPericiaMedica.pacientePericiaMedicaDto.sexo;

                    $scope.rnDiasConsulta = true;
                    $scope.rnAposentar = true;

                    GenericoService.GetById('/prontuarioPericiaMedica/ByIdPacientePerica/' + $scope.prontuarioPericiaMedica.pacientePericiaMedicaId, function (response) {
                        $rootScope.$broadcast('preloader:active');
                        if (response.status === 200) {
                            $rootScope.$broadcast('preloader:hide');
                            $scope.prontuarioPericiaMedicaList = response.data;
                        }
                    });

                }
            });
        }

        if ($state.params && $state.params.detalhes) {
            $scope.detalhes = true;
        }

        $scope.hasChanged = function() {
            if ($scope.prontuarioPericiaMedica.acao == 'REMARCAR_CLINICO_GERAL_PRESENCIAL' || $scope.prontuarioPericiaMedica.acao == 'REMARCAR_CLINICO_GERAL_NAO_PRESENCIAL'
                || $scope.prontuarioPericiaMedica.acao == 'RETORNO_CLINICO_GERAL_PRESENCIAL' || $scope.prontuarioPericiaMedica.acao == 'RETORNO_CLINICO_GERAL_NAO_PRESENCIAL'
                    ||  $scope.prontuarioPericiaMedica.acao == 'AGENDAR_ESPECIALISTA'){
                        delete $scope.prontuarioPericiaMedica.diasProximaConsulta;
                        $scope.rnDiasConsulta = false;
            }else{
                if ($scope.prontuarioPericiaMedica.acao !== undefined){
                    $scope.rnDiasConsulta = true;
                }
            }

            if ($scope.prontuarioPericiaMedica.acao == 'APONSENTAR'){
                $scope.rnAposentar = false;
            }else{
                if ($scope.prontuarioPericiaMedica.acao !== undefined && $scope.prontuarioPericiaMedica.acao != 'APONSENTAR'){
                    $scope.rnAposentar = true;
                    delete $scope.prontuarioPericiaMedica.especialidadeMedicaId;
                    delete $scope.prontuarioPericiaMedica.periodoProximaPericia;
                }
            }
        }

        $scope.showLaudo = function(){
            $rootScope.$broadcast('preloader:active');
            GenericoService.GetAllRelatorio('/prontuariosPericiasMedicas').then(
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

            var titulo = { text: 'Laudo', alignment: 'center', margin: [0, 10] };
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
                    widths: ['auto', '*', '*', '*', '*', '*'],
                    body: [
                        [{ text: 'Matrícula', fontSize: 10, fillColor: 'lightgray', style: 'tableHeader', alignment: 'center' },
                        { text: 'Status', fontSize: 10, fillColor: 'lightgray', style: 'tableHeader', alignment: 'center' },
                        { text: 'Nomes', fontSize: 10, fillColor: 'lightgray', style: 'tableHeader', alignment: 'center' },
                        { text: 'CRM', fontSize: 10, fillColor: 'lightgray', style: 'tableHeader', alignment: 'center' },
                        { text: 'Especialidade', fontSize: 10, fillColor: 'lightgray', style: 'tableHeader', alignment: 'center' },
                        { text: 'Filial', fontSize: 10, fillColor: 'lightgray', style: 'tableHeader', alignment: 'center' }
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
   
            docDefinition.content.push(lista);

            return docDefinition;

        }

        $scope.save = function () {
            $rootScope.$broadcast('preloader:active');
            if ($scope.prontuarioPericiaMedica.id) {
                RestService.Update('/prontuarioPericiaMedica', $scope.prontuarioPericiaMedica)
                    .then(function successCallback(response) {
                        $rootScope.$broadcast('preloader:hide');
                        if (response.status === 201 && response.data.success) {
                            $scope.showSimpleToast(response.data.message);
                            $location.path('/prontuarioPericiaMedica/gestao');
                        }
                    }, function errorCallback(response) {
                        $rootScope.$broadcast('preloader:hide');
                        if (response.status === 400) {
                            $scope.showSimpleToast(response.data.message);
                        }
                    });
            } else {
                RestService.Create('/prontuarioPericiaMedica', $scope.prontuarioPericiaMedica)
                    .then(function successCallback(response) {
                        $rootScope.$broadcast('preloader:hide');
                        if (response.status === 201 && response.data.success) {
                            $scope.showSimpleToast(response.data.message);
                            $location.path('/prontuarioPericiaMedica/gestao');
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
            $location.path('/prontuarioPericiaMedica/gestao');
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