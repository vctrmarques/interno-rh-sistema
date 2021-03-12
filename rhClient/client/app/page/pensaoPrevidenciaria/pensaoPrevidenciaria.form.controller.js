(function () {
    'use strict';

    angular.module('app.page')
        .controller('pensaoPrevidenciariaFormCtrl', ['$state', '$location', '$q', 'configValue', '$scope', '$mdToast', '$mdDialog', '$mdMedia', '$rootScope', 'GenericoService', 'RestService', 'EnumService', '$filter', 'FileUploader', '$http', pensaoPrevidenciariaFormCtrl]);

    function pensaoPrevidenciariaFormCtrl($state, $location, $q, configValue, $scope, $mdToast, $mdDialog, $mdMedia, $rootScope, GenericoService, RestService, EnumService, $filter, FileUploader, $http) {

        GenericoService.VerificaPermissao('/usuario/verificaPermissao', 'ROLE_ADMIN').then(
            function (response) {

            }, function errorCallback(response) {
                if (response.status === 400) {
                    $location.path('page/403');
                }
            });

        $scope.limitOptions = [5, 10, 15];
        $scope.list = {
            "count": 0,
            "data": []
        };
        $scope.detalhes = false;
        $scope.pensaoPrevidenciaria = {};
        $scope.pensaoPrevidenciariaPagamento = {};
        $scope.pensaoPrevidenciariaCreate = {};

        if ($state.params && $state.params.detalhes) {
            $scope.detalhes = true;
        }

        $scope.limpaFiltro = function () {
            delete $scope.descricaoBusca;
        }

        /*Início - dados ex-segurado*/

        $scope.querySearchExSegurado = function (query) {
            var deferred = $q.defer();
            var config = { params: { search: query } }
            if (query) {
                if (query.length > 2) {
                    GenericoService.GetAll('/pensao/exSegurado/searchComplete', config).then(
                        function (response) {
                            if (response.data && response.data.length > 0) {
                                deferred.resolve(response.data);
                                $scope.pensaoPrevidenciaria.status = true;
                            }
                        });
                }
            }
            return deferred.promise;
        };

        $scope.exSeguradoSelecionado = function () {
            if ($scope.exSegurado) {
                $scope.pensaoPrevidenciaria.funcionarioId = $scope.exSegurado.id;

                $rootScope.$broadcast('preloader:active');
                GenericoService.GetById('/funcionario/' + $scope.pensaoPrevidenciaria.funcionarioId, function (response) {
                    $rootScope.$broadcast('preloader:hide');
                    if (response.status === 200) {
                        $scope.funcionario = response.data;
                        $scope.origem = $scope.funcionario.filial.nomeFilial;
                    } else {
                        $scope.showSimpleToast("Funcionário não encontrado na base");
                    }
                });

                $scope.dadosExSegurado = $scope.exSegurado;
            }
        };

        /*Fim - dados ex-segurado*/

        /*Início - Aba Dados Pensionista*/

        EnumService.Get("GeneroEnum").then(function (dados) {
            $scope.list.generos = dados;
        });

        EnumService.Get("EstadoCivilEnum").then(function (dados) {
            $scope.list.estadoCivil = dados;
        });

        EnumService.Get("GrauInstrucaoEnum").then(function (dados) {
            $scope.list.grauInstrucao = dados;
        });

        EnumService.Get("CorPeleEnum").then(function (dados) {
            $scope.list.corPeles = dados;
        });

        EnumService.Get("TipoSanguineoEnum").then(function (dados) {
            $scope.list.tipoSanguineo = dados;
        });

        // Busca nacionalidades
        RestService.Get('/nacionalidade/search')
            .then(function (response) {
                if (response.status === 200 && response.data) {
                    $scope.nacionalidadeList = response.data;
                }
            }, function errorCallback(response) {
                if (response.status === 400) {
                    $scope.showSimpleToast(response.data.message);
                }
            });

        $scope.getUnidadesFederativas = function () {
            $rootScope.$broadcast('preloader:active');
            GenericoService.GetAllDropdown('listaUfs', function (response) {
                if (response.status === 200) {
                    $scope.list.uf = response.data;
                    $rootScope.$broadcast('preloader:hide');
                } else if (response.status === 500) {
                    $scope.showSimpleToast("Falha no carregamento de dados, favor contate o administrador do sistema");
                }
            });
        }

        $scope.getUnidadesFederativas();


        $scope.getMunicipios = function (id, naturalidade) {
            if (naturalidade) {
                $scope.getNaturalidadeByUf(id);
            } else {
                $scope.getMunicipiosByUf(id);
            }
        }

        $scope.getNaturalidadeByUf = function (id) {
            GenericoService.GetAllDropdownById(id, function (response) {
                if (response.status === 200) {
                    $scope.list.naturalidade = response.data;
                } else if (response.status === 500) {
                    $scope.showSimpleToast("Não existem municípios cadastrados para este estado");
                }
            });
        }

        $scope.getMunicipiosByUf = function (id) {
            GenericoService.GetAllDropdownById(id, function (response) {
                if (response.status === 200) {
                    $scope.list.municipios = response.data;
                } else if (response.status === 500) {
                    $scope.showSimpleToast("Não existem municípios cadastrados para este estado");
                }
            });
        }

        // EnumService.Get("TipoFamiliaEnum").then(function (dados) {
        //     $scope.list.tipoFamilia = dados;
        // });

        $scope.list.tipoFamilia = [
            { value: 'PRIMEIRA', label: 'Primeira' },
            { value: 'SEGUNDA', label: 'Segunda' },
            { value: 'TERCEIRA', label: 'Terceira' },
            { value: 'QUARTA', label: 'Quarta' },
            { value: 'QUINTA', label: 'Quinta' }
        ];

        EnumService.Get("GrauParentescoEnum").then(function (dados) {
            $scope.list.grauParentesco = dados;
        });

        EnumService.Get("MotivoPensionistaEnum").then(function (dados) {
            $scope.list.motivo = dados;
        });

        $scope.querySearchResponsavelLegal = function (query) {

            var deferred = $q.defer();
            var config = { params: { search: query } }
            if (query) {
                if (query.length > 2) {
                    $rootScope.$broadcast('preloader:active');
                    GenericoService.GetAll('/responsavelLegal/searchComplete', config).then(
                        function (response) {
                            if (response.data && response.data.length > 0) {
                                deferred.resolve(response.data);

                            }
                        });
                } else {
                    $scope.responsavelLegal = [];
                }
                $rootScope.$broadcast('preloader:hide');
            }
            return deferred.promise;
        };

        $scope.responsavelLegalSelecionado = function () {
            if ($scope.responsavelLegal) {
                $scope.pensaoPrevidenciaria.responsavelLegalId = $scope.responsavelLegal.id;
            }
        };

        /*Fim - Aba Dados Pensionista*/

        /*Início - Aba Dados Pagamento*/

        EnumService.Get("TipoRateioEnum").then(function (dados) {
            $scope.list.tipoRateio = dados;
        });

        EnumService.Get("TipoPensaoEnum").then(function (dados) {
            $scope.list.tipoPensao = dados;
        });

        $scope.list.comParidade = [{ label: "Sim", value: true }, { label: "Não", value: false }];

        EnumService.Get("TipoCotaEnum").then(function (dados) {
            $scope.list.tipoCota = dados;
        });

        EnumService.Get("OperacaoContaBancariaEnum").then(function (dados) {
            $scope.list.operacao = dados;
        });

        $scope.querySearchBanco = function (query) {

            var deferred = $q.defer();
            var config = {
                params: {
                    search: query,
                }
            }
            if (query) {
                if (query.length > 2) {
                    $rootScope.$broadcast('preloader:active');
                    GenericoService.GetAll('/banco/searchComplete', config).then(
                        function (response) {
                            if (response.data && response.data.length > 0) {
                                deferred.resolve(response.data);
                            }
                        });
                } else {
                    $scope.banco = [];
                }
                $rootScope.$broadcast('preloader:hide');
            }
            return deferred.promise;
        };

        $scope.bancoSelecionado = function () {
            if ($scope.banco) {
                $scope.bancoSelecionadoId = $scope.banco.id;
            }
        };

        $scope.querySearchAgencia = function (query) {


            var deferred = $q.defer();
            var config = { params: { search: query, bancoId: $scope.bancoSelecionadoId } }
            if (query) {
                if (query.length > 2) {
                    $rootScope.$broadcast('preloader:active');
                    GenericoService.GetAll('/agencia/searchComBanco', config).then(
                        function (response) {
                            if (response.data && response.data.length > 0) {
                                deferred.resolve(response.data);

                            }
                        });
                } else {
                    $scope.agencia = [];
                }
                $rootScope.$broadcast('preloader:hide');
            }
            return deferred.promise;
        };

        $scope.agenciaSelecionada = function () {
            if ($scope.agencia) {
                $scope.pensaoPrevidenciariaPagamento.agenciaId = $scope.agencia.id;
            }
        };

        EnumService.Get("DadoBancarioTipoContaEnum").then(function (dados) {
            $scope.list.tipoConta = dados;
        });

        EnumService.Get("CondicaoIsencaoEnum").then(function (dados) {
            $scope.list.condicaoIsencao = dados;
        });



        /*Fim - Aba Dados Pagamento*/

        if ($state.params && $state.params.id) {
            $rootScope.$broadcast('preloader:active');
            GenericoService.GetById('/pensaoPrevidenciaria/' + $state.params.id, function (response) {
                $rootScope.$broadcast('preloader:hide');
                if (response.status === 200) {
                    $scope.pensaoPrevidenciaria = response.data;
                    $scope.pensaoPrevidenciariaPagamento = response.data.pensaoPagamento;

                    $scope.pensaoPrevidenciaria.genero = response.data.genero;
                    $scope.funcionario = $scope.pensaoPrevidenciaria.funcionario;
                    $scope.pensaoPrevidenciaria.dataNascimento = moment(response.data.dataNascimento);
                    $scope.pensaoPrevidenciaria.dataExpedicaoRg = moment(response.data.dataExpedicaoRg)
                    $scope.pensaoPrevidenciaria.dataInicioResponsavel = moment(response.data.dataInicioResponsavel);
                    $scope.pensaoPrevidenciaria.dataVencimentoResponsavel = moment(response.data.dataVencimentoResponsavel);
                    $scope.ufNaturalidade = $scope.pensaoPrevidenciaria.naturalidade.uf.id;
                    $scope.unidadeFederativa = $scope.pensaoPrevidenciaria.municipio.uf.id;
                    $scope.getMunicipios($scope.ufNaturalidade, true);
                    $scope.getMunicipios($scope.unidadeFederativa, false);
                    $scope.pensaoPrevidenciaria.naturalidade = $scope.pensaoPrevidenciaria.naturalidade.id;
                    $scope.pensaoPrevidenciaria.municipio = $scope.pensaoPrevidenciaria.municipio.id;

                    $scope.responsavelLegal = $scope.pensaoPrevidenciaria.responsavelLegal;
                    $scope.responsavelLegalSelecionado();

                    $scope.pensaoPrevidenciariaPagamento.dataPrimeiroPagamento = moment(response.data.pensaoPagamento.dataPrimeiroPagamento);
                    $scope.pensaoPrevidenciariaPagamento.dataFimBeneficio = moment(response.data.pensaoPagamento.dataFimBeneficio);
                    $scope.pensaoPrevidenciariaPagamento.dataLimiteRetroativo = moment(response.data.pensaoPagamento.dataLimiteRetroativo);

                    $scope.banco = $scope.pensaoPrevidenciariaPagamento.agencia.banco;
                    $scope.bancoSelecionado();
                    $scope.agencia = $scope.pensaoPrevidenciariaPagamento.agencia;
                    $scope.agenciaSelecionada();
                    $scope.digito = $scope.pensaoPrevidenciariaPagamento.digito;
                    $scope.operacao = $scope.pensaoPrevidenciariaPagamento.operacao;

                    // $scope.pensaoPrevidenciariaPagamento.dataInicioIsencao = moment(response.data.pensaoPagamento.dataInicioIsencao);
                    // $scope.pensaoPrevidenciariaPagamento.dataFimIsencao = moment(response.data.pensaoPagamento.dataFimIsencao);

                    if (response.data.pensaoPagamento.dataInicialIsencaoIr)
                        $scope.pensaoPrevidenciariaPagamento.dataInicialIsencaoIr = moment(response.data.pensaoPagamento.dataInicialIsencaoIr);

                    if (response.data.pensaoPagamento.dataFinalIsencaoIr)
                        $scope.pensaoPrevidenciariaPagamento.dataFinalIsencaoIr = moment(response.data.pensaoPagamento.dataFinalIsencaoIr);

                    if (response.data.pensaoPagamento.dataInicialIsencaoPrevidencia)
                        $scope.pensaoPrevidenciariaPagamento.dataInicialIsencaoPrevidencia = moment(response.data.pensaoPagamento.dataInicialIsencaoPrevidencia);

                    if (response.data.pensaoPagamento.dataFinalIsencaoPrevidencia)
                        $scope.pensaoPrevidenciariaPagamento.dataFinalIsencaoPrevidencia = moment(response.data.pensaoPagamento.dataFinalIsencaoPrevidencia);

                    $scope.pensaoPrevidenciaria.criadoEm = $scope.convertDate(response.data.criadoEm, true);
                    $scope.pensaoPrevidenciaria.alteradoEm = $scope.convertDate(response.data.alteradoEm, true);

                } else {
                    $scope.showSimpleToast("Pensão previdenciaria não encontrado na base");
                }
            });
        }

        /*
         * Recebe data e converte para valor com hora ou não
         * 
         * param data - recebe uma data tipo LocalDate
         * param comHora - recebe um valor boolean
         * 
         * */
        $scope.convertDate = function (data, comHora) {
            var valor = moment(data);
            if (comHora) {
                return $filter('date')(new Date(valor), 'dd/MM/yyyy - HH:mm');
            } else {
                return $filter('date')(new Date(valor), 'dd/MM/yyyy');
            }
        }

        /*
         * Retorna para a listagem
         * 
         * */
        $scope.goBack = function () {
            $location.path('/pensaoPrevidenciaria/gestao');
        }

        $scope.showSimpleToast = function (textContent) {
            $mdToast.show(
                $mdToast.simple()
                    .textContent(textContent)
                    .position('top right')
                    .hideDelay(3000)
            );
        };

        function salvaDescricaoAnexo() {
            if ($scope.anexo && $scope.anexo.id) {
                var anexo = {
                    id: $scope.anexo.id,
                    description: $scope.anexo.description
                }
                GenericoService.Update('/anexo', anexo, function (response) {
                });
            }
        }

        $scope.uploader = new FileUploader();
        $scope.uploader.url = configValue.baseUrl + "/api/anexo/pensaoPrevidenciaria";
        $scope.uploader.headers = $http.defaults.headers.common;
        $scope.uploader.removeAfterUpload = true;
        $scope.uploader.onSuccessItem = function (fileItem, response, status, headers) {
            $scope.anexo = response.obj;
        };

        var validateExtensions = ["application/pdf"];

        $scope.onFileChange = function (event) {
            if (validateExtensions.indexOf(event.target.files[0].type) == -1) {
                $scope.showSimpleToast('So é aceito arquivos do tipo: .pdf');
                $scope.uploader.removeFromQueue(0);
                $scope.uploader.clearQueue();
            } else {
                $scope.uploader.addToQueue(event.target.files[0]);
                $scope.uploader.uploadItem(0);

            }
        };

        $scope.save = function () {
            $rootScope.$broadcast('preloader:active');

            if ($scope.anexo) {
                salvaDescricaoAnexo();
                $scope.pensaoPrevidenciaria.anexoId = $scope.anexo.id;
            }

            $scope.pensaoPrevidenciaria.naturalidadeId = $scope.pensaoPrevidenciaria.naturalidade;
            $scope.pensaoPrevidenciaria.municipioId = $scope.pensaoPrevidenciaria.municipio;
            $scope.pensaoPrevidenciaria.pensaoPrevidenciariaPagamento = $scope.pensaoPrevidenciariaPagamento;
            $scope.pensaoPrevidenciaria.nacionalidadeId = $scope.pensaoPrevidenciaria.nacionalidade;

            if ($scope.pensaoPrevidenciariaPagamento.id) {
                $scope.pensaoPrevidenciaria.pensaoPagamento = $scope.pensaoPrevidenciariaPagamento;
                $scope.pensaoPrevidenciaria.pensaoPagamentoId = $scope.pensaoPrevidenciariaPagamento.id;
            }

            if ($scope.pensaoPrevidenciaria.id) {
                $scope.pensaoPrevidenciaria.funcionarioId = $scope.pensaoPrevidenciaria.funcionario.id;
                GenericoService.Update('/pensaoPrevidenciaria', $scope.pensaoPrevidenciaria, function (response) {
                    $rootScope.$broadcast('preloader:hide');
                    if (response.status === 201 && response.data.success) {
                        $scope.showSimpleToast(response.data.message);
                        $location.path('/pensaoPrevidenciaria/gestao');
                    } else if (response.status === 400) {
                        $scope.showSimpleToast(response.data.message);
                        $location.path('pensaoPrevidenciaria/formulario/' + $state.params.id)
                    }
                });
            } else {
                $scope.pensaoPrevidenciariaCreate.pensaoPrevidenciaria = $scope.pensaoPrevidenciaria;
                $scope.pensaoPrevidenciariaCreate.pensaoPrevidenciariaPagamento = $scope.pensaoPrevidenciariaPagamento;

                GenericoService.Create('/pensaoPrevidenciaria', $scope.pensaoPrevidenciariaCreate, function (response) {
                    $rootScope.$broadcast('preloader:hide');
                    if (response.status === 201 && response.data.success) {
                        $scope.showSimpleToast(response.data.message);
                        $location.path('/pensaoPrevidenciaria/gestao');
                    } else if (response.status === 400) {
                        $scope.showSimpleToast(response.data.message);
                        $location.path('/pensaoPrevidenciaria/formulario');
                    }
                });
            }
        }


    }

})();