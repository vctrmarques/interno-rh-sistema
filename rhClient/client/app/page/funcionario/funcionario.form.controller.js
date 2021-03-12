(function () {
    'use strict';

    angular.module('app.page')
        .controller('funcionarioFormCtrl', ['$q', '$scope', '$mdToast', '$location', '$state', '$rootScope', 'GenericoService', '$mdDialog', funcionarioFormCtrl]);

    function funcionarioFormCtrl($q, $scope, $mdToast, $location, $state, $rootScope, GenericoService, $mdDialog) {

        $scope.acessaTela = function () {
            GenericoService.VerificaPermissao('/usuario/verificaPermissao', 'ROLE_ADMIN').then(
                function (response) {
                    if (response.status === 200) {
                    }
                }, function errorCallback(response) {
                    if (response.status === 400) {
                        $location.path('page/403');
                    }
                });
        }

        $scope.valesTransporte = [];
        $scope.list = {

            "banco": [],
            "cargo": [],
            "faixaSalarial": [],
            "referenciaSalarial": [],
            "classificacaoAto": [],
            "ufTrabalho": [],
            "valeTransporte": [],
            "empresa": [],
            "filial": [],
            "lotacao": [],
            "afastamento": [],
            "motivoAfastamento": [],
            "registroEstrangeiroUf": [],
            "municipioTrabalho": [],
            "uf": [],
            "vinculo": [],
            "turno": [],
            "tipoFolha": [],
            "funcao": [],
            "municipio": [],
            "municipioRegistroEstrangeiro": [],
            "nacionalidade": [],
            "tipoEstabilidade": [],
            "estadoCivil": [
                { value: "SOLTEIRO", label: "Solteiro" },
                { value: "CASADO", label: "Casado" },
                { value: "DIVORCIADO", label: "Divorciado" },
                { value: "VIUVO", label: "Viúvo" },
                { value: "OUTROS", label: "Outros" }
            ],
            "grauInstrucao": [
                { value: "ANALFABETO", label: "Analfabeto" },
                { value: "NAO_INFORMADO", label: "Não Informado" },
                { value: "ALFABETIZADO", label: "Alfabetizado" },
                { value: "FUNDAMENTAL_INCOMPLETO", label: "Funtamental Incompleto" },
                { value: "FUNDAMENTAL_COMPLETO", label: "Funtamental Completo" },
                { value: "MEDIO_INCOMPLETO", label: "Médio Incompleto" },
                { value: "MEDIO_COMPLETO", label: "Médio Completo" },
                { value: "SUPERIOR_INCOMPLETO", label: "Superior Incompleto" },
                { value: "SUPERIOR_COMPLETO", label: "Superior Completo" },
                { value: "ESPECIALIZACAO_POS_GRADUACAO", label: "Especialização / Pós Graduação" },
                { value: "MESTRADO", label: "Mestrado" },
                { value: "DOUTORADO", label: "Doutorado" },
                { value: "POS_DOUTORADO", label: "Pós Doutorado" }
            ],
            "corPele": [
                { value: "AMARELO", label: "Amarelo(a)" },
                { value: "BRANCO", label: "Branco(a)" },
                { value: "NEGRO", label: "Negro(a)" },
                { value: "PARDO", label: "Pardo(a)" },
                { value: "INDIGENA", label: "Indígena" },
                { value: "NAO_INFORMADO", label: "Não Informado" }
            ],
            "categoriaCnh": [
                { value: "A", label: "A" },
                { value: "B", label: "B" },
                { value: "C", label: "C" },
                { value: "D", label: "D" },
                { value: "E", label: "E" },
                { value: "AB", label: "AB" },
                { value: "AC", label: "AC" },
                { value: "AD", label: "AD" },
                { value: "AE", label: "AE" }
            ],
            "tipoAdicionalTempoServico": [
                { value: "ANUENIO", label: "Anuênio" },
                { value: "TRIENIO", label: "Triênio" },
                { value: "QUINQUENIO", label: "Quinquênio" }
            ],
            "tipoConta": [
                { value: "CONTA_CORRENTE", label: "Conta Corrente" },
                { value: "CONTA_POUPANCA", label: "Conta Poupança" },
                { value: "CONTA_SALARIO", label: "Conta Salário" },
                { value: "EM_BRANCO", label: "Em Branco" }
            ]

        }

        $scope.acessaTela();
        $scope.detalhes = false;
        $scope.edit = false;

        //Início Carregamento de dropdowns  

        $scope.Lotacao = function (empresaFilialId) {
            $scope.loadedLotacao = false;
            $rootScope.$broadcast('preloader:active');
            GenericoService.GetById('/listaLotacoes/' + empresaFilialId, function (response) {
                $rootScope.$broadcast('preloader:hide');
                if (response.status === 200) {
                    $scope.list.lotacao = response.data;
                    $rootScope.$broadcast('preloader:hide');
                } else if (response.status === 500) {
                    $scope.showSimpleToast("Falha no carregamento de dados, favor contate o administrador do sistema");
                }
                $scope.loadedLotacao = true;
            });
        }

        $scope.TipoEstabilidade = function () {

            var config = {
                params: { nomeEnum: "TipoEstabilidadeEnum" }
            };

            $scope.promise = GenericoService.GetAll('/listaEnums', config).then(
                function (response) {
                    if (response.status === 200) {
                        $scope.list.tipoEstabilidade = response.data;
                    } else {
                        $scope.showSimpleToast("Não foi possível carregar os dados.")
                    }
                });
        }


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
                    GenericoService.GetAll('/banco/searchCompleteDTO', config).then(
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
                debugger
                $scope.funcionario.bancoId = $scope.banco.id;
            }
        };

        $scope.querySearchAgencia = function (query) {
            var deferred = $q.defer();
            var config = { params: { search: query, bancoId: $scope.funcionario.bancoId } }
            if (query) {
                if (query.length > 2) {
                    $rootScope.$broadcast('preloader:active');
                    GenericoService.GetAll('/agencia/search', config).then(
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
                $scope.funcionario.agenciaBancariaId = $scope.agencia.id;
            }
        };

        $scope.Cargo = function () {
            $scope.loadedCargo = false;
            $rootScope.$broadcast('preloader:active');
            GenericoService.GetAllDropdown('cargos/list/basico', function (response) {
                if (response.status === 200) {
                    $scope.list.cargo = response.data;
                    $scope.setGrupoCargoId();
                    $rootScope.$broadcast('preloader:hide');
                } else if (response.status === 500) {
                    $scope.showSimpleToast("Falha no carregamento de dados, favor contate o administrador do sistema");
                }
                $scope.loadedCargo = true;
            });
        }

        $scope.setGrupoCargoId = function (cargo) {
            $scope.loadedFaixaSalarialGrupoCargo = false;
            delete $scope.list.referenciaSalarialCargo;
            if ($scope.funcionario.cargoId)
                for (var i = 0; i < $scope.list.cargo.length; i++) {
                    if ($scope.funcionario.cargoId === $scope.list.cargo[i].id &&
                        $scope.list.cargo[i].grupoSalarial) {
                        $scope.grupoCargoId = $scope.list.cargo[i].grupoSalarial.id;
                        $scope.getFaixaSalarialCargoByGrupoId();
                        break;
                    }
                }
        }

        $scope.setReferenciasSalariaisCargo = function () {
            if ($scope.funcionario.faixaSalarialCargoId)
                for (var i = 0; i < $scope.list.faixaSalarialCargo.length; i++) {
                    if ($scope.funcionario.faixaSalarialCargoId === $scope.list.faixaSalarialCargo[i].id) {
                        $scope.list.referenciaSalarialCargo = $scope.list.faixaSalarialCargo[i].niveisSalariais;
                        break;
                    }
                }
        }

        $scope.getFaixaSalarialCargoByGrupoId = function () {
            if ($scope.grupoCargoId) {
                $scope.loadedFaixaSalarialGrupoCargo = false;
                delete $scope.list.faixaSalarialCargo;
                $rootScope.$broadcast('preloader:active');
                GenericoService.GetAllDropdown('faixasSalariais/porGrupo/' + $scope.grupoCargoId, function (response) {
                    if (response.status === 200) {
                        if (response.data.length > 0)
                            $scope.loadedFaixaSalarialGrupoCargo = true;
                        $scope.list.faixaSalarialCargo = response.data;
                        $scope.setReferenciasSalariaisCargo();
                        $rootScope.$broadcast('preloader:hide');
                    } else if (response.status === 500) {
                        $scope.showSimpleToast("Falha no carregamento de dados, favor contate o administrador do sistema");
                    }
                });
            }
        }


        $scope.Funcao = function () {
            $scope.loadedFuncao = false;
            $rootScope.$broadcast('preloader:active');
            GenericoService.GetAllDropdown('listaFuncoes', function (response) {
                if (response.status === 200) {
                    $scope.list.funcao = response.data;
                    $scope.setGrupoFuncaoId();
                    $rootScope.$broadcast('preloader:hide');
                } else if (response.status === 500) {
                    $scope.showSimpleToast("Falha no carregamento de dados, favor contate o administrador do sistema");
                }
                $scope.loadedFuncao = true;
            });
        }

        $scope.setGrupoFuncaoId = function () {
            $scope.loadedFaixaSalarialGrupoFuncao = false;
            delete $scope.list.referenciaSalarialFuncao;
            if ($scope.funcionario.funcaoId)
                for (var i = 0; i < $scope.list.funcao.length; i++) {
                    if ($scope.funcionario.funcaoId === $scope.list.funcao[i].id) {
                        $scope.grupoFuncaoId = $scope.list.funcao[i].grupoSalarialId;
                        $scope.getFaixaSalarialFuncaoByGrupoId();
                        break;
                    }
                }
        }

        $scope.setReferenciasSalariaisFuncao = function () {
            if ($scope.funcionario.faixaSalarialFuncaoId)
                for (var i = 0; i < $scope.list.faixaSalarialFuncao.length; i++) {
                    if ($scope.funcionario.faixaSalarialFuncaoId === $scope.list.faixaSalarialFuncao[i].id) {
                        $scope.list.referenciaSalarialFuncao = $scope.list.faixaSalarialFuncao[i].niveisSalariais;
                        break;
                    }
                }
        }

        $scope.getFaixaSalarialFuncaoByGrupoId = function () {
            if ($scope.funcionario.funcao)
                $scope.funcionario.funcaoId = $scope.funcionario.funcao.id;
            if ($scope.grupoFuncaoId) {
                $scope.loadedFaixaSalarialFuncao = false;
                delete $scope.list.referenciaSalarialFuncao;
                $rootScope.$broadcast('preloader:active');
                GenericoService.GetAllDropdown('faixasSalariais/porGrupo/' + $scope.grupoFuncaoId, function (response) {
                    if (response.status === 200) {
                        if (response.data.length > 0)
                            $scope.loadedFaixaSalarialFuncao = true;
                        $scope.list.faixaSalarialFuncao = response.data;
                        $scope.setReferenciasSalariaisFuncao();
                        $rootScope.$broadcast('preloader:hide');
                    } else if (response.status === 500) {
                        $scope.showSimpleToast("Falha no carregamento de dados, favor contate o administrador do sistema");
                    }
                });
            }
        }

        $scope.ClassificacaoAto = function () {
            $rootScope.$broadcast('preloader:active');
            GenericoService.GetAllDropdown('listaClassificacoesAtos', function (response) {
                if (response.status === 200) {
                    $scope.list.classificacaoAto = response.data;
                    $rootScope.$broadcast('preloader:hide');
                } else if (response.status === 500) {
                    $scope.showSimpleToast("Falha no carregamento de dados, favor contate o administrador do sistema");
                }
            });
        }


        $scope.TipoFolha = function () {
            $rootScope.$broadcast('preloader:active');
            GenericoService.GetAllDropdown('listaTiposFolhas', function (response) {
                if (response.status === 200) {
                    $scope.list.tipoFolha = response.data;
                    $rootScope.$broadcast('preloader:hide');
                } else if (response.status === 500) {
                    $scope.showSimpleToast("Falha no carregamento de dados, favor contate o administrador do sistema");
                }
            });
        }


        $scope.Turno = function () {
            $scope.loadedTurno = false;
            $rootScope.$broadcast('preloader:active');
            GenericoService.GetAllDropdown('listaTurnos', function (response) {
                if (response.status === 200) {
                    $scope.list.turno = response.data;
                    $rootScope.$broadcast('preloader:hide');
                } else if (response.status === 500) {
                    $scope.showSimpleToast("Falha no carregamento de dados, favor contate o administrador do sistema");
                }
                $scope.loadedTurno = true;
            });
        }


        $scope.Afastamento = function () {
            $rootScope.$broadcast('preloader:active');
            GenericoService.GetAllDropdown('listaSituacoesFuncionais', function (response) {
                if (response.status === 200) {
                    $scope.list.afastamento = response.data;
                    $rootScope.$broadcast('preloader:hide');
                } else if (response.status === 500) {
                    $scope.showSimpleToast("Falha no carregamento de dados, favor contate o administrador do sistema");
                }
            });
        }


        $scope.MotivoAfastamento = function () {
            $rootScope.$broadcast('preloader:active');
            GenericoService.GetAllDropdown('listaMotivosAfastamentos', function (response) {
                if (response.status === 200) {
                    $scope.list.motivoAfastamento = response.data;
                    $rootScope.$broadcast('preloader:hide');
                } else if (response.status === 500) {
                    $scope.showSimpleToast("Falha no carregamento de dados, favor contate o administrador do sistema");
                }
            });
        }


        $scope.CentroCusto = function () {
            $scope.loadedCentroCusto = false;
            $rootScope.$broadcast('preloader:active');
            GenericoService.GetAllDropdown('centrosCustos', function (response) {
                if (response.status === 200) {
                    $scope.list.centroCusto = response.data.content;
                    $rootScope.$broadcast('preloader:hide');
                } else if (response.status === 500) {
                    $scope.showSimpleToast("Falha no carregamento de dados, favor contate o administrador do sistema");
                }
                $scope.loadedCentroCusto = true;
            });
        }


        $scope.Vinculo = function () {
            $scope.loadedVinculo = false;
            $rootScope.$broadcast('preloader:active');
            GenericoService.GetAllDropdown('listaVinculos', function (response) {
                if (response.status === 200) {
                    $scope.list.vinculo = response.data;
                    $rootScope.$broadcast('preloader:hide');
                } else if (response.status === 500) {
                    $scope.showSimpleToast("Falha no carregamento de dados, favor contate o administrador do sistema");
                }
                $scope.loadedVinculo = true;
            });
        }


        $scope.Empresa = function () {
            $scope.loadedEmpresas = false;
            $rootScope.$broadcast('preloader:active');
            GenericoService.GetAllDropdown('listaEmpresasMatrizes', function (response) {
                if (response.status === 200) {
                    $scope.list.empresa = response.data;
                    $rootScope.$broadcast('preloader:hide');
                } else if (response.status === 500) {
                    $scope.showSimpleToast("Falha no carregamento de dados, favor contate o administrador do sistema");
                }
                $scope.loadedEmpresas = true;
            });
        }

        $scope.Filial = function () {
            $scope.loadedFilial = false;
            $rootScope.$broadcast('preloader:active');
            GenericoService.GetAllDropdown('listaEmpresasNaoMatrizes', function (response) {
                if (response.status === 200) {
                    $scope.list.filial = response.data;
                    $rootScope.$broadcast('preloader:hide');
                } else if (response.status === 500) {
                    $scope.showSimpleToast("Falha no carregamento de dados, favor contate o administrador do sistema");
                }
                $scope.loadedFilial = true;
            });
        }

        $scope.Filial = function () {
            $scope.loadedFilial = false;
            $rootScope.$broadcast('preloader:active');
            GenericoService.GetAllDropdown('listaEmpresasNaoMatrizes', function (response) {
                if (response.status === 200) {
                    $scope.list.filial = response.data;
                    $rootScope.$broadcast('preloader:hide');
                } else if (response.status === 500) {
                    $scope.showSimpleToast("Falha no carregamento de dados, favor contate o administrador do sistema");
                }
                $scope.loadedFilial = true;
            });
        }

        $scope.Nacionalidade = function () {
            $rootScope.$broadcast('preloader:active');
            GenericoService.GetAllDropdown('listaNacionalidades', function (response) {
                if (response.status === 200) {
                    $scope.list.nacionalidade = response.data;
                    $rootScope.$broadcast('preloader:hide');
                } else if (response.status === 500) {
                    $scope.showSimpleToast("Falha no carregamento de dados, favor contate o administrador do sistema");
                }
            });
        }

        $scope.getMunicipiosRegistrosEstrangeirosByUf = function () {
            if ($scope.funcionario.registroEstrangeiroUfId) {
                $scope.loadedMunicipioRegistroEstrangeiro = false;
                $rootScope.$broadcast('preloader:active');
                var config = { params: { id: $scope.funcionario.registroEstrangeiroUfId } };
                GenericoService.GetAll('/listaMunicipios', config).then(
                    function (response) {
                        if (response.data && response.data.length > 0) {
                            $scope.list.municipioRegistroEstrangeiro = response.data;
                            $rootScope.$broadcast('preloader:hide');
                        } else {
                            $scope.showSimpleToast("Não existem municípios cadastrados para este estado");
                        }
                        $scope.loadedMunicipioRegistroEstrangeiro = true;
                    });
            }
        }

        $scope.getMunicipiosTrabalhoByUf = function () {
            if ($scope.funcionario.ufTrabalhoId) {
                $scope.loadedMunicipioTrabalho = false;
                $rootScope.$broadcast('preloader:active');
                var config = { params: { id: $scope.funcionario.ufTrabalhoId } };
                GenericoService.GetAll('/listaMunicipios', config).then(
                    function (response) {
                        if (response.data && response.data.length > 0) {
                            $scope.list.municipioTrabalho = response.data;
                            $rootScope.$broadcast('preloader:hide');
                        } else {
                            $scope.showSimpleToast("Não existem municípios cadastrados para este estado");
                        }
                        $scope.loadedMunicipioTrabalho = true;
                    });
            }
        }

        $scope.Uf = function () {
            $scope.loadedUfs = false;
            $rootScope.$broadcast('preloader:active');
            GenericoService.GetAllDropdown('listaUfs', function (response) {
                if (response.status === 200) {
                    $scope.list.uf = response.data;
                    $scope.list.registroEstrangeiroUf = response.data;
                    $scope.list.ufTrabalho = response.data;
                    $rootScope.$broadcast('preloader:hide');
                    $scope.loadedUfs = true;
                } else if (response.status === 500) {
                    $scope.showSimpleToast("Falha no carregamento de dados, favor contate o administrador do sistema");
                }
            });
        }

        $scope.getMunicipiosByUf = function () {
            $rootScope.$broadcast('preloader:active');
            var config = { params: { id: $scope.funcionario.ufId } };
            $scope.loadedMunicipios = false;
            GenericoService.GetAll('/listaMunicipios', config).then(
                function (response) {
                    if (response.data && response.data.length > 0) {
                        $scope.list.municipio = response.data;
                        $rootScope.$broadcast('preloader:hide');
                    } else {
                        $scope.showSimpleToast("Não existem municípios cadastrados para este estado");
                    }
                    $scope.loadedMunicipios = true;
                });
        }

        $scope.ValeTransporte = function () {
            $rootScope.$broadcast('preloader:active');
            GenericoService.GetAllDropdown('listaValesTransportes', function (response) {
                if (response.status === 200) {
                    $scope.list.valeTransporte = response.data;
                    $rootScope.$broadcast('preloader:hide');
                } else if (response.status === 500) {
                    $scope.showSimpleToast("Falha no carregamento de dados, favor contate o administrador do sistema");
                }
            });
        }

        //Fim Carregamento de Dropdowns

        if ($state.params && $state.params.id) {
            $rootScope.$broadcast('preloader:active');
            GenericoService.GetById('/funcionario/' + $state.params.id, function (response) {
                $rootScope.$broadcast('preloader:hide');
                if (response.status === 200) {
                    $scope.funcionario = response.data;
                    $scope.getMunicipiosRegistrosEstrangeirosByUf();
                    $scope.getMunicipiosByUf();
                    $scope.getMunicipiosTrabalhoByUf();

                    if ($scope.funcionario.cargo) {
                        $scope.funcionario.cargoId = $scope.funcionario.cargo.id;
                        $scope.getFaixaSalarialCargoByGrupoId();
                    }

                    if ($scope.funcionario.funcao)
                        $scope.getFaixaSalarialFuncaoByGrupoId();

                    $scope.funcionario.funcaoId = $scope.funcionario.funcao.id;
                    debugger
                    if($scope.funcionario.agenciaBancaria){
                        $scope.banco = $scope.funcionario.banco;
                        $scope.bancoSelecionado();
                        $scope.agencia = $scope.funcionario.agenciaBancaria;
                        $scope.agenciaSelecionada();
                    } else {
                        $scope.banco = $scope.funcionario.banco;
                        $scope.bancoSelecionado();
                    }

                    if (!$scope.funcionario.valesTransporte) {
                        $scope.funcionario.valesTransporte = [];
                    } else {
                        $scope.valesTransporte = $scope.funcionario.valesTransporte;
                    }

                    if ($scope.funcionario.filial)
                        $scope.Lotacao($scope.funcionario.filial.id);

                    $scope.funcionario.dataNascimento = moment(response.data.dataNascimento);
                    $scope.funcionario.dataExpedicaoRg = moment(response.data.dataExpedicaoRg);
                    $scope.funcionario.dataAdmissao = moment(response.data.dataAdmissao);
                    $scope.funcionario.empresaId = response.data.empresa.id;

                    if (response.data.centroCusto)
                        $scope.funcionario.centroCustoId = response.data.centroCusto.id;

                    if (response.data.dataUltimoExame)
                        $scope.funcionario.dataUltimoExame = moment(response.data.dataUltimoExame);


                    if (response.data.dataEmissaoPisPasep)
                        $scope.funcionario.dataEmissaoPisPasep = moment(response.data.dataEmissaoPisPasep);

                    if (response.data.dataValidadeCnh)
                        $scope.funcionario.dataValidadeCnh = moment(response.data.dataValidadeCnh);

                    if (response.data.dataNomeacao)
                        $scope.funcionario.dataNomeacao = moment(response.data.dataNomeacao);

                    if (response.data.dataPublicacaoDiarioOficial)
                        $scope.funcionario.dataPublicacaoDiarioOficial = moment(response.data.dataPublicacaoDiarioOficial);

                    if (response.data.dataOpcaoFgts)
                        $scope.funcionario.dataOpcaoFgts = moment(response.data.dataOpcaoFgts);

                    if (response.data.dataExercicioAdmissaoAts)
                        $scope.funcionario.dataExercicioAdmissaoAts = moment(response.data.dataExercicioAdmissaoAts);

                    if (response.data.dataInicioSituacaoFuncional)
                        $scope.funcionario.dataInicioSituacaoFuncional = moment(response.data.dataInicioSituacaoFuncional);

                    if (response.data.dataFimSituacaoFuncional)
                        $scope.funcionario.dataFimSituacaoFuncional = moment(response.data.dataFimSituacaoFuncional);

                    if (response.data.inicioContratoTemporario)
                        $scope.funcionario.inicioContratoTemporario = moment(response.data.inicioContratoTemporario);

                    if (response.data.fimContratoTemporario)
                        $scope.funcionario.fimContratoTemporario = moment(response.data.fimContratoTemporario);

                    if ($scope.funcionario.filial)
                        $scope.funcionario.filialId = $scope.funcionario.filial.id;

                    $scope.TipoEstabilidade();
                    $scope.ClassificacaoAto();
                    $scope.TipoFolha();
                    $scope.Turno();
                    $scope.Cargo();
                    $scope.Afastamento();
                    $scope.MotivoAfastamento();
                    $scope.CentroCusto();
                    $scope.Vinculo();
                    $scope.Funcao();
                    $scope.Nacionalidade();
                    $scope.Uf();
                    $scope.Empresa();
                    $scope.Filial();
                    $scope.ValeTransporte();

                } else {
                    $scope.showSimpleToast("Funcionário não encontrado na base");
                }
            });
        } else {
            $scope.funcionario = {
                "valesTransporte": []
            };
            $scope.TipoEstabilidade();
            $scope.Cargo();
            $scope.ClassificacaoAto();
            $scope.TipoFolha();
            $scope.Turno();
            $scope.Afastamento();
            $scope.MotivoAfastamento();
            $scope.CentroCusto();
            $scope.Vinculo();
            $scope.Funcao();
            $scope.Nacionalidade();
            $scope.Uf();
            $scope.Empresa();
            $scope.Filial();
            $scope.ValeTransporte();
        }

        if ($state.params && $state.params.detalhes) {
            $scope.detalhes = true;
        }

        $scope.goBack = function () {
            $location.path('/funcionario/gestao');
        }

        $scope.verificaIdade = function () {
            var nascimento = $scope.funcionario.dataNascimento;
            var dataAtual = moment();

            var varDiffAnos = dataAtual.diff(nascimento, 'years');

            if (varDiffAnos <= 14) {
                $scope.showSimpleToast("Não é possível cadastrar um funcionário com 14 anos ou menos");

                $scope.funcionario.dataNascimento = null;
            }

        }

        $scope.querySearchClassificacaoAto = function (query) {
            var deferred = $q.defer();
            var config = { params: { search: query } }
            GenericoService.GetAll('/classificacoesAtos/searchComplete', config).then(
                function (response) {
                    if (response.data && response.data.length > 0) {
                        // results = response.data;
                        $scope.itens = response.data;
                        deferred.resolve(response.data);
                    }
                });
            return deferred.promise;
        }

        $scope.setClassificacaoAtoId = function () {
            if ($scope.funcionario.classificacaoAto) {
                $scope.funcionario.classificacaoAtoId = $scope.funcionario.classificacaoAto.id;
            }
        }

        $scope.vale = {};

        $scope.addItem = function () {
            $scope.obj.valeTransporte.valeTransporteId = $scope.obj.valeTransporte.id;
            $scope.obj.valeTransporte.id = null;
            $scope.vale = $scope.obj.valeTransporte;
            $scope.valesTransporte.push($scope.vale);

            if (!$scope.funcionario.id && $scope.funcionario.valesTransporte.length <= 0) {
                $scope.funcionario.valesTransporte.push($scope.vale);
            } else if ($scope.funcionario.id && $scope.funcionario.valesTransporte.length <= 0) {
                $scope.funcionario.valesTransporte.push($scope.vale);
            }

        }

        $scope.removeItem = function (index) {
            $scope.valesTransporte.splice(index, 1);
            $scope.funcionario.valesTransporte.splice(index, 1);
        }

        $scope.validaEstabilidade = function () {
            var anoEstavel = moment().subtract(2, 'years');

            var range = moment().range($scope.funcionario.dataAdmissao, anoEstavel);

            if ($scope.funcionario.dataAdmissao > moment()) {
                $scope.funcionario.probatorio = true;
            } else {
                if (range.contains($scope.funcionario.dataAdmissao)) {
                    $scope.funcionario.probatorio = false;
                } else {
                    $scope.funcionario.probatorio = true;
                }
            }

        }

        $scope.save = function () {
            $rootScope.$broadcast('preloader:active');

            if (!$scope.list.lotacao || $scope.list.lotacao.length === 0) {
                $scope.funcionario.lotacaoId = null;
            }

            $scope.funcionario.agencia  = $scope.searchAgencia;
            $scope.agencia = $scope.searchAgencia;

            if ($scope.funcionario.id) {
                GenericoService.Update('/funcionario', $scope.funcionario, function (response) {
                    $rootScope.$broadcast('preloader:hide');
                    if (response.status === 201 && response.data.success) {
                        $scope.showSimpleToast(response.data.message);
                        $location.path('/funcionario/gestao');
                    } else if (response.status === 400) {
                        $scope.showSimpleToast(response.data.message);
                        $location.path('funcionario/formulario/' + $state.params.id)
                    }
                });
            } else {
                GenericoService.Create('/funcionario', $scope.funcionario, function (response) {
                    $rootScope.$broadcast('preloader:hide');
                    if (response.status === 201 && response.data.success) {
                        $scope.showSimpleToast(response.data.message);
                        $location.path('/funcionario/gestao');
                    } else if (response.status === 400) {
                        $scope.showSimpleToast(response.data.message);
                        $location.path('/funcionario/formulario');
                    }
                });
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

        $scope.showDialog = function () {

            $mdDialog.show({
                scope: $scope,
                preserveScope: true,
                controller: ['$scope', '$q', function ($scope, $q) {
                    $scope.cancel = function () {
                        $mdDialog.cancel();
                    };
                }],
                templateUrl: 'app/page/funcionario/dialog1.tmpl.html',
                parent: angular.element(document.body),
                clickOutsideToClose: true
            });

        }
        $scope.cleanFieldsEstrangeiro = function () {
            $scope.funcionario.naturalizado = false;
            $scope.funcionario.casamentoBrasileiro = false;
            $scope.funcionario.filhoBrasileiro = false;
            $scope.funcionario.registroEstrangeiroUfId = null;
            $scope.funcionario.municipioRegistroEstrangeiroId = null;
        }

        $scope.validaFuncao = function () {
            for (var i = 0; i < $scope.list.funcao.length; i++) {

                if ($scope.list.funcao[i].id === $scope.funcionario.funcaoId) {
                    $scope.funcionario.funcao = $scope.list.funcao[i];

                    break;
                }
            }
        }

        $scope.querySearch = function (query) {
            if (query && query.length > 2) {
                var config = {
                    params: {
                        search: query
                    }
                }
                var deferred = $q.defer();
                GenericoService.GetAll('/municipio/search', config).then(
                    function (response) {
                        if (response.data && response.data.length > 0)
                            deferred.resolve(response.data);
                        else
                            $scope.showSimpleToast("Nenhum registro encontrado");
                    });
                return deferred.promise;
            }
        }

        $scope.querySearchNacionalidade = function (query) {
            if (query && query.length > 2) {
                var config = {
                    params: {
                        search: query
                    }
                }
                var deferred = $q.defer();
                GenericoService.GetAll('/nacionalidade/search', config).then(
                    function (response) {
                        if (response.data && response.data.length > 0)
                            deferred.resolve(response.data);
                        else
                            $scope.showSimpleToast("Nenhum registro encontrado");
                    });
                return deferred.promise;
            }
        }

    }

})(); 