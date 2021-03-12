(function () {
    'use strict';

    angular.module('app.page')
        .controller('prestadorServicoFormCtrl', ['$scope', '$q', '$mdToast', '$location', '$state', '$rootScope', 'GenericoService', 'RestService', prestadorServicoFormCtrl]);

    function prestadorServicoFormCtrl($scope, $q, $mdToast, $location, $state, $rootScope, GenericoService, RestService) {

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

        $scope.list = {
            "nacionalidade": [],
            "ufEndereco": [],
            "ufCtps": [],
            "ufCnh": [],
            "empresaFilial": [],
            "municipio": [],
            "categoriaProfissional": [],
            "verba": [],
            "convenio": []
        }

        $scope.acessaTela();
        $scope.detalhes = false;

        $scope.getMunicipios = function (id) {
            $scope.getMunicipiosByUf(id);
        }

        $scope.getMunicipiosByUf = function (id) {
            GenericoService.GetAllDropdownById(id, function (response) {
                if (response.status === 200) {
                    $scope.list.municipio = response.data;

                } else if (response.status === 500) {
                    $scope.showSimpleToast("Não existem municípios cadastrados para este estado");
                }
            });
        }

        if ($state.params && $state.params.id) {
            $rootScope.$broadcast('preloader:active');
            GenericoService.GetById('/prestadorServico/' + $state.params.id, function (response) {
                $rootScope.$broadcast('preloader:hide');
                if (response.status === 200) {
                    $scope.prestadorServico = response.data;
                    console.log(response.data);

                    $scope.prestadorServico.dataNascimento = moment(response.data.dataNascimento);
                    $scope.prestadorServico.dataEmissaoRg = moment(response.data.dataEmissaoRg);

                    if ($scope.prestadorServico.dataInicial) {
                        $scope.prestadorServico.dataInicial = moment(response.data.dataInicial);
                    }
                    if ($scope.prestadorServico.dataFinal) {
                        $scope.prestadorServico.dataFinal = moment(response.data.dataFinal);
                    }
                    if ($scope.prestadorServico.dataEmpenho) {
                        $scope.prestadorServico.dataEmpenho = moment(response.data.dataEmpenho);
                    }
                    if ($scope.prestadorServico.dataEmissaoNf) {
                        $scope.prestadorServico.dataEmissaoNf = moment(response.data.dataEmissaoNf);
                    }
                    if ($scope.prestadorServico.dataEmissaoIdentCivilNac) {
                        $scope.prestadorServico.dataEmissaoIdentCivilNac = moment(response.data.dataEmissaoIdentCivilNac);
                    }
                    if ($scope.prestadorServico.dataEmissaoRegNacEstrangeiro) {
                        $scope.prestadorServico.dataEmissaoRegNacEstrangeiro = moment(response.data.dataEmissaoRegNacEstrangeiro);
                    }
                    if ($scope.prestadorServico.dataEmissaoRegProfissional) {
                        $scope.prestadorServico.dataEmissaoRegProfissional = moment(response.data.dataEmissaoRegProfissional);
                    }
                    if ($scope.prestadorServico.dataValidadeRegProfissional) {
                        $scope.prestadorServico.dataValidadeRegProfissional = moment(response.data.dataValidadeRegProfissional);
                    }
                    if ($scope.prestadorServico.dataValidadeCnh) {
                        $scope.prestadorServico.dataValidadeCnh = moment(response.data.dataValidadeCnh);
                    }
                    if ($scope.prestadorServico.dataPrimeiraCnh) {
                        $scope.prestadorServico.dataPrimeiraCnh = moment(response.data.dataPrimeiraCnh);
                    }
                    if ($scope.prestadorServico.dataEmissaoCnh) {
                        $scope.prestadorServico.dataEmissaoCnh = moment(response.data.dataEmissaoCnh);
                    }
                    if (response.data.empresaFilial)
                        $scope.prestadorServico.empresaFilialId = response.data.empresaFilial.id;
                    if (response.data.verba)
                        $scope.prestadorServico.verbaId = response.data.verba.id;
                    if (response.data.convenio)
                        $scope.prestadorServico.convenioId = response.data.convenio.id;
                    if (response.data.ufCnh)
                        $scope.prestadorServico.ufIdCnh = response.data.ufCnh.id;

                    $scope.prestadorServico.nacionalidadeId = response.data.nacionalidade.id;

                    if ($scope.prestadorServico.ufEndereco) {
                        $scope.prestadorServico.ufIdEndereco = $scope.prestadorServico.ufEndereco.id;
                        $scope.getMunicipios($scope.prestadorServico.ufEndereco.id);
                    }
                    if ($scope.prestadorServico.municipio) {
                        $scope.prestadorServico.municipioId = $scope.prestadorServico.municipio.id;
                    }

                    $scope.prestadorServico.categoriaProfissionalId = response.data.categoriaProfissional.id;
                    $scope.prestadorServico.ufIdCtps = response.data.ufCtps.id;
                    //$scope.prestadorServico.verbaId = response.data.verba.id;
                    //$scope.prestadorServico.convenioId = response.data.convenio.id;

                } else {
                    $scope.showSimpleToast("Prestador de Serviço não encontrado na base");
                }
            });
        }

        $scope.estadosCivis = [
            { tipo: "Solteiro" },
            { tipo: "Casado" },
            { tipo: "Divorciado" },
            { tipo: "Separado" },
            { tipo: "Viúvo" }
        ]

        $scope.sexos = [
            { nome: "Feminino" },
            { nome: "Masculino" }
        ]

        $scope.cores = [
            { tipo: "Amarela" },
            { tipo: "Branca" },
            { tipo: "Indígena" },
            { tipo: "Negra" },
            { tipo: "Parda" },
            { tipo: "Não Informada" }
        ]

        $scope.locacoes = [
            { nome: "Interno" },
            { nome: "Externo" }
        ]

        $scope.unidadesPagamentos = [
            { tipo: "Por Hora" },
            { tipo: "Por Dia" },
            { tipo: "Por Semana" },
            { tipo: "Por Quinzena" },
            { tipo: "Por Mês" },
            { tipo: "Por Tarefa" },
            { tipo: "Não Aplicável" }
        ]

        $scope.naturezasAtividades = [
            { nome: "Trabalho Rural" },
            { nome: "Trabalho Urbano" }
        ]

        $scope.tempoContribuicoes = [
            { tempo: "15 Anos" },
            { tempo: "20 Anos" },
            { tempo: "25 Anos" }
        ]

        $scope.modosPagamentos = [
            { modo: "Parcela Única" },
            { modo: "Parcelado" }
        ]

        $scope.categoriasCnhs = [
            { categoria: "A" },
            { categoria: "B" },
            { categoria: "C" },
            { categoria: "D" },
            { categoria: "E" },
            { categoria: "AB" },
            { categoria: "AC" },
            { categoria: "AD" },
            { categoria: "AE" }
        ]

        $scope.tiposDependentes = [
            { tipo: "Companheiro(a)" },
            { tipo: "Filho(a)" },
            { tipo: "Irmão(ã)" },
            { tipo: "Pai(Mãe)" },
            { tipo: "Avô(ó)" },
            { tipo: "Bisavô(ó)" },
            { tipo: "Alimentado" }
        ]



        if ($state.params && $state.params.detalhes) {
            $scope.detalhes = true;
        }

        $scope.goBack = function () {
            $location.path('/prestadorServico/gestao');
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

        $scope.EmpresaFilial = function () {
            $rootScope.$broadcast('preloader:active');
            GenericoService.GetAllDropdown('listaEmpresasFiliais', function (response) {
                if (response.status === 200) {
                    $scope.list.empresaFilial = response.data;
                    $rootScope.$broadcast('preloader:hide');
                } else if (response.status === 500) {
                    $scope.showSimpleToast("Falha no carregamento de dados, favor contate o administrador do sistema");
                }
            });
        }

        $scope.CategoriaProfissional = function () {
            $rootScope.$broadcast('preloader:active');
            GenericoService.GetAllDropdown('listaCategoriasProfissionais', function (response) {
                if (response.status === 200) {
                    $scope.list.categoriaProfissional = response.data;
                    $rootScope.$broadcast('preloader:hide');
                } else if (response.status === 500) {
                    $scope.showSimpleToast("Falha no carregamento de dados, favor contate o administrador do sistema");
                }
            });
        }

        $scope.UfEndereco = function () {
            $rootScope.$broadcast('preloader:active');
            GenericoService.GetAllDropdown('listaUfs', function (response) {
                if (response.status === 200) {
                    $scope.list.ufEndereco = response.data;
                    $rootScope.$broadcast('preloader:hide');
                } else if (response.status === 500) {
                    $scope.showSimpleToast("Falha no carregamento de dados, favor contate o administrador do sistema");
                }
            });
        }

        $scope.UfCtps = function () {
            $rootScope.$broadcast('preloader:active');
            GenericoService.GetAllDropdown('listaUfs', function (response) {
                if (response.status === 200) {
                    $scope.list.ufCtps = response.data;
                    $rootScope.$broadcast('preloader:hide');
                } else if (response.status === 500) {
                    $scope.showSimpleToast("Falha no carregamento de dados, favor contate o administrador do sistema");
                }
            });
        }

        $scope.UfCnh = function () {
            $rootScope.$broadcast('preloader:active');
            GenericoService.GetAllDropdown('listaUfs', function (response) {
                if (response.status === 200) {
                    $scope.list.ufCnh = response.data;
                    $rootScope.$broadcast('preloader:hide');
                } else if (response.status === 500) {
                    $scope.showSimpleToast("Falha no carregamento de dados, favor contate o administrador do sistema");
                }
            });
        }

        $scope.Municipio = function () {
            $rootScope.$broadcast('preloader:active');
            GenericoService.GetAllDropdown('listaMunicipios', function (response) {
                if (response.status === 200) {
                    $scope.list.municipio = response.data;
                    $rootScope.$broadcast('preloader:hide');
                } else if (response.status === 500) {
                    $scope.showSimpleToast("Falha no carregamento de dados, favor contate o administrador do sistema");
                }
            });
        }

        $scope.querySearch = function (query) {
            var deferred = $q.defer();
            var config = { params: { nome: query } };
            RestService.Get('/cbo/search', config)
                .then(function successCallback(response) {
                    $rootScope.$broadcast('preloader:hide');
                    if (response.data && response.data.length > 0)
                        deferred.resolve(response.data);
                    else
                        deferred.resolve([]);
                }, function errorCallback(response) {
                    $rootScope.$broadcast('preloader:hide');
                    if (response.status === 400)
                        $scope.showSimpleToast(response.data.message);
                });
            return deferred.promise;
        }

        $scope.Verba = function () {
            $rootScope.$broadcast('preloader:active');
            GenericoService.GetAllDropdown('listaVerbas', function (response) {
                if (response.status === 200) {
                    $scope.list.verba = response.data;
                    $rootScope.$broadcast('preloader:hide');
                } else if (response.status === 500) {
                    $scope.showSimpleToast("Falha no carregamento de dados, favor contate o administrador do sistema");
                }
            });
        }

        $scope.Convenio = function () {
            $rootScope.$broadcast('preloader:active');
            GenericoService.GetAllDropdown('listaConvenios', function (response) {
                if (response.status === 200) {
                    $scope.list.convenio = response.data;
                    $rootScope.$broadcast('preloader:hide');
                } else if (response.status === 500) {
                    $scope.showSimpleToast("Falha no carregamento de dados, favor contate o administrador do sistema");
                }
            });
        }

        $scope.Nacionalidade();
        $scope.UfEndereco();
        $scope.UfCtps();
        $scope.UfCnh();
        $scope.EmpresaFilial();
        $scope.CategoriaProfissional();
        $scope.Verba();
        $scope.Convenio();

        if ($scope.list.ufEndereco.length > 0) {
            $scope.Municipio();
        }


        $scope.save = function () {
            $rootScope.$broadcast('preloader:active');

            $scope.prestadorServico.cboId = $scope.prestadorServico.cbo.id;

            if ($scope.prestadorServico.id) {
                GenericoService.Update('/prestadorServico', $scope.prestadorServico, function (response) {
                    $rootScope.$broadcast('preloader:hide');
                    if (response.status === 201 && response.data.success) {
                        $scope.showSimpleToast(response.data.message);
                        $location.path('/prestadorServico/gestao');
                    } else if (response.status === 400) {
                        $scope.showSimpleToast(response.data.message);
                        $location.path('prestadorServico/formulario/' + $state.params.id)
                    }
                });
            } else {

                GenericoService.Create('/prestadorServico', $scope.prestadorServico, function (response) {
                    $rootScope.$broadcast('preloader:hide');
                    if (response.status === 201 && response.data.success) {
                        $scope.showSimpleToast(response.data.message);
                        $location.path('/prestadorServico/gestao');
                    } else if (response.status === 400) {
                        $scope.showSimpleToast(response.data.message);
                        $location.path('/prestadorServico/formulario');
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

    }

})(); 