(function () {
    'use strict';

    angular.module('app.page')
        .controller('correcaoSalarialFormCtrl', ['$scope', '$mdToast', '$location', '$state', '$rootScope', 'GenericoService',  'EnumService', correcaoSalarialFormCtrl]);

    function correcaoSalarialFormCtrl($scope, $mdToast, $location, $state, $rootScope, GenericoService, EnumService) {

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

        $scope.list ={
            "tipoProcessamento": [],
            "empresa": [],
            "filial": [],
            "lotacao": [],
            "centroCusto": [],
            "sindicato": [],
            "funcao": [],
            "nivelSalarial": [],
            "situacao": [],
            "tipoFolha": [],
            "verba": []
        }

        EnumService.Get("TipoArredondamentoEnum").then(function (dados) {
            $scope.list.tipoArredondamento = dados;
        });

        $scope.acessaTela();
        $scope.detalhes = false;

        if ($state.params && $state.params.id) {
            $rootScope.$broadcast('preloader:active');
            GenericoService.GetById('/correcao/' + $state.params.id, function (response) {
                $rootScope.$broadcast('preloader:hide');
                if (response.status === 200) {
                    $scope.correcao = response.data;

                    $scope.correcao.dataCompetencia = moment(response.data.dataCompetencia);

                    if($scope.correcao.retroativo){
                        $scope.correcao.retroativo = moment(response.data.retroativo);
                    }

                    if($scope.correcao.empresa){
                        $scope.correcao.empresaId =  $scope.correcao.empresa.id;
                    }

                    if($scope.correcao.filial){
                        $scope.correcao.filialId =  $scope.correcao.filial.id;
                    }

                } else {
                    $scope.showSimpleToast("Correção não encontrada na base");
                }
            });
        }

        if ($state.params && $state.params.detalhes) {
            $scope.detalhes = true;
        }

        $scope.limpaVerbaAbono = function(){
            if($scope.correcao.verbaAbono){
                delete $scope.correcao.verbaAbono;
            } else if($scope.correcao.retroativo){
                delete $scope.correcao.retroativo;
            }
            
        }

        $scope.goBack = function () {
            $location.path('/correcaoSalarial/gestao');
        }

        // Início de carregamento de dropdowns do formulário

                $scope.TipoProcessamento = function () {
                    $rootScope.$broadcast('preloader:active');
                    GenericoService.GetAllDropdown('listaTiposProcessamentos', function (response) {
                        if (response.status === 200) {
                            $scope.list.tipoProcessamento = response.data;
                            $rootScope.$broadcast('preloader:hide');
                        } else if (response.status === 500) {
                            $scope.showSimpleToast("Falha no carregamento de dados, favor contate o administrador do sistema");
                        }
                    });
                }
                $scope.TipoProcessamento();

                $scope.Empresa = function () {
                    $rootScope.$broadcast('preloader:active');
                    GenericoService.GetAllDropdown('listaEmpresasMatrizes', function (response) {
                        if (response.status === 200) {
                            $scope.list.empresa = response.data;
                            $rootScope.$broadcast('preloader:hide');
                        } else if (response.status === 500) {
                            $scope.showSimpleToast("Falha no carregamento de dados, favor contate o administrador do sistema");
                        }
                    });
                }
                $scope.Empresa();

                $scope.Filial = function () {
                    $rootScope.$broadcast('preloader:active');
                    GenericoService.GetAllDropdown('listaEmpresasNaoMatrizes', function (response) {
                        if (response.status === 200) {
                            $scope.list.filial = response.data;
                            $rootScope.$broadcast('preloader:hide');
                        } else if (response.status === 500) {
                            $scope.showSimpleToast("Falha no carregamento de dados, favor contate o administrador do sistema");
                        }
                    });
                }
                $scope.Filial()

                $scope.Lotacao = function () {
                    $rootScope.$broadcast('preloader:active');
                    GenericoService.GetAllDropdown('listaLotacoes', function (response) {
                        if (response.status === 200) {
                            $scope.list.lotacao = response.data;
                            $rootScope.$broadcast('preloader:hide');
                        } else if (response.status === 500) {
                            $scope.showSimpleToast("Falha no carregamento de dados, favor contate o administrador do sistema");
                        }
                    });
                }
                $scope.Lotacao();

                $scope.CentroCusto = function () {
                    $rootScope.$broadcast('preloader:active');
                    GenericoService.GetAllDropdown('listaCentroCustos', function (response) {
                        if (response.status === 200) {
                            $scope.list.centroCusto = response.data;
                            $rootScope.$broadcast('preloader:hide');
                        } else if (response.status === 500) {
                            $scope.showSimpleToast("Falha no carregamento de dados, favor contate o administrador do sistema");
                        }
                    });
                }
                $scope.CentroCusto();

                $scope.Sindicato = function () {
                    $rootScope.$broadcast('preloader:active');
                    GenericoService.GetAllDropdown('listaSindicatos', function (response) {
                        if (response.status === 200) {
                            $scope.list.sindicato = response.data;
                            $rootScope.$broadcast('preloader:hide');
                        } else if (response.status === 500) {
                            $scope.showSimpleToast("Falha no carregamento de dados, favor contate o administrador do sistema");
                        }
                    });
                }
                $scope.Sindicato();

                $scope.Funcao = function () {
                    $rootScope.$broadcast('preloader:active');
                    GenericoService.GetAllDropdown('listaFuncoes', function (response) {
                        if (response.status === 200) {
                            $scope.list.funcao = response.data;
                            $rootScope.$broadcast('preloader:hide');
                        } else if (response.status === 500) {
                            $scope.showSimpleToast("Falha no carregamento de dados, favor contate o administrador do sistema");
                        }
                    });
                }
                $scope.Funcao();

                $scope.NivelSalarial = function () {
                    $rootScope.$broadcast('preloader:active');
                    GenericoService.GetAllDropdown('listaNiveisSalariais', function (response) {
                        if (response.status === 200) {
                            $scope.list.nivelSalarial = response.data;
                            $rootScope.$broadcast('preloader:hide');
                        } else if (response.status === 500) {
                            $scope.showSimpleToast("Falha no carregamento de dados, favor contate o administrador do sistema");
                        }
                    });
                }
                $scope.NivelSalarial();

                $scope.Situacao = function () {
                    $rootScope.$broadcast('preloader:active');
                    GenericoService.GetAllDropdown('listaAfastamentos', function (response) {
                        if (response.status === 200) {
                            $scope.list.situacao = response.data;
                            $rootScope.$broadcast('preloader:hide');
                        } else if (response.status === 500) {
                            $scope.showSimpleToast("Falha no carregamento de dados, favor contate o administrador do sistema");
                        }
                    });
                }
                $scope.Situacao();

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
                $scope.TipoFolha();

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
                $scope.Verba()
        // Fim carregamento de dropdowns


        $scope.save = function () {
            $rootScope.$broadcast('preloader:active');
            if ($scope.correcao.id) {
                GenericoService.Update('/correcao',$scope.correcao, function (response) {
                    $rootScope.$broadcast('preloader:hide');
                    if (response.status === 201 && response.data.success) {
                        $scope.showSimpleToast(response.data.message);
                        $location.path('/correcaoSalarial/gestao');
                    } else if (response.status === 400) {
                        $scope.showSimpleToast(response.data.message);
                        $location.path('correcaoSalarial/formulario/' + $state.params.id)
                    }
                });
            } else {
                GenericoService.Create('/correcao', $scope.correcao, function (response) {
                    $rootScope.$broadcast('preloader:hide');
                    if (response.status === 201 && response.data.success) {
                        $scope.showSimpleToast(response.data.message);
                        $location.path('/correcaoSalarial/gestao');
                    } else if (response.status === 400) {
                        $scope.showSimpleToast(response.data.message);
                        $location.path('/correcaoSalarial/formulario');
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