(function () {
    'use strict';

    angular.module('app.page')
        .controller('empresaFilialFormCtrl', ['$q', '$scope', '$mdToast', '$location', '$state', '$rootScope', 'GenericoService', 'EnumService', '$timeout', '$filter', empresaFilialFormCtrl]);

    function empresaFilialFormCtrl($q, $scope, $mdToast, $location, $state, $rootScope, GenericoService, EnumService, $timeout, $filter) {

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

        $scope.reloadTipoFilialMatriz = function () {
            $scope.reload ? $scope.reload = false : $scope.reload = true;
            $timeout(function () { ($scope.reload ? $scope.reload = false : $scope.reload = true) }, 400);
        }

        $scope.list = {
            "banco": [],
            "uf": [],
            "codigoPagamentoGps": [],
            "cnae": [],
            "municipio": [],
            "pais": [],
            "tipoOperacao": []
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

        $scope.querySearchCnae = function (query) {
            var deferred = $q.defer();
            var config = { params: { search: query } }
            GenericoService.GetAll('/cnaes/searchComplete', config).then(
                function (response) {
                    if (response.data && response.data.length > 0) {
                        // results = response.data;
                        $scope.itens = response.data;
                        deferred.resolve(response.data);
                    }
                });
            return deferred.promise;
        }

        $scope.setCnaeId = function(){
            $scope.empresaFilial.cnaeId = $scope.empresaFilial.cnae.id;
        }

        if ($state.params && $state.params.id) {
            $rootScope.$broadcast('preloader:active');
            GenericoService.GetById('/empresaFilial/' + $state.params.id, function (response) {
                $rootScope.$broadcast('preloader:hide');
                if (response.status === 200) {
                    $scope.empresaFilial = response.data;


                    if (!$scope.empresaFilial.anexo) {
                        $scope.empresaFilial.anexo = {};
                    }

                    if($scope.empresaFilial.agenciaBancaria){
                        $scope.banco = $scope.empresaFilial.agenciaBancaria.banco;
                        $scope.bancoSelecionado();
                        $scope.agencia = $scope.empresaFilial.agenciaBancaria;
                        $scope.agenciaSelecionada();
                    } else {
                        $scope.banco = $scope.empresaFilial.banco;
                        $scope.bancoSelecionado();
                    }

                    if ($scope.empresaFilial.dataInicialAtividade) {
                        $scope.empresaFilial.dataInicialAtividade = moment(response.data.dataInicialAtividade);
                    }
                    if ($scope.empresaFilial.vigenciaInicial) {
                        $scope.empresaFilial.vigenciaInicial = moment(response.data.vigenciaInicial);
                    }
                    if ($scope.empresaFilial.vigenciaFinal) {
                        $scope.empresaFilial.vigenciaFinal = moment(response.data.vigenciaFinal);
                    }
                    if ($scope.empresaFilial.pais) {
                        $scope.empresaFilial.paisId = $scope.empresaFilial.pais.id;
                    }

                    if ($scope.empresaFilial.uf) {
                        $scope.empresaFilial.ufId = $scope.empresaFilial.uf.id;
                        $scope.getMunicipios($scope.empresaFilial.uf.id);
                    }
                    if ($scope.empresaFilial.municipio) {
                        $scope.empresaFilial.municipioId = $scope.empresaFilial.municipio.id;
                    }

                    $scope.empresaFilial.codigoGpsId = $scope.empresaFilial.codigoGps.id;
                    $scope.empresaFilial.cnaeId = $scope.empresaFilial.cnae.id;

                } else {
                    $scope.showSimpleToast("Empresa Filial não encontrada na base");
                }
            });
        } else {
            $scope.empresaFilial = {
                tipoInscricao: "CNPJ",
                anexo: {}
            }
        }

        $scope.filiais = [
            { tipo: "Ativa" },
            { tipo: "Inativa" },
            { tipo: "Correção Histórico" }
        ]

        $scope.situacoes = [
            { nome: "Liberado" },
            { nome: "Bloqueado" }
        ]

        $scope.tiposEstabelecimentos = [
            { tipo: "Único" },
            { tipo: "Principal" },
            { tipo: "Outros" }
        ]

        $scope.referenciasContribuicao = [
            { tipo: "Empregador" },
            { tipo: "Autônomo Liberal" },
            { tipo: "Empregados" }
        ]

        $scope.esferas = [
            { tipoEsfera: "Empresa Pública" },
            { tipoEsfera: "Empresa Privada" },
            { tipoEsfera: "Governo Federal" },
            { tipoEsfera: "Governo Estadual" },
            { tipoEsfera: "Governo Estadual (Secretarias)" },
            { tipoEsfera: "Governo Estadual (Estações)" },
            { tipoEsfera: "Governo Estadual (Empresas Públicas)" },
            { tipoEsfera: "Municipal" },
            { tipoEsfera: "Sociedade de Economia Mista" },
            { tipoEsfera: "Autarquia Municipal" }
        ]

        EnumService.Get("TipoOperacaoEnum").then(function (dados) {
            $scope.list.tipoOperacao = $filter('orderBy')(dados, 'label');
		});

        if ($state.params && $state.params.detalhes) {
            $scope.detalhes = true;
        }

        $scope.goBack = function () {
            $location.path('/empresaFilial/gestao');
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
                $scope.empresaFilial.bancoId = $scope.banco.id;
            }
        };

        $scope.querySearchAgencia = function (query) {
            var deferred = $q.defer();
            var config = { params: { search: query, bancoId: $scope.empresaFilial.bancoId } }
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
                $scope.empresaFilial.agenciaBancariaId = $scope.agencia.id;
            }
        };

        $scope.CodigoPagamentoGps = function () {
            $rootScope.$broadcast('preloader:active');
            GenericoService.GetAllDropdown('listaCodigosPagamentosGps', function (response) {
                if (response.status === 200) {
                    $scope.list.codigoPagamentoGps = response.data;
                    $rootScope.$broadcast('preloader:hide');
                } else if (response.status === 500) {
                    $scope.showSimpleToast("Falha no carregamento de dados, favor contate o administrador do sistema");
                }
            });
        }

        $scope.pais = function () {
            $rootScope.$broadcast('preloader:active');
            GenericoService.GetAllDropdown('listaNacionalidades', function (response) {
                if (response.status === 200) {
                    $scope.list.pais = response.data;
                    $rootScope.$broadcast('preloader:hide');
                } else if (response.status === 500) {
                    $scope.showSimpleToast("Falha no carregamento de dados, favor contate o administrador do sistema");
                }
            });
        }

        $scope.Cnae = function () {
            $rootScope.$broadcast('preloader:active');
            GenericoService.GetAllDropdown('listaCnaes', function (response) {
                if (response.status === 200) {
                    $scope.list.cnae = response.data;
                    $rootScope.$broadcast('preloader:hide');
                } else if (response.status === 500) {
                    $scope.showSimpleToast("Falha no carregamento de dados, favor contate o administrador do sistema");
                }
            });
        }

        $scope.Uf = function () {
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

        $scope.Uf();
        $scope.CodigoPagamentoGps();
        $scope.Cnae();
        $scope.pais();

        $scope.save = function () {
            $rootScope.$broadcast('preloader:active');
            if($scope.empresaFilial.empresaMatriz === false)
                delete $scope.empresaFilial.empresaMatriz;

            if ($scope.empresaFilial.cnpj && $scope.empresaFilial.cnpj != '' || $scope.empresaFilial.cei && $scope.empresaFilial.cei != '') {
                if ($scope.empresaFilial.id) {
                    GenericoService.Update('/empresaFilial', $scope.empresaFilial, function (response) {
                        $rootScope.$broadcast('preloader:hide');
                        if (response.status === 201 && response.data.success) {
                            $scope.showSimpleToast(response.data.message);
                            $location.path('/empresaFilial/gestao');
                        } else if (response.status === 400) {
                            $scope.showSimpleToast(response.data.message);
                            $location.path('empresaFilial/formulario/' + $state.params.id)
                        }
                    });
                } else {
                    GenericoService.Create('/empresaFilial', $scope.empresaFilial, function (response) {
                        $rootScope.$broadcast('preloader:hide');
                        if (response.status === 201 && response.data.success) {
                            $scope.showSimpleToast(response.data.message);
                            $location.path('/empresaFilial/gestao');
                        } else if (response.status === 400) {
                            $scope.showSimpleToast(response.data.message);
                            $location.path('/empresaFilial/formulario');
                        }
                    });
                }
            }else{
                $scope.showSimpleToast('Preencha ao menos um dos dois campos CNPJ ou CEI');
            }
        }

        $scope.getFile = function ($file) {
            $scope.file = $file;
            if ($scope.file[0]) {
                uploadSingleFile($scope.file[0]);
            }
        };

        $scope.loadInputFile = function () {
            document.getElementById('fileInput').click();
        };

        function uploadSingleFile(file) {
            GenericoService.UploadFile(file, 'empresafilial', function (response) {
                if (response.status === 200 && response.data.success) {
                    $scope.empresaFilial.anexo.fileDownloadUri = response.data.obj.fileDownloadUri;
                    $scope.empresaFilial.anexoId = response.data.obj.id;
                } else if (response.status === 400) {
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