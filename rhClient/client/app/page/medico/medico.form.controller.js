(function () {
    'use strict';

    angular.module('app.page')
        .controller('medicoFormCtrl', ['$q', '$scope', '$mdToast', '$location', '$state', '$rootScope', '$filter', 'EnumService', 'GenericoService', 'RestService', medicoFormCtrl]);

    function medicoFormCtrl($q, $scope, $mdToast, $location, $state, $rootScope, $filter, EnumService, GenericoService, RestService) {

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
            "empresa": [],
            "filial": [],
            "ufTrabalho": [],
            "uf": [],
            "municipio": [],
            "nacionalidade": [],
            "usuario": [],
            "especialidadeMedicaList":[],
            "telefones": []
        };

        $scope.acessaTela();
        $scope.detalhes = false;
        $scope.edit = false;

        $scope.mostrarStatus = true;
        
        $scope.medico = {};
        $scope.medico.telefones = [];
        $scope.medico.emails = [];

        EnumService.Get("GeneroEnum").then(function (dados) {
            $scope.list.generos = dados;
        });

        EnumService.Get("EstadoCivilEnum").then(function (dados) {
            $scope.list.estadoCivil = dados;
        });

        EnumService.Get("TipoTelefoneEnum").then(function (dados) {
            $scope.list.tipoTelefone = $filter('orderBy')(dados, 'label');
        });

        EnumService.Get("ColetivoEnum").then(function (dados) {
            $scope.list.simNao = dados;
        });

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

        $scope.getUnidadesFederativas = function () {
            $rootScope.$broadcast('preloader:active');
            GenericoService.GetAllDropdown('listaUfs', function (response) {
                if (response.status === 200) {
                    $scope.list.uf = response.data;
                    $scope.list.ufTrabalho = response.data;
                    $rootScope.$broadcast('preloader:hide');
                } else if (response.status === 500) {
                    $scope.showSimpleToast("Falha no carregamento de dados, favor contate o administrador do sistema");
                }
            });
        }

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

        $scope.getMunicipiosByUf = function () {
            $rootScope.$broadcast('preloader:active');
            var config = { params: { id: $scope.medico.enderecoUfId } };
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

        $scope.querySearchUsuario = function (query) {
            if (query && query.length > 2) {
                var config = {
                    params: {
                        search: query
                    }
                }
                var deferred = $q.defer();
                GenericoService.GetAll('/usuario/search', config).then(
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

        $scope.adicionarTelefone = function () {
            adicionarTelefoneEmail(true);
        }

        $scope.adicionarEmail = function () {
            adicionarTelefoneEmail(false);
        }

        function adicionarTelefoneEmail(isTelefone) {
            var msg = "Não foi possível adicionar o telefone, verifique se os campos estão preenchidos";
            var msgEmail = "Não foi possível adicionar o email, verifique se os campos estão preenchidos";
            
            if(isTelefone) {
                if($scope.numeroTelefone && $scope.tipoTelefone) {
                    let telefone = { numero: $scope.numeroTelefone, tipo: $scope.tipoTelefone }
                    $scope.medico.telefones.push(telefone);
                    
                    delete $scope.numeroTelefone;
                    delete $scope.tipoTelefone;
                } else {
                    $scope.showSimpleToast(msg);
                }
            } else {
                if($scope.email) {
                    $scope.medico.emails.push($scope.email);
                    
                    delete $scope.email;
                } else {
                    $scope.showSimpleToast(msgEmail);
                }
            }
        }

        $scope.removerTelefone = function (index) {
            removerTelefoneEmail(index, true);
        }

        $scope.removerEmail = function (index) {
            removerTelefoneEmail(index, false);
        }

        function removerTelefoneEmail(index, isEndereco) {
            if(isEndereco){
                $scope.medico.telefones.splice(index, 1);
            } else {
                $scope.medico.emails.splice(index, 1);
            }
        }

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

        if ($state.params && $state.params.id) {
            $rootScope.$broadcast('preloader:active');
            GenericoService.GetById('/medico/' + $state.params.id, function (response) {
                $rootScope.$broadcast('preloader:hide');
                if (response.status === 200) {
                    $scope.medico = response.data;

                    $scope.mostrarStatus = false;
                    $scope.edit = true;
                    $scope.medico.naturalidadeId = $scope.medico.naturalidade.id;
                    $scope.medico.nacionalidadeId = $scope.medico.nacionalidade.id;
                    $scope.medico.municipioId = $scope.medico.municipio.id;
                    if($scope.medico.usuario){
                        $scope.medico.usuarioId = $scope.medico.usuario.id;
                    }

                    $scope.medico.dataNascimento = moment(response.data.dataNascimento);
                    $scope.medico.dataExpedicaoRg = moment(response.data.dataExpedicaoRg);
                    $scope.medico.dataExpedicaoCrm = moment(response.data.dataExpedicaoCrm);
                    $scope.medico.empresaId = response.data.empresa.id;

                    if ($scope.medico.filial)
                        $scope.medico.filialId = $scope.medico.filial.id;

                    $scope.unidadeFederativa = $scope.medico.municipio.uf.id;
                    $scope.getMunicipios($scope.ufNaturalidade, true);
                    $scope.getMunicipios($scope.unidadeFederativa, false);
                }else {
                    $scope.showSimpleToast("Médico não encontrado na base");
                }
            });
        }

        if ($state.params && $state.params.detalhes) {
            $scope.detalhes = true;
        }

        $scope.Empresa();
        $scope.Filial();
        $scope.getUnidadesFederativas();
        $scope.getListaEspecialidadesMedicas();

        $scope.save = function () {
            // Validação dos filtros
            if (!$scope.medico.telefones.length > 0) {
                $scope.showSimpleToast("Favor informe ao menos um telefone.");
                return;
            }

            if (!$scope.medico.emails.length > 0) {
                $scope.showSimpleToast("Favor informe ao menos uma e-mail.");
                return;
            }

            $rootScope.$broadcast('preloader:active');
            if ($scope.medico.id) {
                GenericoService.Update('/medico', $scope.medico, function (response) {
                    $rootScope.$broadcast('preloader:hide');
                    if (response.status === 201 && response.data.success) {
                        $scope.showSimpleToast(response.data.message);
                        $location.path('/medico/gestao');
                    } else if (response.status === 400) {
                        $scope.showSimpleToast(response.data.message);
                        $location.path('medico/formulario/' + $state.params.id)
                    }
                });
            } else {
                GenericoService.Create('/medico', $scope.medico, function (response) {
                    $rootScope.$broadcast('preloader:hide');
                    if (response.status === 201 && response.data.success) {
                        $scope.showSimpleToast(response.data.message);
                        $location.path('/medico/gestao');
                    } else if (response.status === 400) {
                        $scope.showSimpleToast(response.data.message);
                        $location.path('/medico/formulario');
                    }
                });
            }
        }

        $scope.goBack = function () {
            $location.path('/medico/gestao');
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