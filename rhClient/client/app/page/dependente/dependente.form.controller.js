(function () {
    'use strict';

    angular.module('app.page')
        .controller('dependenteFormCtrl', ['$scope', '$mdToast', '$location', '$state', '$rootScope', 'GenericoService', 'EnumService', dependenteFormCtrl]);

    function dependenteFormCtrl($scope, $mdToast, $location, $state, $rootScope, GenericoService, EnumService) {

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
        $scope.acessaTela();
        moment.locale('pt-BR');
        $scope.list = {
            
        }

        $scope.dependente = {dependenciaIr: false, dependenciaSf: false};

        $scope.dependentes = {
            funcionario: {},
            dependentes: []
        };

        $scope.verifyPermission = false;

        $scope.adicionarDependente = function () {
            if ($scope.dependente.nome == "") {
                $scope.showSimpleToast("Nome de Dependente em branco.");
            } else {
                if ($scope.dependente.inicioMotivo != "" && typeof ($scope.dependente.inicioMotivo) != "undefined") {
                    $scope.dependente.inicioMotivo = moment($scope.dependente.inicioMotivo).toDate();
                }
                if ($scope.dependente.fimMotivo != "" && typeof ($scope.dependente.fimMotivo) != "undefined") {
                    $scope.dependente.fimMotivo = moment($scope.dependente.fimMotivo).toDate();
                }

                $scope.dependente.funcionarioId = $scope.dependentes.funcionario.id;

                $scope.dependentes.dependentes.push($scope.dependente);
                $scope.dependente = { dependenciaIr: false, dependenciaSf: false };
            }
            $scope.verifyPermission = false;
        }

        $scope.removerDependente = function (index) {
            $scope.dependentes.dependentes.splice(index, 1);
        }

        $scope.editarDependente = function (index) {
            $scope.dependente = $scope.dependentes.dependentes[index];
            if ($scope.dependentes.dependentes[index].inicioMotivo != "" && typeof ($scope.dependentes.dependentes[index].inicioMotivo) != "undefined") {
                $scope.dependente.inicioMotivo = moment($scope.dependentes.dependentes[index].inicioMotivo);
            }
            if ($scope.dependentes.dependentes[index].fimMotivo != "" && typeof ($scope.dependentes.dependentes[index].fimMotivo) != "undefined") {
                $scope.dependente.fimMotivo = moment($scope.dependentes.dependentes[index].fimMotivo);
            }
            if ($scope.dependentes.dependentes[index].dataNascimento != "" && typeof ($scope.dependentes.dependentes[index].dataNascimento) != "undefined") {
                $scope.dependente.dataNascimento = moment($scope.dependentes.dependentes[index].dataNascimento);
            }
            if ($scope.dependentes.dependentes[index].dataDocumentoComprobatorio != "" && typeof ($scope.dependentes.dependentes[index].dataDocumentoComprobatorio) != "undefined") {
                $scope.dependente.dataDocumentoComprobatorio = moment($scope.dependentes.dependentes[index].dataDocumentoComprobatorio);
            }
            $scope.getMunicipios($scope.dependente.unidadeFederativaId);
            $scope.dependentes.dependentes.splice(index, 1);
            $scope.verifyPermission = true;
        }


        $scope.validarForm = function () {
            if (
                ($scope.dependente.tipo != null && $scope.dependente.tipo != '') &&
                ($scope.dependente.nome != null && $scope.dependente.nome != '') &&
                ($scope.dependente.sexo != null && $scope.dependente.sexo != '') &&
                ($scope.dependente.municipioId != null && $scope.dependente.municipioId != '') &&
                ($scope.dependente.unidadeFederativaId != null && $scope.dependente.unidadeFederativaId != '')
            ) {
                $scope.verifyPermission = true;
            }
        };


        $scope.detalhes = false;
        $scope.edit = false;
        var i = 0;

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

        $scope.getMunicipios = function (id) {
            $scope.getMunicipiosByNaturalidade(id);
        }

        $scope.getMunicipiosByNaturalidade = function (id) {
            GenericoService.GetAllDropdownById(id, function (response) {
                if (response.status === 200) {
                    $scope.list.municipio = response.data;

                } else if (response.status === 500) {
                    $scope.showSimpleToast("Não existem municípios cadastrados para este estado");
                }
            });
        }

        if ($state.params && $state.params.funcionarioId) {
            $rootScope.$broadcast('preloader:active');
            GenericoService.GetById('/funcionario/' + $state.params.funcionarioId, function (response) {
                $rootScope.$broadcast('preloader:hide');
                if (response.status === 200) {
                    $scope.dependentes.funcionario = response.data;
                } else {
                    $scope.showSimpleToast("Funcionário não encontrado na base");
                }
            });

            GenericoService.GetById('/dependentes/' + parseInt($state.params.funcionarioId), function (response) {
                if (response.status === 200) {
                    
                    $scope.dependentes.dependentes = response.data;
                    for (var i = 0; i < response.data.length; i++) {
                        $scope.dependentes.dependentes[i].funcionarioId = $scope.dependentes.dependentes[i].funcionario.id;
                        $scope.edit = true;
                    }
                }
            });   
        } 

        if ($state.params && $state.params.detalhes){
            $rootScope.$broadcast('preloader:active');
            $scope.detalhes = true;
            GenericoService.GetById('/funcionario/' + $state.params.funcionarioId, function (response) {
                $rootScope.$broadcast('preloader:hide');
                if (response.status === 200) {
                    $scope.dependentes.funcionario = response.data;
                } else {
                    $scope.showSimpleToast("Funcionário não encontrado na base");
                }
            });

            GenericoService.GetById('/dependentes/' + parseInt($state.params.funcionarioId), function (response) {
                if (response.status === 200) {

                    $scope.dependentes.dependentes = response.data;
                    for (var i = 0; i < response.data.length; i++) {
                        $scope.dependentes.dependentes[i].funcionarioId = $scope.dependentes.dependentes[i].funcionario.id;
                    }
                }
            });

        }

        EnumService.Get("DependenteTipoDependenteEnum").then(function (dados) {
            $scope.tiposDependentes = dados;
        });

        $scope.sexos = [
            { nome: "Feminino" },
            { nome: "Masculino" }
        ]

        $scope.unidadesFederativas = function () {
            $rootScope.$broadcast('preloader:active');
            GenericoService.GetAllDropdown('listaUfs', function (response) {
                if (response.status === 200) {
                    $scope.list.unidadesFederativas = response.data;
                    $rootScope.$broadcast('preloader:hide');
                } else if (response.status === 500) {
                    $scope.showSimpleToast("Falha no carregamento de dados, favor contate o administrador do sistema");
                }
            });
        }
        $scope.unidadesFederativas();

        $scope.goBack = function () {
            $location.path('/dependente/gestao');
        }

        $scope.save = function () {
            $rootScope.$broadcast('preloader:active');
            if($scope.edit){
                GenericoService.Update('/dependente', $scope.dependentes.dependentes, function (response) {
                    $rootScope.$broadcast('preloader:hide');
                    if (response.status === 201 && response.data.success) {
                        $scope.showSimpleToast(response.data.message);
                        $location.path('/dependente/gestao');
                    } else if (response.status === 400) {
                        $scope.showSimpleToast(response.data.message);
                        $location.path('/dependente/formulario');
                    }
                }); 
            }else{
                GenericoService.Create('/dependente', $scope.dependentes.dependentes, function (response) {
                    $rootScope.$broadcast('preloader:hide');
                    if (response.status === 201 && response.data.success) {
                        $scope.showSimpleToast(response.data.message);
                        $location.path('/dependente/gestao');
                    } else if (response.status === 400) {
                        $scope.showSimpleToast(response.data.message);
                        $location.path('/dependente/formulario');
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